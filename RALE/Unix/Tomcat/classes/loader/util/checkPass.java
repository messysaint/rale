// Original code from  http://developer.iplanet.com/docs/technote/ldap/pass_sha.html
package loader.util;

/*
 * Used to digest/hash userpassword (called by modifyUserPassword3.java)
 */
import java.security.MessageDigest; // http://www.javasoft.com/products/jdk/1.1/docs/api/java.security.MessageDigest.html
import javax.commerce.util.BASE64Encoder; // http://www.javasoft.com/products/commerce/ jecf.jar
import javax.commerce.util.BASE64Decoder; // An alternative: import util.BASE64Encoder; // http://professionals.com/~cmcmanis/java/encoders/
import java.io.*;

public class checkPass {
    
    
    String password = null;
    String digest = null;
    
    public checkPass( String dgst, String passwd ) {
        digest = dgst;
        password = passwd;
    }
    
    public boolean isValid() throws java.io.IOException, java.security.NoSuchAlgorithmException {
        
        boolean rvalue = false;
        
        if(digest.regionMatches(true, 0, "{SHA}", 0, 5)) {
            digest = digest.substring(5); // ignore the label
        } else if (digest.regionMatches(true, 0, "{SSHA}", 0, 6)) {
            digest = digest.substring(6); // ignore the label
        }
        
        BASE64Decoder base64 = new BASE64Decoder();
        byte[][] hs = split(base64.decodeBuffer(digest), 20);
        byte[] hash = hs[0];
        byte[] salt = hs[1];
        
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        
        sha.reset();
        sha.update(password.getBytes());
        sha.update(salt);
        byte[] pwhash = sha.digest();
        
        if( sha.isEqual( hash, pwhash ) ) {
            rvalue = true;
        }
        
        return rvalue;
        
    }
    
    
    private byte[] concatenate(byte[] l, byte[] r) {
        byte[] b = new byte [l.length + r.length];
        System.arraycopy(l, 0, b, 0, l.length);
        System.arraycopy(r, 0, b, l.length, r.length);
        return b;
    }
    
    private byte[][] split(byte[] src, int n) {
        byte[] l, r;
        if (src.length <= n) {
            l = src;
            r = new byte[0];
        } else {
            l = new byte[n];
            r = new byte[src.length - n];
            System.arraycopy(src, 0, l, 0, n);
            System.arraycopy(src, n, r, 0, r.length);
        }
        byte[][] lr = {l, r};
        return lr;
    }
    
    private String hexits = "0123456789abcdef";
    
    private String toHex( byte[] block ) {
        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < block.length; ++i ) {
            buf.append( hexits.charAt( ( block[i] >>> 4 ) & 0xf ) );
            buf.append( hexits.charAt( block[i] & 0xf ) );
        }
        return buf + "";
    }
    
    private byte[] fromHex( String s ) {
        s = s.toLowerCase();
        byte[] b = new byte [(s.length() + 1) / 2];
        int j = 0;
        int h;
        int nybble = -1;
        for (int i = 0; i < s.length(); ++i) {
            h = hexits.indexOf(s.charAt(i));
            if (h >= 0) {
                if (nybble < 0) {
                    nybble = h;
                } else {
                    b[j++] = (byte)((nybble << 4) + h);
                    nybble = -1;
                }
            }
        }
        if (nybble >= 0) {
            b[j++] = (byte)(nybble << 4);
        }
        if (j < b.length) {
            byte[] b2 = new byte [j];
            System.arraycopy(b, 0, b2, 0, j);
            b = b2;
        }
        return b;
    }
    
}

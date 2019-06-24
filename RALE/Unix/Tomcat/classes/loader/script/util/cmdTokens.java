/*
 * tokenize.java
 *
 * Created on May 14, 2003, 2:44 PM
 */

package loader.script.util;

import java.util.*;
/**
 *
 * @author  BMutia
 */
public class cmdTokens {
    
    /** Creates a new instance of tokenize */
    public cmdTokens() {
    }
    
    
    /**
     * Return a String array of tokens.  The delimeter is always space/s.
     */
    public static String[] getTokens( String s, String delim ) {
        
        StringTokenizer tokenString = new StringTokenizer( s, delim, false );
        String[] returnValue = new String[tokenString.countTokens()];
        
        int i = 0;
        while( tokenString.hasMoreTokens()) {
            returnValue[i++] = tokenString.nextToken();
        }
        
        return returnValue;
    }
    
    
    /**
     * Return a String array of tokens.  The delimeter is always space/s.
     */
    public static String[] getTokens( String s ) {
        
        StringTokenizer tokenString = new StringTokenizer( s );
        String[] returnValue = new String[tokenString.countTokens()];
        
        int i = 0;
        while( tokenString.hasMoreTokens() ) {
            returnValue[i++] = tokenString.nextToken();
        }
        
        return returnValue;
    }
    
    /**
     * Return a String of token elements.  The delimeter is always space/s.
     */
    public static String arrayToString( String[] array ) {
        
        String rvalue = new String();
        
        for( int i = 0 ; i < array.length ; i++ ) {
            rvalue += " " + array[i];
        }
        return rvalue;
    }
    
}

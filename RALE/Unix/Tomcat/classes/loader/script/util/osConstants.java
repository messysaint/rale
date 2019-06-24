package loader.script.util;

/*
 * systemConstants.java
 *
 * Created on May 19, 2003, 2:02 PM
 */

/**
 *
 * @author  BMutia
 */
public class osConstants {
    
    static final String FS = System.getProperty( "file.separator" );
    
    /** Creates a new instance of systemConstants */
    public osConstants() {
    }
    
    public static String getFileSeparator() {
        return FS;
    }
    
}

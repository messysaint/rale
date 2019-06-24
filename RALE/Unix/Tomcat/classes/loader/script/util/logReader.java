/*
 *
 *
 */
package loader.script.util;

import java.io.*;
import java.util.*;

/**
 *
 * @author  BMUTIA
 * @version
 */
public class logReader extends Object {
    
    DataInputStream in;
    String logFile;
    
    // class constructor
    public logReader( String fileName ) {
        
        logFile = fileName;
        
        try {
            in = new DataInputStream( new BufferedInputStream( new FileInputStream( fileName )));
        } catch( IOException e) {
            logWriter.write( e.toString() );
        }
        
    }
    
    
    // read data
    public String getLog() {
        
        String rvalue = "";
        String s = new String();
        
        try {
            while( ( s = in.readLine() )!= null ) {
                rvalue += s + '\n';
            }
            
            try {
                in.close();
            } catch( IOException e ){
                logWriter.write( e.toString() );
            }
        } catch( IOException e){
            logWriter.write( e.toString() );
        }
        
        return rvalue;
        
    }
    
    
    
}

/*
From http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?
 
Java Traps
When Runtime.exec() won't
Navigate yourself around pitfalls related to the Runtime.exec() method
 
By Michael C. Daconta
 */

package loader.script.util;

import java.io.*;

public class ioGobbler extends Thread {
    
    InputStream is;
    String type;
    String logMessage;
    
    public ioGobbler(InputStream is, String type) {
        this.is = is;
        this.type = type;
        logMessage = "";
    }
    
    public void run() {
        
        try {
            InputStreamReader isr = new InputStreamReader( is );
            BufferedReader br = new BufferedReader( isr );
            String line = null;
            while ( ( line = br.readLine() ) != null ) {
                logMessage += line + '\n';
            }
            //logWriter.write( logMessage );
        } catch( IOException ioe ) {
            logMessage += ioe.toString();
            logWriter.write( logMessage );
        }
        
    }
    
    
    public String getLogMessage() {
        return logMessage;
    }
    
    public void clearLogMessage() {
        logMessage = null;
    }
    
}


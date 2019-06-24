/*
 * requestWorker.java
 *
 * Created on May 14, 2003, 4:55 PM
 */
package loader.script.worker;

import java.io.*;

import loader.script.util.*;
/**
 *
 * @author  BMutia
 */
public class implementor {
    
    String fs = osConstants.getFileSeparator();
    scriptMap cmdMap = new scriptMap();
    
    String scriptDir = null;
    String command = null;
    String parameters = null;
    String logFile = null;
    boolean verbose = true;
    
    String message = null;
    
    /** Creates a new instance of requestWorker */
    public implementor(String dir, String cmd, String param, boolean verboseLog) {
        scriptDir = dir;
        command = cmdMap.getScriptName( cmd );
        parameters = param;
        logFile = cmdMap.getLogFileName( cmd );
        verbose = verboseLog;
    }
    
    
    public synchronized boolean runScript() {
        
        boolean rvalue = false;
        String execStr = scriptDir + fs + command;
        
        if( (new File( execStr ).exists() ) ) {
            
            
            try {
                // attach parameters
                execStr += " " + parameters;
                execStr = execStr.trim();
                
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec( execStr );
                
                // ------------
                ioGobbler errorGobbler = new ioGobbler(proc.getErrorStream(), "Error");
                ioGobbler outputGobbler = new ioGobbler(proc.getInputStream(), "Output");
                errorGobbler.start();
                outputGobbler.start();
                // ------------
                
                int exit = proc.waitFor();
                
                rvalue = ( exit == 0 ) ? true : false;
                
                message = "[" + execStr + "] [" + exit  + "]";
                
                if( verbose ) {
                    message += "\n\n[Standard Output]\n" +
                    outputGobbler.getLogMessage() +
                    "\n[Standard Error]\n" +
                    errorGobbler.getLogMessage();
                    
                    if( logFile != null ) { // if this is not scheduled command read log file
                        if( !logFile.trim().toLowerCase().equalsIgnoreCase( "null" ) ) { 
                            if( new File( logFile ).exists() ) {
                                logReader lr = new logReader( logFile );
                                message += "\n\n[Script Log]\n" + lr.getLog();
                            } else {
                                message += "\n\n[Error: " + logFile + " does not exist]";
                            }
                        }
                    } else {
                        // this is a scheduled command so there is no log file
                    }
                    
                }
                
                errorGobbler.clearLogMessage();
                outputGobbler.clearLogMessage();
                
            } catch ( Throwable t ) {
                message = t.toString();
            }
            
        } else { // script file was not found
            message = "[Error: " + execStr + " was not found]";
        }
        
        return rvalue;
        
    }
    
    
    public String getReturnMessage() {
        return message;
    }
    
}

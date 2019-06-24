/*
 *
 *
 * Created on May 14, 2003, 4:31 PM
 */

package loader.script.util;

import java.util.*;

/**
 *
 * @author  BMutia
 */
public class scriptMap {
    
    static public final String CMD_USER_COMMANDS = "getUserCommands";
    static public final String CMD_SCHEDULED_COMMANDS = "getScheduledCommands";
    
    // for user commands
    static HashMap commandMap = new HashMap();
    static HashMap logFileMap = new HashMap();
    
    // for scheduled commands
    static HashMap scheduleMap = new HashMap();
    static HashMap runTimeMap = new HashMap();
    
    
    /** Creates a new instance of commandConstants */
    public scriptMap() {
    }
    
    
    
    public void addCommandBundle( String prefix, ResourceBundle bundle ) {
        
        Enumeration enum = bundle.getKeys();
        String key = new String();
        
        // store commands and scriptnames to command map
        while( enum.hasMoreElements() ) {
            
            key = (String) enum.nextElement();
            if( key.startsWith( prefix ) ) { 
                String[] tokens = cmdTokens.getTokens( bundle.getString( key ) );
                commandMap.put( tokens[0].trim(), tokens[1].trim() );  // command 
                logFileMap.put( tokens[0].trim(), tokens[1].trim() );  // log file - dynamically generate
                //logWriter.write( tokens[0].trim() + " " + tokens[1].trim() );
            }
            
        } // while
        
    }
    
    //returns shell script name to execute
    public String getScriptName( String key ) {
        String rvalue = (String) commandMap.get( key );
        if( rvalue == null ) {
            rvalue = (String) scheduleMap.get( key );
        }
        return rvalue;
    }
    
    //returns shell logfile name to read
    public String getLogFileName( String key ) {
        return (String) logFileMap.get( key );
    }
    
    //returns true if the command is found
    public boolean isValidCommand( String key ) {
        return commandMap.containsKey( key );
    }
    
    //returns String array of valid commands
    public String[] getCommands() {
        
        Set keys = commandMap.keySet();
        String[] commands = new String[commandMap.size()];
        
        Iterator iter = keys.iterator();
        
        for( int i = 0 ; iter.hasNext() ; i++ ) {
            commands[i] = (String) iter.next();
        }
        
        return commands;
        
    }
    
    
    
    
}

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
public class parameterMap {
    
    
    static HashMap parameterMap = new HashMap();
    scriptMap cmdMap = new scriptMap();
    
    /** Creates a new instance of commandConstants */
    public parameterMap() {
    }
    
       
    public void addParameterBundle( String[] commands, ResourceBundle bundle ) {
        
        // store parameters to parameter map
        String paramList = null;
        
        for( int i = 0 ; i < commands.length ; i++ ) {
            if( cmdMap.isValidCommand( commands[i] ) ) {
                paramList = bundle.getString( commands[i] );
                parameterMap.put( commands[i], paramList );
            }
        }
        
    }
    
    
    //returns String array of valid Parameters
    public String[] getParameters() {
        
        Set keys = parameterMap.keySet();
        String[] parameters = new String[parameterMap.size()];
        String paramTemp = null;
        String cmdKey = null;
        
        Iterator iter = keys.iterator();
        
        for( int i = 0 ; iter.hasNext() ; i++ ) {
            cmdKey = (String) iter.next();
            paramTemp = '[' + cmdKey + '=' + (String) parameterMap.get( cmdKey ) + ']';
            parameters[i] = paramTemp;
        }
        
        return parameters;
        
    }
    
    
}

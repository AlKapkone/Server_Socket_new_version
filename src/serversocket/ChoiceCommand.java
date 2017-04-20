package serversocket;

import java.sql.SQLException;
import org.json.JSONException;

public class ChoiceCommand {
    private final Execute execute = new Execute();
    public String choice(String command, String request) throws SQLException, ClassNotFoundException, JSONException{
        
        String response ;
        switch (command){
            case "Add" : 
                execute.addUser(request);
                response = "Add successfully";
                break;

            case "Del" :
                response = execute.deleteUser(request);
                break;

            case "Upd" :
                execute.updateUser(request);
                response = "Update successfully";
                break;

            case "Get" :
                response = execute.getAllUsers();
                break;
                
            default:
                response = "Error";
        }   
        return response;
    }    
}
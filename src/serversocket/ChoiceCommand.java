package serversocket;

import java.sql.SQLException;
import org.json.JSONException;

public class ChoiceCommand {
    private final Exsecute exsecute = new Exsecute();
    public String choice(String command, String request) throws SQLException, ClassNotFoundException, JSONException{
        
        String response ;
        switch (command){
            case "Add" : 
                exsecute.addUser(request);
                response = "Add successfully";
                break;

            case "Del" :
                response = exsecute.deleteUser(request);
                break;

            case "Upd" :
                exsecute.updateUser(request);
                response = "Update successfully";
                break;

            case "Get" :
                response = exsecute.getAllUsers();
                break;
                
            default:
                response = "Error";
        }   
        return response;
    }    
}

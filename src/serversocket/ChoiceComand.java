package serversocket;

import java.sql.SQLException;
import org.json.JSONException;

public class ChoiceComand {
    private final Exsecute exsecute = new Exsecute();
    public String choice(String command, String request) throws SQLException, ClassNotFoundException, JSONException{
        
        String respons ;
        switch (command){
            case "Add" : 
                exsecute.addUser(request);
                respons = "Add succesfull";
                break;

            case "Del" :
                respons = exsecute.deleteUser(request);
                break;

            case "Upd" :
                exsecute.updateUser(request);
                respons = "Update successfully";
                break;

            case "Get" :
                respons = exsecute.getAllUsers();
                break;
                
            default:
                respons = "Error";
        }   
        return respons;
    }    
}

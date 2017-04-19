package serversocket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONException;

public class Exsecute {

    private List<User> getUserList(String inLine){
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {
        }.getType();
        List<User> usersList = gson.fromJson(inLine, type);
        return usersList;
    }

    
    public void addUser(String inLine) throws SQLException, ClassNotFoundException, JSONException {
//        getUserList(inLine);
        DB.addNewUser(getUserList(inLine));
    }

    public String deleteUser(String inLine) throws SQLException{

       return DB.deleteUser(getUserList(inLine));
    }
    
    public void updateUser(String inLine) throws SQLException {
       DB.updateUser(getUserList(inLine));
    }

    public String getAllUsers() throws SQLException, JSONException, ClassNotFoundException {
        //TODO getUser

        String allUsers = DB.getAllUsers();

        return allUsers;
    }
    
}

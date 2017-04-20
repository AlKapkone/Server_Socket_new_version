package serversocket;

import com.google.gson.Gson;
import org.json.JSONException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static Connection conn;

    public static void conToDB() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:Test.s3db");

        System.out.println("DB Successfully connected!");
    }

    private  static Statement statmt;
    
    public static void createDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Email' text);");

        System.out.println("Table created or already exists");
    }

    public static void updateUser(List<User> usersList) throws SQLException
    {
        User user = usersList.get(0);
        System.out.println(user.getEmail()+"  "+ user.getName() +" "+ user.getId());
        statmt.executeUpdate("UPDATE 'users' " +
                "SET name = ('" + user.getName() + "'), " +
                "Email = ('" + user.getEmail() + "')"+
                "WHERE id = ('" + user.getId() + "')"
        );

        System.out.println("Інформацію про користувача оновлено");
    }

    public static String deleteUser(List<User> usersList) throws SQLException
    {
        User user = usersList.get(0);
        int respId = user.getId();

        statmt.execute("DELETE FROM 'users' WHERE id = ('"+respId+"')");
        String delResult = "Користувача з ID = " + respId + "видалено";
        System.out.println("Користувача видалено");
                
        return delResult;
    }

    public static void addNewUser(List<User> usersList) throws SQLException {
        for (User user : usersList) {
            String name = user.getName();
            String email = user.getEmail();
            statmt.execute("INSERT INTO 'users' ('name', 'Email') VALUES ('"+name+"','"+email+"');");
        }
        System.out.println("Користувача додано");
    }
    
    public static ResultSet resSet;

    public static String getAllUsers() throws ClassNotFoundException, SQLException, JSONException {
        resSet = statmt.executeQuery("SELECT * FROM users");

        List<User> usersData = new ArrayList<>();
        while (resSet.next()) {
            User user = new User(
                    resSet.getInt("id"),
                    resSet.getString("name"),
                    resSet.getString("email")
            );

            usersData.add(user);
        }
            Gson gson = new Gson();
            return gson.toJson(usersData);
    }
    
    public static void closeDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();
        System.out.println("Всі з'єднання успішно закрито");
    }
}

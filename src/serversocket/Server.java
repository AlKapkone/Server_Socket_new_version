package serversocket;

import java.sql.SQLException;

public class Server {
    
    private static final int PORT_NUMBER = 55555;

    public static void main(String args[]) {

        try{
        initiateDB();
        
        StartServer startServer = new StartServer(PORT_NUMBER);
        startServer.startServ();
        
        } catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }        
    }
    
    private static void initiateDB() throws ClassNotFoundException, SQLException{
        DB.conToDB();
        DB.createDB();
    }
}
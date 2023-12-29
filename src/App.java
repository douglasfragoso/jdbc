import java.sql.Connection;

import db.DB;

public class App {
    public static void main(String[] args){
        
        Connection coon = DB.getConnection();
        DB.closeConnection();

    }
}

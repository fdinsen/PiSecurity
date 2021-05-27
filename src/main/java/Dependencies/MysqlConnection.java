package Dependencies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class MysqlConnection  {
    public static Connection connect() {
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/customers";
        String username = "root";
        String password = "root123";

        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Post establishing a DB connection - "+con);
        }
        catch (SQLException e)
        {
            Logger.getLogger("MysqlConnection").error("Mysql connection could not be set up.");
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
        return con;
    }

    public static boolean disconnect() {
        return false;
    }
}

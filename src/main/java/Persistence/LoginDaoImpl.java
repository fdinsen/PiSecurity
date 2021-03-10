package Persistence;

import Dependencies.MysqlConnection;
import Persistence.DAO.LoginDao;
import Login.LoginBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDaoImpl implements LoginDao {
    public String verifyCredentials(LoginBean loginBean)
    {
        String userName = loginBean.getUserName();
        String password = loginBean.getPassword();

        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String userNameDB = "";
        String passwordDB = "";
        String roleDB = "";

        try
        {
            con = MysqlConnection.connect();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select username,password,role from users");

            while(resultSet.next())
            {
                userNameDB = resultSet.getString("username");
                passwordDB = resultSet.getString("password");
                roleDB = resultSet.getString("role");

                if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("Admin"))
                    return "Admin_Role";
                else if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("Editor"))
                    return "Editor_Role";
                else if(userName.equals(userNameDB) && password.equals(passwordDB) && roleDB.equals("User"))
                    return "User_Role";
            }
        }
        catch( SQLException e)
        {
            e.printStackTrace();
        }
        return "Invalid user credentials";
    }
}

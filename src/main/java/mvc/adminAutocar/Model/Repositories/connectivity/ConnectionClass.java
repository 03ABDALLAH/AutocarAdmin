package mvc.adminAutocar.Model.Repositories.connectivity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ConnectionClass<connection> {
public Connection connection;
    public Connection getConnection()
    {
        String dbName ="u139820734_autocar";
        String username = "u139820734_admin";
        String password = "Autocaradmin123.";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://185.166.188.206:3306/" + dbName + "?useSSL=false",username,password);

        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
        catch(ClassNotFoundException e) {
            System.out.println("null");
        }
        return connection;
    }
}

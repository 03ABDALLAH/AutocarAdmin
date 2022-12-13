package mvc.adminAutocar.Model.Repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuichetRepository {

    // function allows to get a list of Guichets from data base
    public ObservableList<Guichet> getGuichets() {
        ObservableList<Guichet> guichet_list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select * from guichet");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                guichet_list.add(new Guichet(resultSet.getInt("IdGuichet"),
                        resultSet.getString("Addresse"),
                        resultSet.getString("Status"),
                        resultSet.getString("Responsable")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return guichet_list;
    }


    // function allows to delete guichet in data base
    public void deleteGuichet(int idGuichet){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try {
            String query = "DELETE FROM `guichet` WHERE IdGuichet  ="+idGuichet;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

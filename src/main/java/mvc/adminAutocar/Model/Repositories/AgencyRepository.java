package mvc.adminAutocar.Model.Repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import mvc.adminAutocar.Controller.AgencesController;
import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgencyRepository {

    // function allows to get a list of Agencies from data base
    public ObservableList<Agency> getAgencies() {
        ObservableList<Agency> agency_list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select * from agency");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                agency_list.add(new Agency(resultSet.getInt("IdAgency"),
                        resultSet.getString("Name"),
                        resultSet.getString("Addresse"),
                        resultSet.getString("Status"),
                        resultSet.getString("Tel")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return agency_list;
    }


    // function allows to delete agency in data base
    public void deleteAgency(int idAgency){
        try {
            String query = "DELETE FROM `agency` WHERE IdAgency  ="+idAgency;
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

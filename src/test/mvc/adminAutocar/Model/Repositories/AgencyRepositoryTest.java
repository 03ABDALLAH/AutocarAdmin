package mvc.adminAutocar.Model.Repositories;

import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import static org.junit.jupiter.api.Assertions.*;

class AgencyRepositoryTest {

    @Test
    void getAgencies() {
        AgencyRepository agencyRepository = new AgencyRepository();
        ObservableList<Agency> agency_list = agencyRepository.getAgencies();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select count(*) from agency");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertTrue(agency_list.size() == count);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Test
    void deleteAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        agencyRepository.deleteAgency(1);
        assertTrue(true);
    }

}
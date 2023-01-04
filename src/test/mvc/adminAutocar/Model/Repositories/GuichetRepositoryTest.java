package mvc.adminAutocar.Model.Repositories;

import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuichetRepositoryTest {

    @Test
    void getGuichets() {
        GuichetRepository guihcetRepository = new GuichetRepository();
        ObservableList<Guichet> guicht_list = guihcetRepository.getGuichets();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select count(*) from guichet");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertTrue(guicht_list.size() == count);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Test
    void deleteGuichet() {
        GuichetRepository guihcetRepository = new GuichetRepository();
        guihcetRepository.deleteGuichet(1);
        assertTrue(true);
    }
}
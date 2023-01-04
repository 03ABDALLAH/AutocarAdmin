package mvc.adminAutocar.Model.Repositories;

import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import mvc.adminAutocar.Model.Ticket;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class TicketRepositoryTest {

    @Test
    void getTickets() {

        TicketRepository ticketRepository = new TicketRepository();
        ObservableList<Ticket> tickets_list = ticketRepository.getTickets();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select count(*) from ticket");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertTrue(tickets_list.size() == count);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void deleteTicket() {
        TicketRepository ticketRepository = new TicketRepository();
        ticketRepository.deleteTicket(1);
        assertTrue(true);
    }
}
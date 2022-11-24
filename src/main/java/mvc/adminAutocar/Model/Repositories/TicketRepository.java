package mvc.adminAutocar.Model.Repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import mvc.adminAutocar.Model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRepository {

    // function allows to get a list of Tickets from data base
    public ObservableList<Ticket> getTickets() {
        ObservableList<Ticket> ticket_list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select * from ticket");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                ticket_list.add(new Ticket(resultSet.getInt("IdTicket"),
                        resultSet.getString("Destination"),
                        resultSet.getString("Departure"),
                        resultSet.getDate("DepartureDate").toLocalDate(),
                        resultSet.getInt("PlaceNumber"),
                        resultSet.getDouble("Prix"),
                        resultSet.getDate("ArrivalDate").toLocalDate(),
                        resultSet.getInt("IdAdmin"),
                        resultSet.getInt("IdPaiment"),
                        resultSet.getInt("IdAgency"),
                        resultSet.getBoolean("isPurchesed")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ticket_list;
    }

    // function allows to delete ticket in data base
    public void deleteTicket(int idTicket){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try {
            String query = "DELETE FROM `ticket` WHERE IdTicket  ="+idTicket;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

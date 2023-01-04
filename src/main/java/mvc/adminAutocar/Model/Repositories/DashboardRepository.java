package mvc.adminAutocar.Model.Repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import mvc.adminAutocar.Model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DashboardRepository {

    // function Returns count of actif guichets.
    public int getActifGuichets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        int count = 0;
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) FROM guichet WHERE Status = 'actif';");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    // function Returns count of En Panne guichets.
    public int getEnPanneGuichets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        int count = 0;
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) FROM guichet WHERE Status = 'enpanne';");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    //Function to get the number of tickets that have been purchased
    public int getPurchesedTickets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        int count = 0;
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) FROM ticket WHERE isPurchesed = 1;");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    //Function to get the number of tickets that have not been purchased
    public int getNotPurchesedTickets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        int count = 0;
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) FROM ticket WHERE isPurchesed = 0;");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;
    }

    //Function to get the agency name with the biggest number of purchased tickets
    public String getAgencyNameWithBiggestPurchasedTickets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        String agencyName = "";
        try {
            pst = connection.prepareStatement("SELECT agency.Name " +
                    "FROM ticket INNER JOIN agency ON ticket.IdAgency = agency.IdAgency " +
                    "WHERE ticket.isPurchesed = 1 " +
                    "GROUP BY agency.Name " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 1;");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                agencyName = resultSet.getString(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return agencyName;
    }

    //Function to get the agency name with the least number of purchased tickets
    public String getAgencyWithLeastPurchesedTickets() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        String agencyName = "";
        try {
            pst = connection.prepareStatement("SELECT agency.Name " +
                    "FROM agency " +
                    "INNER JOIN ticket " +
                    "ON ticket.IdAgency = agency.IdAgency " +
                    "WHERE ticket.isPurchesed = 1 " +
                    "GROUP BY agency.Name " +
                    "ORDER BY COUNT(*) ASC " +
                    "LIMIT 1;");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                agencyName = resultSet.getString(1);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return agencyName;
    }

    public ArrayList<Integer> getPurchesedTicketsByMonth() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        ArrayList<Integer> tickets = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) " +
                    "FROM ticket " +
                    "WHERE ticket.isPurchesed = 1 " +
                    "AND MONTH(ticket.DepartureDate) = ? ;");
            for (int i = 1; i <= 12; i++) {
                pst.setInt(1, i);
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    tickets.add(resultSet.getInt(1));
                }
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }

    public ArrayList<Integer> getNotPurchesedTicketsByMonth() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        ArrayList<Integer> tickets = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT COUNT(*) " +
                    "FROM ticket " +
                    "WHERE ticket.isPurchesed = 0 " +
                    "AND MONTH(ticket.DepartureDate) = ? ;");
            for (int i = 1; i <= 12; i++) {
                pst.setInt(1, i);
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    tickets.add(resultSet.getInt(1));
                }
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return tickets;
    }

    public ArrayList<Double> getSumOfPaymentsByMonth() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        ArrayList<Double> payments = new ArrayList<>();
        try {
            pst = connection.prepareStatement("SELECT SUM(Prix) " +
                    "FROM ticket " +
                    "INNER JOIN paiment " +
                    "ON ticket.IdPaiment = paiment.IdPaiment " +
                    "WHERE ticket.isPurchesed = 1 " +
                    "AND MONTH(paiment.PaymentDate) = ? ;");
            for (int i = 1; i <= 12; i++) {
                pst.setInt(1, i);
                ResultSet resultSet = pst.executeQuery();
                while (resultSet.next()) {
                    payments.add(resultSet.getDouble(1));
                }
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return payments;
    }

    public HashMap<String, Integer> getTopAgencies() {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        HashMap<String, Integer> agencies = new HashMap<>();
        try {
            pst = connection.prepareStatement("SELECT agency.Name, COUNT(*) " +
                    "FROM ticket " +
                    "INNER JOIN agency ON agency.IdAgency = ticket.IdAgency " +
                    "GROUP BY agency.Name " +
                    "ORDER BY COUNT(*) DESC " +
                    "LIMIT 5;");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                agencies.put(resultSet.getString(1), resultSet.getInt(2));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return agencies;
    }
}

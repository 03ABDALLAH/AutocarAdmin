package mvc.adminAutocar.Model.Repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Payment;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentRepository {


    // function allows to get a list of payments from data base
    public ObservableList<Payment> getPayments() {
        ObservableList<Payment> payment_list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select * from paiment");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                payment_list.add(new Payment(resultSet.getInt("IdPaiment"),
                        resultSet.getDate("PaymentDate").toLocalDate()));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return payment_list;
    }


    public void AddPayment(Payment payment) {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("INSERT INTO paiment (PaymentDate) VALUES (?)");
            pst.setString(1, payment.getPaymentDate().toString());
            int row = pst.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    // function allows to delete payment in data base
    public void deletePayment(int idPaiment){
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        try {
            String query = "DELETE FROM `paiment` WHERE IdPaiment  ="+idPaiment;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // function allows to get Totale de Revenue d'aujourd'hui
    public double getRevenuAujourdui() {
        double total = (double)0;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("SELECT SUM(t.Prix) as TotalRevenue " +
                    "FROM ticket t " +
                    "INNER JOIN paiment p ON t.IdPaiment = p.IdPaiment " +
                    "WHERE DATE(p.PaymentDate) = '" + LocalDate.now() + "';");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getDouble("TotalRevenue");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }

    // function allows to get Totale de Revenue de ce mois
    public double getRevenuMois() {
        double total = (double)0;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("SELECT SUM(t.Prix) as TotalRevenue " +
                    "FROM ticket t " +
                    "INNER JOIN paiment p ON t.IdPaiment = p.IdPaiment " +
                    "WHERE MONTH(p.PaymentDate) = '" + LocalDate.now().getMonthValue() + "';");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getDouble("TotalRevenue");
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return total;
    }
}

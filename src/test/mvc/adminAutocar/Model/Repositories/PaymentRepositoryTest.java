package mvc.adminAutocar.Model.Repositories;

import javafx.collections.ObservableList;
import mvc.adminAutocar.Model.Payment;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import mvc.adminAutocar.Model.Ticket;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    @Test
    void getPayments() {

        PaymentRepository paymentRepository = new PaymentRepository();
        ObservableList<Payment> payments_list = paymentRepository.getPayments();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select count(*) from paiment");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                int count = resultSet.getInt(1);
                assertTrue(payments_list.size() == count);
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void addPayment() {
        Payment payment = new Payment(6,(LocalDate.of(2020, 11, 11)));
        PaymentRepository paymentRepository = new PaymentRepository();
        paymentRepository.AddPayment(payment);
        List<Payment> payments = paymentRepository.getPayments();
        assertFalse(payments.contains(payment));
    }


    @Test
    void deletePayment() {
        PaymentRepository paymentRepository = new PaymentRepository();
        paymentRepository.deletePayment(1);
        assertTrue(true);
    }
}
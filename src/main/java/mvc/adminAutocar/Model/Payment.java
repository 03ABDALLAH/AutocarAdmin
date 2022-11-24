package mvc.adminAutocar.Model;

import java.time.LocalDate;
import java.util.List;

public class Payment {
    private int idPaiment;
    private LocalDate PaymentDate;
    private List<Ticket> tickets;

    public Payment(int idPaiment, LocalDate PaymentDate) {
        this.PaymentDate = PaymentDate;
        this.idPaiment = idPaiment;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getIdPaiment() {
        return idPaiment;
    }

    public void setIdPaiment(int idPaiment) {
        this.idPaiment = idPaiment;
    }

    public LocalDate getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.PaymentDate = paymentDate;
    }
}

package mvc.adminAutocar.Model;

import java.util.List;

public class Guichet {
    private int idGuichet;
    private String addresse;
    private String status;

    public String getResponsable() {
        return responsable;
    }

    public Guichet(int idGuichet, String addresse, String status, String responsable) {
        this.idGuichet = idGuichet;
        this.addresse = addresse;
        this.status = status;
        this.responsable = responsable;
        this.tickets = tickets;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    private String responsable;
    private List<Ticket> tickets;

    public Guichet(int idGuichet, String addresse, String status) {
        this.idGuichet = idGuichet;
        this.addresse = addresse;
        this.status = status;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getIdGuichet() {
        return idGuichet;
    }

    public void setIdGuichet(int idGuichet) {
        this.idGuichet = idGuichet;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

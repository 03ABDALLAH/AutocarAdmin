package mvc.adminAutocar.Model;

import java.util.List;

public class Agency {

    private int idAgency;
    private String name;
    private String addresse;
    private String status;
    private String tel;
    private List<Ticket> tickets;

    public Agency(int idAgency, String name, String addresse, String status, String tel) {
        this.idAgency = idAgency;
        this.name = name;
        this.addresse = addresse;
        this.status = status;
        this.tel = tel;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getIdAgency() {
        return idAgency;
    }

    public void setIdAgency(int idAgency) {
        this.idAgency = idAgency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}

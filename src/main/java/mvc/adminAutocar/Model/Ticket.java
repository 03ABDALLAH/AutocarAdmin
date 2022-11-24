package mvc.adminAutocar.Model;


import java.time.LocalDate;

public class Ticket {
    private int idTicket;
    private String destination;
    private String departure;
    private LocalDate departureDate;
    private int placeNumber;
    private double prix;
    private LocalDate ArrivalDate;
    private boolean isPurchesed;
    private int idAdmin;
    private int idPaiment;
    private int idAgency;

    public Ticket(int idTicket, String destination, String departure, LocalDate DepartureDate, int placeNumber, double prix, LocalDate ArrivalDate, int idAdmin, int idPaiment, int idAgency, boolean isPurchesed) {
        this.idTicket = idTicket;
        this.destination = destination;
        this.departure = departure;
        this.departureDate = DepartureDate;
        this.placeNumber = placeNumber;
        this.prix = prix;
        this.ArrivalDate = ArrivalDate;
        this.isPurchesed = isPurchesed;
        this.idAdmin = idAdmin;
        this.idPaiment = idPaiment;
        this.idAgency = idAgency;
    }

    public boolean getIsPurchesed() {
        return isPurchesed;
    }

    public void setIsPurchesed(boolean purchesed) {
        isPurchesed = purchesed;
    }

    public int getIdAgency() {
        return idAgency;
    }

    public void setIdAgency(int idAgency) {
        this.idAgency = idAgency;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(int placeNumber) {
        this.placeNumber = placeNumber;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getAgence() {
        return idAgency;
    }

    public void setAgence(int idAgency) {
        this.idAgency = idAgency;
    }

    public LocalDate getArrivalDate() {
        return ArrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.ArrivalDate = arrivalDate;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdPaiment() {
        return idPaiment;
    }

    public void setIdPaiment(int idPaiment) {
        this.idPaiment = idPaiment;
    }
}

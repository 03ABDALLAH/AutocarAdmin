package mvc.adminAutocar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;
import mvc.adminAutocar.Model.Ticket;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTicketController implements Initializable {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private ComboBox<String> fieldAgence;

    @FXML
    private DatePicker fieldArrivalDate;

    @FXML
    private DatePicker fieldDeparDate;

    @FXML
    private TextField fieldDepart;

    @FXML
    private TextField fieldDestnation;

    @FXML
    private TextField fieldNbPlace;

    @FXML
    private TextField fieldPrice;


    String query = null;
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection;
    PreparedStatement preparedStatement;
    private boolean update;
    int ticketId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldAgence.setItems(getAgencies());
    }

    @FXML
    private void save() {

        connection = connectionClass.getConnection();
        String depart = fieldDepart.getText();
        String destination = fieldDestnation.getText();
        LocalDate ArrivalDate = fieldArrivalDate.getValue();
        LocalDate DepartDate = fieldDeparDate.getValue();
        String nbplace = fieldNbPlace.getText();
        String price = fieldPrice.getText();
        String agence = fieldAgence.getSelectionModel().getSelectedItem();

        if (depart.isEmpty() || destination.isEmpty()|| price.isEmpty()|| nbplace.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();
        }
    }

    @FXML
    private void clean() {
        fieldDepart.setText(null);
        fieldDestnation.setText(null);
        fieldNbPlace.setText(null);
        fieldPrice.setText(null);
        fieldDeparDate.setValue(null);
        fieldArrivalDate.setValue(null);
        fieldAgence.setValue(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `ticket`( `Destination`, `Departure`, `DepartureDate`, `ArrivalDate`, `PlaceNumber`, `Prix`, `idAdmin`, `IdAgency`) VALUES (?,?,?,?,?,?,1,47)";

        }else{
            query = "UPDATE `ticket` SET "
                    + "`Destination`=?,"
                    + "`Departure`=?,"
                    + "`DepartureDate`=?,"
                    + "`ArrivalDate`=?,"
                    + "`PlaceNumber`=?,"
                    + "`Prix`=?,"
                    + "`idAdmin`=1,"
                    + "`IdAgency`=47 WHERE IdTicket = '"+ticketId+"'";
        }
    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fieldDestnation.getText());
            preparedStatement.setString(2, fieldDepart.getText());
            preparedStatement.setString(3, fieldDeparDate.getValue().toString());
            preparedStatement.setString(4, fieldArrivalDate.getValue().toString());
            preparedStatement.setInt(5, Integer.parseInt(fieldNbPlace.getText()));
            preparedStatement.setInt(6, Double.valueOf(fieldPrice.getText()).intValue());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddTicketController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Ticket ticket) {
        ticketId = ticket.getIdTicket();
        fieldDepart.setText(ticket.getDeparture());
        fieldDestnation.setText(ticket.getDestination());
        fieldArrivalDate.setValue(ticket.getArrivalDate());
        fieldDeparDate.setValue(ticket.getDepartureDate());
        fieldPrice.setText(String.valueOf(ticket.getPrix()));
        fieldNbPlace.setText(String.valueOf(ticket.getPlaceNumber()));
    }

    void setUpdate(boolean b) {
        this.update = b;
        btnAjouter.setText("Modifier");
    }


    public ObservableList<String> getAgencies() {
        ObservableList<String> guichet_list = FXCollections.observableArrayList();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        PreparedStatement pst;
        try {
            pst = connection.prepareStatement("select Name from agency");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                guichet_list.add(resultSet.getString("Name"));
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return guichet_list;
    }

    }

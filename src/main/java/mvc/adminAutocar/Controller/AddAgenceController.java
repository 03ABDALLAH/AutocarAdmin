package mvc.adminAutocar.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddAgenceController implements Initializable{

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField fieldAdress;

    @FXML
    private ComboBox<String> fieldEtat;

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldTele;


    String query = null;
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection;
    PreparedStatement preparedStatement;
    private boolean update;
    int agencyId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldEtat.getItems().addAll("payed", "else");
    }

    @FXML
    private void save() {

        connection = connectionClass.getConnection();
        String name = fieldName.getText();
        String adress = fieldAdress.getText();
        String tel = fieldTele.getText();
        String etat = fieldEtat.getSelectionModel().getSelectedItem();

        if (name.isEmpty() || adress.isEmpty() || etat.isEmpty() || tel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();
        }

    }

    @FXML
    private void clean() {
        fieldTele.setText(null);
        fieldName.setText(null);
        fieldAdress.setText(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `agency`( `Name`, `Addresse`, `Status`, `Tel`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `agency` SET "
                    + "`Name`=?,"
                    + "`Addresse`=?,"
                    + "`Status`=?,"
                    + "`Tel`= ? WHERE IdAgency = '"+agencyId+"'";
        }

    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fieldName.getText());
            preparedStatement.setString(2, fieldAdress.getText());
            preparedStatement.setString(3, fieldEtat.getSelectionModel().getSelectedItem());
            preparedStatement.setString(4, fieldTele.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddAgenceController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Agency agency) {
        agencyId = agency.getIdAgency();
        fieldTele.setText(agency.getTel());
        fieldName.setText(agency.getName());
        fieldAdress.setText(agency.getAddresse());
        fieldEtat.setValue(agency.getStatus());
    }

    void setUpdate(boolean b) {
        this.update = b;
        btnAjouter.setText("Modifier");
    }


}

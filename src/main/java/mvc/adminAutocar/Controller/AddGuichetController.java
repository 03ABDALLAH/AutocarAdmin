package mvc.adminAutocar.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.connectivity.ConnectionClass;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddGuichetController implements Initializable {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private TextField fieldAdress;

    @FXML
    private TextField fieldRespo;

    @FXML
    private ComboBox<String> fieldStatus;



    String query = null;
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection;
    PreparedStatement preparedStatement;
    private boolean update;
    int guichetId;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldStatus.getItems().addAll("open", "close");
    }

    @FXML
    private void save() {

        connection = connectionClass.getConnection();
        String adress = fieldAdress.getText();
        String status = fieldStatus.getSelectionModel().getSelectedItem();
        String respo = fieldRespo.getText();


        if (adress.isEmpty() || respo.isEmpty() || status.isEmpty()) {
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
        fieldRespo.setText(null);
        fieldAdress.setText(null);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `guichet`( `Addresse`, `Status`, `Responsable`) VALUES (?,?,?)";

        }else{
            query = "UPDATE `guichet` SET "
                    + "`Addresse`=?,"
                    + "`Status`=?,"
                    + "`Responsable`=? WHERE IdGuichet = '"+guichetId+"'";
        }

    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fieldAdress.getText());
            preparedStatement.setString(2, fieldStatus.getSelectionModel().getSelectedItem());
            preparedStatement.setString(3, fieldRespo.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddGuichetController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(Guichet guichet) {
        guichetId = guichet.getIdGuichet();
        fieldRespo.setText(guichet.getResponsable());
        fieldStatus.setValue(guichet.getStatus());
        fieldAdress.setText(guichet.getAddresse());

    }

    void setUpdate(boolean b) {
        this.update = b;
        btnAjouter.setText("Modifier");
    }
}

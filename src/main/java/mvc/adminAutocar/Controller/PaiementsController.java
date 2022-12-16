package mvc.adminAutocar.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Payment;
import mvc.adminAutocar.Model.Repositories.GuichetRepository;
import mvc.adminAutocar.Model.Repositories.PaymentRepository;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaiementsController implements Initializable {


    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnExporter;

    @FXML
    private TableColumn<Payment, String> colAction;

    @FXML
    private TableColumn<Payment, String> colAmount;

    @FXML
    private TableColumn<Payment, String> colDate;

    @FXML
    private TableColumn<Payment, String> colId;

    @FXML
    private TableColumn<Payment, String> colQuantity;

    @FXML
    private TableColumn<Payment, String> colTypeOfPayment;

    @FXML
    private TableView<Payment> paymentTable;

    PaymentRepository paymentRepository = new PaymentRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    // function allows to fetch the data from data base and show it in the table
    private void loadData(){
        paymentTable.setItems(paymentRepository.getPayments());
        colId.setCellValueFactory(new PropertyValueFactory<>("IdPaiment"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("PaymentDate"));


        // set the two button to these column
        colAction.setCellFactory(cellFoctory);

        paymentTable.setItems(paymentRepository.getPayments());
    }

    // function allows to add two button foreach row in the data table: one for the edit action and the other for delete action
    Callback<TableColumn<Payment, String>, TableCell<Payment, String>> cellFoctory = (TableColumn<Payment, String> param) -> {
        // make cell containing buttons
        final TableCell<Payment, String> cell = new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                //that cell created only on non-empty rows
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    Button deleteIcon = new Button();
                    Button editIcon = new Button();

                    deleteIcon.setStyle("-fx-background-radius: 5em; " +
                            "-fx-min-width: 30px; " +
                            "-fx-min-height: 30px; " +
                            "-fx-max-width: 30px; " +
                            "-fx-max-height: 30px;");
                    editIcon.setStyle("-fx-background-radius: 5em; " +
                            "-fx-min-width: 30px; " +
                            "-fx-min-height: 30px; " +
                            "-fx-max-width: 30px; " +
                            "-fx-max-height: 30px;");
                    Image editIconImg = new Image("C:/Users/hakee/IdeaProjects/AutocarAdmin/src/main/resources/assets/Images/icons8-edit-file-48.png", 25, 25,true , true);
                    Image deleteIconImg = new Image("C:/Users/hakee/IdeaProjects/AutocarAdmin/src/main/resources/assets/Images/icons8-remove-48.png", 25, 25 ,true , true);

                    ImageView viewEdit = new ImageView(editIconImg);
                    ImageView viewDelete = new ImageView(deleteIconImg);

                    deleteIcon.setGraphic(viewDelete);
                    editIcon.setGraphic(viewEdit);

                    deleteIcon.setOnMouseClicked((event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you shure you want to delete?");
                        alert.getDialogPane().setHeaderText(null);
                        Optional<ButtonType> action = alert.showAndWait();
                        if(action.get() == ButtonType.OK) {
                            var payment = this.getTableRow();
                            if (payment != null) {
                                paymentRepository.deletePayment(payment.getItem().getIdPaiment());
                                paymentTable.setItems(paymentRepository.getPayments());
                            }
                        }
                    });

                    editIcon.setOnMouseClicked((event) -> {
                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                    setGraphic(managebtn);
                    setText(null);
                }
            }

        };
        return cell;
    };
}

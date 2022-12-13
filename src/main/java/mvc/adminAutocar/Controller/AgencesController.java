package mvc.adminAutocar.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Repositories.AgencyRepository;

public class AgencesController implements Initializable {

    @FXML
    private TableView<Agency> agencyTable;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnExporter;

    @FXML
    private TableColumn<Agency, String> colAction;

    @FXML
    private TableColumn<Agency, String> colAddress;

    @FXML
    private TableColumn<Agency, String> colId;

    @FXML
    private TableColumn<Agency, String> colNom;

    @FXML
    private TableColumn<Agency, String> colTel;

    @FXML
    private TableColumn<Agency, String> colTicketsDisponible;

    @FXML
    private TableColumn<Agency, String> colTicketsVendu;

    AgencyRepository agencyRepository = new AgencyRepository();
    AddAgenceController addAgenceController = new AddAgenceController();


    @FXML
    void handleAddAgency() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddAgence.fxml"));
        Parent root1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setOnCloseRequest(v ->{
            agencyTable.setItems(agencyRepository.getAgencies());
        });
        addAgenceController = fxmlLoader.getController();
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }



    // function allows to fetch the data from data base and show it in the table
    private void loadData(){
        agencyTable.setItems(agencyRepository.getAgencies());
        colId.setCellValueFactory(new PropertyValueFactory<>("IdAgency"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Addresse"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));


        // set the two button to these column
        colAction.setCellFactory(cellFoctory);

        agencyTable.setItems(agencyRepository.getAgencies());
    }

    // function allows to add two button foreach row in the data table: one for the edit action and the other for delete action
    Callback<TableColumn<Agency, String>, TableCell<Agency, String>> cellFoctory = (TableColumn<Agency, String> param) -> {
        // make cell containing buttons
        final TableCell<Agency, String> cell = new TableCell<>() {
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

                    Image editIconImg = new Image("D:/AutocarAdmin/src/main/resources/assets/Images/icons8-edit-file-48.png", 25, 25,true , true);
                    Image deleteIconImg = new Image("D:/AutocarAdmin/src/main/resources/assets/Images/icons8-remove-48.png", 25, 25 ,true , true);

                    ImageView viewEdit = new ImageView(editIconImg);
                    ImageView viewDelete = new ImageView(deleteIconImg);

                    deleteIcon.setGraphic(viewDelete);
                    editIcon.setGraphic(viewEdit);

                    deleteIcon.setOnMouseClicked((event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you shure you want to delete?");
                        alert.getDialogPane().setHeaderText(null);
                        Optional<ButtonType> action = alert.showAndWait();
                        if(action.get() == ButtonType.OK){
                            var agency= this.getTableRow();
                            if (agency!=null){
                                agencyRepository.deleteAgency(agency.getItem().getIdAgency());
                                agencyTable.setItems(agencyRepository.getAgencies());
                            }
                        }
                    });

                    editIcon.setOnMouseClicked((event) -> {
                        var agency= this.getTableRow().getItem();
                        try {
                            handleAddAgency();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        addAgenceController.setUpdate(true);
                        addAgenceController.setTextField(agency);
                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");

                    setGraphic(managebtn);
                    setText(null);
                }
            }

        };
        return cell;
    };
}

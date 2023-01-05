package mvc.adminAutocar.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import javafx.stage.FileChooser;
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
   TableColumn<Agency, String> colAddress;

    @FXML
   TableColumn<Agency, String> colId;

    @FXML
   TableColumn<Agency, String> colNom;

    @FXML
    private TableColumn<Agency, String> colTel;

    @FXML
    private TableColumn<Agency, String> colTicketsDisponible;

    @FXML
    private TableColumn<Agency, String> colTicketsVendu;

    AgencyRepository agencyRepository = new AgencyRepository();
    AddAgenceController addAgenceController = new AddAgenceController();

    @FXML
    void exporterAgennce() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Agencies");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File outputFile = fileChooser.showSaveDialog(btnExporter.getScene().getWindow());
        if (outputFile != null) {
            try {
                FileWriter fw = new FileWriter(outputFile);
                BufferedWriter bw = new BufferedWriter(fw);
                ObservableList<Agency> list = agencyTable.getItems();
                ListIterator<Agency> iter = list.listIterator();
                while (iter.hasNext()) {
                    Agency agency = iter.next();
                    bw.write(String.format("%s,%s,%s,%s,%s\n", agency.getIdAgency(), agency.getName(), agency.getAddresse(), agency.getTel(), agency.getStatus()));
                }
                bw.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Succès");
                alert.setContentText("Les données ont été exportées avec succès!");
                alert.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(AgencesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
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
   void loadData(){
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
                    Image editIconImg = new Image("C:\\Users\\Yassine\\eclipse-workspace\\AutocarAdmin\\src\\main\\resources\\assets\\Images\\icons8-edit-file-48.png", 25, 25,true , true);
                    Image deleteIconImg = new Image("C:\\Users\\Yassine\\eclipse-workspace\\AutocarAdmin\\src\\main\\resources\\assets\\Images\\icons8-remove-48.png", 25, 25 ,true , true);

                    ImageView viewEdit = new ImageView(editIconImg);
                    ImageView viewDelete = new ImageView(deleteIconImg);

                    deleteIcon.setGraphic(viewDelete);
                    editIcon.setGraphic(viewEdit);

                    deleteIcon.setOnMouseClicked((event) -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr de vouloir supprimer?");
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

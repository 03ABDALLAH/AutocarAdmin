package mvc.adminAutocar.Controller;

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
import javafx.util.Callback;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.GuichetRepository;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GuichetsController implements Initializable {


    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnExporter;

    @FXML
    private TableColumn<Guichet, String> colAction;

    @FXML
    private TableColumn<Guichet, String> colAddress;

    @FXML
    private TableColumn<Guichet, String> colId;

    @FXML
    private TableColumn<Guichet, String> colNbTickSold;

    @FXML
    private TableColumn<Guichet, String> colResponsable;

    @FXML
    private TableColumn<Guichet, String> colRevenue;

    @FXML
    private TableColumn<Guichet, String> colStatus;

    @FXML
    private TableView<Guichet> guichetTable;

    GuichetRepository guichetRepository = new GuichetRepository();
    AddGuichetController addGuichetController = new AddGuichetController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }


    @FXML
    void handleAddGuichet() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddGuichet.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.setOnCloseRequest(v ->{
            guichetTable.setItems(guichetRepository.getGuichets());
        });
        addGuichetController = fxmlLoader.getController();
        stage.show();
    }


    // function allows to fetch the data from data base and show it in the table
    private void loadData(){
        guichetTable.setItems(guichetRepository.getGuichets());
        colId.setCellValueFactory(new PropertyValueFactory<>("IdGuichet"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Addresse"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colResponsable.setCellValueFactory(new PropertyValueFactory<>("Responsable"));


        // set the two button to these column
        colAction.setCellFactory(cellFoctory);

        guichetTable.setItems(guichetRepository.getGuichets());
    }

    // function allows to add two button foreach row in the data table: one for the edit action and the other for delete action
    Callback<TableColumn<Guichet, String>, TableCell<Guichet, String>> cellFoctory = (TableColumn<Guichet, String> param) -> {
        // make cell containing buttons
        final TableCell<Guichet, String> cell = new TableCell<>() {
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
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr de vouloir supprimer?");
                        alert.getDialogPane().setHeaderText(null);
                        Optional<ButtonType> action = alert.showAndWait();
                        if(action.get() == ButtonType.OK) {
                            var guichet = this.getTableRow();
                            if (guichet != null) {
                                guichetRepository.deleteGuichet(guichet.getItem().getIdGuichet());
                                guichetTable.setItems(guichetRepository.getGuichets());
                            }
                        }
                    });

                    editIcon.setOnMouseClicked((event) -> {
                        var guichet= this.getTableRow().getItem();
                        try {
                            handleAddGuichet();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        addGuichetController.setUpdate(true);
                        addGuichetController.setTextField(guichet);
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

package mvc.adminAutocar.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import mvc.adminAutocar.Model.Agency;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.GuichetRepository;

import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    // function allows to fetch the data from data base and show it in the table
    private void loadData(){
        guichetTable.setItems(guichetRepository.getGuichets());
        colId.setCellValueFactory(new PropertyValueFactory<>("IdGuichet"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Addresse"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));


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

                    deleteIcon.setOnMouseClicked((event) -> {
                        var guichet= this.getTableRow();
                        if (guichet!=null){
                            guichetRepository.deleteGuichet(guichet.getItem().getIdGuichet());
                            guichetTable.setItems(guichetRepository.getGuichets());
                        }
                    });

                    editIcon.setOnMouseClicked((event) -> {
                    });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    managebtn.setStyle("-fx-alignment:center");
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

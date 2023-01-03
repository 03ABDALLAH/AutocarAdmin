package mvc.adminAutocar.Controller;

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
import javafx.util.Callback;
import mvc.adminAutocar.Model.Guichet;
import mvc.adminAutocar.Model.Repositories.TicketRepository;
import mvc.adminAutocar.Model.Ticket;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketsController implements Initializable {

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnExporter;

    @FXML
    private TableColumn<Ticket, String> colAction;

    @FXML
    private TableColumn<Ticket, String> colArrivalDate;

    @FXML
    private TableColumn<Ticket, String> colDepar;

    @FXML
    private TableColumn<Ticket, String> colDeparDate;

    @FXML
    private TableColumn<Ticket, String> colDestination;

    @FXML
    private TableColumn<Ticket, String> colId;

    @FXML
    private TableColumn<Ticket, String> colIsPurshised;

    @FXML
    private TableColumn<Ticket, String> colNbPlace;

    @FXML
    private TableColumn<Ticket, String> colPrice;

    @FXML
    private TableView<Ticket> ticketTable;

    TicketRepository ticketRepository = new TicketRepository();
    AddTicketController addTicketController = new AddTicketController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    @FXML
    public void exporterTickets() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Agencies");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File outputFile = fileChooser.showSaveDialog(btnExporter.getScene().getWindow());
        if (outputFile != null) {
            try {
                FileWriter fw = new FileWriter(outputFile);
                BufferedWriter bw = new BufferedWriter(fw);
                ObservableList<Ticket> list = ticketTable.getItems();
                ListIterator<Ticket> iter = list.listIterator();
                while (iter.hasNext()) {
                    Ticket ticket = iter.next();
                    bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s\n", ticket.getIdTicket(),ticket.getAgence(),ticket.getArrivalDate(),ticket.getDepartureDate(),ticket.getDestination(),ticket.getPrix(),ticket.getPlaceNumber(),ticket.getIsPurchesed()));
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
    void handleAddTickets() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddTickets.fxml"));
        Parent root2 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.setOnCloseRequest(v ->{
            ticketTable.setItems(ticketRepository.getTickets());
        });
        addTicketController = fxmlLoader.getController();
        stage.show();
    }


    // function allows to fetch the data from data base and show it in the table
    private void loadData(){
        ticketTable.setItems(ticketRepository.getTickets());
        colId.setCellValueFactory(new PropertyValueFactory<>("IdTicket"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        colDepar.setCellValueFactory(new PropertyValueFactory<>("Departure"));
        colDeparDate.setCellValueFactory(new PropertyValueFactory<>("DepartureDate"));
        colNbPlace.setCellValueFactory(new PropertyValueFactory<>("PlaceNumber"));
        colArrivalDate.setCellValueFactory(new PropertyValueFactory<>("ArrivalDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Prix"));
        colIsPurshised.setCellValueFactory(new PropertyValueFactory<>("isPurchesed"));

        // set the two button to these column
        colAction.setCellFactory(cellFoctory);

        ticketTable.setItems(ticketRepository.getTickets());
    }

    // function allows to add two button foreach row in the data table: one for the edit action and the other for delete action
    Callback<TableColumn<Ticket, String>, TableCell<Ticket, String>> cellFoctory = (TableColumn<Ticket, String> param) -> {
        // make cell containing buttons
        final TableCell<Ticket, String> cell = new TableCell<>() {
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
                            var ticket = this.getTableRow();
                            if (ticket != null) {
                                ticketRepository.deleteTicket(ticket.getItem().getIdTicket());
                                ticketTable.setItems(ticketRepository.getTickets());
                            }
                        }
                    });

                    editIcon.setOnMouseClicked((event) -> {
                        var ticket= this.getTableRow().getItem();
                        try {
                            handleAddTickets();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        addTicketController.setUpdate(true);
                        addTicketController.setTextField(ticket);
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

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
import mvc.adminAutocar.Model.Repositories.TicketRepository;
import mvc.adminAutocar.Model.Ticket;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
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

                    deleteIcon.setOnMouseClicked((event) -> {
                        var ticket= this.getTableRow();
                        if (ticket!=null){
                            ticketRepository.deleteTicket(ticket.getItem().getIdTicket());
                            ticketTable.setItems(ticketRepository.getTickets());
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

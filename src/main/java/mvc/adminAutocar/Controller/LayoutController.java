package mvc.adminAutocar.Controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import mvc.adminAutocar.AdminSafariBusApplication;
import mvc.adminAutocar.Model.Repositories.*;
import mvc.adminAutocar.AdminSafariBusApplication;

import mvc.adminAutocar.Controller.*;
import mvc.adminAutocar.Model.Repositories.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayoutController implements Initializable{
    @FXML
    private Button btnLogOut;

    @FXML
    private BorderPane mainBorderPan;

    @FXML
    private Button btnDashboard;




    static AgencyRepository agencesRepository =new AgencyRepository();
    static GuichetRepository guichetsRepository =new GuichetRepository();
    static TicketRepository ticketsRepository =new TicketRepository();
    static DashboardRepository dashboardRepository =new DashboardRepository();
    static PaymentRepository paiementsRepository =new PaymentRepository();


    @FXML
    public void SwitchDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/DashboardView.fxml"));
        Pane view=fxmlLoader.load();
        DashboardController dashboardController =fxmlLoader.getController();
        mainBorderPan.setCenter(view);
        btnDashboard.setStyle("-fx-text-fill : black ");
    }

    @FXML
    public void SwitchPaiements() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/PaiementsView.fxml"));
        Pane view=fxmlLoader.load();
        PaiementsController paiementsController =fxmlLoader.getController();
        mainBorderPan.setCenter(view);
        btnDashboard.setStyle("-fx-text-fill : #8a8b9f");
    }

    @FXML
    public void SwitchTickets() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/TicketsView.fxml"));
        Pane view=fxmlLoader.load();
        TicketsController ticketsController =fxmlLoader.getController();
        mainBorderPan.setCenter(view);
        btnDashboard.setStyle("-fx-text-fill : #8a8b9f");
    }

    @FXML
    public void SwitchAgences() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/AgencesView.fxml"));
        Pane view=fxmlLoader.load();
        AgencesController agencesController=fxmlLoader.getController();
        mainBorderPan.setCenter(view);
        btnDashboard.setStyle("-fx-text-fill : #8a8b9f");

    }

    @FXML
    public void SwitchGuichets() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/GuichetsView.fxml"));
        Pane view=fxmlLoader.load();
        GuichetsController guichetsController =fxmlLoader.getController();
        mainBorderPan.setCenter(view);
        btnDashboard.setStyle("-fx-text-fill : #8a8b9f");
    }

    @FXML
    private void quitterMouseClicked(MouseEvent event) {
        if(event.getSource() == btnLogOut)
            System.exit(0);
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SwitchDashboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
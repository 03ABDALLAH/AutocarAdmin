package mvc.adminAutocar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import mvc.adminAutocar.Model.Repositories.DashboardRepository;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Text EnPanneGuichet;

    @FXML
    private Text ActifGuichet;

    @FXML
    private Text TicketReste;

    @FXML
    private Text TicketsVendu;

    @FXML
    private Text AgencePire;

    @FXML
    private Text AgenceMeilleur;

    @FXML
    private BarChart<?, ?> TicketsChart;

    @FXML
    private BarChart<?, ?> PaymentChart;

    @FXML
    private PieChart AgenciesChart;


    DashboardRepository dashboardRepository = new DashboardRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EnPanneGuichet.setText(dashboardRepository.getEnPanneGuichets() + "");
        ActifGuichet.setText(dashboardRepository.getActifGuichets() + "");
        TicketReste.setText(dashboardRepository.getNotPurchesedTickets() + "");
        TicketsVendu.setText(dashboardRepository.getPurchesedTickets() + "");
        AgencePire.setText(dashboardRepository.getAgencyWithLeastPurchesedTickets() + "");
        AgenceMeilleur.setText(dashboardRepository.getAgencyNameWithBiggestPurchasedTickets() + "");


        ArrayList<Integer> ticketsByMonth= new ArrayList<>();
        // Purchesed Serie
        ticketsByMonth = dashboardRepository.getPurchesedTicketsByMonth();
        XYChart.Series purchesedSerie = new XYChart.Series();
        purchesedSerie.setName("Purchesed");

        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Jan", ticketsByMonth.get(0)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Feb", ticketsByMonth.get(1)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Mar", ticketsByMonth.get(2)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Apr", ticketsByMonth.get(3)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("May", ticketsByMonth.get(4)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Jun", ticketsByMonth.get(5)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Jul", ticketsByMonth.get(6)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Aug", ticketsByMonth.get(7)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Sep", ticketsByMonth.get(8)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Oct", ticketsByMonth.get(9)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Nov", ticketsByMonth.get(10)));
        purchesedSerie.getData().add(new XYChart.Data<String,Number>("Dec", ticketsByMonth.get(10)));

        // Not Purchesed Serie
        ticketsByMonth = dashboardRepository.getNotPurchesedTicketsByMonth();
        XYChart.Series notPurchesedSerie = new XYChart.Series();
        notPurchesedSerie.setName("Not Purchesed");

        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Jan", ticketsByMonth.get(0)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Feb", ticketsByMonth.get(1)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Mar", ticketsByMonth.get(2)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Apr", ticketsByMonth.get(3)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("May", ticketsByMonth.get(4)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Jun", ticketsByMonth.get(5)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Jul", ticketsByMonth.get(6)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Aug", ticketsByMonth.get(7)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Sep", ticketsByMonth.get(8)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Oct", ticketsByMonth.get(9)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Nov", ticketsByMonth.get(10)));
        notPurchesedSerie.getData().add(new XYChart.Data<String,Number>("Dec", ticketsByMonth.get(10)));

        // Add the data to the chart
        TicketsChart.getData().add(purchesedSerie);
        TicketsChart.getData().add(notPurchesedSerie);

        // Payments Serie
        ArrayList<Double> paymentsByMonth = dashboardRepository.getSumOfPaymentsByMonth();
        XYChart.Series paymentsSerie = new XYChart.Series();
        paymentsSerie.setName("Amount");
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Jan", paymentsByMonth.get(0)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Feb", paymentsByMonth.get(1)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Mar", paymentsByMonth.get(2)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Apr", paymentsByMonth.get(3)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("May", paymentsByMonth.get(4)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Jun", paymentsByMonth.get(5)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Jul", paymentsByMonth.get(6)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Aug", paymentsByMonth.get(7)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Sep", paymentsByMonth.get(8)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Oct", paymentsByMonth.get(9)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Nov", paymentsByMonth.get(10)));
        paymentsSerie.getData().add(new XYChart.Data<String,Number>("Dec", paymentsByMonth.get(10)));

        PaymentChart.getData().add(paymentsSerie);

        HashMap<String, Integer> agenciesData= dashboardRepository.getTopAgencies();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
        );

        for (Map.Entry<String, Integer> agency: agenciesData.entrySet()
             ) {
            pieChartData.add(new PieChart.Data(agency.getKey(), agency.getValue()));
        }

        AgenciesChart.setData(pieChartData);
    }
}








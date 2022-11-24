module com.example.admin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mvc.adminAutocar to javafx.fxml;
    exports mvc.adminAutocar;
    exports mvc.adminAutocar.Controller;
    opens mvc.adminAutocar.Controller to javafx.fxml;
    opens mvc.adminAutocar.Model;
}
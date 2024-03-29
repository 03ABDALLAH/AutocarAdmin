package mvc.adminAutocar;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class AdminSafariBusApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminSafariBusApplication.class.getResource("/View/layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
      scene.getStylesheets().add(getClass().getResource("/assets/style.css").toExternalForm());

        stage.setTitle("SafariBus Admin");
        stage.setScene(scene);
      stage.getIcons().add(new Image("/assets/images/Logo.png"));
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
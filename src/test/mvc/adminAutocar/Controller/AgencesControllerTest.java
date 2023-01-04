package mvc.adminAutocar.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import mvc.adminAutocar.Model.Agency;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static javafx.beans.binding.Bindings.when;
import static org.junit.jupiter.api.Assertions.*;

class AgencesControllerTest {

    @Test
    void exporterAgennce() throws IOException {
        Agency agency = new Agency(1, "test", "test", "123456", "test");
        File file = new File("test.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(String.format("%s,%s,%s,%s,%s\n", agency.getIdAgency(), agency.getName(), agency.getAddresse(), agency.getTel(), agency.getStatus()));
        bw.close();
        assertTrue(file.exists());
    }


}
package sample;

import helpers.FileHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Employee;
import model.Technician;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Employee roadsideAssistanceEmployee = new Technician("Giorgos K", "roadsideAssistance");
        Employee truckEmployee = new Technician("Giannis L", "truck");
        Employee truckEmployee2 = new Technician("Kostas R", "truck");
        Employee motorEmployee = new Technician("Dimitris P", "motor");
        Employee motorEmployee2 = new Technician("Fotis K", "motor");

        FileHelper.saveFile("roadsideAssistanceEmployee", roadsideAssistanceEmployee.toString());
        FileHelper.saveFile("truckEmployee", truckEmployee.toString());
        FileHelper.saveFile("truckEmployee2", truckEmployee2.toString());
        FileHelper.saveFile("motorEmployee", motorEmployee.toString());
        FileHelper.saveFile("motorEmployee2", motorEmployee2.toString());

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Kentauros");
        primaryStage.setScene(new Scene(root, 520, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

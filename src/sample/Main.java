package sample;

import helpers.FileHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {

    public static RoadsideAssistance roadsideAssistance = new RoadsideAssistance();
    public static Core core = new Core();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Employee roadsideAssistanceEmployee = new Technician("Giorgos K", "roadsideAssistance");
        Technician truckEmployee = new Technician("Giannis L", "truck");
        Technician truckEmployee2 = new Technician("Kostas R", "truck");
        Technician motorEmployee = new Technician("Dimitris P", "motor");
        Technician motorEmployee2 = new Technician("Fotis K", "motor");

        FileHelper.saveFile("roadsideAssistanceEmployee", roadsideAssistanceEmployee.toString());
        FileHelper.saveFile("truckEmployee", truckEmployee.toString());
        FileHelper.saveFile("truckEmployee2", truckEmployee2.toString());
        FileHelper.saveFile("motorEmployee", motorEmployee.toString());
        FileHelper.saveFile("motorEmployee2", motorEmployee2.toString());

        Truck truck = new Truck();
        Truck truck2 = new Truck();
        Motorcycle motorcycle = new Motorcycle();
        Motorcycle motorcycle2 = new Motorcycle();

        Station station = new Station("Kozani", "Tranta 2");
        station.setMotorcycle1(motorcycle);
        station.setMotorcycle2(motorcycle2);
        station.setTruck1(truck);
        station.setTruck2(truck2);
        station.setTechnician1(truckEmployee);
        station.setTechnician2(truckEmployee2);
        station.setTechnician3(truckEmployee);
        station.setTechnician4(motorEmployee2);

        Station station2 = new Station("Kozani", "Giannari 2");
        station2.setMotorcycle1(motorcycle);
        station2.setMotorcycle2(motorcycle2);
        station2.setTruck1(truck);
        station2.setTruck2(truck2);
        station2.setTechnician1(truckEmployee);
        station2.setTechnician2(truckEmployee2);
        station2.setTechnician3(truckEmployee);
        station2.setTechnician4(motorEmployee2);

        Station station3 = new Station("Kozani", "Aristotelous 3");
        station3.setMotorcycle1(motorcycle);
        station3.setMotorcycle2(motorcycle2);
        station3.setTruck1(truck);
        station3.setTruck2(truck2);
        station3.setTechnician1(truckEmployee);
        station3.setTechnician2(truckEmployee2);
        station3.setTechnician3(truckEmployee);
        station3.setTechnician4(motorEmployee2);

        roadsideAssistance.setStation1(station);
        roadsideAssistance.setStation2(station2);
        roadsideAssistance.setStation3(station3);

        roadsideAssistance.setVehicles(truck);
        roadsideAssistance.setVehicles(truck2);
        roadsideAssistance.setVehicles(motorcycle);
        roadsideAssistance.setVehicles(motorcycle2);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Kentauros");
        primaryStage.setScene(new Scene(root, 520, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

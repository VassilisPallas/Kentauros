package roadside_assistance;

import helpers.AlertBoxHelper;
import helpers.FileHelper;
import helpers.GoToOtherPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Giota on 16/1/2017.
 */
public class RoadsideAssistanceController implements Initializable {

    @FXML
    private TextArea description;

    @FXML
    private ComboBox vehicleType;

    @FXML
    private Label descriptionError;

    @FXML
    private javafx.scene.control.Button callIncident;

    private Vehicle vehicle = null;
    private NonSubscriber subscriber;

    private ObservableList<String> vehicleTypes =
            FXCollections.observableArrayList(
                    "ΙΧ",
                    "Φορτηγό",
                    "Δίκυκλο"
            );

    private double wholePrice = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleType.setItems(vehicleTypes);
        callIncident.setOnAction(event -> {
            if (validate()) {
                if (subscriber  == null) {
                    // to to register page
                    try {
                        Incident incident = new Incident(new Date(), new Date(), description.getText(), 0, subscriber, false);
                        GoToOtherPage.registerPage(getClass(), (Stage) callIncident.getScene().getWindow(), null, wholePrice + 70, vehicle, incident);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Incident incident = new Incident(new Date(), new Date(), description.getText(), 0, subscriber, true);
                    try {
                        saveIncident(incident, FileHelper.searchFilesForSubscriber(new File("src/files/"), subscriber.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    AlertBoxHelper.infoBox("Νέο περιστατικό", "Το νέο περιστατικό αποθηκεύτηκε.");
                }
            }
        });

        vehicleType.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    String insuranceType = (String) newValue;

                    switch (insuranceType) {
                        case "ΙΧ":
                            vehicle = new Automobile();
                            wholePrice = 10;
                            break;
                        case "Φορτηγό":
                            vehicle = new Truck();
                            wholePrice = 15;
                            break;
                        case "Δίκυκλο":
                            vehicle = new Motorcycle();
                            wholePrice = 7;
                            break;
                    }
                }));
    }

    public void getData(NonSubscriber subscriber, Vehicle vehicle) {
        this.subscriber = subscriber;
        this.vehicle = vehicle;

        if (vehicle instanceof Automobile) {
            vehicleType.getSelectionModel().select("ΙΧ");
            vehicleType.setDisable(true);
        } else if (vehicle instanceof Truck) {
            vehicleType.getSelectionModel().select("Φορτηγό");
            vehicleType.setDisable(true);
        } else if (vehicle instanceof Motorcycle){
            vehicleType.getSelectionModel().select("Δίκυκλο");
            vehicleType.setDisable(true);
        }
    }

    private boolean validate() {
        boolean result = true;
        if (description.getText().length() == 0) {
            descriptionError.setVisible(true);
            result = false;
        } else {
            descriptionError.setVisible(false);
        }

        return result;
    }

    private void saveIncident(Incident incident, Subscriber subscriber) {
        String username = "";
        if (subscriber != null) {
            username = subscriber.getAccount().getUsername();
        }
        if (FileHelper.fileExists(username + "_incident.txt")) {
            FileHelper.appendToFile(username + "_incident", incident.toString());
        } else {
            FileHelper.saveFile(username + "_incident", incident.toString());
        }

        if (FileHelper.fileExists("incidents.txt")) {
            FileHelper.appendToFile("incidents", incident.toString());
        } else {
            FileHelper.saveFile("incidents", incident.toString());
        }
    }
}

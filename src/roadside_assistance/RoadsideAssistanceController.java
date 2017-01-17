package roadside_assistance;

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

    public void getData(NonSubscriber subscriber) {
        this.subscriber = subscriber;
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
}

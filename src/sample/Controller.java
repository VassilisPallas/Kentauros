package sample;

import helpers.GoToOtherPage;
import helpers.SubscriptionType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Automobile;
import model.Motorcycle;
import model.Truck;
import model.Vehicle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Hyperlink registerLink;

    @FXML
    private ComboBox vehicleType;

    @FXML
    private RadioButton semi;

    @FXML
    private RadioButton annual;

    @FXML
    private Label price;

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button callRoadsideAssistance;

    private double pricePerMonth = 0, wholePrice = 0;
    private SubscriptionType type = SubscriptionType.SEMI;
    private Vehicle vehicle = null;

    private ObservableList<String> vehicleTypes =
            FXCollections.observableArrayList(
                    "ΙΧ",
                    "Φορτηγό",
                    "Δίκυκλο"
            );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleType.setItems(vehicleTypes);
        registerLink.setOnAction(event -> {
            try {
                java.awt.Desktop.getDesktop().browse(new URI("http://www.yahoo.com"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        loginButton.setOnAction(event -> {
            try {
                GoToOtherPage.loginPage(getClass(), (Stage) loginButton.getScene().getWindow());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        registerButton.setOnAction(event -> {
            try {
                goToRegisterPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        vehicleType.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    String insuranceType = (String) newValue;

                    switch (insuranceType) {
                        case "ΙΧ":
                            pricePerMonth = 5;
                            vehicle = new Automobile();
                            break;
                        case "Φορτηγό":
                            pricePerMonth = 8;
                            vehicle = new Truck();
                            break;
                        case "Δίκυκλο":
                            pricePerMonth = 3;
                            vehicle = new Motorcycle();
                            break;
                    }

                    calculatePrice(pricePerMonth, type);
                }));

        semi.setOnAction(event -> {
            type = SubscriptionType.SEMI;
            calculatePrice(pricePerMonth, type);
        });
        annual.setOnAction(event -> {
            type = SubscriptionType.ANNUAL;
            calculatePrice(pricePerMonth, type);
        });

        callRoadsideAssistance.setOnAction(event -> {

        });
    }

    private void calculatePrice(double pricePerMonth, SubscriptionType type) {
        switch (type) {
            case SEMI:
                wholePrice = (pricePerMonth * 12) - ((pricePerMonth * 12) * 0.05);
                break;
            case ANNUAL:
                wholePrice = pricePerMonth * 12;
                break;
        }

        if (wholePrice > 0)
            registerButton.setDisable(false);

        price.setText(wholePrice + "€");
    }

    private void goToRegisterPage() throws IOException {
        GoToOtherPage.registerPage(getClass(), (Stage) registerButton.getScene().getWindow(), type, wholePrice, vehicle);
    }
}

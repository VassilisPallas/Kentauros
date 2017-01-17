package register;

import helpers.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Giota on 16/1/2017.
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField cardNumber;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField username;

    @FXML
    private TextField phone;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField licencePlate;

    @FXML
    private Label firstNameError;

    @FXML
    private Label lastNameError;

    @FXML
    private Label usernameError;

    @FXML
    private Label phoneError;

    @FXML
    private Label emailError;

    @FXML
    private Label passwordError;

    @FXML
    private Label licencePlateError;

    @FXML
    private Label cardNumberError;

    @FXML
    private Button registerButton;

    @FXML
    private Label priceLabel;

    private double price;
    private SubscriptionType type;
    private Vehicle vehicle;
    private Incident incident;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addTextLimiter(cardNumber, 16);

        registerButton.setOnAction(event -> {
            if (validate()) {
                Subscription subscription = getSubscription();
                List<Subscription> subscriptions = new ArrayList<>();
                subscriptions.add(subscription);
                vehicle.licensePlate = licencePlate.getText();
                Subscriber subscriber = new Subscriber(firstName.getText() + " " + lastName.getText(), subscriptions, new Card(cardNumber.getText()), vehicle);
                Account account = new Account(phone.getText(), email.getText(), username.getText(), password.getText(), new Date(), subscriber);
                subscriber.setAccount(account);

                saveUser(subscriber);
                saveSubscription(subscription);
                saveAccount(account);
                saveVehicle();
                if (incident != null) {
                    incident.setNonSubscriber(subscriber);
                    saveIncident(incident, subscriber);
                }

                AlertBoxHelper.infoBox("Δημιουργία Χρήστη", "Η δημιουργία και η πληρωμή ολοκληρώθηκε.");

                try {
                    GoToOtherPage.profilePage(getClass(), (Stage) registerButton.getScene().getWindow(), subscriber, account, subscription);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getData(double price, SubscriptionType type, Vehicle vehicle, Incident incident) {
        this.price = price;
        this.type = type;
        this.vehicle = vehicle;
        this.incident = incident;

        priceLabel.setText(price + "€ " + (type != null ? type : ""));
    }

    private static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    private boolean validate() {
        boolean result = true;
        if (firstName.getText().length() == 0) {
            firstNameError.setVisible(true);
            result = false;
        } else {
            firstNameError.setVisible(false);
        }

        if (lastName.getText().length() == 0) {
            lastNameError.setVisible(true);
            result = false;
        } else {
            lastNameError.setVisible(false);
        }

        if (username.getText().length() == 0) {
            usernameError.setVisible(true);
            result = false;
        } else {
            usernameError.setVisible(false);
        }

        if (phone.getText().length() == 0) {
            phoneError.setVisible(true);
            result = false;
        } else {
            phoneError.setVisible(false);
        }

        if (email.getText() == null || !isValidEmailAddress(email.getText())) {
            emailError.setVisible(true);
            result = false;
        } else {
            emailError.setVisible(false);
        }

        if (password.getText().length() == 0) {
            passwordError.setVisible(true);
            result = false;
        } else {
            passwordError.setVisible(false);
        }

        if (cardNumber.getText().length() == 0) {
            cardNumberError.setVisible(true);
            result = false;
        } else {
            cardNumberError.setVisible(false);
        }

        if (licencePlate.getText().length() == 0) {
            licencePlateError.setVisible(true);
            result = false;
        } else {
            licencePlateError.setVisible(false);
        }

        return result;
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private Subscription getSubscription() {
        Date expirationDate = null;

        if (type == null) {
            type = SubscriptionType.ANNUAL;
        }

        switch (type) {
            case SEMI:
                try {
                    expirationDate = DateHelper.addMonths(6);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case ANNUAL:
                try {
                    expirationDate = DateHelper.addMonths(12);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }


        return new Subscription(type, price, new Date(), expirationDate, new Date());
    }

    private void saveUser(Subscriber subscriber) {

        String content = subscriber.toString();
        FileHelper.saveFile(username.getText(), content);
    }

    private void saveSubscription(Subscription subscription) {

        String content = subscription.toString();
        FileHelper.saveFile(username.getText() + "_subscription", content);
    }

    private void saveAccount(Account account) {
        String content = account.toString();
        FileHelper.saveFile(username.getText() + "_account", content);
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

    private void saveVehicle() {
        FileHelper.saveFile("vehicle_" + licencePlate.getText(), vehicle.getClass().getName().replace("model.", "") + " " + licencePlate.getText());
    }
}

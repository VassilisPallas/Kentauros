package profile;

import helpers.DateHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Account;
import model.Subscriber;
import model.Subscription;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Giota on 17/1/2017.
 */
public class ProfileController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private Label expiryDate;

    @FXML
    private Button newSubscription;

    @FXML
    private Button newIncident;

    private Subscriber subscriber;
    private Account account;
    private Subscription subscription;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newSubscription.setOnAction(event -> {

        });

        newIncident.setOnAction(event -> {

        });
    }

    public void getData(Subscriber subscriber, Account account, Subscription subscription) {
        this.subscriber = subscriber;
        this.account = account;
        this.subscription = subscription;

        username.setText(account.getUsername());
        expiryDate.setText(DateHelper.dateFormat(subscription.getExpirationDate()));
    }
}

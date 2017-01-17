package login;

import helpers.FileHelper;
import helpers.GoToOtherPage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import model.Subscriber;
import model.Subscription;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Giota on 16/1/2017.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginButton.setOnAction(event -> {
            if (validate()) {
                try {
                    Account account = FileHelper.getUserAccount(username.getText());
                    Subscriber subscriber = FileHelper.getSubscriber(username.getText());
                    subscriber.setAccount(account);
                    Subscription subscription = FileHelper.getSubscription(username.getText());
                    List<Subscription> subscriptions = new ArrayList<>();
                    subscriptions.add(subscription);
                    subscriber.setSubscriptions(subscriptions);

                    GoToOtherPage.profilePage(getClass(), (Stage) loginButton.getScene().getWindow(), subscriber, account, subscription);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validate() {
        boolean result = true;

        if (username.getText().length() == 0) {
            loginError.setVisible(true);
            result = false;
        } else {
            loginError.setVisible(false);
        }

        if (password.getText().length() == 0) {
            loginError.setVisible(true);
            result = false;
        } else {
            loginError.setVisible(false);
        }

        try {
            if (!FileHelper.getLogin(username.getText(), password.getText())) {
                loginError.setVisible(true);
                result = false;
            } else {
                loginError.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            loginError.setVisible(true);
            return false;
        }
        return result;
    }
}

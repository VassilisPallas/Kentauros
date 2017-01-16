package login;

import helpers.FileHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
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
                System.out.print("ok");
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

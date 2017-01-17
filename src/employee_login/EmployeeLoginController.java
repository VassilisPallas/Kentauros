package employee_login;

import helpers.FileHelper;
import helpers.GoToOtherPage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 17/1/2017.
 */
public class EmployeeLoginController implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private Button login;

    @FXML
    private Label nameError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login.setOnAction(event -> {
            if (validate()) {
                try {
                    GoToOtherPage.employeePage(getClass(), (Stage) login.getScene().getWindow(), FileHelper.getTechnician(name.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean validate() {
        boolean result = true;

        if (name.getText().length() == 0) {
            nameError.setVisible(true);
            result = false;
        } else {
            nameError.setVisible(false);
        }

        try {
            if (!FileHelper.employeeLogin(name.getText())) {
                nameError.setVisible(true);
                result = false;
            } else {
                nameError.setVisible(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            nameError.setVisible(true);
            return false;
        }

        return result;
    }
}

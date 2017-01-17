package helpers;

import javafx.scene.control.Alert;

/**
 * Created by User on 16/1/2017.
 */
public class AlertBoxHelper {

    public static void infoBox(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}

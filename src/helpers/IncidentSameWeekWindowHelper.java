package helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Subscriber;

/**
 * Created by Giota on 17/1/2017.
 */
public class IncidentSameWeekWindowHelper {
    private Label label = new Label("Επιβαρύνεστε με 20€ γιατί έχετε ξανακαλέσει για βοήθεια μέσα στην βδομάδα.");
    private Button button = new Button("Πληρωμή");
    private Stage stage = new Stage();
    private boolean paid = false;

    public boolean pay() {

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Πληρωμή");
        stage.setMinHeight(300);
        stage.setMinWidth(450);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(label, 0, 0);
        gridPane.add(button, 0, 1);

        button.setOnAction(event -> {
            paid = true;
            stage.close();
        });

        Scene scene = new Scene(gridPane, 450, 300);
        stage.setScene(scene);
        stage.showAndWait();

        return paid;
    }
}

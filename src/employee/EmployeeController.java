package employee;

import com.sun.org.apache.xpath.internal.operations.Bool;
import helpers.DateHelper;
import helpers.FileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import model.Incident;
import model.NonSubscriber;
import model.Subscriber;
import model.Technician;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Giota on 17/1/2017.
 */
public class EmployeeController implements Initializable {

    @FXML
    private GridPane grid;

    private TableView table = new TableView();
    private Technician technician;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initialize(ObservableList<Incident> incidents){
        table.setPlaceholder(new Label("Δεν υπάρχουν περιστατικά"));
        table.setEditable(true);

        TableColumn callDate = new TableColumn("Ημερομηνία Περιστατικού");
        callDate.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incident, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incident, String> p) {
                return new SimpleStringProperty(DateHelper.dateFormat(p.getValue().getCallDate()));
            }
        });

        TableColumn callTime = new TableColumn("Ώρα Περιστατικού");
        callTime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incident, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incident, String> p) {
                return new SimpleStringProperty(DateHelper.timeFormat(p.getValue().getCallTime()));
            }
        });

        TableColumn description = new TableColumn("Περιγραφή");
        description.setCellValueFactory(
                new PropertyValueFactory<Incident, String>("description"));

        TableColumn name = new TableColumn("Όνομα Πελάτη");

        name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incident, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incident, String> p) {
                return new SimpleStringProperty(p.getValue().getNonSubscriber().getName());
            }
        });

        TableColumn isSubscriber = new TableColumn("Συνδρομητής");
        isSubscriber.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Incident, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Incident, String> p) {
                return new SimpleStringProperty(p.getValue().isSubscriber ? "Ναι" : "Όχι");
            }
        });

        TableColumn vehiclePosition = new TableColumn("Θέση οχήματος");
        vehiclePosition.setCellValueFactory(
                new PropertyValueFactory<Incident, Integer>("vehiclePosition"));

        table.setItems(incidents);

        table.getColumns().addAll(callDate, callTime, description, name, isSubscriber, vehiclePosition);

        grid.add(table, 0, 0);
    }

    public void getData(Technician technician) throws IOException {
        ObservableList<Incident> incidents = FXCollections.observableArrayList();
        this.technician = technician;
        System.out.println(technician.getCategory().trim());
        if(technician.getCategory().trim().equals("roadsideAssistance")){
            List<Incident> incidentList = FileHelper.getAllIncidents();
            incidents.addAll(incidentList);
            System.out.println(Arrays.toString(incidentList.toArray()));
        }

        initialize(incidents);
    }
}

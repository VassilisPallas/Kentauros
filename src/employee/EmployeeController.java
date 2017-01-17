package employee;

import com.sun.org.apache.xpath.internal.operations.Bool;
import helpers.AlertBoxHelper;
import helpers.DateHelper;
import helpers.FileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import model.Incident;
import model.NonSubscriber;
import model.Subscriber;
import model.Technician;
import sample.Main;

import java.awt.event.ActionEvent;
import java.io.File;
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
    private String buttonTitle = "";
    private String title = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initialize(ObservableList<Incident> incidents) {
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

        TableColumn actionCol = new TableColumn("Ενέργεια");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        actionCol.setCellFactory(action);

        table.setItems(incidents);

        table.getColumns().addAll(callDate, callTime, description, name, isSubscriber, vehiclePosition, actionCol);

        table.setColumnResizePolicy((param) -> TableView.CONSTRAINED_RESIZE_POLICY);

        grid.add(table, 0, 0);
    }

    public void getData(Technician technician) throws IOException {
        this.technician = technician;
        ObservableList<Incident> incidents = FXCollections.observableArrayList();
        this.technician = technician;
        if (technician.getCategory().trim().equals("roadsideAssistance")) {
            List<Incident> incidentList = FileHelper.getAllIncidents("src/files/incidents.txt");
            incidents.addAll(incidentList);
            Main.core.setEmployee(technician);
            Main.core.setIncidents(incidents);
            buttonTitle = "Ειδοποίηση Κέντρου";
        } else if (technician.getCategory().trim().equals("truck")) {
            List<Incident> incidentList = FileHelper.getAllIncidents("src/files/openIncidents.txt");
            incidents.addAll(incidentList);
            buttonTitle = "Μεταφορά Οχήματος";
            title = "Μεταφορά Οχήματος";
        } else {
            List<Incident> incidentList = FileHelper.getAllIncidents("src/files/openIncidents.txt");
            incidents.addAll(incidentList);
            buttonTitle = "Κλήση Φορτηγού";
            title = "Κλήση Φορτηγού";
        }

        initialize(incidents);
    }

    Callback<TableColumn<Incident, String>, TableCell<Incident, String>> action = //
            new Callback<TableColumn<Incident, String>, TableCell<Incident, String>>() {
                @Override
                public TableCell call(final TableColumn<Incident, String> param) {
                    final TableCell<Incident, String> cell = new TableCell<Incident, String>() {

                        final Button btn = new Button(buttonTitle);

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btn.setOnAction(event -> {
                                    Incident incident = getTableView().getItems().get(getIndex());
                                    if (technician.getCategory().trim().equals("roadsideAssistance")) {
                                        employeeListener(incident);
                                    } else {
                                        truckListener(incident);
                                    }

                                    btn.setDisable(true);
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

    private void employeeListener(Incident incident) {
        if (FileHelper.fileExists("openIncidents.txt")) {
            FileHelper.appendToFile("openIncidents.txt", incident.toString());
        } else {
            FileHelper.saveFile("openIncidents", incident.toString());
        }
    }

    private void truckListener(Incident incident) {
        try {
            Subscriber subscriber = FileHelper.searchFilesForSubscriber(new File("src/files/"), incident.getNonSubscriber().getName());
            Main.roadsideAssistance.setVehicles(subscriber.getVehicle());
            if (Main.roadsideAssistance.canCarry()) {
                AlertBoxHelper.infoBox(title, "Το όχημα μπορεί να μεταφερθεί");
            } else {
                AlertBoxHelper.errorBox(title, "Το όχημα δεν μπορεί να μεταφερθεί");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

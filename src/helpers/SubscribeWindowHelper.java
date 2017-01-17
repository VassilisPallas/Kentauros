package helpers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Giota on 17/1/2017.
 */
public class SubscribeWindowHelper {
    private Label price = new Label();
    private TextField cardNumber = new TextField();
    private Button pay = new Button();
    private Stage stage = new Stage();
    private Vehicle vehicle = null;
    private ToggleGroup group = new ToggleGroup();
    private RadioButton semi = new RadioButton("Εξαμινιαία"), annual = new RadioButton("Ετήσια");
    private double pricePerMonth = 0;
    private SubscriptionType type;

    public Subscription subscribe(String title, Subscriber subscriber, Subscription subscription) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinHeight(300);
        stage.setMinWidth(450);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        semi.setToggleGroup(group);
        annual.setToggleGroup(group);

        switch (subscription.getDuration()) {
            case ANNUAL:
                annual.setSelected(true);
                type = SubscriptionType.ANNUAL;
                break;
            case SEMI:
                semi.setSelected(true);
                type = SubscriptionType.SEMI;
        }
        calculatePrice(subscriber);

        semi.setOnAction(event -> {
            type = SubscriptionType.SEMI;
            calculatePrice(subscriber);
        });
        annual.setOnAction(event -> {
            type = SubscriptionType.ANNUAL;
            calculatePrice(subscriber);
        });


        Label subscriptionLabel = new Label("Επιλέξτε συνδρομή");
        gridPane.add(subscriptionLabel, 0, 0);

        HBox hBox = new HBox(20);
        hBox.getChildren().addAll(semi, annual);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        gridPane.add(hBox, 1, 0);

        gridPane.add(price, 1, 1);

        Label cardLabel = new Label(subscriber.getCard().getCardNumber());
        gridPane.add(cardLabel, 0, 2);

        Button pay = new Button("Ανανέωση Συνδρομής");
        gridPane.add(pay, 0, 3);

        pay.setOnAction(event -> {
            String content = getSubscription().toString();
            FileHelper.saveFile(subscriber.getAccount().getUsername() + "_subscription", content);
            stage.close();
        });

        Scene scene = new Scene(gridPane, 450, 300);
        stage.setScene(scene);
        stage.showAndWait();

        return getSubscription();
    }

    private void calculatePrice(Subscriber subscriber) {

        double wholePrice = 0;

        if (subscriber.getVehicle() instanceof Automobile) {
            pricePerMonth = 5;
        } else if (subscriber.getVehicle() instanceof Truck) {
            pricePerMonth = 8;
        } else {
            pricePerMonth = 3;
        }

        switch (type) {
            case ANNUAL:
                wholePrice = (pricePerMonth * 12) - ((pricePerMonth * 12) * 0.05);
                break;
            case SEMI:
                wholePrice = pricePerMonth * 12;
                break;
        }

        price.setText(wholePrice + "€");
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


        return new Subscription(type, Double.valueOf(price.getText().replace("€", "")), new Date(), expirationDate, new Date());
    }
}

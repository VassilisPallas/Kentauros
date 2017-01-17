package helpers;

import employee.EmployeeController;
import employee_login.EmployeeLoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.*;
import profile.ProfileController;
import register.RegisterController;
import roadside_assistance.RoadsideAssistanceController;

import java.io.IOException;

/**
 * Created by Giota on 16/1/2017.
 */
public class GoToOtherPage {

    public static void registerPage(Class clazz, Stage stage, SubscriptionType type, double price, Vehicle vehicle, Incident incident) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/register/register.fxml"));
        Parent sceneMain = loader.load();
        RegisterController controller = loader.getController();
        controller.getData(price, type, vehicle, incident);

        stage.setTitle("Εγγραφή και πληρωμή");
        Scene scene = new Scene(sceneMain, 360, 550);
        stage.setScene(scene);
    }

    public static void loginPage(Class clazz, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/login/login.fxml"));
        Parent sceneMain = loader.load();

        stage.setTitle("Kentauros - Είσοδος");
        Scene scene = new Scene(sceneMain, 360, 300);
        stage.setScene(scene);
    }

    public static void roadsideAssistancePage(Class clazz, Stage stage, Subscriber subscriber, Vehicle vehicle) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/roadside_assistance/roadside_assistance.fxml"));
        Parent sceneMain = loader.load();
        RoadsideAssistanceController controller = loader.getController();
        controller.getData(subscriber, vehicle);


        stage.setTitle("Κλήση Οδικής Βοήθειας");
        Scene scene = new Scene(sceneMain, 360, 300);
        stage.setScene(scene);
    }

    public static void profilePage(Class clazz, Stage stage, Subscriber subscriber, Account account, Subscription subscription) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/profile/profile.fxml"));
        Parent sceneMain = loader.load();
        ProfileController controller = loader.getController();
        controller.getData(subscriber, account, subscription);

        stage.setTitle("Προφίλ");
        Scene scene = new Scene(sceneMain, 380, 300);
        stage.setScene(scene);
    }

    public static void employeeLoginPage(Class clazz, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/employee_login/employee_login.fxml"));
        Parent sceneMain = loader.load();

        stage.setTitle("Σύνδεση");
        Scene scene = new Scene(sceneMain, 500, 400);
        stage.setScene(scene);
    }

    public static void employeePage(Class clazz, Stage stage, Technician technician) throws IOException {
        FXMLLoader loader = new FXMLLoader(clazz.getResource("/employee/employee.fxml"));
        Parent sceneMain = loader.load();
        EmployeeController controller = loader.getController();
        controller.getData(technician);

        stage.setTitle("Προφίλ");
        Scene scene = new Scene(sceneMain, 380, 300);
        stage.setScene(scene);
    }



}

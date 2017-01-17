package helpers;

import model.*;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Created by User on 16/1/2017.
 */
public class FileHelper {
    public static void saveFile(String name, String content) {
        BufferedWriter writer = null;

        File file = new File("src/files/" + name + ".txt");
        try {
            writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getLogin(String username, String password) throws IOException {
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + username + "_account.txt"));
        byte[] buffer = new byte[8192];

        try {
            int i = 0;
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");
                if (split[3].equals(password)) {
                    return true;
                }
            }
        } finally {
            input.close();
        }

        return false;
    }

    public static void appendToFile(String title, String content) {
        System.out.println(content);
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(new File("src/files/" + title), true));
            pw.println();
            pw.println(content);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExists(String title) {
        File f = new File("src/files/" + title);
        return f.exists() && !f.isDirectory();
    }

    public static Account getUserAccount(String username) throws IOException {
        Account account = new Account();
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + username + "_account.txt"));
        byte[] buffer = new byte[8192];

        try {
            int i = 0;
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");
                account.setPhone(split[0]);
                account.setEmail(split[1]);
                account.setUsername(split[2]);
                account.setPassword(split[3]);
                account.setRegistrationDate(DateHelper.stringToDate(split[4], "dd/MM/yyyy"));
            }
        } finally {
            input.close();
            return account;
        }
    }

    public static Subscriber getSubscriber(String username) throws IOException {
        Subscriber subscriber = new Subscriber("");
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + username + ".txt"));
        byte[] buffer = new byte[8192];

        try {
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");
                subscriber.setName(split[0] + " " + split[1]);
                subscriber.setCard(new Card(split[2]));

                String licencePlate = "";
                for (int i = 3; i < split.length; i++) {
                    licencePlate += split[i] + " ";
                }

                subscriber.setVehicle(getVehicle(licencePlate.trim()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            input.close();
            return subscriber;
        }
    }

    private static Vehicle getVehicle(String licencePlate) throws IOException {
        System.out.print("src/files/" + "vehicle_" + licencePlate + ".txt");
        Vehicle vehicle = null;
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + "vehicle_" + licencePlate + ".txt"));
        byte[] buffer = new byte[8192];

        try {
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");

                switch (split[0]) {
                    case "Automobile":
                        vehicle = new Automobile();
                        break;
                    case "Motorcycle":
                        vehicle = new Motorcycle();
                        break;
                    case "Truck":
                        vehicle = new Truck();
                        break;
                }
                vehicle.licensePlate = licencePlate;
            }
        } finally {
            input.close();
            return vehicle;
        }
    }

    public static Subscription getSubscription(String username) throws IOException {
        Subscription subscription = new Subscription();
        InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + username + "_subscription.txt"));
        byte[] buffer = new byte[8192];

        try {
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                String[] split = new String(buffer).split(" ");
                switch (split[0]) {
                    case "semi":
                        subscription.setDuration(SubscriptionType.SEMI);
                        break;
                    case "annual":
                        subscription.setDuration(SubscriptionType.ANNUAL);
                        break;
                }
                subscription.setAmount(Double.parseDouble(split[1]));
                subscription.setRegistrationDate(DateHelper.stringToDate(split[2], "dd/MM/yyyy"));
                subscription.setExpirationDate(DateHelper.stringToDate(split[3], "dd/MM/yyyy"));
                subscription.setPaymentDate(DateHelper.stringToDate(split[4], "dd/MM/yyyy"));
            }
        } finally {
            input.close();
            return subscription;
        }
    }

    public static Subscriber searchFilesForSubscriber(File file, String pattern) throws IOException {

        Subscriber subscriber = null;
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("file has to be a directory");
        }

        File[] files = file.listFiles();

        if (files != null) {
            for (File currentFile : files) {
                if (currentFile.isDirectory()) {
                    searchFilesForSubscriber(currentFile, pattern);
                } else {
                    if (!currentFile.getName().contains("incident")) {
                        Scanner scanner = new Scanner(currentFile);
                        if (scanner.findWithinHorizon(pattern, 0) != null) {
                            String[] array = currentFile.getName().split("\\.");
                            subscriber = getSubscriber(array[0]);
                            subscriber.setAccount(getUserAccount(array[0]));
                            ArrayList<Subscription> subscriptions = new ArrayList<>();
                            subscriptions.add(getSubscription(array[0]));
                            subscriber.setSubscriptions(subscriptions);
                            break;
                        }
                        scanner.close();
                    }
                }
            }
        }
        return subscriber;
    }

    private static List<Incident> getIncidents(String Path) throws IOException {
        List<Incident> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(Path)))) {
            for (String line; (line = br.readLine()) != null; ) {
                String[] arr = line.split(" ");
                try {
                    String description = "";
                    for (int i = 7; i < arr.length; i++) {
                        description += arr[i] + " ";
                    }
                    if (!arr[0].isEmpty() && !arr[1].isEmpty())
                        list.add(new Incident(DateHelper.stringToDate(arr[0], "dd/MM/yyyy"), DateHelper.stringToDate(arr[1], "HH:mm:ss"), description, Integer.parseInt(arr[3]), new NonSubscriber(arr[4] + " " + arr[5]), Boolean.valueOf(arr[6])));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public static List<Incident> getIncidents(Subscriber subscriber) throws IOException {
        return getIncidents("src/files/" + subscriber.getAccount().getUsername() + "_incident.txt");
    }

    public static List<Incident> getAllIncidents() throws IOException {
        return getIncidents("src/files/incidents.txt");
    }

    public static boolean employeeLogin(String name) throws IOException {
        List<String> files = new ArrayList<>();
        files.add("roadsideAssistanceEmployee");
        files.add("truckEmployee");
        files.add("truckEmployee2");
        files.add("motorEmployee");
        files.add("motorEmployee2");

        for (String file : files) {
            InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + file + ".txt"));
            byte[] buffer = new byte[8192];

            try {
                int i = 0;
                for (int length = 0; (length = input.read(buffer)) != -1; ) {
                    String[] split = new String(buffer).split(" ");
                    String n = split[0] + " " + split[1];
                    if (n.equals(name)) {
                        return true;
                    }
                }
            } finally {
                input.close();
            }
        }

        return false;
    }

    public static Technician getTechnician(String name) throws IOException {
        List<String> files = new ArrayList<>();
        files.add("roadsideAssistanceEmployee");
        files.add("truckEmployee");
        files.add("truckEmployee2");
        files.add("motorEmployee");
        files.add("motorEmployee2");

        Technician technician = null;

        for (String file : files) {
            InputStream input = new BufferedInputStream(new FileInputStream("src/files/" + file + ".txt"));
            byte[] buffer = new byte[8192];

            try {
                int i = 0;
                for (int length = 0; (length = input.read(buffer)) != -1; ) {
                    String[] split = new String(buffer).split(" ");
                    String n = split[0] + " " + split[1];
                    if (n.equals(name)) {
                        technician = new Technician(n, split[2]);
                    }
                }
            } finally {
                input.close();
            }
        }
        return technician;
    }
}

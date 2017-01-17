package helpers;

/**
 * Created by User on 16/1/2017.
 */
public enum SubscriptionType {
    SEMI("Εξαμηνιαία"), ANNUAL("Ετήσια");

    private String label;

    SubscriptionType(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }

    public String getSubscriptionName() {
        switch (label) {
            case "Εξαμηνιαία":
                return "semi";
            case "Ετήσια":
                return "annual";
        }
        return "";
    }
}

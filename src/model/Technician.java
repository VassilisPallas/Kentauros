package model;

/**
 * Created by Giota on 31/12/2016.
 */
public class Technician extends Employee {
    private String category;

    public Technician(String name, String category) {
        super(name);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return getName() + " " + category;
    }
}

package model;

/**
 * Created by User on 31/12/2016.
 */
public class Station {
    private String city;
    private String address;
    private Truck truck1;
    private Truck truck2;
    private Motorcycle motorcycle1;
    private Motorcycle motorcycle2;
    private Technician technician1;
    private Technician technician2;
    private Technician technician3;
    private Technician technician4;

    public Station(String city, String address) {
        this.city = city;
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Truck getTruck1() {
        return truck1;
    }

    public void setTruck1(Truck truck1) {
        this.truck1 = truck1;
    }

    public Truck getTruck2() {
        return truck2;
    }

    public void setTruck2(Truck truck2) {
        this.truck2 = truck2;
    }

    public Motorcycle getMotorcycle1() {
        return motorcycle1;
    }

    public void setMotorcycle1(Motorcycle motorcycle1) {
        this.motorcycle1 = motorcycle1;
    }

    public Motorcycle getMotorcycle2() {
        return motorcycle2;
    }

    public void setMotorcycle2(Motorcycle motorcycle2) {
        this.motorcycle2 = motorcycle2;
    }

    public Technician getTechnician1() {
        return technician1;
    }

    public void setTechnician1(Technician technician1) {
        this.technician1 = technician1;
    }

    public Technician getTechnician2() {
        return technician2;
    }

    public void setTechnician2(Technician technician2) {
        this.technician2 = technician2;
    }

    public Technician getTechnician3() {
        return technician3;
    }

    public void setTechnician3(Technician technician3) {
        this.technician3 = technician3;
    }

    public Technician getTechnician4() {
        return technician4;
    }

    public void setTechnician4(Technician technician4) {
        this.technician4 = technician4;
    }
}

package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 31/12/2016.
 */
public class RoadsideAssistance {
    private String name;
    private String core;
    private Station station1;
    private Station station2;
    private Station station3;
    private List<Vehicle> vehicles = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public Station getStation1() {
        return station1;
    }

    public void setStation1(Station station1) {
        this.station1 = station1;
    }

    public Station getStation2() {
        return station2;
    }

    public void setStation2(Station station2) {
        this.station2 = station2;
    }

    public Station getStation3() {
        return station3;
    }

    public void setStation3(Station station3) {
        this.station3 = station3;
    }

    public List<Vehicle> getVehicle() {
        return vehicles;
    }

    public void setVehicle(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void setVehicles(Vehicle vehicle){
        vehicles.add(vehicle);
    }

    public boolean canCarry() {
        int automobileCount = 0, motorCount = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Automobile)
                automobileCount++;
            else if (vehicle instanceof Motorcycle)
                motorCount++;
        }

        return (automobileCount == 2 && motorCount == 0) || (automobileCount == 0 && motorCount == 4) || (automobileCount == 1 && motorCount == 2) || (automobileCount == 1 || motorCount == 1);
    }
}

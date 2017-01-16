package model;

import java.util.Date;

/**
 * Created by Giota on 31/12/2016.
 */
public class Incident {
    private Date callDate;
    private Date callTime;
    private Date confrontationTime;
    private String description;
    private int vehiclePosition;
    private NonSubscriber nonSubscriber;
    private Core core;

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public Date getConfrontationTime() {
        return confrontationTime;
    }

    public void setConfrontationTime(Date confrontationTime) {
        this.confrontationTime = confrontationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVehiclePosition() {
        return vehiclePosition;
    }

    public void setVehiclePosition(int vehiclePosition) {
        this.vehiclePosition = vehiclePosition;
    }

    public NonSubscriber getNonSubscriber() {
        return nonSubscriber;
    }

    public void setNonSubscriber(NonSubscriber nonSubscriber) {
        this.nonSubscriber = nonSubscriber;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }
}

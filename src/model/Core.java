package model;

import java.util.List;

/**
 * Created by Giota on 31/12/2016.
 */
public class Core {
    private Employee employee;
    private List<Incident> incidents;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }
}

package model;

import java.util.List;

/**
 * Created by Giota on 31/12/2016.
 */
public class Workshop {
    private List<Incident> incidents;

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncident(List<Incident> incidents) {
        this.incidents = incidents;
    }
}

package model;

import java.util.Set;

/**
 * Created by Giota on 31/12/2016.
 */
public class NonSubscriber {
    private String name;
    //private Set<Incident> incidents;


    public NonSubscriber(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Incident> getIncidents() {
//        return incidents;
//    }
//
//    public void setIncidents(Set<Incident> incidents) {
//        this.incidents = incidents;
//    }
}

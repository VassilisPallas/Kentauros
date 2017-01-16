package model;

import java.util.List;

/**
 * Created by Giota on 31/12/2016.
 */
public class Subscriber extends NonSubscriber {
    private List<Subscription> subscriptions;
    private Card card;
    private Account account;
    private Vehicle vehicle;

    public Subscriber(String name, List<Subscription> subscriptions, Card card, Vehicle vehicle) {
        super(name);
        this.subscriptions = subscriptions;
        this.card = card;
        this.vehicle = vehicle;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card cards) {
        this.card = cards;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return super.getName() + " " + card.getCardNumber();
    }
}

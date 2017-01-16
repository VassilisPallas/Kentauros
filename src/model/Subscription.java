package model;

import helpers.DateHelper;
import helpers.SubscriptionType;

import java.util.Date;

/**
 * Created by Giota on 31/12/2016.
 */
public class Subscription {
    private SubscriptionType duration;
    private double amount;
    private Date registrationDate;
    private Date expirationDate;
    private Date paymentDate;

    public Subscription(SubscriptionType duration, double amount, Date registrationDate, Date expirationDate, Date paymentDate) {
        this.duration = duration;
        this.amount = amount;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
        this.paymentDate = paymentDate;
    }

    public SubscriptionType getDuration() {
        return duration;
    }

    public void setDuration(SubscriptionType duration) {
        this.duration = duration;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return duration.getSubscriptionName() + " " + amount + " " + DateHelper.dateFormat(registrationDate) + " " + DateHelper.dateFormat(expirationDate) + " " + DateHelper.dateFormat(paymentDate);
    }
}

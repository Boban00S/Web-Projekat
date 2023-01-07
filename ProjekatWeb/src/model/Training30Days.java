package model;

import java.time.LocalDateTime;
import java.util.List;

public class Training30Days {
    private Integer id;
    private Customer customer;
    private Trainer trainer;
    private Training training;
    private List<LocalDateTime> last30Days;

    public Training30Days(Integer id) {
        this.id = id;
    }

    public Training30Days(Integer id, Customer customer, Trainer trainer, Training training, List<LocalDateTime> last30Days) {
        this.id = id;
        this.customer = customer;
        this.trainer = trainer;
        this.training = training;
        this.last30Days = last30Days;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public List<LocalDateTime> getLast30Days() {
        return last30Days;
    }

    public void setLast30Days(List<LocalDateTime> last30Days) {
        this.last30Days = last30Days;
    }
}

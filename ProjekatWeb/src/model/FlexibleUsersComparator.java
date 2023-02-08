package model;

import dao.CustomerDAO;

import java.time.LocalDateTime;
import java.util.Comparator;

public class FlexibleUsersComparator implements Comparator<User> {
    public enum Order {FirstName, LastName, Username, Points}

    private Order sortingBy = Order.FirstName;
    private boolean isAscending;
    private CustomerDAO customerDAO;

    public FlexibleUsersComparator(boolean isAscending, CustomerDAO customerDAO){
        this.isAscending = isAscending; this.customerDAO = customerDAO;
    }

    @Override
    public int compare(User u1, User u2) {
        switch (sortingBy){
            case FirstName:
            {
                if(this.isAscending)
                    return u1.getName().compareTo(u2.getName());
                else
                    return u2.getName().compareTo(u1.getName());
            }
            case LastName: {
                if(this.isAscending)
                    return u1.getLastName().compareTo(u2.getLastName());
                else
                    return u2.getLastName().compareTo(u1.getLastName());
            }
            case Username: {
                if(this.isAscending)
                    return u1.getUsername().compareTo(u2.getUsername());
                else
                    return u2.getUsername().compareTo(u1.getUsername());
            }
            case Points: {
                Float p1 = getUserPoints(u1);
                Float p2 = getUserPoints(u2);
                if(this.isAscending)
                {
                    return p1.compareTo(p2);
                }
                else{
                    return p2.compareTo(p1);
                }
            }
        }
        throw new RuntimeException("Practically unreachable code, can't be thrown");
    }

    public void setSortingBy(String sortBy){
        switch (sortBy){
            case "FirstName" -> this.sortingBy = Order.FirstName;
            case "LastName" -> this.sortingBy = Order.LastName;
            case "Username" -> this.sortingBy = Order.Username;
            case "Points" -> this.sortingBy = Order.Points;
        }
    }
    public Float getUserPoints(User user){
        if(user.getRole() == Role.customer)
            return customerDAO.findPointsById(user.getId());
        else
            return 0f;
    }
}
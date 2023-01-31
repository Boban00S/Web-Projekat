package model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class FlexibleTrainerTrainingsComparator implements Comparator<Training> {
    public enum Order {Name, Price, Date}

    private Order sortingBy = Order.Name;
    private boolean isAscending;

    public FlexibleTrainerTrainingsComparator(boolean isAscending){
        this.isAscending = isAscending;
    }

    @Override
    public int compare(Training o1, Training o2) {
        switch (sortingBy){
            case Name:
            {
                if(this.isAscending)
                    return o1.getSportsObject().getName().compareTo(o2.getSportsObject().getName());
                else
                    return o2.getSportsObject().getName().compareTo(o1.getSportsObject().getName());
            }
            case Price: {
                if(this.isAscending)
                    return o1.getPrice().compareTo(o2.getPrice());
                else
                    return o2.getPrice().compareTo(o1.getPrice());
            }
            case Date: {
                if(this.isAscending)
                    return compareDates(o2.getDate(), o1.getDate());
                else
                    return compareDates(o1.getDate(), o2.getDate());
            }
        }
        throw new RuntimeException("Practically unreachable code, can't be thrown");
    }

    public int compareDates(LocalDateTime l1, LocalDateTime l2){
        if(l1.isBefore(l2))
            return 1;
        else if(l1.isEqual(l2))
            return 0;
        else
            return -1;
    }
    public void setSortingBy(String sortBy){
        switch (sortBy){
            case "Name" -> this.sortingBy = Order.Name;
            case "Price" -> this.sortingBy = Order.Price;
            case "Date" -> this.sortingBy = Order.Date;
        }
    }

}
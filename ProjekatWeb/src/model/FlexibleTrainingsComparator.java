package model;

import java.time.LocalDateTime;
import java.util.Comparator;

public class FlexibleTrainingsComparator implements Comparator<Training30Days> {
    public enum Order {Name, Price, Date}

    private Order sortingBy = Order.Name;

    private boolean isAscending;

    public FlexibleTrainingsComparator(boolean isAsending){this.isAscending = isAsending; }

    @Override
    public int compare(Training30Days o1, Training30Days o2) {
        switch (sortingBy){
            case Name:
            {
                if(this.isAscending)
                    return o1.getTraining().getSportsObject().getName().compareTo(o2.getTraining().getSportsObject().getName());
                else
                    return o2.getTraining().getSportsObject().getName().compareTo(o1.getTraining().getSportsObject().getName());
            }
            case Price: {
                if(this.isAscending)
                    return o1.getTraining().getPrice().compareTo(o2.getTraining().getPrice());
                else
                    return o2.getTraining().getPrice().compareTo(o1.getTraining().getPrice());
            }
            case Date: {
                if(this.isAscending)
                    return compareDates(o2.getTraining().getDate(), o1.getTraining().getDate());
                else
                    return compareDates(o1.getTraining().getDate(), o2.getTraining().getDate());
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
            case "Name" -> this.sortingBy = FlexibleTrainingsComparator.Order.Name;
            case "Price" -> this.sortingBy = FlexibleTrainingsComparator.Order.Price;
            case "Date" -> this.sortingBy = FlexibleTrainingsComparator.Order.Date;
        }
    }
}
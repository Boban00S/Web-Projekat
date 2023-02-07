package model;

import dao.SportsObjectDAO;

import java.util.Comparator;

public class FlexibleCentersComparator implements Comparator<SportsObject> {
    public enum Order {Name, City, Country, AverageRate}

    private Order sortingBy = Order.Name;
    private boolean isAscending;

    public FlexibleCentersComparator(boolean isAscending){this.isAscending = isAscending;}

    @Override
    public int compare(SportsObject sportsObject1, SportsObject sportsObject2){
        switch (sortingBy){
            case Name: {
                    if(this.isAscending)
                        return sportsObject1.getName().compareTo(sportsObject2.getName());
                    else
                        return sportsObject2.getName().compareTo(sportsObject1.getName());
            }
            case City: {
                if(this.isAscending)
                    return sportsObject1.getLocation().getPlace().compareTo(sportsObject2.getLocation().getPlace());
                else
                    return sportsObject2.getLocation().getPlace().compareTo(sportsObject1.getLocation().getPlace());
            }
            case Country:{
                if(this.isAscending)
                    return sportsObject1.getLocation().getCountry().compareTo(sportsObject2.getLocation().getCountry());
                else
                    return sportsObject2.getLocation().getCountry().compareTo(sportsObject1.getLocation().getCountry());
            }
            case AverageRate: {
                if(this.isAscending)
                    return sportsObject1.getAverageGrade().compareTo(sportsObject2.getAverageGrade());
                else
                    return sportsObject2.getAverageGrade().compareTo(sportsObject1.getAverageGrade());
            }
        }
        throw new RuntimeException("Practically unreachable code, can't be thrown");
    }

    public void setSortingBy(String sortBy) {
        switch (sortBy) {
            case "Name" -> this.sortingBy = Order.Name;
            case "City" -> this.sortingBy = Order.City;
            case "Country" -> this.sortingBy = Order.Country;
            case "Average Rate" -> this.sortingBy = Order.AverageRate;
        }
    }
}
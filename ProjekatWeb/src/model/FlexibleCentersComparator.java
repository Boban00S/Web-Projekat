package model;

import dao.SportsObjectDAO;

import java.util.Comparator;

public class FlexibleCentersComparator implements Comparator<SportsObject> {
    public enum Order {Name, City, Country, AverageRate}

    private Order sortingBy = Order.Name;

    @Override
    public int compare(SportsObject sportsObject1, SportsObject sportsObject2){
        switch (sortingBy){
            case Name: return sportsObject1.getName().compareTo(sportsObject2.getName());
            case City: return sportsObject1.getLocation().getPlace().compareTo(sportsObject2.getLocation().getPlace());
            case Country: return sportsObject1.getLocation().getCountry().compareTo(sportsObject2.getLocation().getCountry());
            case AverageRate: return sportsObject1.getAverageGrade().compareTo(sportsObject2.getAverageGrade());
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
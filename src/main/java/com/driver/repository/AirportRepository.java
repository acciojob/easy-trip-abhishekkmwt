package com.driver.repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {
    private HashMap<String,Airport> airportDB;
    private HashMap<Integer, Flight> flightDB;
    private HashMap<Integer, Passenger> passengerDB;
    private HashMap<Integer,List<Integer>> bookedTickedDB;

    public AirportRepository() {
        this.airportDB = new HashMap<>();
        this.passengerDB=new HashMap<>();
        this.flightDB=new HashMap<>();
        this.bookedTickedDB=new HashMap<>();
    }

    public void addAirport(Airport airport){
       airportDB.put(airport.getAirportName(),airport);
    }

    public String getLargestAirportName(){
       int max=0;
       for(Map.Entry<String,Airport> entry : airportDB.entrySet()){
           if(entry.getValue().getNoOfTerminals() > max){
               max = entry.getValue().getNoOfTerminals();
           }
       }

       List<String> ans= new ArrayList<>();
       for(Map.Entry<String,Airport> entry : airportDB.entrySet()){
           if(entry.getValue().getNoOfTerminals() == max){
               ans.add(entry.getKey());
           }
       }

       if(ans.size() > 1){
           Collections.sort(ans, new Comparator<String>() {
               @Override
               public int compare(String s1, String s2) {
                   return s1.compareToIgnoreCase(s2);
               }
           });
           return ans.get(0);
        }
       return ans.get(0);
    }

    public void addFlight(Flight flight){
        flightDB.put(flight.getFlightId(), flight);
    }

    public void addPassenger(Passenger passenger){
        passengerDB.put(passenger.getPassengerId(), passenger);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double mini=-1;
        for(Map.Entry<Integer,Flight> entry : flightDB.entrySet()){
            if(entry.getValue().getFromCity()==fromCity && entry.getValue().getToCity()==toCity){
                mini = entry.getValue().getDuration();
            }
        }
        return mini;
    }

    public String bookATicket(Integer flightId, Integer passengerId){
        if(flightDB.containsKey(flightId)){
            int maxCapacity=flightDB.get(flightId).getMaxCapacity();
            List<Integer> passengers=bookedTickedDB.get(flightId);
            if(bookedTickedDB.get(flightId) == null){
                passengers=new ArrayList<>();
            }
            if(bookedTickedDB.get(flightId).size() < maxCapacity){
                if(bookedTickedDB.get(flightId).contains(passengerId)){
                    return "FAILURE";
                }
                else{
                    passengers.add(passengerId);
                }
                bookedTickedDB.put(flightId,passengers);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        int ans=0;
        if(airportDB.containsKey(airportName)){
            Airport airport=airportDB.get(airportName);
            City fromCity=airport.getCity();
            ans=0;
            for(Map.Entry<Integer,Flight> entry:flightDB.entrySet()){
                if(entry.getValue()!=null && entry.getValue().getFromCity().equals(fromCity) && entry.getValue().getFlightDate()==date){
                    if(bookedTickedDB.get(entry.getKey())!=null) ans+=bookedTickedDB.get(entry.getKey()).size();
                }
                if(entry.getValue().getToCity().equals(fromCity) && entry.getValue().getFlightDate()==date){
                    if(bookedTickedDB.get(entry.getKey())!=null) ans+=bookedTickedDB.get(entry.getKey()).size();
                }
            }
        }
        return ans;
    }

    public int calculateFlightFare(Integer flightId){
        int price=0;
        if(bookedTickedDB.containsKey(flightId)){
            int noOfPeopleWhoHaveAlreadyBooked =0;
            if(bookedTickedDB.get(flightId) != null){
                noOfPeopleWhoHaveAlreadyBooked = bookedTickedDB.get(flightId).size();
            }
            price= 3000 + (noOfPeopleWhoHaveAlreadyBooked*50);
        }
        return price;
    }

    public String cancelATicket(Integer flightId, Integer passengerId){
        if(bookedTickedDB.containsKey(flightId)) {
            if(bookedTickedDB.get(flightId)!=null && bookedTickedDB.get(flightId).contains(passengerId)){
                bookedTickedDB.get(flightId).remove(passengerId);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
         int count=0;
         for(Map.Entry<Integer,List<Integer>> entry : bookedTickedDB.entrySet()){
             if(entry.getValue()!=null && entry.getValue().contains(passengerId)){
                 count++;
             }
         }
         return count;
    }

    public int calculateRevenueOfAFlight(int flightId){
        int revenue =0;
        if(bookedTickedDB.containsKey(flightId)){
            if(bookedTickedDB.get(flightId)!=null){
                int totalPassengersBooked=bookedTickedDB.get(flightId).size();
                for(int i=0;i<totalPassengersBooked;i++){
                    revenue+=50*(60+i);
                }
            }
        }
        return revenue;
    }

    public String getAirportNameFromFlightId(Integer flightId){
        if(flightDB.containsKey(flightId)){
            City fromCity=flightDB.get(flightId).getFromCity();
            for(Map.Entry<String,Airport> entry : airportDB.entrySet()){
                if(entry.getValue().getCity().equals(fromCity)){
                    String airportName = entry.getKey();
                    return airportName;
                }
            }
        }
        return null;
    }
}

package com.driver.service;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AirportService {
    AirportRepository airportRepository=new AirportRepository();

    public void addAirport(Airport airport){
        airportRepository.addAirport(airport);
    }

    public String getLargestAirportName(){
       return airportRepository.getLargestAirportName();
    }

    public void addFlight(Flight flight){
        airportRepository.addFlight(flight);
    }

    public void addPassenger(Passenger passenger){
        airportRepository.addPassenger(passenger);
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String bookATicket(int flightId, int passengerId){
        return airportRepository.bookATicket(flightId,passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(int flightId){
        return airportRepository.calculateFlightFare(flightId);
    }

    public String cancelATicket(int flightId, int passengerId){
        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId){
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int calculateRevenueOfAFlight(int flightId){
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

    public String getAirportNameFromFlightId(int flightId){
        return airportRepository.getAirportNameFromFlightId(flightId);
    }
}

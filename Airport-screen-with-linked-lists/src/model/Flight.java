package model;

import java.time.LocalDate;

public class Flight implements Comparable<Flight>{

	private LocalDate date;
	private String hour;
	private String airline;
	private int flightNumber;
	private String destination;
	private String gate;

	
	private Flight next;
	private Flight prev;
	
	
	public Flight(LocalDate date, String hour, String airline, int flightNumber, String destination,
			String gate) {
		this.date = date;
		this.hour = hour;
		this.airline = airline;
		this.flightNumber = flightNumber;
		this.destination = destination;
		this.gate = gate;
	}


	
	
	public Flight getNext() {
		return next;
	}

	
	public void setNext(Flight next) {
		this.next = next;
	}

	
	public Flight getPrev() {
		return prev;
	}


	
	public void setPrev(Flight prev) {
		this.prev = prev;
	}

	
	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}




	public String getHour() {
		return hour;
	}



	public void setHour(String hour) {
		this.hour = hour;
	}



	public String getAirline() {
		return airline;
	}


	public void setAirline(String airline) {
		this.airline = airline;
	}


	public int getFlightNumber() {
		return flightNumber;
	}



	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}



	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getGate() {
		return gate;
	}


	public void setGate(String gate) {
		this.gate = gate;
	}


	@Override
	public int compareTo(Flight next) {
		return date.compareTo(next.getDate());
	}



	public int compareToDestination(Flight current) {
		return destination.compareTo(current.getDestination());
	}



	public int compareToFN(Flight o2) {
		int msg = 0;
		if(flightNumber < o2.getFlightNumber()) {
			msg = -1;
		}else if(flightNumber > o2.getFlightNumber()) {
			msg = 1;
		}else {
			msg = 0;
		}
		return msg;
	}



	public int compareToAirline(Flight toInsert) {
		return airline.compareTo(toInsert.getAirline());
	}



	public int compareToGate(Flight current) {
		return gate.compareTo(current.getGate());

	}


}

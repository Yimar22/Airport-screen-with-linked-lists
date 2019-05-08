package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Flight;

public class Airport {


	private static final String AIRLINE_PATH = "documents/airlines.txt";
	private static final String CITIES_PATH = "documents/cities.txt";
	public enum SortingsTypes {
		TIME,DATE, AIRLINE,FLIGHT_NUMBER, DESTINATION, GATE;
	}

	private SortingsTypes currentSortType;

	private Flight first;

	public Airport() {

		currentSortType = SortingsTypes.TIME;
	}


	public Flight getFirst() {
		return first;
	}


	public void setFirst(Flight first) {
		this.first = first;
	}


	@SuppressWarnings("null")
	public void generateFlights(int flightsNumber) throws IOException{
		int i = 0;
		Flight current = null;
	//	current = first;
		while(i<flightsNumber) {
			LocalDate date = generateDate();
			String hour = generateHour();
			String airlines = generateAirline();
			int fn = generateFlightNumber(flightsNumber);
	//		verifyFlightNumber(flightsNumber);
			String destination= generateDestination();
			String gate= generateGate();
		//	Flight current = null;
			Flight f = new Flight(date, hour, airlines, fn, destination, gate);

			if(first==null) {
				first=f;
	//			current = first;
			}else {
				current = first;
				while (current.getNext()!=null) {
					current = current.getNext();
				}
				current.setNext(f);
				f.setPrev(current);

			}
			i++;
		}
		setSortingsTypes(SortingsTypes.TIME);
		sort();

	}

	public LocalDate generateDate() {

		long minDay = LocalDate.of(2018, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		return randomDate;
	}

	public String generateHour() {
		String msg = "";
		LocalTime medium = LocalTime.of(12,0);
		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			LocalTime time = LocalTime.of(random.nextInt(23), random.nextInt(23));
			if(time.compareTo(medium)>0) {
				time = time.minus(12, ChronoUnit.HOURS);
				msg = time + "PM";
			}else {
				msg = time + "AM";
			}
		}
		return msg;
	}


	public String getAirlines(int airlineNumber) throws IOException {
		File f = new File(AIRLINE_PATH);
		BufferedReader br = new BufferedReader(new FileReader(f));
		int i = 0;
		String msg = br.readLine();
		while(i < airlineNumber) {
			msg = br.readLine();
			i++;
		}
		br.close();
		return msg;
	}

	public String generateAirline() throws IOException {
		Random rnd = new Random();
		String airline = getAirlines(rnd.nextInt(100));
		return airline;
	}

	public int generateFlightNumber(int flightsNumber) {
		Random rnd = new Random();
		int flightNumber = (int)(rnd.nextInt(flightsNumber));
		//verifyFlightNumber(flightsNumber);
		return flightNumber;
	}

	/*public void verifyFlightNumber(int flightsNumber) {
		for(int i=0;i<flights.size();i++) {
			int id1 = flights.get(i).getFlightNumber();
			for(int j=i+1;j<flights.size();j++) {
				int id2 = flights.get(j).getFlightNumber();
				if(id1 == id2) {
					flights.get(j).setFlightNumber(generateFlightNumber(flightsNumber));
				}
			}
		}
	}*/

	public String getDestination(int destinationNumber) throws IOException {
		File f = new File(CITIES_PATH);
		BufferedReader br = new BufferedReader(new FileReader(f));
		int i = 0;
		String msg = br.readLine();
		while(i < destinationNumber) {
			msg = br.readLine();
			i++;
		}
		br.close();
		return msg;
	}
	public String generateDestination() throws IOException {
		Random rnd = new Random();
		String airline = getDestination(rnd.nextInt(100));
		return airline;
	} 

	public String generateGate() {
		int gateNumber=(int)(Math.random()*10-1);
		gateNumber++;

		String gate=""+gateNumber;

		return gate;
	}

	public void setSortingsTypes(SortingsTypes st) {
		currentSortType = st;
	}


	public void sort() {
		switch(currentSortType) {
		case TIME:
			sortTime();
			break;
		case DATE:
			sortDate();
		case AIRLINE:
			sortAirline();
			break;
		case FLIGHT_NUMBER:
			sortFN();
			break;
		case DESTINATION:
			sortDestination();
			break;
		case GATE:
			sortGates();
			break;

		}
	}


	


	public void sortTime() {
		Flight current = first;
		while(current != null) {
			Flight temp = current.getNext();
			Flight initial = current;
			while(temp != null) {
				if(temp.compareTo(initial) >= 0) {
					initial = temp;
				}
				temp = temp.getNext();
			}
			boolean firstOne = false;
			if(initial != current) {
				Flight next = current.getNext();
				Flight previous = current.getPrev();

				Flight nextto = initial.getNext();
				Flight previousto = initial.getPrev();

				if(initial == current.getNext()	) {
					if(previous != null) previous.setNext(initial);
					else {
						first = initial;
						firstOne = true;
					}
					current.setNext(nextto);
					current.setPrev(initial);
					if(nextto != null) nextto.setPrev(current);
					initial.setNext(current);
					initial.setPrev(previous);
				} else {
					if(next != null) next.setPrev(initial);
					if(previous != null) previous.setNext(initial);
					else {
						first = initial;
						firstOne = true;
					}

					initial.setNext(next);
					initial.setPrev(previous);

					if(nextto != null) nextto.setPrev(current);
					if(previousto != null) previousto.setNext(current);

					current.setNext(nextto);
					current.setPrev(previousto);
				}
				current = initial;
			}

			if(firstOne) {
				current = first.getNext();
			} else {
				current = current.getNext();
			}
		}

	}

	public void sortDate() {

	}
	public void sortAirline() {
		if(first.getNext() != null) {
			Flight current = first.getNext();
			while(current != null) {
				Flight temp = current;
				while(temp.getPrev() != null) {
					if(temp.getAirline().compareTo(temp.getPrev().getAirline())<0) {
						if(temp.getPrev() == first) first = temp;
						Flight next = temp.getNext();
						Flight prev = temp.getPrev().getPrev();
						if(next != null) next.setPrev(temp.getPrev());
						if(prev != null) prev.setNext(temp);
						temp.setNext(temp.getPrev());
						temp.getPrev().setPrev(temp);
						temp.getPrev().setNext(next);
						temp.setPrev(prev);
					} else {
						temp = temp.getPrev();
					}
				}
				current = current.getNext();
			}
		}
	}

	public void sortDestination() {
		Flight current = first;
		while(current != null) {
			Flight temp = current.getNext();
			Flight initial = current;
			while(temp != null) {
				if(temp.getDestination().compareTo(initial.getDestination()) <= 0) {
					initial = temp;
				}
				temp = temp.getNext();
			}
			boolean firstIt = false;
			if(initial != current) {
				Flight next1 = current.getNext();
				Flight previous1 = current.getPrev();

				Flight next2 = initial.getNext();
				Flight previous2 = initial.getPrev();

				if(initial == current.getNext()	) {
					if(previous1 != null) previous1.setNext(initial);
					else {
						first = initial;
						firstIt = true;
					}
					current.setNext(next2);
					current.setPrev(initial);
					if(next2 != null) next2.setPrev(current);
					initial.setNext(current);
					initial.setPrev(previous1);
				} else {
					if(next1 != null) next1.setPrev(initial);
					if(previous1 != null) previous1.setNext(initial);
					else {
						first = initial;
						firstIt = true;
					}

					initial.setNext(next1);
					initial.setPrev(previous1);

					if(next2 != null) next2.setPrev(current);
					if(previous2 != null) previous2.setNext(current);

					current.setNext(next2);
					current.setPrev(previous2);
				}
				current = initial;
			}

			if(firstIt) {
				current = first.getNext();
			} else {
				current = current.getNext();
			}
		}
	}
	public void sortFN() {
		if(first.getNext() != null) {
			Flight current = first.getNext();
			while(current != null) {
				Flight temp = current;
				while(temp.getPrev() != null) {
					if(temp.getFlightNumber()<temp.getPrev().getFlightNumber()) {
						if(temp.getPrev() == first) first = temp;
						Flight next = temp.getNext();
						Flight prev = temp.getPrev().getPrev();
						if(next != null) next.setPrev(temp.getPrev());
						if(prev != null) prev.setNext(temp);
						temp.setNext(temp.getPrev());
						temp.getPrev().setPrev(temp);
						temp.getPrev().setNext(next);
						temp.setPrev(prev);
					} else {
						temp = temp.getPrev();
					}
				}
				current = current.getNext();
			}
		}
	}
	
	private void sortGates() {
		if(first.getNext() != null) {
			Flight current = first.getNext();
			while(current != null) {
				Flight temp = current;
				while(temp.getPrev() != null) {
					if(temp.getGate().compareTo(temp.getPrev().getGate())<0) {
						if(temp.getPrev() == first) first = temp;
						Flight next = temp.getNext();
						Flight prev = temp.getPrev().getPrev();
						if(next != null) next.setPrev(temp.getPrev());
						if(prev != null) prev.setNext(temp);
						temp.setNext(temp.getPrev());
						temp.getPrev().setPrev(temp);
						temp.getPrev().setNext(next);
						temp.setPrev(prev);
					} else {
						temp = temp.getPrev();
					}
				}
				current = current.getNext();
			}
		}
	}

	public Flight search(SortingsTypes criteria, String parameter) throws NumberFormatException, IndexOutOfBoundsException{
		Flight found = null;
		switch(criteria) {
		case AIRLINE:
			found = searchAirline(parameter);
			break;
		case DESTINATION:
			found = searchDestination(parameter);
			break;
		case FLIGHT_NUMBER:
			int fn = Integer.parseInt(parameter);
			found = searchFN(fn);
			break;
		case GATE:
			found = searchGate(parameter);
			break;			
		}
		return found;

	}

	public Flight searchAirline(String al){
		Flight current = first;
		while(current!=null) {
			if (current.getAirline().compareTo(al)==0) {
				return current;
			}else {
				current = current.getNext();
			}
		}
		return null;
	}

	public Flight searchDestination(String city) {
		Flight current = first;
		while(current!=null) {
			if (current.getDestination().compareTo(city)==0) {
				return current;
			}else {
				current = current.getNext();
			}
		}
		return null;
	}

	public Flight searchFN(int flightNumber) {
		Flight current = first;
		while(current!=null) {
			if (current.getFlightNumber()==(flightNumber)) {
				return current;
			}else {
				current = current.getNext();
			}
		}
		return null;
	}

	public Flight searchGate(String g){
		Flight current = first;
		while(current!=null) {
			if (current.getGate().compareTo(g)==0) {
				return current;
			}else {
				current = current.getNext();
			}
		}
		return null;
	}



}

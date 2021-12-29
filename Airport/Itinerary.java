// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;
public class Itinerary
{
	boolean isfound=false;
	ArrayList<Flight> itinerary;
	
	Itinerary(boolean isfound){
		this.isfound=isfound;
	}
	
  // constructor
	Itinerary(ArrayList<Flight> itinerary, boolean isfound) {
		this.itinerary=itinerary;
		this.isfound=isfound;
	}

	public boolean isFound() {
		return isfound;
	}

	public void print() {
		if(isfound) {
		  Iterator<Flight> iter=itinerary.iterator();
		  while(iter.hasNext()) {
			  Flight f = iter.next();
			  f.print();
		  }
		  System.out.println();
		}
		else {
			System.out.println("No Flight Schedule Found.");
		}
	}

}

// Bongki Moon (bkmoon@snu.ac.kr)
import java.util.*;
import java.util.LinkedList;

public class Planner {

	HashMap<String,Integer> indexing = new HashMap<String,Integer>();
	ArrayList<LinkedList<Airport>> vertex = new ArrayList<LinkedList<Airport>>();
	HashMap<String,LinkedList<Flight>> edge = new HashMap<String,LinkedList<Flight>>();
	
	int size=0;
	int M=100000;
	
  // constructor
  public Planner(LinkedList<Airport> portList, LinkedList<Flight> fltList) {
	  Iterator<Airport> port_iter = portList.iterator();
	  Iterator<Flight> flight_iter = fltList.iterator();
	  
	  LinkedList<Airport> tmp_airport;
	  String s_d;
	  LinkedList<Flight> tmp_flight;
	  
	  while(port_iter.hasNext()) {
		  Airport port=port_iter.next();
		  tmp_airport = new LinkedList<Airport>();
		  tmp_airport.add(port);
		  vertex.add(tmp_airport);
		  indexing.put(port.p, size);
		  size++;
	  }
	  int idx;
	  while(flight_iter.hasNext()) {
		  Flight flight = flight_iter.next();
		  idx=indexing.get(flight.s);
		  vertex.get(idx).add(vertex.get(indexing.get(flight.d)).get(0));
		  
		  s_d = flight.s.concat(flight.d);
		  
		  if(edge.containsKey(s_d)) {
			  edge.get(s_d).add(flight);
		  }
		  else {
			  tmp_flight = new LinkedList<Flight>();
			  tmp_flight.add(flight);
			  edge.put(s_d,tmp_flight);
		  }
	  }
  }

  public Itinerary Schedule(String start, String end, String departure) {
	  
	  if(!indexing.containsKey(start)||!indexing.containsKey(end)) {
		  Itinerary itinerary=new Itinerary(false);
		  return itinerary;
	  }
	  
	  ArrayList<Integer> check = new ArrayList<Integer>();
	  ArrayList<Integer> not_checked = new ArrayList<Integer>();
	  
	  int[] d = new int[size];
	  HashMap<Integer,Flight> f = new HashMap<Integer,Flight>();
	  
	  int departtime=Integer.parseInt(departure.substring(0,2))*60+Integer.parseInt(departure.substring(2,4));
	  int start_idx=indexing.get(start);
	  check.add(start_idx);
	  LinkedList<Airport> start_linked_list=vertex.get(start_idx);
	  
	  String next;
	  String start_next;
	  LinkedList<Flight> start_next_flight;
	  ArrayList<Flight> flight_order=new ArrayList<Flight>();
	  
	  for(int i=0;i<size;i++) {
		  if(i==start_idx) {
			  continue;
		  }
		  else {
			  not_checked.add(i);
			  Airport tmp_airport=vertex.get(i).get(0);
			  if(start_linked_list.contains(tmp_airport)) {
				  next = tmp_airport.p;
				  start_next=start.concat(next);
				  start_next_flight=edge.get(start_next);
				  
				  int[] duration_flight=min_duration(departtime,start_next_flight);
				  d[i]=duration_flight[1]+tmp_airport.connecttime;
				  f.put(i, start_next_flight.get(duration_flight[0]));
			  }
			  else {
				  d[i]=M;
			  }
		  }
	  }
	  while(!check.contains(indexing.get(end))) {
		  Iterator<Integer> not_checked_iter=not_checked.iterator();
		  int min_d=M;
		  int min_port_num=-1;
		  int port_num;
		  while(not_checked_iter.hasNext()) {
			  port_num = not_checked_iter.next();
			  if(d[port_num]<min_d) {
				  min_d=d[port_num];
				  min_port_num=port_num;
			  }
		  }
		  if(min_port_num==-1) {
			  Itinerary itinerary=new Itinerary(false);
			  return itinerary;
		  }
		  
		  if(!check.contains(min_port_num)) {
			  check.add(min_port_num);
		  }
		  if(not_checked.contains(min_port_num)) {
			  not_checked.remove(Integer.valueOf(min_port_num));
		  }
		  LinkedList<Airport> v_connected = vertex.get(min_port_num);
		  Iterator<Integer> not_checked_iter2=not_checked.iterator();
		  int new_departtime=(min_d+departtime)%1440;
		  int port_num2;
		  String str1=vertex.get(min_port_num).get(0).p;
		  String str2, str3;
		  while(not_checked_iter2.hasNext()) {
			  port_num2 = not_checked_iter2.next();
			  Airport port2=vertex.get(port_num2).get(0);
			  
			  if(v_connected.contains(port2)) {
				  str2=port2.p;
				  str3=str1.concat(str2);
				  LinkedList<Flight> new_flight_list=edge.get(str3);
				  int[] new_duration=min_duration(new_departtime,new_flight_list);
				  if(d[min_port_num]+new_duration[1]+port2.connecttime<d[port_num2]) {
					  d[port_num2]=d[min_port_num]+new_duration[1]+port2.connecttime;
					  f.put(port_num2, new_flight_list.get(new_duration[0]));
				  }
			  }
		  }
	  }
	  
	  int tmp_idx=indexing.get(end);
	  while(tmp_idx!=start_idx) {
		  Flight tmp_flight=f.get(tmp_idx);
		  flight_order.add(0,tmp_flight);
		  tmp_idx=indexing.get(tmp_flight.s);
	  }
	  
	  Itinerary itinerary=new Itinerary(flight_order,true);
	  return itinerary;
	  
  }
  
  public int[] min_duration(int departtime, LinkedList<Flight> flight_list){
	  
	  int min_time=M;
	  int timecost;
	  int pivot=0;
	  int[] return_list = new int[2];
	  Iterator<Flight> flight_iter = flight_list.iterator();
	  
	  int flight_srctime;
	  int flight_desttime;
	  while(flight_iter.hasNext()) {
		  Flight flight=flight_iter.next();
		  
		  flight_srctime=flight.srctime;
		  flight_desttime=flight.desttime;
		  
		  if(departtime<=flight_srctime){
	          if(flight_srctime<=flight_desttime) {
	        	  timecost=flight_desttime-departtime;
	          }
	          else {
	        	  timecost=flight_desttime+1440-departtime;
	          }
	      }
	      else{
	          if(flight_srctime<=flight_desttime) {
	        	  timecost=flight_desttime+1440-departtime;
	          }
	          else {
	        	  timecost=flight_desttime+2880-departtime;
	          }
	      }
		  if(timecost<min_time) {
			  min_time=timecost;
			  return_list[0]=pivot;
		  }
		  pivot++;
	  }
	  return_list[1]=min_time;
	  
      return return_list;
  }

}


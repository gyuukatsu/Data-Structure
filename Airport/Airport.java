// Bongki Moon (bkmoon@snu.ac.kr)

public class Airport
{
	String p;
	int hour;
	int minute;
	int connecttime;
	
  public Airport(String port, String connectTime) {
	this.p=port;
	hour=Integer.parseInt(connectTime.substring(0,2));
	if(hour>=24) {
		hour-=24;
	}
	minute=Integer.parseInt(connectTime.substring(2,4));
	this.connecttime=hour*60+minute;
  }	// constructor

  public void print() {
	  
  }

}

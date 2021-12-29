// Bongki Moon (bkmoon@snu.ac.kr)

public class Flight
{
	String s;
	String d;
	
	String str_stime;
	String str_dtime;
	
	int src_h;
	int src_m;
	int dest_h;
	int dest_m;
	
	int srctime;
	int desttime;
	
  // constructor
  public Flight(String src, String dest, String stime, String dtime) {
	this.s=src;
	this.d=dest;
	this.str_stime=stime;
	this.str_dtime=dtime;
	
	src_h=Integer.parseInt(stime.substring(0,2));
	src_m=Integer.parseInt(stime.substring(2,4));
	dest_h=Integer.parseInt(dtime.substring(0,2));
	dest_m=Integer.parseInt(dtime.substring(2,4));
	
	if(src_h>=24) {
		src_h-=24;
	}
	if(dest_h>=24) {
		dest_h-=24;
	}
	srctime=src_h*60+src_m;
	desttime=dest_h*60+dest_m;
  }

  public void print() {
	  System.out.printf("[%s->%s:%s->%s]",s,d,str_stime,str_dtime);
  }

}

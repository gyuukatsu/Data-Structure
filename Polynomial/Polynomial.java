// Skeleton of the Polynomial ADT
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

public class Polynomial {
	HashMap<Integer,Integer> poly=new HashMap<Integer,Integer>();
  // Create an empty polynomial
  public Polynomial() {
  }
  // Create a single-term polynomial
  public Polynomial(int coef, int exp) {
	  poly.put(exp,coef);
  }

  // Add opnd to 'this' polynomial; 'this' is returned
  public Polynomial add(Polynomial opnd) {
	  for(Integer i : opnd.poly.keySet()) {
		  if(this.poly.containsKey(i)) {
			  int result=this.poly.get(i)+opnd.poly.get(i);
			  if(result!=0) {
				  this.poly.put(i,result);
			  }
			  else {
				  this.poly.remove(i);
			  }
		  }
		  else {
			  if(opnd.poly.get(i)!=0){
				  this.poly.put(i, opnd.poly.get(i));
			  }
		  }
	  }
	  return this;
  
  }

  // Subtract opnd from 'this' polynomial; 'this' is returned
  public Polynomial sub(Polynomial opnd) {
	  for(Integer i : opnd.poly.keySet()) {
		  if(this.poly.containsKey(i)) {
			  int result = this.poly.get(i)-opnd.poly.get(i);
			  if(result!=0) {
				  this.poly.put(i,result);
			  }
			  else {
				  this.poly.remove(i);
			  }
			  
		  }
		  else {
			  if(opnd.poly.get(i)!=0) {
				  this.poly.put(i, -opnd.poly.get(i));
			  }
		  }
	  }
	  return this;
  
  }

  // Print the terms of 'this' polynomial in decreasing order of exponents.
  // No pair of terms can share the same exponent in the printout.
  public void print() {
	  TreeMap<Integer, Integer> tm=new TreeMap<Integer,Integer>(this.poly);
	  
	  Iterator<Integer> keyiterator=tm.descendingKeySet().iterator();
	  int k;
	  while(keyiterator.hasNext()) {
		  k=keyiterator.next();
		  System.out.print(this.poly.get(k));
		  System.out.print(" ");
		  System.out.print(k);
		  System.out.print(" ");
	  }
	  
  }
}

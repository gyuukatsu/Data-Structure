import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  
public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
    
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");
  
    String sign="";
    byte[] byteArray=new byte[200];
    int len = 0;
    
    public BigInteger(String s)
    {
    	len=s.length();
    	for(int i=0 ; i<len;i++) {
			byteArray[i]=(byte)(s.charAt(i)-'0');
		}
    }
    
    public int compare(BigInteger big) {
    	if(len<big.len) {
    		return -1; 
    	}
    	else if(len>big.len) {
    		return 1;
    	}
    	else {
    		for(int i=0;i<len;i++) {
    			if(byteArray[i]<big.byteArray[i]) {return -1;}
    			else if(byteArray[i]>big.byteArray[i]) {return 1;}
    		}
    		return 0;
    	}
    }
    
    public BigInteger add(BigInteger big)
    {
    	byte[] addresult=new byte[101];
    	int digit=0, i=len-1, j=big.len-1, r=0;
    	String result="";
    	while(true) {
    		if(i>=0 && j>=0) {
    			addresult[digit]=(byte)((byteArray[i]+big.byteArray[j]+r)%10);
    			r=(byteArray[i]+big.byteArray[j]+r)/10;
    			digit++;
    			i--;
    			j--;
    		}
    		else if(i>=0 && j==-1) {
    			addresult[digit]=(byte)((byteArray[i]+r)%10);
    			r=(byteArray[i]+r)/10;
    			digit++;
    			i--;
    		}
    		else if(i==-1 && j>=0) {
    			addresult[digit]=(byte)((big.byteArray[j]+r)%10);
    			r=(big.byteArray[j]+r)/10;
    			digit++;
    			j--;
    		}
    		else {
    			addresult[digit]=(byte)(r%10);
    			break;}
    	}
    	while(addresult[digit]==0 && digit>=1) {digit--;}
    	for(int k=digit;k>=0;k--) {
    		result=result+addresult[k];
    	}
    	BigInteger op_result=new BigInteger(result);
    	return op_result;
    }
  
    public BigInteger subtract(BigInteger big)
    {
    	byte[] subresult=new byte[100];
    	int digit=0, i=len-1, j=big.len-1, r=0;
    	String result="";
    	while(true) {
    		if(i>=0 && j>=0) {
    			if(byteArray[i]>=big.byteArray[j]+r) {
    				subresult[digit]=(byte)(byteArray[i]-big.byteArray[j]-r);
    				r=0;
    				digit++;
    				i--;
    				j--;
    			}
    			else {
    				subresult[digit]=(byte)(10+byteArray[i]-big.byteArray[j]-r);
    				r=1;
    				digit++;
    				i--;
    				j--;
    			}
    		}
    		else if(i>=0 && j==-1) {
    			if(byteArray[i]>=r) {
    				subresult[digit]=(byte)(byteArray[i]-r);
    				r=0;
    				digit++;
    				i--;
    			}
    			else {
    				subresult[digit]=(byte)(10+byteArray[i]-r);
    				r=1;
    				digit++;
    				i--;
    			}
    		}
    		else {break;}
    	}
    	while(subresult[digit-1]==0 && digit>1) {digit--;}
    	for(int k=digit-1;k>=0;k--) {
    		result=result+subresult[k];
    	}
    	BigInteger op_result=new BigInteger(result);
    	return op_result;
    }
  
    public BigInteger multiply(BigInteger big)
    {
    	byte[] multiresult = new byte[200];
    	int len1=len-1, len2=big.len-1;
    	int tot=len1+len2, i=len1, j=len2;
    	while(true) {
    		if(j>=0) {
    			for(i=len1 ; i>=0 ; i--) {
    				multiresult[tot+1-i-j]=(byte)((byteArray[i]*big.byteArray[j]+multiresult[tot-i-j])/10+multiresult[tot+1-i-j]);
    				multiresult[tot-i-j]=(byte)((byteArray[i]*big.byteArray[j]+multiresult[tot-i-j])%10);
    			}
    			j--;
    		}
    		else {break;}
    	}
    	while(multiresult[tot+1]==0 && tot>=0) {tot--;}
    	String result="";
    	for(i=tot+1 ; i>=0;i--) {
    		result=result+multiresult[i];
    	}
    	BigInteger op_result=new BigInteger(result);
    	return op_result;
    }
  
    @Override
    public String toString()
    {
    	String s ="";
    	for(int i=0;i<len;i++) {
    		s=s+byteArray[i];
    	}
    	return s;
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
  
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    	
    	String arg1="", arg2="", op="";
    	String[] sign = {"+","+"};
    	
    	input=input.replace(" ",""); //공백 제거
    	String[] input_array = input.split("");
    	
    	int init_piv=0, cnt=0;
    	if(input_array[0].equals("+")||input_array[0].equals("-")) {
    		sign[0]=input_array[0];
    		init_piv++;
    	}
    	for(int i=init_piv; i<input_array.length;i++) {
    		if((input_array[i].equals("+")||input_array[i].equals("-")||input_array[i].equals("*"))&&cnt==0) {
    			op=input_array[i];
    			cnt++;
    		}
    		else if((input_array[i].equals("+")||input_array[i].equals("-"))&&cnt==1) {
    			sign[1]=input_array[i];
    		}
    		else if(cnt==0){
    			arg1=arg1+input_array[i];
    		}
    		else if(cnt==1) {
    			arg2=arg2+input_array[i];
    		}
    	}
    	BigInteger num1 = new BigInteger(arg1);
    	BigInteger num2 = new BigInteger(arg2);
    	BigInteger op_result;
    	
    	if(op.equals("+")) {
    		if(sign[0].equals("+") && sign[1].equals("+")) {
    			op_result= num1.add(num2);
    		}
    		else if(sign[0].equals("-") && sign[1].equals("-")) {
    			op_result=num1.add(num2);
    			op_result.sign="-";
    		}
    		else if(sign[0].equals("-")&&sign[1].equals("+")){
    			if(num1.compare(num2)==1) {
    				op_result=num1.subtract(num2);
    				op_result.sign="-";
    			}
    			else {
    				op_result=num2.subtract(num1);
    			}
    		}
    		else{
    			if(num1.compare(num2)==-1) {
    				op_result=num2.subtract(num1);
    				op_result.sign="-";
    			}
    			else {
    				op_result=num1.subtract(num2);
    			}
    		}
    	}
    	else if(op.equals("-")) {
    		if(sign[0].equals("+") && sign[1].equals("+")) {
    			if(num1.compare(num2)==-1) {
    				op_result=num2.subtract(num1);
    				op_result.sign="-";
    			}
    			else {
    				op_result=num1.subtract(num2);
    			}
    		}
    		else if(sign[0].equals("-") && sign[1].equals("-")) {
    			if(num1.compare(num2)==1) {
    				op_result=num1.subtract(num2);
    				op_result.sign="-";
    			}
    			else {
    				op_result=num2.subtract(num1);
    			}
    		}
    		else if(sign[0].equals("-")&&sign[1].equals("+")){
    			op_result=num1.add(num2);
    			op_result.sign="-";
    		}
    		else{
    			op_result=num1.add(num2);
    		}
    		
    	}
    	else{
    		if(sign[0].equals(sign[1])) {
    			op_result=num1.multiply(num2);
    		}
    		else {
    			op_result=num1.multiply(num2);
    			op_result.sign="-";
    		}
    	}
    	return op_result;
    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result;
            result= evaluate(input);
            System.out.println(result.sign+result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}

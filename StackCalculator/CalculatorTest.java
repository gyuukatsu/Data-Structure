import java.io.*;
import java.util.Stack;

public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;
				
				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
				System.out.println("ERROR");
			}
		}
	}

	private static void command(String input)
	{
		//input에서 탭을 공백으로 변환
		input=input.replaceAll("\\t", " ");
		//input에서 문자열 앞 뒤의 공백을 제거
		input=input.trim();
		//input에서 연속적인 공백을 하나의 공백으로 변환
		StringBuffer sb= new StringBuffer(input);
		for(int i=0 ; i<sb.length() ; i++) {
			while(sb.charAt(i)==' ' && sb.charAt(i-1)==' ') {
				sb.deleteCharAt(i);
			}	
		}
		input=sb.toString();
		//괄호 개수 에러 처리
		int check=0;
		for(int i=0; i<input.length() ; i++) {
			if(input.charAt(i)=='(') {check++;}
			else if(input.charAt(i)==')') {check--;}
			//'('보다 ')'의 수가 더 많아지면 에러 처리
			if(check<0) {
				System.out.println("ERROR");
				return;
			}
		}
		//최종 '('의 수가 ')'의 수보다 많으면 에러 처리
		if(check>0) {
			System.out.println("ERROR");
			return;
		}
		//연속해서 숫자가 나오는 경우 에러 처리
		for(int i=0; i<input.length();i++) {
			if(i>=2 && input.charAt(i)>='0'&&input.charAt(i)<='9' && input.charAt(i-1)==' ' && input.charAt(i-2)>='0' && input.charAt(i-2)<='9') {
				System.out.println("ERROR");
                return;
			}
		}
		//입력받은 문자열을 숫자, 기호로 쪼개어 하나씩 String 배열에 저장
		int pivot=0;
		String[] input_array = new String[input.length()] ;
		for(int i=0;i<input.length();i++) {
			if(input.charAt(i)>='0' && input.charAt(i)<='9') {
				if(input_array[pivot]==null) {
					input_array[pivot]="";
				}
				input_array[pivot]=input_array[pivot]+input.charAt(i);
				//연속된 숫자인지 아닌지를 확인하고 pivot+1
				if(i<input.length()-1 && input.charAt(i+1)>='0' && input.charAt(i+1)<='9') {continue;}
				else {pivot++;}
			}
			else if(input.charAt(i)==' ') {
				continue;
			}
			else if(input.charAt(i)=='+'||input.charAt(i)=='-'||input.charAt(i)=='*'||input.charAt(i)=='/'||input.charAt(i)=='%'||input.charAt(i)=='^'||input.charAt(i)=='('||input.charAt(i)==')') {
				input_array[pivot]=""+input.charAt(i);
				pivot++;
			}
			else {
				System.out.println("ERROR");
				return;
			}
		}
		//postfix 식을 저장할 String 배열
		String[] postfix = new String[pivot+1];
		//연산자를 저장할 Stack
		Stack<String> op = new Stack<>();
		
		int cnt=0;
		//이전 원소가 숫자였는지 아니었는지를 저장하는 boolean형 변수
		boolean is_number=false;
		
		for(int i=0; i<pivot;i++) {
			//'('는 Stack에 그대로 저장
			if(input_array[i].equals("(")) {
				op.push(""+input_array[i]);
			}
			//')'는 '('가 나올 때까지 Stack에 있는 연산 꺼내어 postfix에 저장
			else if(input_array[i].equals(")")) {
				if(is_number==false) {
					System.out.println("ERROR");
					return;
				}
				else {
					while(op.isEmpty()==false) {
						if(op.peek().equals("(")) {
							op.pop();
							break;
						}
						else {
							postfix[cnt]=op.pop();
							cnt++;
						}
					}
					is_number=true;
				}
			}
			//'^'는 우선순위가 가장 높으므로 그대로 Stack에 저장
			else if(input_array[i].equals("^")) {
				op.push(input_array[i]);
				is_number=false;
			}
			//'*','/','%'는 더 우선순위가 낮은 연산이 나올 때까지 Stack의 연산을 postfix에 저장, 이후 Stack에 투입
			else if(input_array[i].equals("*")||input_array[i].equals("/")||input_array[i].equals("%")) {
				if(is_number==false) {
					System.out.println("ERROR");
					return;
				}
				else {
					while(op.isEmpty()==false&&(op.peek().compareTo("+")!=0&&op.peek().compareTo("-")!=0&&op.peek().compareTo("(")!=0)) {
						postfix[cnt]=op.pop();
						cnt++;
					}
					op.push(input_array[i]);
					is_number=false;
				}
			}
			//'+','-'(binary)는 우선순위가 가장 낮으므로 Stack에 있는 모든 연산을 postfix에 저장, 이후 Stack에 투입
			else if((input_array[i].equals("+")||input_array[i].equals("-"))&&is_number==true) {
				while(op.isEmpty()==false && op.peek().equals("(")==false) {
					postfix[cnt]=op.pop();
					cnt++;
				}
				op.push(input_array[i]);				
				is_number=false;
			}
			//'-'(unary)는 Stack에 '^'연산이 있다면 postfix에 저장, 이후 Stack에 투입
			else if(is_number==false && input_array[i].equals("-")) {
				while(op.isEmpty()==false && op.peek().equals("^")) {
					postfix[cnt]=op.pop();
					cnt++;
				}
				op.push("~");
				is_number=false;
			}
			//숫자이면 postfix에 저장
			else {
				if(is_number==false) {
					postfix[cnt]=input_array[i];
					cnt++;
					is_number=true;
				}
				//숫자가 연달아 나오는 경우 에러 처리
				else {
					System.out.println("ERROR");
					return;
				}
			}
			
		}
		//Stack에 남아있는 모든 연산을 postfix에 저장
		while(op.isEmpty()==false) {
			postfix[cnt]=op.pop();
			cnt++;
		}
		//postfix를 연산하기 위해 수를 저장하는 Stack
		Stack<String> pfeval = new Stack<>();
		
		long a,b=0;
		//기호의 종류에 따라 경우를 나누어 연산에 필요한 수들이 Stack에 존재하는지 확인, 없다면 에러 처리
		//아무 문제가 없다면 연산 실행하여 그 결과를 다시 Stack에 저장
		for(int i=0 ; i<cnt ; i++) {			
			if(postfix[i].equals("+")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						pfeval.push(Long.toString(a+b));
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
				
			}
			else if(postfix[i].equals("-")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						pfeval.push(Long.toString(a-b));
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
			}
			else if(postfix[i].equals("*")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						pfeval.push(Long.toString(a*b));
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
				
			}
			else if(postfix[i].equals("/")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(b==0) {
						System.out.println("ERROR");
						return;
					}
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						pfeval.push(Long.toString(a/b));
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
			}
			else if(postfix[i].equals("%")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(b==0) {
						System.out.println("ERROR");
						return;
					}
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						pfeval.push(Long.toString(a%b));
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
			}
			else if(postfix[i].equals("^")) {
				if(pfeval.isEmpty()==false) {
					b=Long.parseLong(pfeval.pop());
					if(b==0) {
						System.out.println("ERROR");
						return;
					}
					if(pfeval.isEmpty()==false) {
						a=Long.parseLong(pfeval.pop());
						if(a==0 && b<0) {
							System.out.println("ERROR");
							return;
						}
						else {
							pfeval.push(Long.toString((long)Math.pow(a, b)));
						}
					}
					else {
						System.out.println("ERROR");
						return;
					}
				}
				else {
					System.out.println("ERROR");
					return;
				}
			}
			else if(postfix[i].equals("~")) {
				if(pfeval.isEmpty()==false) {
					a=Long.parseLong(pfeval.pop());
					pfeval.push(Long.toString(-a));
				}
				else {
					System.out.println("ERROR");
					return;
				}
			}
			//숫자면 그대로 Stack에 투입
			else {
				pfeval.push(postfix[i]);
			}
		}
		for(int i=0;i<cnt;i++) {
			if(i!=0) {System.out.print(" ");}
			System.out.print(postfix[i]);
		}
		System.out.println();
		System.out.println(pfeval.pop());
	}
}
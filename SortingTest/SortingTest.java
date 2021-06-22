import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	
	//수업 시간에 배운 알고리즘에 따라 아래 코드들을 짰습니다.
	//코드 출처: 수업 자료
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		int tmp=0;
		for(int i = value.length-1 ; i>=0 ; i--) {
			for(int j = 0 ; j<i ; j++) {
				if(value[j]>value[j+1]) {
					tmp = value[j];
					value[j]=value[j+1];
					value[j+1]=tmp;
				}
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.
		int j=0, item=0;
		for(int i=1 ; i<value.length ; i++) {
			j = i-1;
			item = value[i];
			while(j>=0 && item <value[j]) {
				value[j+1]=value[j];
				j--;
			}
			value[j+1]=item;
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		buildheap(value, value.length);
		for(int i=value.length-1 ; i>=1 ; i--) {
			value[i]=deleteMax(value, i+1);
		}
		return (value);
	}
	
	private static void buildheap(int A[], int numItems)
	{
		if(numItems>=2) {
			for(int i = (numItems-2)/2 ; i>=0 ; i--) {
				percolateDown(A, i,numItems);
			}
		}
	}
	private static void percolateDown(int A[], int i, int numItems)
	{
		int child = 2*i+1;
		int rightchild = 2*i+2;
		if(child<=numItems-1) {
			if(rightchild<=numItems-1 && A[child]<A[rightchild]) {
				child = rightchild;
			}
			if(A[i]<A[child]) {
				int tmp = A[i];
				A[i]=A[child];
				A[child]=tmp;
				percolateDown(A,child,numItems);
			}
		}
	}
	
	private static int deleteMax(int A[], int numItems)
	{
		int max = A[0];
		A[0]=A[numItems-1];
		numItems--;
		percolateDown(A, 0, numItems);
		return max;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		mergeSort(value,0,value.length-1);
		return (value);
	}
	private static void mergeSort(int A[], int p, int r)
	{	
		int q=0;
		if(p<r) {
			q = (p+r)/2;
			mergeSort(A,p,q);
			mergeSort(A,q+1,r);
			merge(A,p,q,r);
		}
	}
	private static void merge(int A[], int p, int q, int r)
	{
		int i = p, j = q+1, t=0;
		int tmp[]= new int[r-p+1];
		while(i<=q && j<=r) {
			if(A[i]<=A[j]) {
				tmp[t++]=A[i++];
			}
			else {
				tmp[t++]=A[j++];
			}
		}
		while(i<=q) {
			tmp[t++]=A[i++];
		}
		while(j<=r) {
			tmp[t++]=A[j++];
		}
		i=p;
		t=0;
		while(i<=r){
			A[i++]=tmp[t++];
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		quickSort(value,0,value.length-1);
		return (value);
	}
	private static void quickSort(int A[],int p, int r)
	{
		int q=0;
		if(p<r) {
			q=partition(A,p,r);
			quickSort(A,p,q-1);
			quickSort(A,q+1,r);
		}
	}
	private static int partition(int A[],int p,int r)
	{
		int x=A[r], i=p-1, tmp=0;
		for(int j=p ; j<r ; j++) {
			if(A[j]<x) {
				tmp=A[j];
				A[j]=A[++i];
				A[i]=tmp;
			}
		}
		tmp=A[r];
		A[r]=A[i+1];
		A[i+1]=tmp;
		return i+1;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		
		Queue<Integer>[] bucket = new Queue[10];
		for(int i=0 ; i<10 ; i++) {
			bucket[i]=new LinkedList<>();
		}
		
		for(int i=0 ; i<10 ; i++) {
			for(int j=0 ; j<value.length ; j++) {
				long num = (long)value[j]+2147483648L;
				int cnt=0;
				while(cnt < i) {
					num = num/10L;
					cnt++;
				}
				num=num%10;
				bucket[(int)num].add(value[j]);
			}
			int pivot=0;
			for(int j=0 ; j<10 ; j++) {
				while(bucket[j].isEmpty()==false) {
					value[pivot]=bucket[j].peek();
					pivot++;
					bucket[j].remove();
				}
			}
		}
		return (value);
	}
}

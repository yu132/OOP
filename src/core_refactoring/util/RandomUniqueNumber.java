package core_refactoring.util;

import java.util.NoSuchElementException;
import java.util.Random;

public class RandomUniqueNumber {

	private int[] temp;
	private int length;
	private int allLength;
	
	static Random r=new Random();

	public RandomUniqueNumber(int from,int to) {
		if(from>to)
			throw new IllegalArgumentException("From can't be bigger than to");
		this.temp = new int[to-from+1];
		length=to-from+1;
		allLength=length;
		for(int i=from,j=0;i<=to;i++,j++)
			temp[j]=i;
	}
	
	public int getNum(){
		if(length==0)
			throw new NoSuchElementException("No more number");
		int l=r.nextInt(length);
		int n=temp[l];
		temp[l]=temp[--length];
		temp[length]=n;
		return n;
	}
	
	public void reSet(){
		length=allLength;
	}
	
	public static void main(String[] args) {
		RandomUniqueNumber r=new RandomUniqueNumber(0,10);
		for(int i=0;i<=10;i++)
			System.out.println(r.getNum());
		r.reSet();
		for(int i=0;i<=10;i++)
			System.out.println(r.getNum());
	}
}

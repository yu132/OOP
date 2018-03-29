package core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import core.interfaces.Card;
import core.interfaces.CardInitializer;
import core.interfaces.CardNumber;
import core.interfaces.CardType;

public class SimpleCardInitializerImpl implements CardInitializer{

	private int[][] notGiven=new int[4][13];
	
	private int[] eachNumber=new int[4];
	
	private Random r=new Random();
	
	public SimpleCardInitializerImpl() {
		for(int i=0;i<4;i++)
			for(int j=0;j<13;j++)
				notGiven[i][j]=j;
		for(int i=0;i<4;i++)
			eachNumber[i]=13;
	}

	@Override
	public Card getCard() {
		int count=0;
		for(int i=0;i<4;i++){
			if(eachNumber[i]!=0)
				count++;
		}
		if(count==0)
			throw new RuntimeException("No more card to give");
		
		int type=r.nextInt(count);
		
		for(int i=0;i<4;i++){
			if(eachNumber[i]==0)
				continue;
			if(type==0){
				int numberi=r.nextInt(eachNumber[i]);
				int number=notGiven[i][numberi];
				notGiven[i][numberi]=notGiven[i][--eachNumber[i]];
				return CardImpl.valueOf(CardNumber.values()[number],CardType.values()[i]);
			}else
				type--;
		}
		return null;
	}
	
	public static void main(String[] args) {
		SimpleCardInitializerImpl sci=new SimpleCardInitializerImpl();
		ArrayList<String> a=new ArrayList<>();
		for(int i=0;i<52;i++)
			a.add(sci.getCard().toString()+"\n");
		
		Collections.sort(a);
		
		System.out.println(a);
	}
}

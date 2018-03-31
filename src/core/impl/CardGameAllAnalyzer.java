package core.impl;

import java.util.ArrayList;
import java.util.Random;

import core.interfaces.Card;
import core.interfaces.CardGame.Components;
import core.interfaces.CardGameAnalyzer;
import core.interfaces.CardInitializer;
import core.interfaces.CardNumber;
import core.interfaces.CardType;
import core.interfaces.MoveState;

public class CardGameAllAnalyzer implements CardGameAnalyzer,CardInitializer{

	private static class SolvableCardGame{
		
		public static interface  Operation{}
		
		public static class NextOperation implements Operation{}
		
		public static class MoveOperation implements Operation{ 
			private Components from;
			private Components to;
			private int num;
		}
		
		static Random r=new Random();
		
		static void initHeap(ArrayList<Card> CardHeap[]){
			boolean f=true;
			for(int i=12;i>=0;i--){
				if(f){
					if(r.nextBoolean()){
						CardHeap[0].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
						CardHeap[1].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
					}else{
						CardHeap[0].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
						CardHeap[1].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
					}
					
					if(r.nextBoolean()){
						CardHeap[2].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
					}else{
						CardHeap[2].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
					}
					
					f=false;
				}else{
					if(r.nextBoolean()){
						CardHeap[0].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
						CardHeap[1].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
					}else{
						CardHeap[1].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
						CardHeap[0].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
					}
					
					if(r.nextBoolean()){
						CardHeap[2].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
					}else{
						CardHeap[2].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
					}
					
					f=true;
				}
			}
		}
		
		static boolean fromHeaptoHeap(ArrayList<Card> CardHeap[],int[] coverNumber,int from,int to,int num){
			if(CardHeap[to].size()!=coverNumber[to]){
				if(CardHeap[to].get(CardHeap[to].size()-1)
					.isStackableInHeap(CardHeap[from].get(CardHeap[from].size()-num-1))
					!=MoveState.SUCCESS)
					return false;
			}
			
			CardHeap[to].addAll(CardHeap[from]
					.subList(CardHeap[from].size()-num-1, CardHeap[from].size()-1));
			for(int i=0;i<num;i++){
				CardHeap[from].remove(CardHeap[from].size()-1);
			}
			return true;
			
		}
		
		static void coverHeapCard(ArrayList<Card> CardHeap[],int[] coverNumber,int x){
			if(CardHeap[x].size()==coverNumber[x]+1){
				coverNumber[x]++;
			}
		}
		
		static void fromHeapToDealer(){
			
		}
		
		static SolvableCardGame getASolvableCardGame(){
			
			ArrayList<Card>[] CardHeap = new ArrayList[7];
			
			int[] coverNumber=new int[7];
			
			for(int i=0;i<7;i++){
				CardHeap[i]=new ArrayList<>();
			}
			
			Card[][] tempDealer=new Card[8][3];
			
			
			
			
			return null;
		}
	}
	
	@Override
	public Card getCard(Components c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTips() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGameFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

}

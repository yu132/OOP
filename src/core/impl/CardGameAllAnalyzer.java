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
import core.util.RandomUniqueNumber;

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
			if(CardHeap[from].size()-coverNumber[from]<num)
				return false;
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
		
		static boolean fromHeapToDealer(ArrayList<Card> CardHeap[],int[] coverNumber,Card[][] tempDealer,int from,int to,int[] togiveNum){
			if(CardHeap[from].size()==coverNumber[from]){
				return false;
			}
			if(togiveNum[to]==0)
				return false;
			Card c=CardHeap[from].remove(CardHeap[from].size()-1);
			tempDealer[to][3-(togiveNum[to]--)]=c;
			return true;
		}
		
		static int organizeDealer(Card[][] tempDealer,int[] togiveNum,RandomUniqueNumber ru,int allDealerNum){
			
			int temp1=r.nextInt(100)+1;
			
			int orgNum;
			
			int temp;
			if(temp1<10)
				return 0;
			else if(temp1<70)
				temp=r.nextInt(5)+1;
			else if(temp1<90)
				temp=r.nextInt(5)+3;
			else
				temp=r.nextInt(5)+5;
			
			if(temp>allDealerNum)
				orgNum=allDealerNum;
			else
				orgNum=temp;
			allDealerNum-=orgNum;
			
			ru.reSet();
			int[] order=new int[8];
			for(int i=0;i<7;i++)
				order[i]=ru.getNum();
			
			int inxde1=0;
			int indexIn=0;
			
			Card[][] tempDealerNew=new Card[8][3];
			for(int i=0;i<7;i++){
				int next=r.nextInt(orgNum+1>4?4:orgNum+1);
				togiveNum[i]=next;
				orgNum-=next;
				for(int j=0;j<3-next;j++){
					if(indexIn==3){
						indexIn=0;
						inxde1++;
					}
					tempDealerNew[i][j]=tempDealer[inxde1][indexIn];
				}
			}
			
			tempDealer=tempDealerNew;
			
			return allDealerNum;
		}
		
		static SolvableCardGame getASolvableCardGame(){
			
			ArrayList<Card>[] CardHeap = new ArrayList[7];
			
			int[] coverNumber=new int[7];
			
			for(int i=0;i<7;i++){
				CardHeap[i]=new ArrayList<>();
			}
			
			int allDealerNum=24;
			
			RandomUniqueNumber r=new RandomUniqueNumber(0,7);
			
			Card[][] tempDealer=new Card[8][3];
			
			int[] togiveNum=new int[8];
			
			//TODO 这里继续做 hth htd
			
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

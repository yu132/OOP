package core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
		
		private final static Map<Integer,Components> heapMap=new HashMap<>();
		
		static {
			heapMap.put(0, Components.CARD_HEAP_1);
			heapMap.put(1, Components.CARD_HEAP_2);
			heapMap.put(2, Components.CARD_HEAP_3);
			heapMap.put(3, Components.CARD_HEAP_4);
			heapMap.put(4, Components.CARD_HEAP_5);
			heapMap.put(5, Components.CARD_HEAP_6);
			heapMap.put(6, Components.CARD_HEAP_7);
		}
		
		public static interface  Operation{}
		
		public static class NextOperation implements Operation{
			@Override
			public String toString() {
				return ""+getClass();
			}
		}
		
		public static class MoveOperation implements Operation{ 
			private Components from;
			private Components to;
			private int num;
			public MoveOperation(Components from, Components to, int num) {
				super();
				this.from = from;
				this.to = to;
				this.num = num;
			}
			@Override
			public String toString() {
				return getClass()+ " "+from + " " + to + " " + num   ;
			}
			
		}
		
		private ArrayList<Card>[] CardHeap;
		private ArrayList<Card> Dealer;
		private ArrayList<Operation> mvlist;
		
		public SolvableCardGame(ArrayList<Card>[] cardHeap, ArrayList<Card> dealer, ArrayList<Operation> mvlist) {
			super();
			CardHeap = cardHeap;
			Dealer = dealer;
			this.mvlist = mvlist;
		}
		
		public synchronized ArrayList<Card>[] getCardHeap() {
			return CardHeap;
		}

		public synchronized ArrayList<Card> getDealer() {
			return Dealer;
		}

		public synchronized ArrayList<Operation> getMvlist() {
			return mvlist;
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
			if(num<=0)
				return false;
			if(CardHeap[from].size()-coverNumber[from]<num)
				return false;
			if(CardHeap[to].size()!=coverNumber[to]){
				if(!CardHeap[to].isEmpty()&&CardHeap[to].get(CardHeap[to].size()-1)
					.isStackableInHeap(CardHeap[from].get(CardHeap[from].size()-num))
					!=MoveState.SUCCESS)
					return false;
			}
			
			CardHeap[to].addAll(CardHeap[from]
					.subList(CardHeap[from].size()-num, CardHeap[from].size()));
			for(int i=0;i<num;i++){
				CardHeap[from].remove(CardHeap[from].size()-1);
			}
			return true;
		}
		
		static void coverHeapCard(ArrayList<Card> CardHeap[],int[] coverNumber,int x){
			if(coverNumber[x]==x)
				return;
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
		
		static void organizeHeap(ArrayList<Card> CardHeap[],int[] coverNumber,ArrayList<Operation> mvlist){
			int[] need=new int[7];
			int[] togive=new int[7];
			boolean[] finish=new boolean[7];
			
			RandomUniqueNumber ruH1=new RandomUniqueNumber(0,6);
			RandomUniqueNumber ruH2=new RandomUniqueNumber(0,6);
			
			for(int i=0;i<7;i++){
				need[i]=i-coverNumber[i];
				togive[i]=CardHeap[i].size()-coverNumber[i];
				if(need[i]==0&&togive[i]==1)
					finish[i]=true;
			}
			
			while(true){
				
				boolean flag=true;
				for(int i=0;i<7;i++)
					if(!finish[i]){
						flag=false;
						break;
					}
				if(flag)
					break;
				
				boolean flagx=r.nextBoolean();
				int h1=0;
				int h2=0;
				ruH1.reSet();
				l:
				for(int x=0;x<6;x++){
					h1=ruH1.getNum();
					if(finish[h1])
						continue;
					if(togive[h1]<=1)
						continue;
					ruH2.reSet();
					for(int y=0;y<7;y++){
						h2=ruH2.getNum();
						if(h1==h2)
							continue;
						if(finish[h2])
							continue;
						int num=flagx?CardHeap[h1].size()-coverNumber[h1]-1:1;
						if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2,num)){
							
							System.out.println("test");
							
							
							
							mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), num));
							
							togive[h1]=h1-coverNumber[h1];
							need[h2]=CardHeap[h2].size()-coverNumber[h2];
							if(need[h1]==0&&togive[h1]==1)
								finish[h1]=true;
							if(need[h2]==0&&togive[h2]==1)
								finish[h2]=true;
							flag=true;
							break l;
						}
					}
				}
				if(flag){
					if(flagx){
						coverHeapCard(CardHeap, coverNumber, h1);
					}else{
						coverHeapCard(CardHeap, coverNumber, h2);
					}
				}
			}
			
		}
		
		static int organizeDealer(Card[][] tempDealer,int[] togiveNum,int allDealerNum){
			
			int x=0;
			int group=0;
			
			l:
			for(int i=1;i<=8;i++){
				group=i;
				for(int j=0;j<3;j++){
					x++;
					if(tempDealer[i-1][j]==null)
						break l;
				}
			}
			
			int temp1=r.nextInt(100)+1;
			
			int groupNext;
			int addGroup;
			
			if(temp1<30)
				addGroup=0;
			else if(temp1<60)
				addGroup=1;
			else if(temp1<87)
				addGroup=2;
			else if(temp1<89)
				addGroup=3;
			else if(temp1<91)
				addGroup=4;
			else if(temp1<92)
				addGroup=5;
			else if(temp1<93)
				addGroup=6;
			else{
				for(int i=0;i<togiveNum.length;i++)
					togiveNum[i]=0;
				return allDealerNum;
			}
			
			if(group+addGroup>=8)
				groupNext=8;
			else
				groupNext=group+addGroup;
			
			int nextCardNumber=(groupNext-2)*3+r.nextInt(4);
			
			if(nextCardNumber<=x){
				if(r.nextBoolean()){
					nextCardNumber=x+1;
				}else{
					if(r.nextBoolean()){
						nextCardNumber=x+2;
					}else{
						for(int i=0;i<togiveNum.length;i++)
							togiveNum[i]=0;
						return allDealerNum;
					}
				}
			}
			
			int orgNum=nextCardNumber-x;
			
			allDealerNum-=orgNum;
			
			if(groupNext-2>=0){
				RandomUniqueNumber ru=new RandomUniqueNumber(0,groupNext-2);
				
				while(true){
					ru.reSet();
					
					for(int i=0;i<=groupNext-2;i++){
						
						int g=ru.getNum();
						
						int next=r.nextInt(orgNum+1>4?4:orgNum+1);
						if(next>orgNum){
							next=orgNum;
						}
						if(togiveNum[g]+next>3){
							orgNum=orgNum+togiveNum[g]-3;
							togiveNum[g]=3;
						}else{
							orgNum-=next;
							togiveNum[g]+=next;
						}
						
					}
					if(orgNum==0){
						break;
					}
				}
			}
			togiveNum[groupNext-1]=nextCardNumber-(groupNext-1)*3;
			
			Card[][] tempDealerNew=new Card[8][3];
			
			int inxde1=0;
			int indexIn=0;
			
			for(int i=0;i<groupNext-2;i++){
				for(int j=0;j<3-togiveNum[i];j++){
					if(indexIn==3){
						indexIn=0;
						inxde1++;
					}
					tempDealerNew[i][j]=tempDealer[inxde1][indexIn];
				}
			}
			
			for(int i=0;i<3-togiveNum[groupNext-1];i++){
				if(indexIn==3){
					indexIn=0;
					inxde1++;
				}
				tempDealerNew[groupNext-1][i]=tempDealer[inxde1][indexIn];
			}
			/*int orgNum;
			
			int temp;
			if(temp1<10)
				return 0;
			else if(temp1<70)
				temp=r.nextInt(5)+1;
			else if(temp1<90)
				temp=r.nextInt(5)+3;
			else
				temp=r.nextInt(5)+5;
			
			int range=(x+temp)/3;
			RandomUniqueNumber ru=new RandomUniqueNumber(0,range);
			
			if(temp>allDealerNum)
				orgNum=allDealerNum;
			else
				orgNum=temp;
			
			allDealerNum-=orgNum;
			
			int[] order=new int[range+1];
			for(int i=0;i<=range;i++)
				order[i]=ru.getNum();
			
			int inxde1=0;
			int indexIn=0;
			
			Card[][] tempDealerNew=new Card[8][3];
			
			while(true){
				for(int i=0;i<range;i++){
					int next=r.nextInt(orgNum+1>4?4:orgNum+1);
					if(next>orgNum){
						next=orgNum;
					}
					if(togiveNum[i]+next>3){
						orgNum=orgNum+togiveNum[i]-3;
						togiveNum[i]=3;
					}else{
						orgNum-=next;
						togiveNum[i]+=next;
					}
				}
				if(orgNum==0){
					break;
				}
			}
			
			//可能会少
			for(int i=0;i<range;i++){
				for(int j=0;j<3-togiveNum[i];j++){
					if(indexIn==3){
						indexIn=0;
						inxde1++;
					}
					tempDealerNew[i][j]=tempDealer[inxde1][indexIn];
				}
			}
			
			tempDealer=tempDealerNew;*/
			
			return allDealerNum;
		}
		
		static SolvableCardGame getASolvableCardGame(){
			
			ArrayList<Operation> mvlist=new ArrayList<>();
			
			ArrayList<Card>[] CardHeap = new ArrayList[7];
			
			int[] coverNumber=new int[7];
			
			for(int i=0;i<7;i++){
				CardHeap[i]=new ArrayList<>();
			}
			
			int allDealerNum=24;
			
			
			RandomUniqueNumber ruH1=new RandomUniqueNumber(0,5);
			RandomUniqueNumber ruH2=new RandomUniqueNumber(0,5);
			
			Card[][] tempDealer=new Card[8][3];
			
			int[] togiveNum=new int[8];
			
			initHeap(CardHeap);
			
			for(int i=0;i<7;i++)
				System.out.println(CardHeap[i]);
			System.out.println();
			
			while(allDealerNum!=0){
				allDealerNum=organizeDealer(tempDealer, togiveNum, allDealerNum);
				
				for(int i=0;i<7;i++){
					System.out.println(CardHeap[i]);
					System.out.println("COVER"+coverNumber[i]);
				}
				System.out.println();
				
				for(int ii=0;ii<8;ii++){
					System.out.print(togiveNum[ii]+" ");
				}
				System.out.println();
				for(int ii=0;ii<8;ii++){
					for(int jj=0;jj<3;jj++){
						System.out.print(tempDealer[ii][jj]+" ");
					}
				}
				System.out.println();
				
				for(int i=6;i>=0;i--){
					
					mvlist.add(new NextOperation());
					
					int tt=togiveNum[i];
					for(int j=0;j<tt;j++){
						
						int temp=0;
						while(temp<50){
							int temp2=r.nextInt(100);
							if(temp2<40){
								ruH1.reSet();
								l:
								for(int x=0;x<6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									for(int y=0;y<6;y++){
										int h2=ruH2.getNum();
										if(h1==h2)
											continue;
										
										int num=CardHeap[h1].size()-coverNumber[h1]-1;
										if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2,num)){
											coverHeapCard(CardHeap, coverNumber, h1);
											
											mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), num));
											
										/*	System.out.println(mvlist.get(mvlist.size()-1));
											for(int ii=0;ii<7;ii++){
												System.out.println(CardHeap[ii]);
												System.out.println("COVER"+coverNumber[ii]);
											}
											System.out.println();*/
											
											break l;
										}
									}
								}
							}else if(temp2<80){
								ruH1.reSet();
								l:
								for(int x=0;x<6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									for(int y=0;y<6;y++){
										int h2=ruH2.getNum();
										if(h1==h2)
											continue;
										if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2, 1)){
											coverHeapCard(CardHeap, coverNumber, h2);
											
											mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), 1));
											
									/*		System.out.println(mvlist.get(mvlist.size()-1));
											for(int ii=0;ii<7;ii++){
												System.out.println(CardHeap[ii]);
												System.out.println("COVER"+coverNumber[ii]);
											}
											System.out.println();*/
											
											break l;
										}
									}
								}
									
							}else if(temp2<95){
								ruH1.reSet();
								l:
								for(int x=0;x<6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									if(CardHeap[h1].size()-coverNumber[h1]==0)
										continue;
									for(int y=0;y<6;y++){
										int h2=ruH2.getNum();
										if(h1==h2)
											continue;
										int num=r.nextInt(CardHeap[h1].size()-coverNumber[h1]);
										if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2, num)){
											
											mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), num));
											
										/*	System.out.println(mvlist.get(mvlist.size()-1));
											for(int ii=0;ii<7;ii++){
												System.out.println(CardHeap[ii]);
												System.out.println("COVER"+coverNumber[ii]);
											}
											System.out.println();*/
											
											break l;
										}
									}
								}
							}
							
							temp=r.nextInt(100);
						}
						
						ruH1.reSet();
						for(int x=0;x<6;x++){
							int from=ruH1.getNum();
							System.out.println(from);
							if(fromHeapToDealer(CardHeap, coverNumber, tempDealer, from, i, togiveNum)){
								
								mvlist.add(new MoveOperation(heapMap.get(from), Components.DEALER, 1));
								
								System.out.println(mvlist.get(mvlist.size()-1)+" "+i+" "+togiveNum[i]);
								for(int ii=0;ii<7;ii++){
									System.out.println(CardHeap[ii]);
									System.out.println("COVER"+coverNumber[ii]);
								}
								for(int ii=0;ii<8;ii++){
									for(int jj=0;jj<3;jj++){
										System.out.print(tempDealer[ii][jj]+" ");
									}
									System.out.print("# ");
								}
								System.out.println();
								System.out.println();
								
								
								break;
							}
						}
					}
				}
			}
			
			System.out.println("f");
			
			ArrayList<Card> Dealer=new ArrayList<>();
			for(int i=0;i<8;i++){
				for(int j=0;j<3;j++){
					Dealer.add(tempDealer[i][j]);
				}
			}
			
			for(int i=0;i<7;i++)
				System.out.println(CardHeap[i]);
			
			System.out.println(Dealer);
			
			organizeHeap(CardHeap, coverNumber,mvlist);
			
			System.out.println("f2");
			
		
			
			return new SolvableCardGame(CardHeap, Dealer, mvlist);
		}
		
		public static void main(String[] args) {
			SolvableCardGame.getASolvableCardGame();
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

package core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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

			@Override
			public boolean equals(Object obj) {
				if(obj instanceof NextOperation)
					return true;
				else 
					return false;
			}
		}
		
		public static class IndexAndSize implements Comparable<IndexAndSize>{
			private int index;
			private int size;
			public IndexAndSize(int index, int size) {
				super();
				this.index = index;
				this.size = size;
			}
			@Override
			public int compareTo(IndexAndSize o) {
				return o.size-size;
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
			@Override
			public boolean equals(Object obj) {
				if(obj instanceof MoveOperation){
					if( (((MoveOperation) obj).from==from) && (((MoveOperation) obj).to==to) && (((MoveOperation) obj).num==num) ){
						return true;
					}
				}
				return false;
			}
		}
		
		private ArrayList<Card>[] CardHeap;
		private ArrayList<Card> Dealer;
		private ArrayList<Operation> mvlist;
		
		public void showGame(){
			Set<Card> all=new HashSet<>();
			for(int i=1;i<=7;i++){
				System.out.println("牌堆"+i+":"+CardHeap[i-1]);
				all.addAll(CardHeap[i-1]);
			}
			System.out.println();
			
			System.out.println("发牌器:"+Dealer);
			all.addAll(Dealer);
			
			all.add(null);
			
			System.out.println("总牌数："+(all.size()-1));
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
		
		private SolvableCardGame(ArrayList<Card>[] cardHeap, ArrayList<Card> dealer, ArrayList<Operation> mvlist) {
			super();
			CardHeap = cardHeap;
			Dealer = dealer;
			this.mvlist = mvlist;
		}
		
		private static Random r=new Random();
		
		private static void initHeap(ArrayList<Card> CardHeap[]){
			boolean f=true;
			for(int i=12;i>=0;i--){
				if(f){
					if(r.nextBoolean()){
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
						CardHeap[4].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
					}else{
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
						CardHeap[4].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
					}
					
					if(r.nextBoolean()){
						CardHeap[5].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
						CardHeap[6].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
					}else{
						CardHeap[5].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
						CardHeap[6].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
					}
					
					f=false;
				}else{
					if(r.nextBoolean()){
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
						CardHeap[4].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
					}else{
						CardHeap[3].add(CardImpl.valueOf(CardNumber.values()[i],CardType.HEARTS));
						CardHeap[4].add(CardImpl.valueOf(CardNumber.values()[i],CardType.DIAMONDS));
					}
					
					if(r.nextBoolean()){
						CardHeap[5].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
						CardHeap[6].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
					}else{
						CardHeap[5].add(CardImpl.valueOf(CardNumber.values()[i],CardType.SPADES));
						CardHeap[6].add(CardImpl.valueOf(CardNumber.values()[i],CardType.CLUBS));
					}
					
					f=true;
				}
			}
		}
		
		private static boolean fromHeaptoHeap(ArrayList<Card> CardHeap[],int[] coverNumber,int from,int to,int num){
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
		
		private static boolean coverHeapCard(ArrayList<Card> CardHeap[],int[] coverNumber,int x){
			if(coverNumber[x]==x)
				return false;
			if(CardHeap[x].size()==coverNumber[x]+1){
				coverNumber[x]++;
				return true;
			}
			return false;
		}
		
		private static boolean fromHeapToDealer(ArrayList<Card> CardHeap[],int[] coverNumber,Card[][] tempDealer,int from,int to,int[] togiveNum,int lastGroup){
			if(CardHeap[from].size()==coverNumber[from]){
				return false;
			}
			if(togiveNum[to]==0)
				return false;
			Card c=CardHeap[from].remove(CardHeap[from].size()-1);
			if(to!=lastGroup)
				tempDealer[to][3-(togiveNum[to]--)]=c;
			else{
				for(int i=0;i<3;i++){
					if(tempDealer[to][i]==null){
						tempDealer[to][i]=c;
						togiveNum[to]--;
						break;
					}
				}
			}
			return true;
		}
		
		private static boolean organizeHeap(ArrayList<Card> CardHeap[],int[] coverNumber,ArrayList<Operation> mvlist){
			int[] need=new int[7];
			int[] togive=new int[7];
			boolean[] finish=new boolean[7];
			
			RandomUniqueNumber ruH1=new RandomUniqueNumber(0,6);
			RandomUniqueNumber ruH2=new RandomUniqueNumber(1,6);
			
			for(int i=0;i<7;i++){
				need[i]=i-coverNumber[i];
				togive[i]=CardHeap[i].size()-coverNumber[i];
				if(need[i]==0&&togive[i]==1)
					finish[i]=true;
			}
			
			boolean kasi=true;
			
			while(true){
				
				if(finish[0]){
					finish[0]=finish[1]&&finish[2]&&finish[3]&&finish[4]&&finish[5]&&finish[6];
				}
				
				boolean flag=true;
				for(int i=1;i<7;i++)
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
				for(int x=0;x<7;x++){
					
					if(x==0)
						flagx=false;
					
					h1=ruH1.getNum();
					if(finish[h1])
						continue;
					if(togive[h1]<=1)
						continue;
					ruH2.reSet();
					for(int y=1;y<7;y++){
						h2=ruH2.getNum();
						if(h1==h2)
							continue;
						if(finish[h2])
							continue;
						int num=flagx?CardHeap[h1].size()-coverNumber[h1]-1:1;
						
						if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2,num)){
							
							if(flagx){
								coverHeapCard(CardHeap, coverNumber, h1);
							}else{
								coverHeapCard(CardHeap, coverNumber, h2);
							}
							
							togive[h1]=CardHeap[h1].size()-coverNumber[h1];
							need[h1]=h1-coverNumber[h1];
							
							togive[h2]=CardHeap[h2].size()-coverNumber[h2];
							need[h2]=h2-coverNumber[h2];
							
							if(need[h1]==0&&togive[h1]==1)
								finish[h1]=true;
							if(need[h2]==0&&togive[h2]==1)
								finish[h2]=true;
							
						/*	for(int ii=0;ii<7;ii++){
								System.out.println(CardHeap[ii]);
								System.out.println("COVER:"+coverNumber[ii]+" NEED:"+need[ii]+" TOGIVE:"+togive[ii]+" FINISH:"+finish[ii]);
							}
							System.out.println();*/
							
							mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), num));
							
							kasi=false;
							
							if(mvlist.get(mvlist.size()-1).equals(mvlist.get(mvlist.size()-3)))
								kasi=true;
							
							break l;
						}
					}
				}
				
				if(kasi){
					
					boolean cover=false;
					
					for(int i=1;i<7;i++)
						if(coverHeapCard(CardHeap, coverNumber, i))
							cover=true;
					if(!cover){
						
						ArrayList<IndexAndSize> slist=new ArrayList<>();
						for(int i=1;i<7;i++){
							slist.add(new IndexAndSize(i, CardHeap[i].size()-coverNumber[i]));
						}
						Collections.sort(slist);
						
						boolean faliure=true;
						
						for(int x=0;x<6;x++){
							h1=slist.get(x).index;
							if(finish[h1])
								continue;
							if(togive[h1]<=1)
								continue;
							h2=0;
							if(fromHeaptoHeap(CardHeap, coverNumber, h1, h2,CardHeap[h1].size()-coverNumber[h1]-1)){
								
								faliure=false;
								
								coverHeapCard(CardHeap, coverNumber, h1);
								
								togive[h1]=CardHeap[h1].size()-coverNumber[h1];
								need[h1]=h1-coverNumber[h1];
								
								togive[h2]=CardHeap[h2].size()-coverNumber[h2];
								need[h2]=h2-coverNumber[h2];
								
								if(need[h1]==0&&togive[h1]==1)
									finish[h1]=true;
								if(need[h2]==0&&togive[h2]==1)
									finish[h2]=true;
								
							/*	for(int ii=0;ii<7;ii++){
									System.out.println(CardHeap[ii]);
									System.out.println("COVER:"+coverNumber[ii]+" NEED:"+need[ii]+" TOGIVE:"+togive[ii]+" FINISH:"+finish[ii]);
								}
								System.out.println();*/
								
								mvlist.add(new MoveOperation(heapMap.get(h1), heapMap.get(h2), CardHeap[h1].size()-coverNumber[h1]-1));
								
							}
							
						}
						
						if(faliure)
							return false;
					}
				}else{
					kasi=true;
				}
				
			}
			return true;
		}
		
		private static int[] organizeDealer(Card[][] tempDealer,int[] togiveNum,int allDealerNum){
			
			int[] ret=new int[2];
			
			int x=0;
			int group=0;
			
			l:
			for(int i=1;i<=8;i++){
				group=i;
				for(int j=0;j<3;j++){
					if(tempDealer[i-1][j]==null)
						break l;
					else
						x++;
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
				
				ret[0]=allDealerNum;
				ret[1]=-1;
				
				return ret;
			}
			
			if(group+addGroup>=8)
				groupNext=8;
			else
				groupNext=group+addGroup;
			
			int nextCardNumber=(groupNext-1)*3+r.nextInt(3)+1;
			
			if(nextCardNumber>24)
				nextCardNumber=24;
			
			if(nextCardNumber<=x){
				if(r.nextBoolean()){
					nextCardNumber=x+1;
				}else{
					if(r.nextBoolean()){
						nextCardNumber=x+2;
					}else{
						for(int i=0;i<togiveNum.length;i++)
							togiveNum[i]=0;
						
						ret[0]=allDealerNum;
						ret[1]=-1;
						
						return ret;
					}
				}
			}
			
			int orgNum=nextCardNumber-x;
			
			int xx=((nextCardNumber-1)/3+1);
			if(xx<8)
				groupNext=xx;
			else
				groupNext=8;
			
			if(allDealerNum-orgNum>=0)
				allDealerNum-=orgNum;
			else{
				orgNum=allDealerNum;
				allDealerNum=0;
			}
			
			int leave=nextCardNumber-(groupNext-1)*3;
			
			if(groupNext-2>=0){
				RandomUniqueNumber ru=new RandomUniqueNumber(0,groupNext-2);
				
				while(true){
					ru.reSet();
					
					for(int i=0;i<groupNext-1;i++){
						
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
					
					if(orgNum<=leave){
						if(r.nextBoolean())
							break;
					}
				}
			}
			togiveNum[groupNext-1]=orgNum;
			
			Card[][] tempDealerNew=new Card[8][3];
			
			int inxde1=0;
			int indexIn=0;
			
		//	System.err.println("x"+x);
			
			for(int i=0;i<groupNext-1;i++){
				for(int j=0;j<3-togiveNum[i];j++){
					if(indexIn==3){
						indexIn=0;
						inxde1++;
					}
					tempDealerNew[i][j]=tempDealer[inxde1][indexIn++];
					x--;
				}
			}
			
//			System.err.println("x"+x);
//			System.out.println("Group"+groupNext+" Card"+nextCardNumber);
			
			for(int i=0;i<x;i++){
				if(indexIn==3){
					indexIn=0;
					inxde1++;
				}
				tempDealerNew[groupNext-1][i]=tempDealer[inxde1][indexIn++];
			}
			
			ret[0]=allDealerNum;
			ret[1]=groupNext-1;
			
			for(int i=0;i<8;i++)
				for(int j=0;j<3;j++)
					tempDealer[i][j]=tempDealerNew[i][j];
			
			return ret;
			
			
		}
		
		private static SolvableCardGame getASolvableCardGamePro(){
			
			ArrayList<Operation> mvlist=new ArrayList<>();
			
			ArrayList<Card>[] CardHeap = new ArrayList[7];
			
			int[] coverNumber=new int[7];
			
			for(int i=0;i<7;i++){
				CardHeap[i]=new ArrayList<>();
			}
			
			int allDealerNum=24;
			
			RandomUniqueNumber ruH1=new RandomUniqueNumber(1,6);
			RandomUniqueNumber ruH2=new RandomUniqueNumber(1,6);
			
			Card[][] tempDealer=new Card[8][3];
			
			int[] togiveNum=new int[8];
			
			initHeap(CardHeap);
			
		/*	for(int i=0;i<7;i++)
				System.out.println(CardHeap[i]);
			System.out.println();*/
			
			while(allDealerNum!=0){
				
				int[] ret=organizeDealer(tempDealer, togiveNum, allDealerNum);
				allDealerNum=ret[0];
				
			/*	for(int i=0;i<7;i++){
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
				System.out.println();*/
				
				for(int i=7;i>=0;i--){
					
					mvlist.add(new NextOperation());
					
					int tt=togiveNum[i];
					for(int j=0;j<tt;j++){
						
						int temp=0;
						while(temp<50){
							int temp2=r.nextInt(100);
							if(temp2<40){
								ruH1.reSet();
								l:
								for(int x=1;x<=6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									for(int y=1;y<=6;y++){
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
								for(int x=1;x<=6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									for(int y=1;y<6;y++){
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
								for(int x=1;x<=6;x++){
									int h1=ruH1.getNum();
									ruH2.reSet();
									if(CardHeap[h1].size()-coverNumber[h1]==0)
										continue;
									for(int y=1;y<6;y++){
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
						for(int x=1;x<=6;x++){
							int from=ruH1.getNum();
							if(fromHeapToDealer(CardHeap, coverNumber, tempDealer, from, i, togiveNum,ret[1])){
								
								mvlist.add(new MoveOperation(heapMap.get(from), Components.DEALER, 1));
								
							/*	System.out.println(mvlist.get(mvlist.size()-1)+" "+i+" "+togiveNum[i]);
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
								System.out.println();*/
								
								
								break;
							}
						}
					}
				}
			}
			
/*			System.out.println("f");*/
			
			ArrayList<Card> Dealer=new ArrayList<>();
			for(int i=0;i<8;i++){
				for(int j=0;j<3;j++){
					Dealer.add(tempDealer[i][j]);
				}
			}
			
			if(!organizeHeap(CardHeap, coverNumber,mvlist))
				return null;
			
	/*		for(int i=0;i<7;i++)
				System.out.println(CardHeap[i]);
			System.out.println();
			
			System.out.println(Dealer);
			
			
			
			System.out.println("f");*/
			
			return new SolvableCardGame(CardHeap, Dealer, mvlist);
		}
		
		public static SolvableCardGame getASolvableCardGame(){
			SolvableCardGame ans=getASolvableCardGamePro();
			while(ans==null)
				ans=getASolvableCardGamePro();
			return ans;
		}
		
		public static void main(String[] args) {
			SolvableCardGame scg=SolvableCardGame.getASolvableCardGame();
			scg.showGame();
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

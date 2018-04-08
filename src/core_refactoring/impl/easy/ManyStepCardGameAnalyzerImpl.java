package core_refactoring.impl.easy;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import core_refactoring.*;
import core_refactoring.impl.SingleStepCardGameAnalyzerImpl;

public class ManyStepCardGameAnalyzerImpl implements BindingCardGameAnalyzer{
	
	public static class AnalyzerCardInitializer implements CardInitializer{

		private CardInitializer realOne;
		
		private ArrayList<Card>[] CardHeap;
		private ArrayList<Card> Dealer;
		
		private int dealerIndexReal=0;
		private int[] heapIndexesReal=new int[7]; 
		
		private int dealerIndexAnalyzer=0;
		private int[] heapIndexesAnalyzer=new int[7]; 
		

		@SuppressWarnings("unchecked")
		public AnalyzerCardInitializer(CardInitializer realOne) {
			super();
			this.realOne = realOne;
			CardHeap=new ArrayList[7];
			for(int i=0;i<7;i++)
				CardHeap[i]=new ArrayList<>();
			Dealer=new ArrayList<>();
		}
		
		private Card getCardx(Components c){
			Card card=realOne.getCard(c);
			switch(c){
			case DEALER:
				Dealer.add(card);
				break;
			case CARD_HEAP_1:
				CardHeap[0].add(card);
				break;
			case CARD_HEAP_2:
				CardHeap[1].add(card);
				break;
			case CARD_HEAP_3:
				CardHeap[2].add(card);
				break;
			case CARD_HEAP_4:
				CardHeap[3].add(card);
				break;
			case CARD_HEAP_5:
				CardHeap[4].add(card);
				break;
			case CARD_HEAP_6:
				CardHeap[5].add(card);
				break;
			case CARD_HEAP_7:
				CardHeap[6].add(card);
				break;
			}
			return card;
		}

		@Override
		public Card getCard(Components c) {
			try{
				switch(c){
				case DEALER:
					return Dealer.get(dealerIndexReal++);
				case CARD_HEAP_1:
					return CardHeap[0].get(heapIndexesReal[0]++);
				case CARD_HEAP_2:
					return CardHeap[1].get(heapIndexesReal[1]++);
				case CARD_HEAP_3:
					return CardHeap[2].get(heapIndexesReal[2]++);
				case CARD_HEAP_4:
					return CardHeap[3].get(heapIndexesReal[3]++);
				case CARD_HEAP_5:
					return CardHeap[4].get(heapIndexesReal[4]++);
				case CARD_HEAP_6:
					return CardHeap[5].get(heapIndexesReal[5]++);
				case CARD_HEAP_7:
					return CardHeap[6].get(heapIndexesReal[6]++);
				default:
					return null;
				}
			}catch (Exception e) {
				return getCardx(c);
			}
		}
		
		public Card getAnalyzerCard(Components c){
			try{
				switch(c){
				case DEALER:
					return Dealer.get(dealerIndexAnalyzer++);
				case CARD_HEAP_1:
					return CardHeap[0].get(heapIndexesAnalyzer[0]++);
				case CARD_HEAP_2:
					return CardHeap[1].get(heapIndexesAnalyzer[1]++);
				case CARD_HEAP_3:
					return CardHeap[2].get(heapIndexesAnalyzer[2]++);
				case CARD_HEAP_4:
					return CardHeap[3].get(heapIndexesAnalyzer[3]++);
				case CARD_HEAP_5:
					return CardHeap[4].get(heapIndexesAnalyzer[4]++);
				case CARD_HEAP_6:
					return CardHeap[5].get(heapIndexesAnalyzer[5]++);
				case CARD_HEAP_7:
					return CardHeap[6].get(heapIndexesAnalyzer[6]++);
				default:
					return null;
				}
			}catch (Exception e) {
				return getCardx(c);
			}
		}
	}
	
	private static class DfsTreeNode{
		private String lastMove;
		private DfsTreeNode lastDfsTreeNode;
		
		private ArrayList<String> nextMoveRoad;
		private boolean finishedGame;
		
		private ArrayList<DfsTreeNode> nextMove;
		public DfsTreeNode() {
			this.nextMove = new ArrayList<>();
		}
	}
	
	final private AnalyzerCardInitializer cardInitializer;
	
	final private SingleStepCardGameAnalyzerImpl singleStepCardGameAnalyzer=new SingleStepCardGameAnalyzerImpl(false);
	
	final private CardManagement analyzerCopy;
	
	final private Deque<String> moveStack=new ArrayDeque<>();
	
	final private DfsTreeNode root=new DfsTreeNode();
	
	public boolean dfs(DfsTreeNode dfsTreeNode){
		singleStepCardGameAnalyzer.analyzerGame(analyzerCopy);
		if(singleStepCardGameAnalyzer.isGameFinish())
			return true;
		dfsTreeNode.nextMoveRoad=singleStepCardGameAnalyzer.getTips();
		dfsTreeNode.finishedGame=singleStepCardGameAnalyzer.isGameFinish();
		for(String s:dfsTreeNode.nextMoveRoad){
			String[] sp=s.split(" ");
			
			DfsTreeNode dfs=new DfsTreeNode();
			dfs.lastMove=s;
			dfsTreeNode.nextMove.add(dfs);
			dfs.lastDfsTreeNode=dfsTreeNode;
			
			analyzerCopy.moveCards(Components.valueOf(sp[0]), Components.valueOf(sp[1]), Integer.valueOf(sp[2]));
			moveStack.push(s);
			if(dfs(dfs))
				return true;
			moveStack.pop();
			analyzerCopy.undo();
		}
		return false;
	}
	
	public boolean searchNode(ArrayList<String> road){
		//将节点指向移动到指定位置
		return false;
	}
	
	private Runnable runnable=new Runnable() {
		@Override
		public void run() {
			root.lastMove=null;
			root.lastDfsTreeNode=null;
			dfs(root);
		}
	};
	
	public ManyStepCardGameAnalyzerImpl(CardInitializer cardInitializer,CardManagement analyzerCopy) {
		this.cardInitializer=new AnalyzerCardInitializer(cardInitializer);
		this.analyzerCopy=analyzerCopy;
	}

	public CardInitializer getCardInitializer(){
		return cardInitializer;
	}

	@Override
	public ArrayList<String> getTips() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBestTips() {
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

	@Override
	public void analyzerGame() {
		// TODO Auto-generated method stub
		
	}

}

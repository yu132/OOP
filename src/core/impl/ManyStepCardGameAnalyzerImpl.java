package core.impl;

import java.util.ArrayList;

import core.interfaces.Card;
import core.interfaces.CardGame;
import core.interfaces.CardGame.Components;
import core.interfaces.CardGameAnalyzer;
import core.interfaces.CardInitializer;
import core.interfaces.Dealer;

public class ManyStepCardGameAnalyzerImpl implements CardGameAnalyzer{
	
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
	
	public static class AnalyzerCardInitializer implements CardInitializer{

		private CardInitializer realOne;
		
		private ArrayList<Card>[] CardHeap;
		private ArrayList<Card> Dealer;
		private int dealerIndex=0;
		private int[] heapIndexes=new int[7]; 
		
		@SuppressWarnings("unchecked")
		public AnalyzerCardInitializer(CardInitializer realOne) {
			super();
			this.realOne = realOne;
			CardHeap=new ArrayList[7];
			for(int i=0;i<7;i++)
				CardHeap[i]=new ArrayList<>();
			Dealer=new ArrayList<>();
		}

		@Override
		public Card getCard(Components c) {
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
		
		public Card getAnalyzerCard(Components c){
			try{
				switch(c){
				case DEALER:
					return Dealer.get(dealerIndex++);
				case CARD_HEAP_1:
					return CardHeap[0].get(heapIndexes[0]++);
				case CARD_HEAP_2:
					return CardHeap[1].get(heapIndexes[1]++);
				case CARD_HEAP_3:
					return CardHeap[2].get(heapIndexes[2]++);
				case CARD_HEAP_4:
					return CardHeap[3].get(heapIndexes[3]++);
				case CARD_HEAP_5:
					return CardHeap[4].get(heapIndexes[4]++);
				case CARD_HEAP_6:
					return CardHeap[5].get(heapIndexes[5]++);
				case CARD_HEAP_7:
					return CardHeap[6].get(heapIndexes[6]++);
				default:
					return null;
				}
			}catch (Exception e) {
				return null;
			}
		}
	}

	public static class AnalyzerCardGame{
		Dealer analyzerDealer=new DealerImpl(null, null){
			
		};
		
		
	}

	@Override
	public void analyzerGame(CardGame cardGame) {
		// TODO Auto-generated method stub
		
	}

}

package core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import core.interfaces.CardGame;
import core.interfaces.CardGame.Components;
import core.interfaces.CardGameAnalyzer;
import core.interfaces.MoveState;

public class SingleStepCardGameAnalyzerImpl implements CardGameAnalyzer{

	private static Map<Integer,Components> map=new HashMap<>();
	
	private static Map<Integer,Components> boxmap=new HashMap<>();
	
	static{
		map.put(1, Components.CARD_HEAP_1);
		map.put(2, Components.CARD_HEAP_2);
		map.put(3, Components.CARD_HEAP_3);
		map.put(4, Components.CARD_HEAP_4);
		map.put(5, Components.CARD_HEAP_5);
		map.put(6, Components.CARD_HEAP_6);
		map.put(7, Components.CARD_HEAP_7);
		
		boxmap.put(1, Components.BOX_1);
		boxmap.put(2, Components.BOX_2);
		boxmap.put(3, Components.BOX_3);
		boxmap.put(4, Components.BOX_4);
	}
	
	private ArrayList<String> tips;
	private boolean finishedGame;
	
	private boolean fastMode;

	public SingleStepCardGameAnalyzerImpl(boolean fastMode) {
		super();
		this.fastMode = fastMode;
	}
	
	public boolean isFastMode() {
		return fastMode;
	}

	public void setFastMode(boolean fastMode) {
		this.fastMode = fastMode;
	}

	@Override
	public void analyzerGame(CardGame cardGame){
		tips=new ArrayList<>();
		
		if(cardGame.getAllCard(Components.DEALER).size()==0){
			boolean flag=true;
			for(int i=1;i<7;i++){
				if(cardGame.getAllCard(map.get(i)).contains(null)){
					flag=false;
					break;
				}
			}
			if(flag){
				finishedGame=true;
				return;
			}
		}
		
		for(int i=1;i<=7;i++){
			for(int j=1;j<=4;j++){
				if(cardGame.moveCards(map.get(i), boxmap.get(j), 1)==MoveState.SUCCESS){
					tips.add(map.get(i)+" "+boxmap.get(j)+" "+1);
					cardGame.undo();
					if(fastMode){
						return;
					}
				}
			}
		}
		
		for(int num=1;num<13;num++){
			for(int i=1;i<=7;i++){
				for(int j=1;j<=7;j++){
					if(i==j)
						continue;
					if(cardGame.moveCards(map.get(i), map.get(j), num)==MoveState.SUCCESS){
						tips.add(map.get(i)+" "+map.get(i)+" "+num);
						cardGame.undo();
						if(fastMode){
							return;
						}
					}
				}
			}
		}
		
		for(int i=0;i<8;i++){
			for(int j=1;j<=7;j++){
				if(cardGame.moveCards(Components.DEALER, map.get(j), 1)==MoveState.SUCCESS){
					tips.add(Components.DEALER+" "+map.get(j)+" "+1);
					cardGame.undo();
					if(fastMode){
						while(i--!=0)
							cardGame.undo();
						return;
					}
				}
			}
			for(int j=1;j<=4;j++){
				if(cardGame.moveCards(Components.DEALER, boxmap.get(j), 1)==MoveState.SUCCESS){
					tips.add(Components.DEALER+" "+boxmap.get(j)+" "+1);
					cardGame.undo();
					if(fastMode){
						while(i--!=0)
							cardGame.undo();
						return;
					}
				}
			}
			cardGame.nextCard();
		}
		for(int i=0;i<8;i++){
			cardGame.undo();
		}
	}

	@Override
	public ArrayList<String> getTips() {
		return tips;
	}

	@Override
	public String getBestTips() {
		if(!tips.isEmpty())
			return tips.get(0);
		throw new NoSuchElementException("No tips available");
	}

	@Override
	public boolean isGameFinish() {
		return finishedGame;
	}

	@Override
	public boolean isGameOver() {
		return !finishedGame&&tips.isEmpty();
	}
	
}

package core.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.interfaces.CardGame;
import core.interfaces.CardGame.Components;
import core.interfaces.CardGameAnalyzer;
import core.interfaces.MoveState;

public class SingleStepCardGameAnalyzerImpl implements CardGameAnalyzer{

	private static Map<Integer,Components> map=new HashMap<>();
	
	static{
		map.put(1, Components.CARD_HEAP_1);
		map.put(2, Components.CARD_HEAP_2);
		map.put(3, Components.CARD_HEAP_3);
		map.put(4, Components.CARD_HEAP_4);
		map.put(5, Components.CARD_HEAP_5);
		map.put(6, Components.CARD_HEAP_6);
		map.put(7, Components.CARD_HEAP_7);
	}
	
	private ArrayList<String> tips;
	private boolean finishedGame;
	private boolean gameOver;
	
	public void analyzerGame(CardGame cardGame){
		if(cardGame.getAllCard(Components.DEALER)==""){
			
		}
		for(int i=1;i<=7;i++){
			
		}
		
		tips=new ArrayList<>();
		
		for(int num=1;num<13;num++){
			for(int i=1;i<=7;i++){
				for(int j=1;j<=7;j++){
					if(i==j)
						continue;
					if(cardGame.moveCards(map.get(i), map.get(j), num)==MoveState.SUCCESS){
						tips.add(map.get(i)+" "+map.get(i)+" "+num);
						cardGame.undo();
					}
				}
			}
		}
		
		
	}
	
	@Override
	public String getTips() {
		return tips;
	}

	@Override
	public boolean isGameFinish() {
		return finishedGame;
	}

	@Override
	public boolean isGameOver() {
		return gameOver;
	}

}

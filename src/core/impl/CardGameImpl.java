package core.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import core.interfaces.Box;
import core.interfaces.CardGame;
import core.interfaces.CardHeap;
import core.interfaces.Component;
import core.interfaces.Dealer;
import core.interfaces.MoveState;
import core.interfaces.PointCounter;
import core.interfaces.Timer;
import core.interfaces.CardGameAnalyzer;

public class CardGameImpl implements CardGame{
	
	private enum Operation{
		move,next;
	}
	
	private class Operation_pair{
		private Operation o;
		private Components from;
		private Components to;
		public Operation_pair(Operation o, Components from, Components to) {
			super();
			this.o = o;
			this.from = from;
			this.to = to;
		}
	}
	
	private boolean needAnalyze=true;
	
	private Deque<Operation_pair> snapshot=new ArrayDeque<>();

	private Dealer dealer;
	
	private Box box_1;
	private Box box_2;
	private Box box_3;
	private Box box_4;
	
	private CardHeap cardHeap_1;
	private CardHeap cardHeap_2;
	private CardHeap cardHeap_3;
	private CardHeap cardHeap_4;
	private CardHeap cardHeap_5;
	private CardHeap cardHeap_6;
	private CardHeap cardHeap_7;
	
	private Timer timer;
	
	private PointCounter pointCounter;
	
	private CardGameAnalyzer tipsGetter;
	
	private String lastMove="";
	
	private Map<Components,Component> map=new HashMap<>();
	
	public CardGameImpl(Dealer dealer, Box box_1, Box box_2, Box box_3, Box box_4, CardHeap cardHeap_1,
			CardHeap cardHeap_2, CardHeap cardHeap_3, CardHeap cardHeap_4, CardHeap cardHeap_5, CardHeap cardHeap_6,
			CardHeap cardHeap_7, Timer timer, PointCounter pointCounter, CardGameAnalyzer tipsGetter) {
		super();
		this.dealer = dealer;
		this.box_1 = box_1;
		this.box_2 = box_2;
		this.box_3 = box_3;
		this.box_4 = box_4;
		this.cardHeap_1 = cardHeap_1;
		this.cardHeap_2 = cardHeap_2;
		this.cardHeap_3 = cardHeap_3;
		this.cardHeap_4 = cardHeap_4;
		this.cardHeap_5 = cardHeap_5;
		this.cardHeap_6 = cardHeap_6;
		this.cardHeap_7 = cardHeap_7;
		this.timer = timer;
		this.pointCounter = pointCounter;
		this.tipsGetter = tipsGetter;
		map.put(Components.DEALER, this.dealer);
		map.put(Components.BOX_1, this.box_1);
		map.put(Components.BOX_2, this.box_2);
		map.put(Components.BOX_3, this.box_3);
		map.put(Components.BOX_4, this.box_4);
		map.put(Components.CARD_HEAP_1, this.cardHeap_1);
		map.put(Components.CARD_HEAP_2, this.cardHeap_2);
		map.put(Components.CARD_HEAP_3, this.cardHeap_3);
		map.put(Components.CARD_HEAP_4, this.cardHeap_4);
		map.put(Components.CARD_HEAP_5, this.cardHeap_5);
		map.put(Components.CARD_HEAP_6, this.cardHeap_6);
		map.put(Components.CARD_HEAP_7, this.cardHeap_7);
		
		if(needAnalyze){
			needAnalyze=false;
			tipsGetter.analyzerGame(this);
			needAnalyze=true;
		}
	}

	private Operation_pair getSnapshot(Components from, Components to){
		return new Operation_pair(Operation.move, from, to);
	}
	
	@Override
	public MoveState moveSingleCard(Components from, Components to) {
		if(from==to)
			return MoveState.ILLEGAL_MOVE;
		
		MoveState ms=map.get(from).sentSingleCard(map.get(to));
		
		if(ms==MoveState.SUCCESS){
			
			
			snapshot.push(getSnapshot(from, to));
		
			pointCounter.addPoint(map.get(from), map.get(to), ms);
			
			if(needAnalyze){
				needAnalyze=false;
				lastMove=from+" "+to+" "+1;
				tipsGetter.analyzerGame(this);
				needAnalyze=true;
			}
		
		}
		return ms;
	}

	@Override
	public MoveState moveCards(Components from, Components to, int number) {
		if(from==to)
			return MoveState.ILLEGAL_MOVE;
		
		if(number==1){
			return moveSingleCard(from,to);
		}
		
		MoveState ms=map.get(from).sentCards(map.get(to),number);
		
		if(ms==MoveState.SUCCESS){
			
			
			snapshot.push(getSnapshot(from, to));
		
			pointCounter.addPoint(map.get(from), map.get(to), ms);
			
			if(needAnalyze){
				needAnalyze=false;
				lastMove=from+" "+to+" "+number;
				tipsGetter.analyzerGame(this);
				needAnalyze=true;
			}
		}
		
		return ms;
	}

	@Override
	public void nextCard() {
		if(needAnalyze){
			lastMove="next";
		}
		snapshot.push(new Operation_pair(Operation.next,null,null));
		dealer.nextCards();
	}

	@Override
	public ArrayList<String> getOpenCard(Components c) {
		return map.get(c).getOpenedCard();
	}

	@Override
	public ArrayList<String> getAllCard(Components c) {
		return map.get(c).getAllCard();
	}

	@Override
	public String getTips() {
		return tipsGetter.getBestTips();
	}

	@Override
	public boolean undo() {
		if(snapshot.isEmpty())
			return false;
		
		Operation_pair op=snapshot.pop();
		
		if(op.o==Operation.next){
			dealer.undo();
		}else{
			System.out.println("undo:"+op.from+" "+op.to);
			map.get(op.from).undo();
			map.get(op.to).undo();
			pointCounter.undo();
		}
		
		return true;
	}

	@Override
	public boolean undoAll() {
		if(snapshot.isEmpty())
			return false;
		
		snapshot.clear();
		
		for(Component c:map.values()){
			c.undoAll();
		}
		
		pointCounter.undoAll();
		
		return true;
	}

	@Override
	public boolean undoable() {
		return !snapshot.isEmpty();
	}

	@Override
	public boolean isGameOver() {
		return tipsGetter.isGameOver();
	}

	@Override
	public boolean isGameFinish() {
		return tipsGetter.isGameFinish();
	}

	@Override
	public long getTime() {
		return timer.getTime();
	}

	@Override
	public int getPoint() {
		return pointCounter.getPoint();
	}

	@Override
	public void stopGame() {}

	@Override
	public String lastMove() {
		return lastMove;
	}

}

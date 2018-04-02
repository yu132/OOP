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
	}

	private Operation_pair getSnapshot(Components from, Components to){
		return new Operation_pair(Operation.move, from, to);
	}
	
	@Override
	public MoveState moveSingleCard(Components from, Components to) {
		snapshot.add(getSnapshot(from, to));
		MoveState ms=map.get(from).sentSingleCard(map.get(to));
		pointCounter.addPoint(map.get(from), map.get(to), ms);
		return ms;
	}

	@Override
	public MoveState moveCards(Components from, Components to, int number) {
		snapshot.add(getSnapshot(from, to));
		MoveState ms=map.get(from).sentCards(map.get(to),number);
		pointCounter.addPoint(map.get(from), map.get(to), ms);
		return ms;
	}

	@Override
	public void nextCard() {
		snapshot.add(new Operation_pair(Operation.next,null,null));
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean finishGame() {
		// TODO Auto-generated method stub
		return false;
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
	public void stopGame() {
		// TODO Auto-generated method stub
	}

}

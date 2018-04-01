package core.impl;

import java.util.ArrayDeque;
import java.util.Deque;

import core.interfaces.Box;
import core.interfaces.CardHeap;
import core.interfaces.Component;
import core.interfaces.Dealer;
import core.interfaces.MoveState;
import core.interfaces.PointCounter;

public class PointCounterImpl implements PointCounter{

	private int point=0;
	
	private Deque<Integer> snapshot=new ArrayDeque<>();
	
	@Override
	public int getPoint() {
		return point;
	}

	@Override
	public void addPoint(Component c1, Component c2, MoveState ms) {
		snapshot.push(point);
		if(ms==MoveState.SUCCESS){
			if(c1 instanceof Dealer){
				if(c2 instanceof Box){
					point+=10;
				}else{
					point+=5;
				}
			}else if(c1 instanceof Box){
				point-=10;
			}else if(c1 instanceof CardHeap){
				if(c2 instanceof Box){
					point+=10;
				}
				if(((CardHeap) c1).openCardLastRound()){
					point+=5;
				}
				
			}
		}
	}

	@Override
	public boolean undo() {
		if(snapshot.isEmpty())
			return false;
		
		point=snapshot.pop();
		return true;
	}

	@Override
	public boolean undoAll() {
		if(snapshot.isEmpty())
			return false;
		
		snapshot.clear();
		point=0;
		return true;
	}

}

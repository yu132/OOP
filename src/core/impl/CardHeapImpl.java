package core.impl;

import java.util.ArrayList;

import core.interfaces.Card;
import core.interfaces.CardHeap;
import core.interfaces.Component;
import core.interfaces.MoveState;

public class CardHeapImpl implements CardHeap{

	@Override
	public MoveState sentSingleCard(Component c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveState getSingleCard(Card card) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveState sentCards(Component c, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveState getCards(ArrayList<Card> cards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getOpenedCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ismovable(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openCardLastRound() {
		// TODO Auto-generated method stub
		return false;
	}

}

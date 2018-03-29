package core.impl;

import java.util.ArrayList;

import core.interfaces.CardGame;
import core.interfaces.MoveState;

public class CardGameImpl implements CardGame{

	@Override
	public MoveState moveSingleCard(Components from, Components to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MoveState moveCards(Components from, Components to, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getOpenCard(Components c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getAllCard(Components c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTips() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean undoable() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stopGame() {
		// TODO Auto-generated method stub
		
	}

}

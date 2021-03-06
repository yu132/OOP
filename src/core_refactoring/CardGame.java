package core_refactoring;

import java.util.ArrayList;

public interface CardGame {

	public MoveState moveCards(Components from,Components to,int number);
	
	public void nextCard();
	
	public ArrayList<String> getAllCard(Components c);
	
	public ArrayList<String> getTopCard(Components c);
	
	public boolean undoable();
	
	public boolean undo();
	
	public boolean undoAll();
	
	public String getTips();
	
	public boolean isGameOver();
	
	public boolean isGameFinish();
	
	public long getTime();
	
	public int getPoint();
	
}

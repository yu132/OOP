package core_refactoring;

import java.util.ArrayList;

public interface CardManagement {

	public MoveState moveCards(Components from,Components to,int number);
	
	public void nextCard();
	
	public ArrayList<String> getTopCard(Components c);
	
	public ArrayList<String> getAllCard(Components c);
	
	public boolean undoable();
	
	public boolean undo();
	
	public boolean undoAll();
	
	public String lastMove();
	
}

package core.interfaces;

import java.util.ArrayList;

public interface Component {

	boolean sentSingleCard(Component c);
	
	boolean getSingleCard(Card card);
	
	boolean sentCards(Component c,int number);
	
	boolean getCards(ArrayList<Card> cards);
	
}

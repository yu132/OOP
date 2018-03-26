package core.interfaces;

public interface CardGame {

	public enum Components{
		DEALER,BOX_1,BOX_2,BOX_3,BOX_4,
		CARD_HEAP_1,CARD_HEAP_2,CARD_HEAP_3,
		CARD_HEAP_4,CARD_HEAP_5,CARD_HEAP_6,CARD_HEAP_7,
	}
	
	public boolean moveSingleCard(Components from,Components to);
	
	public boolean moveCards(Components from,Components to,int number);
	
	public boolean undo();
	
	public long getTime();
	
	public int getPoint();
	
	public void stopGame();
	
}

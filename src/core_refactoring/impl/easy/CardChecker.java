package core_refactoring.impl.easy;

import core.util.RandomUniqueNumber;
import core_refactoring.Card;
import core_refactoring.CardNumber;
import core_refactoring.CardType;
import core_refactoring.impl.CardImpl;

public class CardChecker {

	private boolean[] used=new boolean[52];
	
	private RandomUniqueNumber r=new RandomUniqueNumber(0,51);
	
	public boolean checkCard(Card card){
		if(card==null)
			return false;
		int index=card.getCardType().ordinal()*12+card.getCardNumber().ordinal();
		if(used[index]){
			return false;
		}else{
			used[index]=true;
			return true;
		}
	}
	
	public Card getRandomUnusedCard(){
		while(true){
			int n=r.getNum();
			if(!used[n]){
				used[n]=true;
				return CardImpl.valueOf(CardNumber.values()[n%12], CardType.values()[n/12]);
			}
		}
	}
	
	public void reset(){
		used=new boolean[52];
	}
	
	public static void main(String[] args) {
		Card card=CardImpl.valueOf(CardNumber.ACE, CardType.CLUBS);
		Card card2=CardImpl.valueOf(CardNumber.KING, CardType.HEARTS);
		CardChecker c=new CardChecker();
		System.out.println(c.checkCard(card));
		System.out.println(c.checkCard(card));
		System.out.println(c.checkCard(card2));
	}
}

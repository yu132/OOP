package core.impl;

import core.interfaces.Card;
import core.interfaces.CardNumber;
import core.interfaces.CardType;

public class CardImpl implements Card{
	
	private static CardImpl[][] cards=new CardImpl[4][13];
	
	public final CardNumber CARDNUMBER;
	public final CardType CARDTYPE;
	
	private CardImpl(CardNumber cardNumber, CardType cardType) {
		super();
		CARDNUMBER = cardNumber;
		CARDTYPE = cardType;
	}
	
	static CardImpl valueOf(CardNumber cardNumber, CardType cardType){
		int number=cardNumber.ordinal();
		int type=cardType.ordinal();
		if(cards[type][number]==null){
			cards[type][number]=new CardImpl(cardNumber,cardType);
		}
		return cards[type][number];
	}
	
	@Override
	public CardNumber getCardNumber() {
		return CARDNUMBER;
	}

	@Override
	public CardType getCardType() {
		return CARDTYPE;
	}

	@Override
	public boolean isStackable(Card card) {
		if(CARDNUMBER.ordinal()!=card.getCardNumber().ordinal()+1)
			return false;
		switch(CARDTYPE){
		case CLUBS:
		case SPADES:
			switch(card.getCardType()){
			case CLUBS:
			case SPADES:
				return false;
			case DIAMONDS:
			case HEARTS:
				return true;
			}
		case DIAMONDS:
		case HEARTS:
			switch(card.getCardType()){
			case CLUBS:
			case SPADES:
				return true;
			case DIAMONDS:
			case HEARTS:
				return false;
			}
		default:
			return false;
		}
	}

}

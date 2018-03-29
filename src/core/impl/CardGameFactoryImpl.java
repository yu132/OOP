package core.impl;

import core.interfaces.Box;
import core.interfaces.CardGame;
import core.interfaces.CardGameFactory;
import core.interfaces.CardHeap;
import core.interfaces.CardInitializer;
import core.interfaces.Dealer;
import core.interfaces.Difficulty;
import core.interfaces.Mode;
import core.interfaces.PointCounter;
import core.interfaces.Timer;

public class CardGameFactoryImpl implements CardGameFactory{

	@Override
	public CardGame getCardGame() {
		return getCardGame(Mode.THREE_CARD_MODE);
	}

	@Override
	public CardGame getCardGame(Difficulty d) {
		return null;
	}

	@Override
	public CardGame getCardGame(Mode mode) {
		CardInitializer c=new SimpleCardInitializerImpl();
		
		Dealer dealer = new DealerImpl(mode, c);

		Box box_1=new BoxImpl();
		Box box_2=new BoxImpl();
		Box box_3=new BoxImpl();
		Box box_4=new BoxImpl();

		CardHeap cardHeap_1=new CardHeapImpl(1,c);
		CardHeap cardHeap_2=new CardHeapImpl(2,c);
		CardHeap cardHeap_3=new CardHeapImpl(3,c);
		CardHeap cardHeap_4=new CardHeapImpl(4,c);
		CardHeap cardHeap_5=new CardHeapImpl(5,c);
		CardHeap cardHeap_6=new CardHeapImpl(6,c);
		CardHeap cardHeap_7=new CardHeapImpl(7,c);

		Timer timer=new TimerImpl();

		PointCounter pointCounter=new PointCounterImpl();

		return new CardGameImpl(dealer, box_1, box_2, box_3, box_4, cardHeap_1, cardHeap_2, cardHeap_3, cardHeap_4, cardHeap_5, cardHeap_6, cardHeap_7, timer, pointCounter, null);
	}

	@Override
	public CardGame getCardGame(Difficulty d, Mode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}

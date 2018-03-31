package test;


import core.impl.BoxImpl;
import core.impl.CardHeapImpl;
import core.impl.CardImpl;
import core.impl.CardInitializerImpl;
import core.impl.SimpleCardInitializerImpl;
import core.interfaces.Card;
import core.interfaces.CardInitializer;
import core.interfaces.CardNumber;
import core.interfaces.CardType;
import core.interfaces.Component;
import core.interfaces.MoveState;

public  class test_BoxImpl {
	public static void main(String []arg){
		CardInitializer s=new SimpleCardInitializerImpl();
		CardHeapImpl test_heap = new CardHeapImpl(2,s);
		test_BoxImpl test = new test_BoxImpl();
		BoxImpl test_box = new BoxImpl();
		Card card1 = CardImpl.valueOf(CardNumber.ACE,CardType.SPADES);
		Card card2 = CardImpl.valueOf(CardNumber.TWO,CardType.DIAMONDS);

		test.test_getSingleCard(test_box, card1);
		test.test_getSingleCard(test_box, card2);
		
		
		
		test.test_sentSingleCard(test_box, test_heap);
		
		
	}
	private void test_sentSingleCard(BoxImpl test,Component c){
		MoveState s = test.sentSingleCard(c);
		System.out.println(s);
	}
	
	private void test_getSingleCard(BoxImpl test,Card c){
		MoveState s = test.getSingleCard(c);
		System.out.println(s);
	}
	private MoveState returnstate(){
		return MoveState.SUCCESS;
	}
}

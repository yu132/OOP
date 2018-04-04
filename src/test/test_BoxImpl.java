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
import core.interfaces.CardGame.Components;

public  class test_BoxImpl {
	public static void main(String []arg){
		test_BoxImpl test = new test_BoxImpl();
		
		CardInitializer s=new SimpleCardInitializerImpl();
		BoxImpl test_box = new BoxImpl();
		simple_CardHeapImpl simpleHeap = new simple_CardHeapImpl();
		
		
		Card card1 = CardImpl.valueOf(CardNumber.ACE,CardType.SPADES);
		Card card2 = CardImpl.valueOf(CardNumber.TWO,CardType.SPADES);
		Card card3 = CardImpl.valueOf(CardNumber.THREE,CardType.SPADES);
		Card card4 = CardImpl.valueOf(CardNumber.FOUR,CardType.SPADES);
		Card card5 = CardImpl.valueOf(CardNumber.THREE,CardType.SPADES);
		
		
		
		test.test_getSingleCard(test_box, card1);
	/*	test.test_getSingleCard(test_box, card2);
		test.test_getSingleCard(test_box, card3);
		test.test_getSingleCard(test_box, card4);
		test.test_getSingleCard(test_box, card5);*/
		
	//	test.test_sentSingleCard(test_box, simpleHeap);
		
		test.test_getAllCard(test_box);
		
	//	test.test_getSingleCard(test_box, card5);
		
/*		test.test_getOpenedCard(test_box);
		
		test.test_getAllCard(test_box);*/
		
		test.test_undo(test_box);
		test.test_getAllCard(test_box);
		
		test.test_undo(test_box);
		test.test_getAllCard(test_box);
		
	}
	
	private void test_undoAll(BoxImpl test){
		System.out.println("test_undoAll		"+test.undoAll());;

	}
	
	private void test_undo(BoxImpl test){
		System.out.println("test_undo		"+test.undo());;

	}
	
	private void test_getAllCard(BoxImpl test){
		System.out.println("test_getAllCard		"+test.getAllCard());;

	}
	
	private void test_getOpenedCard(BoxImpl test){
		System.out.println("test_getOpenedCard		"+test.getOpenedCard());;

	}
	
	private void test_sentSingleCard(BoxImpl test,Component c){
		MoveState s = test.sentSingleCard(c);
		System.out.println("test_sentSingleCard		"+s);
	}
	
	private void test_getSingleCard(BoxImpl test,Card c){
		MoveState s = test.getSingleCard(c);
		System.out.println("test_getSingleCard		"+s);
	}
	
}

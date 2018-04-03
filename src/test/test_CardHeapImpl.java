package test;

import core.impl.CardHeapImpl;
import core.impl.CardImpl;
import core.impl.SimpleCardInitializerImpl;
import core.interfaces.CardGame.Components;
import core.interfaces.CardHeap;
import core.interfaces.Card;
import core.interfaces.CardInitializer;
import core.interfaces.CardNumber;
import core.interfaces.CardType;
import core.interfaces.Component;

public class test_CardHeapImpl {
	public static void main(String []arg){
		test_CardHeapImpl test = new test_CardHeapImpl();
		CardInitializer s=new SimpleCardInitializerImpl();
		simple_BoxImpl simBox = new simple_BoxImpl();
		CardHeapImpl test_CardHeap = new CardHeapImpl(4,s,Components.CARD_HEAP_4);
		CardHeapImpl test_CardHeap2 = new CardHeapImpl(5,s,Components.CARD_HEAP_5);
		
		CardHeap test_CardHeap3=new simple_CardHeapImpl();
		
		test.test_sentCards(test_CardHeap,test_CardHeap3,1);
		test.test_undo(test_CardHeap);
		
		/*test.test_getOpenedCard(test_CardHeap);
		Card card1 = CardImpl.valueOf(CardNumber.FOUR,CardType.DIAMONDS);
		Card card2 = CardImpl.valueOf(CardNumber.THREE,CardType.CLUBS);
		test.test_getSingleCard(test_CardHeap,card1);
		test.test_getSingleCard(test_CardHeap,card2);
		
		test.test_getAllCard(test_CardHeap);
		test.test_getOpenedCard(test_CardHeap);
		
		test.test_sentCards(test_CardHeap,test_CardHeap2,2);
		test.test_getAllCard(test_CardHeap);
		test.test_getOpenedCard(test_CardHeap);
		
		test.test_getOpenedCard(test_CardHeap2);
		
		test.test_undo(test_CardHeap);
		test.test_undo(test_CardHeap2);
		
		test.test_getOpenedCard(test_CardHeap);
		test.test_getAllCard(test_CardHeap);
		
		
		test.test_getOpenedCard(test_CardHeap2);
		test.test_getAllCard(test_CardHeap2);*/
		
		/*test.test_openCardLastRound(test_CardHeap);
		test.test_getAllCard(test_CardHeap);
		
		test.test_sentSingleCard(test_CardHeap, simBox);
		test.test_openCardLastRound(test_CardHeap);
		test.test_getOpenedCard(test_CardHeap);
		test.test_getAllCard(test_CardHeap);
		
		test.test_undo(test_CardHeap);
		test.test_getOpenedCard(test_CardHeap);
		test.test_openCardLastRound(test_CardHeap);
		test.test_getAllCard(test_CardHeap);
		
		test.test_sentSingleCard(test_CardHeap, simBox);
		test.test_openCardLastRound(test_CardHeap);
		test.test_getOpenedCard(test_CardHeap);
		test.test_getAllCard(test_CardHeap);*/
	}
	
	private void test_sentCards(CardHeapImpl test_CardHeap,Component c,int number){
		System.out.println("test_sentCards		"+test_CardHeap.sentCards(c,number));
	}
	
	private void test_getSingleCard(CardHeapImpl test_CardHeap,Card card){
		System.out.println("test_getSingleCard		"+test_CardHeap.getSingleCard(card));
	}
	
	private void test_undo(CardHeapImpl test_CardHeap){
		System.out.println("test_undo		"+test_CardHeap.undo());
	}
	
	private void test_sentSingleCard(CardHeapImpl test_CardHeap,Component c){
		System.out.println("test_sentSingleCard		"+test_CardHeap.sentSingleCard(c));
	}
	
	private void test_getAllCard(CardHeapImpl test_CardHeap){
		System.out.println("test_getAllCard		"+test_CardHeap.getAllCard());
	}
	
	private void test_getOpenedCard(CardHeapImpl test_CardHeap){
		System.out.println("test_getOpenedCard		"+test_CardHeap.getOpenedCard());
	}
	
	private void test_openCardLastRound(CardHeapImpl test_CardHeap){
		System.out.println("test_openCardLastRound		"+test_CardHeap.openCardLastRound());
	}
}

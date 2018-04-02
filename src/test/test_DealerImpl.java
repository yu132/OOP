package test;

import core.impl.DealerImpl;
import core.impl.SimpleCardInitializerImpl;
import core.interfaces.CardInitializer;
import core.interfaces.Component;
import core.interfaces.Mode;
import core.interfaces.MoveState;

public class test_DealerImpl {
	public static void main(String []arg){
		test_DealerImpl test = new test_DealerImpl();
		
		simple_CardHeapImpl simpleHeap = new simple_CardHeapImpl();
		CardInitializer s=new SimpleCardInitializerImpl();
		DealerImpl test_dealer = new DealerImpl(Mode.THREE_CARD_MODE,s);
		
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		test.test_undo(test_dealer);
		test.test_getAllCard(test_dealer);
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		
		/*test.test_nextCards(test_dealer);
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_sentSingleCard(test_dealer, simpleHeap);
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_undo(test_dealer);
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);*/
		
	/*	test.test_nextCards(test_dealer);
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_sentSingleCard(test_dealer, simpleHeap);
		test.test_sentSingleCard(test_dealer, simpleHeap);
		test.test_sentSingleCard(test_dealer, simpleHeap);
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_nextCards(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_undo(test_dealer);
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);*/
		
		
		/*test.test_nextCards(test_dealer);
		
		test.test_getOpenedCard(test_dealer);
		test.test_sentSingleCard(test_dealer, simpleHeap);
		
		test.test_getOpenedCard(test_dealer);
		test.test_nextCards(test_dealer);
		
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);
		
		test.test_undo(test_dealer);
		test.test_getOpenedCard(test_dealer);

		test.test_undo(test_dealer);
		test.test_getOpenedCard(test_dealer);

		test.test_getAllCard(test_dealer);
		
		test.test_nextCards(test_dealer);
		test.test_getOpenedCard(test_dealer);
		test.test_getAllCard(test_dealer);*/
	}
	private void test_sentSingleCard(DealerImpl test,Component c){
		MoveState ms = test.sentSingleCard(c);
		System.out.println("test_sentSingleCard		"+ms);
	}
	
	private void test_getOpenedCard(DealerImpl test){
		System.out.println("test_getOpenedCard		"+test.getOpenedCard());
		
	}
	
	private void test_getAllCard(DealerImpl test){
		System.out.println("test_getAllCard		"+test.getAllCard());
	}
	
	private void test_undo(DealerImpl test){
		System.out.println("test_undo		"+test.undo());
		
	}

	private void test_undoAll(DealerImpl test){
		System.out.println("test_undoAll		"+test.undoAll());	}
	
	private void test_nextCards(DealerImpl test){
		test.nextCards();
		System.out.println("test_nextCards		");
	}
}

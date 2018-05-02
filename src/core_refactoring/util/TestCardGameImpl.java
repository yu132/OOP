package core_refactoring.util;

import core_refactoring.CardGameAnalyzer;
import core_refactoring.CardManagement;
import core_refactoring.Components;
import core_refactoring.MoveState;
import core_refactoring.PointCounter;
import core_refactoring.Timer;
import core_refactoring.impl.CardGameImpl;

public class TestCardGameImpl extends CardGameImpl{

	private TestCard t=new TestCard();
	
	public TestCardGameImpl(CardManagement cardGameManager, CardGameAnalyzer cardGameAnalyzer,
			PointCounter pointCounter, Timer timer) {
		super(cardGameManager, cardGameAnalyzer, pointCounter, timer);
	}
	
	@Override
	public MoveState moveCards(Components from, Components to, int number) {
		MoveState temp=super.moveCards(from, to, number);
		t.testMove(from, to, number, this);
		return temp;
	}

	@Override
	public void nextCard() {
		super.nextCard();
		t.testNext(this);
	}

	@Override
	public boolean undo() {
		boolean temp=super.undo();
		t.testUndo(this);
		return temp;
	}

	
	
}

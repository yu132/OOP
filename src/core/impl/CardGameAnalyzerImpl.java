package core.impl;

import core.interfaces.CardGame;
import core.interfaces.CardGameAnalyzer;

public class CardGameAnalyzerImpl implements CardGameAnalyzer{

	private final CardGame cardGame;
	
	public CardGameAnalyzerImpl(CardGame cardGame) {
		this.cardGame = cardGame;
	}

	@Override
	public String getTips() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isGameFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

}

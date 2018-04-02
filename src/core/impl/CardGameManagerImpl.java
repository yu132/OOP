package core.impl;

import core.interfaces.CardGame;
import core.interfaces.CardGameFactory;
import core.interfaces.CardGameManager;
import core.interfaces.Difficulty;

public class CardGameManagerImpl implements CardGameManager{
	
	private CardGameFactory cardGameFactory=new CardGameFactoryImpl();

	@Override
	public CardGame creatGame() {
		return cardGameFactory.getCardGame();
	}

	@Override
	public CardGame creatGame(Difficulty difficulty) {
		return cardGameFactory.getCardGame(difficulty);
	}

	@Override
	public void deleteGame(CardGame cardGame) {
		cardGame.stopGame();
	}

	@Override
	public CardGame reCreatGame() {
		return cardGameFactory.getCardGame();
	}

	@Override
	public CardGame reCreatGame(Difficulty difficulty) {
		return cardGameFactory.getCardGame(difficulty);
	}

}

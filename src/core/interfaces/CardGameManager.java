package core.interfaces;

public interface CardGameManager {
	
	public enum Difficulty{
		EASY,HARD,MASTER;
	}

	public CardGame creatGame();
	
	public CardGame creatGame(Difficulty difficulty);
	
	public void deleteGame(CardGame cardGame);
	
	public CardGame reCreatGame();
	
}

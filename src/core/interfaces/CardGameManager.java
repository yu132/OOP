package core.interfaces;

/**
 * 游戏管理器，对外接口，负责产生游戏，以及销毁游戏
 * @author 87663
 */
public interface CardGameManager {

	public CardGame creatGame();
	
	public CardGame creatGame(Difficulty difficulty);
	
	public void deleteGame(CardGame cardGame);
	
	public CardGame reCreatGame();
	
}

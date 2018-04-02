package core.interfaces;

/**
 * 游戏管理器，对外接口，负责产生游戏，以及销毁游戏
 * @author 87663
 */
public interface CardGameManager {

	/**
	 * 返回一个默认难度的游戏
	 * @return 纸牌游戏
	 */
	public CardGame creatGame();
	
	/**
	 * 返回一个指定难度的游戏
	 * @param difficulty 游戏难度
	 * @return 纸牌游戏
	 */
	public CardGame creatGame(Difficulty difficulty);
	
	/**
	 * 销毁一个纸牌游戏
	 * @param cardGame 被销毁的纸牌游戏
	 */
	public void deleteGame(CardGame cardGame);
	
	/**
	 * 重新开始一个游戏
	 * @return 一个新的纸牌游戏
	 */
	public CardGame reCreatGame();
	
	/**
	 * 重新开始一个指定难度的游戏
	 * @return 一个新的纸牌游戏
	 */
	public CardGame reCreatGame(Difficulty difficulty);
}

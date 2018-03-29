package core.interfaces;

/**
 * 游戏工厂类，负责游戏的组件构造及初始化
 * @author 87663
 */
public interface CardGameFactory {

	/**
	 * 得到一个纸牌游戏类
	 * @return 纸牌游戏对象
	 */
	CardGame getCardGame();
	
	
	CardGame getCardGame(Difficulty  d);
	
	CardGame getCardGame(Mode mode);
	
	CardGame getCardGame(Difficulty  d,Mode mode);
	
}

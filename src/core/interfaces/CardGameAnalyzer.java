package core.interfaces;

import java.util.ArrayList;

/**
 * 牌局分析器，通过分析当前牌局给出一个比较好的解决方案
 * @author 87663
 */
public interface CardGameAnalyzer {
	
	public void analyzerGame(CardGame cardGame);

	ArrayList<String> getTips();
	
	String getBestTips();
	
	boolean isGameFinish();
	
	boolean isGameOver();
	
}

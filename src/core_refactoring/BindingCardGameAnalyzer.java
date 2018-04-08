package core_refactoring;

import java.util.ArrayList;

public interface BindingCardGameAnalyzer {

	public void analyzerGame();

	ArrayList<String> getTips();
	
	String getBestTips();
	
	boolean isGameFinish();
	
	boolean isGameOver();
	
}

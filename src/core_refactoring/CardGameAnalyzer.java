package core_refactoring;

import java.util.ArrayList;

public interface CardGameAnalyzer {

	ArrayList<String> getTips();
	
	String getBestTips();
	
	boolean isGameFinish();
	
	boolean isGameOver();
	
	
}

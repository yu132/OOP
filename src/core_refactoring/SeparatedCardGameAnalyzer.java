package core_refactoring;

/**
 * 牌局分析器，通过分析当前牌局给出一个比较好的解决方案
 * @author 87663
 */
public interface SeparatedCardGameAnalyzer extends CardGameAnalyzer{
	
	public void analyzerGame(CardManagement cardManagement);
	
}

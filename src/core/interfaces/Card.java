package core.interfaces;

/**
 * 牌类，每个对象表示一张纸牌
 * @author 87663
 */
public interface Card {
	
	/**
	 * 判断另一张纸牌是否能够堆叠在本张纸牌上
	 * @param card 另一张纸牌
	 * @return 是否能够堆叠
	 */
	boolean isStackable(Card card);
	
	/**
	 * 获得卡牌数字
	 * @return 卡牌数字
	 */
	CardNumber getCardNumber();
	
	/**
	 * 获得卡牌花色
	 * @return 卡牌花色
	 */
	CardType getCardType();
	
}

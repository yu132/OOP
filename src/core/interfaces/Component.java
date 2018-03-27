package core.interfaces;

import java.util.ArrayList;

/**
 * 游戏组件类，表示游戏组件应该有的一些操作
 * @author 87663
 */
public interface Component {

	/**
	 * 移动最顶上的一张牌，去另一个组件
	 * @param c 牌移动到的组件
	 * @return 本次移动是否成功
	 */
	boolean sentSingleCard(Component c);
	
	/**
	 * 从另一个组件上得到一张牌
	 * @param card 得到的牌
	 * @return 本次移动是否成功
	 */
	boolean getSingleCard(Card card);
	
	/**
	 * 移动最顶上的几张牌，去另一个组件
	 * @param c 牌移动到的组件
	 * @param number 移动牌的数量
	 * @return 本次移动是否成功
	 */
	boolean sentCards(Component c,int number);
	
	/**
	 * 从另一个组件上得到几张牌
	 * @param cards 得到的牌
	 * @return 本次移动是否成功
	 */
	boolean getCards(ArrayList<Card> cards);
	
	/**
	 * 获取已经打开的牌
	 * @return 牌的枚举常量
	 */
	String getOpenedCard();
	
	/**
	 * 获取所有牌，看不见的用指定字符串填充
	 * @return  牌的枚举常量
	 */
	String getAllCard();
	
	/**
	 * 检查第几张牌是否能够移动
	 * @param index 牌的序号
	 * @return 能否移动
	 */
	boolean ismovable(int index);
	
	/**
	 * 撤销上一次移动
	 * @return
	 */
	boolean undo();
	
	/**
	 * 撤销所有的移动
	 * @return
	 */
	boolean undoAll();
	
}

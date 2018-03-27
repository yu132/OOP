package core.interfaces;

/**
 * 纸牌游戏类，每个类表示一个纸牌游戏
 * @author 87663
 */
public interface CardGame {

	/**
	 * @author 87663
	 * 枚举所有游戏组件
	 */
	public enum Components{
		DEALER,BOX_1,BOX_2,BOX_3,BOX_4,
		CARD_HEAP_1,CARD_HEAP_2,CARD_HEAP_3,
		CARD_HEAP_4,CARD_HEAP_5,CARD_HEAP_6,CARD_HEAP_7,
	}
	
	/**
	 * 移动一张纸牌的方法
	 * @param from 纸牌来源的组件
	 * @param to 纸牌去往的组件
	 * @return 是否能够完成这次移动
	 */
	public boolean moveSingleCard(Components from,Components to);
	
	/**
	 * 一次移动多张纸牌的方法
	 * @param from 纸牌来源的组件
	 * @param to 纸牌去往的组件
	 * @param number 一次移动纸牌的数量
	 * @return 是否能够完成这次移动
	 */
	public boolean moveCards(Components from,Components to,int number);
	
	/**
	 * 撤销上一次的移动
	 * @return 是否撤销成功，如果已经到开始的时候，则不能够撤销
	 */
	public boolean undo();
	
	/**
	 * 撤销所有的移动，回到纸牌游戏一开始的情况
	 * @return 是否撤销成功，如果已经到开始的时候，则不能够撤销
	 */
	public boolean undoAll();
	
	/**
	 * 是否能够撤销，如果已经到开始的时候，则不能够撤销
	 * @return 是否能够撤销
	 */
	public boolean undoable();
	
	/**
	 * 获取已经开始的时间长度
	 * @return 时间长度
	 */
	public long getTime();
	
	/**
	 * 获取当前得分
	 * @return 当前得分
	 */
	public int getPoint();
	
	/**
	 * 停止游戏
	 */
	void stopGame();
	
}

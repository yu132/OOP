package core.interfaces;

import java.util.ArrayList;

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
		
		DEALER,//发牌器
		
		BOX_1,BOX_2,BOX_3,BOX_4,//箱子1-4
		
		CARD_HEAP_1,CARD_HEAP_2,CARD_HEAP_3,//牌堆1-7
		CARD_HEAP_4,CARD_HEAP_5,CARD_HEAP_6,CARD_HEAP_7;
		
	}
	
	/**
	 * 移动一张纸牌的方法
	 * @param from 纸牌来源的组件
	 * @param to 纸牌去往的组件
	 * @return 是否能够完成这次移动
	 */
	public MoveState moveSingleCard(Components from,Components to);
	
	/**
	 * 一次移动多张纸牌的方法
	 * @param from 纸牌来源的组件
	 * @param to 纸牌去往的组件
	 * @param number 一次移动纸牌的数量
	 * @return 是否能够完成这次移动
	 */
	public MoveState moveCards(Components from,Components to,int number);
	
	/**
	 * 让发牌器换下一波牌
	 */
	public void nextCard();
	
	/**
	 * 获取某个组件内部显示出来的牌
	 * @param c 需要获取的组件
	 * @return 显示出来的牌
	 */
	public ArrayList<String> getOpenCard(Components c);
	
	/**
	 * 获取某个组件内部所有的牌，没显示的用指定字符串填充
	 * @param c 需要获取的组件
	 * @return 所有的牌
	 */
	public ArrayList<String> getAllCard(Components c);
	
	/**
	 * 获取提示
	 * @return 提示的信息
	 */
	public String getTips();
	
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
	 * 判断是否已经失败
	 * @return 是否已经失败
	 */
	public boolean isGameOver();
	
	/**
	 * 检查游戏是否已经胜利
	 * @return 游戏是否已经胜利
	 */
	public boolean isGameFinish();
	
	/**
	 *  在isGameFinish()返回true的时候自动完成牌堆
	 * @return 是否自动完成了游戏
	 */
	public boolean finishGame();
	
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

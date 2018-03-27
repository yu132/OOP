package core.interfaces;

/**
 * 计分器，计算上一次得分，并可以获取得分
 * @author 87663
 *
 */
public interface PointCounter {

	/**
	 * 通过上一次牌的移动来计算上一次得分
	 * @param c1 牌移动的来源
	 * @param c2 牌移动的去向
	 */
	void addPoint(Component c1,Component c2,MoveState ms);
	
	/**
	 * 获取当前得分
	 * @return 当前得分
	 */
	int getPoint();
	
}

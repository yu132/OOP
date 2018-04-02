package test;

import core.impl.PointCounterImpl;
import core.interfaces.Component;
import core.interfaces.MoveState;

public class test_PointCounterImpl {
	public static void main(String []arg){
		test_PointCounterImpl test = new test_PointCounterImpl();
		
		simple_BoxImpl simBox = new simple_BoxImpl();
		simple_DealerImpl simDealer = new simple_DealerImpl();
		simple_CardHeapImpl simHeap = new simple_CardHeapImpl();
		
		PointCounterImpl test_point = new PointCounterImpl();
		
		test.test_getPoint(test_point);
		test.test_addPoint(test_point,simDealer , simHeap, MoveState.SUCCESS);
		test.test_getPoint(test_point);
		test.test_addPoint(test_point,simDealer , simBox, MoveState.SUCCESS);
		test.test_getPoint(test_point);
		test.test_addPoint(test_point,simBox , simHeap, MoveState.SUCCESS);
		test.test_getPoint(test_point);
		
		test.test_undoAll(test_point);
		test.test_getPoint(test_point);
		
	
	
	}	
	
	private void test_undoAll(PointCounterImpl test_point){
		System.out.println("test_undoAll		"+test_point.undoAll());
	}
	
	private void test_undo(PointCounterImpl test_point){
		System.out.println("test_undo		"+test_point.undo());
	}
	
	private void test_getPoint(PointCounterImpl test_point){
		System.out.println("test_getPoint		"+test_point.getPoint());
	}
	
	private void test_addPoint(PointCounterImpl test_point,Component c1, Component c2, MoveState ms){
		System.out.println("test_addPoint		");
		test_point.addPoint(c1,c2,ms);
	}
}

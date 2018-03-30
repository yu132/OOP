package test;

import core.impl.TimerImpl;

public class test_TimerImpl {
	public static void main(String []args){
		TimerImpl test = new TimerImpl();
		
		try {  
		    Thread.sleep(1000);  
		} catch (InterruptedException e) {  
		    e.printStackTrace();   
		}  
		
		System.out.println(test.getTime());
		
		try {  
		    Thread.sleep(3000);  
		} catch (InterruptedException e) {  
		    e.printStackTrace();   
		}  
		
		System.out.println(test.getTime());
	}
}

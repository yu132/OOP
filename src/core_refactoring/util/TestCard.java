package core_refactoring.util;

import java.util.ArrayList;

import core_refactoring.CardNumber;
import core_refactoring.CardType;
import core_refactoring.Components;
import core_refactoring.impl.CardGameImpl;

public class TestCard {
	
	public void testMove(Components from, Components to, int number,CardGameImpl c){
		if(!count(c)){
			throw new RuntimeException("MOVE: from:"+from+" to:"+to+" number:"+number);
		}
	}
	public void testUndo(CardGameImpl c){
		if(!count(c)){
			throw new RuntimeException("UNDO");
		}
	}
	public void testNext(CardGameImpl c){
		if(!count(c)){
			throw new RuntimeException("NEXT");
		}
	}
	
	private boolean count(CardGameImpl c){
		int[] count=new int[52];
		for(int i=0;i<12;i++){
			ArrayList<String> allCards=c.getAllCard(Components.values()[i]);
			for(int j=0;j<allCards.size();j++){
				if(allCards.get(j).equals("null"))
					continue;
				System.out.println("#"+allCards.get(j));
				String[] sp=allCards.get(j).split("_");
				int index=CardType.valueOf(sp[0].trim()).ordinal()*13+CardNumber.valueOf(sp[1].trim()).ordinal();
				count[index]++;
				if(count[index]>=2){
					return false;
				}
			}
		}
		return true;
	}
}

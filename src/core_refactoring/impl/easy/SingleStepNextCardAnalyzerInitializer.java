package core_refactoring.impl.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core_refactoring.Card;
import core_refactoring.CardInitializer;
import core_refactoring.CardManagement;
import core_refactoring.Components;
import core_refactoring.impl.CardImpl;
import core_refactoring.util.RandomUniqueNumber;

public class SingleStepNextCardAnalyzerInitializer implements CardInitializer{
	
	private static Map<Integer,Components> map=new HashMap<>();
	
	static {
		map.put(0, Components.DEALER);
		map.put(1, Components.CARD_HEAP_1);
		map.put(2, Components.CARD_HEAP_2);
		map.put(3, Components.CARD_HEAP_3);
		map.put(4, Components.CARD_HEAP_4);
		map.put(5, Components.CARD_HEAP_5);
		map.put(6, Components.CARD_HEAP_6);
		map.put(7, Components.CARD_HEAP_7);
	}
	
	private CardManagement cardGame;
	
	private CardChecker cardChecker=new CardChecker();
	

	@Override
	public Card getCard(Components c) {
		String[] movesp=cardGame.lastMove().split(" ");
		Components from=Components.valueOf(movesp[0]);
		Components to=Components.valueOf(movesp[1]);
		
		ArrayList<Card> collector=new ArrayList<>();
		
		for(int i=0;i<=7;i++){
			Components temp=map.get(i);
			if(temp==from||temp==to)
				continue;
			if(temp==Components.DEALER){
				ArrayList<String> cards=cardGame.getTopCard(temp);
				if(cards.size()>=1)
					collector.add(CardImpl.valueOf(cards.get(cards.size()-1)));
			}else{
				ArrayList<String> cards=cardGame.getTopCard(temp);
				if(cards.size()>=2){
					collector.add(CardImpl.valueOf(cards.get(0)));
					collector.add(CardImpl.valueOf(cards.get(cards.size()-1)));
				}else if(cards.size()==1){
					collector.add(CardImpl.valueOf(cards.get(0)));
				}
			}
		}
		
		RandomUniqueNumber r=new RandomUniqueNumber(0, collector.size()-1);
		for(int i=0;i<collector.size();i++){
			int index=r.getNum();
			
			Card nowCard=collector.get(index);
			
			Card nextCard=nowCard.getCardSrackable();
			if(cardChecker.checkCard(nextCard)){
				return nextCard;
			}
		}
		return cardChecker.getRandomUnusedCard();
	}

	
	
}

package core.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import core.interfaces.Card;
import core.interfaces.CardHeap;
import core.interfaces.CardNumber;
import core.interfaces.Component;
import core.interfaces.MoveState;

public class CardHeapImpl implements CardHeap{
	private int uncoveredCard;
	private int totalNumber;
	private int start_totalNumber;
	private boolean openNew=false;
	CardHeapImpl(int number){
		uncoveredCard = number-1;
		start_totalNumber=totalNumber = number;
	}
	/**
	 * 一个栈，用于存储当前在该牌堆内的卡牌
	 */
	private Deque<Card> cardStack=new ArrayDeque<>();
	
	/**
	 * 一个栈，用于存储每一次对这个牌堆操作前的快照
	 */
	private Deque<String> snapshot=new ArrayDeque<>();
	
	
	/**
	 * 内部方法，获取当前牌堆内卡牌的快照，用于撤销操作
	 * @return 当前卡牌的快照，以String方式存储
	 */
	private String getSnapshot(){
		StringBuilder sb=new StringBuilder(cardStack.size()*10);
		boolean f=true;
		for(Card c:cardStack){
			if(f){
				f=false;
				sb.append(c.toString());
			}else
				sb.append(" "+c.toString());
		}
		return sb.toString();
	}
	
	@Override
	public MoveState sentSingleCard(Component c) {
		if(cardStack.isEmpty())
			return MoveState.ILLEGAL_MOVE;
		
		Card topCard=cardStack.peek();
		MoveState ms=c.getSingleCard(topCard);
		if(ms==MoveState.SUCCESS){
			if(totalNumber-1 == uncoveredCard ){
				uncoveredCard--;
				openNew=true;
			}
				
			totalNumber--;
			snapshot.push(getSnapshot());
			cardStack.pop();
		}
		
		return ms;
	}

	@Override
	public MoveState getSingleCard(Card card) {
		if(cardStack.isEmpty()){
			if(card.getCardNumber()==CardNumber.KING){
				snapshot.push(getSnapshot());
				cardStack.push(card);
				totalNumber++;
				openNew=false;
				return MoveState.SUCCESS;
			}else
				return MoveState.HEAP_TOP_IS_NOT_K;
		}
		
		Card topCard=cardStack.peek();
		MoveState temp=topCard.isStackableInHeap(card);
		if(temp==MoveState.SUCCESS){
			snapshot.push(getSnapshot());
			cardStack.push(card);
			totalNumber++;
			openNew=false;
			return MoveState.SUCCESS;
		}else
			return temp;
	}

	@Override
	public MoveState sentCards(Component c, int number) {
		
		if(cardStack.isEmpty()||totalNumber-uncoveredCard<number)
			return MoveState.ILLEGAL_MOVE;
		ArrayList<Card> li = new ArrayList<>();
		Card topCard=cardStack.peek();//从小到大，循环完为最大数字牌面
		snapshot.push(getSnapshot());
		for(int i =0;i<number;i++){
			topCard = cardStack.pop();
			li.add(0, topCard);
		}
		MoveState ms=c.getSingleCard(topCard);
		if(ms==MoveState.SUCCESS){
			totalNumber-=number;
			if(totalNumber-uncoveredCard==number){
				uncoveredCard--;
				openNew=true;
			}
		}else{
			snapshot.pop();
			for(int i =0;i<number;i++){
				topCard = li.get(i);
				cardStack.push(topCard);;
			}
		}
		return ms;
		
	}

	@Override
	public MoveState getCards(ArrayList<Card> cards) {
		if(cardStack.isEmpty()){
			if(cards.get(0).getCardNumber()==CardNumber.KING){
				for(int i =0;i<cards.size();i++){
					cardStack.push(cards.get(i));
				}
				totalNumber+=cards.size();
				return MoveState.SUCCESS;
			}else
				return MoveState.HEAP_TOP_IS_NOT_K;
		}
		
		Card topCard=cardStack.peek();
		MoveState temp=topCard.isStackableInHeap(cards.get(0));
		if(temp==MoveState.SUCCESS){
			snapshot.push(getSnapshot());
			for(int i =0;i<cards.size();i++){
				cardStack.push(cards.get(i));
			}
			totalNumber+=cards.size();
			return MoveState.SUCCESS;
		}else
			return temp;
	}

	@Override
	public ArrayList<String> getOpenedCard() {
		ArrayList<String> temp=new ArrayList<>();
		boolean f=true;
		
		for(Card c:cardStack){
			if(f){
				f=false;
				temp.add(c.toString());
			}else
				temp.add(" "+c.toString());
		}
		
		return temp;
	}

	@Override
	public ArrayList<String> getAllCard() {
		ArrayList<String> temp=new ArrayList<>();
		boolean f=true;
		
		for(Card c:cardStack){
			if(f){
				f=false;
				temp.add(c.toString());
			}else
				temp.add(" "+c.toString());
			
		}
		for(int i=0;i<uncoveredCard;i++){
			temp.add(" "+"null");
		}
		return temp;
	}

	@Override
	public boolean ismovable(int index) {
		if (index<totalNumber-uncoveredCard){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean undo() {
		if(snapshot.isEmpty())
			return false;
		
		String last=snapshot.pop();
		cardStack.clear();
		String[] cards=last.split(" ");
		int visiableNumber = cards.length;
		if(openNew){
			uncoveredCard++;
		}
		totalNumber = uncoveredCard+visiableNumber;
		for(int i=0;i<cards.length;i++)
			cardStack.push(CardImpl.valueOf(cards[i]));
		
		return true;
	}

	@Override
	public boolean undoAll() {
		if(snapshot.isEmpty())
			return false;
		
		String last=snapshot.peekLast();
		cardStack.clear();
		snapshot.clear();
		String[] cards=last.split(" ");
		for(int i=0;i<cards.length;i++)
			cardStack.push(CardImpl.valueOf(cards[i]));
		uncoveredCard = start_totalNumber-1;
		totalNumber = start_totalNumber;
		return true;
	}

	@Override
	public boolean openCardLastRound() {
		// TODO Auto-generated method stub
		return openNew;
	}

}

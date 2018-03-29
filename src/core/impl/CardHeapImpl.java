package core.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import core.interfaces.Card;
import core.interfaces.CardHeap;
import core.interfaces.CardInitializer;
import core.interfaces.CardNumber;
import core.interfaces.Component;
import core.interfaces.MoveState;

public class CardHeapImpl implements CardHeap{
	/**
	 * 保存未翻开纸牌数目
	 */
	private int unopenedCard;
	
	/**
	 * 保存牌堆中纸牌总数
	 */
	private int totalNumber;
	
	/**
	 * 记录初始化时纸牌总数
	 */
	private int start_totalNumber;
	
	/**
	 * 标记当前操作是否有新的纸牌翻开
	 */
	private boolean openNew=false;
	
	/**
	 * 生成纸牌
	 */
	CardInitializer generate;
	
	/**
	 * 构造函数
	 */
	CardHeapImpl(int number,CardInitializer a){
		unopenedCard = number-1;
		start_totalNumber=totalNumber = number;
		generate = a;
		Card begin = a.getCard();
		cardStack.push(begin);
		openedcard2.push(begin);
		
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
	 * 一个栈，用于存储翻开过的纸牌
	 */
	private Deque<Card> openedcard=new ArrayDeque<>();
	
	/**
	 * 一个栈，用于存储翻开过的所有纸牌 
	 */
	private Deque<Card> openedcard2=new ArrayDeque<>();
	
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
			if(totalNumber-1 == unopenedCard ){
				
				totalNumber--;
				snapshot.push(getSnapshot());
				cardStack.pop();
				
				if(unopenedCard>0&&openedcard.isEmpty()){
					Card temp = generate.getCard();
					cardStack.push(temp);
					openedcard2.push(temp);
					openNew=true;
				}else if(unopenedCard>0&&!openedcard.isEmpty()){
					Card temp = openedcard.pop();
					cardStack.push(temp);
					openNew=true;
				}
				unopenedCard--;
				
			}else{
				totalNumber--;
				snapshot.push(getSnapshot());
				cardStack.pop();
			}

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
		
		if(cardStack.isEmpty()||totalNumber-unopenedCard<number)
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
			if(totalNumber-unopenedCard==number){
				unopenedCard--;
				openNew=true;
				if(openedcard.isEmpty()){
					Card temp = generate.getCard();
					cardStack.push(temp);
					openedcard2.push(temp);
				}else{
					Card temp = openedcard.pop();
					cardStack.push(temp);
				}
				
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
		for(int i=0;i<unopenedCard;i++){
			temp.add(" "+"null");
		}
		return temp;
	}

	@Override
	public boolean ismovable(int index) {
		if (index>totalNumber-unopenedCard){
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
		if(openNew){
			unopenedCard++;
			openedcard.push(cardStack.peek());
		}
		cardStack.clear();
		String[] cards=last.split(" ");
		int visiableNumber = cards.length;
		
		for(int i=0;i<cards.length;i++)
			cardStack.push(CardImpl.valueOf(cards[i]));
		
		totalNumber = unopenedCard+visiableNumber;

		return true;
	}

	@Override
	public boolean undoAll() {
		if(snapshot.isEmpty())
			return false;
		
		String last=snapshot.peekLast();
		cardStack.clear();
		snapshot.clear();
		openedcard.clear();
		String[] cards=last.split(" ");
		for(int i=0;i<cards.length;i++)
			cardStack.push(CardImpl.valueOf(cards[i]));
		unopenedCard = start_totalNumber-1;
		totalNumber = start_totalNumber;
		
		for(Card c:openedcard2){
			openedcard.push(c);
		}
		
		return true;
	}

	@Override
	public boolean openCardLastRound() {
		// TODO Auto-generated method stub
		return openNew;
	}

}

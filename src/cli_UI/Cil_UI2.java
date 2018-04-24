package cli_UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import core_refactoring.CardGame;
import core_refactoring.Components;
import core_refactoring.impl.CardGameFactoryImpl;
import core_refactoring.impl.hard.SolvableGameCardInitializer.SolvableCardGame.Operation;


public class Cil_UI2 {
	static private Map<String,Components> strMap=new HashMap<>();
	
	public static ArrayList<Operation> mvlist;
	
	static{
		strMap.put("d", Components.DEALER);
		strMap.put("dealer", Components.DEALER);
		
		strMap.put("b1", Components.BOX_1);
		strMap.put("box1", Components.BOX_1);
		
		strMap.put("b2", Components.BOX_2);
		strMap.put("box2", Components.BOX_2);
		
		strMap.put("b3", Components.BOX_3);
		strMap.put("box3", Components.BOX_3);
		
		strMap.put("b4", Components.BOX_4);
		strMap.put("box4", Components.BOX_4);
		
		strMap.put("h1", Components.CARD_HEAP_1);
		strMap.put("heap1", Components.CARD_HEAP_1);
		
		strMap.put("h2", Components.CARD_HEAP_2);
		strMap.put("heap2", Components.CARD_HEAP_2);
		
		strMap.put("h3", Components.CARD_HEAP_3);
		strMap.put("heap3", Components.CARD_HEAP_3);
		
		strMap.put("h4", Components.CARD_HEAP_4);
		strMap.put("heap4", Components.CARD_HEAP_4);
		
		strMap.put("h5", Components.CARD_HEAP_5);
		strMap.put("heap5", Components.CARD_HEAP_5);
		
		strMap.put("h6", Components.CARD_HEAP_6);
		strMap.put("heap6", Components.CARD_HEAP_6);
		
		strMap.put("h7", Components.CARD_HEAP_7);
		strMap.put("heap7", Components.CARD_HEAP_7);
	}
	
	private CardGame core;

	
	private Runnable r=new Runnable() {
		@Override
		public void run() {
			cli();
		}
	};
	
	public boolean dealInput(String input){
		String[] inputsp=input.split(" ");
		switch(inputsp[0]){
		
		case "sg":
		case "startgame":
			if(inputsp.length!=1){
				System.out.println("Wrong input");
				break;
			}
			
			if(core==null){
				core=new CardGameFactoryImpl().getCardGame();
				System.out.println("Success");
			}else
				System.out.println("Game has started");
			
			for(int i=0;i<12;i++){
				System.out.println(Components.values()[i]+":"+core.getTopCard(Components.values()[i]));
			}
			break;
			
		case "show":
		case "sh":
			if(inputsp.length!=1){
				System.out.println("Wrong input");
				break;
			}
			
			for(int i=0;i<12;i++){
				System.out.println(Components.values()[i]+":"+core.getTopCard(Components.values()[i]));
			}
			break;
			
		case "ut":
		case "tip":
			System.out.println(core.getTips());
			String[] t=core.getTips().split(" ");
			if(t.length==4)
				for(int i=0;i<Integer.parseInt(t[3]);i++)
					core.nextCard();
			System.out.println(core.moveCards(Components.valueOf(t[0]), Components.valueOf(t[1]), Integer.valueOf(t[2])));
			break;
			
		case "ti":
		case "usetips":
			System.out.println(core.getTips());
			
			break;
			
			
		case "mvlist":
			System.out.println(mvlist);
			break;
		
		case "mv":
		case "move":
			if(inputsp.length!=3&&inputsp.length!=4){
				System.out.println("Wrong input");
				break;
			}
			if(inputsp.length==3){
				System.out.println(core.moveCards(strMap.get(inputsp[1]), strMap.get(inputsp[2]),1));
			}
			else if(inputsp.length==4)
				try{
					System.out.println(core.moveCards(strMap.get(inputsp[1]), strMap.get(inputsp[2]), Integer.valueOf(inputsp[3])));
				}catch(Exception e){
					System.out.println("Wrong input");
				}
			
			for(int i=0;i<12;i++){
				System.out.println(Components.values()[i]+":"+core.getTopCard(Components.values()[i]));
			}
			break;
			
		case "n":
		case "next":
		case "nextcard":
			core.nextCard();
			System.out.println("Success");
			
			for(int i=0;i<12;i++){
				System.out.println(Components.values()[i]+":"+core.getTopCard(Components.values()[i]));
			}
			break;
		
		case "ud":
		case "undo":
			if(inputsp.length!=1){
				System.out.println("Wrong input");
				break;
			}
			System.out.println(core.undo()?"Success":"Faliure");
			break;
		
		case "uda":
		case "undoall":
			if(inputsp.length!=1){
				System.out.println("Wrong input");
				break;
			}
			System.out.println(core.undoAll()?"Success":"Faliure");
			break;	
			
		case "re":
		case "restart":
			core=new CardGameFactoryImpl().getCardGame();
			System.out.println("Success");
			break;
			
		case "stop":
			System.out.println("Success");
			return true;	
		}

		return false;
	}
	
	public void cli(){
		Scanner scan=new Scanner(System.in);
		while(true){
			if(dealInput(scan.nextLine()))
				break;;
		}
	}
	
	public void startcli(){
		new Thread(r).start();
	}
	
	public static void main(String[] args) {
		new Cil_UI2().startcli();
	}
}

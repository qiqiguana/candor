package src;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class Battle extends Actions{
	public void perform(Collection args){
/*Arguments for battle follows the following order:
 * 1- Method name (attack target)
 * 		- vassal
 * 		- peasant
 * 		- knight's name
 * 		- viking
 * 		- magyar
 * 		- muslim
 * 2- Attacker's Name
 *
*/
		try {
			Iterator argsIter = args.iterator();
/*The following will call a method dinamically according 
 * to the item the player wants to buy
*/
			Class aMethod = this.getClass().forName("feudalism.Battle");
			Class[] argType = {String.class};
			Method methodObj = aMethod.getMethod((String)argsIter.next(), new Class[]{Collection.class});
			methodObj.invoke(this, args);
			GameAutoActions.saveAll();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int[] rollDice(int diceQty){
		int[] results = new int[6];
		Random rand = new Random();
		int die = 0;

		for(int i = 0; i < diceQty; i++){
			die = rand.nextInt(6);
			results[die]++;
		}
		return results;
	}


	public void vassal(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		ArrayList<Player> allies = new ArrayList<Player>();
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name
		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		if(fief.getRebelliousVassals().size() > 0){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				ArrayList<Vassals> rebelliousVassals = fief.getRebelliousVassals();
				allies = attackingKnight.getAllies();
				attackingKnight.allyTo(attackingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army

				if(allies.size() > 0){
					for(Player soldier : allies){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
				attackersDice += fief.getLoyalVassals().size();
				defendersDice = fief.getRebelliousVassals().size();
				System.out.println("Attacker's dice: " + attackersDice);
				System.out.println("Defender's dice: " + defendersDice);
				while(allies.size() > 0 && rebelliousVassals.size() > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(allies.size() > 0 && rebelliousVassals.size() > 0){
							System.out.println(i + " Rebellious: " + rebelliousVassals.size());
							System.out.println(i + " Allied Forces: " + allies.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < rebelliousVassals.size()) ? attackers[i] : rebelliousVassals.size();
							defenders[i] = (defenders[i] < allies.size()) ? defenders[i] : allies.size();
							switch(i){
								case 3: //autokill
									for(int b = 0; b < attackers[i]; b++){
										rebelliousVassals.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = new Random().nextInt(allies.size());
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											allies.remove(rand);
										}
									}
									break;
								case 4: //conditional kill
									if(attackers[i] > defenders[i] || (attackers[i] == defenders[i] && (fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											rebelliousVassals.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int c = 0; c < attackers[i]; c++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
											Player soldier = allies.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									break;
								case 1: //autowound
									for(int b = 0; b < attackers[i]; b++){
										rebelliousVassals.get(0).wound();
										rebelliousVassals.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										System.out.println(c);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.wound();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											allies.remove(rand);
										}
									}
									break;
			
								case 2: //conditional wound
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											rebelliousVassals.get(0).wound();
											rebelliousVassals.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int d = 0; d < defenders[i]; d++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0; //There is an error here!
											System.out.println("n: " + rand);
											Player soldier = allies.get(rand);
											System.out.println("got a " + soldier.getRank());
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals || 
													soldier instanceof Retainers || 
													soldier instanceof Mercenaries){
												soldier.wound();
												allies.remove(rand);
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				if(allies.size() > rebelliousVassals.size()){
					System.out.println("Allied forces won the battle " + rebelliousVassals.size());
					for(Player vassal : allies){
						if(vassal instanceof Vassals){
							vassal.wound(); //The wound method will cause the vassal to go back to the fiefdom he belongs to
						}
					}
					
				}	
				else if(allies.size() < rebelliousVassals.size()){
					if(CurrentPlayers.currentPlayers.get(fief.getOwner()).getDead()){
						fief.setName(CurrentPlayers.getKing().getName());
					}
					
				}
				
			}
		}
		else{
			System.out.println("There is no vassal rebellion in your fiefdom");
		}
		
	}

	public void peasant(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name
		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		ArrayList<Player> allies = new ArrayList<Player>();
		if(fief.getPeasants() > 0){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				int peasantArmy = fief.getPeasants();
				allies = attackingKnight.getAllies();
				attackingKnight.allyTo(attackingKnight.getName());
	
				System.out.println("Attacker's dice: " + attackersDice);
				if(allies.size() > 0){
					for(Player soldier : allies){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
				attackersDice += fief.getLoyalVassals().size();
				defendersDice = 1;
				
				while(allies.size() > 0 && peasantArmy > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(allies.size() > 0 && peasantArmy > 0){
							System.out.println(i + " Peasants: " + peasantArmy);
							System.out.println(i + " Allied Forces: " + allies.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < peasantArmy) ? attackers[i] : peasantArmy;
							defenders[i] = (defenders[i] < allies.size()) ? defenders[i] : allies.size();
							switch(i){ //auto kill
								case 1:
									for(int b = 0; b < attackers[i]; b++){
										peasantArmy --;
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() < 3){
												soldier.kill();
												System.out.println("Got a knight");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											allies.remove(rand);
										}
									}
									break;
								case 2: //conditional kill
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											peasantArmy--;
										}
									}
									else if(attackers[i] < defenders[i]){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() < 3){
												soldier.kill();
												System.out.println("Got a knight");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
									}
									break;
								case 3:
									for(int b = 0; b < attackers[i]; b++){
										peasantArmy--;
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() < 3){
												soldier.wound();
												System.out.println("Got a Knight");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											allies.remove(rand);
										}
									}
									break;
			
								case 4: //conditional wound
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											peasantArmy--;
										}
									}
									else if(attackers[i] < defenders[i]){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() < 3){
												soldier.kill();
												System.out.println("Got a Knight");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				if(allies.size() > peasantArmy){
					System.out.println("Allied forces won the battle " + peasantArmy);
					for(Player vassal : allies){
						if(vassal instanceof Vassals){
							vassal.wound(); //The wound method will cause the vassal to go back to the fiefdom he belongs to
						}
					}
				}
				else
					System.out.println("Peasants won the battle " + allies.size());
			}
		}
		else{
			System.out.println("There is no peasant uprising in this fiefdom");
		}
	}


	public void viking(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name
		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		System.out.println(fief.getLoyalVassals().size());
		ArrayList<Player> allies = new ArrayList<Player>();
		if(fief.getViking().size() > 0){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				ArrayList<Intruders> viking = fief.getViking();
				attackingKnight.allyTo(attackingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army
				allies = attackingKnight.getAllies();
				
				if(allies.size() > 0){
					for(Player soldier : allies){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
				defendersDice = viking.size();
				System.out.println("\nAttacker's dice: " + attackersDice);
				System.out.println("Defender's dice: " + defendersDice);
				while(allies.size() > 0 && viking.size() > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(allies.size() > 0 && viking.size() > 0){
							System.out.println(i + " Rebellious: " + viking.size());
							System.out.println(i + " Allied Forces: " + allies.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < viking.size()) ? attackers[i] : viking.size();
							defenders[i] = (defenders[i] < allies.size()) ? defenders[i] : allies.size();
							switch(i){
								case 1: //autokill
									for(int b = 0; b < attackers[i]; b++){
										viking.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = new Random().nextInt(allies.size());
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											allies.remove(rand);
										}
									}
									break;
								case 2: //conditional kill
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											viking.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int c = 0; c < attackers[i]; i++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
											Player soldier = allies.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									break;
								case 3: //autowound
									for(int b = 0; b < attackers[i]; b++){
										viking.get(0).wound();
										viking.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										System.out.println(c);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.wound();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											allies.remove(rand);
										}
									}
									break;
			
								case 4: //conditional wound
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											viking.get(0).wound();
											viking.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int d = 0; d < defenders[i]; d++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0; //There is an error here!
											System.out.println("n: " + rand);
											Player soldier = allies.get(rand);
											System.out.println("got a " + soldier.getRank());
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals || 
													soldier instanceof Retainers || 
													soldier instanceof Mercenaries){
												soldier.wound();
												allies.remove(rand);
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				if(allies.size() > viking.size()){
					System.out.println("Allied forces won the battle " + viking.size());
					for(Player vassal : allies){
						if(vassal instanceof Vassals){
							vassal.wound(); //The wound method will cause the vassal to go back to the fiefdom he belongs to
						}
					}
					
				}
				else
					System.out.println("Rebellious Vassals won the battle " + allies.size());
			}
		}else{
			System.out.println("There is no Viking invasion in this fiefdom");
		}
		
	}


	public void magyar(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name
		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		if(fief.getMagyar().size() > 0){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				ArrayList<Intruders> magyar = fief.getMagyar();
				attackingKnight.allyTo(attackingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army
				ArrayList<Player> allies = attackingKnight.getAllies();
				
				if(allies.size() > 0){
					for(Player soldier : allies){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
				defendersDice = magyar.size();
				System.out.println("Attacker's dice: " + attackersDice);
				System.out.println("Defender's dice: " + defendersDice);
				while(allies.size() > 0 && magyar.size() > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(allies.size() > 0 && magyar.size() > 0){
							System.out.println(i + " Rebellious: " + magyar.size());
							System.out.println(i + " Allied Forces: " + allies.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < magyar.size()) ? attackers[i] : magyar.size();
							defenders[i] = (defenders[i] < allies.size()) ? defenders[i] : allies.size();
							switch(i){
								case 1: //autokill
									for(int b = 0; b < attackers[i]; b++){
										magyar.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = new Random().nextInt(allies.size());
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											allies.remove(rand);
										}
									}
									break;
								case 2: //conditional kill
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											magyar.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int c = 0; c < attackers[i]; c++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
											Player soldier = allies.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									break;
								case 3: //autowound
									for(int b = 0; b < attackers[i]; b++){
										magyar.get(0).wound();
										magyar.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										System.out.println(c);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.wound();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											allies.remove(rand);
										}
									}
									break;
			
								case 4: //conditional wound
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											magyar.get(0).wound();
											magyar.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int d = 0; d < defenders[i]; d++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0; //There is an error here!
											System.out.println("n: " + rand);
											Player soldier = allies.get(rand);
											System.out.println("got a " + soldier.getRank());
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals || 
													soldier instanceof Retainers || 
													soldier instanceof Mercenaries){
												soldier.wound();
												allies.remove(rand);
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				if(allies.size() > magyar.size())
					System.out.println("Allied forces won the battle " + magyar.size());
				else
					System.out.println("Rebellious Vassals won the battle " + allies.size());
			}
		}else{
			System.out.println("There is no invasion by Magyars in this fiefdom");
		}
	}


	public void muslim(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name
		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		if(fief.getMuslim().size() > 0){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				ArrayList<Intruders> muslim = fief.getMuslim();
				ArrayList<Player> allies = attackingKnight.getAllies();
				attackingKnight.allyTo(attackingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army
				
				if(allies.size() > 0){
					for(Player soldier : allies){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
	
				defendersDice = muslim.size();
				System.out.println("Attacker's dice: " + attackersDice);
				System.out.println("Defender's dice: " + defendersDice);
				while(allies.size() > 0 && muslim.size() > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(allies.size() > 0 && muslim.size() > 0){
							System.out.println(i + " Rebellious: " + muslim.size());
							System.out.println(i + " Allied Forces: " + allies.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < muslim.size()) ? attackers[i] : muslim.size();
							defenders[i] = (defenders[i] < allies.size()) ? defenders[i] : allies.size();
							switch(i){
								case 1: //autokill
									for(int b = 0; b < attackers[i]; b++){
										muslim.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = new Random().nextInt(allies.size());
										Player soldier = allies.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											allies.remove(rand);
										}
									}
									break;
								case 2: //conditional kill
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											muslim.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int c = 0; c < attackers[i]; c++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
											Player soldier = allies.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									break;
								case 3: //autowound
									for(int b = 0; b < attackers[i]; b++){
										muslim.get(0).wound();
										muslim.remove(0);
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0;
										Player soldier = allies.get(rand);
										System.out.println(c);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.wound();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												allies.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											allies.remove(rand);
										}
									}
									break;
			
								case 4: //conditional wound
									if(attackers[i] > defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < attackers[i]; c++){
											muslim.get(0).wound();
											muslim.remove(0);
										}
									}
									else if(attackers[i] < defenders[i]){
										for(int d = 0; d < defenders[i]; d++){
											int rand = (allies.size() > 0) ? new Random().nextInt(allies.size()) : 0; //There is an error here!
											System.out.println("n: " + rand);
											Player soldier = allies.get(rand);
											System.out.println("got a " + soldier.getRank());
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													allies.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals || 
													soldier instanceof Retainers || 
													soldier instanceof Mercenaries){
												soldier.wound();
												allies.remove(rand);
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				if(allies.size() > muslim.size())
					System.out.println("Allied forces won the battle " + muslim.size());
				else
					System.out.println("Rebellious Vassals won the battle " + allies.size());
			}
		}
	}

	
	public void knight(Collection args){
		int attackersDice = 0;
		int defendersDice = 0;
		Iterator argsIter = args.iterator();
		argsIter.next(); //Method name

		Knight attackingKnight = CurrentPlayers.currentPlayers.get(argsIter.next());
		Fiefdoms fief = Map.map.get(attackingKnight.getLocation());
		Knight defendingKnight = CurrentPlayers.currentPlayers.get(fief.getOwner());

		if(attackingKnight != defendingKnight){
			if(attackingKnight.getLocation().equalsIgnoreCase(fief.getName())){
				attackingKnight.allyTo(attackingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army
				ArrayList<Player> attackingArmy = attackingKnight.getAllies();
				System.out.println("Attacking Army allies: " + attackingArmy.size());
				ArrayList<Player> defendingArmy = new ArrayList<Player>();
				if(defendingKnight.getLocation().equalsIgnoreCase(fief.getName())){
					defendingKnight.allyTo(defendingKnight.getName()); // The knight himself, retainers, and mercenaries (if any) will be added to the allied army
					defendingArmy = defendingKnight.getAllies();
				}
				else{
					defendingArmy.addAll(fief.getLoyalVassals());
				}
				if(defendingArmy.size() > 0){
					for(Player soldier : defendingArmy){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							defendersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							defendersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							defendersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							defendersDice += 1;
					}
				}
				
				if(attackingArmy.size() > 0){
					for(Player soldier : attackingArmy){
						if(soldier.getRank().equalsIgnoreCase("knight"))
							attackersDice += 3;
						if(soldier.getRank().equalsIgnoreCase("count"))
							attackersDice += 4;
						if(soldier.getRank().equalsIgnoreCase("duke") || soldier.getRank().equalsIgnoreCase("king"))
							attackersDice += 6;
						if(soldier.getRank().equalsIgnoreCase("vassal"))
							attackersDice += 1;
					}
				}
	
				System.out.println("Attacker's dice: " + attackersDice);
				System.out.println("Defender's dice: " + defendersDice);
				while(attackingArmy.size() > 0 && defendingArmy.size() > 0){
					int attackers[] = this.rollDice(attackersDice);
					int defenders[] = this.rollDice(defendersDice);
					for(int i = 1; i < 6; i++){
						if(attackingArmy.size() > 0 && defendingArmy.size() > 0){
							System.out.println(i + " Rebellious: " + defendingArmy.size());
							System.out.println(i + " Allied Forces: " + attackingArmy.size());
							System.out.println("Allies has thrown " + attackers[i] + " " + i + "'s");
							System.out.println("Rebellious Vassals has thrown " + defenders[i] + " " + i + "'s");
							attackers[i] = (attackers[i] < defendingArmy.size()) ? attackers[i] : defendingArmy.size();
							defenders[i] = (defenders[i] < attackingArmy.size()) ? defenders[i] : attackingArmy.size();
							switch(i){
								case 1: //autokill
									for(int c = 0; c < attackers[i]; c++){
										int rand = new Random().nextInt(defendingArmy.size());
										Player soldier = defendingArmy.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												defendingArmy.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											defendingArmy.remove(rand);
										}
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (attackingArmy.size() > 0) ? new Random().nextInt(attackingArmy.size()) : 0;
										
										Player soldier = attackingArmy.get(rand);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.kill();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												attackingArmy.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.kill();
											attackingArmy.remove(rand);
										}
									}
									break;
								case 2: //conditional kill
	//if Attacking army throws more 2's than the defending army
									if(attackers[i] > defenders[i]){
										for(int c = 0; c < attackers[i]; c++){
											int rand = (defendingArmy.size() > 0) ? new Random().nextInt(defendingArmy.size()) : 0;
											Player soldier = defendingArmy.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													defendingArmy.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
	// if defending army throws more 2's than the attacking army
									else if(attackers[i] < defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int c = 0; c < defenders[i]; c++){
											int rand = (attackingArmy.size() > 0) ? new Random().nextInt(attackingArmy.size()) : 0;
											Player soldier = attackingArmy.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.kill();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													attackingArmy.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									break;
								case 3: //autowound
									for(int c = 0; c < attackers[i]; c++){
										int rand = (defendingArmy.size() > 0) ? new Random().nextInt(defendingArmy.size()) : 0;
										Player soldier = defendingArmy.get(rand);
										System.out.println(c);
										if(soldier instanceof Knight){							
											if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
												soldier.wound();
												System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
											}
											else{
												defendingArmy.remove(rand);
												System.out.println("Killed a Knight");
											}
										}
										else if(soldier instanceof Vassals || 
												soldier instanceof Retainers || 
												soldier instanceof Mercenaries){
											soldier.wound();
											defendingArmy.remove(rand);
										}
									}
									for(int c = 0; c < defenders[i]; c++){
										int rand = (attackingArmy.size() > 0) ? new Random().nextInt(attackingArmy.size()) : 0;
										if(rand > 0){
											Player soldier = attackingArmy.get(rand);
											System.out.println(c);
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													attackingArmy.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals ||
													soldier instanceof Retainers ||
													soldier instanceof Mercenaries){
												soldier.wound();
												attackingArmy.remove(rand);
											}
										}
									}
									break;
			
								case 4: //conditional wound
	// if Attacking army throws more 2's than the defending army
									if(attackers[i] > defenders[i]){
										for(int c = 0; c < attackers[i]; c++){
											int rand = (defendingArmy.size() > 0) ? new Random().nextInt(defendingArmy.size()) : 0;
											Player soldier = defendingArmy.get(rand);
											if(soldier instanceof Knight){							
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													defendingArmy.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
										}
									}
									else if(attackers[i] < defenders[i] || 
											(attackers[i] == defenders[i] && 
											(fief.getFiefdomCastles() + fief.getPersonalCastles() > 0))){
										for(int d = 0; d < defenders[i]; d++){
											int rand = (attackingArmy.size() > 0) ? new Random().nextInt(attackingArmy.size()) : 0; //There is an error here!
											Player soldier = attackingArmy.get(rand);
											if(soldier instanceof Knight){
												if(((Knight)soldier).getWounds() + ((Knight)soldier).getKills() + 1 < 3){
													soldier.wound();
													System.out.println("Got a knight. " + (int)(((Knight)soldier).getWounds() + ((Knight)soldier).getKills()) + " slots have been filled");
												}
												else{
													attackingArmy.remove(rand);
													System.out.println("Killed a Knight");
												}
											}
											else if(soldier instanceof Vassals || 
													soldier instanceof Retainers || 
													soldier instanceof Mercenaries){
												soldier.wound();
												attackingArmy.remove(rand);
											}
										}
									}
									break;
									
								case 5:
									break;
									
								case 6:
									break;
								}
							}
						}
					}
				
				if(attackingArmy.size() > defendingArmy.size())
					System.out.println("Attacking army won the battle " + defendingArmy.size());
				else
					System.out.println("Defending army won the battle " + attackingArmy.size());
			
				if(attackingArmy.size() > 0){
					
				}
			}
		}
		else{
			System.out.println("You can only go to war against a fiefdom that doesn't belong to you.");
		}
	}
}
package src;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

public class Purchase extends Actions {

	public void perform(Collection args){
		try {
			Iterator argsIter = args.iterator();
			//The following will call a method dinamically according 
			// to the item the player wants to buy
			Class aMethod = this.getClass().forName("feudalism.Purchase");
			Class[] argType = {String.class};
			Method methodObj = aMethod.getMethod((String)argsIter.next(), new Class[]{Iterator.class});
			methodObj.invoke(this, argsIter);
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
	
	public void vassal(Iterator arg){
/* Collection must follow the following order:
 * 2 Quantity of vassals
 * 3 Buyer's name (remember that the king can buy any item for any knight)
 * 4 Fiefdom to where the purchased vassal(s) will be going to
 * (Position number 1 is where the method name goes, which is used 
 * above in the perform method)
 */
		int qty = Integer.parseInt(((String)arg.next()));
		String buyerName = (String)arg.next();
		Fiefdoms fiefdom = Map.map.get(((String)arg.next())); //

		Knight buyerObj = CurrentPlayers.currentPlayers.get(buyerName);
		if(fiefdom.getOwner() != null){
			if(buyerObj.getRank().equalsIgnoreCase("king") || buyerObj.isFiefdomMine(fiefdom.getName())){ //
				if (buyerObj.setTotalMoney(qty, false)){ //
					fiefdom.setLoyalVassals(qty, true); //
				}
				else{
					System.out.println("You cannot buy more vassals, you have insufficient funds"); //
				}
			}
			else{
				if(CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("count")
						|| CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("duke")){
					System.out.println("You can only buy a castle for a fiefdom that belongs to you");
				}
			}
		}
		else{
			System.out.println("Fiefdom doesn't belong to the kingdom");
		}
	}
	
	public void castleFiefdom(Iterator arg){
/* Collection must follow the following order:
 * 2 Quantity of Fiefdom castles
 * 3 Buyer's name (remember that the king can buy any item for any knight)
 * 4 Fiefdom to where the purchased Fiefdom castle(s) will be going to
 * (Position number 1 is where the method name goes, which is used 
 * above in the perform method)
 */
		int qty = Integer.parseInt(((String)arg.next()));
		String buyerName = (String)arg.next();
		Fiefdoms fiefdom = Map.map.get(((String)arg.next())); //

		Knight buyerObj = CurrentPlayers.currentPlayers.get(buyerName);
		if(fiefdom.getOwner() != null){
			if(buyerObj.getRank().equalsIgnoreCase("king") 
					|| (buyerObj.isFiefdomMine(fiefdom.getName())
					&& fiefdom.getViking().size() + fiefdom.getMagyar().size() +
					fiefdom.getMuslim().size() == 0)){ //
				if (buyerObj.setTotalMoney(qty * 25, false)){ //
					fiefdom.buyFiefdomCastles(qty); //
				}
					else{
						System.out.println("You cannot buy more Fiefdom castles, you have insufficient funds"); //
					}
			}
			else{
				if(CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("count")
						|| CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("duke")){
					System.out.println("You can only buy a castle for a fiefdom that belongs to you");
				}
			}
		}
		else{
			System.out.println("Fiefdom doesn't belong to the kingdom");
		}
	}
	
	public void castlePersonal(Iterator arg){
/* Collection must follow the following order:
 * 2 Quantity of Personal castles
 * 3 Buyer's name (remember that the king can buy personal castles for a regular knight)
 * 4 Fiefdom to where the purchased Fiefdom castle(s) will be going to
 * (Position number 1 is where the method name goes, which is used 
 * above in the perform method)
 */
		int qty = Integer.parseInt(((String)arg.next()));
		String buyerName = (String)arg.next();
		Fiefdoms fiefdom = Map.map.get(((String)arg.next())); //
	
		Knight buyerObj = CurrentPlayers.currentPlayers.get(buyerName);
		if(fiefdom.getOwner() != null){
//A king cannot buy personal castles for counts or dukes
			if((buyerObj.getRank().equalsIgnoreCase("king") 
					&& CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("knight"))
					|| (buyerObj.isFiefdomMine(fiefdom.getName())
					&& fiefdom.getViking().size() + fiefdom.getMagyar().size() +
					fiefdom.getMuslim().size() == 0)){ //
				if (buyerObj.setTotalMoney(qty * 10, false)){ //
					fiefdom.buyPersonalCastles(qty); //
				}
				else{
					System.out.println("You cannot buy more Personal castle, you have insufficient funds"); //
				}
			}
			else{
				if(buyerObj.getRank().equalsIgnoreCase("king")){
					System.out.println("You cannot buy a Personal Castle for Counts or Dukes");
				}
				else if(CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("count")
						|| CurrentPlayers.currentPlayers.get(fiefdom.getOwner()).getRank().equalsIgnoreCase("duke")){
					System.out.println("You can only buy a castle for a fiefdom that belongs to you");
				}
			}
		}
		else{
			System.out.println("Fiefdom doesn't belong to the kingdom: " + fiefdom.getName() + " owner: " + Map.map.get(fiefdom.getName()).getOwner());
		}
	}
	
	public void mercenary(Iterator arg){
/* Collection must follow the following order:
 * 2 Quantity of mercenaries
 * 3 Buyer's name (remember that the king can buy any item for any knight)
 * 4 Knight to where the purchased Fiefdom castle(s) will be going to
 * (Position number 1 is where the method name goes, which is used 
 * above in the perform method)
 */
		int qty = Integer.parseInt(((String)arg.next()));
		String buyerName = (String)arg.next();
		Knight knight = CurrentPlayers.currentPlayers.get(((String)arg.next())); //
		Knight buyerObj = CurrentPlayers.currentPlayers.get(buyerName);

		if(CurrentPlayers.currentPlayers.containsKey(buyerObj.getName()) && 
				CurrentPlayers.currentPlayers.containsKey(knight.getName())){
			if(buyerObj.getName().equals(knight.getName()) || 
					buyerObj.getRank().equals("king")){
				if (buyerObj.setTotalMoney(qty, false)){ //
					knight.addMercenaries(qty); //
				}
				else{
					System.out.println("You cannot buy more mercenaries, you have insufficient funds"); //
				}
			}
		}
		else{
			System.out.println("Inexistent Knight");
		}
	}
}
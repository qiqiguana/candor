package dsachat.share;

import java.io.Serializable;
import java.util.Vector;

import dsachat.share.hero.Hero;
import dsachat.share.hero.Talent;
import dsachat.share.hero.Weapon;

/**
 * This class represents a challenge.<br>
 * It is created by a client and sent to the server which executes the internal task.
 * <br>
 * A challenge can be a talent, spell, attribute or weapon.
 * @author bernshausen
 *
 */
public class Challenge implements Serializable{
//IDEA return lep,asp,aup loss	
	
	private static final long serialVersionUID = 6777899032409172338L;
	private Talent t = null;
	private int attrib1;
	private int attrib2;
	private int attrib3;
	private int mod;
	private String to;
	private boolean silent;
	private String attrib = null;
	private int attrValue;
	private Weapon w = null;
	private boolean attack=false;
	private int dmgkk;
	
	/**
	 * generate new challenge for spells and talents
	 * @param tal the talents or spells name
	 * @param modification difficulty
	 * @param h the hero to get the parameters
	 * @param silent response to sender?
	 * @param response the receiver of the results
	 */
	public Challenge(String tal,int modification, Hero h,boolean silent,String response){
		t = getTalent(tal,h);
		mod = modification;
		to=response;
		this.silent = silent;
		if(t!=null){
			getAttrib(h);
		}
	}
	
	/**
	 * create new attribute challenge
	 * @param attrShortcut the shortcut for the attribute
	 * @param h the hero to get the value
	 * @param modification the difficulty
	 * @param silent response to sender?
	 * @param response the receiver
	 */
	public Challenge(String attrShortcut,Hero h,int modification,
			boolean silent,String response){
		this.silent=silent;
		mod =modification;
		to =response;
		attrib = attrShortcut;
		attrValue = h.getAttrValue(attrShortcut);
	}
	
	/**
	 * create a new challenge for a fight
	 * @param w the weapon
	 * @param attack is attack? else defense
	 * @param h the hero
	 * @param modification difficulty
	 * @param silent response to sender?
	 * @param response the receiver
	 */
	public Challenge(Weapon w,boolean attack,
			Hero h,int modification,boolean silent, String response){
		this.w = w;
		this.mod=modification;
		this.silent=silent;
		this.to=response;
		this.attack = attack;
		dmgkk = 0;
		if(h.getAttrValue("KK") > w.getKkMin()){
			dmgkk = (h.getAttrValue("KK")-w.getKkMin())/w.getKkStep();
		}
		
	}
	
	/**
	 * get the named talent from hero
	 * @param tal the name
	 * @param h the hero
	 * @return the talent (or spell)
	 */
	private Talent getTalent(String tal,Hero h) {
		Vector<Talent> v = h.getTalents();
		for(int i=0; i<v.size(); i++){
			if(v.elementAt(i).getName().equalsIgnoreCase(tal)){
				return v.elementAt(i);
			}
		}
		return null;
	}
	
	/**
	 * Set the attributes for a talent (spell) challenge
	 * @param h the hero to get the values from
	 */
	private void getAttrib(Hero h) {
		String[] ch = t.getChallenge().split("/");
		if(ch.length!=3){
			return;
		}
		attrib1=getAttribFromHero(h,ch[0]);
		attrib2=getAttribFromHero(h,ch[1]);
		attrib3=getAttribFromHero(h,ch[2]);
	}
	
	/**
	 * return the value of the attribute
	 * @param h the hero
	 * @param attr the shortcut of the attribute
	 * @return the attributes value
	 */
	private int getAttribFromHero(Hero h, String attr){
		return h.getAttrValue(attr);
	}
	
	/**
	 * execute this challenge
	 * differ between talents, attributes or fight
	 * @return the result as string
	 */
	public String roll(){
		if(t!=null)
			return rollTalent();
		if(attrib != null)
			return rollAttr();
		if(w!=null){
			return rollFight();
		}
		return " missing Talent, Spell, ...";
	}
	
	/**
	 * roll the attack or defense with the given weapon
	 * @return the results as string
	 */
	private String rollFight() {
		int dices = w.getDices();
		int sides = w.getSides();
		int dmgMod=w.getDmgMod();
		int at = w.getAt();
		int damage =0;
		//set up possible responses
		String good = "good "+ (attack?"attack (Mod: "+mod+") with \""+w.getName()+"\": "+
				"Enemy's defense is cut in half. Would make " :
				"defense (Mod: "+mod+") with \""+w.getName()+"\": You gain one action.");
		String bumbler = "bumbler in "+(attack?"attack ":"defense ")+ " (Mod: "+mod+") with \""+w.getName()+"\"";
		String success = "successful "+(attack?"attack (Mod: "+mod+") with \""+w.getName()+"\". Would make ":
			"defense (Mod: "+mod+") with \""+w.getName()+"\".");
		String fail = "failed "+(attack?"attack (Mod: "+mod+") with \""+w.getName()+"\".":
			"defense (Mod: "+mod+") with \""+w.getName()+"\".");;
		//calculate damage
		for(int i=0; i<dices; i++){
			damage += Challenge.dice(sides);
		}
		damage +=dmgMod;
		//roll dices
		int roll = Challenge.dice(20);
		if(roll == 1){
			roll = Challenge.dice(20);
			if(roll + mod <= at ){
				damage *=2;
				damage += dmgkk;
				return good +(attack?damage+" damage.":"");
			}else{
				damage +=dmgkk;
				return success +(attack?damage+" damage.":"");
			}
		}
		if(roll == 20){
			roll = Challenge.dice(20);
			if(roll + mod > at ){
				//FIXME Patzertabelle
				int bumblerCode = Challenge.dice(6)+Challenge.dice(6);
				String bumblerTxt = "";
				switch(bumblerCode){
				case  2: bumblerTxt="Code: "+2;break;
				case  3: bumblerTxt="Code: "+3;break;
				case  4: bumblerTxt="Code: "+4;break;
				case  5: bumblerTxt="Code: "+5;break;
				case  6: bumblerTxt="Code: "+6;break;
				case  7: bumblerTxt="Code: "+7;break;
				case  8: bumblerTxt="Code: "+8;break;
				case  9: bumblerTxt="Code: "+9;break;
				case 10: bumblerTxt="Code: "+10;break; 
				case 11: bumblerTxt="Code: "+11;break;
				case 12: bumblerTxt="Code: "+12;break;
				default :
				}
				return bumbler + bumblerTxt;
			}else{
				return fail;
			}
		}
		if(roll +mod <=at){
			damage +=dmgkk;
			return success +(attack?damage+" damage.":"");
		}
		if(roll+mod>at){
			return fail;
		}
		return null;
	}

	/**
	 * execute talent challenge
	 * @return results as string
	 */
	private String rollTalent(){
		//set leading result
		String ch=" "+t.getName()+" ("+t.getChallenge()+") "+
			((mod!=0)?( (mod>0)?"+"+mod:mod ):"")+":\t";
		//roll dices
		int f = Challenge.dice(20);
		int s = Challenge.dice(20);
		int tr = Challenge.dice(20);
		//tell what was rolled
		ch +="rolled: "+ f+"|"+s+"|"+tr+" ";
		//calculate result
		if(f==1&&s==1&&tr==1){
			ch += "successed!!!!!(YEAH)";
		}else if( (f==1 && s==1)||(f==1&&tr==1)||(s==1&&tr==1)){
			//good roll
			ch += "automatic success";
		}else if(f==20&&s==20&&tr==20){
			//really bad
			ch += "FAILED!!!";
		}else if( (f==20 && s==20)||(f==20&&tr==20)||(s==20&&tr==20)){
			//good roll
			ch += "automatic fail";
		}else{
			int over =0;
			if(f > attrib1) over += f-attrib1;
			if(s > attrib2) over += s-attrib2;
			if(tr > attrib3) over += tr-attrib3;
			int talPoints = t.getValue() - mod;
			talPoints -= over;
			if(talPoints > 0)
				ch += "successed (TaP* "+talPoints+")";
			if(talPoints ==0){
				ch += "successed (TaP* 1)";
			}
			if(talPoints < 0){
				ch += "failed";
			}
		}
		return ch;
	}
	
	/**
	 * execute attribute challenge
	 * @return result as string
	 */
	private String rollAttr(){
		//intro
		String ch=" "+attrib+
			((mod!=0)?( (mod>0)?"+"+mod:mod ):"")+":\t";
		//roll
		int f = Challenge.dice(20);
		ch += "rolled: "+f;
		//calculate
		if(f==1){
			//good roll
			ch += "automatic success";
		}else if(f==20){
			//bad roll
			ch += "automatic fail";
		}else{
			int over =attrValue - (f+mod);
			ch+=(over < 0)?"  failed:  missing "+Math.abs(over)+" points":
						"  success:  left over "+ over +" points";
		}
		return ch;
	}
	
	/**
	 * represents this challenge
	 */
	public String toString(){
		String ret = "";
		ret += t.toString()+"\n";
		ret += "att: "+attrib1+" " + attrib2+" "+ attrib3+"\n";
		ret += "mod: "+mod+"\n";
		ret += "to: "+to;
		return ret;
	}
	
	/**
	 * roll a dice with variable sides
	 * @param sides the sides this dice should have
	 * @return a random number n between 1<=n<=sides
	 */
	public static int dice(int sides){
		int ret =-1;
		ret = (int)Math.rint((Math.random()*sides))%sides+1;
		return ret;
	}

	/**
	 * return the receiver of the results
	 * @return the receiver
	 */
	public String getTo() {
		return to;
	}

	/**
	 * return the silent mode
	 * @return the silent value
	 */
	public boolean isSilent() {
		return silent;
	}
}

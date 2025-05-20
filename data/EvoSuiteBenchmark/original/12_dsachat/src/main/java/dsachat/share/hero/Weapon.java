package dsachat.share.hero;

import java.io.Serializable;

/**
 * A weapon s used for a fight.
 * 
 * @author bernshausen
 *
 */
public class Weapon implements Serializable{
	
	private static final long serialVersionUID = 5844677371339479453L;
	//mostly all weapons that exist in DSA
	public static final Weapon FLORETT=new Weapon("Florett","1/-1","1W6+3",13,5,3);
	public static final Weapon DEGEN=new Weapon("Degen","0/-1","1W6+3",12,5,2);
	public static final Weapon HAND_RAUFEN=new Weapon("Hand","0/0","1W6+0",10,3,0);
	public static final Weapon HAND_RINGEN=new Weapon("Hand","0/0","1W6+0",10,3,0);
	public static final Weapon LANG_SCHWERT = new Weapon("(Lang-)Schwert","0/0","1W6+4",11,4,0);
	public static final Weapon ACHFAWAR=new Weapon("Achfawar","-1/0","1W6+4",13,4,0);//ini 0
	public static final Weapon AMAZONENSÄBEL=new Weapon("Amazonensäbel","0/0","1W6+4",11,4,1);//ini 1 
	public static final Weapon ANDERGASTER=new Weapon("Andergaster","0/-2","3W6+2",14,2,-3);//ini -3
	public static final Weapon ANDERTHALBHÄNDER=new Weapon("Anderthalbhänder","0/0","1W6+5",11,4,1);//ini 1 
	public static final Weapon ARBACH=new Weapon("Arbach","0/-1","1W6+4",12,3,0);//ini 0
	public static final Weapon BACCANAG=new Weapon("Baccanaq","0/-1","1W6+4",12,4,-1);//ini -1
	public static final Weapon BARBARENSCHWERT=new Weapon("Barbarenschwert","0/-1","1W6+5",13,2,-1);//ini -1
	public static final Weapon BARBARENSTREITAXT=new Weapon("Barbarenstreitaxt","-1/-4","3W6+2",15,1,-2);//ini -2
	public static final Weapon BASILISKENZUNGE=new Weapon("Basiliskenzunge","0/-1","1W6+2",12,4,-1);//ini -1
	public static final Weapon BASTARDSCHWERT=new Weapon("Bastardschwert","0/-1","1W6+5",11,3,0);//ini 0
	public static final Weapon BEIL=new Weapon("Beil","-1/-2","1W6+3",11,4,-1);//ini -1
	public static final Weapon BORONSICHEL=new Weapon("Boronssichel","0/-3","2W6+6",13,3,-2);//ini -2 
	public static final Weapon BRABAKSCHWNGEL=new Weapon("Brabakbengel","0/-1","1W6+5",13,3,0);//ini 0
	public static final Weapon BREITSCHWERT=new Weapon("Breitschwert","0/-1","1W6+4",12,3,0);//ini 0
	public static final Weapon BYAKKA=new Weapon("Byakka","0/-2","1W6+5",12,4,-1);//ini -1
	public static final Weapon DOLCH=new Weapon("Dolch","0/-1","1W6+1",12,5,0);//ini 0
	public static final Weapon DOPPELKUNCHOMER=new Weapon("Doppelkhunchomer","0/-1","1W6+6",12,2,-1);//ini -1
	public static final Weapon DRACHENT…TER=new Weapon("Drachentöter","-2/-4","3W6+5",20,1,-3);//ini -3
	public static final Weapon DRACHENZAHN=new Weapon("Drachenzahn","0/0","1W6+2",11,4,0);//ini 0
	public static final Weapon DREIGLIEDERSTAB=new Weapon("Dreigliederstab","1/1","1W6+2",13,4,2);//ini 2
	public static final Weapon DREIZACK=new Weapon("Dreizack","0/-1","1W6+4",13,3,0);//ini 0
	public static final Weapon DRESCHFLEGEL=new Weapon("Dreschflegel","-2/-3","1W6+3",12,3,-2);//ini -2
	public static final Weapon DSCHADRA=new Weapon("Dschadra","0/-3","1W6+5",12,4,-1);//ini -1
	public static final Weapon EBERFÄNGER=new Weapon("Eberfänger","0/-1","1W6+2",12,4,0);//ini 0
	public static final Weapon ECHSISCHE_AXT=new Weapon("Echsische Axt","0/-1","1W6+5",12,4,0);//ini 0
	public static final Weapon ECHSISCHES_SZEPTER=new Weapon("Echsisches Szepter","0/-1","1W6+3",11,3,-1);//ini -1
	public static final Weapon EFFERDBART=new Weapon("Efferdbart","0/-1","1W6+4",13,3,0);//ini 0
	public static final Weapon ELFISCHER_JAGDSPIESS=new Weapon("Elfischer Jagdspieß","0/-1","1W6+6",12,4,-1);//ini -1
	public static final Weapon ENTERMESSER=new Weapon("Entermesser","0/0","1W6+3",12,4,0);//ini 0
	public static final Weapon FACKEL=new Weapon("Fackel","-2/-3","1W6+0",11,5,-2);//ini -2
	public static final Weapon FELSSPLATER=new Weapon("Felsspalter","0/-2","2W6+2",14,2,-1);//ini -1
	public static final Weapon FLEISCHERBEIL=new Weapon("Fleischerbeil","-2/-3","1W6+2",11,4,-1);//ini -1
	public static final Weapon GEISSEL=new Weapon("Geißel","0/-4","1W6-1",14,5,-1);//ini -1
	public static final Weapon GLEFE=new Weapon("Glefe","0/-2","1W6+4",13,3,-1);//ini -1
	public static final Weapon GROSSER_SKLAVENTOD=new Weapon("Großer Sklaventod","0/-2","2W6+4",13,2,-1);//ini -2
	public static final Weapon GRUUFHAI=new Weapon("Gruufhai","-1/-3","1W6+6",14,2,-2);//ini -2
	public static final Weapon HAKENSPIESS=new Weapon("Hakenspieß","-1/-1","1W6+3",13,4,0);//ini 0
	public static final Weapon HAUMESSER=new Weapon("Haumesser","0/-2","1W6+3",13,3,-1);//ini -1
	public static final Weapon HAUSWEHR=new Weapon("Hauswehr","0/0","1W6+3",12,4,1);//ini 1
	public static final Weapon HELLEBARDE=new Weapon("Hellebarde","0/-1","1W6+5",12,3,0);//ini 0
	public static final Weapon HOLZFÄLLERAXT=new Weapon("Holzfälleraxt","-1/-4","2W6+0",12,2,-2);//ini -2
	public static final Weapon HOLZSPEER=new Weapon("Holzspeer","-1/-3","1W6+3",12,5,0);//ini 0
	public static final Weapon JAGDMESSER=new Weapon("Jagdmesser","0/-2","1W6+2",12,5,-1);//ini -1
	public static final Weapon JAGDMESSER_ELFISCHES=new Weapon("Jagdmesser (Elfisches)","0/-2","1W6+2",12,5,-1);//ini -1
	public static final Weapon JAGDSPIESS=new Weapon("Jagdspieß","0/-1","1W6+6",12,4,-1);//ini -1
	public static final Weapon KAMPFSTAB=new Weapon("Kampfstab","0/0","1W6+1",12,4,1);//ini 1
	public static final Weapon KETTENSTAB=new Weapon("Kettenstab","1/0","1W6+2",13,4,2);//ini 2
	public static final Weapon KEULE=new Weapon("Keule","0/-2","1W6+2",11,3,0);//ini 0
	public static final Weapon KHUNCHOMER=new Weapon("Khunchomer","0/0","1W6+4",12,3,0);//ini 0
	public static final Weapon KNOCHENKEULE_GROSS=new Weapon("Knochenkeule, groß","-1/-3","2W6+2",14,2,-2);//ini -2
	public static final Weapon KNOCHENKEULE_KLEIN=new Weapon("Knochenkeule, klein","-1/-1","1W6+2",11,4,0);//ini 0
	public static final Weapon KNOCHENKEULE_MITTELGR=new Weapon("Knochenkeule, mittelgr","0/-1","1W6+3",11,3,0);//ini 0
	public static final Weapon KN†PPEL=new Weapon("Knüppel","0/-2","1W6+1",11,4,0);//ini 0
	public static final Weapon KORSPIESS=new Weapon("Korspieß","0/-1","2W6+2",12,3,0);//ini 0
	public static final Weapon KRIEGSBEIL=new Weapon("Kriegsbeil","0/-1","1W6+4",13,2,0);//ini 0
	public static final Weapon KRIEGSFLEGEL=new Weapon("Kriegsflegel","-1/-2","1W6+6",12,2,-1);//ini -1
	public static final Weapon KRIEGSHAMMER=new Weapon("Kriegshammer","-1/-3","2W6+3",14,2,-2);//ini -2
	public static final Weapon KRIEGSLANZE=new Weapon("Kriegslanze","-2/-4","1W6+3",12,5,-2);//ini -2
	public static final Weapon KURZSCHWERT=new Weapon("Kurzschwert","0/-1","1W6+2",11,4,0);//ini 0
	public static final Weapon KUSLIKER_SÄBEL=new Weapon("Kusliker Säbel","0/0","1W6+3",12,4,1);//ini 1
	public static final Weapon LANGDOLCH=new Weapon("Langdolch","0/0","1W6+2",12,4,0);//ini 0
	public static final Weapon LINDWURMSCHLÄGER=new Weapon("Lindwurmschläger","0/-1","1W6+4",11,3,-1);//ini -1
	public static final Weapon MAGIERDEGEN=new Weapon("Magierdegen","0/-2","1W6+2",13,5,1);//ini 1
	public static final Weapon MAGIERRAPIER=new Weapon("Magierrapier","0/-1","1W6+3",12,5,1);//ini 1
	public static final Weapon MAGIERSTAB_KURZ=new Weapon("Magierstab (kurz)","-1/-1","1W6+0",11,4,0);//ini 0
	public static final Weapon MAGIERSTAB_SEHR_KURZ=new Weapon("Magierstab (sehr kurz)","-1/-1","1W6-1",11,5,-1);//ini -1
	public static final Weapon MAGIERSTAB_ALS_STAB=new Weapon("Magierstab als Stab","-1/-1","1W6+1",11,5,0);//ini 0
	public static final Weapon MAGIERSTAB_MIT_KRISTALLK_=new Weapon("Magierstab mit Kristallk.","-1/-2","1W6+1",11,4,-2);//ini -2
	public static final Weapon MENGBILAR=new Weapon("Mengbilar","0/-3","1W6+1",12,5,-2);//ini -2
	public static final Weapon MESSER=new Weapon("Messer","-2/-3","1W6+0",12,6,-2);//ini -2
	public static final Weapon MEUCHELDOLCH=new Weapon("Meucheldolch","0/-3","1W6+1",12,4,-1);//ini -1
	public static final Weapon MOLOKDESCHNAJA=new Weapon("Molokdeschnaja","0/0","1W6+4",11,3,0);//ini 0
	public static final Weapon MORGENSTERN=new Weapon("Morgenstern","-1/-2","1W6+5",14,2,1);//ini 1
	public static final Weapon NACHTWIND=new Weapon("Nachtwind","0/0","1W6+4",11,5,2);//ini 2
	public static final Weapon NEETHANER_LANGAXT=new Weapon("Neethaner Langaxt","-1/-3","2W6+2",13,4,-2);//ini -2
	public static final Weapon NEUNSCHWÄNZGE=new Weapon("Neunschwänzige","-1/-4","1W6+1",14,4,-1);//ini -1
	public static final Weapon OCHSENHERDE=new Weapon("Ochsenherde","-2/-4","3W6+3",17,1,-3);//ini -3
	public static final Weapon OGERFÄNGER=new Weapon("Ogerfänger","0/-2","1W6+2",12,4,0);//ini 0
	public static final Weapon OGERSCHELLE=new Weapon("Ogerschelle","-1/-3","2W6+2",15,1,-2);//ini -2
	public static final Weapon ORCHIDEE=new Weapon("Orchidee","-1/-2","1W6+1",12,5,0);//ini 0
	public static final Weapon ORKNASE=new Weapon("Orknase","0/-1","1W6+5",12,2,-1);//ini -1
	public static final Weapon PAILOS=new Weapon("Pailos","-1/-3","2W6+4",14,2,-2);//ini -2
	public static final Weapon PANZERSTECHER=new Weapon("Panzerstecher","-1/-1","1W6+4",13,3,0);//ini 0
	public static final Weapon PARTISANE=new Weapon("Partisane","0/-2","1W6+5",13,3,0);//ini 0
	public static final Weapon PEITSCHE=new Weapon("Peitsche","0/-5","1W6+0",14,5,0);//ini 0 //keine parade
	public static final Weapon PIKE=new Weapon("Pike","-1/-2","1W6+5",14,4,-2);//ini -2
	public static final Weapon RABENSCHNABEL=new Weapon("Rabenschnabel","0/0","1W6+4",10,4,0);//ini 0
	public static final Weapon RABENSCHNABEL_BORON=new Weapon("Rabenschnabel (Boron)","0/0","1W6+4",10,4,0);//ini 0
	public static final Weapon RAPIER=new Weapon("Rapier","0/0","1W6+3",12,4,1);//ini 1
	public static final Weapon REITERSÄBEL=new Weapon("Reitersäbel","0/0","1W6+4",11,4,1); //ini 1
	public static final Weapon REISSER_GEZAHNTE_AXT=new Weapon("Reißer (Gezahnte Axt)","0/0","2W6+4",13,3,0);//ini 0
	public static final Weapon RICHTSCHWERT=new Weapon("Richtschwert","-2/-4","3W6+4",13,2,-3);//ini -3
	public static final Weapon ROBBENT…TER=new Weapon("Robbentöter","0/0","1W6+3",12,4,0);//ini 0
	public static final Weapon RONDRAKAMM=new Weapon("Rondrakamm","0/0","2W6+2",12,3,0);//ini 0
	public static final Weapon RUNASKRAJA=new Weapon("Runaskraja","0/0","1W6+3",11,3,0);//ini 0
	public static final Weapon SCHEIBENDOLCH=new Weapon("Scheibendolch","0/-3","1W6+2",11,4,0);//ini 0
	public static final Weapon SCHLAGRING=new Weapon("Schlagring","-1/-2","1W6+2",10,3,0);//ini 0
	public static final Weapon SCHMIEDEHAMMER=new Weapon("Schmiedehammer","-1/-1","1W6+4",14,2,-1);//ini -1
	public static final Weapon SCHNITTER=new Weapon("Schnitter","0/0","1W6+5",14,4,0);//ini 0
	public static final Weapon SCHWERER_DOLCH=new Weapon("Schwerer Dolch","0/-1","1W6+2",12,4,0);//ini 0
	public static final Weapon SENSE=new Weapon("Sense","-2/-4","1W6+3",13,4,-2);//ini -2
	public static final Weapon SICHEL=new Weapon("Sichel","-2/-2","1W6+2",12,5,-2);//ini -2
	public static final Weapon SKLAVENTOD=new Weapon("Sklaventod","0/0","1W6+4",12,3,0);//ini 0
	public static final Weapon SKRAJA=new Weapon("Skraja","0/0","1W6+3",11,3,0);//ini 0
	public static final Weapon SONNENSZEPTER=new Weapon("Sonnenzepter","-1/-1","1W6+3",12,3,0);//ini 0
	public static final Weapon SPEER=new Weapon("Speer","0/-2","1W6+3",12,4,-1);//ini -1
	public static final Weapon SPITZHACKE=new Weapon("Spitzhacke","-2/-4","1W6+6",13,2,-3);//ini -3
	public static final Weapon STEINBEIL=new Weapon("Steinbeil","0/-1","1W6+4",13,2,-1);//ini -1
	public static final Weapon STEINERNES_BEIL=new Weapon("Steinernes Beil","-1/-2","1W6+3",11,4,-1);//ini -1
	public static final Weapon STEINMESSER=new Weapon("Steinmesser","-2/-3","1W6+1",12,6,-2);//ini -2
	public static final Weapon STOCKDEGEN=new Weapon("Stockdegen","-1/-3","1W6+3",12,5,0);//ini 0
	public static final Weapon STOSSSPEER=new Weapon("Stoßspeer","0/-1","2W6+2",11,4,-1);//ini -1
	public static final Weapon STREITAXT=new Weapon("Streitaxt","0/-1","1W6+4",13,2,0);//ini 0
	public static final Weapon STREITKOLBEN=new Weapon("Streitkolben","0/-1","1W6+4",11,3,3);//ini 3 //TODO check ini
	public static final Weapon STUHLBEIN=new Weapon("Stuhlbein","-1/-1","1W6+0",11,5,-1);//ini -1
	public static final Weapon STURMSENSE=new Weapon("Sturmsense","-1/-2","1W6+4",13,3,-1);//ini -1
	public static final Weapon SÄBEL=new Weapon("Säbel","0/0","1W6+3",12,4,1);//ini 1
	public static final Weapon SÄGEFISCHSCHWERT=new Weapon("Sägefischschwert","-2/1","1W6+2",12,3,-1);//ini -1
	public static final Weapon TURNIERLANZE=new Weapon("Turnierlanze","-1/-4","1W6+2",12,5,-2);//ini -2
	public static final Weapon TURNIERSCHWERT=new Weapon("Turnierschwert","0/0","1W6+3",11,5,0);//ini 0
	public static final Weapon TUZAKMESSER=new Weapon("Tuzakmesser","0/0","1W6+6",12,4,1);//ini 1
	public static final Weapon VETERANENHAND=new Weapon("Veteranenhand","0/-1","1W6+2",12,4,-1);//ini -1
	public static final Weapon VORSCHLAGHAMMER=new Weapon("Vorschlaghammer","-2/-4","1W6+5",13,2,-3);//ini -3
	public static final Weapon VULKANGLASDOLCH=new Weapon("Vulkanglasdolch","-2/-3","1W6-1",12,5,-2);//ini -2
	public static final Weapon WAQQIF=new Weapon("Waqqif","-1/-3","1W6+2",12,5,-2);//ini -2
	public static final Weapon WARUNKER_HAMMER=new Weapon("Warunker Hammer","0/-1","1W6+6",14,3,-1);//ini -1
	public static final Weapon WOLFSMESSER_ELF_RAPIER=new Weapon("Wolfsmesser (elf.Rapier)","0/0","1W6+3",12,4,1);//ini 1
	public static final Weapon WURMSPIESS=new Weapon("Wurmspieß","0/-2","1W6+5",13,4,0);//ini 0
	public static final Weapon ZWEIHÄNDER=new Weapon("Zweihänder","0/-1","2W6+4",12,3,-1);//ini -1	
	public static final Weapon ZWEILILIEN=new Weapon("Zweililien","1/-1","1W6+3",12,4,1);//ini 1
	public static final Weapon ZWERGENSCHLÄGEL=new Weapon("Zwergenschlägel","0/-1","1W6+5",13,3,-1);//ini -1
	public static final Weapon ZWERGENSKRAJA=new Weapon("Zwergenskraja","0/0","1W6+3",11,3,0);//ini 0

	
	private String name;
	private int atMod=0;
	private int deMod=0;
	private String wm="";
	private int dices=0;
	private int sides=0;
	private int dmgMod=0;
	private String dmg="";
	private int kkMin=0;
	private int kkStep=0;
	private FightValue type;
	private int iniMod=0;
	
	/**
	 * default constructor
	 */
	public Weapon(){name ="";}
	
	/**
	 * create a new weapon without any parameters
	 * @param name the name of this weapon
	 */
	public Weapon(String name){
		this.name=name;
	}
	
	/**
	 * Create a new fully useable weapon
	 * @param name the name
	 * @param wm how good the weapon ca be used for atttack and defense
	 * @param damage how much damage it makes
	 * @param kkmin base KK for calculating aditional damage
	 * @param kkstep every kkstep points KK over kkmin adds one point damage
	 */
	public Weapon(String name, String wm, String damage, int kkmin, int kkstep, int iniMod){
		this.name=name;
		this.wm = wm;
		String[] strA = wm.split("/");
		this.atMod=Integer.parseInt(strA[0]);
		this.deMod=Integer.parseInt(strA[1]);
		dmg = damage;
		strA = dmg.split("[wWdD]");
		this.dices=Integer.parseInt(strA[0]);
		strA = strA[1].split("[\\+-]");
		this.sides=Integer.parseInt(strA[0]);
		this.dmgMod = Integer.parseInt(strA[1]);
		this.kkMin=kkmin;
		this.kkStep=kkstep;
		this.iniMod=iniMod;
	}
	
	/**
	 * represents this weapon as string
	 */
	public String toString(){
		return name + " WM: "+wm+" Damage: "+dmg +" TP/KK: "+kkMin+"/"+kkStep;
	}
	
	/**
	 * set the fight value type of this weapon
	 * @param fv the fight value
	 */
	public void setType(FightValue fv){
		type =fv;
	}
	/**
	 * get the type of this weapon
	 * @return the type
	 */
	public FightValue getType(){
		return type;
	}
	/**
	 * return the name of this weapon
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * return the number of dices used for calculating the damage
	 * @return number of dices
	 */
	public int getDices() {
		return dices;
	}
	/**
	 * return the number of sides the dices have (mostly 6)
	 * @return the number of sides
	 */
	public int getSides() {
		return sides;
	}
	/**
	 * get the damage modificator added to the rolled damage
	 * @return the damage modification
	 */
	public int getDmgMod() {
		return dmgMod;
	}
	/**
	 * get the base KK of this weapon
	 * @return the base KK
	 */
	public int getKkMin() {
		return kkMin;
	}
	/**
	 * return the steps for calculating additional damage
	 * @return steps for KK
	 */
	public int getKkStep() {
		return kkStep;
	}
	
	/**
	 * return the attack value of the fightValue
	 * @return the attack value
	 */
	public int getAt(){
		return type.getAttack()+atMod;
	}
	
	/**
	 * get the defense value of the fight value this weapon is used with
	 * @return the defense value
	 */
	public int getDe(){
		return type.getDefense()+deMod;
	}
	/**
	 * Get the initiative modification
	 * @return the initiative modification
	 */
	public int getIniMod() {
		return iniMod;
	}
}

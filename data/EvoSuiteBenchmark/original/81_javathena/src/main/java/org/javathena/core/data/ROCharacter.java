package org.javathena.core.data;

import javolution.util.FastMap;
import javolution.util.FastTable;

public class ROCharacter
{
    private static PersistenteData<IndexedFastMap<Integer, ROCharacter>> persistenceMethod;

    public static enum JOB
    {
	NOVICE(0), SWORDMAN(1), MAGE(2), ARCHER(3), ACOLYTE(4), MERCHANT(5), THIEF(6), KNIGHT(7), PRIEST(8), WIZARD(9), BLACKSMITH(10), HUNTER(11), ASSASSIN(12), KNIGHT2(13), CRUSADER(14), MONK(15), SAGE(16), ROGUE(17), ALCHEMIST(18), BARD(19), DANCER(20), CRUSADER2(21), WEDDING(22), SUPER_NOVICE(23), GUNSLINGER(24), NINJA(25), XMAS(26), SUMMER(27), MAX_BASIC(28),

	NOVICE_HIGH(4001), SWORDMAN_HIGH(4002), MAGE_HIGH(4003), ARCHER_HIGH(4004), ACOLYTE_HIGH(4005), MERCHANT_HIGH(4006), THIEF_HIGH(4007), LORD_KNIGHT(4008), HIGH_PRIEST(4009), HIGH_WIZARD(4010), WHITESMITH(4011), SNIPER(4012), ASSASSIN_CROSS(4013), LORD_KNIGHT2(4014), PALADIN(4015), CHAMPION(4016), PROFESSOR(4017), STALKER(4018), CREATOR(4019), CLOWN(4020), GYPSY(4021), PALADIN2(4022),

	BABY(4023), BABY_SWORDMAN(4024), BABY_MAGE(4025), BABY_ARCHER(4026), BABY_ACOLYTE(4027), BABY_MERCHANT(4028), BABY_THIEF(4029), BABY_KNIGHT(4030), BABY_PRIEST(4031), BABY_WIZARD(4032), BABY_BLACKSMITH(4022), BABY_HUNTER(4034), BABY_ASSASSIN(4035), BABY_KNIGHT2(4036), BABY_CRUSADER(4037), BABY_MONK(4038), BABY_SAGE(4039), BABY_ROGUE(4040), BABY_ALCHEMIST(4041), BABY_BARD(4042), BABY_DANCER(4043), BABY_CRUSADER2(4044), SUPER_BABY(4045),

	TAEKWON(4046), STAR_GLADIATOR(4047), STAR_GLADIATOR2(4048), SOUL_LINKER(4049), MAX(4050);
	JOB(int value)
	{
	    this.value = value;
	}

	public final int getValue()
	{
	    return this.value;
	}

	public static JOB parseFromValue(int val)
	{
	    switch (val)
	    {
		case 0:
		    return NOVICE;
		case 1:
		    return SWORDMAN;
		case 2:
		    return MAGE;
		case 3:
		    return ARCHER;
		case 4:
		    return ACOLYTE;
		case 5:
		    return MERCHANT;
		case 6:
		    return THIEF;
		case 7:
		    return KNIGHT;
		case 8:
		    return PRIEST;
		case 9:
		    return WIZARD;
		case 10:
		    return BLACKSMITH;
		case 11:
		    return HUNTER;
		case 12:
		    return ASSASSIN;
		case 13:
		    return KNIGHT2;
		case 14:
		    return CRUSADER;
		case 15:
		    return MONK;
		case 16:
		    return SAGE;
		case 17:
		    return ROGUE;
		case 18:
		    return ALCHEMIST;
		case 19:
		    return BARD;
		case 20:
		    return DANCER;
		case 21:
		    return CRUSADER2;
		case 22:
		    return WEDDING;
		case 23:
		    return SUPER_NOVICE;
		case 24:
		    return GUNSLINGER;
		case 25:
		    return NINJA;
		case 26:
		    return XMAS;
		case 27:
		    return SUMMER;
		case 28:
		    return MAX_BASIC;
		case 4001:
		    return NOVICE_HIGH;
		case 4002:
		    return SWORDMAN_HIGH;
		case 4003:
		    return MAGE_HIGH;
		case 4004:
		    return ARCHER_HIGH;
		case 4005:
		    return ACOLYTE_HIGH;
		case 4006:
		    return MERCHANT_HIGH;
		case 4007:
		    return THIEF_HIGH;
		case 4008:
		    return LORD_KNIGHT;

		case 4009:
		    return HIGH_PRIEST;
		case 4010:
		    return HIGH_WIZARD;
		case 4011:
		    return WHITESMITH;
		case 4012:
		    return SNIPER;
		case 4013:
		    return ASSASSIN_CROSS;
		case 4014:
		    return LORD_KNIGHT2;
		case 4015:
		    return PALADIN;
		case 4016:
		    return CHAMPION;
		case 4017:
		    return PROFESSOR;
		case 4018:
		    return STALKER;
		case 4019:
		    return CREATOR;
		case 4020:
		    return CLOWN;
		case 4021:
		    return GYPSY;
		case 4022:
		    return PALADIN2;
		case 4023:
		    return BABY;
		case 4024:
		    return BABY_SWORDMAN;
		case 4025:
		    return BABY_MAGE;
		case 4026:
		    return BABY_ARCHER;
		case 4027:
		    return BABY_ACOLYTE;
		case 4028:
		    return BABY_MERCHANT;
		case 4029:
		    return BABY_THIEF;
		case 4030:
		    return BABY_KNIGHT;
		case 4031:
		    return BABY_PRIEST;
		case 4032:
		    return BABY_WIZARD;
		case 4033:
		    return BABY_BLACKSMITH;
		case 4034:
		    return BABY_HUNTER;
		case 4035:
		    return BABY_ASSASSIN;
		case 4036:
		    return BABY_KNIGHT2;
		case 4037:
		    return BABY_CRUSADER;
		case 4038:
		    return BABY_MONK;
		case 4039:
		    return BABY_SAGE;
		case 4040: 
		    return BABY_ROGUE;
		case 4041:
		    return BABY_ALCHEMIST;
		case 4042:
		    return BABY_BARD;
		case 4043:
		    return BABY_DANCER;
		case 4044:
		    return BABY_CRUSADER2;
		case 4045:
		    return SUPER_BABY;
		case 4046:
		    return TAEKWON;
		case 4047:
		    return STAR_GLADIATOR;
		case 4048:
		    return STAR_GLADIATOR2;
		case 4049:
		    return SOUL_LINKER;
		case 4050:
		    return MAX;
		default:
		    return null;
	    }

	}

	private int value;
    };

    public final static int MAX_FRIENDS = 40;

    public static final int MAX_HOTKEY = 38;

    private int char_id;
    private int account_id;
    private int partner_id;
    private int father;
    private int mother;
    private int child;

    private int base_exp, job_exp;
    private int zeny;

    private JOB class_;

    private int status_point;
    private int skill_point;

    private int hp;
    private int max_hp;

    private int sp;
    private int max_sp;

    private int option;
    private short manner;
    private int karma;

    private short hair;
    private short hair_color;
    private short clothes_color;

    private int party_id;
    private int guild_id;
    private int pet_id;
    private int hom_id;
    private int mer_id;
    private int fame;

    // Mercenary Guilds Rank
    private int arch_faith;
    private int arch_calls;
    private int spear_faith;
    private int spear_calls;
    private int sword_faith;
    private int sword_calls;

    private short weapon; // enum weapon_type
    private short shield; // view-id
    private short head_top;
    private short head_mid;
    private short head_bottom;

    private String name;
    private int base_level;
    private int job_level;
    private short str;
    private short agi;
    private short vit;
    private short intel;
    private short dex;
    private short luk;
    private int slot;
    private int sex;
    private int mapip;
    private int mapport;

    private Point last_point;
    private Point save_point;

    private boolean show_equip;
    private short rename;

    private FastTable<Point> memoPoint;
    private FastTable<Item> inventory;
    private FastTable<Item> cart;
    private FastTable<Item> storage;
    // struct storage_data storage;
    private FastTable<Skill> skills;

    private Friend friends[]; // New friend system [Skotlex]

    private Hotkey hotkeys[];

    // @XStreamOmitField
    private FastMap<String, String> globalReg;

    public ROCharacter()
    {
	memoPoint = new FastTable<Point>();
	inventory = new FastTable<Item>();
	cart = new FastTable<Item>();
	storage = new FastTable<Item>();
	skills = new FastTable<Skill>();
	globalReg = new FastMap<String, String>();
	friends = new Friend[MAX_FRIENDS];
    }

    public void addGlobalReg(String key, String value)
    {
	globalReg.put(key, value);
    }

    public short getIntel()
    {
	return intel;
    }

    public void setIntel(short intel)
    {
	this.intel = intel;
    }

    public FastTable<Item> getInventory()
    {
	return inventory;
    }

    public void setInventory(FastTable<Item> inventory)
    {
	this.inventory = inventory;
    }

    public FastTable<Item> getCart()
    {
	return cart;
    }

    public void setCart(FastTable<Item> cart)
    {
	this.cart = cart;
    }

    public int getChar_id()
    {
	return char_id;
    }

    public void setChar_id(int char_id)
    {
	this.char_id = char_id;
    }

    public int getAccount_id()
    {
	return account_id;
    }

    public void setAccount_id(int account_id)
    {
	this.account_id = account_id;
    }

    public int getPartner_id()
    {
	return partner_id;
    }

    public void setPartner_id(int partner_id)
    {
	this.partner_id = partner_id;
    }

    public int getFather()
    {
	return father;
    }

    public void setFather(int father)
    {
	this.father = father;
    }

    public int getMother()
    {
	return mother;
    }

    public void setMother(int mother)
    {
	this.mother = mother;
    }

    public int getChild()
    {
	return child;
    }

    public void setChild(int child)
    {
	this.child = child;
    }

    public int getBase_exp()
    {
	return base_exp;
    }

    public void setBase_exp(int base_exp)
    {
	this.base_exp = base_exp;
    }

    public int getJob_exp()
    {
	return job_exp;
    }

    public void setJob_exp(int job_exp)
    {
	this.job_exp = job_exp;
    }

    public int getZeny()
    {
	return zeny;
    }

    public void setZeny(int zeny)
    {
	this.zeny = zeny;
    }

    public JOB getClass_()
    {
	return class_;
    }

    public void setClass_(JOB class_)
    {
	this.class_ = class_;
    }

    public int getStatus_point()
    {
	return status_point;
    }

    public void setStatus_point(int status_point)
    {
	this.status_point = status_point;
    }

    public int getSkill_point()
    {
	return skill_point;
    }

    public void setSkill_point(int skill_point)
    {
	this.skill_point = skill_point;
    }

    public int getHp()
    {
	return hp;
    }

    public void setHp(int hp)
    {
	this.hp = hp;
    }

    public int getMax_hp()
    {
	return max_hp;
    }

    public void setMax_hp(int max_hp)
    {
	this.max_hp = max_hp;
    }

    public int getSp()
    {
	return sp;
    }

    public void setSp(int sp)
    {
	this.sp = sp;
    }

    public int getMax_sp()
    {
	return max_sp;
    }

    public void setMax_sp(int max_sp)
    {
	this.max_sp = max_sp;
    }

    public int getOption()
    {
	return option;
    }

    public void setOption(int option)
    {
	this.option = option;
    }

    public short getManner()
    {
	return manner;
    }

    public void setManner(short manner)
    {
	this.manner = manner;
    }

    public int getKarma()
    {
	return karma;
    }

    public void setKarma(int karma)
    {
	this.karma = karma;
    }

    public short getHair()
    {
	return hair;
    }

    public void setHair(short hair)
    {
	this.hair = hair;
    }

    public short getHair_color()
    {
	return hair_color;
    }

    public void setHair_color(short hair_color)
    {
	this.hair_color = hair_color;
    }

    public short getClothes_color()
    {
	return clothes_color;
    }

    public void setClothes_color(short clothes_color)
    {
	this.clothes_color = clothes_color;
    }

    public int getParty_id()
    {
	return party_id;
    }

    public void setParty_id(int party_id)
    {
	this.party_id = party_id;
    }

    public int getGuild_id()
    {
	return guild_id;
    }

    public void setGuild_id(int guild_id)
    {
	this.guild_id = guild_id;
    }

    public int getPet_id()
    {
	return pet_id;
    }

    public void setPet_id(int pet_id)
    {
	this.pet_id = pet_id;
    }

    public int getHom_id()
    {
	return hom_id;
    }

    public void setHom_id(int hom_id)
    {
	this.hom_id = hom_id;
    }

    public int getMer_id()
    {
	return mer_id;
    }

    public void setMer_id(int mer_id)
    {
	this.mer_id = mer_id;
    }

    public int getFame()
    {
	return fame;
    }

    public void setFame(int fame)
    {
	this.fame = fame;
    }

    public int getArch_faith()
    {
	return arch_faith;
    }

    public void setArch_faith(int arch_faith)
    {
	this.arch_faith = arch_faith;
    }

    public int getArch_calls()
    {
	return arch_calls;
    }

    public void setArch_calls(int arch_calls)
    {
	this.arch_calls = arch_calls;
    }

    public int getSpear_faith()
    {
	return spear_faith;
    }

    public void setSpear_faith(int spear_faith)
    {
	this.spear_faith = spear_faith;
    }

    public int getSpear_calls()
    {
	return spear_calls;
    }

    public void setSpear_calls(int spear_calls)
    {
	this.spear_calls = spear_calls;
    }

    public int getSword_faith()
    {
	return sword_faith;
    }

    public void setSword_faith(int sword_faith)
    {
	this.sword_faith = sword_faith;
    }

    public int getSword_calls()
    {
	return sword_calls;
    }

    public void setSword_calls(int sword_calls)
    {
	this.sword_calls = sword_calls;
    }

    public short getWeapon()
    {
	return weapon;
    }

    public void setWeapon(short weapon)
    {
	this.weapon = weapon;
    }

    public short getShield()
    {
	return shield;
    }

    public void setShield(short shield)
    {
	this.shield = shield;
    }

    public short getHead_top()
    {
	return head_top;
    }

    public void setHead_top(short head_top)
    {
	this.head_top = head_top;
    }

    public short getHead_mid()
    {
	return head_mid;
    }

    public void setHead_mid(short head_mid)
    {
	this.head_mid = head_mid;
    }

    public short getHead_bottom()
    {
	return head_bottom;
    }

    public void setHead_bottom(short head_bottom)
    {
	this.head_bottom = head_bottom;
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getBase_level()
    {
	return base_level;
    }

    public void setBase_level(int base_level)
    {
	this.base_level = base_level;
    }

    public int getJob_level()
    {
	return job_level;
    }

    public void setJob_level(int job_level)
    {
	this.job_level = job_level;
    }

    public short getStr()
    {
	return str;
    }

    public void setStr(short str)
    {
	this.str = str;
    }

    public short getAgi()
    {
	return agi;
    }

    public void setAgi(short agi)
    {
	this.agi = agi;
    }

    public short getVit()
    {
	return vit;
    }

    public void setVit(short vit)
    {
	this.vit = vit;
    }

    public short getInt()
    {
	return intel;
    }

    public void setInt(short int_)
    {
	this.intel = int_;
    }

    public short getDex()
    {
	return dex;
    }

    public void setDex(short dex)
    {
	this.dex = dex;
    }

    public short getLuk()
    {
	return luk;
    }

    public void setLuk(short luk)
    {
	this.luk = luk;
    }

    public int getSlot()
    {
	return slot;
    }

    public void setSlot(int slot)
    {
	this.slot = slot;
    }

    public int getSex()
    {
	return sex;
    }

    public void setSex(char sex)
    {
	this.sex = sex;
    }

    public int getMapip()
    {
	return mapip;
    }

    public void setMapip(int mapip)
    {
	this.mapip = mapip;
    }

    public int getMapport()
    {
	return mapport;
    }

    public void setMapport(int mapport)
    {
	this.mapport = mapport;
    }

    public Point getLast_point()
    {
	return last_point;
    }

    public void setLast_point(Point last_point)
    {
	this.last_point = last_point;
    }

    public Point getSave_point()
    {
	return save_point;
    }

    public void setSave_point(Point save_point)
    {
	this.save_point = save_point;
    }

    public void addPoint(Point nPoint)
    {
	memoPoint.add(nPoint);
    }

    public Point getPoint(int index)
    {
	return memoPoint.get(index);
    }

    public FastTable<Point> getMemoPoints()
    {
	return memoPoint;
    }

    public void setMemoPoints(FastTable<Point> memo_point)
    {
	this.memoPoint = memo_point;
    }

    public boolean isShow_equip()
    {
	return show_equip;
    }

    public void setShow_equip(boolean show_equip)
    {
	this.show_equip = show_equip;
    }

    public short getRename()
    {
	return rename;
    }

    public void setRename(short rename)
    {
	this.rename = rename;
    }

    public FastTable<Item> getStorage()
    {
	return storage;
    }

    public void setStorage(FastTable<Item> storage)
    {
	this.storage = storage;
    }

    public void addSkill(Skill nSkill)
    {
	this.skills.add(nSkill);
    }

    public FastTable<Skill> getSkill()
    {
	return skills;
    }

    public void setSkill(FastTable<Skill> skills)
    {
	this.skills = skills;
    }

    public Friend[] getFriends()
    {
	return friends;
    }

    public void setFriends(Friend[] friends)
    {
	this.friends = friends;
    }

    public Hotkey[] getHotkeys()
    {
	return hotkeys;
    }

    public void setHotkeys(Hotkey[] hotkeys)
    {
	this.hotkeys = hotkeys;
    }

    public void addInventoryItem(Item nItem)
    {
	inventory.add(nItem);
    }

    public Item getInventoryItem(int ind)
    {
	return inventory.get(ind);
    }

    public void addCartItem(Item nItem)
    {
	cart.add(nItem);
    }

    public Item getCartItem(int ind)
    {
	return cart.get(ind);
    }

    public static void setPersistenceMethod(PersistenteData<IndexedFastMap<Integer, ROCharacter>> aPersistenceMethod)
    {
	persistenceMethod = aPersistenceMethod;
    }

    public static PersistenteData<IndexedFastMap<Integer, ROCharacter>> getPersistenceMethod()
    {
	return persistenceMethod;
    }

    public void addFriend(Friend friend, int index)
    {
	this.friends[index] = friend;

    }

    public void addHotkey(Hotkey hotkey, int index)
    {
	this.hotkeys[index] = hotkey;

    }

}

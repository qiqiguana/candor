/*
 * CharacterManagement.java
 *
 * Created on 27 novembre 2006, 12:23
 *
 * Copyright (c) 2006, Fran?ois Bradette
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of their contributors may be used to endorse or
 *       promote products derived from this software without specific prior
 *       written permission.
 *
 * This software is provided by the regents and contributors "as is" and any
 * express or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are
 * disclaimed.  In no event shall the regents and contributors be liable for any
 * direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services;
 * loss of use, data, or profits; or business interruption) however caused and
 * on any theory of liability, whether in contract, strict liability, or tort
 * (including negligence or otherwise) arising in any way out of the use of this
 * software, even if advised of the possibility of such damage.
 *
 * Translate from eAthena(c) by Fran?ois Bradette
 */

package org.javathena.core.utiles;

/**
 *
 * @author Francois
 */
public abstract class Constants
{
    public static final String DEFAULT_EMAIL = "a@a.com";
    public static final int FIFOSIZE_SERVERLINK = 128*1024;
    public static final String ALGORITHM = "MD5";
// set to 0 to not check IP of player between each server.
    // set to another value if you want to check (1)
    public static final int  CMP_AUTHFIFO_IP = 1;
    public static final int  CMP_AUTHFIFO_LOGIN2 = 1;
    public static final int  MAX_MAP_PER_SERVER = 1024;
    public static final int  MAX_INVENTORY = 100;
    public static final int  MAX_SLOTS = 4;
    public static final int  MAX_AMOUNT = 30000;
    public static final int  MAX_ZENY = 1000000000;	// 1G zeny
    public static final int  MAX_FAME = 1000000000; // 1G fame point [DracoRPG]
    public static final int  MAX_CART = 100;
    public static final int  MAX_SKILL = 650;
    public static final int  GLOBAL_REG_NUM = 96;
    public static final int  ACCOUNT_REG_NUM = 16;
    public static final int  ACCOUNT_REG2_NUM = 16;
    public static final int  DEFAULT_WALK_SPEED = 150;
    public static final int  MIN_WALK_SPEED = 0;
    public static final int  MAX_WALK_SPEED = 1000;
    public static final int  MAX_STORAGE = 300;
    public static final int  MAX_GUILD_STORAGE = 1000;
    public static final int  MAX_PARTY = 12;
    public static final int  MAX_GUILD = 16+10*4;	// increased max guild members +4 per 1 extension levels [Lupus]
    public static final int  MAX_GUILDPOSITION = 20;	// increased max guild positions to accomodate for all members [Valaris] (removed) [PoW]
    public static final int  MAX_GUILDEXPLUSION = 32;
    public static final int  MAX_GUILDALLIANCE = 16;
    public static final int  MAX_GUILDSKILL = 15; // increased max guild skills because of new skills [Sara-chan]
    public static final int  MAX_GUILDCASTLE = 24;	// increased to include novice castles [Valaris]
    public static final int  MAX_GUILDLEVEL = 50;
    public static final int  MAX_GUARDIANS = 8;	//Local max per castle. [Skotlex]
    /*
    public static final int  MIN_HAIR_STYLE = battle_config.min_hair_style
    public static final int  MAX_HAIR_STYLE = battle_config.max_hair_style
    public static final int  MIN_HAIR_COLOR = battle_config.min_hair_color
    public static final int  MAX_HAIR_COLOR = battle_config.max_hair_color
    public static final int  MIN_CLOTH_COLOR = battle_config.min_cloth_color
    public static final int  MAX_CLOTH_COLOR = battle_config.max_cloth_color
     */
    // for produce
    public static final int  MIN_ATTRIBUTE = 0;
    public static final int  MAX_ATTRIBUTE = 4;
    public static final int  ATTRIBUTE_NORMAL = 0;
    public static final int  MIN_STAR = 0;
    public static final int  MAX_STAR = 3;
    
    public static final int  MIN_PORTAL_MEMO = 0;
    public static final int  MAX_PORTAL_MEMO = 2;
    
    public static final int  MAX_STATUS_TYPE = 5;
    
    public static final int  WEDDING_RING_M = 2634;
    public static final int  WEDDING_RING_F = 2635;
    
    //For character names, title names, guilds, maps, etc.
    //Includes null-terminator as it is the length of the array.
    public static final int  NAME_LENGTH = 24;
    //For item names, which tend to have much longer names.
    public static final int  ITEM_NAME_LENGTH = 24;
    //For Map Names, which the client considers to be 16 in length
    public static final int  MAP_NAME_LENGTH = 16;
    
    public static final int  MAX_FRIENDS = 20;
    public static final int  MAX_MEMOPOINTS = 10;
    //These max values can be exceeded and the char/map servers will update them with no problems
    //These are just meant to minimize the updating needed between char/map servers as players login.
    //Room for initial 10K accounts
    public static final int  DEFAULT_MAX_ACCOUNT_ID = 2010000;
    //Room for initial 100k characters
    public static final int  DEFAULT_MAX_CHAR_ID = 250000;
    
    public static final String CHAR_CONF_NAME  = "conf/char_athena.conf";
    public static final boolean  WINDOWS = System.getProperty("os.name").toUpperCase().contains("WINDOWS");
    
    //
    public static final int DB_TXT = 0;
    public static final int DB_MYSQL = 1;
    public static final int DB_XML = 2;
    public static final int DB_MODE = stringToDB_MODE(System.getenv("DB_TYPE"));
    
    public static final String  LANG = System.getProperty("user.language");
    
    public static final String CL_RESET = WINDOWS?"":"\033[0;0m";
    public static final String  CL_CLS = WINDOWS?"":"\033[2J";
    public static final String  CL_CLL = WINDOWS?"":"\033[K";
    
    // font settings
    public static final String CL_BOLD = WINDOWS?"":"\033[1m";
    public static final String  CL_NORMAL = CL_RESET;
    public static final String  CL_NONE = CL_RESET;
    
    public static final String CL_WHITE = WINDOWS?"":"\033[1;29m";
    public static final String CL_GRAY = WINDOWS?"":"\033[1;30m";
    public static final String CL_RED = WINDOWS?"":"\033[1;31m";
    public static final String CL_GREEN = WINDOWS?"":"\033[1;32m";
    public static final String CL_YELLOW = WINDOWS?"":"\033[1;33m";
    public static final String CL_BLUE	 = WINDOWS?"":"\033[1;34m";
    public static final String CL_MAGENTA = WINDOWS?"":"\033[1;35m";
    public static final String CL_CYAN	 = WINDOWS?"":"\033[1;36m";
    
    public static final String CL_BT_YELLOW = WINDOWS?"":"\033[1;33m";
    public static final String CL_WTBL = WINDOWS?"":"\033[37;44m";// white on blue
    public static final String CL_XXBL = WINDOWS?"":"\033[0;44m";// default on blue
    public static final String  CL_PASS = WINDOWS?"":"\033[0;32;42m";// green on green
    public static final String NEWLINE =  System.getProperty("line.separator");
    
    public static final int DEFAULT_LOGIN_PORT = 6900;
    public static final int DEFAULT_GM_LEVEL = 10;
    
    public static final int MAX_MAPINDEX = 2000;
    
    public static final int DEFAULT_AUTOSAVE_INTERVAL = 300 * 1000;
    
    public static final int MAX_FAME_LIST = 10;
    
    public static final int START_CHAR_NUM = 150000;
    
    public static final int  MAX_PC_CLASS = 4050;
    public static final int  PC_CLASS_BASE =0;
    public static final int  PC_CLASS_BASE2 =(PC_CLASS_BASE + 4001);
    public static final int  PC_CLASS_BASE3 =(PC_CLASS_BASE2 + 22);
    public static final int  MAX_NPC_PER_MAP =512;
    public static final int  BLOCK_SIZE =8;
    public static final int  LIFETIME_FLOORITEM =60;
    public static final int  DAMAGELOG_SIZE =30;
    public static final int  LOOTITEM_SIZE =10;
    public static final int  MAX_STATUSCHANGE= 250;
//Quick defines to know which are the min-max common ailments. [Skotlex]
//Because of the way the headers are included.. these must be replaced for actual values.
//Remember to update as needed! Min is SC_STONE and max is SC_DPOISON currently.
    public static final int  SC_COMMON_MIN =0;
    public static final int  SC_COMMON_MAX =10;
    
    public static final int  MAX_SKILL_LEVEL= 100;
    public static final int  MAX_SKILLUNITGROUP= 25;
    public static final int  MAX_SKILLUNITGROUPTICKSET =25;
    public static final int  MAX_SKILLTIMERSKILL =15;
    public static final int  MAX_MOBSKILL =50;
    public static final int  MAX_MOB_LIST_PER_MAP =128;
    public static final int  MAX_EVENTQUEUE =2;
    public static final int  MAX_EVENTTIMER= 32;
    public static final int  NATURAL_HEAL_INTERVAL =500;
    public static final int  MAX_FLOORITEM =500000;
    public static final int  MAX_LEVEL =1000;
    public static final int  MAX_WALKPATH =32;
    public static final int  MAX_DROP_PER_MAP= 48;
    public static final int  MAX_IGNORE_LIST =80;
    public static final int  MAX_VENDING =12;
    public static final int  MOBID_EMPERIUM =1288;
    
    public static final int  MAX_PC_BONUS =10;
//Designed for search functions, species max number of matches to display.
    public static final int  MAX_SEARCH =5;
    public static final int  MAX_DUEL =1024;
    
//The following system marks a different job ID system used by the map server,
//which makes a lot more sense than the normal one. [Skotlex]
//
//These marks the "level" of the job.
    public static final int  JOBL_2_1 = 0x100; //256
    public static final int  JOBL_2_2 = 0x200; //512
    public static final int  JOBL_2 = 0x300;
    
    public static final int  JOBL_UPPER = 0x1000; //4096;
    public static final int  JOBL_BABY = 0x2000;  //8192
    
//for filtering and quick checking.
    public static final int MAPID_UPPERMASK = 0x0fff;
    public static final int MAPID_BASEMASK = 0x00ff;
    
    public static final int MESSAGE_SIZE = 80;
    /**
     * To convert miliseconde
     */
    public final static int SECONDE = 1000;
    public final static int MINUTE = 60 * SECONDE;
    public final static int HOURE = 60 * MINUTE;
    public final static int DAY = 24 * HOURE;
    
    public enum JOB
    {
        
        NOVICE(),
        SWORDMAN(),
        MAGE(),
        ARCHER(),
        ACOLYTE(),
        MERCHANT(),
        THIEF(),
        KNIGHT(),
        PRIEST(),
        WIZARD(),
        BLACKSMITH(),
        HUNTER(),
        ASSASSIN(),
        KNIGHT2(),
        CRUSADER(),
        MONK(),
        SAGE(),
        ROGUE(),
        ALCHEMIST(),
        BARD(),
        DANCER(),
        CRUSADER2(),
        WEDDING(),
        SUPER_NOVICE(),
        GUNSLINGER(),
        NINJA(),
        XMAS(),
        
        NOVICE_HIGH(4001),
        SWORDMAN_HIGH(),
        MAGE_HIGH(),
        ARCHER_HIGH(),
        ACOLYTE_HIGH(),
        MERCHANT_HIGH(),
        THIEF_HIGH(),
        LORD_KNIGHT(),
        HIGH_PRIEST(),
        HIGH_WIZARD(),
        WHITESMITH(),
        SNIPER(),
        ASSASSIN_CROSS(),
        LORD_KNIGHT2(),
        PALADIN(),
        CHAMPION(),
        PROFESSOR(),
        STALKER(),
        CREATOR(),
        CLOWN(),
        GYPSY(),
        PALADIN2(),
        
        BABY(),
        BABY_SWORDMAN(),
        BABY_MAGE(),
        BABY_ARCHER(),
        BABY_ACOLYTE(),
        BABY_MERCHANT(),
        BABY_THIEF(),
        BABY_KNIGHT(),
        BABY_PRIEST(),
        BABY_WIZARD(),
        BABY_BLACKSMITH(),
        BABY_HUNTER(),
        BABY_ASSASSIN(),
        BABY_KNIGHT2(),
        BABY_CRUSADER(),
        BABY_MONK(),
        BABY_SAGE(),
        BABY_ROGUE(),
        BABY_ALCHEMIST(),
        BABY_BARD(),
        BABY_DANCER(),
        BABY_CRUSADER2(),
        SUPER_BABY(),
        
        TAEKWON(),
        STAR_GLADIATOR(),
        STAR_GLADIATOR2(),
        SOUL_LINKER();
        JOB(int val)
        {
            value = val;
            setLastValue(val);
        }
        
        JOB()
        {
            value = addLastValue();
        }
        
        private static void setLastValue(int val)
        {
            lastValue = val;
        }
        
        private static int  addLastValue()
        {
            return ++lastValue;
        }
        public int getValue()
        {
            return value;
        }
        private int value;
        private static int lastValue = 0;
    };
    public enum BL
    {
        NULL(0x000),
        PC(0x001),
        MOB(0x002),
        PET(0x004),
        HOMUNCULUS(0x008),	//[blackhole89]
        ITEM(0x010),
        SKILL(0x020),
        NPC(0x040),
        CHAT(0x080);
        
        BL(final int val)
        {
            value = val;
        }
        public int value()
        {
            return value;
        }
        
        private int value;
        
    };
    public enum MAPID
    {
        NOVICE(),
        SWORDMAN(),
        MAGE(),
        ARCHER(),
        ACOLYTE(),
        MERCHANT(),
        THIEF(),
        TAEKWON(),
        WEDDING(),
        GUNSLINGER(),
        NINJA(),
        XMAS(), // [Valaris]
//2_1 classes
        SUPER_NOVICE(JOBL_2_1),
        KNIGHT(),
        WIZARD(),
        HUNTER(),
        PRIEST(),
        BLACKSMITH(),
        ASSASSIN(),
        STAR_GLADIATOR(),
//2_2 classes
        CRUSADER(JOBL_2_2|0x1),
        SAGE(),
        BARDDANCER(),
        MONK(),
        ALCHEMIST(),
        ROGUE(),
        SOUL_LINKER(),
//1-1(), advanced
        NOVICE_HIGH(JOBL_UPPER),
        SWORDMAN_HIGH(),
        MAGE_HIGH(),
        ARCHER_HIGH(),
        ACOLYTE_HIGH(),
        MERCHANT_HIGH(),
        THIEF_HIGH(),
//2_1 advanced
        LORD_KNIGHT(JOBL_UPPER|JOBL_2_1|0x1),
        HIGH_WIZARD(),
        SNIPER(),
        HIGH_PRIEST(),
        WHITESMITH(),
        ASSASSIN_CROSS(),
//2_2 advanced
        PALADIN(JOBL_UPPER|JOBL_2_2|0x1),
        PROFESSOR(),
        CLOWNGYPSY(),
        CHAMPION(),
        CREATOR(),
        STALKER(),
//1-1 baby
        BABY(JOBL_BABY),
        BABY_SWORDMAN(),
        BABY_MAGE(),
        BABY_ARCHER(),
        BABY_ACOLYTE(),
        BABY_MERCHANT(),
        BABY_THIEF(),
        BABY_TAEKWON(),
//2_1 baby
        SUPER_BABY( JOBL_BABY|JOBL_2_1),
        BABY_KNIGHT(),
        BABY_WIZARD(),
        BABY_HUNTER(),
        BABY_PRIEST(),
        BABY_BLACKSMITH(),
        BABY_ASSASSIN(),
        BABY_STAR_GLADIATOR(),
//2_2 baby
        BABY_CRUSADER(JOBL_BABY|JOBL_2_2|0x1),
        BABY_SAGE(),
        BABY_BARDDANCER(),
        BABY_MONK(),
        BABY_ALCHEMIST(),
        BABY_ROGUE(),
        BABY_SOUL_LINKER();
        MAPID(int val)
        {
            value = val;
            setLastValue(val);
        }
        
        MAPID()
        {
            value = addLastValue();
        }
        
        private static void setLastValue(int val)
        {
            lastValue = val;
        }
        
        private static int  addLastValue()
        {
            return ++lastValue;
        }
        public int getValue()
        {
            return value;
        }
        private int value;
        private static int lastValue = 0;
    };
    
    public enum SUBTYPE
    {
        WARP, SHOP, SCRIPT, MONS
    }
    
    public enum RACE
    {
        FORMLESS,
        UNDEAD,
        BRUTE,
        PLANT,
        INSECT,
        FISH,
        DEMON,
        DEMIHUMAN,
        ANGEL,
        DRAGON,
        BOSS,
        NONBOSS,
        MAX
    }
    
    public enum ELEMENT
    {
        NEUTRAL,
        WATER,
        EARTH,
        FIRE,
        WIND,
        POISON,
        HOLY,
        DARK,
        GHOST,
        UNDEAD,
        MAX
    }
    public enum ITEM_GROUP
    {
        BLUEBOX(1),
        VIOLETBOX(),	//2
        CARDALBUM(),	//3
        GIFTBOX(),	//4
        SCROLLBOX(),	//5
        FINDINGORE(),	//6
        COOKIEBAG(),	//7
        POTION(),	//8
        HERBS(),	//9
        FRUITS(),	//10
        MEAT(),	//11
        CANDY(),	//12
        JUICE(),	//13
        FISH(),	//14
        BOXES(),	//15
        GEMSTONE(),	//16
        JELLOPY(),	//17
        ORE(),	//18
        FOOD(),	//19
        RECOVERY(),	//20
        MINERALS(),	//21
        TAMING(),	//22
        SCROLLS(),	//23
        QUIVERS(),	//24
        MASKS(),	//25
        ACCESORY(),	//26
        JEWELS(),	//27
        GIFTBOX_1(),	//28
        GIFTBOX_2(),	//29
        GIFTBOX_3(),	//30
        GIFTBOX_4(),	//31
        EGGBOY(),	//32
        EGGGIRL(),	//33
        GIFTBOXCHINA(),	//34
        LOTTOBOX(),	//35
        MAX_ITEMGROUP();
        
        ITEM_GROUP(int val)
        {
            value = val;
            setLastValue(val);
        }
        
        ITEM_GROUP()
        {
            value = addLastValue();
        }
        
        private static void setLastValue(int val)
        {
            lastValue = val;
        }
        
        private static int  addLastValue()
        {
            return ++lastValue;
        }
        public int getValue()
        {
            return value;
        }
        private int value;
        private static int lastValue = 0;
    }
    public static final int BL_CHAR = (BL.PC.value()|BL.MOB.value()|BL.HOMUNCULUS.value());
    public static final int BL_ALL = 0xfff;
    public static final int INT_LENTH = 4;
    public static final int SHORT_LENTH = 2;
    public static final int BYTE_LENTH = 1;
	public static final int START_ACCOUNT_NUM = 2000000;
	public static final String DEFAULT_LOGIN_CONF_NAME = "conf/login_athena.conf";
	public static final String DEFAULT_LOGIN_CONF_NAME_XML = "conf/login_athena.xml";
	
    public static int stringToDB_MODE(String mode)
    {
        if(mode != null)
        {
            if(mode.equals("MySQL"))
                return 1;
            else
                if(mode.toUpperCase().equals("XML"))
                    return 2;
        }
        return 0;
    }
}

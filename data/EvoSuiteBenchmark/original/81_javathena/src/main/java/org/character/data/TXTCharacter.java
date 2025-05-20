package org.character.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.character.data.config.CharConfig;
import org.javathena.core.data.Friend;
import org.javathena.core.data.Hotkey;
import org.javathena.core.data.IndexedFastMap;
import org.javathena.core.data.Item;
import org.javathena.core.data.PersistenteData;
import org.javathena.core.data.Point;
import org.javathena.core.data.ROCharacter;
import org.javathena.core.data.Skill;
import org.javathena.core.data.ROCharacter.JOB;
import org.javathena.core.utiles.Functions;

public class TXTCharacter implements PersistenteData<IndexedFastMap<Integer, ROCharacter>>
{
    private final static String DEFAULT_ACCOUNT_FILE = "save/athena.txt";
    private final static int VERSION_LENGTH = 18;
    private final static String DEFAULT_FRIEND_FILE = "save/friends.txt";

    private final static String DEFAULT_HOTKEYS_FILE = "save/hotkeys.txt";

    private String fileDB = CharConfig.getCharConfig().getDbPath() != null ? CharConfig.getCharConfig().getDbPath() : DEFAULT_ACCOUNT_FILE;
    private String friendDB = DEFAULT_FRIEND_FILE;
    private String hotkeyDB = DEFAULT_HOTKEYS_FILE;

    @Override
    public void save(IndexedFastMap<Integer, ROCharacter> data) throws IOException
    {
	// TODO Auto-generated method stub

    }

    /**
     * Load character under the last format version only version 1500 (homun +
     * mapindex maps)
     * 
     * @return indexedChar a fastmap containing loaded characters
     * @throws MalformedDataException
     * */
    @Override
    public IndexedFastMap<Integer, ROCharacter> load() throws IOException
    {
	IndexedFastMap<Integer, ROCharacter> indexedChar = new IndexedFastMap<Integer, ROCharacter>();
	BufferedReader in = new BufferedReader(new FileReader(new File(fileDB)));

	String line = null;

	while ((line = in.readLine()) != null)
	{
	    try
	    {
		ROCharacter currChar = readCharactere(line);
		indexedChar.put(currChar.getChar_id(), currChar);

	    }
	    catch (MalformedDataException e)
	    {
		Functions.showWarning(e.getMessage());
	    }
	}
	in.close();
	readFriend(indexedChar);
	readHotkeys(indexedChar);
	return indexedChar;
    }

    private void readHotkeys(IndexedFastMap<Integer, ROCharacter> indexedChar) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader(new File(hotkeyDB)));
	String line = null;
	String splitedHotkeys[] = null;
	while ((line = in.readLine()) != null)
	{
	    if (!line.startsWith("//"))
	    {
		for (int i = 0; i < splitedHotkeys.length && i < ROCharacter.MAX_HOTKEY; i++)
		{
		    splitedHotkeys = line.split(",");
		    indexedChar.getData().get(Integer.parseInt(splitedHotkeys[0])).addHotkey(new Hotkey(Integer.parseInt(splitedHotkeys[1]), 
		                                                                                        Integer.parseInt(splitedHotkeys[2]), 
		                                                                                        Integer.parseInt(splitedHotkeys[3])), i);
		}
	    }
	}
    }

    private void readFriend(IndexedFastMap<Integer, ROCharacter> indexedChar) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader(new File(friendDB)));

	String line = null;
	String splitedFriend[] = null;
	while ((line = in.readLine()) != null)
	{
	    if (!line.startsWith("//"))
	    {
		splitedFriend = line.split(",");
		for (int i = 0; i < splitedFriend.length && i < ROCharacter.MAX_FRIENDS; i++)
		{
		    indexedChar.getData().get(Integer.parseInt(splitedFriend[0])).addFriend(new Friend(Integer.parseInt(splitedFriend[1]), 
		                                                                                       Integer.parseInt(splitedFriend[2]), 
		                                                                                       splitedFriend[3]), i);
		}
	    }
	}
	in.close();
    }

    private ROCharacter readCharactere(String line) throws MalformedDataException
    {
	String[] mainCharSL;
	ROCharacter currChar = new ROCharacter();
	if (!line.startsWith("//") && line.length() != 0)
	{
	    if (line.contains("//"))
	    {
		line = line.substring(0, line.indexOf("//"));
	    }
	    ;
	    mainCharSL = line.split("\t");
	    if (mainCharSL.length == VERSION_LENGTH)
	    {
		for (int i = 0; i < mainCharSL.length; i++)
		{
		    String tmpSplit[] = null;
		    Point tmpPoint = null;
		    currChar.setChar_id(Integer.parseInt(mainCharSL[0]));

		    tmpSplit = mainCharSL[1].split(",");
		    currChar.setAccount_id(Integer.parseInt(tmpSplit[0]));
		    currChar.setSlot(Integer.parseInt(tmpSplit[1]));

		    currChar.setName(mainCharSL[2]);

		    tmpSplit = mainCharSL[3].split(",");
		    currChar.setClass_(JOB.parseFromValue(Short.parseShort(tmpSplit[0])));
		    currChar.setBase_level(Integer.parseInt(tmpSplit[0]));
		    currChar.setJob_level(Integer.parseInt(tmpSplit[0]));

		    tmpSplit = mainCharSL[4].split(",");
		    currChar.setBase_exp(Integer.parseInt(tmpSplit[0]));
		    currChar.setJob_exp(Integer.parseInt(tmpSplit[1]));
		    currChar.setZeny(Integer.parseInt(tmpSplit[2]));

		    tmpSplit = mainCharSL[5].split(",");
		    currChar.setHp(Integer.parseInt(tmpSplit[0]));
		    currChar.setMax_hp(Integer.parseInt(tmpSplit[1]));
		    currChar.setSp(Integer.parseInt(tmpSplit[2]));
		    currChar.setMax_sp(Integer.parseInt(tmpSplit[3]));

		    tmpSplit = mainCharSL[6].split(",");
		    currChar.setStr(Short.parseShort(tmpSplit[0]));
		    currChar.setAgi(Short.parseShort(tmpSplit[1]));
		    currChar.setVit(Short.parseShort(tmpSplit[2]));
		    currChar.setInt(Short.parseShort(tmpSplit[3]));
		    currChar.setDex(Short.parseShort(tmpSplit[4]));
		    currChar.setLuk(Short.parseShort(tmpSplit[5]));

		    tmpSplit = mainCharSL[7].split(",");
		    currChar.setStatus_point(Integer.parseInt(tmpSplit[0]));
		    currChar.setSkill_point(Integer.parseInt(tmpSplit[1]));

		    tmpSplit = mainCharSL[8].split(",");
		    currChar.setOption(Integer.parseInt(tmpSplit[0]));
		    currChar.setKarma(Integer.parseInt(tmpSplit[1]));
		    currChar.setManner(Short.parseShort(tmpSplit[2]));

		    tmpSplit = mainCharSL[9].split(",");
		    currChar.setParty_id(Integer.parseInt(tmpSplit[0]));
		    currChar.setGuild_id(Integer.parseInt(tmpSplit[1]));
		    currChar.setPet_id(Integer.parseInt(tmpSplit[2]));
		    currChar.setHom_id(Integer.parseInt(tmpSplit[3]));

		    tmpSplit = mainCharSL[10].split(",");
		    currChar.setHair(Short.parseShort(tmpSplit[0]));
		    currChar.setHair_color(Short.parseShort(tmpSplit[1]));
		    currChar.setClothes_color(Short.parseShort(tmpSplit[2]));

		    tmpSplit = mainCharSL[11].split(",");
		    currChar.setWeapon(Short.parseShort(tmpSplit[0]));
		    currChar.setShield(Short.parseShort(tmpSplit[1]));
		    currChar.setHead_top(Short.parseShort(tmpSplit[2]));
		    currChar.setHead_mid(Short.parseShort(tmpSplit[3]));
		    currChar.setHead_bottom(Short.parseShort(tmpSplit[4]));

		    tmpSplit = mainCharSL[12].split(",");
		    tmpPoint = new Point(Integer.parseInt(tmpSplit[0]), Integer.parseInt(tmpSplit[1]), Integer.parseInt(tmpSplit[2]));
		    currChar.setLast_point(tmpPoint);

		    tmpSplit = mainCharSL[13].split(",");
		    tmpPoint = new Point(Integer.parseInt(tmpSplit[0]), Integer.parseInt(tmpSplit[1]), Integer.parseInt(tmpSplit[2]));
		    currChar.setSave_point(tmpPoint);

		    currChar.setPartner_id(Integer.parseInt(tmpSplit[3]));
		    currChar.setFather(Integer.parseInt(tmpSplit[4]));
		    currChar.setMother(Integer.parseInt(tmpSplit[5]));
		    currChar.setChild(Integer.parseInt(tmpSplit[6]));

		    tmpSplit = mainCharSL[14].split(" ");
		    if (!tmpSplit[0].equals(""))
		    {
			String memoSplit[] = null;
			for (int j = 0; j < tmpSplit.length; j++)
			{
			    memoSplit = tmpSplit[j].split(",");
			    currChar.addPoint(new Point(Integer.parseInt(memoSplit[0]), Integer.parseInt(memoSplit[1]), Integer.parseInt(memoSplit[2])));
			}
		    }

		    tmpSplit = mainCharSL[15].split(" ");
		    if (!tmpSplit[0].equals(""))
		    {
			String invSplit[] = null;
			for (int j = 0; j < tmpSplit.length; j++)
			{
			    invSplit = tmpSplit[j].split(",");
			    Item nItem = new Item();
			    nItem.setId(Integer.parseInt(invSplit[0]));
			    nItem.setNameid(Short.parseShort(invSplit[1]));
			    nItem.setAmount(Short.parseShort(invSplit[2]));
			    nItem.setEquip(Short.parseShort(invSplit[3]));
			    nItem.setIdentify(Integer.parseInt(invSplit[4]));
			    nItem.setRefine(Integer.parseInt(invSplit[5]));
			    nItem.setAttribute(Integer.parseInt(invSplit[6]));
			    for (int k = 0; k < Item.MAX_SLOT + 7 && !invSplit[7 + k].equals("0"); k++)
			    {
				nItem.setCard(k, Integer.parseInt(invSplit[7 + k]));
			    }
			    currChar.addInventoryItem(nItem);
			}
		    }

		    tmpSplit = mainCharSL[16].split(" ");
		    if (!tmpSplit[0].equals(""))
		    {
			String invSplit[] = null;
			for (int j = 0; j < tmpSplit.length; j++)
			{
			    invSplit = tmpSplit[j].split(",");
			    Item nItem = new Item();
			    nItem.setId(Integer.parseInt(invSplit[0]));
			    nItem.setNameid(Short.parseShort(invSplit[1]));
			    nItem.setAmount(Short.parseShort(invSplit[2]));
			    nItem.setEquip(Short.parseShort(invSplit[3]));
			    nItem.setIdentify(Integer.parseInt(invSplit[4]));
			    nItem.setRefine(Integer.parseInt(invSplit[5]));
			    nItem.setAttribute(Integer.parseInt(invSplit[6]));
			    for (int k = 0; k < Item.MAX_SLOT + 7 && !invSplit[7 + k].equals("0"); k++)
			    {
				nItem.setCard(k, Integer.parseInt(invSplit[7 + k]));
			    }
			    currChar.addCartItem(nItem);
			}
		    }

		    tmpSplit = mainCharSL[17].split(" ");
		    if (!tmpSplit[0].equals(""))
		    {
			String skillSplit[] = null;
			for (int j = 0; j < tmpSplit.length; j++)
			{
			    skillSplit = tmpSplit[j].split(",");
			    currChar.addSkill(new Skill(Short.parseShort(skillSplit[0]), Short.parseShort(skillSplit[1])));
			}
		    }

		    tmpSplit = mainCharSL[18].split(" ");
		    if (!tmpSplit[0].equals(""))
		    {
			String regSplit[] = null;
			for (int j = 0; j < tmpSplit.length; j++)
			{
			    regSplit = tmpSplit[j].split(",");
			    currChar.addGlobalReg(regSplit[0], regSplit[1]);
			}
		    }
		}
	    }
	    else
	    {
		Functions.showDebug("Character info: %s\n", line);
		throw new MalformedDataException("Char-loading: Unrecognized character data version, info lost!\n");
	    }
	}
	return currChar;
    }
}

class MalformedDataException extends Exception
{

    /**
	 * 
	 */
    private static final long serialVersionUID = 9211207786015208067L;

    public MalformedDataException(String message)
    {
	super(message);
    }

}

package org.character.data.config;

import java.io.IOException;

import org.javathena.core.data.PersistenteData;
import org.javathena.core.data.Point;
import org.javathena.core.utiles.Functions;

public class CharConfig
{
	private static PersistenteData<CharConfig> persistenceMethod;
	private static CharConfig charConfig;

	private String timestampFormat;
	private int msgSilent;
	private boolean stdoutWithAnsisequence;
	private String userid;
	private String passwd;
	private String serverName;
	private String wispServerName;
	private String loginIp;
	private int loginPort;
	private String charIp;
	private String bindIp;
	private int charPort;
	private int charMaintenance;
	private boolean charNew;
	private int charNewDisplay;
	private boolean emailCreation;
	private String scdataTXT;
	private String charTXT;
	private String friendsTXT;
	private String hotkeysTXT;
	private int maxConnectUser;
	private int gmAllowLevel;
	private int autosaveInterval;
	private boolean saveLog;
	private Point startPoint;
	private int startZeny;
	private int startWeapon;
	private int startArmor;
	private boolean logChar;
	private String unknownCharName;
	private String charLogFilename;
	private boolean nameIgnoringCase;
	private int charNameOption;
	private String charNameLetters;
	private String onlineTxtFilename;
	private String onlineHtmlFilename;
	private int onlineSortingOption;
	private int onlineDisplayOption;
	//Send online file every 5 seconds to player is enough
	private int onlineGmDisplayMinLevel;
	private int onlineRefreshHtml;
	private String dbPath;
	private boolean console;
	private int fameListSizeAlchemist;
	private int fameListSizeSmith;
	private int fameListSizeTaekwon;
	private int fameListSizeBlacksmith;
	private int guildExpRate;
	private int charsPerAccount;
	private int charDelLevel;

	public int getFameListSizeBlacksmith()
	{
		return fameListSizeBlacksmith;
	}

	public void setFameListSizeBlacksmith(int fameListSizeBlacksmith)
	{
		this.fameListSizeBlacksmith = fameListSizeBlacksmith;
	}
	
	public int getCharDelLevel()
	{
		return charDelLevel;
	}

	public void setCharDelLevel(int charDelLevel)
	{
		this.charDelLevel = charDelLevel;
	}

	public int getCharsPerAccount()
	{
		return charsPerAccount;
	}

	public void setCharsPerAccount(int charsPerAccount)
	{
		this.charsPerAccount = charsPerAccount;
	}

	public String getTimestampFormat()
	{
		return timestampFormat;
	}

	public void setTimestampFormat(String timestampFormat)
	{
		this.timestampFormat = timestampFormat;
	}

	public int getMsgSilent()
	{
		return msgSilent;
	}

	public void setMsgSilent(int msgSilent)
	{
		this.msgSilent = msgSilent;
	}

	public boolean isStdoutWithAnsisequence()
	{
		return stdoutWithAnsisequence;
	}

	public void setStdoutWithAnsisequence(boolean stdoutWithAnsisequence)
	{
		this.stdoutWithAnsisequence = stdoutWithAnsisequence;
	}

	public String getUserid()
	{
		return userid;
	}

	public void setUserid(String userid)
	{
		this.userid = userid;
	}

	public String getPasswd()
	{
		return passwd;
	}

	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}

	public String getServerName()
	{
		return serverName;
	}

	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	public String getWispServerName()
	{
		return wispServerName;
	}

	public void setWispServerName(String wispServerName)
	{
		this.wispServerName = wispServerName;
	}

	public String getLoginIp()
	{
		return loginIp;
	}

	public void setLoginIp(String loginIp)
	{
		this.loginIp = loginIp;
	}

	public int getLoginPort()
	{
		return loginPort;
	}

	public void setLoginPort(int loginPort)
	{
		this.loginPort = loginPort;
	}

	public String getCharIp()
	{
		return charIp;
	}

	public void setCharIp(String charIp)
	{
		this.charIp = charIp;
	}

	public String getBindIp()
	{
		return bindIp;
	}

	public void setBindIp(String bindIp)
	{
		this.bindIp = bindIp;
	}

	public int getCharPort()
	{
		return charPort;
	}

	public void setCharPort(int charPort)
	{
		this.charPort = charPort;
	}

	public int getCharMaintenance()
	{
		return charMaintenance;
	}

	public void setCharMaintenance(int charMaintenance)
	{
		this.charMaintenance = charMaintenance;
	}

	public boolean isCharNew()
	{
		return charNew;
	}

	public void setCharNew(boolean charNew)
	{
		this.charNew = charNew;
	}

	public int getCharNewDisplay()
	{
		return charNewDisplay;
	}

	public void setCharNewDisplay(int charNewDisplay)
	{
		this.charNewDisplay = charNewDisplay;
	}

	public boolean isEmailCreation()
	{
		return emailCreation;
	}

	public void setEmailCreation(boolean emailCreation)
	{
		this.emailCreation = emailCreation;
	}

	public String getScdataTXT()
	{
		return scdataTXT;
	}

	public void setScdataTXT(String scdataTXT)
	{
		this.scdataTXT = scdataTXT;
	}

	public String getCharTXT()
	{
		return charTXT;
	}

	public void setCharTXT(String charTXT)
	{
		this.charTXT = charTXT;
	}

	public String getFriendsTXT()
	{
		return friendsTXT;
	}

	public void setFriendsTXT(String friendsTXT)
	{
		this.friendsTXT = friendsTXT;
	}

	public String getHotkeysTXT()
	{
		return hotkeysTXT;
	}

	public void setHotkeysTXT(String hotkeysTXT)
	{
		this.hotkeysTXT = hotkeysTXT;
	}

	public int getMaxConnectUser()
	{
		return maxConnectUser;
	}

	public void setMaxConnectUser(int maxConnectUser)
	{
		this.maxConnectUser = maxConnectUser;
	}

	public int getGmAllowLevel()
	{
		return gmAllowLevel;
	}

	public void setGmAllowLevel(int gmAllowLevel)
	{
		this.gmAllowLevel = gmAllowLevel;
	}

	public int getAutosaveInterval()
	{
		return autosaveInterval;
	}

	public void setAutosaveInterval(int autosaveInterval)
	{
		this.autosaveInterval = autosaveInterval;
	}

	public boolean isSaveLog()
	{
		return saveLog;
	}

	public void setSaveLog(boolean saveLog)
	{
		this.saveLog = saveLog;
	}

	public Point getStartPoint()
	{
		return startPoint;
	}

	public void setStartPoint(Point startPoint)
	{
		this.startPoint = startPoint;
	}

	public int getStartZeny()
	{
		return startZeny;
	}

	public void setStartZeny(int startZeny)
	{
		this.startZeny = startZeny;
	}

	public int getStartWeapon()
	{
		return startWeapon;
	}

	public void setStartWeapon(int startWeapon)
	{
		this.startWeapon = startWeapon;
	}

	public int getStartArmor()
	{
		return startArmor;
	}

	public void setStartArmor(int startArmor)
	{
		this.startArmor = startArmor;
	}

	public boolean isLogChar()
	{
		return logChar;
	}

	public void setLogChar(boolean logChar)
	{
		this.logChar = logChar;
	}

	public String getUnknownCharName()
	{
		return unknownCharName;
	}

	public void setUnknownCharName(String unknownCharName)
	{
		this.unknownCharName = unknownCharName;
	}

	public String getCharLogFilename()
	{
		return charLogFilename;
	}

	public void setCharLogFilename(String charLogFilename)
	{
		this.charLogFilename = charLogFilename;
	}

	public boolean isNameIgnoringCase()
	{
		return nameIgnoringCase;
	}

	public void setNameIgnoringCase(boolean nameIgnoringCase)
	{
		this.nameIgnoringCase = nameIgnoringCase;
	}

	public int getCharNameOption()
	{
		return charNameOption;
	}

	public void setCharNameOption(int charNameOption)
	{
		this.charNameOption = charNameOption;
	}

	public String getCharNameLetters()
	{
		return charNameLetters;
	}

	public void setCharNameLetters(String charNameLetters)
	{
		this.charNameLetters = charNameLetters;
	}

	public String getOnlineTxtFilename()
	{
		return onlineTxtFilename;
	}

	public void setOnlineTxtFilename(String onlineTxtFilename)
	{
		this.onlineTxtFilename = onlineTxtFilename;
	}

	public String getOnlineHtmlFilename()
	{
		return onlineHtmlFilename;
	}

	public void setOnlineHtmlFilename(String onlineHtmlFilename)
	{
		this.onlineHtmlFilename = onlineHtmlFilename;
	}

	public int getOnlineSortingOption()
	{
		return onlineSortingOption;
	}

	public void setOnlineSortingOption(int onlineSortingOption)
	{
		this.onlineSortingOption = onlineSortingOption;
	}

	public int getOnlineDisplayOption()
	{
		return onlineDisplayOption;
	}

	public void setOnlineDisplayOption(int onlineDisplayOption)
	{
		this.onlineDisplayOption = onlineDisplayOption;
	}

	public int getOnlineGmDisplayMinLevel()
	{
		return onlineGmDisplayMinLevel;
	}

	public void setOnlineGmDisplayMinLevel(int onlineGmDisplayMinLevel)
	{
		this.onlineGmDisplayMinLevel = onlineGmDisplayMinLevel;
	}

	public int getOnlineRefreshHtml()
	{
		return onlineRefreshHtml;
	}

	public void setOnlineRefreshHtml(int onlineRefreshHtml)
	{
		this.onlineRefreshHtml = onlineRefreshHtml;
	}

	public String getDbPath()
	{
		return dbPath;
	}

	public void setDbPath(String dbPath)
	{
		this.dbPath = dbPath;
	}

	public boolean isConsole()
	{
		return console;
	}

	public void setConsole(boolean console)
	{
		this.console = console;
	}

	public int getFameListSizeAlchemist()
	{
		return fameListSizeAlchemist;
	}

	public void setFameListSizeAlchemist(int fameListSizeChemist)
	{
		this.fameListSizeAlchemist = fameListSizeChemist;
	}

	public int getFameListSizeSmith()
	{
		return fameListSizeSmith;
	}

	public void setFameListSizeSmith(int fameListSizeSmith)
	{
		this.fameListSizeSmith = fameListSizeSmith;
	}

	public int getFameListSizeTaekwon()
	{
		return fameListSizeTaekwon;
	}

	public void setFameListSizeTaekwon(int fameListSizeTaekwon)
	{
		this.fameListSizeTaekwon = fameListSizeTaekwon;
	}

	public int getGuildExpRate()
	{
		return guildExpRate;
	}

	public void setGuildExpRate(int guildExpRate)
	{
		this.guildExpRate = guildExpRate;
	}

	public static void setPersistenceMethod(
			PersistenteData<CharConfig> aPersistenceMethod)
	{
		persistenceMethod = aPersistenceMethod;
	}

	public static PersistenteData<CharConfig> getPersistenceMethod()
	{
		return persistenceMethod;
	}

	public void save()
	{
		try
		{
			persistenceMethod.save(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void load()
	{
		try
		{
			charConfig = persistenceMethod.load();
			if (charConfig.getUserid().compareTo("s1") == 0 && charConfig.getUserid().compareTo("p1")==0) {
				Functions.showError("Using the default user/password s1/p1 is NOT RECOMMENDED.\n");
				Functions.showNotice("Please edit your account file to create a proper inter-server user/password (gender 'S')\n");
				Functions.showNotice("And then change the user/password to use in your configuration file\n");
			}
			Functions.showInfo("Finished reading the char-server configuration.\n");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static CharConfig getCharConfig()
	{
		return charConfig;
	}

	public static void setCharConfig(CharConfig charConfig)
	{
		CharConfig.charConfig = charConfig;
	}
}

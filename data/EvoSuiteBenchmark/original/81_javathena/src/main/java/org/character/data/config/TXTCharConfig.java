package org.character.data.config;

import java.io.File;
import java.io.IOException;

import org.character.data.map.MapIndex;
import org.javathena.core.data.PersistenteData;
import org.javathena.core.data.Point;
import org.javathena.core.utiles.Functions;

public class TXTCharConfig implements PersistenteData<CharConfig> 
{

	private final static String DEFAULT_FILE = "conf/char_athena.conf";

	private String fileConfig = DEFAULT_FILE;


	@Override
	public void save(CharConfig data) throws IOException
	{/*
		PrintWriter out = null;
		out = new PrintWriter(new BufferedWriter(new FileWriter(fileConfig)));
		out.write(data.toString());
		out.close();*/
	}

	@Override
	public CharConfig load() throws IOException
	{
		File fp;
		String lu = null;
		int indArg = -1;
		
		fp = new File(fileConfig);

		if (fp == null || !fp.exists())
		{
			return null;
		}
		try
		{
			lu = Functions.readConf(fp);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		CharConfig confLoad = new CharConfig();
		if((indArg = lu.lastIndexOf("timestamp_format"))!= -1)
		{
			confLoad.setTimestampFormat(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("console_silent"))!= -1)
		{
			confLoad.setMsgSilent(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("stdout_with_ansisequence"))!= -1)
		{
			confLoad.setStdoutWithAnsisequence(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("userid"))!= -1)
		{
			confLoad.setUserid(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("passwd"))!= -1)
		{
			confLoad.setPasswd(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("server_name"))!= -1)
		{
			confLoad.setServerName(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("wisp_server_name"))!= -1)
		{
			confLoad.setWispServerName(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("login_ip"))!= -1)
		{
			confLoad.setLoginIp(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("login_port"))!= -1)
		{
			confLoad.setLoginPort(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("char_ip"))!= -1)
		{
			confLoad.setCharIp(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("char_port"))!= -1)
		{
			confLoad.setCharPort(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("char_maintenance"))!= -1)
		{
			confLoad.setCharMaintenance(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("char_new"))!= -1)
		{
			confLoad.setCharNew(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("char_new_display"))!= -1)
		{
			confLoad.setCharNewDisplay(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("max_connect_user"))!= -1)
		{
			confLoad.setMaxConnectUser(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("gm_allow_level"))!= -1)
		{
			confLoad.setGmAllowLevel(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("autosave_time"))!= -1)
		{
			confLoad.setAutosaveInterval(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("save_log"))!= -1)
		{
			confLoad.setSaveLog(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("start_point"))!= -1)
		{
			String startPoint[] = lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim().split(",");
			confLoad.setStartPoint(new Point(MapIndex.get(startPoint[0]), Integer.parseInt(startPoint[1]),Integer.parseInt(startPoint[2])));
		}
		if((indArg = lu.lastIndexOf("start_zeny"))!= -1)
		{
			confLoad.setStartZeny(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("start_weapon"))!= -1)
		{
			confLoad.setStartWeapon(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("start_armor"))!= -1)
		{
			confLoad.setStartArmor(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("log_char"))!= -1)
		{
			confLoad.setLogChar(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("unknown_char_name"))!= -1)
		{
			confLoad.setUnknownCharName(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("name_ignoring_case"))!= -1)
		{
			confLoad.setNameIgnoringCase(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("char_name_option"))!= -1)
		{
			confLoad.setCharNameOption(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("char_name_letters"))!= -1)
		{
			confLoad.setCharNameLetters(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim());
		}
		if((indArg = lu.lastIndexOf("chars_per_account"))!= -1)
		{
			confLoad.setCharsPerAccount(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("char_del_level"))!= -1)
		{
			confLoad.setCharDelLevel(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("console"))!= -1)
		{
			confLoad.setConsole(Functions.config_switch(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()) == 1);
		}
		if((indArg = lu.lastIndexOf("fame_list_alchemist"))!= -1)
		{
			confLoad.setFameListSizeAlchemist(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("fame_list_blacksmith"))!= -1)
		{
			confLoad.setFameListSizeBlacksmith(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("fame_list_taekwon"))!= -1)
		{
			confLoad.setFameListSizeTaekwon(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		if((indArg = lu.lastIndexOf("guild_exp_rate"))!= -1)
		{
			confLoad.setGuildExpRate(Integer.parseInt(lu.substring(lu.indexOf(':',indArg) + 1, lu.indexOf('\n',indArg)).trim()));
		}
		
		return confLoad;
	}
	
	public String getFileConfig()
	{
		return fileConfig;
	}

	public void setFileConfig(String fileConfig)
	{
		this.fileConfig = fileConfig;
	}
}

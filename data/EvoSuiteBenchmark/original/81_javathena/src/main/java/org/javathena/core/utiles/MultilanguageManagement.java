/*
 * Multilanguage.java
 *
 * Created on 11 mai 2006, 20:54
 *
 * Copyright (c) 2006, Franois Bradette
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
 * Translate from eAthena(c) by Franois Bradette
 */

package org.javathena.core.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javolution.util.FastMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * @author Darksid_1
 */
public class MultilanguageManagement
{
	private static String warning;

	private static String error;

	private static String notice;

	private static String info;

	private static String status;

	private static String fatalError;

	private static String warning_1;

	private static String warning_2;

	private static String warning_3;

	private static String warning_4;

	private static String warning_5;

	private static String warning_6;

	private static String warning_7;

	private static String warning_8;

	private static String warning_9;

	private static String warning_10;

	private static String warning_11;

	private static String warning_12;

	private static String warning_13;

	private static String warning_14;

	private static String warning_15;

	private static String warning_16;

	private static String warning_17;

	private static String warning_18;

	private static String warning_19;

	private static String warning_20;

	private static String warning_21;

	private static String warning_22;

	private static String warning_23;

	private static String warning_24;

	private static String warning_25;

	private static String warning_26;

	private static String warning_27;

	private static String warning_28;

	private static String warning_29;

	private static String warning_30;

	private static String warning_31;

	private static String warning_32;

	private static String warning_33;

	private static String warning_34;

	private static String warning_35;

	private static String warning_36;

	private static String warning_37;

	private static String warning_38;

	private static String warning_39;

	private static String warning_40;

	private static String warning_41;

	private static String warning_42;

	private static String warning_43;

	private static String warning_44;

	private static String warning_45;

	private static String warning_46;

	private static String warning_47;

	private static String warning_48;

	private static String warning_49;

	private static String warning_50;

	private static String warning_51;

	private static String fatal_error_1;

	private static String error_1;

	private static String error_2;

	private static String error_3;

	private static String error_4;

	private static String error_5;

	private static String error_6;

	private static String error_7;

	private static String error_8;

	private static String error_9;

	private static String error_10;

	private static String error_11;

	private static String error_12;

	private static String error_13;

	private static String error_14;

	private static String error_15;

	private static String error_16;

	private static String error_17;

	private static String error_18;

	private static String error_19;

	private static String error_20;

	private static String error_21;

	private static String error_22;

	private static String error_23;

	private static String error_24;

	private static String notice_1;

	private static String notice_2;

	private static String notice_3;

	private static String notice_4;

	private static String notice_5;

	private static String notice_6;

	private static String notice_7;

	private static String notice_8;

	private static String notice_9;

	private static String notice_10;

	private static String notice_11;

	private static String notice_12;

	private static String notice_13;

	private static String notice_14;

	private static String notice_15;

	private static String notice_16;

	private static String notice_17;

	private static String notice_18;

	private static String notice_19;

	private static String info_1;

	private static String info_2;

	private static String info_3;

	private static String info_4;

	private static String info_5;

	private static String info_6;

	private static String info_7;

	private static String info_8;

	private static String info_9;

	private static String info_10;

	private static String info_11;

	private static String info_12;

	private static String login_log_1;

	private static String login_log_2;

	private static String login_log_3;

	private static String login_log_4;

	private static String login_log_5;

	private static String login_log_6;

	private static String login_log_7;

	private static String login_log_8;

	private static String login_log_9;

	private static String login_log_10;

	private static String login_log_11;

	private static String login_log_12;

	private static String login_log_13;

	private static String login_log_14;

	private static String login_log_15;

	private static String login_log_16;

	private static String login_log_17;

	private static String login_log_18;

	private static String login_log_19;

	private static String login_log_20;

	private static String login_log_21;

	private static String login_log_22;

	private static String login_log_23;

	private static String login_log_24;

	private static String login_log_25;

	private static String login_log_26;

	private static String login_log_27;

	private static String login_log_28;

	private static String login_log_29;

	private static String login_log_30;

	private static String login_log_31;

	private static String login_log_32;

	private static String login_log_33;

	private static String login_log_34;

	private static String login_log_35;

	private static String login_log_36;

	private static String login_log_37;

	private static String login_log_38;

	private static String login_log_39;

	private static String login_log_40;

	private static String login_log_41;

	private static String login_log_42;

	private static String login_log_43;

	private static String login_log_44;

	private static String login_log_45;

	private static String login_log_46;

	private static String login_log_47;

	private static String login_log_48;

	private static String status_1;

	private static String status_2;

	private static String status_3;

	private static String status_4;

	private static String status_5;

	private static String status_6;

	private static String char_log_1;

	private static String char_log_2;

	private static String error_25;

	public static String getChar_log_1()
	{
		return char_log_1;
	}

	public static String getChar_log_2()
	{
		return char_log_2;
	}

	public static String getError()
	{
		return error;
	}

	public static String getError_1()
	{
		return error_1;
	}

	public static String getError_10()
	{
		return error_10;
	}

	public static String getError_11()
	{
		return error_11;
	}

	public static String getError_12()
	{
		return error_12;
	}

	public static String getError_13()
	{
		return error_13;
	}

	public static String getError_14()
	{
		return error_14;
	}

	public static String getError_15()
	{
		return error_15;
	}

	public static String getError_16()
	{
		return error_16;
	}

	public static String getError_17()
	{
		return error_17;
	}

	public static String getError_18()
	{
		return error_18;
	}

	public static String getError_19()
	{
		return error_19;
	}

	public static String getError_2()
	{
		return error_2;
	}

	public static String getError_20()
	{
		return error_20;
	}

	public static String getError_21()
	{
		return error_21;
	}

	public static String getError_22()
	{
		return error_22;
	}

	public static String getError_23()
	{
		return error_23;
	}

	public static String getError_24()
	{
		return error_24;
	}

	public static String getError_25()
	{
		return error_25;
	}

	public static String getError_3()
	{
		return error_3;
	}

	public static String getError_4()
	{
		return error_4;
	}

	public static String getError_5()
	{
		return error_5;
	}

	public static String getError_6()
	{
		return error_6;
	}

	public static String getError_7()
	{
		return error_7;
	}

	public static String getError_8()
	{
		return error_8;
	}

	public static String getError_9()
	{
		return error_9;
	}

	public static String getFatal_error_1()
	{
		return fatal_error_1;
	}

	public static String getFatalError()
	{
		return fatalError;
	}

	public static String getInfo()
	{
		return info;
	}

	public static String getInfo_1()
	{
		return info_1;
	}

	public static String getInfo_10()
	{
		return info_10;
	}

	public static String getInfo_11()
	{
		return info_11;
	}

	public static String getInfo_12()
	{
		return info_12;
	}

	public static String getInfo_2()
	{
		return info_2;
	}

	public static String getInfo_3()
	{
		return info_3;
	}

	public static String getInfo_4()
	{
		return info_4;
	}

	public static String getInfo_5()
	{
		return info_5;
	}

	public static String getInfo_6()
	{
		return info_6;
	}

	public static String getInfo_7()
	{
		return info_7;
	}

	public static String getInfo_8()
	{
		return info_8;
	}

	public static String getInfo_9()
	{
		return info_9;
	}

	public static String getLogin_log_1()
	{
		return login_log_1;
	}

	public static String getLogin_log_10()
	{
		return login_log_10;
	}

	public static String getLogin_log_11()
	{
		return login_log_11;
	}

	public static String getLogin_log_12()
	{
		return login_log_12;
	}

	public static String getLogin_log_13()
	{
		return login_log_13;
	}

	public static String getLogin_log_14()
	{
		return login_log_14;
	}

	public static String getLogin_log_15()
	{
		return login_log_15;
	}

	public static String getLogin_log_16()
	{
		return login_log_16;
	}

	public static String getLogin_log_17()
	{
		return login_log_17;
	}

	public static String getLogin_log_18()
	{
		return login_log_18;
	}

	public static String getLogin_log_19()
	{
		return login_log_19;
	}

	public static String getLogin_log_2()
	{
		return login_log_2;
	}

	public static String getLogin_log_20()
	{
		return login_log_20;
	}

	public static String getLogin_log_21()
	{
		return login_log_21;
	}

	public static String getLogin_log_22()
	{
		return login_log_22;
	}

	public static String getLogin_log_23()
	{
		return login_log_23;
	}

	public static String getLogin_log_24()
	{
		return login_log_24;
	}

	public static String getLogin_log_25()
	{
		return login_log_25;
	}

	public static String getLogin_log_26()
	{
		return login_log_26;
	}

	public static String getLogin_log_27()
	{
		return login_log_27;
	}

	public static String getLogin_log_28()
	{
		return login_log_28;
	}

	public static String getLogin_log_29()
	{
		return login_log_29;
	}

	public static String getLogin_log_3()
	{
		return login_log_3;
	}

	public static String getLogin_log_30()
	{
		return login_log_30;
	}

	public static String getLogin_log_31()
	{
		return login_log_31;
	}

	public static String getLogin_log_32()
	{
		return login_log_32;
	}

	public static String getLogin_log_33()
	{
		return login_log_33;
	}

	public static String getLogin_log_34()
	{
		return login_log_34;
	}

	public static String getLogin_log_35()
	{
		return login_log_35;
	}

	public static String getLogin_log_36()
	{
		return login_log_36;
	}

	public static String getLogin_log_37()
	{
		return login_log_37;
	}

	public static String getLogin_log_38()
	{
		return login_log_38;
	}

	public static String getLogin_log_39()
	{
		return login_log_39;
	}

	public static String getLogin_log_4()
	{
		return login_log_4;
	}

	public static String getLogin_log_40()
	{
		return login_log_40;
	}

	public static String getLogin_log_41()
	{
		return login_log_41;
	}

	public static String getLogin_log_42()
	{
		return login_log_42;
	}

	public static String getLogin_log_43()
	{
		return login_log_43;
	}

	public static String getLogin_log_44()
	{
		return login_log_44;
	}

	public static String getLogin_log_45()
	{
		return login_log_45;
	}

	public static String getLogin_log_46()
	{
		return login_log_46;
	}

	public static String getLogin_log_47()
	{
		return login_log_47;
	}

	public static String getLogin_log_48()
	{
		return login_log_48;
	}

	public static String getLogin_log_5()
	{
		return login_log_5;
	}

	public static String getLogin_log_6()
	{
		return login_log_6;
	}

	public static String getLogin_log_7()
	{
		return login_log_7;
	}

	public static String getLogin_log_8()
	{
		return login_log_8;
	}

	public static String getLogin_log_9()
	{
		return login_log_9;
	}

	public static String getNotice()
	{
		return notice;
	}

	public static String getNotice_1()
	{
		return notice_1;
	}

	public static String getNotice_10()
	{
		return notice_10;
	}

	public static String getNotice_11()
	{
		return notice_11;
	}

	public static String getNotice_12()
	{
		return notice_12;
	}

	public static String getNotice_13()
	{
		return notice_13;
	}

	public static String getNotice_14()
	{
		return notice_14;
	}

	public static String getNotice_15()
	{
		return notice_15;
	}

	public static String getNotice_16()
	{
		return notice_16;
	}

	public static String getNotice_17()
	{
		return notice_17;
	}

	public static String getNotice_18()
	{
		return notice_18;
	}

	public static String getNotice_19()
	{
		return notice_19;
	}

	public static String getNotice_2()
	{
		return notice_2;
	}

	public static String getNotice_3()
	{
		return notice_3;
	}

	public static String getNotice_4()
	{
		return notice_4;
	}

	public static String getNotice_5()
	{
		return notice_5;
	}

	public static String getNotice_6()
	{
		return notice_6;
	}

	public static String getNotice_7()
	{
		return notice_7;
	}

	public static String getNotice_8()
	{
		return notice_8;
	}

	public static String getNotice_9()
	{
		return notice_9;
	}

	public static String getStatus()
	{
		return status;
	}

	public static String getStatus_1()
	{
		return status_1;
	}

	public static String getStatus_2()
	{
		return status_2;
	}

	public static String getStatus_3()
	{
		return status_3;
	}

	public static String getStatus_4()
	{
		return status_4;
	}

	public static String getStatus_5()
	{
		return status_5;
	}

	public static String getStatus_6()
	{
		return status_6;
	}

	public static String getWarning()
	{
		return warning;
	}

	public static String getWarning_1()
	{
		return warning_1;
	}

	public static String getWarning_10()
	{
		return warning_10;
	}

	public static String getWarning_11()
	{
		return warning_11;
	}

	public static String getWarning_12()
	{
		return warning_12;
	}

	public static String getWarning_13()
	{
		return warning_13;
	}

	public static String getWarning_14()
	{
		return warning_14;
	}

	public static String getWarning_15()
	{
		return warning_15;
	}

	public static String getWarning_16()
	{
		return warning_16;
	}

	public static String getWarning_17()
	{
		return warning_17;
	}

	public static String getWarning_18()
	{
		return warning_18;
	}

	public static String getWarning_19()
	{
		return warning_19;
	}

	public static String getWarning_2()
	{
		return warning_2;
	}

	public static String getWarning_20()
	{
		return warning_20;
	}

	public static String getWarning_21()
	{
		return warning_21;
	}

	public static String getWarning_22()
	{
		return warning_22;
	}

	public static String getWarning_23()
	{
		return warning_23;
	}

	public static String getWarning_24()
	{
		return warning_24;
	}

	public static String getWarning_25()
	{
		return warning_25;
	}

	public static String getWarning_26()
	{
		return warning_26;
	}

	public static String getWarning_27()
	{
		return warning_27;
	}

	public static String getWarning_28()
	{
		return warning_28;
	}

	public static String getWarning_29()
	{
		return warning_29;
	}

	public static String getWarning_3()
	{
		return warning_3;
	}

	public static String getWarning_30()
	{
		return warning_30;
	}

	public static String getWarning_31()
	{
		return warning_31;
	}

	public static String getWarning_32()
	{
		return warning_32;
	}

	public static String getWarning_33()
	{
		return warning_33;
	}

	public static String getWarning_34()
	{
		return warning_34;
	}

	public static String getWarning_35()
	{
		return warning_35;
	}

	public static String getWarning_36()
	{
		return warning_36;
	}

	public static String getWarning_37()
	{
		return warning_37;
	}

	public static String getWarning_38()
	{
		return warning_38;
	}

	public static String getWarning_39()
	{
		return warning_39;
	}

	public static String getWarning_4()
	{
		return warning_4;
	}

	public static String getWarning_40()
	{
		return warning_40;
	}

	public static String getWarning_41()
	{
		return warning_41;
	}

	public static String getWarning_42()
	{
		return warning_42;
	}

	public static String getWarning_43()
	{
		return warning_43;
	}

	public static String getWarning_44()
	{
		return warning_44;
	}

	public static String getWarning_45()
	{
		return warning_45;
	}

	public static String getWarning_46()
	{
		return warning_46;
	}

	public static String getWarning_47()
	{
		return warning_47;
	}

	public static String getWarning_48()
	{
		return warning_48;
	}

	public static String getWarning_49()
	{
		return warning_49;
	}

	public static String getWarning_5()
	{
		return warning_5;
	}

	public static String getWarning_50()
	{
		return warning_50;
	}

	public static String getWarning_51()
	{
		return warning_51;
	}

	public static String getWarning_6()
	{
		return warning_6;
	}

	public static String getWarning_7()
	{
		return warning_7;
	}

	public static String getWarning_8()
	{
		return warning_8;
	}

	public static String getWarning_9()
	{
		return warning_9;
	}

	public static void init()
	{
		// use the user lang file if the ResourceBundle exist else we use the
		// default Locale
		init(Constants.LANG);
	}

	public static void init(String lang)
	{
		FastMap<String, String> langBundle = null;
		XStream xstream = new XStream(new DomDriver());

		try
		{
			File localLangFile = new File("lang/" + lang + ".xml");
			File defaultLangFile = new File("lang/en.xml");
			if (localLangFile.exists())
				langBundle = (FastMap<String, String>) xstream
						.fromXML(new FileInputStream(localLangFile));
			else
				if (defaultLangFile.exists())
					langBundle = (FastMap<String, String>) xstream
							.fromXML(new FileInputStream(defaultLangFile));
				else
				{ 
					System.out.println("The server cannot work without lang file");
					System.exit(0);
				}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		warning = langBundle.get("warning");
		error = langBundle.get("error");
		notice = langBundle.get("notice");
		info = langBundle.get("info");
		status = langBundle.get("status");
		fatalError = langBundle.get("fatal_error");

		error_1 = langBundle.get("error_1");
		error_2 = langBundle.get("error_2");
		error_3 = langBundle.get("error_3");
		error_4 = langBundle.get("error_4");
		error_5 = langBundle.get("error_5");
		error_6 = langBundle.get("error_6");
		error_7 = langBundle.get("error_7");
		error_8 = langBundle.get("error_8");
		error_9 = langBundle.get("error_9");
		error_10 = langBundle.get("error_10");
		error_11 = langBundle.get("error_11");
		error_12 = langBundle.get("error_12");
		error_13 = langBundle.get("error_13");
		error_14 = langBundle.get("error_14");
		error_15 = langBundle.get("error_15");
		error_16 = langBundle.get("error_16");
		error_17 = langBundle.get("error_17");
		error_18 = langBundle.get("error_18");
		error_19 = langBundle.get("error_19");
		error_20 = langBundle.get("error_20");
		error_21 = langBundle.get("error_21");
		error_22 = langBundle.get("error_22");
		error_23 = langBundle.get("error_23");
		error_24 = langBundle.get("error_24");
		error_25 = langBundle.get("error_25");

		fatal_error_1 = langBundle.get("fatal_error_1");

		notice_1 = langBundle.get("notice_1");
		notice_2 = langBundle.get("notice_2");
		notice_3 = langBundle.get("notice_3");
		notice_4 = langBundle.get("notice_4");
		notice_5 = langBundle.get("notice_5");
		notice_6 = langBundle.get("notice_6");
		notice_7 = langBundle.get("notice_7");
		notice_8 = langBundle.get("notice_8");
		notice_9 = langBundle.get("notice_9");
		notice_10 = langBundle.get("notice_10");
		notice_11 = langBundle.get("notice_11");
		notice_12 = langBundle.get("notice_12");
		notice_13 = langBundle.get("notice_13");
		notice_14 = langBundle.get("notice_14");
		notice_15 = langBundle.get("notice_15");
		notice_16 = langBundle.get("notice_16");
		notice_17 = langBundle.get("notice_17");
		notice_18 = langBundle.get("notice_18");
		notice_19 = langBundle.get("notice_19");

		warning_1 = langBundle.get("warning_1");
		warning_2 = langBundle.get("warning_2");
		warning_3 = langBundle.get("warning_3");
		warning_4 = langBundle.get("warning_4");
		warning_5 = langBundle.get("warning_5");
		warning_6 = langBundle.get("warning_6");
		warning_7 = langBundle.get("warning_7");
		warning_8 = langBundle.get("warning_8");
		warning_9 = langBundle.get("warning_9");
		warning_10 = langBundle.get("warning_10");
		warning_11 = langBundle.get("warning_11");
		warning_12 = langBundle.get("warning_12");
		warning_13 = langBundle.get("warning_13");
		warning_14 = langBundle.get("warning_14");
		warning_15 = langBundle.get("warning_15");
		warning_16 = langBundle.get("warning_16");
		warning_17 = langBundle.get("warning_17");
		warning_18 = langBundle.get("warning_18");
		warning_19 = langBundle.get("warning_19");
		warning_20 = langBundle.get("warning_20");
		warning_21 = langBundle.get("warning_21");
		warning_22 = langBundle.get("warning_22");
		warning_23 = langBundle.get("warning_23");
		warning_24 = langBundle.get("warning_24");
		warning_25 = langBundle.get("warning_25");
		warning_26 = langBundle.get("warning_26");
		warning_27 = langBundle.get("warning_27");
		warning_28 = langBundle.get("warning_28");
		warning_29 = langBundle.get("warning_29");
		warning_30 = langBundle.get("warning_30");
		warning_31 = langBundle.get("warning_31");
		warning_32 = langBundle.get("warning_32");
		warning_33 = langBundle.get("warning_33");
		warning_34 = langBundle.get("warning_34");
		warning_35 = langBundle.get("warning_35");
		warning_36 = langBundle.get("warning_36");
		warning_37 = langBundle.get("warning_37");
		warning_38 = langBundle.get("warning_38");
		warning_39 = langBundle.get("warning_39");
		warning_40 = langBundle.get("warning_40");
		warning_41 = langBundle.get("warning_41");
		warning_42 = langBundle.get("warning_42");
		warning_43 = langBundle.get("warning_43");
		warning_44 = langBundle.get("warning_44");
		warning_45 = langBundle.get("warning_45");
		warning_46 = langBundle.get("warning_46");
		warning_47 = langBundle.get("warning_47");
		warning_48 = langBundle.get("warning_48");
		warning_49 = langBundle.get("warning_49");
		warning_50 = langBundle.get("warning_50");
		warning_51 = langBundle.get("warning_51");

		info_1 = langBundle.get("info_1");
		info_2 = langBundle.get("info_2");
		info_3 = langBundle.get("info_3");
		info_4 = langBundle.get("info_4");
		info_5 = langBundle.get("info_5");
		info_6 = langBundle.get("info_6");
		info_7 = langBundle.get("info_7");
		info_8 = langBundle.get("info_8");
		info_9 = langBundle.get("info_9");
		info_10 = langBundle.get("info_10");
		info_11 = langBundle.get("info_11");
		info_12 = langBundle.get("info_12");

		login_log_1 = langBundle.get("login_log_1");
		login_log_2 = langBundle.get("login_log_2");
		login_log_3 = langBundle.get("login_log_3");
		login_log_4 = langBundle.get("login_log_4");
		login_log_5 = langBundle.get("login_log_5");
		login_log_6 = langBundle.get("login_log_6");
		login_log_7 = langBundle.get("login_log_7");
		login_log_8 = langBundle.get("login_log_8");
		login_log_9 = langBundle.get("login_log_9");
		login_log_10 = langBundle.get("login_log_10");
		login_log_11 = langBundle.get("login_log_11");
		login_log_12 = langBundle.get("login_log_12");
		login_log_13 = langBundle.get("login_log_13");
		login_log_14 = langBundle.get("login_log_14");
		login_log_15 = langBundle.get("login_log_15");
		login_log_16 = langBundle.get("login_log_16");
		login_log_17 = langBundle.get("login_log_17");
		login_log_18 = langBundle.get("login_log_18");
		login_log_19 = langBundle.get("login_log_19");
		login_log_20 = langBundle.get("login_log_20");
		login_log_21 = langBundle.get("login_log_21");
		login_log_22 = langBundle.get("login_log_22");
		login_log_23 = langBundle.get("login_log_23");
		login_log_24 = langBundle.get("login_log_24");
		login_log_25 = langBundle.get("login_log_25");
		login_log_26 = langBundle.get("login_log_26");
		login_log_27 = langBundle.get("login_log_27");
		login_log_28 = langBundle.get("login_log_28");
		login_log_29 = langBundle.get("login_log_29");
		login_log_30 = langBundle.get("login_log_30");
		login_log_31 = langBundle.get("login_log_31");
		login_log_32 = langBundle.get("login_log_32");
		login_log_33 = langBundle.get("login_log_33");
		login_log_34 = langBundle.get("login_log_34");
		login_log_35 = langBundle.get("login_log_35");
		login_log_36 = langBundle.get("login_log_36");
		login_log_37 = langBundle.get("login_log_37");
		login_log_38 = langBundle.get("login_log_38");
		login_log_39 = langBundle.get("login_log_39");
		login_log_40 = langBundle.get("login_log_40");
		login_log_41 = langBundle.get("login_log_41");
		login_log_42 = langBundle.get("login_log_42");
		login_log_43 = langBundle.get("login_log_43");
		login_log_44 = langBundle.get("login_log_44");
		login_log_45 = langBundle.get("login_log_45");
		login_log_46 = langBundle.get("login_log_46");
		login_log_47 = langBundle.get("login_log_47");
		login_log_48 = langBundle.get("login_log_48");

		status_1 = langBundle.get("status_1");
		status_2 = langBundle.get("status_2");
		status_3 = langBundle.get("status_3");
		status_4 = langBundle.get("status_4");
		status_5 = langBundle.get("status_5");
		status_6 = langBundle.get("status_6");

		char_log_1 = langBundle.get("char_log_1");
		char_log_2 = langBundle.get("char_log_2");

	}

	public static void setInfo_12(String info_12)
	{
		MultilanguageManagement.info_12 = info_12;
	}

}

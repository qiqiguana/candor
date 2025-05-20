package net.sf.xbus.admin.html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * The <code>ConfigurationBean</code> stored instance from
 * {@link net.sf.xbus.base.core.config.Configuration}and comprises this
 * configuration in the form of the opened/disclosed three-level menu:
 * <ul>
 * <li>The <b>chapter </b> is the top level.</li>
 * <li>Every chapter contains one or more <b>sections </b></li>
 * <li><b>Keys and values </b> are at the bottom of the hierarchie, beyond the
 * sections.</li>
 * </ul>
 */
public class ConfigurationBean
{

	private Configuration config = null;

	private List chapterList = new Vector();

	private HashMap clickedMenusHash = new HashMap();

	private String chapterMenu = "";

	private String sectionMenu = "";

	private static final String leer = "leer";

	private boolean color;

	private static final String bgColorDark = "#D1D0C7";

	private static final String bgColorLight = "#E7E9E6";

	private static final String openTagTrColor = "<tr align=left bgcolor=\"";

	private static final String openTagHref = "href=\"/xbus/admin/jsp/ConfigurationPage.jsp?parameter=leer";

	private static final String openTagTdA_chapter = "\"><td width=\"15\"></td><td width=\"*\"><a class=\"chapter\" title=\"chapter\"";

	private static final String openTagParameter_chapterMenu = "&chapterMenu=";

	private static final String openTagParameter_sectionMenu = "&sectionMenu=";

	private static final String openTagPosition = "#position\" target=\"haupt\"";

	private static final String openTagTrTable_section = "<tr align=left><td width=\"15\"></td><td width=\"*\"><table cellspacing=\"0\" width=\"100%\">";

	private static final String openTagTrTd_keys = "<tr align=left><td width=\"40\"></td>";

	private static final String openTagAnker = "name=\"position\"";

	private static final String openTagTrTdA_memoSection = "<tr class=\"sections\" align=left><td class=\"sections\" width=\"40\"><a class=\"memo\" title=\"section\"";

	private static final String openTagImage = "<img src=\"/xbus/admin/images/section.gif\" border=\"0\" align=\"right\"></a></td>";

	private static final String openTagTdA_section = "<td width=\"*\"><a class=\"section\" title=\"section\"";

	private static final String openTagTableKey = "<table class=\"keys\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=left><tr class=\"keys\" align=left><td width=\"50\"></td><td class=\"keys\" width=\"130\">";

	private static final String openTagTableTransform = "<table class=\"keys\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=left><tr class=\"keys\" align=left><td width=\"50\" class=\"keys\"></td><td class=\"keys\" colspan=2>";

	private static final String openTagValue_transform = "<tr class=\"keys\" align=left><td width=\"50\" class=\"keys\"></td><td class=\"keys\" colspan=2>";

	private static final String openTagTd_restkeys = "<td class=\"keys\" width=\"*\">";

	private static final String openTagTd_restJournal = "<td class=\"journal\" width=\"*\">";

	private static final String fullTagTransform_value = "<table table class=\"keys\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=left><tr class=\"keys\" align=left><td width=\"240\" class=\"keys\">&nbsp;</td><td width=\"*\" class=\"keys\">";

	private static final String closeTagValue_transform = "</td></tr></table></td></tr>";

	private static final String closeTagATdTr = "</a></td></tr>";

	private static final String closeTagTableTdTr = "</table></td></tr>";

	private static final String closeTagTdTr = "</td></tr>";

	private static final String closeTagTd = "</td>";

	private static final String closeTag = ">";

	/**
	 * Allows you to set the property <b><i>chapterMenu </i> </b>
	 */
	public void setChapterMenu(String newChapterMenu)
	{
		this.chapterMenu = newChapterMenu;
	}

	/**
	 * Allows you to set the property <b><i>section </i> </b>
	 */
	public void setSectionMenu(String newSectionMenu)
	{
		this.sectionMenu = newSectionMenu;
	}

	/**
	 * <code>checkMenus()</code> method composes the HashMap similar to the
	 * configuration type. Depending on the properties <code> chapterMenu</code>
	 * and the <code>sectionMenu</code> from the
	 * <code>ConfigurationPage</code> ,it adds or removes the appropriate
	 * fields from this HashMap, leaving in it only open fields.
	 */

	private void checkMenus()
	{
		HashMap sectionMap = null;
		HashMap keyMap = null;

		// add values to the clickedHashMap
		if (!this.chapterMenu.equals(leer))
		{
			if (this.sectionMenu.equals(leer))
			{
				if (!clickedMenusHash.containsKey(this.chapterMenu))
				{
					sectionMap = new HashMap();
					clickedMenusHash.put(chapterMenu, sectionMap);
				}
				else
					clickedMenusHash.remove(this.chapterMenu);

			}
			else
			{
				sectionMap = (HashMap) clickedMenusHash.get(this.chapterMenu);

				if (!sectionMap.containsKey(this.sectionMenu))
				{
					keyMap = new HashMap();
					sectionMap.put(this.sectionMenu, keyMap);
				}

				else
					sectionMap.remove(this.sectionMenu);

			}
		}

	}

	/**
	 * Deafulst constructor. Creates instance from <@link
	 * net.sf.xbus.base.core.Configuration>.
	 */
	public ConfigurationBean() throws XException
	{
		config = Configuration.getInstance();
		chapterList = config.getChapters();
	}

	/**
	 * Color to the opposite is changed
	 */
	private String checkColor(boolean color)
	{
		if (color)
		{
			this.color = false;
			return ConfigurationBean.bgColorDark;
		}
		else
		{
			this.color = true;
			return ConfigurationBean.bgColorLight;
		}
	}

	/**
	 * Gets property <b><i>configurationAsTableRows </i> </b>. This method
	 * compares each object from the configuration with the presence in
	 * <code>clickedMenuHash</code>, composes and returnes the String with
	 * all Html tages to the <code>ConfiguratonPage.jsp</code>.
	 * 
	 * @return String with Html tages for the configurationPage.jsp
	 */
	public String getConfigurationAsTableRows()
	{

		this.checkMenus();
		StringBuffer configBuffer = new StringBuffer();

		this.color = true;

		for (int i = 0; i < chapterList.size(); i++)
		{
			String section = null;
			String key = null;
			String value = null;

			String chapter = (String) chapterList.get(i);
			configBuffer.append(ConfigurationBean.openTagTrColor);
			configBuffer.append(checkColor(this.color));
			configBuffer.append(ConfigurationBean.openTagTdA_chapter);
			configBuffer.append(ConfigurationBean.openTagHref);
			configBuffer.append(ConfigurationBean.openTagParameter_chapterMenu);
			configBuffer.append(chapter);
			configBuffer.append(ConfigurationBean.openTagParameter_sectionMenu);
			configBuffer.append(ConfigurationBean.leer);
			configBuffer.append(ConfigurationBean.openTagPosition);

			// anker
			if (chapter.equals(chapterMenu))
			{
				configBuffer.append(ConfigurationBean.openTagAnker);
			}
			configBuffer.append(ConfigurationBean.closeTag);
			configBuffer.append(chapter);
			configBuffer.append(ConfigurationBean.closeTagATdTr);

			List sectionList = new Vector();

			// sections
			if (this.clickedMenusHash.containsKey(chapter))
			{
				sectionList = config.getSections(chapter);
				for (int j = 0; j < sectionList.size(); j++)
				{
					List keyList = new Vector();
					section = (String) sectionList.get(j);

					configBuffer
							.append(ConfigurationBean.openTagTrTable_section);
					configBuffer
							.append(ConfigurationBean.openTagTrTdA_memoSection);
					configBuffer.append(ConfigurationBean.openTagHref);
					configBuffer
							.append(ConfigurationBean.openTagParameter_chapterMenu);
					configBuffer.append(chapter);
					configBuffer
							.append(ConfigurationBean.openTagParameter_sectionMenu);
					configBuffer.append(section);
					configBuffer.append(ConfigurationBean.openTagPosition);
					configBuffer.append(ConfigurationBean.closeTag);
					configBuffer.append(ConfigurationBean.openTagImage);
					configBuffer.append(ConfigurationBean.openTagTdA_section);
					configBuffer.append(ConfigurationBean.openTagHref);
					configBuffer
							.append(ConfigurationBean.openTagParameter_chapterMenu);
					configBuffer.append(chapter);
					configBuffer
							.append(ConfigurationBean.openTagParameter_sectionMenu);
					configBuffer.append(section);
					configBuffer.append(ConfigurationBean.openTagPosition);
					configBuffer.append(ConfigurationBean.closeTag);
					configBuffer.append(section);
					configBuffer.append(ConfigurationBean.closeTagATdTr);

					this.color = true;

					// keys + values
					HashMap sectionClicked = (HashMap) this.clickedMenusHash
							.get(chapter);
					if (sectionClicked.containsKey(section))
					{
						Map keyAndValueMap = config.getKeysAndValues(chapter,
								section);
						keyList.addAll(keyAndValueMap.keySet());
						for (int k = 0; k < keyList.size(); k++)
						{
							key = (String) keyList.get(k);
							value = (String) keyAndValueMap.get(key);
							if (chapter.equals("Transform"))
							{
								configBuffer
										.append(ConfigurationBean.openTagTrTd_keys);
								configBuffer
										.append(ConfigurationBean.openTagTd_restJournal);
								configBuffer
										.append(ConfigurationBean.openTagTableTransform);
								configBuffer.append(key);
								configBuffer
										.append(ConfigurationBean.closeTagTdTr);
								configBuffer
										.append(ConfigurationBean.openTagValue_transform);
								configBuffer
										.append(ConfigurationBean.fullTagTransform_value);
								configBuffer.append(value);
								configBuffer
										.append(ConfigurationBean.closeTagValue_transform);

							}
							else
							{
								configBuffer
										.append(ConfigurationBean.openTagTrTd_keys);
								configBuffer
										.append(ConfigurationBean.openTagTd_restkeys);
								configBuffer
										.append(ConfigurationBean.openTagTableKey);
								configBuffer.append(key);
								configBuffer
										.append(ConfigurationBean.closeTagTd);
								configBuffer
										.append(ConfigurationBean.openTagTd_restkeys);
								if (key.equals("Password"))
								{
									configBuffer.append("***");
								}
								else
								{
									configBuffer.append(value);
								}
								configBuffer
										.append(ConfigurationBean.closeTagTdTr);
							}

							configBuffer
									.append(ConfigurationBean.closeTagTableTdTr);
						}
					}

					configBuffer.append(ConfigurationBean.closeTagTableTdTr);
				}
			}
		}
		return configBuffer.toString();

	}

}

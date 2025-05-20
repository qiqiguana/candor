package net.sf.xbus.admin.html;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;

/**
 * TODO Kommentierung
 */
public class DeletedMessageStoreBean
{
	public String getDeletedMessageFilenames()
	{
		StringBuffer buf = new StringBuffer(
				"<option selected>------------------------------");

		try
		{
			String[] filenames = DeletedMessageStore
					.getDeletedMessageFilenames();

			for (int i = 0; i < filenames.length; i++)
			{
				buf.append("<option>");
				buf.append(filenames[i]);
			}
		}
		catch (XException e)
		{
			// do nothing
		}
		return buf.toString();
	}
}

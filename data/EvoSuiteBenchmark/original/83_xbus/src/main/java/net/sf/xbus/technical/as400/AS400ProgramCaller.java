package net.sf.xbus.technical.as400;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.timeoutcall.Callable;
import net.sf.xbus.base.core.trace.Trace;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400Message;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.ProgramCall;
import com.ibm.as400.access.ProgramParameter;

/**
 * Used by {@link AS400ProgramSender#execute(String, Object)} together with
 * {@link net.sf.xbus.base.core.timeoutcall.TimedCallable} to be able to stop
 * the execution of the program after a timeout.
 */
public class AS400ProgramCaller implements Callable
{
	private AS400 mAS400 = null;
	private String mProgramName = null;
	private ProgramParameter[] mParameters = null;

	public AS400ProgramCaller(AS400 as400, String programName,
			ProgramParameter[] parameters, CharConverter converter)
	{
		mAS400 = as400;
		mProgramName = programName;
		mParameters = parameters;
	}

	/**
	 * Calling the AS400 program.
	 */
	public Object call() throws XException
	{
		String message = "Call AS400 program " + mProgramName;
		if (mParameters != null && mParameters.length > 0)
			message = message
					+ " "
					+ CharConverter.byteArrayToString(mAS400, mParameters[0]
							.getInputData());

		// Constructs a program call object for specified server, program name,
		// and parameter list.
		ProgramCall programCall = new ProgramCall(mAS400, mProgramName,
				mParameters);

		try
		{
			Trace.info("Program " + mProgramName + " called");
			// Run the program.
			if (!programCall.run())
			{
				// Show the messages
				AS400Message[] msgList = programCall.getMessageList();
				StringBuffer buf = new StringBuffer();
				for (int i = 1; i < msgList.length; i++)
				{
					buf.append(msgList[i].getText());
				} // for (int i = 1; i < msgList.length; i++)

				List params = new Vector();
				params.add(buf);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "36", params);
			} // if (!programCall.run())
			Trace.info("Program " + mProgramName + " returned");
		} // try
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		} // catch
		finally
		{
			mAS400.disconnectService(AS400.COMMAND);
		}
		return mParameters;
	}

	/**
	 * Stopping the execution of the program by disconnecting from the AS400.
	 */
	public void stop()
	{
		mAS400.disconnectService(AS400.COMMAND);
	}

}

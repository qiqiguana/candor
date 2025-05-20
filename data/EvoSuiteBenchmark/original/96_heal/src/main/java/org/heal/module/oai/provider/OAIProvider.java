/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.heal.module.oai.provider.verb.OAIVerb;

/**
 * @author Seth Wright
 *
 */
public class OAIProvider {
	public static final boolean debug = false;
	private static OAIProviderConfig config = null;
	public static void init(final OAIProviderConfig config) {
		OAIProvider.config = config;
	}
	
	//NOTE: the parameters Map contains String arrays (String[])
	//This is required because we have to detect duplicate parameters and
	//give a badArgument error
	public static void processRequest(final String requestURL,
									  final DataAccessor dataAccessor,
									  final Map parameters,
									  final PrintWriter out) 
	throws IOException
	{

		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.print("<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		out.println("xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd\">");
		//these should be ordered by which is most likely to occur
		ValidatedInput input = validateInput(dataAccessor,parameters,out);
		writeOAIResponseHeader(input,
								parameters,
								dataAccessor,
								requestURL,
								out);
		if (input.errorList.size() == 0) {
			input.verb.execute(input,config,dataAccessor,out);
			dataAccessor.closeQuery();			
		} else {
			Iterator iter = input.errorList.iterator();
			while(iter.hasNext()) {
				out.println((String)iter.next());
			}
		}
		out.println("</OAI-PMH>");
	}
	
	private static void writeOAIResponseHeader(final ValidatedInput input,
												final Map parameters,
												final DataAccessor accessor, 
												final String requestURL, 
												final PrintWriter writer) {
		//get nowUTC from the database because that's the relevant time.
		Date responseDate = accessor.getNow();
		String now = OAIGranularity.yearMonthDayHourMinuteSecond.format(responseDate);
		writer.print("<responseDate>");
		writer.print(now);
		writer.println("</responseDate>");
		writer.print("<request");

		if (input.verb != null) {
			writer.print(" verb=\"");				
			writer.print(input.verb.getName());
			writer.print("\"");
		}
		if (input.identifier != null) {
			writer.print(" identifier=\"");				
			writer.print(input.identifier);
			writer.print("\"");
		}
		if (input.format != null) {
			writer.print(" metadataPrefix=\"");				
			writer.print(input.format.getPrefix());
			writer.print("\"");
		}
		if (input.from != null) {
			writer.print(" from=\"");				
			writer.print(((String[])parameters.get("from"))[0]);
			writer.print("\"");
		}
		if (input.until != null) {
			writer.print(" until=\"");				
			writer.print(((String[])parameters.get("until"))[0]);
			writer.print("\"");
		}
		if (input.resumptionToken != null) {
			writer.print(" resumptionToken=\"");				
			writer.print(((String[])parameters.get("resumptionToken"))[0]);
			writer.print("\"");
		}
		if (input.set != null) {
			writer.print(" set=\"");				
			writer.print(((String[])parameters.get("set"))[0]);
			writer.print("\"");
		}
		writer.print(">");
		writer.print(requestURL);
		writer.println("</request>");		
	}

	public static void writeString(final String tagName,final String value,final PrintWriter writer) {
		writer.print("<"+tagName+">");
		writer.print(value);
		writer.println("</"+tagName+">");		
	}

	public static void writeStrings(final String[] strings, 
									 final String tagName, 
									 final PrintWriter writer) 
	{
		if (strings == null) return;
		for(int i=0;i<strings.length;i++) {
			writeString(tagName,strings[i],writer);
		}
	}

	public static String getErrorString(final OAIErrorCode code, final String description) {
		StringBuffer buf = new StringBuffer();
		buf.append("<error code=\"");
		buf.append(code.getName());
		buf.append("\">");
		if (description != null) {
			buf.append(description);
		}
		buf.append("</error>");		
		return buf.toString();
	}
	
	public static void writeResumptionToken(final OAIResumptionToken token,final PrintWriter writer) 
	{
		if (token == null) {
			writer.println("<resumptionToken></resumptionToken>");
			return;
		}
		long size = token.getCompleteListSize();
		long cursor = token.getCursor();
		boolean queryCompleted = token.queryCompleted();
		writer.print("<resumptionToken");
		if (!queryCompleted) {
			Date expirationDate = token.getExpirationDate();
			if (expirationDate != null) {
				writer.print(" expirationDate=\""+OAIGranularity.yearMonthDayHourMinuteSecond.format(expirationDate)+"\"");
			}
		}
		if (size > 0) {
			writer.print(" completeListSize=\""+size+"\"");
		}
		if (cursor >= 0) {
			writer.print(" cursor=\""+cursor+"\"");				
		}
		if (queryCompleted) {
			writer.println("/>");
		} else {
			writer.print(">");
			writer.print(token.getContent());
			writer.println("</resumptionToken>");
		}
	}
			
	public static void writeRecord(final OAIRecord record, final OAIMetadataHandler handler, PrintWriter writer) throws IOException {
		writer.println("<record>");
		writeRecordHeader(record,writer);
		if (!record.isDeleted()) {
			OAIRecordAdapter adapter = record.getRecordAdapter(handler.getFormat());
			//if the record was deleted, only the header is returned because there is no record 
			//(if there was a record, it wouldn't have been deleted, would it?
			writer.print("<metadata>");
			handler.writeMetadata(adapter,writer);		
			writer.println("</metadata>");
			if (adapter.hasOAIAbout() || adapter.hasCustomAbout()) {
				writer.print("<about>");
				if (adapter.hasOAIAbout()) {
					handler.writeOAIAbout(adapter,writer);
				}
				if (adapter.hasCustomAbout()) {
					handler.writeCustomAbout(adapter,writer);
				}
				writer.println("</about>");
			}
		}
		writer.println("</record>");
	}

	public static void writeRecordHeader(final OAIRecord record, final PrintWriter writer) {
		if (record.isDeleted()) {
			writer.println("<header status=\"deleted\">");
		} else {
			writer.println("<header>");
		}
		writer.print("<identifier>");
		writer.print(record.getOAIIdentifier());
		writer.println("</identifier>");
		writer.print("<datestamp>");
		writer.print(OAIGranularity.yearMonthDayHourMinuteSecond.format(record.getDateStamp()));
		writer.println("</datestamp>");
		OAISet[] sets = record.getSets();
		if (sets != null && sets.length > 0) {
			for (int i=0;i<sets.length;i++) {
				writer.print("<setSpec>");
				writer.print(sets[i].getSpec());
				writer.println("</setSpec>");
			}
		}
		writer.println("</header>");
	}

	private static String getParameterUnique(String paramName,Map parameters,ValidatedInput validated) {
		String retval = null;
		String[] paramArr = (String[])parameters.get(paramName);
		if (paramArr != null) {
			if (paramArr.length > 1) {
				validated.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,"Unexpected duplicate paramater '"+paramName+"'.  Please resubmit the request with only one."));
			}
			//we still only validate the first one.
			retval = paramArr[0];
		}
		return retval;
	}
	
	/* Return null if an error occurs, otherwise it returns a DateHolder
	 * with the from and until set to the Date versions of the input strings
	 * 
	 * If the input strings are null, the corresponding Date will also be
	 * set to null.
	 */
	private static ValidatedInput validateInput(final DataAccessor dataAccessor,
												final Map parameters,
												final PrintWriter writer) 
	{
		ValidatedInput retval = new ValidatedInput();
		//verb,identifier,metadataPrefix,from,until,resumptionToken,set,
		String verbStr = getParameterUnique("verb",parameters,retval);
		String resumptionToken = getParameterUnique("resumptionToken",parameters,retval);
		String identifier = getParameterUnique("identifier",parameters,retval);
		String metadataPrefix = getParameterUnique("metadataPrefix",parameters,retval);
		String set = getParameterUnique("set",parameters,retval);
		String from = getParameterUnique("from",parameters,retval);
		String until = getParameterUnique("until",parameters,retval);
		System.out.println("OAI request = [verb="+verbStr+", identifier="+identifier+", metadataPrefix="+metadataPrefix+", from="+from+", until="+until+", resumptionToken="+resumptionToken+", set="+set+"]");
		parameters.remove("verb");
		retval.verb = (verbStr!=null)?OAIVerb.getVerb(verbStr):null;
		if (verbStr == null) {
			retval.errorList.add(getErrorString(OAIErrorCode.BAD_VERB,"The request was missing a verb."));
		} else if (retval.verb == null) {
			retval.errorList.add(getErrorString(OAIErrorCode.BAD_VERB,"Illegal OAI verb '"+verbStr+"'"));
		}

		if (resumptionToken != null) {
			retval.resumptionToken = dataAccessor.parseResumptionToken(resumptionToken);
			if (retval.resumptionToken == null) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_RESUMPTION_TOKEN,resumptionToken+" does not conform to the resumption token format of this provider."));
			} else if (dataAccessor.isExpired(retval.resumptionToken)) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_RESUMPTION_TOKEN,"resumptionToken '"+resumptionToken+"' has expired."));
			}
			if (identifier != null || metadataPrefix != null || from != null || until != null || set != null) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,"The resumptionToken argument is an exclusive argument but other parameters were included in this request."));
			}
			if (retval.resumptionToken != null) {
				if (retval.resumptionToken.getMetadataPrefix() != null) {
					retval.format = config.getMetadataFormat(retval.resumptionToken.getMetadataPrefix());
				} else {
					System.out.println("resumptiontoken prefix was null");
				}
			}
		}
		if (identifier != null) {
			if (!dataAccessor.validateIdentifier(identifier)) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,identifier+" does not conform to the identifier format of this provider."));
				retval.identifier = null;
			} else {
				retval.identifier = identifier;
			}
		}
		//validate that the metadataPrefix is supported
		if (metadataPrefix != null) {
			OAIMetadataFormat format = config.getMetadataFormat(metadataPrefix);
			if (format == null || format.getHandler() == null) {
				retval.errorList.add(getErrorString(OAIErrorCode.CANNOT_DISSEMINATE_FORMAT,"The metadataPrefix '"+metadataPrefix+"' is not recognized by this provider."));
				retval.format = null;
			} else {
				retval.format = format;
			}
		}
		//set validation
		if (set != null) {
			//we don't check for no set heirarchy here, that gets pushed to the later responses
			if (!dataAccessor.validateSet(set)) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,set+" does not conform to the set token format of this provider."));
				retval.set = null;
			} else if (!dataAccessor.setExists(set)) {
				retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,set+" is not a valid set for this repository."));
				retval.set = null;
			} else {
				retval.set = set;
			}
		}
		//now we check the dates
		if (until != null || from != null) {
			//we only care about this if we have dates to parse...
			//the parsing should be based on the granularity we support
			OAIGranularity granularity = config.getGranularity();
			if (until != null) {
				try {
					retval.until = granularity.parse(until);
				} catch (ParseException ex) {
					retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,"The until date '"+until+"' is not in the expected format of "+granularity));
					retval.until = null;
				}
			}
			if (from != null) {
				try {
					retval.from = granularity.parse(from);
				} catch (ParseException ex) {
					retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,"The from date '"+from+"' is not in the expected format of "+granularity));
					retval.from = null;
				}
			}
			if (retval.from != null && retval.until != null) {
				if (retval.from.getTime() > retval.until.getTime()) {
					retval.errorList.add(getErrorString(OAIErrorCode.BAD_ARGUMENT,"The from date '"+from+"' cannot be later than the until date '"+until+"'"));
				}
			}
//			if (retval.until != null) {
//				Date earliestDate = config.getEarliestDatestamp();
//				if (retval.until.getTime() < earliestDate.getTime()) {
//					String earliestAvailable = granularity.format(earliestDate);
//					writeError(OAIErrorCode.BAD_ARGUMENT,"The until date '"+until+"' is earlier than this repository's earliest available date '"+earliestAvailable+"'",writer);					
//					errorOccurred = true;
//				}
//			}
		}
		if (retval.verb != null) {
			retval.verb.validateInput(retval,parameters);
		}
		return retval;
	}
}

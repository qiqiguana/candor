package net.sf.gaeappmanager.google.appengine;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

import java.io.FileInputStream;
import java.math.BigDecimal;

import net.sf.gaeappmanager.google.appengine.Quota;
import net.sf.gaeappmanager.google.appengine.QuotaDetails;
import net.sf.gaeappmanager.google.appengine.QuotaDetailsParser;
import net.sf.gaeappmanager.google.appengine.QuotaValue;

import org.testng.annotations.Test;

public class QuotaDetailsParserTest {

	private static void checkQuotaValue(QuotaDetails details, Quota quota,
			String value, String limit, String percent, String unit,
			Boolean isOkay) {
		QuotaValue qVal = details.findQuota(quota);
		assertNotNull(qVal);
		assertEquals(new BigDecimal(value), qVal.getValue());
		assertEquals(new BigDecimal(limit), qVal.getLimit());
		assertEquals(new BigDecimal(percent), qVal.getPercent());
		assertEquals(unit, qVal.getUnit());

		if (isOkay) {
			assertTrue(qVal.isOkay());
		} else {
			assertFalse(qVal.isOkay());
		}
	}

	@Test
	public void test() throws Exception {
		QuotaDetails details = null;

		QuotaDetailsParser quotaDetailsParser = new QuotaDetailsParser();

		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream("src/test/resources/details.html");

			details = quotaDetailsParser.parse(inputStream);
		} finally {
			inputStream.close();
		}

		assertEquals(24, details.getResetIntervalHours());
		assertEquals(18, details.getNextResetWithinHours());

		checkQuotaValue(details, Quota.REQUESTS_CPU_TIME, "0.00", "6.50", "0",
				"CPU hours", true);
		checkQuotaValue(details, Quota.REQUESTS_COUNT, "0", "1333328", "0", "",
				true);
		checkQuotaValue(details, Quota.REQUESTS_OUTGOING_BANDWIDTH, "0.00",
				"1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.REQUESTS_INCOMING_BANDWIDTH, "0.00",
				"1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.REQUESTS_SECURE_COUNT, "0", "1333328",
				"0", "", true);
		checkQuotaValue(details, Quota.REQUESTS_SECURE_OUTGOING_BANDWIDTH,
				"0.00", "1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.REQUESTS_SECURE_INCOMING_BANDWIDTH,
				"0.00", "1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.STORAGE_DATASTORE_API_CALLS, "0",
				"10368000", "0", "", true);
		checkQuotaValue(details, Quota.STORAGE_BLOBSTORE_API_CALLS, "0",
				"10368000", "0", "", true);
		checkQuotaValue(details, Quota.STORAGE_TOTAL_STORED_DATA, "0.00",
				"1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.STORAGE_BLOBSTORE_STORED_DATA, "0.00",
				"1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.STORAGE_DATA_SENT_TO_DATASTORE_API,
				"0.00", "12.00", "0", "GBytes", true);
		checkQuotaValue(details,
				Quota.STORAGE_DATA_RECEIVED_FROM_DATASTORE_API, "0.00",
				"116.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.STORAGE_DATASTORE_CPU_TIME, "0.00",
				"62.11", "0", "CPU hours", true);
		checkQuotaValue(details, Quota.MAIL_MAIL_API_CALLS, "0", "7000", "0",
				"", true);
		checkQuotaValue(details, Quota.MAIL_RECIPIENTS_EMAILED, "0", "2000",
				"0", "", true);
		checkQuotaValue(details, Quota.MAIL_ADMINS_EMAILED, "0", "5000", "0",
				"", true);
		checkQuotaValue(details, Quota.MAIL_MESSAGE_BODY_DATA_SENT, "0.00",
				"0.06", "0", "GBytes", true);
		checkQuotaValue(details, Quota.MAIL_ATTACHMENTS_SENT, "0", "2000", "0",
				"", true);
		checkQuotaValue(details, Quota.MAIL_ATTACHMENT_DATA_SENT, "0.00",
				"0.10", "0", "GBytes", true);
		checkQuotaValue(details, Quota.URLFETCH_API_CALLS, "0", "657084", "0",
				"", true);
		checkQuotaValue(details, Quota.URLFETCH_DATA_SENT, "0.00", "4.00", "0",
				"GBytes", true);
		checkQuotaValue(details, Quota.URLFETCH_DATA_RECEIVED, "0.00", "4.00",
				"0", "GBytes", true);
		checkQuotaValue(details, Quota.IMAGEMANIPULATION_API_CALLS, "0",
				"864000", "0", "", true);
		checkQuotaValue(details, Quota.IMAGEMANIPULATION_DATA_SENT_TO_API,
				"0.00", "1.00", "0", "GBytes", true);
		checkQuotaValue(details,
				Quota.IMAGEMANIPULATION_DATA_RECEIVED_FROM_API, "0.00", "5.00",
				"0", "GBytes", true);
		checkQuotaValue(details,
				Quota.IMAGEMANIPULATION_TRANSFORMATIONS_EXECUTED, "0",
				"2592000", "0", "", true);
		checkQuotaValue(details, Quota.MEMCACHE_API_CALLS, "0", "8640000", "0",
				"", true);
		checkQuotaValue(details, Quota.MEMCACHE_DATA_SENT_TO_API, "0.00",
				"1.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.MEMCACHE_DATA_RECEIVED_FROM_API, "0.00",
				"5.00", "0", "GBytes", true);
		checkQuotaValue(details, Quota.XMPP_API_CALLS, "0", "657084", "0", "",
				true);
		checkQuotaValue(details, Quota.XMPP_DATA_SENT, "0.00", "4.00", "0",
				"GBytes", true);
		checkQuotaValue(details, Quota.XMPP_RECIPIENTS_MESSAGED, "0", "657084",
				"0", "", true);
		checkQuotaValue(details, Quota.XMPP_INVITATIONS_SENT, "0", "1000", "0",
				"", true);
		checkQuotaValue(details, Quota.TASKQUEUE_API_CALLS, "0", "100000", "0",
				"", true);
		checkQuotaValue(details, Quota.DEPLOYMENTS_COUNT, "0", "1000", "0", "",
				true);
	}
}

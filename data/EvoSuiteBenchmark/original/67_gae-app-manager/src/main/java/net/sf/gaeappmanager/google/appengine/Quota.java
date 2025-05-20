package net.sf.gaeappmanager.google.appengine;

/**
 * Existing quota enums.
 * 
 * @author Alois Belaska
 */
public enum Quota {

	REQUESTS_CPU_TIME(QuotaGroup.REQUESTS, "CPU Time"),

	REQUESTS_COUNT(QuotaGroup.REQUESTS, "Requests"),

	REQUESTS_OUTGOING_BANDWIDTH(QuotaGroup.REQUESTS, "Outgoing Bandwidth"),

	REQUESTS_INCOMING_BANDWIDTH(QuotaGroup.REQUESTS, "Incoming Bandwidth"),

	REQUESTS_SECURE_COUNT(QuotaGroup.REQUESTS, "Secure Requests"),

	REQUESTS_SECURE_OUTGOING_BANDWIDTH(QuotaGroup.REQUESTS,
			"Secure Outgoing Bandwidth"),

	REQUESTS_SECURE_INCOMING_BANDWIDTH(QuotaGroup.REQUESTS,
			"Secure Incoming Bandwidth"),

	STORAGE_DATASTORE_API_CALLS(QuotaGroup.STORAGE, "Datastore API Calls"),

	STORAGE_BLOBSTORE_API_CALLS(QuotaGroup.STORAGE, "Blobstore API Calls"),

	STORAGE_TOTAL_STORED_DATA(QuotaGroup.STORAGE, "Total Stored Data"),

	STORAGE_BLOBSTORE_STORED_DATA(QuotaGroup.STORAGE, "Blobstore Stored Data"),

	STORAGE_DATA_SENT_TO_DATASTORE_API(QuotaGroup.STORAGE,
			"Data Sent to Datastore API"),

	STORAGE_DATA_RECEIVED_FROM_DATASTORE_API(QuotaGroup.STORAGE,
			"Data Received from Datastore API"),

	STORAGE_DATASTORE_CPU_TIME(QuotaGroup.STORAGE, "Datastore CPU Time"),

	MAIL_MAIL_API_CALLS(QuotaGroup.MAIL, "Mail API Calls"),

	MAIL_RECIPIENTS_EMAILED(QuotaGroup.MAIL, "Recipients Emailed"),

	MAIL_ADMINS_EMAILED(QuotaGroup.MAIL, "Admins Emailed"),

	MAIL_MESSAGE_BODY_DATA_SENT(QuotaGroup.MAIL, "Message Body Data Sent"),

	MAIL_ATTACHMENTS_SENT(QuotaGroup.MAIL, "Attachments Sent"),

	MAIL_ATTACHMENT_DATA_SENT(QuotaGroup.MAIL, "Attachment Data Sent"),

	URLFETCH_API_CALLS(QuotaGroup.URLFETCH, "UrlFetch API Calls"),

	URLFETCH_DATA_SENT(QuotaGroup.URLFETCH, "UrlFetch Data Sent"),

	URLFETCH_DATA_RECEIVED(QuotaGroup.URLFETCH, "UrlFetch Data Received"),

	IMAGEMANIPULATION_API_CALLS(QuotaGroup.IMAGEMANIPULATION,
			"Image Manipulation API Calls"),

	IMAGEMANIPULATION_DATA_SENT_TO_API(QuotaGroup.IMAGEMANIPULATION,
			"Data Sent to API"),

	IMAGEMANIPULATION_DATA_RECEIVED_FROM_API(QuotaGroup.IMAGEMANIPULATION,
			"Data Received from API"),

	IMAGEMANIPULATION_TRANSFORMATIONS_EXECUTED(QuotaGroup.IMAGEMANIPULATION,
			"Transformations executed"),

	MEMCACHE_API_CALLS(QuotaGroup.MEMCACHE, "Memcache API Calls"),

	MEMCACHE_DATA_SENT_TO_API(QuotaGroup.MEMCACHE, "Data Sent to API"),

	MEMCACHE_DATA_RECEIVED_FROM_API(QuotaGroup.MEMCACHE,
			"Data Received from API"),

	XMPP_API_CALLS(QuotaGroup.XMPP, "XMPP API Calls"),

	XMPP_DATA_SENT(QuotaGroup.XMPP, "XMPP Data Sent"),

	XMPP_RECIPIENTS_MESSAGED(QuotaGroup.XMPP, "Recipients Messaged"),

	XMPP_INVITATIONS_SENT(QuotaGroup.XMPP, "Invitations Sent"),

	TASKQUEUE_API_CALLS(QuotaGroup.TASKQUEUE, "Task Queue API Calls"),

	DEPLOYMENTS_COUNT(QuotaGroup.DEPLOYMENTS, "Deployments");

	private QuotaGroup quotaGroup;

	private String quotaName;

	public QuotaGroup getQuotaGroup() {
		return quotaGroup;
	}

	public String getQuotaName() {
		return quotaName;
	}

	private Quota(QuotaGroup quotaGroup, String quotaName) {
		this.quotaGroup = quotaGroup;
		this.quotaName = quotaName;
	}
}

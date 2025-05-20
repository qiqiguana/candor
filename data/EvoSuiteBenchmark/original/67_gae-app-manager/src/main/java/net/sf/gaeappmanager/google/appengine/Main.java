package net.sf.gaeappmanager.google.appengine;

/**
 * Simple utility retreving quotas details of application from Google App
 * Engine.
 * 
 * @author Alois Belaska
 */
public class Main {

	private static void usage() {
		System.err
				.println("Usage: java -jar gae-app-manager.jar [full gmail address] [account password] [appspot application name]");
		System.err.println("\nEx.:");
		System.err
				.println("\tjava -jar gae-app-manager.jar alois.belaska@gmail.com heslo eshopsengine");
	}

	private static void printQuota(QuotaValue qValue) {
		StringBuilder builder = new StringBuilder();

		builder.append(qValue.getQuota().name());
		builder.append("[");
		builder.append(qValue.getValue());
		builder.append("/");
		builder.append(qValue.getLimit());
		builder.append(" (");
		builder.append(qValue.getPercent());
		builder.append("%)");

		if (!qValue.getUnit().isEmpty()) {
			builder.append(" ");
			builder.append(qValue.getUnit());
		}

		builder.append(",");

		if (qValue.isOkay()) {
			builder.append("OKAY");
		} else {
			builder.append("NOT OKAY");
		}

		builder.append("]");

		System.out.println(builder);
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			usage();
			return;
		}

		QuotaDetails details = Manager.retrieveAppQuotaDetails(args[0],
				args[1], args[2] + "-watchdog", args[2]);

		System.out.println("Quotas are reset every "
				+ details.getResetIntervalHours() + " hours. Next reset: "
				+ details.getNextResetWithinHours() + " hours");

		for (QuotaValue qValue : details.getValues()) {
			printQuota(qValue);
		}
	}
}

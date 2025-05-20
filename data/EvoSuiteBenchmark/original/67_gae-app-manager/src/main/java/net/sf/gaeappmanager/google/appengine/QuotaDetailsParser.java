package net.sf.gaeappmanager.google.appengine;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.w3c.tidy.Tidy;

/**
 * Parser of quota details HTML page.
 * 
 * @author Alois Belaska
 */
public class QuotaDetailsParser {

	/**
	 * @param el
	 *            processed element
	 * @param tag
	 *            tag name (td, ...)
	 * @param index
	 *            index of requested tag, begins with 0
	 */
	private Element getNthElementByTag(Element el, String tag, int index) {
		int idx = 0;
		for (Object obj : el.elements()) {
			Element e = (Element) obj;
			if (e.getName().equals(tag) && idx++ == index) {
				return e;
			}
		}
		return null;
	}

	private Element getElementByTagAndValue(Element el, String tag, String value) {
		if (el.getName().equals(tag) && el.getStringValue().equals(value)) {
			return el;
		}

		for (Object obj : el.elements()) {
			Element result = getElementByTagAndValue((Element) obj, tag, value);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	private Element getElementById(Element el, String id) {
		if (el.attributeCount() > 0) {
			Attribute attr = el.attribute("id");
			if (attr != null && attr.getValue().equals(id)) {
				return el;
			}
		}

		for (Object obj : el.elements()) {
			Element result = getElementById((Element) obj, id);
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	private QuotaValue parseValue(Element parentEl, Quota quota) {
		QuotaValue qVal = new QuotaValue();
		qVal.setQuota(quota);

		Element valEl = getElementByTagAndValue(parentEl, "td", quota
				.getQuotaName());
		if (valEl == null) {
			return null;
		}

		Element el = getNthElementByTag(valEl.getParent(), "td", 2);
		if (el == null) {
			return null;
		}

		qVal
				.setPercent(new BigDecimal(el.getStringValue().replaceAll("%",
						"")));

		el = getNthElementByTag(valEl.getParent(), "td", 3);
		if (el == null) {
			return null;
		}

		String[] parts = el.getStringValue().split(" ");
		if (parts.length < 3) {
			return null;
		}

		qVal.setValue(new BigDecimal(parts[0]));
		qVal.setLimit(new BigDecimal(parts[2]));

		StringBuilder unitBuilder = new StringBuilder();
		for (int i = 3; i < parts.length; i++) {
			if (unitBuilder.length() > 0) {
				unitBuilder.append(" ");
			}
			unitBuilder.append(parts[i]);
		}
		qVal.setUnit(unitBuilder.toString());

		el = getNthElementByTag(valEl.getParent(), "td", 4);
		if (el == null) {
			return null;
		}

		qVal.setOkay(el.getStringValue().equals("Okay"));

		return qVal;
	}

	private void parseRefreshNote(Element parentEl, QuotaDetails result) {
		Element el = getElementById(parentEl, "ae-quota-refresh-note");
		if (el == null) {
			return;
		}

		Pattern pattern = Pattern
				.compile("Quotas are reset every ([0-9]*) hours. Next reset: ([0-9]*) hours");

		Matcher matcher = pattern.matcher(el.getStringValue());
		if (matcher.matches()) {
			result.setResetIntervalHours(Integer.valueOf(matcher.group(1)));
			result.setNextResetWithinHours(Integer.valueOf(matcher.group(2)));
		}
	}

	public QuotaDetails parse(InputStream inputStream) {
		QuotaDetails result = new QuotaDetails();

		Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		tidy.setXmlOut(false);
		org.w3c.dom.Document xhtml = tidy.parseDOM(inputStream, null);

		DOMReader xmlReader = new DOMReader();
		Document document = xmlReader.read(xhtml);

		Element quotaDetailsElement = getElementById(document.getRootElement(),
				"ae-quota-details");

		parseRefreshNote(quotaDetailsElement, result);

		for (Quota quota : Quota.values()) {
			QuotaValue qValue = parseValue(quotaDetailsElement, quota);
			if (qValue != null) {
				result.getValues().add(qValue);
			}
		}

		return result;
	}
}

package ipac;

import java.text.*;
import java.util.*;
import java.math.*;

public class BinaryCalculate {

    /**
     *  Calculates the IP Range from the provided IP address and number of IP
     *  addresses. The results are stored into an <code>string</code> to be
     *  returned to the method that called it.
     *
     * @param IP The starting IP address (subnet) in binary
     * @param IPPrefix Number of IP addresses in binary
     * @return Results string
     */
    public String IPCalculate(String IP, String IPPrefix) {
        String outputText = "";
        checkIPType(IP.length() - 1);
        String subnet = convert(IP);
        String netmask = getNetmask(IPPrefix);
        calculate(IP, IPPrefix);
        outputText += "Total Range: " + IPRangeAddress + "\n";
        outputText += "Usable Range: " + usableRange + "\n";
        NumberFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###,###,###,###,###,###");
        outputText += "\n";
        outputText += "Total usable IP Addresses : " + formatter.format(totalIPAddresses) + "\n";
        outputText += "Subnet: " + subnet + "\n";
        outputText += "Binary Subnet: " + getBinaryIP(IP) + "\n";
        outputText += "Broadcast Address: " + currentIP + "\n";
        outputText += "Prefix: " + prefix + "\n";
        checkIPType(netmask.length() - 1);
        String outputNetmask = convert(netmask);
        outputText += "Netmask: " + outputNetmask + "\n";
        String binaryNetmask = getBinaryIP(netmask);
        outputText += "Binary Netmask: " + binaryNetmask;
        return outputText;
    }
}

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

    /**
     *  Determines whether an IP Address provided in binary form is an IPv4 or
     *  an IPv6 by the length of the binary string.
     *
     *  @param       size1    Length of binary string
     */
    private void checkIPType(int size1);

    /**
     *  Reconstructs the dotted-decimal or hexidecimal IP address with
     *  seperators from an given binary IP Address. The reconstructed IP address
     *  is stored as a global declared variable, <code>currentIP</code>.
     *
     *  @param   IP  An binary IP Address to be converted back into
     *               dotted-decimal or hexidecimal format
     */
    private String convert(String binaryIP);

    /**
     *  Gets the netmask from a binary representation of number of IP addresses
     *
     *  @param      binaryIP     binary representation of number of IP addresses
     *  @return     netmask of <code>binaryIP</code>
     */
    private String getNetmask(String binaryIP);

    /**
     *  Calculates the IP Range from the provided IP address and number of IP
     *  addresses. The results are stored as declared global variables for many
     *  other methods to manipulate or use.
     *  <br>This method also detects if the provided of number of IP addresses
     *  to be calculate exceeds past these IP addresses: 255.255.255.255 and
     *  FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF, as further IP addresses exceeds
     *  the IP addressing bounds.
     *  <br>This method also calculates the prefix size needed to accomodate all
     *  the IP Addresses to be listed and stores it as a global variable for use
     *  in other methods.
     *
     *  @param       subnet              The starting IP address (subnet)
     *  @param       noOfIPAddresses     Number of IP addresses
     */
    private void calculate(String subnet, String noOfIPAddresses);

    /**
     *  Adds delimiters back into a given IP address
     *
     *  @param      binaryIP     binary IP address with no delimiters
     *  @return     binary IP address with delimiters
     */
    private String getBinaryIP(String binaryIP);
}

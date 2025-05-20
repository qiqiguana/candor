package ipac;

import java.text.*;
import java.util.*;
import java.math.*;

public class BinaryCalculate {

    boolean isIPv4 = false;

    boolean isIPv6 = false;

    String currentIP = "";

    String fullReassembler = "";

    String DELIMITER = "";

    int lengthOfToken = 0;

    String IPRangeAddress = "";

    String usableRange = "";

    int semiCol = 0;

    String prefix = "";

    BigInteger totalIPAddresses;

    int MAXPREFIX = 0;

    /**
     *  Calculates the sum of two binary numbers.
     *
     *  @param       num1    The starting binary number
     *  @param       num2    The binary number to be added to the starting binary
     *  @return      The sum of two binary numbers.
     */
    public String addBinary(String num1, String num2);

    /**
     *  Subtracts two binary numbers.
     *
     *  @param       num1    The starting binary number
     *  @param       num2    The binary number to be subtracted from the
     *                       starting binary
     *  @return      The difference of two binary numbers.
     */
    public String subBinary(String num1, String num2);

    /**
     *  Calculates the IP Range from the provided IP address and number of IP
     *  addresses. The results are stored into an <code>string</code> to be
     *  returned to the method that called it.
     *
     *  @param      IP          The starting IP address (subnet) in binary
     *  @param      IPPrefix    Number of IP addresses in binary
     *  @return     Results string
     */
    public String IPCalculate(String IP, String IPPrefix);

    /**
     *  Adds delimiters back into a given IP address
     *
     *  @param      binaryIP     binary IP address with no delimiters
     *  @return     binary IP address with delimiters
     */
    private String getBinaryIP(String binaryIP);

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
     *  Calculates the IP Range from the provided IP address and number of IP
     *  addresses from the smaller prefix. The results are stored into an array
     *  to be printed using <code><b>Output.java</b></code>.
     *
     *  @param       IP          The starting IP address (subnet)
     *  @param       IPPrefix    Number of IP addresses for smaller prefix
     *  @param       noPrefix    Number of smaller prefixes within the total
     *                           prefix
     *  @param       endPrefix   Prefix of total number of IP Addresses
     */
    public void prefixInPrefixCalculate(String IP, String IPPrefix, int noPrefix, String endPrefix);

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
     *  Converts an given IPv4 or IPv6 address into binary format.
     *
     *  @param   IP  An IP Address to be converted back into binary format
     *  @return  Binary representation of given IP Address.
     */
    private String convertToBinary(String IP);

    /**
     *  Gets the starting IP of an IP range.
     *
     *  @return     Starting IP of IP range
     */
    private String getStartIP();
}

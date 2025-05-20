package ipac;
import java.text.*;
import java.util.*;
import java.math.*;

/**
 *  Title: Binary Calculator
 *  <br>Description: Background binary calculator for the IPAC program
 *  <br>Copyright (c) 2006-7 Jason Wang
 *
 *  <p>This program is free software; you can redistribute it and/or
 *  <br>modify it under the terms of the GNU General Public License
 *  <br>as published by the Free Software Foundation; either version 2
 *  <br>of the License, or (at your option) any later version.
 *
 *  <br>This program is distributed in the hope that it will be useful,
 *  <br>but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  <br>MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  <br>GNU General Public License for more details.
 *
 *  <br>You should have received a copy of the GNU General Public License
 *  <br>along with this program; if not, write to the Free Software
 *  <br>Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *                                                              02110-1301, USA.
 *
 *  @author      Jason Wang
 *  @version     v0.19
 */

public class BinaryCalculate{
    
    boolean isIPv4 = false;
    boolean isIPv6= false;
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
    public String addBinary(String num1, String num2) {
        boolean carry = false;
        int size1 = num1.length() - 1;
        int size2 = num2.length() - 1;
        String sum = "";
        
        if (size2 > size1) {
            String s = num1;
            num1 = num2;
            num2 = s;
            int n = size1;
            size1 = size2;
            size2 = n;
        }
        
        for (; size2 >= 0 ; size1--, size2--) {
            if (num1.charAt(size1) == '0' && num2.charAt(size2) == '0') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = false;
            } else if (num1.charAt(size1) == '1' && num2.charAt(size2) == '0') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            } else if (num1.charAt(size1) == '0' && num2.charAt(size2) == '1') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            } else if (num1.charAt(size1) == '1' && num2.charAt(size2) == '1') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = true;
            }
        }
        
        for (; size1 >= 0 ; size1--) {
            if (num1.charAt(size1) == '0') {
                if (!carry)
                    sum = "0" + sum;
                else
                    sum = "1" + sum;
                carry = false;
            } else if (num1.charAt(size1) == '1') {
                if (!carry)
                    sum = "1" + sum;
                else {
                    sum = "0" + sum;
                    carry = true;
                }
            }
        }
        
        if (carry)
            sum = "1" + sum;
        
        return sum;
    }
    
    /**
     *  Subtracts two binary numbers.
     *
     *  @param       num1    The starting binary number
     *  @param       num2    The binary number to be subtracted from the
     *                       starting binary
     *  @return      The difference of two binary numbers.
     */
    public String subBinary(String num1, String num2) {
        
        String diff = "";
        String twosComp = "";
        int size = num1.length() - 1;
        
        for (;  size>= 0 ; size--) {
            if (num1.charAt(size) == '0')
                twosComp = "1" + twosComp;
            else
                twosComp = "0" + twosComp;
        }
        
        twosComp = "0" + twosComp;
        twosComp = addBinary(twosComp, "1");
        
        diff = addBinary(twosComp, num2);
        
        return diff.substring(1);
    }
    
    /**
     *  Calculates the IP Range from the provided IP address and number of IP
     *  addresses. The results are stored into an <code>string</code> to be
     *  returned to the method that called it.
     *
     *  @param      IP          The starting IP address (subnet) in binary
     *  @param      IPPrefix    Number of IP addresses in binary
     *  @return     Results string
     */
    public String IPCalculate(String IP, String IPPrefix){
        String outputText = "";
        checkIPType(IP.length() - 1);
        String subnet = convert(IP);
        
        String netmask = getNetmask(IPPrefix);
        
        calculate(IP, IPPrefix);
        outputText += "Total Range: " + IPRangeAddress + "\n";
        outputText += "Usable Range: " + usableRange + "\n";
        NumberFormat formatter = new DecimalFormat(
                "###,###,###,###,###,###,###,###,###,###,###,###,###");
        outputText += "\n";
        outputText += "Total usable IP Addresses : " + formatter.format(
                totalIPAddresses) + "\n";
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
     *  Adds delimiters back into a given IP address
     *
     *  @param      binaryIP     binary IP address with no delimiters
     *  @return     binary IP address with delimiters
     */
    private String getBinaryIP(String binaryIP){
        String output = "";
        checkIPType(binaryIP.length() - 1);
        for (int i = 0; i < MAXPREFIX; i++){
            output += binaryIP.charAt(i);
            if (i == semiCol){
                if (i != (binaryIP.length() - 1))
                    output += DELIMITER;
                semiCol += lengthOfToken;
            }
        }
        return output;
    }
    
    /**
     *  Gets the netmask from a binary representation of number of IP addresses
     *
     *  @param      binaryIP     binary representation of number of IP addresses
     *  @return     netmask of <code>binaryIP</code>
     */
    private String getNetmask(String binaryIP){
        String invertedIPPrefix = "";
        for (int i = 0; i < MAXPREFIX; i++){
            if (binaryIP.charAt(i) == '0')
                invertedIPPrefix += "1";
            else
                invertedIPPrefix += "0";
        }
        return invertedIPPrefix;
    }
    
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
    private void calculate(String subnet, String noOfIPAddresses) {
        currentIP = "";
        String number1 = subnet;
        String usableIP = "";
        
        String IP = addBinary(subnet, noOfIPAddresses);
        checkIPType(subnet.length() - 1);
        String addSubBinary = "";
        for (int i = 0; i < (MAXPREFIX - 1); i++){
            addSubBinary += "0";
        }
        addSubBinary += "1";
        subnet = addBinary(addSubBinary, subnet);
        usableIP = convert (subnet);
        subnet = subBinary(addSubBinary, subnet);
        checkIPType(subnet.length() - 1);
        currentIP = convert(subnet);
        fullReassembler = "";
        
        semiCol = lengthOfToken - 1;
        IPRangeAddress = currentIP + " -- ";
        usableRange = usableIP + " -- ";
        currentIP = "";
        
        String currentIPString = "";
        
        if (IP.length() > 128){
            IPRangeAddress = IPRangeAddress +
                    "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF";
            currentIP = "FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF";
        }else if(IP.length() > 32 && IP.length() < 128){
            IPRangeAddress = IPRangeAddress + "255.255.255.255";
            currentIP = "255.255.255.255";
        }else{
            String IPRangeEnd = subBinary(addSubBinary, IP);
            String endIP = convert(IPRangeEnd);
            usableRange = usableRange + endIP;
            checkIPType(subnet.length() - 1);
            currentIP = convert(IP);
            IPRangeAddress = IPRangeAddress + currentIP;
        }
        
        currentIPString = subBinary(number1, convertToBinary(currentIP));
        totalIPAddresses = new BigInteger(currentIPString, 2);
        totalIPAddresses = totalIPAddresses.add(new BigInteger("1"));
        int prefixInt = 0;
        if (totalIPAddresses.doubleValue() < 2)
            prefixInt = MAXPREFIX;
        else if (totalIPAddresses.doubleValue() == 2)
            prefixInt = MAXPREFIX - 2;
        else {
            if (isIPv6){
                String currentIPString1 = currentIPString.substring(0, 64);
                BigInteger totalIPAddresses1 = new BigInteger(currentIPString1,
                        2);
                double prefixInt1 = 0;
                
                if (totalIPAddresses1.doubleValue() > 0)
                    prefixInt1 = Math.log(totalIPAddresses1.doubleValue())/
                            Math.log(2.0);
                
                String currentIPString2 = currentIPString.substring(64,128);
                BigInteger totalIPAddresses2 = new BigInteger(currentIPString2,
                        2);
                double prefixInt2 = Math.log(totalIPAddresses2.doubleValue())/
                        Math.log(2.0);
                
                prefixInt = MAXPREFIX - (int)Math.ceil(prefixInt1) -
                        (int)Math.round(prefixInt2);
            } else if (isIPv4)
                prefixInt = MAXPREFIX - (int)Math.ceil(Math.log(
                        totalIPAddresses.doubleValue())/Math.log(2.0));
        }
        prefix = "/" + prefixInt;
        totalIPAddresses = totalIPAddresses.subtract(new BigInteger("2"));
    }
    
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
    public void prefixInPrefixCalculate(String IP, String IPPrefix,
            int noPrefix, String endPrefix){
        try {
            String [] [] output = new String [noPrefix + 8] [2];
            
            checkIPType(IP.length() - 1);
            String subnet = convert(IP);
            
            String netmask = getNetmask(IPPrefix);
            calculate(IP, IPPrefix);
            
            for (int i = 0; i < noPrefix; i++){
                calculate(IP, IPPrefix);
                output [i] [0] = getStartIP() + " " + prefix;
                output [i] [1] = IPRangeAddress;
                IP = addBinary(fullReassembler.substring(MAXPREFIX, fullReassembler.length()), Integer.toBinaryString(1));
                fullReassembler = "";
            }
            output [noPrefix + 1] [0] = "Total usable IP Addresses :";
            NumberFormat formatter = new DecimalFormat(
                "###,###,###,###,###,###,###,###,###,###,###,###,###");
            output [noPrefix + 1] [1] = formatter.format(totalIPAddresses.multiply(new BigInteger(
                    noPrefix + ""))) + " ";
            output [noPrefix + 2] [0] = "Subnet:";
            output [noPrefix + 2] [1] = subnet;
            
            StringTokenizer subnetTokens = new StringTokenizer(convertToBinary(subnet), DELIMITER);
            
            String paddedSubnet = "";
            while (subnetTokens.hasMoreTokens()){
                String unpaddedSubnet = subnetTokens.nextToken();
                while (unpaddedSubnet.length() < lengthOfToken)
                    unpaddedSubnet = "0" + unpaddedSubnet;
                paddedSubnet += unpaddedSubnet;
            }
            
            output [noPrefix + 3] [0] = "Binary Subnet:";
            output [noPrefix + 3] [1] = getBinaryIP(paddedSubnet);
            output [noPrefix + 4] [0] = "Broadcast Address:";
            output [noPrefix + 4] [1] = currentIP;
            output [noPrefix + 5] [0] = "Prefix:";
            output [noPrefix + 5] [1] = endPrefix;
            checkIPType(netmask.length() - 1);
            output [noPrefix + 6] [0] = "Netmask";
            output [noPrefix + 6] [1] = convert(netmask);
            output [noPrefix + 7] [0] = "Binary Netmask:";
            output [noPrefix + 7] [1] = getBinaryIP(netmask);
            new Output(output);
        } catch (OutOfMemoryError e) {
            new IPv4().displayError("Your computer does not have sufficent " +
                    "memory to process this query.");
        }
    }
    
    /**
     *  Determines whether an IP Address provided in binary form is an IPv4 or
     *  an IPv6 by the length of the binary string.
     *
     *  @param       size1    Length of binary string
     */
    private void checkIPType(int size1){
        if (size1 == 31) {
            isIPv4 = true;
            lengthOfToken = 8;
            DELIMITER = ".";
            MAXPREFIX = 32;
        } else if (size1 == 127) {
            isIPv6 = true;
            lengthOfToken = 16;
            DELIMITER = ":";
            MAXPREFIX = 128;
        }
        semiCol = lengthOfToken - 1;
    }
    
    /**
     *  Reconstructs the dotted-decimal or hexidecimal IP address with
     *  seperators from an given binary IP Address. The reconstructed IP address
     *  is stored as a global declared variable, <code>currentIP</code>.
     *
     *  @param   IP  An binary IP Address to be converted back into
     *               dotted-decimal or hexidecimal format
     */
    private String convert(String binaryIP){
        String outputIP = "";
        String reassembler = "";
        for (int i = 0; i < binaryIP.length(); i++){
            reassembler += binaryIP.charAt(i);
            fullReassembler += binaryIP.charAt(i);
            if (i == semiCol){
                if (isIPv4){
                    outputIP += Integer.parseInt(reassembler, 2);
                    reassembler = "";
                } else if (isIPv6) {
                    outputIP += Integer.toHexString(Integer.parseInt(
                            reassembler, 2));
                    reassembler = "";
                }
                
                if (i != (binaryIP.length() - 1))
                    outputIP += DELIMITER;
                semiCol = semiCol + lengthOfToken;
            }
        }
        return outputIP;
    }
    
    /**
     *  Converts an given IPv4 or IPv6 address into binary format.
     *
     *  @param   IP  An IP Address to be converted back into binary format
     *  @return  Binary representation of given IP Address.
     */
    private String convertToBinary(String IP){
        StringTokenizer octetToken = new StringTokenizer(IP);
        String output = "";
        int radix = 0;
        if (isIPv4)
            radix = 10;
        else if (isIPv6)
            radix = 16;
        while (octetToken.hasMoreTokens()){
            String out = Integer.toBinaryString(Integer.parseInt(
                    octetToken.nextToken(".:"), radix));
            while (out.length() < lengthOfToken)
                out = "0" + out;
            output += out;
        }
        return output;
    }
    
    /**
     *  Gets the starting IP of an IP range.
     *
     *  @return     Starting IP of IP range
     */
    private String getStartIP(){
        StringTokenizer tokens = new StringTokenizer(IPRangeAddress, " -");
        return tokens.nextToken();
    }
}
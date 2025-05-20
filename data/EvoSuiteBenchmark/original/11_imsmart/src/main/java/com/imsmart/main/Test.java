/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imsmart.main;

/**
 *
 * @author gowerdh
 */
public class Test {

    public static void main(String args[]) {
        String name = "Meta_Name|DATE";
        String dataType = name.substring(name.indexOf("|") + 1, name.length());
        String format = "";
        if (dataType.indexOf("|") != -1) {
            format = dataType.substring(dataType.indexOf("|") + 1, dataType.length());
            dataType = dataType.substring(0, dataType.indexOf("|"));
        }
        String metadataName = name.substring(0, name.indexOf("|"));

        System.out.println("metadata name: " + metadataName);
        System.out.println("Data type: " + dataType);
        System.out.println("Format: " + format);
    }
}

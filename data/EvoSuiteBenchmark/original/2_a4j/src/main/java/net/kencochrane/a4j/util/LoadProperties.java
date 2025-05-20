/*
Copyright (c) 2003, Ken Cochrane
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted
provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
    this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

    * Neither the name of Ken Cochrane nor the names of its contributors may be used to endorse
    or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package net.kencochrane.a4j.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * a4j
 * http://www.KenCochrane.net/a4j/
 * Ken Cochrane Cochrane
 * Date: Jul 31, 2003
 * Time: 5:43:41 PM
 *
 *
 */
public class LoadProperties {
    Properties props = new Properties();
    boolean propsLoaded;

    static private LoadProperties _instance = null;

    static public LoadProperties instance() {
        if (null == _instance) {
            _instance = new LoadProperties();
        }
        return _instance;
    }

    protected LoadProperties() {
        InputStream instr = this.getClass().getClassLoader().getResourceAsStream("a4j-config.txt");
       // System.out.println("Load Properties");
        try {
           if(instr != null){
            props = new Properties();
            props.load(instr);
           }else{
             System.out.println("There is no Properties File Setting to default");
             setDefaultProperties(props);
           }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            System.out.println("Log file isn't there Setting default properties");
            setDefaultProperties(props);
            }
    }

    public Properties getProperties() {
        return props;
    }

    private void setDefaultProperties(Properties props){
        props.setProperty("associateID", "popcornmonste2-20");
        props.setProperty("cacheLife", "86400000");
        props.setProperty("URLSeperator", "-");
        props.setProperty("cacheDir", "/");
        props.setProperty("amazonServerURL", "http://xml.amazon.net/onca/xml3");
    }
}

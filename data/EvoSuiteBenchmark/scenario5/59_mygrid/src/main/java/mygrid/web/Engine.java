package mygrid.web;

public class Engine extends mygrid.web.MarshalByRefObject implements java.io.Serializable {

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }
}

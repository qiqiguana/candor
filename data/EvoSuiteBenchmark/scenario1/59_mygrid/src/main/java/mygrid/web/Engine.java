package mygrid.web;

public class Engine extends mygrid.web.MarshalByRefObject implements java.io.Serializable {

    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }
}

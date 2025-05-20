/**
 * GridJobStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package mygrid.web;

public class GridJobStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected GridJobStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Available = "Available";
    public static final java.lang.String _Acquired = "Acquired";
    public static final java.lang.String _Complete = "Complete";
    public static final java.lang.String _Failed = "Failed";
    public static final java.lang.String _Deleted = "Deleted";
    public static final java.lang.String _Progress = "Progress";
    public static final GridJobStatus Available = new GridJobStatus(_Available);
    public static final GridJobStatus Acquired = new GridJobStatus(_Acquired);
    public static final GridJobStatus Complete = new GridJobStatus(_Complete);
    public static final GridJobStatus Failed = new GridJobStatus(_Failed);
    public static final GridJobStatus Deleted = new GridJobStatus(_Deleted);
    public static final GridJobStatus Progress = new GridJobStatus(_Progress);
    public java.lang.String getValue() { return _value_;}
    public static GridJobStatus fromValue(java.lang.String value)
          throws java.lang.IllegalStateException {
        GridJobStatus enume = (GridJobStatus)
            _table_.get(value);
        if (enume==null) throw new java.lang.IllegalStateException();
        return enume;
    }
    public static GridJobStatus fromString(java.lang.String value)
          throws java.lang.IllegalStateException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
}

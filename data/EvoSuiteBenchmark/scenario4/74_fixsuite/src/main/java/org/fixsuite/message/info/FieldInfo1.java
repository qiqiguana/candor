package org.fixsuite.message.info;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a Field as defined by the FIX specification.
 *
 * @author jramoyo
 */
public class FieldInfo implements FixInfo {

    private int tagNumber;

    private String name;

    private String dataType;

    private String description;

    private String abbreviation;

    private String overrideXmlName;

    private String baseCategory;

    private String baseCategoryXmlName;

    private String unionDataType;

    private String usesEnumFromTag;

    private String comments;

    private int length;

    private boolean isNotRequiredXml;

    private String deprecatingVersion;

    private List<ComponentInfo> requiringComponents;

    private TreeMap<String, ValueInfo> validValues;

    /**
     * Returns the tagNumber
     *
     * @return the tagNumber
     */
    public int getTagNumber();

    /**
     * Modifies the tagNumber
     *
     * @param tagNumber - the tagNumber to set
     */
    public void setTagNumber(int tagNumber);

    /**
     * Returns the fieldName
     *
     * @return the fieldName
     */
    public String getName();

    /**
     * Modifies the fieldName
     *
     * @param name - the fieldName to set
     */
    public void setName(String name);

    /**
     * Returns the dataType
     *
     * @return the dataType
     */
    public String getDataType();

    /**
     * Modifies the dataType
     *
     * @param dataType - the dataType to set
     */
    public void setDataType(String dataType);

    /**
     * Returns the description
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Modifies the description
     *
     * @param description - the description to set
     */
    public void setDescription(String description);

    /**
     * Returns the comments
     *
     * @return the comments
     */
    public String getComments();

    /**
     * Modifies the comments
     *
     * @param comments - the comments to set
     */
    public void setComments(String comments);

    /**
     * Returns the abbreviation
     *
     * @return the abbreviation
     */
    public String getAbbreviation();

    /**
     * Modifies the abbreviation
     *
     * @param abbreviation - the abbreviation to set
     */
    public void setAbbreviation(String abbreviation);

    /**
     * Returns the overrideXmlName
     *
     * @return the overrideXmlName
     */
    public String getOverrideXmlName();

    /**
     * Modifies the overrideXmlName
     *
     * @param overrideXmlName - the overrideXmlName to set
     */
    public void setOverrideXmlName(String overrideXmlName);

    /**
     * Returns the baseCategory
     *
     * @return the baseCategory
     */
    public String getBaseCategory();

    /**
     * Modifies the baseCategory
     *
     * @param baseCategory - the baseCategory to set
     */
    public void setBaseCategory(String baseCategory);

    /**
     * Returns the baseCategoryXmlName
     *
     * @return the baseCategoryXmlName
     */
    public String getBaseCategoryXmlName();

    /**
     * Modifies the baseCategoryXmlName
     *
     * @param baseCategoryXmlName - the baseCategoryXmlName to set
     */
    public void setBaseCategoryXmlName(String baseCategoryXmlName);

    /**
     * Returns the unionDataType
     *
     * @return the unionDataType
     */
    public String getUnionDataType();

    /**
     * Modifies the unionDataType
     *
     * @param unionDataType - the unionDataType to set
     */
    public void setUnionDataType(String unionDataType);

    /**
     * Returns the usesEnumFromTag
     *
     * @return the usesEnumFromTag
     */
    public String getUsesEnumFromTag();

    /**
     * Modifies the usesEnumFromTag
     *
     * @param usesEnumFromTag - the usesEnumFromTag to set
     */
    public void setUsesEnumFromTag(String usesEnumFromTag);

    /**
     * Returns the length
     *
     * @return the length
     */
    public int getLength();

    /**
     * Modifies the length
     *
     * @param length - the length to set
     */
    public void setLength(int length);

    /**
     * Returns the isNotRequiredXml
     *
     * @return the isNotRequiredXml
     */
    public boolean isNotRequiredXml();

    /**
     * Modifies the isNotRequiredXml
     *
     * @param isNotRequiredXml - the isNotRequiredXml to set
     */
    public void setNotRequiredXml(boolean isNotRequiredXml);

    /**
     * Returns the deprecatingVersion
     *
     * @return the deprecatingVersion
     */
    public String getDeprecatingVersion();

    /**
     * Modifies the deprecatingVersion
     *
     * @param deprecatingVersion - the deprecatingVersion to set
     */
    public void setDeprecatingVersion(String deprecatingVersion);

    /**
     * Returns the requiringComponents
     *
     * @return the requiringComponents
     */
    public List<ComponentInfo> getRequiringComponents();

    /**
     * Add a requiringComponent
     *
     * @param component - a requiringComponent
     */
    public void addRequiringComponent(ComponentInfo component);

    /**
     * Modifies the requiringComponents
     *
     * @param requiringComponents - the requiringComponent to set
     */
    protected void setRequiringComponents(List<ComponentInfo> requiringComponents);

    /**
     * Returns whether this field is required in the specified component
     *
     * @param component - a component
     * @return whether this field is required in the specified component
     */
    public boolean isRequiredInComponent(ComponentInfo component);

    /**
     * Returns the validValues
     *
     * @return the validValues
     */
    public List<ValueInfo> getValidValues();

    /**
     * Adds a value
     *
     * @param value - a value
     */
    public void addValidValue(ValueInfo value);

    /**
     * Returns whether a given value is valid
     *
     * @param value - a value
     * @return whether a given value is valid
     */
    public boolean isValidValue(String value);

    /**
     * Modifies the validValues
     *
     * @param validValues - the validValues to set
     */
    protected void setValidValues(TreeMap<String, ValueInfo> validValues);

    /**
     * Returns the validValues Map
     *
     * @return the validValues Map
     */
    protected TreeMap<String, ValueInfo> getValidValuesMap();
}

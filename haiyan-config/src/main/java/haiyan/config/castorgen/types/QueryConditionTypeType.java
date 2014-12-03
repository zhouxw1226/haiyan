/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.2</a>, using an XML
 * Schema.
 * $Id$
 */

package haiyan.config.castorgen.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Class QueryConditionTypeType.
 * 
 * @version $Revision$ $Date$
 */
public class QueryConditionTypeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The none type
     */
    public static final int NONE_TYPE = 0;

    /**
     * The instance of the none type
     */
    public static final QueryConditionTypeType NONE = new QueryConditionTypeType(NONE_TYPE, "none");

    /**
     * The displayOnly type
     */
    public static final int DISPLAYONLY_TYPE = 1;

    /**
     * The instance of the displayOnly type
     */
    public static final QueryConditionTypeType DISPLAYONLY = new QueryConditionTypeType(DISPLAYONLY_TYPE, "displayOnly");

    /**
     * The equal type
     */
    public static final int EQUAL_TYPE = 2;

    /**
     * The instance of the equal type
     */
    public static final QueryConditionTypeType EQUAL = new QueryConditionTypeType(EQUAL_TYPE, "equal");

    /**
     * The blurMatching type
     */
    public static final int BLURMATCHING_TYPE = 3;

    /**
     * The instance of the blurMatching type
     */
    public static final QueryConditionTypeType BLURMATCHING = new QueryConditionTypeType(BLURMATCHING_TYPE, "blurMatching");

    /**
     * The region type
     */
    public static final int REGION_TYPE = 4;

    /**
     * The instance of the region type
     */
    public static final QueryConditionTypeType REGION = new QueryConditionTypeType(REGION_TYPE, "region");

    /**
     * The in type
     */
    public static final int IN_TYPE = 5;

    /**
     * The instance of the in type
     */
    public static final QueryConditionTypeType IN = new QueryConditionTypeType(IN_TYPE, "in");

    /**
     * Field _memberTable.
     */
    private static java.util.Hashtable _memberTable = init();

    /**
     * Field type.
     */
    private final int type;

    /**
     * Field stringValue.
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private QueryConditionTypeType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of QueryConditionTypeType
     * 
     * @return an Enumeration over all possible instances of
     * QueryConditionTypeType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * QueryConditionTypeType
     * 
     * @return the type of this QueryConditionTypeType
     */
    public int getType(
    ) {
        return this.type;
    }

    /**
     * Method init.
     * 
     * @return the initialized Hashtable for the member table
     */
    private static java.util.Hashtable init(
    ) {
        Hashtable members = new Hashtable();
        members.put("none", NONE);
        members.put("displayOnly", DISPLAYONLY);
        members.put("equal", EQUAL);
        members.put("blurMatching", BLURMATCHING);
        members.put("region", REGION);
        members.put("in", IN);
        return members;
    }

    /**
     * Method readResolve. will be called during deserialization to
     * replace the deserialized object with the correct constant
     * instance.
     * 
     * @return this deserialized object
     */
    private java.lang.Object readResolve(
    ) {
        return valueOf(this.stringValue);
    }

    /**
     * Method toString.Returns the String representation of this
     * QueryConditionTypeType
     * 
     * @return the String representation of this
     * QueryConditionTypeType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new QueryConditionTypeType based on
     * the given String value.
     * 
     * @param string
     * @return the QueryConditionTypeType value of parameter 'string
     */
    public static haiyan.config.castorgen.types.QueryConditionTypeType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid QueryConditionTypeType";
            throw new IllegalArgumentException(err);
        }
        return (QueryConditionTypeType) obj;
    }

}

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
 * Class CustomeizeLayoutForType.
 * 
 * @version $Revision$ $Date$
 */
public class CustomeizeLayoutForType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The edit type
     */
    public static final int EDIT_TYPE = 0;

    /**
     * The instance of the edit type
     */
    public static final CustomeizeLayoutForType EDIT = new CustomeizeLayoutForType(EDIT_TYPE, "edit");

    /**
     * The queryCondition type
     */
    public static final int QUERYCONDITION_TYPE = 1;

    /**
     * The instance of the queryCondition type
     */
    public static final CustomeizeLayoutForType QUERYCONDITION = new CustomeizeLayoutForType(QUERYCONDITION_TYPE, "queryCondition");

    /**
     * The query type
     */
    public static final int QUERY_TYPE = 2;

    /**
     * The instance of the query type
     */
    public static final CustomeizeLayoutForType QUERY = new CustomeizeLayoutForType(QUERY_TYPE, "query");

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

    private CustomeizeLayoutForType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of CustomeizeLayoutForType
     * 
     * @return an Enumeration over all possible instances of
     * CustomeizeLayoutForType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * CustomeizeLayoutForType
     * 
     * @return the type of this CustomeizeLayoutForType
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
        members.put("edit", EDIT);
        members.put("queryCondition", QUERYCONDITION);
        members.put("query", QUERY);
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
     * CustomeizeLayoutForType
     * 
     * @return the String representation of this
     * CustomeizeLayoutForType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new CustomeizeLayoutForType based
     * on the given String value.
     * 
     * @param string
     * @return the CustomeizeLayoutForType value of parameter
     * 'string'
     */
    public static haiyan.config.castorgen.types.CustomeizeLayoutForType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid CustomeizeLayoutForType";
            throw new IllegalArgumentException(err);
        }
        return (CustomeizeLayoutForType) obj;
    }

}

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
 * Class PluginOperationHostPageType.
 * 
 * @version $Revision$ $Date$
 */
public class PluginOperationHostPageType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The query type
     */
    public static final int QUERY_TYPE = 0;

    /**
     * The instance of the query type
     */
    public static final PluginOperationHostPageType QUERY = new PluginOperationHostPageType(QUERY_TYPE, "query");

    /**
     * The edit type
     */
    public static final int EDIT_TYPE = 1;

    /**
     * The instance of the edit type
     */
    public static final PluginOperationHostPageType EDIT = new PluginOperationHostPageType(EDIT_TYPE, "edit");

    /**
     * The ids type
     */
    public static final int IDS_TYPE = 2;

    /**
     * The instance of the ids type
     */
    public static final PluginOperationHostPageType IDS = new PluginOperationHostPageType(IDS_TYPE, "ids");

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

    private PluginOperationHostPageType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of PluginOperationHostPageType
     * 
     * @return an Enumeration over all possible instances of
     * PluginOperationHostPageType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * PluginOperationHostPageType
     * 
     * @return the type of this PluginOperationHostPageType
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
        members.put("query", QUERY);
        members.put("edit", EDIT);
        members.put("ids", IDS);
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
     * PluginOperationHostPageType
     * 
     * @return the String representation of this
     * PluginOperationHostPageType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new PluginOperationHostPageType
     * based on the given String value.
     * 
     * @param string
     * @return the PluginOperationHostPageType value of parameter
     * 'string'
     */
    public static haiyan.config.castorgen.types.PluginOperationHostPageType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid PluginOperationHostPageType";
            throw new IllegalArgumentException(err);
        }
        return (PluginOperationHostPageType) obj;
    }

}

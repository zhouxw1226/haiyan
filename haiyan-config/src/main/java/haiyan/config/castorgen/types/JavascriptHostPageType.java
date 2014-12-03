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
 * Class JavascriptHostPageType.
 * 
 * @version $Revision$ $Date$
 */
public class JavascriptHostPageType implements java.io.Serializable {


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
    public static final JavascriptHostPageType QUERY = new JavascriptHostPageType(QUERY_TYPE, "query");

    /**
     * The edit type
     */
    public static final int EDIT_TYPE = 1;

    /**
     * The instance of the edit type
     */
    public static final JavascriptHostPageType EDIT = new JavascriptHostPageType(EDIT_TYPE, "edit");

    /**
     * The ids type
     */
    public static final int IDS_TYPE = 2;

    /**
     * The instance of the ids type
     */
    public static final JavascriptHostPageType IDS = new JavascriptHostPageType(IDS_TYPE, "ids");

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

    private JavascriptHostPageType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of JavascriptHostPageType
     * 
     * @return an Enumeration over all possible instances of
     * JavascriptHostPageType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * JavascriptHostPageType
     * 
     * @return the type of this JavascriptHostPageType
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
     * JavascriptHostPageType
     * 
     * @return the String representation of this
     * JavascriptHostPageType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new JavascriptHostPageType based on
     * the given String value.
     * 
     * @param string
     * @return the JavascriptHostPageType value of parameter 'string
     */
    public static haiyan.config.castorgen.types.JavascriptHostPageType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid JavascriptHostPageType";
            throw new IllegalArgumentException(err);
        }
        return (JavascriptHostPageType) obj;
    }

}

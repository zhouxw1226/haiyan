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
 * Class PageViewTypeType.
 * 
 * @version $Revision$ $Date$
 */
public class PageViewTypeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The querybyform type
     */
    public static final int QUERYBYFORM_TYPE = 0;

    /**
     * The instance of the querybyform type
     */
    public static final PageViewTypeType QUERYBYFORM = new PageViewTypeType(QUERYBYFORM_TYPE, "querybyform");

    /**
     * The gentree type
     */
    public static final int GENTREE_TYPE = 1;

    /**
     * The instance of the gentree type
     */
    public static final PageViewTypeType GENTREE = new PageViewTypeType(GENTREE_TYPE, "gentree");

    /**
     * The xloadtree type
     */
    public static final int XLOADTREE_TYPE = 2;

    /**
     * The instance of the xloadtree type
     */
    public static final PageViewTypeType XLOADTREE = new PageViewTypeType(XLOADTREE_TYPE, "xloadtree");

    /**
     * The complextree type
     */
    public static final int COMPLEXTREE_TYPE = 3;

    /**
     * The instance of the complextree type
     */
    public static final PageViewTypeType COMPLEXTREE = new PageViewTypeType(COMPLEXTREE_TYPE, "complextree");

    /**
     * The userdefined type
     */
    public static final int USERDEFINED_TYPE = 4;

    /**
     * The instance of the userdefined type
     */
    public static final PageViewTypeType USERDEFINED = new PageViewTypeType(USERDEFINED_TYPE, "userdefined");

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

    private PageViewTypeType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of PageViewTypeType
     * 
     * @return an Enumeration over all possible instances of
     * PageViewTypeType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this PageViewTypeType
     * 
     * @return the type of this PageViewTypeType
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
        members.put("querybyform", QUERYBYFORM);
        members.put("gentree", GENTREE);
        members.put("xloadtree", XLOADTREE);
        members.put("complextree", COMPLEXTREE);
        members.put("userdefined", USERDEFINED);
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
     * PageViewTypeType
     * 
     * @return the String representation of this PageViewTypeType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new PageViewTypeType based on the
     * given String value.
     * 
     * @param string
     * @return the PageViewTypeType value of parameter 'string'
     */
    public static haiyan.config.castorgen.types.PageViewTypeType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid PageViewTypeType";
            throw new IllegalArgumentException(err);
        }
        return (PageViewTypeType) obj;
    }

}

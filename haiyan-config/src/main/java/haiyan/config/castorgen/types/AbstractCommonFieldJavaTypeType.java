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
 * Class AbstractCommonFieldJavaTypeType.
 * 
 * @version $Revision$ $Date$
 */
public class AbstractCommonFieldJavaTypeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The password type
     */
    public static final int PASSWORD_TYPE = 0;

    /**
     * The instance of the password type
     */
    public static final AbstractCommonFieldJavaTypeType PASSWORD = new AbstractCommonFieldJavaTypeType(PASSWORD_TYPE, "password");

    /**
     * The string type
     */
    public static final int STRING_TYPE = 1;

    /**
     * The instance of the string type
     */
    public static final AbstractCommonFieldJavaTypeType STRING = new AbstractCommonFieldJavaTypeType(STRING_TYPE, "string");

    /**
     * The bigDecimal type
     */
    public static final int BIGDECIMAL_TYPE = 2;

    /**
     * The instance of the bigDecimal type
     */
    public static final AbstractCommonFieldJavaTypeType BIGDECIMAL = new AbstractCommonFieldJavaTypeType(BIGDECIMAL_TYPE, "bigDecimal");

    /**
     * The date type
     */
    public static final int DATE_TYPE = 3;

    /**
     * The instance of the date type
     */
    public static final AbstractCommonFieldJavaTypeType DATE = new AbstractCommonFieldJavaTypeType(DATE_TYPE, "date");

    /**
     * The blob type
     */
    public static final int BLOB_TYPE = 4;

    /**
     * The instance of the blob type
     */
    public static final AbstractCommonFieldJavaTypeType BLOB = new AbstractCommonFieldJavaTypeType(BLOB_TYPE, "blob");

    /**
     * The dbBlob type
     */
    public static final int DBBLOB_TYPE = 5;

    /**
     * The instance of the dbBlob type
     */
    public static final AbstractCommonFieldJavaTypeType DBBLOB = new AbstractCommonFieldJavaTypeType(DBBLOB_TYPE, "dbBlob");

    /**
     * The dbClob type
     */
    public static final int DBCLOB_TYPE = 6;

    /**
     * The instance of the dbClob type
     */
    public static final AbstractCommonFieldJavaTypeType DBCLOB = new AbstractCommonFieldJavaTypeType(DBCLOB_TYPE, "dbClob");
    
    /**
     * The dbClob type
     */
    public static final int INTEGER_TYPE = 7;

    /**
     * The instance of the dbClob type
     */
    public static final AbstractCommonFieldJavaTypeType INTEGER = new AbstractCommonFieldJavaTypeType(DBCLOB_TYPE, "integer");

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

    private AbstractCommonFieldJavaTypeType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of AbstractCommonFieldJavaTypeType
     * 
     * @return an Enumeration over all possible instances of
     * AbstractCommonFieldJavaTypeType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * AbstractCommonFieldJavaTypeType
     * 
     * @return the type of this AbstractCommonFieldJavaTypeType
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
        members.put("password", PASSWORD);
        members.put("string", STRING);
        members.put("bigDecimal", BIGDECIMAL);
        members.put("date", DATE);
        members.put("blob", BLOB);
        members.put("dbBlob", DBBLOB);
        members.put("dbClob", DBCLOB);
        members.put("integer", INTEGER);
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
     * AbstractCommonFieldJavaTypeType
     * 
     * @return the String representation of this
     * AbstractCommonFieldJavaTypeType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new AbstractCommonFieldJavaTypeType
     * based on the given String value.
     * 
     * @param string
     * @return the AbstractCommonFieldJavaTypeType value of
     * parameter 'string'
     */
    public static haiyan.config.castorgen.types.AbstractCommonFieldJavaTypeType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid AbstractCommonFieldJavaTypeType";
            throw new IllegalArgumentException(err);
        }
        return (AbstractCommonFieldJavaTypeType) obj;
    }

}

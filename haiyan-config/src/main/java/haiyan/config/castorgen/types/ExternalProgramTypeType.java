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
 * Class ExternalProgramTypeType.
 * 
 * @version $Revision$ $Date$
 */
public class ExternalProgramTypeType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The before type
     */
    public static final int BEFORE_TYPE = 0;

    /**
     * The instance of the before type
     */
    public static final ExternalProgramTypeType BEFORE = new ExternalProgramTypeType(BEFORE_TYPE, "before");

    /**
     * The after type
     */
    public static final int AFTER_TYPE = 1;

    /**
     * The instance of the after type
     */
    public static final ExternalProgramTypeType AFTER = new ExternalProgramTypeType(AFTER_TYPE, "after");

    /**
     * The beforeAll type
     */
    public static final int BEFOREALL_TYPE = 2;

    /**
     * The instance of the beforeAll type
     */
    public static final ExternalProgramTypeType BEFOREALL = new ExternalProgramTypeType(BEFOREALL_TYPE, "beforeAll");

    /**
     * The afterAll type
     */
    public static final int AFTERALL_TYPE = 3;

    /**
     * The instance of the afterAll type
     */
    public static final ExternalProgramTypeType AFTERALL = new ExternalProgramTypeType(AFTERALL_TYPE, "afterAll");

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

    private ExternalProgramTypeType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of ExternalProgramTypeType
     * 
     * @return an Enumeration over all possible instances of
     * ExternalProgramTypeType
     */
    public static java.util.Enumeration enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this
     * ExternalProgramTypeType
     * 
     * @return the type of this ExternalProgramTypeType
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
        members.put("before", BEFORE);
        members.put("after", AFTER);
        members.put("beforeAll", BEFOREALL);
        members.put("afterAll", AFTERALL);
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
     * ExternalProgramTypeType
     * 
     * @return the String representation of this
     * ExternalProgramTypeType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new ExternalProgramTypeType based
     * on the given String value.
     * 
     * @param string
     * @return the ExternalProgramTypeType value of parameter
     * 'string'
     */
    public static haiyan.config.castorgen.types.ExternalProgramTypeType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid ExternalProgramTypeType";
            throw new IllegalArgumentException(err);
        }
        return (ExternalProgramTypeType) obj;
    }

}

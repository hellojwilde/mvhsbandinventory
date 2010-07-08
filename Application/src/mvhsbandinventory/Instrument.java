package mvhsbandinventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Defines the data related to an instrument stored in the flat-file database.
 *
 * @author nicholson
 * @author jonathan
 */
public class Instrument
{
    public static final Instrument NULL_INSTRUMENT = new Instrument();
    private Map<String,String> properties = new HashMap<String,String>();
    private List<String> history = new ArrayList<String>();

    public static final String[] attributes =
    {
        "Name", "Brand", "Serial", "Rank", "Value", "Status", "Notes",
        "Ligature", "Mouthpiece", "MouthpieceCap", "Bow", "NeckStrap",
        "Renter", "SchoolYear", "DateOut", "Fee", "Period", "Other",
        "Contract"
    };


    public static final List<String> attributeList = Arrays.asList(attributes);
    public static final int attributesLength = attributes.length;

    /**
     * Constructor for an instrument without any properties.
     */
    public Instrument ()
    {
        for(String s: attributes)
        {
            try
            {
                set(s, "");
            } catch(Exception ex) {}
        }
    }

    /**
     * An accessor that sets the property named by the attribute argument to
     * the value passed in via the value argument.  The attribute must be
     * contained in the Instruments.attributes array.  If it is not, an
     * exception will be thrown.
     *
     * @param attribute
     * @param value
     * @throws Exception
     */
    public void set (String attribute, String value) throws Exception
    {
        // Prevent people from storing arbitrary values in the instrument
        if (!attributeList.contains(attribute))
        {
            throw new Exception("Illegal attribute name.");
        }
        
        properties.put(attribute, value);
    }

    /**
     * An accessor that retrieves the value of the property named by the
     * attribute argument.
     *
     * @param attribute
     * @return value
     */
    public String get (String attribute)
    {
        return properties.get(attribute);
    }

    public List<String> getHistory ()
    {
        return history;
    }

    public void setHistory (List<String> history)
    {
        this.history = history;
    }

    public void addHistory (String line)
    {
        history.add(line);
    }
}

package mvhsbandinventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * @author jonathan
 */
public class InstrumentList extends AbstractTableModel
{

    private List<Instrument> list;
    private InstrumentStore store;
    private String[] columnNames =
    {
        "Type", "Brand", "Serial #"
    };
    public static String[] singles =
    {
        "Name", "Brand", "Serial", "Rank", "Value", "Status", "Notes",
        "Ligature", "Mouthpiece", "MouthpieceCap", "Bow", "NeckStrap",
        "Renter", "SchoolYear", "DateOut", "Fee", "Period", "Other",
        "Contract"
    };

    /**
     * The constructor for the instrument list class.  The model argument is the
     * subclass of InstrumentStore that will be used for long-term storage of
     * the instruments.
     * @param model
     */
    public InstrumentList(InstrumentStore model)
    {
        try {
            // Store our pointer to the store for later use
            store = model;

            // Load all of the items from the datastore and put them into our
            // private ArrayList
            list = new ArrayList<Instrument>(Arrays.asList(store.load()));
        } catch (Exception e) {
            // The file path was defined incorrectly; find a way to let the user
            // fix it
        }
    }

    /**
     * Adds the Instrument object to the in-memory cache of the datastore and to
     * the data store that was specified via the constructor.
     * @param instrument - the Instrument to add
     */
    public void add(Instrument instrument)
    {
        // Add the item to our local memory cache and to our data store
        list.add(instrument);
        store.add(instrument);
    }

    /**
     * Commits the changes that were made to the instrument object to the long-
     * term datastore.
     * @param instrument - the Instrument to update
     */
    public void update(Instrument instrument)
    {
        // Commit the changes to the instrument to the disk
        store.update(instrument);
    }

    /**
     * Deletes the specified Instrument object from the in-memory cache list of
     * instruments and from the data store that was specified via the
     * constructor.
     * @param instrument - the Instrument object to remove
     */
    public void delete(Instrument instrument)
    {
        try
        {
            // Delete the item from our local memory cache and to our data store
            list.remove(instrument);
            store.delete(instrument);
        } catch(Exception ex)
        {
            Logger.getLogger(InstrumentList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sorts the InstrumentList object memory cache of the instruments based on
     * the values at the specified key.  The ascending parameter will determine
     * whether the list will be sorted in ascending or descending order.
     * @param key - the key to sort by
     * @param ascending - if set to true, the list will be sorted in ascending
     * order; if set to false, the list will be sorted in descending order
     */
    public void sort(String key, boolean ascending)
    {
        try
        {
            Comparator comp = new InstrumentAttributeComparator(key, ascending);
            Collections.sort(list, comp);
        } catch(Exception ex)
        {
            Logger.getLogger(InstrumentList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns an array of all of the Instrument objects that have the specified
     * value (set by the value argument) set for the the key argument specified.
     * @param key
     * @param value
     * @return instrument array subset
     */
    public Instrument[] selectList(String key, Object value)
    {
        // This is a cache of the length of the list of all of the Instrument
        // objects that we're dealing with so that we can loop through them; we
        // determine this here so that we don't have to recalculate this for
        // every iteration of the loop
        int length = list.size();

        // These are an array of the items that we're selecting and a variable 
        // to mark the index of the next item to be inserted into the array
        Instrument[] selection =
        {
        };
        int next = 0;

        // Iterate through all of the items in our memcache of the data store
        for(int i = 0; i < length; i++)
        {
            // Grab the item from the memcache for this index
            Instrument current = (Instrument) list.get(i);

            // If this item's value for the specified key matches the value 
            // argument, add it to our selection array
            if(current.get(key) == value)
            {
                selection[next] = current;
                next++;
            }
        }

        return selection;
    }

    /**
     * Creates a CSV string that can be opened by Excel to get an overview of  
     * the Instrument objects in the application.
     * @param instruments - an array of the instrument objects to be exported
     * @param fields - an array of strings of the field names to be exported
     * @return a CSV string that can be written to file
     */
    public String exportToExcel(Instrument[] instruments, String[] fields)
    {
        return "";  // Placeholder to keep the build from breaking
    }

    /**
     * A convience overload of the exportToExcel function that exports the
     * fields of the instrument object specified in the
     * InstrumentList.singles static array.
     * @param instruments - an array of the instrument objects to be exported
     * @return a CSV string that can be written to file
     */
    public String exportToExcel(Instrument[] instruments)
    {
        return exportToExcel(instruments, singles);
    }

    /**
     * Returns the number of items inside the list. Useful for building
     * iterators to create tables.
     * @return an integer representing the number of Instruments stored in this
     * instance of the InstrumentList object
     */
    public int size()
    {
        return list.size();
    }

    /**
     * Retrieves the Instrument stored in the InstrumentList at the specified
     * index.
     * @param index - the index to retrieve an Instrument from the
     * InstrumentList at
     * @return the Instrument object at the specified index
     */
    public Instrument get(int index)
    {
        return list.get(index);
    }

    /**
     * Retrieves the Instrument stored in the InstrumentList that has the 
     * specified name, brand and serial codes.  All of these arguments have the
     * type string.
     * @param name
     * @param brand
     * @param serial
     * @return the Instrument object that satisfies all of the requirements that
     * were passed in via arguments
     */
    public Instrument get(String name, String brand, String serial)
    {
        int length = list.size();
        for(int i = 0; i < length; i++)
        {
            Instrument instrument = (Instrument) list.get(i);

            String testName = (String) instrument.get("Name");
            String testBrand = (String) instrument.get("Brand");
            String testSerial = (String) instrument.get("Serial");

            if(testName.equals(name) &&
                    testBrand.equals(brand) &&
                    testSerial.equals(serial))
            {
                return instrument;
            }
        }

        return null;
    }

    public boolean isEmpty()
    {
        return list.size() == 0;
    }

    //Below this point are methods for the TabelModel handling
    public int getColumnCount()
    {
        return columnNames.length;
    }

    public int getRowCount()
    {
        return list.size();
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        Instrument instru = list.get(row);
        switch(col)
        {
            case 0:
                return instru.get("Name");
            case 1:
                return instru.get("Brand");
            case 2:
                return instru.get("Serial");
            default:
                return "";
        }
    }

    @Override
    public Class getColumnClass(int c)
    {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        Instrument instru = list.get(row);
        try
        {
            switch(col)
            {
                case 0:
                {
                    instru.set("Name", (String) value);
                    break;
                }
                case 1:
                {
                    instru.set("Brand", (String) value);
                    break;
                }
                case 2:
                {
                    instru.set("Serial", (String) value);
                    break;
                }
            }
            update(instru);
        } catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        fireTableCellUpdated(row, col);
    }
}

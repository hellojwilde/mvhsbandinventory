package mvhsbandinventory;

/**
 *
 * @author jonathan
 */
public abstract class InstrumentStore
{
    /**
     * Adds a new instrument to the store; serializes all of the data and
     * writes the data to the disk.
     * @param instrument
     */
    public abstract void add (Instrument instrument);

    /**
     * Updates an existing instrument in the store; serializes all of the data
     * in the instrument parameter and writes the data to the disk.
     * @param instrument
     */
    public abstract void update (Instrument instrument);

    /**
     * Deletes the instrument from the store permenantly.
     * @param instrument
     */
    public abstract void delete (Instrument instrument);

    /**
     * Loads all of the instruments from the store.
     * @return an array of all of the parsed instruments in the store
     */
    public abstract Instrument[] load ();
}

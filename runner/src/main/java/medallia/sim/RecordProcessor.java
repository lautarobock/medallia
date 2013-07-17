package medallia.sim;

/** Basic interface for classes that need to process records */
public interface RecordProcessor {
	/**
	 * Process a single record.
	 * @param layoutIdx Layout index in 'layouts' this record represents.
	 */
	void processRecord(int layoutIdx);
}

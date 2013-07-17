package medallia.sim;

import medallia.sim.data.Field;

import java.util.BitSet;
import java.util.List;

/**
 * Base class for simulators. The meat of the logic is in
 * {@link SimulatorBase}.
 */
public abstract class RecordLayoutSimulator extends SimulatorBase {
	/**
	 * Initialize simulator with given layout and fields.
	 */
	public RecordLayoutSimulator(BitSet[] layouts, List<Field> fields) {
		super(layouts, fields);
	}

	/**
	 * @return Completed layout of segments
	 */
	public abstract List<int[]> getSegments();

	/**
	 * Flush layout. This is used to signify a publishing point during
	 * loading (all currently {@link #processRecord(int)} records must be
	 * visible in a call to {@link #getSegments()}) and at the end.
	 */
	public void flush() {
		// Optional
	}
}

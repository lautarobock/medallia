package medallia.sim;

import medallia.sim.data.Field;

import java.util.BitSet;
import java.util.List;

/**
 * Base class for field layout simulators. These can be used to pre-analyze the dataset,
 * but their primary purpose is to change column allocation for fields.
 * Field layout simulators. typically only see a subset of the actual data.
 */
public abstract class FieldLayoutSimulator extends SimulatorBase {
	/**
	 * Initialize the simulator with given layout and fields.
	 */
	public FieldLayoutSimulator(BitSet[] layouts, List<Field> fields) {
		super(layouts, fields);
	}

	/**
	 * This can be overridden to change the column of field definitions. Be
	 * aware that the order of fields should never change, only the column
	 * they are allocated in.
	 */
	public List<Field> getFields() {
		return fields;
	}
}

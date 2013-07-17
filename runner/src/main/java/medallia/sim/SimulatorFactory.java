package medallia.sim;

import medallia.sim.data.Field;

import java.util.BitSet;
import java.util.List;

/**
 * Creation of field and record layout simulators.
 */
public interface SimulatorFactory {
	/**
	 * Create a {@link FieldLayoutSimulator}. If this returns non-null, the
	 * simulator will be given a subset of records to calculate optimal column
	 * allocation for the fields.
	 */
	FieldLayoutSimulator createFieldLayoutSimulator(BitSet[] layouts, List<Field> fields);

	/**
	 * Crate a simulator for the given layout and fields. If {@link #createFieldLayoutSimulator}
	 * returned non-null, the field layout built by the {@link medallia.sim.FieldLayoutSimulator}
	 * will be given here.
	 */
	RecordLayoutSimulator createRecordLayoutSimulator(BitSet[] layouts, List<Field> fields);

	/** @return the name of this simulator (used for reporting) */
	String getName();
}

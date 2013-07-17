package medallia.sim;

import medallia.sim.data.Field;

import java.util.BitSet;
import java.util.List;

/**
 * Base class for a simulator. All Simulators should extend
 * {@link RecordLayoutSimulator} or {@link FieldLayoutSimulator}.
 * <p>
 * This simulator uses the term layout id. A layout id is a placeholder for
 * a unique record layout, and the specific layout can be looked up in the
 * {@link #layouts} member. Layout #0 is special, and is used to represent
 * an empty row.
 * <p>
 * The flow of simulation is as follows:
 * <ul>
 * <li>Simulator is initialized with a given list of fields and layouts.
 * <li>{@link #processRecord(int)} is called once per record.
 * </ul>
 * <p>
 * As an example, assume we have two fields, A and B, and that we have three
 * records R1, R2 and R3.
 *
 * <pre>
 * R1: A="Hello", B=3
 * R2: A="World", B=4
 * R3: B=5
 * </pre>
 *
 * The simulator code doesn't have (or care) what the values actually are,
 * it only cares if it they are set, which indicates it has to reserve space
 * to store them. In this case, we would end up with 2 fields (A and B), and
 * 2 layouts. Layout 1 would be (A set, B set) and layout 2 would be (B
 * set).
 * <p>
 * The flow of execution is therefore likely to be:
 *
 * <pre>
 * Constructor()
 * processRecord(1);
 * processRecord(1);
 * processRecord(2);
 * flush();
 * getFields();
 * getSegments();
 * </pre>
 *
 */
public abstract class SimulatorBase implements RecordProcessor {
	/**
	 * The mapping between a layout id and which fields contain values.
	 */
	public final BitSet[] layouts;

	/**
	 * Field definitions on input
	 */
	public final List<Field> fields;

	/**
	 * Initialize constructor with given layout and fields.
	 */
	public SimulatorBase(BitSet[] layouts, List<Field> fields) {
		this.layouts = layouts;
		this.fields = fields;
	}

	/**
	 * Load a single record into the dataset
	 *
	 * @param layoutIdx Layout index in {@link #layouts} this record
	 *            represents.
	 */
	@Override
	public abstract void processRecord(int layoutIdx);

}

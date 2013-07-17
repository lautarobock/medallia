package medallia.sim.data;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.List;

/**
 * Layout for a single dataset
 */
public class DatasetLayout implements Serializable {
	static final long serialVersionUID = 1L;

	/** List of fields */
	public Field[] fields;

	/**
	 * Field with value for given layout id
	 */
	public BitSet[] layouts;

	/**
	 * List of layout id per segment. Each value represents a row.
	 */
	public List<int[]> segments;

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// Initialize the field's index
		for (int i = 0; i < fields.length; i++) {
			fields[i].index = i;
		}
	}
}

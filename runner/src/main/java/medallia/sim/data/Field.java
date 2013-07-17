package medallia.sim.data;

import java.io.Serializable;

/**
 * Information about an individual field.
 */
public class Field implements Serializable {
	static final long serialVersionUID = 1L;

	/** Field name */
	public final String name;

	/** Number of bits needed for storage */
	public final int size;

	/** Current column field is allocated to */
	public final int column;

	/** Index of this field in the layout */
	transient int index;

	/** Create new field with specified parameters */
	public Field(String name, int size, int column) {
		this.name = name;
		this.size = size;
		this.column = column;
	}

	/** Clone other field, but change column */
	public Field(Field other, int newColumn) {
		this(other.name, other.size, newColumn);
		this.index = other.index;
	}

	/** Return this field's index in the bit set */
	public int getIndex() {
		return index;
	}
}

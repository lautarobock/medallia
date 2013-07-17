package medallia.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import medallia.sim.data.Field;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/** Packs fields in columns in the order they're passed */
public class FieldPacker {
	private int column;
	private int bitsUsed;
	private final List<Field> fields = Lists.newArrayList();
	private final Set<Field> alreadyPacked = Sets.newHashSet();

	/** Packs the specified field, adding a column if needed. */
	public FieldPacker pack(Field field) {
		checkArgument(!packed(field), "Field '%s' already packed", field.name);
		if (!fitsInCurrentColumn(field)) {
			column++;
			bitsUsed = 0;
		}
		bitsUsed += field.size;
		fields.add(new Field(field, column));
		alreadyPacked.add(field);
		return this;
	}

	/** @return true if packing the field will not cause a new column to be allocated */
	public boolean fitsInCurrentColumn(Field field) {
		return field.size + bitsUsed <= 32;
	}

	/** @return true if the field has already been packed */
	public boolean packed(Field field) {
		return alreadyPacked.contains(field);
	}

	/** Allocates a new column if the current one has at least one bit occupied */
	public FieldPacker newColumn() {
		if (bitsUsed > 0) {
			++column;
			bitsUsed = 0;
		}
		return this;
	}

	/** @return a list of fields packed by this FieldPacker*/
	public List<Field> getFields() {
		return fields;
	}
}

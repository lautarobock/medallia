package medallia.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import medallia.sim.RecordProcessor;

import java.util.Arrays;
import java.util.List;

/**
 * A {@link medallia.sim.RecordProcessor} that multiplexes calls to {@link #processRecord(int)}
 * on different instances of {@link RecordPacker}, depending on the layoutIdx of the
 * record being processed.
 * <p/>
 * This class receives an array of {@link RecordPacker}, the array should have one element per valid layout index.
 * <p/>
 * The {@link #processRecord(int)} method will delegate the actual record processing to the RecordPacker
 * corresponding to the layoutIdx of the record being processed.
 * On each call to {@link #flush()} all the underlying {@link RecordPacker}s will be flushed once per instance.
 */
@SuppressWarnings("unused")
public class MultiplexingPacker implements RecordProcessor {
	private final RecordPacker[] packersByLayout;

	/**
	 * Creates a new multiplexing packer.
	 * @param packersByLayout array mapping layoutIdx to {@link RecordPacker}
	 */
	public MultiplexingPacker(RecordPacker[] packersByLayout) {
		this.packersByLayout = packersByLayout;
	}

	@Override
	public void processRecord(int layoutIdx) {
		packersByLayout[layoutIdx].processRecord(layoutIdx);
	}

	/** Flushes the all the underlying {@link RecordPacker}s once.*/
	public void flush() {
		for (RecordPacker packer : ImmutableSet.copyOf(Arrays.asList(packersByLayout))) {
			packer.flush();
		}
	}

	/** @return list of segments (flush should have been called by now) */
	public List<int[]> getSegments() {
		final List<int[]> result = Lists.newArrayList();
		for (RecordPacker packer : ImmutableSet.copyOf(Arrays.asList(packersByLayout))) {
			result.addAll(packer.getSegments());
		}
		return result;
	}
}

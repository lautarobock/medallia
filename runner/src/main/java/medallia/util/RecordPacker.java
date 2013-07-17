package medallia.util;

import medallia.sim.RecordProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Simple record packer that packs records snugly into segments until the segment is full */
public class RecordPacker implements RecordProcessor {
	public List<int[]> segments = new ArrayList<>();
	public int[] current;
	public int currentIdx;
	public final int segmentSize;

	/**
	 * Initialize a segment packer
	 * @param segmentSize number of rows per segment
	 */
	public RecordPacker(int segmentSize) {
		this.segmentSize = segmentSize;
	}

	@Override
	public void processRecord(int layoutIdx) {
		if (current == null || currentIdx >= current.length) {
			flush();
			current = new int[segmentSize];
		}
		current[currentIdx++ ] = layoutIdx;
	}

	/**
	 * Flush layout any remaining changes.
	 */
	public void flush() {
		if (current != null)
			segments.add(Arrays.copyOf(current, currentIdx));
		current = null;
		currentIdx = 0;
	}

	public List<int[]> getSegments() {
		return segments;
	}
}

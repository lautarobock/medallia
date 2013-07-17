package medallia.runner;

import medallia.sim.SimulatorFactory;
import medallia.sim.data.CompanyLayout;
import medallia.sim.data.DatasetLayout;
import medallia.sim.data.Layout;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import static medallia.util.SimulatorUtil.toSi;

/**
 * Simulator Framework for alternative slug record loading strategies.
 * <p>
 * The purpose of this is to experiment with alternative record/segment/column
 * layouts, in order to maximize the efficiency of shared null vectors.
 * <p/>
 * A simulator is composed of three classes:
 * <ul>
 *     <li>A {@link medallia.sim.FieldLayoutSimulator} (optional) that returns a new field-column mapping</li>
 *     <li>A {@link medallia.sim.RecordLayoutSimulator} that packs records into segments</li>
 *     <li>A {@link medallia.sim.SimulatorFactory} that creates field and record layout simulators for a run on each dataset</li>
 * </ul>
 */
public class SimulatorRunner {

	/**
	 * Fetch data from file and parse
	 */
	public static void run(SimulatorFactory fac) throws IOException, ClassNotFoundException {
		// Collect total bytes used
		long totalUsedBytes = 0;
		final String name = fac.getName();

			final Layout layout;

		try (InputStream in = SimulatorRunner.class.getResourceAsStream("default.layout"); InputStream gzip = new GZIPInputStream(in); ObjectInputStream ois = new ObjectInputStream(gzip)) {
			layout = (Layout) ois.readObject();
		}

		for (Entry<String, CompanyLayout> company : layout.companies.entrySet()) {
			for (Entry<String, DatasetLayout> dataset : company.getValue().datasets.entrySet()) {
				final DatasetLayout stats = dataset.getValue();

				// Ignore empty datasets
				if (stats.segments.isEmpty())
					continue;

				final Analysis analysis = Analysis.simulateCompany(fac, stats);

				System.out.printf("%s:%s:%s %s\n", company.getKey(), dataset.getKey(), name, analysis);

				// Update total used bytes
				totalUsedBytes += analysis.usedBytes;
			}
		}

		System.out.printf("** %s - total bytes used: %s%n", name, toSi(totalUsedBytes));
	}

	/** Size of a segment */
	public static final int SEGMENT_SIZE = 50000;
}

package medallia.sim.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Complete Layout structure
 */
public class Layout implements Serializable {
	static final long serialVersionUID = 1L;

	/**
	 * Mapping from company name to dataset mapping.
	 */
	public Map<String, CompanyLayout> companies = new HashMap<>();
}

package mining;

import java.util.Comparator;

public class ComparatorGrowRate implements Comparator<EmergingPattern> {

	@Override

	// confronto tra due emerging pattern rispetto al grow rate.
	public int compare(EmergingPattern o1, EmergingPattern o2) {
		if (o1.getGrowRate() == o2.getGrowRate()) {
			return 0;
		} else if (o1.getGrowRate() < o2.getGrowRate()) {
			return -1;
		} else
			return 1;
		// return (o1.getGrowRate() == o2.getGrowRate() ? 0 : (o1.getGrowRate() <
		// o2.getGrowRate() ? -1 : 1));
	}

}

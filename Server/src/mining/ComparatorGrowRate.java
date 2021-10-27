package mining;

import java.util.Comparator;

/**
 * Classe comparatore che si occupa di ordinare gli emerging pattern in base al
 * loro grow rate.
 */
public class ComparatorGrowRate implements Comparator<EmergingPattern> {

	/**
	 * Esegue un confronto tra due emerging pattern sulla base del loro grow rate.
	 * 
	 * @param o1 primo emerging pattern da confrontare
	 * @param o2 secondo emerging pattern da confrontare
	 * 
	 * @return 0 se il growrate è uguale; -1 se il primo growrate è minore del
	 *         secondo; 1 altrimenti.
	 */
	public int compare(EmergingPattern o1, EmergingPattern o2) {
		if (o1.getGrowRate() == o2.getGrowRate()) {
			return 0;
		} else if (o1.getGrowRate() < o2.getGrowRate()) {
			return -1;
		} else
			return 1;
	}

}

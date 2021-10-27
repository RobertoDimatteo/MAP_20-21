package mining;

import data.Data;
import data.EmptySetException;
import java.util.*;
import java.io.*;

/**
 * Classe che modella la scoperta di emerging pattern a partire dalla lista di
 * frequent pattern.
 */
public class EmergingPatternMiner implements Iterable<EmergingPattern>, Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lista che contiene riferimenti a oggetti istanza della classe
	 * &lt;EmergingPattern&gt; che definiscono il pattern.
	 */
	private LinkedList<EmergingPattern> epList = new LinkedList<EmergingPattern>();

	// COSTRUTTORE

	/**
	 * Costruttore che scandisce tutti i frequent pattern in &lt;fpList&gt;, per
	 * ognuno di essi calcola il grow rate usando &lt;dataBackground&gt; e se tale
	 * valore è maggiore uguale di &lt;minG&gt; allora il pattern è aggiunto ad
	 * &lt;epList&gt;.
	 * 
	 * @param dataBackground dataset di background
	 * @param fpList         lista dei pattern frequenti già estratti
	 * @param minG           minimo grow rate
	 * 
	 * @throws EmptySetException lanciata quando l'insieme di training risulta vuoto
	 */
	public EmergingPatternMiner(Data dataBackground, FrequentPatternMiner fpList, float minG) throws EmptySetException {
		if (dataBackground.getNumberOfExamples() != 0) {
			for (FrequentPattern fp : fpList) {
				try {
					EmergingPattern ep = computeEmergingPattern(dataBackground, fp, minG);
					epList.add(ep);
				} catch (EmergingPatternException patternError) {
					System.err.println(patternError);
				}
			}
			sort();
		} else
			throw new EmptySetException();
	}

	// METODI

	/**
	 * Ottiene da &lt;fp&gt; il suo supporto relativo al dataset target, calcola il
	 * supporto di &lt;fp&gt; relativo al dataset di background e infine calcola il
	 * grow rate come rapporto dei due supporti.
	 * 
	 * @param dataBackground dataset di background
	 * @param fp             un frequent pattern
	 * 
	 * @return grow rate finale calcolato
	 */
	float computeGrowRate(Data dataBackground, FrequentPattern fp) {
		float targetSupp = fp.getSupport();
		float backgroundSupp = fp.computeSupport(dataBackground);
		float growrate = targetSupp / backgroundSupp;
		return growrate;
	}

	/**
	 * Verifica che il grow rate di &lt;fp&gt; sia maggiore uguale di &lt;minGr&gt;
	 * e in caso affermativo crea un nuovo emerging pattern.
	 * 
	 * @param dataBackground dataset di background
	 * @param fp             un frequent pattern
	 * @param minGR          minimo grow rate
	 * 
	 * @return emerging pattern creato dal frequent pattern
	 * 
	 * @throws EmergingPatternException lanciata quando il pattern non soddisfa le
	 *                                  condizioni del minimo grow rate
	 */
	EmergingPattern computeEmergingPattern(Data dataBackground, FrequentPattern fp, float minGR)
			throws EmergingPatternException {
		float growrate = computeGrowRate(dataBackground, fp);
		if (growrate >= minGR) {
			EmergingPattern ep = new EmergingPattern(fp, growrate);
			return ep;
		} else
			throw new EmergingPatternException();
	}

	/**
	 * Richiama il metodo &lt;sort()&gt; della classe &lt;List&gt; su &lt;epList&gt;
	 * passandogli come parametro un'istanziazione della classe
	 * &lt;ComparatorGrowRate&gt;.
	 */
	private void sort() {
		Collections.sort(epList, new ComparatorGrowRate());
	}

	/**
	 * Serializza l’oggetto riferito da &lt;this&gt; nel file il cui nome è passato
	 * come parametro.
	 * 
	 * @param nomeFile nome del file
	 * 
	 * @throws FileNotFoundException lanciata quando fallisce l'apertura di un file
	 * @throws IOException           lanciata per segnalare operazioni di I/O
	 *                               fallite o interrotte
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(nomeFile));
		outStream.writeObject(this);
		outStream.close();
	}

	/**
	 * Legge e restituisce l’oggetto così come è memorizzato nel file il cui nome è
	 * passato come parametro.
	 * 
	 * @param nomeFile nome del file
	 * 
	 * @return emerging pattern presente nel file
	 * 
	 * @throws FileNotFoundException  lanciata quando fallisce l'apertura di un file
	 * @throws IOException            lanciata per segnalare operazioni di I/O
	 *                                fallite o interrotte
	 * @throws ClassNotFoundException lanciata quando si cerca di caricare una
	 *                                classe tramite uno specifico nome ma non viene
	 *                                trovato alcun riferimento
	 */
	public static EmergingPatternMiner carica(String nomeFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(nomeFile));
		EmergingPatternMiner eps = (EmergingPatternMiner) inStream.readObject();
		inStream.close();
		return eps;
	}

	/**
	 * Scandisce &lt;epList&gt; al fine di concatenare in un'unica stringa le
	 * stringhe rappresentanti i pattern emergenti letti.
	 * 
	 * @return stringa concatenata di pattern
	 */
	public String toString() {
		String epListStr = "";
		int i = 0;
		for (EmergingPattern ep : epList) {
			epListStr += (i + 1) + ":" + ep + "\n";
			i++;
		}
		return epListStr;
	}

	/**
	 * Restituisce un oggetto iteratore della classe &lt;EmergingPattern&gt; usato
	 * per scandire l'oggetto &lt;epList&gt;.
	 * 
	 * @return un oggetto iteratore
	 */
	public Iterator<EmergingPattern> iterator() {
		return epList.iterator();
	}
	
}

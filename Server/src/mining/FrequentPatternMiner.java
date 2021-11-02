package mining;

import data.*;
import utility.Queue;
import utility.EmptyQueueException;

import java.io.*;
import java.util.*;

/**
 * Classe che include i metodi per la scoperta di pattern frequenti con
 * Algoritmo APRIORI.
 */
public class FrequentPatternMiner implements Iterable<FrequentPattern>, Serializable {

	// ATTRIBUTI

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Lista che contiene riferimenti a oggetti istanza della classe
	 * &lt;FrequentPattern&gt; che definiscono il pattern.
	 */
	private LinkedList<FrequentPattern> outputFP = new LinkedList<FrequentPattern>();

	// COSTRUTTORE

	/**
	 * Costruttore che genera tutti i pattern frequenti k=1 e per ognuno di questi
	 * genera quelli con k>1 richiamando &lt;expandFrequentPatterns()&gt;. I pattern
	 * sono poi memorizzati nel membro &lt;OutputFP&gt;.
	 * 
	 * @param data   insieme delle transazioni
	 * @param minSup minimo supporto
	 * 
	 * @throws EmptySetException lanciata quando l'insieme di training risulta vuoto
	 */
	public FrequentPatternMiner(Data data, float minSup) throws EmptySetException {
		Queue<FrequentPattern> fpQueue = new Queue<FrequentPattern>();

		if (data.getNumberOfExamples() != 0) {
			for (Attribute genericAttribute : data.getAttributeSet()) {
				if (genericAttribute instanceof DiscreteAttribute) {
					DiscreteAttribute currentAttribute = (DiscreteAttribute) genericAttribute;
					for (String value : currentAttribute.getValues()) {
						DiscreteItem item = new DiscreteItem(currentAttribute, value);
						FrequentPattern fp = new FrequentPattern();
						fp.addItem(item);
						fp.setSupport(fp.computeSupport(data));
						if (fp.getSupport() >= minSup) { // 1-FP CANDIDATE
							fpQueue.enqueue(fp);
							outputFP.addLast(fp);
						}
					}
				}
				if (genericAttribute instanceof ContinuousAttribute) {
					ContinuousAttribute currentAttribute = (ContinuousAttribute) genericAttribute;
					Iterator<Float> it = currentAttribute.iterator();
					if (it.hasNext()) {
						float estrInf = it.next();
						while (it.hasNext()) {
							float estrSup = it.next();
							ContinuousItem item = new ContinuousItem(currentAttribute, new Interval(estrInf, estrSup));
							FrequentPattern fp = new FrequentPattern();
							fp.addItem(item);
							fp.setSupport(fp.computeSupport(data));
							if (fp.getSupport() >= minSup) { // 1-FP CANDIDATE
								fpQueue.enqueue(fp);
								outputFP.addLast(fp);
							}
							estrInf = estrSup;
						}
					}
				}
			}
			outputFP = expandFrequentPatterns(data, minSup, fpQueue, outputFP);
			sort();
		} else
			throw new EmptySetException();
	}

	// METODI

	/**
	 * Crea un nuovo pattern a cui aggiunge tutti gli item di &lt;FP&gt; e il
	 * parametro &lt;item&gt;.
	 * 
	 * @param FP   pattern da raffinare
	 * @param item item generico da aggiungere al pattern
	 * 
	 * @return nuovo pattern ottenuto per effetto del raffinamento
	 */
	private FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item) {
		FrequentPattern newFp = new FrequentPattern(FP);
		newFp.addItem(item);
		return newFp;
	}

	/**
	 * Finché &lt;fpQueue&gt; contiene elementi, si estrae un elemento dalla coda
	 * &lt;fpQueue&gt; e si generano i raffinamenti per questo (aggiungendo un nuovo
	 * item non incluso). Per ogni raffinamento si verifica se è frequente e, in
	 * caso affermativo, lo si aggiunge sia ad &lt;fpQueue&gt; sia ad
	 * &lt;outputFP&gt;.
	 * 
	 * @param data     insieme delle transazioni
	 * @param minSup   minimo supporto
	 * @param fpQueue  coda contenente i pattern da valutare
	 * @param outputFP lista dei pattern frequenti già estratti
	 * 
	 * @return lista popolata con pattern frequenti a k>1
	 */
	private LinkedList<FrequentPattern> expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue,
			LinkedList<FrequentPattern> outputFP) {
		while (true) {
			try {
				FrequentPattern fp = (FrequentPattern) fpQueue.first(); // fp to be refined
				fpQueue.dequeue();
				for (Attribute genericAttribute : data.getAttributeSet()) {
					boolean found = false;
					for (Item ite : fp.getFP()) // the new item should involve an attribute
												// different
												// form attributes already involved into the items
												// of fp
						if (ite.getAttribute().equals(genericAttribute)) { // controlla che la categoria
																			// sia
																			// la stessa, se lo è si
																			// ferma (
																			// non può usare la stessa
																			// categoria)
							found = true;
							break;
						}
					if (!found) // data.getAttribute(i) is not involve into an item of fp
					{
						if (genericAttribute instanceof DiscreteAttribute) {
							DiscreteAttribute currentAttribute = (DiscreteAttribute) genericAttribute;
							for (String value : currentAttribute.getValues()) {
								DiscreteItem item = new DiscreteItem(currentAttribute, value);
								FrequentPattern newFP = refineFrequentPattern(fp, item); // generate refinement
								newFP.setSupport(newFP.computeSupport(data));
								if (newFP.getSupport() >= minSup) {
									fpQueue.enqueue(newFP);
									outputFP.addLast(newFP);
								}
							}
						}
						if (genericAttribute instanceof ContinuousAttribute) {
							ContinuousAttribute currentAttribute = (ContinuousAttribute) genericAttribute;
							Iterator<Float> it = currentAttribute.iterator();
							if (it.hasNext()) {
								float estrInf = it.next();
								while (it.hasNext()) {
									float estrSup = it.next();
									ContinuousItem item = new ContinuousItem(currentAttribute,
											new Interval(estrInf, estrSup));
									FrequentPattern newFP = refineFrequentPattern(fp, item); // generate refinement
									newFP.setSupport(newFP.computeSupport(data));
									if (newFP.getSupport() >= minSup) {
										fpQueue.enqueue(newFP);
										outputFP.addLast(newFP);
									}
									estrInf = estrSup;
								}
							}

						}

					}
				}

			} catch (EmptyQueueException codaVuota) {
				break;
			}
		}
		return outputFP;
	}

	/**
	 * Richiama il metodo &lt;sort()&gt; della classe &lt;List&gt; su
	 * &lt;outputFP&gt;.
	 */
	private void sort() {
		Collections.sort(outputFP);
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
	 * @return frequent pattern presente nel file
	 * 
	 * @throws FileNotFoundException  lanciata quando fallisce l'apertura di un file
	 * @throws IOException            lanciata per segnalare operazioni di I/O
	 *                                fallite o interrotte
	 * @throws ClassNotFoundException lanciata quando si cerca di caricare una
	 *                                classe tramite uno specifico nome ma non viene
	 *                                trovato alcun riferimento
	 */
	public static FrequentPatternMiner carica(String nomeFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(nomeFile));
		FrequentPatternMiner fps = (FrequentPatternMiner) inStream.readObject();
		inStream.close();
		return fps;
	}

	/**
	 * Scandisce &lt;outputFP&gt; al fine di concatenare in un'unica stringa i
	 * pattern frequenti letti.
	 * 
	 * @return stringa concatenata di pattern
	 */
	public String toString() {
		String outputStr = "";
		int i = 0;
		for (FrequentPattern fp : outputFP) {
			outputStr += (i + 1) + ":" + fp + "\n";
			i++;
		}
		return outputStr;
	}

	/**
	 * Restituisce un oggetto iteratore della classe &lt;FrequentPattern&gt; usato
	 * per scandire l'oggetto &lt;outputFP&gt;.
	 * 
	 * @return un oggetto iteratore
	 */
	public Iterator<FrequentPattern> iterator() {
		return outputFP.iterator();
	}

}

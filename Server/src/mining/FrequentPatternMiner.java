package mining;

import data.*;
import utility.Queue;
import utility.EmptyQueueException;

import java.io.*;
import java.util.*;

// classe FrequentPatternMiner che include i metodi per la scoperta di pattern frequenti con Algoritmo APRIORI.
@SuppressWarnings("serial")
public class FrequentPatternMiner implements Iterable<FrequentPattern>, Serializable {

	// ATTRIBUTI

	private LinkedList<FrequentPattern> outputFP = new LinkedList<FrequentPattern>(); // lista che contiene riferimenti
																						// a oggetti istanza della
																						// classe
	// FrequentPattern che definiscono il pattern.

	// COSTRUTTORE

	// Costruttore che genera tutti i pattern k = 1 frequenti e per ognuno di questi
	// genera quelli con k>1 richiamando expandFrequentPatterns ()
	// I pattern sono memorizzati nel membro OutputFP.
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
							// System.out.println(fp);
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
								// System.out.println(fp);
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
			throw new EmptySetException("L'insieme di training risulta vuoto");
	}

	// METODI

	// Crea un nuovo pattern a cui aggiunge tutti gli item di FP e il parametro
	// item.
	FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item) {
		FrequentPattern newFp = new FrequentPattern(FP);
		newFp.addItem(item);
		return newFp;
	}

	// Finché fpQueue contiene elementi, si estrae un elemento dalla coda fpQueue,
	// si generano i raffinamenti per questo (aggiungendo un nuovo item non
	// incluso). Per ogni raffinamento si verifica se è frequente e, in caso
	// affermativo, lo si aggiunge sia ad fpQueue sia ad outputFP.
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
									// System.out.println(newFP);
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
										// System.out.println(newFP);
										outputFP.addLast(newFP);
									}
									estrInf = estrSup;
								}
							}

						}

					}
				}

			} catch (EmptyQueueException codaVuota) {
				// System.err.println(codaVuota);
				break;
			}
		}
		return outputFP;
	}

	private void sort() {
		Collections.sort(outputFP);
	}

	// metodo che si occupa di serializzare l’oggetto riferito da this nel file il
	// cui nome è passato come parametro
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(nomeFile));
		outStream.writeObject(this);
		outStream.close();
	}

	// metodo che si occupa di leggere e restituire l’oggetto come è memorizzato nel
	// file il cui nome è passato come parametro
	public static FrequentPatternMiner carica(String nomeFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(nomeFile));
		FrequentPatternMiner fps = (FrequentPatternMiner) inStream.readObject();
		inStream.close();
		return fps;
	}

	// Scandisce OutputFp al fine di concatenare in un'unica stringa i pattern //DA
	// CONTROLLARE
	// frequenti letti.
	public String toString() {
		String outputStr = "";
		int i = 0;
		for (FrequentPattern fp : outputFP) {
			outputStr += (i + 1) + ":" + fp + "\n";
			i++;
		}
		return outputStr;
	}

	@Override
	public Iterator<FrequentPattern> iterator() {
		return outputFP.iterator();
	}

}

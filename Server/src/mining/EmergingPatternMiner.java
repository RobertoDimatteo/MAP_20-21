package mining;

import data.Data;
import data.EmptySetException;
import java.util.*;
import java.io.*;

// classe EmergingPatternMiner che modella la scoperta di emerging pattern partire dalla lista di frequent pattern.
@SuppressWarnings("serial")
public class EmergingPatternMiner implements Iterable<EmergingPattern>, Serializable {

	// ATTRIBUTI

	private LinkedList<EmergingPattern> epList = new LinkedList<EmergingPattern>(); // lista che contiene riferimenti a
																					// oggetti istanza della classe
	// EmergingPattern
	// che definiscono il pattern.

	// COSTRUTTORE

	// Si scandiscono tutti i frequent pattern in fpList , per ognuno di essi si
	// calcola il grow rate usando dataBackground e se tale valore è maggiore uguale
	// di minG allora il pattern è aggiunto ad epList (fare uso del metodo
	// computeEmergingPattern).
	public EmergingPatternMiner(Data dataBackground, FrequentPatternMiner fpList, float minG) throws EmptySetException {
		if (dataBackground.getNumberOfExamples() != 0) {
			for (FrequentPattern fp : fpList) {
				try {
					EmergingPattern ep = computeEmergingPattern(dataBackground, fp, minG);
					epList.add(ep);
				} catch (EmergingPatternException patternError) {
					// System.err.println(patternError);
				}
			}
			sort();
		} else
			throw new EmptySetException("L'insieme di training risulta vuoto");
	}

	// METODI

	// Si ottiene da fp il suo supporto relativo al dataset target. Si calcola il
	// supporto di fp relativo al dataset di background. Si calcola il grow rate
	// come rapporto dei due supporti
	float computeGrowRate(Data dataBackground, FrequentPattern fp) {
		float targetSupp = fp.getSupport();
		float backgroundSupp = fp.computeSupport(dataBackground);
		float growrate = targetSupp / backgroundSupp;
		return growrate;
	}

	// Verifica che il grow rate di fp sia maggiore uguale di minGR. In caso
	// affermativo
	// crea un oggetto EmergingPattern da fp.
	EmergingPattern computeEmergingPattern(Data dataBackground, FrequentPattern fp, float minGR)
			throws EmergingPatternException {
		float growrate = computeGrowRate(dataBackground, fp);
		if (growrate >= minGR) {
			EmergingPattern ep = new EmergingPattern(fp, growrate);
			return ep;
		} else
			throw new EmergingPatternException("Il pattern corrente non soddisfa le condizioni del minimo growrate");
	}

	private void sort() {
		Collections.sort(epList, new ComparatorGrowRate());
	}

	// metodo che si occupa di serializzare l’oggetto riferito da this nel file il
	// cui nome è passato come parametro
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(nomeFile));
		outStream.writeObject(this);
		outStream.close();
	}

	// metodo che si occupa di leggere e restituire l’oggetto come è memorizzato nel
	// file il cui nome è passato come parametro.
	public static EmergingPatternMiner carica(String nomeFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(nomeFile));
		EmergingPatternMiner eps = (EmergingPatternMiner) inStream.readObject();
		inStream.close();
		return eps;
	}

	// Scandisce epList al fine di concatenare in un'unica stringa le stringhe
	// rappresentati i pattern emergenti letti. //CONTROLLA SE FUNZIONA
	public String toString() {
		String epListStr = "";
		int i = 0;
		for (EmergingPattern ep : epList) {
			epListStr += (i + 1) + ":" + ep + "\n";
			i++;
		}
		return epListStr;
	}

	@Override
	public Iterator<EmergingPattern> iterator() {
		return epList.iterator();
	}
}

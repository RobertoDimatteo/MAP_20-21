package data;

import java.util.Iterator;
import java.io.*;

// Tale classe realizza l’iteratore che itera sugli elementi della sequenza 
// composta da numValues valori reali equidistanti tra di loro (cut points) 
// compresi tra min e max ottenuti per mezzo di discretizzazione. 
// La classe implementa i metodi della interfaccia generica Iterator<T> tipizzata con Float
@SuppressWarnings("serial")
public class ContinuousAttributeIterator implements Iterator<Float>, Serializable {

	// ATTRIBUTI

	private float min; // minimo
	private float max; // massimo
	private int j = 0; // posizione dell’iteratore nella collezione di cut point generati per [min,
						// max[ tramite discretizzazione

	private int numValues; // numero di intervalli di discretizzazione

	// COSTRUTTORE

	// avvalora i membri attributo della classe con i parametri del costruttore
	public ContinuousAttributeIterator(float min, float max, int numValues) {
		this.min = min;
		this.max = max;
		this.numValues = numValues;
	}

	// METODI

	@Override
	// restituisce true se j<=numValues, false altrimenti
	public boolean hasNext() {
		return (j <= numValues);
	}

	@Override
	// incrementa j, restituisce il cut point in posizione j-1 (min +
	// (j-1)*(max-min)/numValues).
	public Float next() {
		j++;
		return min + ((max - min) / numValues) * (j - 1);
	}

	public void remove() {

	}

}

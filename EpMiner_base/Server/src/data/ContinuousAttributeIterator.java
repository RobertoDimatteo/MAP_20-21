package data;

import java.util.Iterator;
import java.io.*;

/**
 * Classe che realizza l’iteratore che itera sugli elementi della sequenza
 * composta da valori reali equidistanti tra di loro (cut points) compresi
 * nell'intervallo [min, max[ ottenuti per mezzo di discretizzazione.
 */
public class ContinuousAttributeIterator implements Iterator<Float>, Serializable {

	/**
	 * ID necessario per serializzare gli oggetti di questa classe.
	 */
	private static final long serialVersionUID = 1L;

	// ATTRIBUTI

	/**
	 * Estremo inferiore dell'intervallo.
	 */
	private float min;

	/**
	 * Estremo superiore dell'intervallo.
	 */
	private float max;

	/**
	 * Posizione dell'iteratore nella collezione di cut points
	 * generati per l'intervallo [min, max[ tramite discretizzazione.
	 */
	private int j = 0;

	/**
	 * Numero di intervalli di discretizzazione.
	 */
	private int numValues;

	// COSTRUTTORE

	/**
	 * Costruttore che avvalora i membri con i valori passati come parametri.
	 * 
	 * @param min       valore dell'estremo inferiore dell'intervallo
	 * @param max       valore dell'estremo superiore dell'intervallo
	 * @param numValues valore del numero di intervalli di discretizzazione
	 */
	public ContinuousAttributeIterator(float min, float max, int numValues) {
		this.min = min;
		this.max = max;
		this.numValues = numValues;
	}

	// METODI

	/**
	 * Restituisce true se &lt;j&gt; è minore o uguale a &lt;numValues&gt;,
	 * false altrimenti.
	 * 
	 * @return true se j<=numValues, false altrimenti
	 */
	public boolean hasNext() {
		return (j <= numValues);
	}

	/**
	 * Incrementa j di 1 e restituisce il cut point in posizione j-1.
	 * 
	 * @return cut point in posizione j-1
	 */
	public Float next() {
		j++;
		return min + ((max - min) / numValues) * (j - 1);
	}

	public void remove() {
	}

}
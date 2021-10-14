package data;

import java.io.*;

// classe astratta Attribute che modella un generico attributo discreto o continuo.
@SuppressWarnings("serial")
public abstract class Attribute implements Serializable {

	// ATTRIBUTI

	private String name; // nome simbolico dell'attributo
	private int index; // identificativo numerico dell'attributo (indica la posizione della colonna
						// (0,1,2,…) che rappresenta l’attributo nella tabella di dati)

	// COSTRUTTORE

	// inizializza i valori dei membri name, index
	public Attribute(String name, int index) {

		this.name = name;
		this.index = index;
	}

	// METODI

	// restituisce il valore nel membro name
	public String getName() {
		return name;
	}

	// restituisce il valore nel membro index
	public int getIndex() {
		return index;
	}

	// overriding del metodo toString della classe Object
	// restituisce il valore del membro name
	public String toString() {
		return this.name;
	}
}

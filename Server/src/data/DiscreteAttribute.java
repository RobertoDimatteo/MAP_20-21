package data;


//classe DiscreteAttribute che estende la classe Attribute e 
//modella un attributo discreto rappresentando l’insieme di valori distinti del relativo dominio.
@SuppressWarnings("serial")
public class DiscreteAttribute extends Attribute{

	// ATTRIBUTI

	private String values[]; // array di stringhe, una per ciascun valore discreto ,
								// che rappresenta il domino dell’attributo

	// COSTRUTTORE

	// Invoca il costruttore della classe madre e
	// avvalora l'array values[ ] con i valori discreti in input.
	public DiscreteAttribute(String name, int index, String values[]) {
		super(name, index);

		this.values = values;
	}

	// METODI

	// Restituisce la cardinalità del membro values
	public int getNumberOfDistinctValues() {
		return values.length;
	}

	public String[] getValues() {
		return values;
	}

	// Restituisce il valore in posizione i del membro values
	public String getValue(int index) {
		return values[index];
	}
}

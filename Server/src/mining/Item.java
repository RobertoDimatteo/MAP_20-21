package mining;

import data.Attribute;
import java.io.*;

// classe astratta Item che modella un generico item (coppia attributo-valore, per esempio Outlook=”Sunny”).
@SuppressWarnings("serial")
abstract class Item implements Serializable {

	// ATTRIBUTI

	private Attribute attribute; // attributo coinvolto nell'item
	private Object value; // valore assegnato all'attributo

	// COSTRUTTORE

	// inizializza i valori dei membri attributi con i parametri passati come
	// argomento al costruttore.
	public Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;

	}

	// METODI

	// restituisce il membro attribute.
	public Attribute getAttribute() {
		return attribute;
	}

	// restituisce il membro value.
	public Object getValue() {
		return value;
	}

	// da realizzare nelle sottoclassi.
	abstract boolean checkItemCondition(Object value);

	// restituisce una stringa nella forma <attribute>=<value>.
	public String toString() {
		return "(" + getAttribute() + "=" + getValue() + ")";
	}
}

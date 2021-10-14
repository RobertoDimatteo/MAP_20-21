package mining;

import data.ContinuousAttribute;

// estende la classe astratta Item e modella la coppia 
// <Attributo continuo - Intervallo di valori > (Temperature in [10;30.31[)
@SuppressWarnings("serial")
public class ContinuousItem extends Item{

	// COSTRUTTORE

	// chiama il costruttore della superclasse passandogli come argomenti attribute
	// e value.
	public ContinuousItem(ContinuousAttribute attribute, Interval value) {
		super(attribute, value);
	}

	// METODI

	@Override
	// Comportamento: verifica che il parametro value rappresenti un numero reale
	// incluso tra gli estremi dell’intervallo associato allo item in oggetto
	// (richiamare il metodo checkValueInclusion(_) della classe Interval).
	// Object value al run time sarà di tipo Float
	boolean checkItemCondition(Object value) {
		Interval i = (Interval) this.getValue();
		return i.checkValueInclusion((Float) value);
	}

	// avvalora la stringa che rappresenta lo stato dell’oggetto (per esempio,
	// Temperatura in [10;20.4[) e ne restituisce il riferimento.
	public String toString() {
		String contAttr = "";
		Interval i = (Interval) this.getValue();
		return contAttr += "(" + this.getAttribute() + " in " + i + ")";
	}

}

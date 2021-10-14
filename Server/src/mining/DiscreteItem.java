package mining;

import data.DiscreteAttribute;

// classe concreta DiscreteItem che estende la classe Item e 
// rappresenta la coppia <Attributo discreto - valore discreto> (Outlook=”Sunny”).
@SuppressWarnings("serial")
class DiscreteItem extends Item{

	// COSTRUTTORE

	// Invoca il costruttore della classe madre per avvalora i membri.
	public DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	// METODI

	@Override
	// verifica che il membro value sia uguale (nello stato) all’argomento passato
	// come parametro della funzione.
	protected boolean checkItemCondition(Object value) {
		return getValue().equals(value);
	}

}

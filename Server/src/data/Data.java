package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import database.*;
import database.TableData.TupleData;

/**
 * Classe che modella un insieme di transazioni associando un valore
 * ad ogni attributo.
 */
public class Data {

	// ATTRIBUTI 
	
	/**
	 * Matrice di Object che ha numero di righe pari al numero di transazioni da
	 * memorizzare e numero di colonne pari al numero di attributi in ciascuna
	 * transazione.
	 *
	 * @uml.property name="data" multiplicity="(0 -1)" dimension="2"
	 */
	private Object data[][];

	/**
	 * Cardinalità dell’insieme di transazioni.
	 * 
	 * @uml.property name="numberOfExamples"
	 */
	private int numberOfExamples;

	/**
	 * Lista di attributi avvalorati in ciascuna transazione.
	 * 
	 * @uml.property name="attributeSet"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 */
	private List<Attribute> attributeSet = new LinkedList<Attribute>();

	// COSTRUTTORE
	
	/**
	 * Costruttore che si occupa di caricare i dati di addestramento da una tabella
	 * del database.
	 * 
	 * @param tableName nome della tabella
	 * 
	 * @throws DatabaseConnectionException lanciata quando fallisce la connessione
	 *                                     al database
	 * @throws SQLException                lanciata quando occorre un problema di
	 *                                     accesso al database
	 * @throws NoValueException            lanciata se il resultset è vuoto o il
	 *                                     valore calcolato è pari a null
	 */
	public Data(String tableName) throws DatabaseConnectionException, SQLException, NoValueException {
		// open db connection

		DbAccess db = new DbAccess();
		db.initConnection();

		TableSchema tSchema;
		try {
			tSchema = new TableSchema(tableName, db.getConnection());
			TableData tableData = new TableData(db.getConnection());
			List<TupleData> transSet = tableData.getTransazioni(tableName);

			data = new Object[transSet.size()][tSchema.getNumberOfAttributes()];
			for (int i = 0; i < transSet.size(); i++)
				for (int j = 0; j < tSchema.getNumberOfAttributes(); j++) {
					data[i][j] = transSet.get(i).tuple.get(j);
				}

			numberOfExamples = transSet.size();

			for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
				if (tSchema.getColumn(i).isNumber())
					attributeSet.add(new ContinuousAttribute(tSchema.getColumn(i).getColumnName(), i,
							((Float) (tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i),
									QUERY_TYPE.MIN))).floatValue(),
							((Float) (tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i),
									QUERY_TYPE.MAX))).floatValue() + 0.0606f));
				else {
					// avvalora values con i valori distinti della colonna oridinati in maniera
					// crescente
					List<Object> valueList = tableData.getDistinctColumnValues(tableName, tSchema.getColumn(i));
					String values[] = new String[valueList.size()];
					@SuppressWarnings("rawtypes")
					Iterator it = valueList.iterator();
					int ct = 0;
					while (it.hasNext()) {
						values[ct] = (String) it.next();
						ct++;
					}
					attributeSet.add(new DiscreteAttribute(tSchema.getColumn(i).getColumnName(), i, values));
				}
			}
		} finally {
			db.closeConnection();
		}
	}

	// METODI

	/**
	 * Restituisce il valore del membro &lt;numberOfExamples&gt;.
	 * 
	 * @return cardinalità dell'insieme di transazioni
	 * @uml.property name="numberOfExamples"
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Restituisce la cardinalità del membro &lt;attributeSet&gt;.
	 * 
	 * @return cardinalità dell'insieme degli attributi
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * Restituisce il valore dell' attributo &lt;attributeIndex&gt; per la
	 * transazione &lt;exampleIndex&gt; memorizzata in &lt;data&gt;.
	 * 
	 * @param exampleIndex   indice di riga per una specifica transazione
	 * @param attributeIndex indice di colonna per un attributo
	 * 
	 * @return valore assunto dall’attributo nella transazione di data
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data[exampleIndex][attributeSet.get(attributeIndex).getIndex()];
	}

	/**
	 * Restituisce l’attributo in posizione &lt;attributeIndex&gt; di
	 * &lt;attributeSet&gt;.
	 * 
	 * @param attributeIndex indice di colonna per un attributo
	 * 
	 * @return attributo con indice attributeIndex
	 */
	public Attribute getAttribute(int attributeIndex) {
		return attributeSet.get(attributeIndex);
	}

	/**
	 * Restituisce il membro &lt;attributeSet&gt;.
	 * 
	 * @return lista di attributi avvalorati in ciascuna transazione
	 */
	public List<Attribute> getAttributeSet() {
		return attributeSet;
	}

	/**
	 * Concatena i valori assunti dagli attributi separati da virgole in una stringa
	 * che rappresenta la transazione. Le stringhe sono poi concatenate in un'unica
	 * stringa.
	 * 
	 * @return stringa rappresentante tutti gli attributi e transazioni
	 */
	public String toString() {
		String value = "";
		for (int i = 0; i < numberOfExamples; i++) {
			value += (i + 1) + ":";
			for (int j = 0; j < attributeSet.size() - 1; j++)
				value += data[i][j] + ",";

			value += data[i][attributeSet.size() - 1] + "\n";
		}
		return value;
	}

}
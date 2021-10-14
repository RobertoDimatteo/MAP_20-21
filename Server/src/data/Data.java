package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import database.*;
import database.TableData.TupleData;

public class Data {

	/**
	 * @uml.property name="data" multiplicity="(0 -1)" dimension="2"
	 */
	private Object data[][];
	/**
	 * @uml.property name="numberOfExamples"
	 */
	private int numberOfExamples;
	/**
	 * @uml.property name="attributeSet"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 */
	private List<Attribute> attributeSet = new LinkedList<Attribute>();

	/*
	 * costruttore che si occupa di caricare i dati di addestramento da una tabella
	 * della base di dati. Il nome della tabella è un parametro del costruttore.
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
					Iterator it = valueList.iterator();
					int ct = 0;
					while (it.hasNext()) {
						values[ct] = (String) it.next();
						ct++;
					}
					attributeSet.add(new DiscreteAttribute(tSchema.getColumn(i).getColumnName(), i, values));
				}
			}
		}

		finally {
			db.closeConnection();
		}

	}

	// METODI

	/**
	 * @return
	 * @uml.property name="numberOfExamples"
	 */
	// Restituisce il valore del membro numberOfExamples
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	// Restituisce la cardinalità del membro attributeSet
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	// Restituisce il valore dell' attributo attributeIndex per la transazione
	// exampleIndex memorizzata in data
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data[exampleIndex][attributeSet.get(attributeIndex).getIndex()];
	}

	// Restituisce l’attributo in posizione attributeIndex di attributeSet
	public Attribute getAttribute(int attributeIndex) {
		return attributeSet.get(attributeIndex);
	}

	public List<Attribute> getAttributeSet() {
		return attributeSet;
	}

	// per ogni transazione memorizzata in data, concatena i valori assunti dagli
	// attributi
	// nella transazione separati da virgole in una stringa. Le stringhe che
	// rappresentano
	// ogni transazione sono poi concatenate in un’unica stringa da restituire in
	// output.
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
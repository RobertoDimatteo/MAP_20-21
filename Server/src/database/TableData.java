package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import database.TableSchema.Column;

/**
 * Classe che modella l'insieme di tuple collezionate in una tabella SQL
 * instaurandone una connessione. La singola tupla è modellata dalla inner class
 * &lt;TupleData&gt;.
 */
public class TableData {

	// ATTRIBUTI

	/**
	 * Gestisce una connessione.
	 */
	private Connection connection;

	// COSTRUTTORE

	public TableData(Connection connection) {
		this.connection = connection;
	}

	// INNER CLASS

	public class TupleData {

		// ATTRIBUTI

		public List<Object> tuple = new ArrayList<Object>();

		// METODI

		public String toString() {
			String value = "";
			Iterator<Object> it = tuple.iterator();
			while (it.hasNext())
				value += (it.next().toString() + " ");
			return value;
		}

	}

	// METODI

	/**
	 * Ricava lo schema della tabella con il nome del parametro &lt;table&gt; ed esegue una
	 * interrogazione per estrarne le tuple. Per ogni tupla del resultset viene
	 * creato un oggetto, istanza della classe &lt;TupleData&gt;, il cui riferimento
	 * va incluso nella lista da restituire. Ad ogni oggetto creato vengono aggiunti
	 * i valori dei singoli campi estratti da ogni tupla corrente.
	 * 
	 * @param table nome della tabella nel database
	 * 
	 * @return lista di tuple memorizzate nella tabella.
	 * 
	 * @throws SQLException lanciata quando occorre un problema di accesso al
	 *                      database
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException {
		LinkedList<TupleData> transSet = new LinkedList<TupleData>();
		Statement statement;
		TableSchema tSchema = new TableSchema(table, connection);

		String query = "select ";

		for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();
		query += (" FROM " + table);

		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			TupleData currentTuple = new TupleData();
			for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
				if (tSchema.getColumn(i).isNumber())
					currentTuple.tuple.add(rs.getFloat(i + 1));
				else
					currentTuple.tuple.add(rs.getString(i + 1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();

		return transSet;

	}

	/**
	 * Formula ed esegue un'interrogazione SQL per estrarre i valori distinti
	 * ordinati di &lt;column&gt; e per popolare una lista che restituisce in
	 * output.
	 * 
	 * @param table  nome della tabella
	 * @param column nome della colonna nella tabella
	 * 
	 * @return lista di valori distinti ordinati in modalità ascendente
	 * 
	 * @throws SQLException lanciata quando occorre un problema di accesso al
	 *                      database
	 */
	public List<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		LinkedList<Object> valueSet = new LinkedList<Object>();
		Statement statement;
		@SuppressWarnings("unused")
		TableSchema tSchema = new TableSchema(table, connection);

		String query = "select distinct ";

		query += column.getColumnName();

		query += (" FROM " + table);

		query += (" ORDER BY " + column.getColumnName());

		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);

		while (rs.next()) {
			if (column.isNumber())
				valueSet.add(rs.getFloat(1));
			else
				valueSet.add(rs.getString(1));
		}

		rs.close();
		statement.close();

		return valueSet;
	}

	/**
	 * Formula ed esegue un'interrogazione SQL per estrarre il valore aggregato
	 * cercato nella colonna &lt;column&gt; della tabella &lt;table&gt;.
	 * 
	 * @param table     nome della tabella
	 * @param column    nome della colonna nella tabella
	 * @param aggregate operatore SQL di aggregazione
	 * 
	 * @return aggregato cercato
	 * 
	 * @throws SQLException     lanciata quando occorre un problema di accesso al
	 *                          database
	 * @throws NoValueException lanciata se il resultset è vuoto o il valore
	 *                          calcolato è pari a null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
			throws SQLException, NoValueException {
		Statement statement;
		@SuppressWarnings("unused")
		TableSchema tSchema = new TableSchema(table, connection);
		Object value = null;
		String aggregateOp = "";

		String query = "select ";
		if (aggregate == QUERY_TYPE.MAX)
			aggregateOp += "max";
		else
			aggregateOp += "min";
		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;

		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if (column.isNumber())
				value = rs.getFloat(1);
			else
				value = rs.getString(1);
		}

		rs.close();
		statement.close();
		if (value == null)
			throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());

		return value;
	}

}
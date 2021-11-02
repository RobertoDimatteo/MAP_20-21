package utility;

/**
 * Classe che modella una struttura coda, usata come contenitore a modalità FIFO
 * per i pattern frequenti scoperti a livello k da usare per generare i pattern
 * candidati a livello k+1.
 * 
 * @param <T> tipo generico
 */
public class Queue<T> {

	private Record begin = null;

	private Record end = null;

	private class Record {

		Object elem;

		Record next;

		Record(Object e) {
			this.elem = e;
			this.next = null;
		}
	}

	@SuppressWarnings("hiding")
	public <T> boolean isEmpty() {
		return this.begin == null;
	}

	@SuppressWarnings("hiding")
	public <T> void enqueue(Object e) {
		if (this.isEmpty())
			this.begin = this.end = new Record(e);
		else {
			this.end.next = new Record(e);
			this.end = this.end.next;
		}
	}

	@SuppressWarnings("hiding")
	public <T> Object first() throws EmptyQueueException {
		if (!isEmpty()) {
			return this.begin.elem;
		} else
			throw new EmptyQueueException("La coda è vuota");
	}

	@SuppressWarnings("hiding")
	public <T> void dequeue() throws EmptyQueueException {
		if (this.begin == this.end) {
			if (this.begin == null)
				throw new EmptyQueueException("La coda è vuota");
			else
				this.begin = this.end = null;
		} else {
			begin = begin.next;
		}
		
	}

}
package utility;

public class Queue<T> {

	private Record<T> begin = null;

	private Record<T> end = null;

	@SuppressWarnings("hiding")
	private class Record<T> {

		Object elem;

		Record<T> next;

		Record(Object e) {
			this.elem = e;
			this.next = null;
		}
	}

	public boolean isEmpty() {
		return this.begin == null;
	}

	public void enqueue(Object e) {
		if (this.isEmpty())
			this.begin = this.end = new Record<T>(e);
		else {
			this.end.next = new Record<T>(e);
			this.end = this.end.next;
		}
	}

	// l metodo che legge il primo elemento aggiunto alla coda
	public Object first() throws EmptyQueueException {
		if (!isEmpty()) {
			return this.begin.elem;
		} else
			throw new EmptyQueueException("La coda è vuota");
	}

	public void dequeue() throws EmptyQueueException {
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
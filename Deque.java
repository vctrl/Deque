import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
        assert check();
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size () {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (N == 0)
            last = first;
        else
            oldfirst.prev = first;
        N++;
        assert check();

    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (N == 0)
            first = last;
        else
            oldlast.next = last;
        N++;
        assert check();
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow");
        Item item = first.item;
        first = first.next;
        N--;
        assert check();
        if (isEmpty()) {
            // to avoid loitering; first already points to null
            last = null;
        } else {
            first.prev = null;
        }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque underflow");
        Item item = last.item;
        last = last.prev;
        N--;
        if (isEmpty()) {
            // to avoid loitering; first already points to null
            first = null;
        } else {
            last.next = null;
        }
        assert check();
        return item;
    }

    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private boolean check() {

        // check a few properties of instance variable 'first'
        if (N < 0) {
            return false;
        }
        if (N == 0) {
            if ((first != null) && (last != null)) return false;
        }
        else if (N == 1) {
            if (first == null)      return false;
            if (last == null)       return false;
            if (first.next != null) return false;
            if (last.prev != null)  return false;
        }
        else {
            if (first == null)      return false;
            if (first.next == null) return false;
            if (last == null)       return false;
            if (last.prev == null)  return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= N; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;

        return true;
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.isEmpty();
        deque.addFirst("one");
        deque.addFirst("two");
        deque.addFirst("three");
        deque.addFirst("guys");
        deque.isEmpty();
        deque.removeFirst();
        for (String a : deque)
            StdOut.println(a);
    }
}

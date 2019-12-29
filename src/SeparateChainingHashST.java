import java.util.*;

class Node<K, V> {
	K key;
	V val;
	Node<K, V> next;
	
	public Node(K key, V val, Node<K, V> next) {
		this.key = key;
		this.val = val;
		this.next = next;
	}
}

class SequentialSearchST<K, V> {
	private Node<K, V> first;
	int N;
	
	public V get(K key) {
		for(Node<K, V> x = first; x != null; x = x.next)
			if(key.equals(x.key)) return x.val;
		return null;
	}
	
	public void put(K key, V val) {
		for(Node<K, V> x = first; x != null; x = x.next) {
			if(key.equals(x.key)) {
				x.val = val;
				return;
			}
		}
		first = new Node<K, V>(key, val, first);
		N++;
	}
	
	public void delete(K key) {
		if(key.equals(first.key)) {
			first = first.next;
			N--;
			return;
		}
		for(Node<K, V> x = first; x.next != null; x = x.next) {
			if(key.equals(x.next.key)) {
				x.next = x.next.next;
				N--;
				return;
			}
		}
	}
	
	public Iterable<K> keys() {
		ArrayList<K> keyList = new ArrayList<K>(N);
		for(Node<K, V> x = first; x != null; x = x.next)
			keyList.add(x.key);
		return keyList;
	}
	
	public boolean contains(K key) { return get(key) != null; }
	
	public boolean isEmpty() { return N == 0; }
	
	public int size() { return N; }
}

public class SeparateChainingHashST<K, V> {
	private int M;
	private int N;
	private SequentialSearchST<K, V>[] st;
	
	public SeparateChainingHashST() { this(997); }
	public SeparateChainingHashST(int M) {
		this.M = M;
		st = new SequentialSearchST[M];
		for(int i = 0; i < M; i++)
			st[i] = new SequentialSearchST();
	}
	public boolean contains(K key) { return get(key) != null; }
	
	public boolean isEmpty() { return N == 0; }
	
	public int size() { return N; }
	
	private int hash(K key) { return (key.hashCode() & 0x7fffffff) % M; }

	public V get(K key) { return st[hash(key)].get(key); }
	
	public void put(K key, V val) {
		if(!contains(key)) N++;
		st[hash(key)].put(key,  val);
	}
	
	public void delete(K key) {
		if(!contains(key)) return;
		st[hash(key)].delete(key);
		N--;
	}
	
	public Iterable<K> keys() {
		ArrayList<K> keyList = new ArrayList<K>();
		for(int i = 0; i < M; i++) {
			for(K key : st[i].keys())
				keyList.add(key);
		}
		return keyList;
	}
	
	public static void main(String[] args) {
		SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST(5);
		
		st.put("S", 0); st.put("E", 1); st.put("A", 2); st.put("R", 3); st.put("C", 4); st.put("H", 5);
		st.put("E", 6); st.put("X", 7); st.put("A", 8); st.put("M", 9); st.put("P", 10); st.put("L", 11); st.put("E", 12);
		
		for(int i = 0; i < st.M; i++) {
			for(String key : st.st[i].keys())
				System.out.print("(" + key + ", " + st.get(key) + ") ");
			System.out.println();
		}
	}
}
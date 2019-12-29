public class LinearProbingHashST<K, V> {
	private int N;
	private int M;
	private K[] keys;
	private V[] vals;
	
	public LinearProbingHashST() { this(31); }
	public LinearProbingHashST(int M) {
		keys = (K[])new Object[M];
		vals = (V[])new Object[M];
		this.M = M;
	}
	
	public boolean contains(K key) { return get(key) != null; }
	public boolean isEmpty() { return N == 0; }
	public int size() { return N; }
	private int hash(K key) { return (key.hashCode() & 0x7fffffff) % M; }
	
	public V get(K key) {
		for(int i = hash(key); keys[i] != null; i = (i + 1) % M) {
			if(key.equals(keys[i])) return vals[i];
		}
		return null;
	}
	
	public void put(K key, V val) {
		if(N >= (M / 2)) resize(2 * M + 1);
		int i;
		
		for(i = hash(key); keys[i] != null; i++) {
			if(key.equals(keys[i])) {
				vals[i] = val;
				return;
			}
		}
		keys[i] = key;
		vals[i] = val;
		N++;
	}
	
	public void delete(K key) {
		if(!contains(key)) return;
		
		int i = hash(key);
		while(!key.equals(keys[i])) i = (i + 1) & M;
		keys[i] = null; vals[i] = null;
		
		i = (i + 1) & M;
		while(keys[i] != null) {
			K tempKey = keys[i];
			V tempVal = vals[i];
			keys[i] = null; vals[i] = null; N--;
			put(tempKey, tempVal);
			i = (i + 1) & M;
		}
		N--;
	}
	
	private void resize(int cap) {
		LinearProbingHashST<K, V> t = new LinearProbingHashST(cap);
		
		for(int i = 0; i < M; i++) {
			if(keys[i] != null) t.put(keys[i], vals[i]);
		}
		keys = t.keys;
		vals = t.vals;
		M = t.M;
	}
	
	public static void main(String[] args) {
		LinearProbingHashST<String, Integer> st = new LinearProbingHashST(5);
		
		st.put("S", 0); st.put("E", 1); st.put("A", 2); st.put("R", 3); st.put("C", 4); st.put("H", 5);
		st.put("E", 6); st.put("X", 7); st.put("A", 8); st.put("M", 9); st.put("P", 10); st.put("L", 11); st.put("E", 12);
		
		System.out.println("(S, " + st.get("S") + ")"); System.out.println("(E, " + st.get("E") + ")");
		System.out.println("(A, " + st.get("A") + ")"); System.out.println("(R, " + st.get("R") + ")");
		System.out.println("(C, " + st.get("C") + ")"); System.out.println("(H, " + st.get("H") + ")");
		System.out.println();
		System.out.println("(E, " + st.get("E") + ")"); System.out.println("(X, " + st.get("X") + ")");
		System.out.println("(A, " + st.get("A") + ")"); System.out.println("(M, " + st.get("M") + ")");
		System.out.println("(P, " + st.get("P") + ")"); System.out.println("(L, " + st.get("L") + ")");
		System.out.println("(E, " + st.get("E") + ")");
	}
}

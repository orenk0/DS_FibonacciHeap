/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	private int size;
	private HeapNode first;
	private HeapNode min;
	
	public FibonacciHeap() {
		this.size = 0;
	}
	
   /**
    * public boolean isEmpty()
    *
    * Returns true if and only if the heap is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return this.size==0;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * The added key is assumed not to already belong to the heap.  
    * 
    * Returns the newly created node.
    */
    public HeapNode insert(int key)
    {    
    	HeapNode node = new HeapNode(key);
    	if(this.isEmpty()) {
    		node.setLeft(node);
    		node.setRight(node);
    		this.size++;
    		this.first = node;
    		this.min = node;
    		return node;
    	}
    	node.setLeft(first.getLeft());
    	node.setRight(first);
    	this.size++;
    	this.first = node;
    	if(this.min.getKey()>key) this.min = node;
    	return node;
    }

   /**
    * public void deleteMin()
    *
    * Deletes the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	return new HeapNode(678);// should be replaced by student code
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	  return; // should be replaced by student code   		
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return -123; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
    * (Note: The size of of the array depends on the maximum order of a tree.)  
    * 
    */
    public int[] countersRep()
    {
    	int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
	* It is assumed that x indeed belongs to the heap.
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
    * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

   /**
    * public int nonMarked() 
    *
    * This function returns the current number of non-marked items in the heap
    */
    public int nonMarked() 
    {    
        return -232; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * 
    * In words: The potential equals to the number of trees in the heap
    * plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
        return -234; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the
    * run-time of the program. A link operation is the operation which gets as input two
    * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
    * tree which has larger value in its root under the other tree.
    */
    public static int totalLinks()
    {    
    	return -345; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the
    * run-time of the program. A cut operation is the operation which disconnects a subtree
    * from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return -456; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
    * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
    *  
    * ###CRITICAL### : you are NOT allowed to change H. 
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in another file. 
    *  
    */
    public static class HeapNode{
    	
    	private HeapNode right;
    	private HeapNode left;
    	private HeapNode parent;
    	private HeapNode child;
    	private boolean mark; 
    	public int key;
    	

    	public HeapNode(int key) {
    		this.key = key;
    		this.mark = 0;
    	}

    	public int getKey() {
    		return this.key;
    	}
    	public boolean getMark() {
    		return this.mark;
    	}
    	public HeapNode getRight() {
    		return this.right;
    	}
    	public HeapNode getLeft() {
    		return this.left;
    	}
    	public HeapNode getParent() {
    		return this.parent;
    	}
    	public HeapNode getChild() {
    		return this.child;
    	}
    	

    	public int setKey(int key) {
    		this.key;
    	}
    	public boolean setMark(boolean mark) {
    		this.mark = mark;
    	}
    	public HeapNode setRight(HeapNode r) {
    		this.right = r;
    	}
    	public HeapNode setLeft(HeapNode l) {
    		this.left = l;
    	}
    	public HeapNode setParent(HeapNode p) {
    		this.parent = p;
    	}
    	public HeapNode setChild(HeapNode c) {
    		this.child = c;
    	}
    }
}

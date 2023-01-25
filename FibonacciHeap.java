import java.lang.Math.*;
/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap
{
	private static int links = 0;
	private static int cuts = 0;
	private int size;
	private HeapNode first;
	private HeapNode min;
	private int trees = 0;
	private int marked = 0;
	
	public FibonacciHeap() {
		this.size = 0;
	}
	public void setSize(int s) {
		this.size = s;
	}
	public void setFirst(HeapNode f) {
		this.first = f;
	}
	public void setMin(HeapNode m) {
		this.min = m;
	}
	public int getSize() {
		return this.size;
	}
	public HeapNode getFirst() {
		return this.first;
	}
	public HeapNode getMin() {
		return this.min;
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
    	this.trees++;
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
    	first.getLeft().setRight(node);
    	first.setLeft(node);
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
    	//separated cases:
     	if(this.isEmpty()) return;
     	if(this.size==1) {
     		this.trees = 0;
     		this.first = null;
     		this.min = null;
     		this.size = 0;
     		return;
     	}
     	
     	this.trees+=1-this.min.getRank();
     	//melding children:
     	if(this.min==this.first) {
     		if(this.min.getChild()!=null) this.first = this.min.getChild();
     		else this.first = this.min.getRight();
     	}
     	if(this.min.getChild()!=null) {
	     	this.min.getLeft().setRight(this.min.getChild());
	     	this.min.getRight().setLeft(this.min.getChild().getLeft());
	     	HeapNode tempPrevLeft = this.min.getChild().getLeft();
	     	this.min.getChild().setLeft(this.min.getLeft());
	     	tempPrevLeft.setRight(this.min.getRight());
     	}else if(this.min.getRight()!=null){
     		HeapNode tempPrevLeft = this.min.getLeft();
     		this.min.getLeft().setRight(this.min.getRight());
     		this.min.getRight().setLeft(tempPrevLeft);
     	}
     	
     	
     	
     	//creating the array of the "buckets" (max rank is log_{phi}(n); I took log_{1.6}(n) which is even larger):
     	HeapNode[] buckets = new HeapNode[(int)(1+Math.log(this.size)/Math.log(1.6))];//same as log_{1.6}(n)
     	//iterating over the forest and linking trees with the same rank (consolidate). also finding new min node and deleting min as a parent:
     	int minVal = this.first.getKey();
     	HeapNode cur = this.first;
     	HeapNode next;
     	while(cur.getRight()!=cur){//different node than first:
     		if(cur.getParent()==this.min) cur.setParent(null);
     		next = cur.getRight();
     		if(cur.getKey()<minVal) {//updating min.
     			minVal = cur.getKey();
     		}
     		insertNodeToBuckets(buckets, cur);
     		cur=next;
     	}
     	//one more iteration 
     	if(cur.getParent()==this.min) cur.setParent(null);
 		if(cur.getKey()<minVal) {//updating min.
 			minVal = cur.getKey();
 		}
 		insertNodeToBuckets(buckets, cur);

     	
     	this.first = null;
     	HeapNode prev = null;
     	cur = null;
     	HeapNode last = null;
     	for(HeapNode node : buckets) {
     		if(this.first == null && node != null) this.first = node;
     		if(node != null) {
     			if(node.getKey() == minVal) this.min = node;
     			cur = node;
     			if(prev != null) {
     				cur.setLeft(prev);
         			prev.setRight(cur);
     			}
     			prev = cur;
     			last = cur;
     		}
     	}
     	last.setRight(this.first);
     	this.first.setLeft(last);
     	this.size-=1;
    }
    
    //inserts the tree's pointer to the array and links accordingly:
    public void insertNodeToBuckets(HeapNode[] buckets, HeapNode tree) {
    	HeapNode slot = buckets[tree.getRank()];
    	if(slot==null) {
    		//put in bucket:
    		buckets[tree.getRank()]=tree;
    		//uproot:
    		if(tree.getLeft()!=null && tree.getRight()!=null) {
    			tree.getLeft().setRight(tree.getRight());
    			tree.getRight().setLeft(tree.getLeft());
    			tree.setLeft(tree);
    			tree.setRight(tree);
    		}
    	}
    	else {
    		buckets[tree.getRank()]=null;
    		HeapNode linked = link(slot, tree);//the new pointer to the combined tree
    		
    		insertNodeToBuckets(buckets,linked);
    	}
    	
    	
    	
    }
    
    //link and returns pointer to new combine tree
    public HeapNode link(HeapNode t1, HeapNode t2) {
    	links++;
    	if(t1.getKey()<t2.getKey()) {
    		//removing pointers from neighbors to t2 (digging a hole/uprooting):
    		if(t2.getLeft()!=null && t2.getRight()!=null) {
	    		t2.getLeft().setRight(t2.getRight());
	    		t2.getRight().setLeft(t2.getLeft());
    		}
    		//setting t2 3 directions (plant):
    		t2.setParent(t1);
    		if(t1.getChild()!=null) {
	    		t2.setRight(t1.getChild());
	    		t2.setLeft(t1.getChild().getLeft());
    		}else {
    			t2.setRight(t2);
    			t2.setLeft(t2);
    		}
    		//point all neighbors to t2 (cover with soil):
    		if(t1.getChild()!=null) {
	    		t1.getChild().getLeft().setRight(t2);
	    		t1.getChild().setLeft(t2);
    		}
    		t1.setChild(t2);
    		//change rank of t1 (water?):
    		t1.setRank(t1.getRank()+1);
    		//removing pointers from neighbors to t1 (uprooting):
    		if(t1.getLeft()!=null && t1.getRight()!=null) {
	    		t1.getLeft().setRight(t1.getRight());
	    		t1.getRight().setLeft(t1.getLeft());
    		}
    		return t1;
    	}else {
    		//removing pointers from neighbors to t1 (digging a hole/uprooting):
    		if(t1.getLeft()!=null && t1.getRight()!=null) {
	    		t1.getLeft().setRight(t1.getRight());
	    		t1.getRight().setLeft(t1.getLeft());
    		}
    		//setting t1 3 directions (plant):
    		t1.setParent(t2);
    		if(t2.getChild()!=null) {
	    		t1.setRight(t2.getChild());
	    		t1.setLeft(t2.getChild().getLeft());
    		}else {
    			t1.setRight(t1);
    			t1.setLeft(t1);
    		}
    		//point all neighbors to t1 (cover with soil):
    		if(t2.getChild()!=null) {
	    		t2.getChild().getLeft().setRight(t1);
	    		t2.getChild().setLeft(t1);
    		}
    		t2.setChild(t1);
    		//change rank of t2 (water?):
    		t2.setRank(t2.getRank()+1);
    		//removing pointers from neighbors to t2 (uprooting):
    		if(t2.getLeft()!=null && t2.getRight()!=null) {
	    		t2.getLeft().setRight(t2.getRight());
	    		t2.getRight().setLeft(t2.getLeft());
    		}
    		return t2;
    	}
    }

   /**
    * public HeapNode findMin()
    *
    * Returns the node of the heap whose key is minimal, or null if the heap is empty.
    *
    */
    public HeapNode findMin()
    {
    	return min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Melds heap2 with the current heap.
    *
    */
    public void meld(FibonacciHeap heap2)
    {
    	this.trees += heap2.trees;
    	//connecting relations:
    	this.first.getLeft().setRight(heap2.getFirst());
    	HeapNode tempRightMost = this.first.getLeft();
    	this.first.setLeft(heap2.getFirst().getLeft());
    	heap2.getFirst().getLeft().setRight(this.first);
    	heap2.getFirst().setLeft(tempRightMost);
    	//updating size:
    	this.size += heap2.getSize();
    	//updating min:
    	if(heap2.getMin().getKey()<this.min.getKey()) this.min=heap2.getMin();
    }

   /**
    * public int size()
    *
    * Returns the number of elements in the heap.
    *   
    */
    public int size()
    {
    	return size;
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
    	int[] result;
    	HeapNode cur = this.first;
    	int r = cur.getRank();
    	if(cur.getRight()==cur) {
    		result = new int[r+1];
    		result[r] = 1;
    		return result;
    	}
    	do {
    		if(cur.getRank()>r) r = cur.getRank();
    		cur = cur.getRight();
    	}while(cur!=this.first);
    	result = new int[r+1];
    	cur = this.first;
    	do {
    		result[cur.getRank()] += 1;
    		cur = cur.getRight();
    	}while(cur!=this.first);
    	return result;
    	
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
    	this.decreaseKey(x,x.getKey()-this.min.getKey()+1);
    	this.deleteMin();
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
        return this.size - this.marked;
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
        return this.trees+2*this.marked;
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
    	return links;
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
    	return cuts;
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
    	FibonacciHeap minor = new FibonacciHeap();
    	int[] result = new int[k];
    	int counter = 1;//used for appending to result
    	HeapNode root = minor.insert(H.getFirst().getKey());//this is the root
    	root.setReference(H.getFirst());//to be able to access back efficiently
    	result[0] = root.getKey();
    	for(int i = 1 ; i < k ; i++) {
    		InternalTester.print(minor);
    		System.out.println();
    		HeapNode node = minor.findMin();
    		node = node.getReference();
    		minor.deleteMin();
    		if(node.getChild()!=null) {
	    		node = node.getChild();
	    		HeapNode cur = node;
	    		do {
	    			HeapNode inserted = minor.insert(cur.getKey());
	    	    	inserted.setReference(cur);//to be able to access back efficiently
	    			cur = cur.getRight();
	    		}while(cur!=node);
    		}
    		result[counter] = minor.findMin().getKey();
    		counter++;
    	}
    	return result;
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
    	private int rank;
    	public int key;
    	
    	private HeapNode reference;//used only for kMin.
    	
    	
    	
    	public HeapNode(int key) {
    		this.key = key;
    		this.mark = false;
    		this.reference = null;
    	}
    	public HeapNode(HeapNode reference) {//used only for kMin.
    		this.key = reference.key;
    		this.mark = false;
    		this.reference = reference;
    	}
    	
    	public int getKey() {
    		return this.key;
    	}
    	public int getRank() {
    		return this.rank;
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
    	public HeapNode getNext() {
    		return this.right;
    	}
    	public HeapNode getPrev() {
    		return this.left;
    	}
    	public HeapNode getParent() {
    		return this.parent;
    	}
    	public HeapNode getChild() {
    		return this.child;
    	}
    	public HeapNode getReference() {
    		return this.reference;
    	}
    	
    	
    	public void setKey(int key) {
    		this.key = key;
    	}
    	public void setRank(int r) {
    		this.rank = r;
    	}
    	public void setMark(boolean mark) {
    		this.mark = mark;
    	}
    	public void setRight(HeapNode r) {
    		this.right = r;
    	}
    	public void setLeft(HeapNode l) {
    		this.left = l;
    	}
    	public void setNext(HeapNode r) {
    		this.right = r;
    	}
    	public void setPrev(HeapNode l) {
    		this.left = l;
    	}
    	public void setParent(HeapNode p) {
    		this.parent = p;
    	}
    	public void setChild(HeapNode c) {
    		this.child = c;
    	}
    	public void setReference(HeapNode r) {
    		this.reference = r;
    	}
    	
    	/*public String toString() {
    		return  "key: "+key+"\n"+
    				"mark: "+mark+"\n"+
    				"rank: "+rank+"\n"+
    				"right: "+right+"\n"+
    				"left: "+left+"\n"+
    				"parent: "+parent+"\n"+
    				"child: "+child+"\n";
    	}*/
    }
}


public class InternalTester
{
	public static void main(String args[]) {
		FibonacciHeap fh = new FibonacciHeap();
		System.out.println(fh.isEmpty());
		for(int i = 0 ; i < 13 ; i++) {
			fh.insert(i);
			if((i*i*i)%53<12) {
				fh.deleteMin();
			}
		}
		System.out.println(fh.isEmpty());
	}
}

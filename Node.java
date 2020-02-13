public class Node<Key extends Comparable<Key>,Value>{
	
	protected Key key;
	protected Value val;
	protected Node left, right,parent,grandparent,uncle;
	protected boolean color;
	protected int size;
	
	 public Node(Key key, Value val, boolean color, int size) {
	        this.key = key;
	        this.val = val;
	        this.color = color;
	        this.size = size;
	    }

}

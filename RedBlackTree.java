public class RedBlackTree <Key extends Comparable<Key>, Value>  {


    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    public Node root; 
   
    
	private boolean isRed(Node node)
    {
        if (node == null)
        {
        	return false;
        }
        return node.color == RED;
    }
	
    public boolean isEmpty() 
    {
        return root == null;
    }
   
    private int Size(Node node)
    {
        if (node == null)
        {
        	return 0;
        }
        return node.size;
    } 
    
    public int Size() 
    {
        return Size(root);
    }

    public Value GetVal(Key key) 
    {
        if (key == null) 
        {
        	throw new IllegalArgumentException("Argument is null");
        }
        return GetVal(root, key);
    }

    private Value GetVal(Node node, Key key) 
    {
        while (node != null) 
        {
            int cmp = key.compareTo((Key) node.key);
            if(cmp < 0) 
            {
            	node = node.left;
            }
            else if(cmp > 0)
            {
            	node = node.right;
            }
            else
            {
            	return (Value) node.val;
            }
        }
        return null;
    }
    
    public void PutVal(Key key, Value val) {
        if (key == null)
        {
        	throw new IllegalArgumentException("Argument is null");
        }
        if (val == null) 
        {
            return;
        }
        root = PutVal(root, key, val);
        //balance(root);
        root.color = BLACK;
    }

    private Node PutVal(Node node, Key key, Value val) 
    { 
    	
        if (node == null) 
        {
        	return new Node(key, val, RED, 1);
        }

        int cmp = key.compareTo((Key) node.key);
        
        if(cmp < 0) 
        {
        	node.left  = PutVal(node.left,  key, val); 
        }
        else if(cmp > 0) 
        {
        	node.right = PutVal(node.right, key, val); 
        }
        else
        {
        	node.val   = val;
        }
        node.size = Size(node.left) + Size(node.right) + 1;
        return node;
    }
    private void balance(Node node)
    {
    	Node parent = parent(node);
    	Node grandparent = grandparent(node);
    	Node uncle = uncle(node);
    	
        if(isTriangle(node) == true && node.uncle.color == BLACK)
        {
        	if(parent == grandparent.left) RotateRight(parent);
        	if(parent == grandparent.right) RotateLeft(parent);
        }
        
        if(isLine(node) == true && node.uncle.color == BLACK)
        {
        	if(parent == grandparent.left)
        	{
        		RotateRight(grandparent);
            	Recolor(grandparent);
            	Recolor(parent);	
        	}
        	if(parent == grandparent.left)
        	{
        		RotateLeft(grandparent);
            	Recolor(grandparent);
            	Recolor(parent);	
        	}	
        }
        
        if(root.color == RED)
        {
        	Recolor(root);
        }
        
        if(node.uncle.color == RED)
        {
        	Recolor(grandparent);
        	Recolor(parent);
        	Recolor(uncle);
        }    
        
        if(depth(node) == false)
        {
        	if(rightdepth(node) > leftdepth(node))
        	{
        		RotateLeft(grandparent);
        		Recolor(grandparent);
            	Recolor(grandparent(parent));
        	}
        	if(leftdepth(node) > rightdepth(node))
        	{
        		RotateRight(grandparent);
        		Recolor(grandparent);
            	Recolor(grandparent(parent));
        	}        	
        }
        
    }
    
    private Node RotateRight(Node node)
    {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = temp.right.color;
        temp.right.color = RED;
        temp.size = node.size;
        node.size = Size(node.left) + Size(node.right) + 1;
        return temp;
    }

    private Node RotateLeft(Node node) 
    {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = temp.left.color;
        temp.left.color = RED;
        temp.size = node.size;
        node.size = Size(node.left) + Size(node.right) + 1;
        return temp;
    }

    public void Recolor(Node node) 
    {
    	node.color = !node.color;
    	node.left.color = !node.left.color;
    	node.right.color = !node.right.color;
    }
    
    private Node find(Node root,Node node)
    {
    	if(root == null || node == null) return null;
    	else if(root.right != null && root.right.val == node.val ||
    			root.left != null && root.left.val == node.val)
    	{
    		return root;
    	}
    	else
    	{
    		Node temp = find(root.right,node);
    		if(temp == null)
    		{
    			temp = find(root.left,node);
    		}
    		return temp;
    	}
    }
    public Node parent(Node node)
	{
		return find(root,node);			
	}
	public Node grandparent(Node node)
	{
		Node parent = parent(node);
		if(parent == null) return null;
		else return parent(parent); 
	}
	public Node sibling(Node node)
	{
		Node parent = parent(node);
		if(parent == null) return null;
		else
		{
			if(node == parent.left) return parent.right;
			else return parent.left;
		}
	}
	
	public Node uncle(Node node)
	{
		Node parent = parent(node);
		Node grandparent = parent(parent);
		if(grandparent == null) return null;
		else return sibling(parent);
	}
	
	 private boolean isLine(Node node)
	 {
		 Node parent = parent(node);
		 Node grandparent = parent(parent);
	    	if(node.parent == node.grandparent.right && node == parent.right && node.color == RED &&node.parent.color ==RED ||
	    	   node.parent == node.grandparent.left && node == parent.left && node.color == RED &&node.parent.color ==RED )
	    		return true;
	    	else  return false;	  
	 }
	 
	 private boolean isTriangle(Node node)
	 {
		 Node parent = parent(node);
		 Node grandparent = parent(parent);
		 if(node.parent == node.grandparent.right && node == parent.left  && node.color == RED &&node.parent.color ==RED ||
		    node.parent == node.grandparent.left && node == parent.right && node.color == RED &&node.parent.color ==RED)
			return true;
		 else return false;
	 }
	 	 
	 public int leftdepth(Node node)
	 {
		 if(node == null) return 0;
		 else return 1 + leftdepth(node.left);
	 }
	 
	 public int rightdepth(Node node)
	 { 
		 if(node == null) return 0;
		 else  return 1 + rightdepth(node.right);
	 }
	 
	 private boolean depth(Node node)
	 {
		 if(leftdepth(root) - rightdepth(root) > 1 || rightdepth(root) - leftdepth(root) > 1)
		 {
			 return false;
		 }
		 else return true;
	 }
	 
	 private boolean search(Key key,Node node)
	 {
		 try
		 { 
			 if(node == null) return false;
			 else
			 {
				 if(key.compareTo((Key) node.key) == 0)
				 {
					 System.out.println("Key : " + node.val);
					 System.out.println("Color : " + node.color);
					 if(grandparent(node) == null && parent(node)==null) throw new Exception ("Error.");
					 System.out.println("Parent.Key : " + parent(node).key + "Parent.Color" + parent(node).color);
					 System.out.println("Grandparent.Key : " + grandparent(node).key + "Grandparent.Color" + grandparent(node).color);
					 System.out.println("Uncle.Key : " + uncle(node).key + "Uncle.Color" + uncle(node).color);
				     return true;
				 }
				 else
				 {
					 if(key.compareTo((Key)node.key) < 0)
					 {
						 if(node.left == null) return false;							
						 else return search(key, node.left);
							
					}
					 else if(key.compareTo((Key)node.key) < 0)
					 {
						 if(node.right == null) return false;
						 else return search(key, node.right);			
					}
				 }
			 }
			 
		 }
		 
		 catch (Exception e)
		 {
			 System.out.println("The node has no ancestor.");
		 }
		return false;
		 
	 }
	 
	 public void search(Key key)
	 {
		 String search = (String) key;
		 search(key,root);
	 }
}

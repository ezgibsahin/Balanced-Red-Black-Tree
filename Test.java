import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		Scanner scn = new Scanner(System.in);
		RedBlackTree <String,Integer> tree = new RedBlackTree<String,Integer>();
		
		tree.PutVal("D", 4);
		tree.PutVal("A", 1);
		tree.PutVal("B", 2);
		tree.PutVal("C", 3);	
		tree.PutVal("E", 5);
		tree.PutVal("F", 6);
	
		while(true)
		{
			
			System.out.println("Search : ");
			String key = scn.next();
			tree.search(key);
		}
	}

}
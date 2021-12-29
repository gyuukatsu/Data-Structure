// AVL Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)
  
public class AVL extends BST
{
	
	public AVL() { 
		this.root=null;
		this.size=0;
	}
	
	public int height(TreeNode n) {
		if(n==null) {
			return 0;
		}
		else {
			return n.height;
		}
	}
	
	public void insert(String key) { 
		root=avl_insert(root,key);
	}
	
	public TreeNode avl_insert(TreeNode n, String key) {
		if(n==null) {
			TreeNode tmp=new TreeNode(key);
			tmp.freq=1;
			this.size++;
			return tmp;
		}
		if(n.key.compareTo(key)>0) {
			n.left=avl_insert(n.left,key);
		}
		else if(n.key.compareTo(key)<0) {
			n.right=avl_insert(n.right,key);
		}
		else {
			n.freq++;
			return n;
		}
		
		n.height=Integer.max(height(n.left),height(n.right))+1;
		
		int b=getbalance(n);
		
		if(b>1 && n.left.key.compareTo(key)>0) {
			return RotateRight(n);
		}
		else if(b<-1 && n.right.key.compareTo(key)<0) {
			return RotateLeft(n);
		}
		else if(b>1 && n.left.key.compareTo(key)<0) {
			n.left=RotateLeft(n.left);
			return RotateRight(n);
		}
		else if(b<-1 && n.right.key.compareTo(key)>0) {
			n.right=RotateRight(n.right);
			return RotateLeft(n);
		}
		return n;
	}
	
	public TreeNode RotateRight(TreeNode n) {
		TreeNode x=n.left;
		TreeNode y=x.right;
		x.right=n;
		n.left=y;
		n.height=Integer.max(height(n.left),height(n.right))+1;
		x.height=Integer.max(height(x.left),height(x.right))+1;
		
		return x;
	}
	
	public TreeNode RotateLeft(TreeNode n) {
		TreeNode x=n.right;
		TreeNode y=x.left;
		x.left=n;
		n.right=y;
		n.height=Integer.max(height(n.left),height(n.right))+1;
		x.height=Integer.max(height(x.left),height(x.right))+1;
		
		return x;
	}
	
	public int getbalance(TreeNode n) {
		if(n==null) {
			return 0;
		}
		else {
			return height(n.left)-height(n.right);
		}
	}
 
}


// (Nearly) Optimal Binary Search Tree
// Bongki Moon (bkmoon@snu.ac.kr)

public class BST{ // Binary Search Tree implementation

  protected boolean NOBSTified = false;
  protected boolean OBSTified = false;

  TreeNode root;
  int size;
  
  public static String[] node_array;
  public static int[] freq_array;
  
  int[][] c;
  int[][] r;
  
  int tr=0;
  
  public static int[] sum_freq_n;
  
  public BST() {
	  this.root=null;
	  this.size=0;
  }

  public int size() { 
	  return this.size;
  }
  
  public void insert(String key) { 
	  root=recursive_insert(root,key);
  }
  public TreeNode recursive_insert(TreeNode root, String key) {
	  if(root==null) {
		  root=new TreeNode(key);
		  this.size++;
		  root.freq=1;
		  return root;
	  }
	  else {
		  if(root.key.compareTo(key)<0) {
			  root.right=recursive_insert(root.right,key);
		  }
		  else if(root.key.compareTo(key)>0) {
			  root.left=recursive_insert(root.left,key);
		  }
		  else {
			  root.freq++;
		  }
		  return root;
	  }
  }
  
  public boolean find(String key) {
	  TreeNode tmp;
	  tmp=recursive_search(root,key);
	  
	  if(tmp==null) {
		  return false;
	  }
	  else {
		  return true;
	  }
  }
  public TreeNode recursive_search(TreeNode root, String key) {
	  if(root==null) {
		  return root;
	  }
	  else if(root.key.compareTo(key)==0) {
		  root.access_cnt++;
		  return root;
	  }
	  else {
		  if(root.key.compareTo(key)<0) {
			  root.access_cnt++;
			  return recursive_search(root.right,key);
		  }
		  else {
			  root.access_cnt++;
			  return recursive_search(root.left,key);
		  }
	  }
  }

  public int sumFreq() { 
	  return recursive_sumfreq(root);
  }
  public int recursive_sumfreq(TreeNode root) {
	  if(root==null) {
		  return 0;
	  }
	  else {
		  return root.freq+recursive_sumfreq(root.left)+recursive_sumfreq(root.right);
	  }
  }
  
  public int sumProbes() { 
	  return recursive_sumprobes(root);
  }
  public int recursive_sumprobes(TreeNode root) {
	  if(root==null) {
		  return 0;
	  }
	  else {
		  return root.access_cnt+recursive_sumprobes(root.left)+recursive_sumprobes(root.right);
	  }
  }
  
  public int sumWeightedPath() { 
	  return recursive_sumweightedpath(root,0);
  }
  public int recursive_sumweightedpath(TreeNode root,int level) {
	  if(root==null) {
		  return 0;
	  }
	  else {
		  return (level+1)*root.freq+recursive_sumweightedpath(root.left,level+1)+recursive_sumweightedpath(root.right,level+1);
	  }
  }
  
  public void resetCounters() { 
	  recursive_resetcounters(root);
  }
  public void recursive_resetcounters(TreeNode root) {
	  if(root==null) {
		  return;
	  }
	  else {
		  root.freq=0;
		  root.access_cnt=0;
		  recursive_resetcounters(root.left);
		  recursive_resetcounters(root.right);
	  }
  }

  public void nobst() { 
	  int s=this.size;
	  node_array=new String[s];
	  freq_array=new int[s];
	  sum_freq_n=new int[s+1];
	  
	  tree_to_array(root);
	  
	  root=null;
	  root=recursive_nobst(0,s-1);
	  
	  NOBSTified = true;
  }
  public void tree_to_array(TreeNode n) {
	  if(n==null) {
		  return;
	  }
	  tree_to_array(n.left);
	  node_array[tr]=n.key;
	  freq_array[tr]=n.freq;
	  sum_freq_n[tr+1]=sum_freq_n[tr]+n.freq;
	  tr++;
	  tree_to_array(n.right);
  }
  
  public TreeNode recursive_nobst(int x,int y) {
	  if(x>y) {
		  return null;
	  }
	  else if(x<y) {
		  int pivot=x;
		  int min_diff=1000000000;
		  for(int i=y ; i>=x ; i--) {
			  int left_sum=sum_freq_n[i]-sum_freq_n[x];
			  int right_sum=sum_freq_n[y+1]-sum_freq_n[i+1];
			  if(Math.abs(left_sum-right_sum)<=min_diff) {
				  min_diff=Math.abs(left_sum-right_sum);
				  pivot=i;
			  }
		  }
		  TreeNode n=new TreeNode(node_array[pivot]);
		  n.freq=freq_array[pivot];
		  n.left=recursive_nobst(x,pivot-1);
		  n.right=recursive_nobst(pivot+1,y);
		  return n;
	  }
	  else {
		  TreeNode n=new TreeNode(node_array[x]);
		  n.freq=freq_array[x];
		  return n;
	  }
  }
  public void obst() { 
	  
	  int s=this.size();
	  
	  c=new int[s+2][s+1];
	  r=new int[s+2][s+1];
	  
	  for(int l=s+1;l>=1;l--) {
		  for(int h=l-1;h<=s;h++) {
			  if(l>h) {
				  c[l][h]=0;
				  r[l][h]=0;
			  }
			  else if(l==h) {
				  c[l][h]=freq_array[l-1];
				  r[l][h]=l;
			  }
			  else {
				  int f_sum=sum_freq_n[h]-sum_freq_n[l-1];
				  int c_sum=100000000;
				  int r_min=0;
				  for(int k=r[l][h-1];k<=r[l+1][h];k++) {
					  int tmp=c[l][k-1]+c[k+1][h];
					  if(tmp<c_sum) {
						  c_sum=tmp;
						  r_min=k;
					  }
				  }
				  c[l][h]=f_sum+c_sum;
				  r[l][h]=r_min;
			  }
		  }
	  }
	  
	  root=null;
	  root=recursive_obst(1,s);
	  
	  OBSTified = true;
  }
  
  public TreeNode recursive_obst(int i, int j) {
	  if(r[i][j]==0) {
		  return null;
	  }
	  
	  TreeNode n=new TreeNode(node_array[r[i][j]-1]);
	  n.freq=freq_array[r[i][j]-1];
	  n.left=recursive_obst(i,r[i][j]-1);
	  n.right=recursive_obst(r[i][j]+1,j);
	  return n;
  }
  
  public void print() { 
	  recursive_print(root);
  }
  public void recursive_print(TreeNode root) {
	  if(root==null) {
		  return;
	  }
	  else {
		  recursive_print(root.left);
		  System.out.println(String.format("[%s:%d:%d]",root.key,root.freq,root.access_cnt));
		  recursive_print(root.right);
	  }
  }

}


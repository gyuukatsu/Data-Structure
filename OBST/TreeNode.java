
public class TreeNode {
	public String key;
	public TreeNode left;
	public TreeNode right;
	public int freq;
	public int access_cnt;
	public int height;
	
	public TreeNode(String key) {
		this.key=key;
		left=null;
		right=null;
		freq=0;
		access_cnt=0;
		height=1;
	}
}

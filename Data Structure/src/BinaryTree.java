import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class BinaryTree{
    class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private int data;
        TreeNode(int d){
            data = d;
            left = null;
            right = null;
        }
    }

    private TreeNode root;

    public BinaryTree(int[] arr) {
//        1. 业务逻辑： left = TreeNode(2^arr.getIndex(root) + 1)
        root = this.buildTree(0, arr);
    }
    public TreeNode buildTree(int index, int[] arr) {
        if (index >= arr.length) {
            return null;
        }

       TreeNode cur = new TreeNode(arr[index]);
       cur.left=buildTree(2 * index + 1, arr);
       cur.right=buildTree(2 * index + 2, arr);
       return cur;
    }

    //toString: 1->2->3
    public String toString() {
        String s = helper(root);

        return s.substring(0, s.length()-2);
    }
    private String helper(TreeNode root){
        String s = "";
        if (root == null) {
            return "";
        }
        System.out.println(root.data);
        s = s + String.valueOf(root.data) +"->";
        s = s+ this.helper(root.left);
        s = s+ this.helper(root.right);
        return s;
    }


    public static void main(String[] args) {
        int[] intArray = new int[20];
        intArray[0] =  1;
        BinaryTree tree = new BinaryTree(intArray);

        System.out.println(tree.toString());
    }

//    Imp
}

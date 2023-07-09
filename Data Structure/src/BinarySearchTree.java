import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Arrays;

public class BinarySearchTree {
    class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private int data;
        private TreeNode parent;

        TreeNode(int d) {
            data = d;
            left = null;
            right = null;
            parent = null;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        public void deleteSelf() {
            TreeNode parent = this.parent;

            if (this.isLeft()) {

                parent.left = null;
            } else {
                parent.right = null;
            }
        }

        private boolean isLeft() {
            TreeNode parent = this.parent;
            if (parent.left == this) {
                System.out.print(this.data);
                return true;
            }
            return false;
        }

        private boolean hasOnlyOneChild() {
            if (this.isLeaf()) {
                return false;
            }
            if (this.hasChildren()) {
                return false;
            }
            return true;
        }

        private boolean hasChildren() {
            return this.right != null && this.left != null;
        }

    }

    //toString // \\
    //   5
    //  /  \
    // 2    6
//parent pointer
    private TreeNode root;

    public BinarySearchTree(int[] arr) {
        Arrays.sort(arr);
        //assume array is in ascending order :)
        root = buildTree(0, arr.length - 1, arr);
    }

    private TreeNode buildTree(int start, int end, int[] arr) {
        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        TreeNode cur = new TreeNode(arr[mid]);

        cur.left = buildTree(start, mid - 1, arr);
        if (cur.left != null) {
            cur.left.parent = cur;
        }
        cur.right = buildTree(mid + 1, end, arr);
        if (cur.right != null) {
            cur.right.parent = cur;
        }
        return cur;
    }


    // left rotation
    private TreeNode leftRotate(TreeNode node) {
        TreeNode newRoot = node.right;


        node.right = newRoot.left;
        if (node.right != null) {
            node.right.parent = node;
        }

        newRoot.left = node;
        newRoot.parent = node.parent; // Set the parent of newRoot
        node.parent = newRoot;

        return newRoot;
//        node = newRoot;

    }

    //right rotation
    private TreeNode rightRotate(TreeNode node) {


        TreeNode newRoot = node.left;
        node.left = newRoot.right;
        if (node.left != null) {
            node.left.parent = node;
        }

        newRoot.right = node;
        newRoot.parent = node.parent; // Set the parent of newRoot
        node.parent = newRoot;

        return newRoot;
//        node = newRoot;
    }

    public BinarySearchTree add(int value) {
        if (root != null) {
            root = this.addHelper(value, root);
        } else {
            root = new TreeNode(value);
        }
        return this;
    }

    private TreeNode addHelper(int value, TreeNode node) {
        if (node == null) {
            return new TreeNode(value);
        }

        if (value > node.data) {
            node.right = this.addHelper(value, node.right);
            node.right.parent = node;
        }
        if (value < node.data) {
            node.left = this.addHelper(value, node.left);
            node.left.parent = node;
        }

        int leftDepth = this.depth(node.left);
        int rightDepth = this.depth(node.right);
        if (Math.abs(leftDepth - rightDepth) > 1) {
            if (leftDepth > rightDepth) {
                return this.rightRotate(node);
            } else {
                return this.leftRotate(node);
            }
        }
        return node;
    }

    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = depth(node.left);
        int right = depth(node.right);
        return Math.max(left, right) + 1;
    }
//delete one
    public String toString() {
        String s = helper(root);
        return s.substring(0, s.length() - 2);
    }

    private String helper(TreeNode root) {
        String s = "";
        if (root == null) {
            return "";
        }
        System.out.println(root.data);
        s = s + this.helper(root.left);
        s = s + String.valueOf(root.data) + "->";

        s = s + this.helper(root.right);
        return s;
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    //            strL = [[" "] * 2 ** ma for _ in range(ma)]
    private void printIndentForLevel(int level) {

        for (int i = (int) (Math.pow(2, level - 1)); i > 0; i--) {
            System.out.print(" ");
        }
    }

    private void printSpacingBetweenNodes(int level) {
        //spacing between nodes

        for (int i = (int) ((Math.pow(2, level - 1)) * 2) - 1; i > 0; i--) {
            System.out.print(" ");
        }
    }

    public void toStringNew() {
        int depth = this.maxDepth(root);
        ArrayList<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        int level = depth;
        while (level > 0) {

            nodes = this.printTree(nodes, level, depth);

            level = level - 1;
        }

    }

    private ArrayList<TreeNode> printTree(ArrayList<TreeNode> nodes, int level, int depth) {
//        We will print nodes layer by layer
        this.printIndentForLevel(level);


        ArrayList<TreeNode> nextLevelNodes = new ArrayList<>();
        for (TreeNode treeNode : nodes) {
            if (treeNode != null) {
                System.out.print(treeNode.data);
            } else {
                System.out.print(" ");
            }
            this.printSpacingBetweenNodes(level);

            if (treeNode != null) {
                if (treeNode.left != null) {
                    nextLevelNodes.add(treeNode.left);
                } else {
                    nextLevelNodes.add(null);
                }
                if (treeNode.right != null) {
                    nextLevelNodes.add(treeNode.right);
                } else {
                    nextLevelNodes.add(null);
                }
            }

        }
//
//        nodes = nextLevelNodes;

        System.out.println();
        return nextLevelNodes;
    }

    public TreeNode delete(int value) {
        TreeNode node = search(value);
        if (node == null) {
            return null; // Value not found in the tree
        }
        deleteNode(node);

        return root;
    }

    private void deleteNode(TreeNode node) {
        if (node.isLeaf()) {
            node.deleteSelf();

        } else if (node.hasOnlyOneChild()) {
            TreeNode parent = node.parent;
            if (node.left != null) {
                if (node.isLeft()) {
                    parent.left = node.left;
                    node.left.parent = parent;
                } else {
                    parent.right = node.left;
                    node.left.parent = parent;
                }
            } else {
                if (node.isLeft()) {
                    parent.left = node.right;
                    parent.left.parent = parent;
                } else {
                    parent.right = node.right;
                    parent.right.parent = parent;
                }
            }
        } else if (node.hasChildren()) {
            TreeNode replacement = this.successor(node);
            node.data = replacement.data;
            this.deleteNode(replacement);
            this.recovery(replacement);
        }
    }

//    complete Recovery
    private void recovery(TreeNode node) {
        if (node == null) {
            return;
        }

        while (node != null) {
            int leftDepth = this.depth(node.left);
            int rightDepth = this.depth(node.right);

            TreeNode newRoot = node;

            if (Math.abs(leftDepth - rightDepth) > 1) {
                if (leftDepth > rightDepth) {
                    newRoot = this.rightRotate(node);
                } else {
                    newRoot = this.leftRotate(node);
                }
            }

            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = newRoot;
                } else if (node == node.parent.right) {
                    node.parent.right = newRoot;
                }
            }

            node = node.parent;
        }
    }
    private TreeNode successor(TreeNode node) {

        TreeNode cur = node.right;

        while (cur.left != null) {
            cur = cur.left;
        }
        return cur;
    }

    public TreeNode search(int value) {
        return this.searchHelper(root, value);
    }
    private TreeNode searchHelper(TreeNode node, int value) {

        if (node == null) {
            return null;
        }
        if (node.data == value) {
            return node;
        }
        TreeNode left = this.searchHelper(node.left, value);
        TreeNode right = this.searchHelper(node.right, value);

        if (left != null) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;

    }
    public static void main(String[] args) {


//        System.out.println(tree.toString());
//        tree.leftRotate();
//        System.out.println(tree.toString());
//        tree.rightRotate();
        //System.out.println(tree.toString());
        int[] arr = {};
        BinarySearchTree tree = new BinarySearchTree(arr);
        tree.add(7).add(6).add(5).add(4).add(3).add(2).add(1);

        tree.toStringNew();
        tree.delete(5);
        tree.delete(4);
        tree.toStringNew();
    }

    //    Init a balanced binary search tree
    //    method: left rotation/right rotation
}

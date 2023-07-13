import java.util.ArrayList;
import java.util.Arrays;

public class RedBlackTree {
    private static final int RED = 1;
// class attribute
    private static final int BLACK = 0;
    private class Node {
        private Node left;
        private Node right;
//      data type cannot equal to null
        private Integer val;
        private Node parent;
        private int color;
        //public boolean isLeaf() {
//            return this.left.val == null && this.right.val == null;
//        }
//        Integer can be null, int cannot.
        Node(Integer color, Integer val) {
//             Without this, I am shadowing the instance variable with the local variable of the same name.
            this.color = color;
            this.val = val;
            left = null;
            right = null;
        }
//        return a red node with two nodes with value null, and add it to the tree
        public Node newRedNode(Integer val){
            Node newNode = new Node(RED, val);
//            System.out.print(newNode.val);
            Node nullNode1 = new Node(BLACK, null);
            Node nullNode2 = new Node(BLACK, null);
            nullNode2.parent = newNode;
            nullNode1.parent = newNode;
            newNode.left = nullNode1;
            newNode.right = nullNode2;
            return newNode;
        }

        private boolean isLeft() {
            Node parent = this.parent;
            if (parent.left == this) {
                //System.out.print(this.data);
                return true;
            }
            return false;
        }
        private boolean hasChildren() {
            return this.right.val != null && this.left.val != null;
        }

//        private boolean hasOnlyOneChild() {
//            if (this.isLeaf()) {
//                return false;
//            }
//            if (this.hasChildren()) {
//                return false;
//            }
//            return true;
//        }

    }
    private Node root;

    public RedBlackTree(int[] arr) {
        Arrays.sort(arr);
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            this.add(arr[i]);
        }
    }

    public void add(Integer value) {
        Node node = new Node(value, BLACK);
        node = node.newRedNode(value);
//        System.out.print(node.val);
        if (root == null) {
            root = node;
            return;
        }
        Node cur = root;
        Node parent = null;
        while (cur.val != null){
            if (cur.val > value) {
                parent = cur;
                cur = cur.left;
            } else if (cur.val < value) {
                parent = cur;
                cur = cur.right;
            }
        }
        if (value < parent.val) {
            parent.left = node;
        } else if (value > parent.val) {
            parent.right = node;
        }
        node.parent = parent;
        if (node.parent == null) {
            node.color = 0;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        this.insertionFixed(node);
    }
    private void insertionFixed(Node k) {
        Node u;
        while (k.parent.color == 1) {

            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        if (k.parent != null) {
                          k.parent.right =  this.rightRotate(k);
                        } else{
                            root = this.rightRotate(k);
                        }
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    if(k.parent.parent.parent != null) {
                        k.parent.parent.parent.right = this.leftRotate(k.parent.parent);
                    }else{
                        root = this.leftRotate(k.parent.parent);
                    }
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == 1) {
                    u.color = 0;
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        if (k.parent != null) {
                            k.parent.left = this.leftRotate(k);
                        }else {
                            root = this.leftRotate(k);
                        }
                    }
                    k.parent.color = 0;
                    k.parent.parent.color = 1;
                    if(k.parent.parent.parent != null) {
                        k.parent.parent.parent.left = this.rightRotate(k.parent.parent);
                    } else {
                        root = this.rightRotate(k.parent.parent);
                    }
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 0;

    }
    private Node addHelper() {
        return null;
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
    private int maxDepth(Node root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public void toStringNew() {
        int depth = this.maxDepth(root);
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(root);
        int level = depth;
        while (level > 0) {

            nodes = this.printTree(nodes, level, depth);

            level = level - 1;
        }

    }

    private ArrayList<Node> printTree(ArrayList<Node> nodes, int level, int depth) {
//        We will print nodes layer by layer
        this.printIndentForLevel(level);


        ArrayList<Node> nextLevelNodes = new ArrayList<>();
        for (Node treeNode : nodes) {
            if (treeNode != null) {
                if (treeNode.val == null) {
                    System.out.print("null");
                } else {
                    System.out.print(treeNode.val);
                    System.out.print(" " + treeNode.color);
                }
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

        System.out.println();
        return nextLevelNodes;
    }

    private Node leftRotate(Node node) {
        Node newRoot = node.right;


        node.right = newRoot.left;
        newRoot.left.parent = node;


        if (node.right.val != null) {
            node.right.parent = node;
        }
        newRoot.parent = node.parent;
        newRoot.left = node;
        //newRoot.parent = node.parent; // Set the parent of newRoot
        node.parent = newRoot;

        return newRoot;
    }

    //right rotation
    private Node rightRotate(Node node) {


        Node newRoot = node.left;
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
    public void preorder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val + " ");
        this.preorder(node.left);
        this.preorder(node.right);
    }
    public static void main(String[] args) {

        int[] arr = {};
        RedBlackTree tree = new RedBlackTree(arr);
        tree.add(7);
        tree.add(8);
        tree.add(5);
        tree.add(9);
        tree.add(4);
        tree.add(3);
        tree.add(2);
        tree.add(1);

        tree.toStringNew();
        //tree.add(10);

        //tree.preorder(tree.root);
    }

}

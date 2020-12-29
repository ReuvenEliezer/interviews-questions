// Java program to find height of tree
//https://www.geeksforgeeks.org/write-a-c-program-to-find-the-maximum-depth-or-height-of-a-tree/
// A binary tree node 
class Node {
    int data;
    Node left, right;

    Node(int item) {
        data = item;
        left = right = null;
    }
}

class BinaryTree {
    Node root;

    int maxDepth(Node node) {
        if (node == null)
            return 0;
        return Math.max(maxDepth(node.left), maxDepth(node.right)) + 1;
    }

    static void inOrder(Node node) {
        if (node == null)
            return;
        System.out.println(node.data + " ");

        inOrder(node.left);
        inOrder(node.right);
    }

    /* Driver program to test above functions */
    public static void main(String[] args) {

/**
 * https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
 Depth First Traversals:
 (a) Inorder (Left, Root, Right) : 4 2 5 1 3
 (b) Preorder (Root, Left, Right) : 1 2 4 5 3
 (c) Postorder (Left, Right, Root) : 4 5 2 3 1
 */
        BinaryTree tree = new BinaryTree();

        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.println("Height of tree is : " +
                tree.maxDepth(tree.root));

        System.out.println("Sum Values of tree is : " +
                tree.sumValues(tree.root));


        System.out.println("Preorder traversal of binary tree is ");
        tree.printPreorder(tree.root);

        System.out.println("Inorder traversal of binary tree is ");
        tree.printInorder(tree.root);

        System.out.println("Postorder traversal of binary tree is ");
        tree.printPostorder(tree.root);

        System.out.println("Before mirror:");
        inOrder(tree.root);
        mirror(tree.root);
        System.out.println("After mirror:");
        inOrder(tree.root);

    }

    static Node mirror(Node node) {
        if (node == null)
            return null;

        /* do the subtrees */
        Node left = mirror(node.left);
        Node right = mirror(node.right);

        /* swap the left and right pointers */
        node.left = right;
        node.right = left;

        return node;
    }

    private int sumValues(Node root) {
        if (root == null)
            return 0;
        System.out.println(root.data);
        return root.data + sumValues(root.left) + sumValues(root.right);
    }

    void printPostorder(Node node) {
        if (node == null)
            return;

        // first recur on left subtree
        printPostorder(node.left);

        // then recur on right subtree
        printPostorder(node.right);

        // now deal with the node
        System.out.println(node.data + " ");
    }

    /* Given a binary tree, print its nodes in inorder*/
    void printInorder(Node node) {
        if (node == null)
            return;

        /* first recur on left child */
        printInorder(node.left);

        /* then print the data of node */
        System.out.println(node.data + " ");

        /* now recur on right child */
        printInorder(node.right);
    }

    /* Given a binary tree, print its nodes in preorder*/
    void printPreorder(Node node) {
        if (node == null)
            return;

        /* first print data of node */
        System.out.println(node.data + " ");

        /* then recur on left sutree */
        printPreorder(node.left);

        /* now recur on right subtree */
        printPreorder(node.right);
    }

}
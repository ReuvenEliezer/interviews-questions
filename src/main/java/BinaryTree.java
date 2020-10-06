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
        System.out.print(node.data + " ");

        inOrder(node.left);
        inOrder(node.right);
    }

    /* Driver program to test above functions */
    public static void main(String[] args) {
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

}
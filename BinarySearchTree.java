/**
 * 
 * A Binary Search Tree (BST) implementation that stores nodes, an in-order algorithim is used in order to access the data, which means
 * that the nodes are traversed from left-root-right. 
 * 
 * @author Payton Burke
 * @version (5/17/2026)
 * 
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class BinarySearchTree {
 
    static class Node {
        int value;
        Node left, right;
 
        Node(int value) {
            this.value = value;
        }
    }
    /**
     * 
     * BST class; have the option to insert, search, and delete a node in either an in-order, pre-order, or post-order traversal of the tree
     */
    static class BST {
        private Node root;
 
        //this function inserts a node into the BST 
        public void insert(int value) {
            root = insertRec(root, value);
        }
 
        private Node insertRec(Node node, int value) {
            if (node == null) return new Node(value);
            if (value < node.value)       node.left  = insertRec(node.left,  value);
            else if (value > node.value)  node.right = insertRec(node.right, value);
            else System.out.println("  ⚠  " + value + " already exists – duplicates are skipped.");
            return node;
        }
 
        // this function searches for a node in the BST
        public boolean search(int value) {
            return searchRec(root, value);
        }
 
        private boolean searchRec(Node node, int value) {
            if (node == null)           return false;
            if (value == node.value)    return true;
            return value < node.value
                    ? searchRec(node.left, value)
                    : searchRec(node.right, value);
        }
 
        //this function deletes a node from the BST 
        public void delete(int value) {
            root = deleteRec(root, value);
        }
 
        private Node deleteRec(Node node, int value) {
            if (node == null) { System.out.println("  ⚠  " + value + " not found."); return null; }
            if (value < node.value) {
                node.left = deleteRec(node.left, value);
            } else if (value > node.value) {
                node.right = deleteRec(node.right, value);
            } else {
                // Node to delete found
                if (node.left == null)  return node.right;
                if (node.right == null) return node.left;
                // Two children: replace with in-order successor
                node.value = minValue(node.right);
                node.right = deleteRec(node.right, node.value);
            }
            return node;
        }
 
        private int minValue(Node node) {
            while (node.left != null) node = node.left;
            return node.value;
        }
 
 
        // in-order traversal
        public List<Integer> inOrder() {
            List<Integer> result = new ArrayList<>();
            inOrderRec(root, result);
            return result;
        }
 
        private void inOrderRec(Node node, List<Integer> result) {
            if (node == null) return;
            inOrderRec(node.left, result);
            result.add(node.value);
            inOrderRec(node.right, result);
        }
 
        // pre-order traversal 
        public List<Integer> preOrder() {
            List<Integer> result = new ArrayList<>();
            preOrderRec(root, result);
            return result;
        }
 
        private void preOrderRec(Node node, List<Integer> result) {
            if (node == null) return;
            result.add(node.value);
            preOrderRec(node.left, result);
            preOrderRec(node.right, result);
        }
 
        //post-order traversal
        public List<Integer> postOrder() {
            List<Integer> result = new ArrayList<>();
            postOrderRec(root, result);
            return result;
        }
 
        private void postOrderRec(Node node, List<Integer> result) {
            if (node == null) return;
            postOrderRec(node.left, result);
            postOrderRec(node.right, result);
            result.add(node.value);
        }
 
        //prints a simple ascii rep of the tree
        public void printTree() {
            if (root == null) { System.out.println("  (empty tree)"); return; }
            printTreeRec(root, "", true);
        }
 
        private void printTreeRec(Node node, String prefix, boolean isLeft) {
            if (node == null) return;
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.value);
            printTreeRec(node.left,  prefix + (isLeft ? "│   " : "    "), true);
            printTreeRec(node.right, prefix + (isLeft ? "│   " : "    "), false);
        }
 
        public boolean isEmpty() { return root == null; }
    }
 
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST bst = new BST();
 
        
        int[] initialValues = {50, 30, 70, 20, 40, 60, 80};
        System.out.println("===========================================");
        System.out.println("       Binary Search Tree Program");
        System.out.println("===========================================");
        System.out.print("Seeding tree with initial values: ");
        for (int v : initialValues) {
            bst.insert(v);
            System.out.print(v + " ");
        }
        System.out.println("\n");
 
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
 
            if (!scanner.hasNextInt()) {
                System.out.println("  ✗  Invalid input. Please enter a number.\n");
                scanner.next();
                continue;
            }
 
            int choice = scanner.nextInt();
            System.out.println();
 
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter value to insert: ");
                    if (scanner.hasNextInt()) {
                        int val = scanner.nextInt();
                        bst.insert(val);
                        System.out.println("  ✔  " + val + " inserted.");
                    } else {
                        System.out.println("  ✗  Invalid number.");
                        scanner.next();
                    }
                }
                case 2 -> {
                    System.out.print("Enter value to search: ");
                    if (scanner.hasNextInt()) {
                        int val = scanner.nextInt();
                        System.out.println(bst.search(val)
                                ? "  ✔  " + val + " found in the tree."
                                : "  ✗  " + val + " not found.");
                    } else {
                        System.out.println("  ✗  Invalid number.");
                        scanner.next();
                    }
                }
                case 3 -> {
                    System.out.print("Enter value to delete: ");
                    if (scanner.hasNextInt()) {
                        int val = scanner.nextInt();
                        bst.delete(val);
                        System.out.println("  ✔  " + val + " deleted (if it existed).");
                    } else {
                        System.out.println("  ✗  Invalid number.");
                        scanner.next();
                    }
                }
                case 4 -> System.out.println("In-Order   (sorted): " + bst.inOrder());
                case 5 -> System.out.println("Pre-Order           : " + bst.preOrder());
                case 6 -> System.out.println("Post-Order          : " + bst.postOrder());
                case 7 -> {
                    System.out.println("Tree structure:");
                    bst.printTree();
                }
                case 8 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("  ✗  Unknown option. Please choose 1–8.");
            }
            System.out.println();
        }
        scanner.close();
    }
 
    private static void printMenu() {
        System.out.println("-------------------------------------------");
        System.out.println(" 1. Insert a number!");
        System.out.println(" 2. Search for a number!");
        System.out.println(" 3. Delete a number!");
        System.out.println(" 4. Display In-Order   (sorted ascending)");
        System.out.println(" 5. Display Pre-Order");
        System.out.println(" 6. Display Post-Order");
        System.out.println(" 7. Print tree structure");
        System.out.println(" 8. Exit the program");
        System.out.println("-------------------------------------------");
    }
}
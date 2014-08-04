package random.algos;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * Consider a binary search tree T which is initially empty. Also, consider the first N positive integers {1, 2, 3, 4, 5, ....., N} and its permutation P {a1, a2, ..., aN}.

 If we start adding these numbers to the binary search tree T, starting from a1, continuing with a2, ... (and so on) ..., ending with aN. After every addition we ask you to output the sum of distances between every pair of T's nodes.

 Input Format
 The first line of the input consists of the single integer N, the size of the list.
 The second line of the input contains N single space separated numbers the permutation a1, a2, ..., aN itself.

 Constraints
 1 ≤ N ≤ 250000

 Output Format
 Output N lines.
 On the ith line output the sum of distances between every pair of nodes after adding the first i numbers from the permutation to the binary search tree T

 Sample Input #00

 8
 4 7 3 1 8 2 6 5
 Sample Output #00

 0
 1
 4
 10
 20
 35
 52
 76
 Explanation #00

 After adding the first element, the distance is 0 as there is only 1 element

 4
 After adding the second element, the distance between 2 nodes is 1.

 4
 \
 7
 After adding the third element, the distance between every pair of elements is 2+1+1=4

 4
 / \
 3   7
 After adding the fourth element, the distance between every pair of elements is 3 + 2 + 1 + 2 + 1 + 1 = 10

 4
 / \
 3   7
 /
 1
 After adding the fifth element, the distance between every pair of elements is 4 + 3 + 2 + 1 + 3 + 2 + 1 + 2 + 1 + 1 = 20

 4
 / \
 3   7
 /     \
 1       8
 After adding the sixth element, the distance between every pair of elements is 5 + 4 + 3 + 2 + 1 + 4 + 3 + 2 + 1 + 3 + 2 + 1 + 2 + 1 + 1 = 35

 4
 / \
 3   7
 /     \
 1       8
 \
 2
 After adding the seventh element, the distance between every pair of elements is 5+5+4+3+2+1+4+4+3+2+1+3+3+2+1+2+2+1+1+1+2=52

 4
 / \
 3   7
 /   / \
 1   6   8
 \
 2
 After adding the final element, the distance between every pair of elements is 6+5+5+4+3+2+1+5+4+4+3+2+1+4+3+3+2+1+3+2+2+1+2+1+1+2+1+3=76

 4
 /   \
 3      7
 /      /   \
 1      6     8
 \    /
 2  5
 */
class BinarySearchTree {
    
    public static class MyQueue {
        public static class QNode {
            int data;
            QNode next = null;
            
            public QNode(int val) { 
                this.data = val;
            }
        }
        QNode front, back;
        
        public void enqueue(int val) {
            if (front == null) {
                back = new QNode(val);
                front = back;
            }
            else {
                back.next = new QNode(val);
                back = back.next;
            }
        }
        
        public QNode dequeue() {
            if (front == null) return null;
            QNode toReturn = front;
            front = front.next;
            return toReturn;
        }
        
        public boolean isEmpty() {
            return front == null;
        }
    }

    public static class BST {
        public static class BSTNode {
            public BSTNode left = null;
            public BSTNode right = null;
            public int data;

            public BSTNode(int val) {
                this.data = val;
            }
        }

        private BSTNode root;

        public void insert(int val) {
            root = insert(root, val);
        }

        private BSTNode insert(BSTNode root, int val) {
            //if (node == null) throw new IllegalArgumentException("Error: Cannot insert null value.");
            if (root == null) {
                return new BSTNode(val);
            }
            else {
                if (val <= root.data) root.left = insert (root.left, val);
                else root.right = insert (root.right, val);
                return root;
            }
        }

        private void syso(String msg) {
            System.out.println(msg);
        }

        public void bfs() {
            if (root == null) {
                return;
            }
            Map<Integer, Integer> visited = new HashMap<Integer, Integer>();

            Queue<BSTNode> queue = new LinkedList<BSTNode>();
            BSTNode sentinal = new BSTNode(-9999);
            queue.add(root);
            queue.add(sentinal);
            visited.put(root.data, 1);

            while(!queue.isEmpty()) {
                BSTNode node = (BSTNode)queue.remove();

                // print current
                if(node != sentinal) {
                    System.out.print(node.data + " ");
                }
                if(node == sentinal && !queue.isEmpty()) {
                    System.out.println();
                    queue.add(sentinal);
                }

                // push children
                if(node.left != null && !visited.containsKey(node.left.data)) {
                    queue.add(node.left);
                    visited.put(node.left.data, 1);
                }
                if(node.right != null && !visited.containsKey(node.right.data)) {
                    queue.add(node.right);
                    visited.put(node.right.data, 1);
                }
            }
        }
    }

    private ArrayList<BST.BSTNode> getAllNodes(BST.BSTNode root) {
        ArrayList<BST.BSTNode> nodes = new ArrayList<BST.BSTNode>();
        if (root == null) return nodes;
        nodes.addAll(getAllNodes(root.left));
        nodes.add(root);
        nodes.addAll(getAllNodes(root.right));
        return nodes;
    }

    private BST.BSTNode search(BST.BSTNode rootNode, int val) {
        if (rootNode == null) return null;
        if (rootNode.data == val) return rootNode;
        else if (val < rootNode.data) {
            // search in left subtree
            search(rootNode.left, val);
        }
        else if (val > rootNode.data) {
            // search in right subtree
            search(rootNode.right, val);
        }
        return null;
    }

    private boolean contains(BST.BSTNode rootNode, int val) {
        return search(rootNode, val) != null;
    }

    private int levelOf(BST.BSTNode rootNode, BST.BSTNode node, int curLevel) {
        // base case, null tree. level = -1
        if (rootNode == null) return -1;

        // accumulate if it is a non-null tree
        int val = node.data;

        // if data at current root is equal to val, val's level is the current level
        if (rootNode.data == val) return curLevel;

        // else traverse sub trees
        int leftLevel = levelOf(rootNode.left, node, curLevel + 1);
        return leftLevel != -1 ? leftLevel : levelOf(rootNode.right, node, curLevel + 1);
    }

    private BST.BSTNode lca(BST.BSTNode root, BST.BSTNode node1, BST.BSTNode node2) {
        // if null tree, return null
        if (root == null) return null;

        // if either of the two nodes is root, then root is the lca
        if (root == node1 || root == node2) return root;

        // find the lca for the two nodes in the left subtree
        BST.BSTNode left = lca(root.left, node1, node2);

        // find the lca for the two nodes in the right subtree
        BST.BSTNode right = lca(root.right, node1, node2);

        // if both left and right lca's are non-null, then the two nodes are on either side of root
        // hence, root is the lca
        if (left != null && right != null) {
            return root;
        }

        // if the left lca is non-null, but right lca is null, then left lca is the lca
        // if the right lca is non-null, but left lca is null, then right lca is the lca
        return left != null ? left : right;
    }

    private int findDistance(BST.BSTNode root, BST.BSTNode node1, BST.BSTNode node2) {
        // get levels of the two nodes
        int level1 = levelOf(root, node1, 0);
        int level2 = levelOf(root, node2, 0);

        // get LCA
        BST.BSTNode lca = lca(root, node1, node2);

        // get level of LCA
        int lcaLevel = levelOf(root, lca, 0);

        // get distance
        int distance = level1 + level2 - 2*lcaLevel;

        return distance;
    }

    public int getCumulativeDistance(BST.BSTNode root) {
        int cumulative = 0;

        // get all nodes
        List<BST.BSTNode> nodes = getAllNodes(root);
        List<BST.BSTNode> copy = new ArrayList<BST.BSTNode>(nodes);
        for (BST.BSTNode node : nodes) {
            copy.remove(node);
            for (BST.BSTNode otherNode : copy) {
                cumulative += findDistance(root, node, otherNode);
            }
        }

        return cumulative;
    }

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        int numNodes = in.nextInt();
        BST bst = new BST();
        BinarySearchTree soln = new BinarySearchTree();
        for (int i = 0; i < numNodes; i++) {
            int node = in.nextInt();
            bst.insert(node);
            System.out.println(soln.getCumulativeDistance(bst.root));
        }
        in.close();
        // bst.bfs();
    }
}
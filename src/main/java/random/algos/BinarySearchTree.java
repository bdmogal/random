package random.algos;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

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
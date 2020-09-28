/*
 * Created by:
 * 
 * Amit Elyasi
 * ID: 316291434
 * Username: amitelyasi
 * 
 * Oren Levy
 * ID: 208410183
 * Username: orenlevy
 * 
 */
/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with distinct integer keys and info
 *
 */

public class AVLTree {

	private IAVLNode root;
	private int size;

	/**
	 * public AVLTree()
	 *
	 * AVLTree constructor. Returns new AVLTree instance
	 * 
	 */
	public AVLTree() {
		this.root = null;
		this.size = 0;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 * O(1)
	 */
	public boolean empty() {
		return (this.size() == 0); // there are no nodes in this tree.
	}

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree otherwise,
	 * returns null
	 * 
	 * O(logn)
	 */
	public String search(int k) {
		IAVLNode node = this.searchNode(k);
		if (node == null) {
			return null;
		}
		return node.getValue();
	}

	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the AVL tree. the tree must remain
	 * valid (keep its invariants). returns the number of rebalancing operations, or
	 * 0 if no rebalancing operations were necessary. returns -1 if an item with key
	 * k already exists in the tree.
	 *
	 * O(logn)
	 */
	public int insert(int k, String i) {
		IAVLNode newNode = new AVLNode(k, i);
		IAVLNode node = this.getRoot();

		if (node == null) { // newNode is the first node in the tree.
			this.root = (newNode);
			this.size++;
			return 0;
		}

		while (true) { // look for the right place for newNodekeys. insert if found. return -1 if
						// already exist.
			if (node.getKey() > k) {
				if (node.getLeft() == null) {
					node.setLeft(newNode);
					this.size++;
					newNode.setParent(node);
					node = node.getLeft();
					break;
				} else {
					node = node.getLeft();
				}
			} else if (node.getKey() < k) {
				if (node.getRight() == null) {
					node.setRight(newNode);
					this.size++;
					newNode.setParent(node);
					node.getRight();
					break;
				} else {
					node = node.getRight();
				}
			} else {
				return -1;
			}
		}

		// fix the tree
		this.updateHeightAndSize(node);
		node = newNode.getParent();
		while (!((AVLNode) node).isCriminal() && node.getParent() != null) { // look for criminals
			node = node.getParent();
		}

		if (((AVLNode) node).isCriminal()) {
			return this.rebalance(node); // rebalance node, return number of rotation.

		} else {
			return 0;
		}
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of rebalancing
	 * operations, or 0 if no rebalancing operations were needed. returns -1 if an
	 * item with key k was not found in the tree.
	 *
	 * O(logn)
	 */
	public int delete(int k) {

		IAVLNode node = this.searchNode(k);

		if (node == null) {
			return -1;
		}

		IAVLNode nodesPredecessor = this.predecessor(node);

		if (node.getParent() != null) { // node has a parent

			if (node.getRight() != null) { // node has right son

				if (node.getLeft() != null) { // node has left son

					this.switchPositions(node, nodesPredecessor); // nodesPredecessor can't be null because node has
																	// left son

					if (this.root == node) {
						this.root = (nodesPredecessor);
					}
					if (node.getLeft() != null) {
						node.getLeft().setParent(node.getParent());

						if (node.getParent() != null) {
							if (node.getParent().getLeft() == node) {
								node.getParent().setLeft(node.getLeft());

							} else {
								node.getParent().setRight(node.getLeft());
							}
						} else {
							node.getParent().setLeft(null);
						}
					} else {

						if (node.getParent() != null) {
							if (node.getParent().getLeft() == node) {
								node.getParent().setLeft(null);

							} else {
								node.getParent().setRight(null);
							}
						}
					}
				} else { // node has only right son
					node.getRight().setParent(node.getParent());

					if (node.getParent().getLeft() == node) { // node is the left son of his parent
						node.getParent().setLeft(node.getRight());

					} else { // node is the right son of his parent
						node.getParent().setRight(node.getRight());
					}
				}
			} else { // node has no right son

				if (node.getLeft() != null) { // node has only left son
					node.getLeft().setParent(node.getParent());

					if (node.getParent().getLeft() == node) {// node is the left son of his parent
						node.getParent().setLeft(node.getLeft());

					} else { // node is the right son of his parent
						node.getParent().setRight(node.getLeft());
					}
				} else { // node has no sons
					if (node.getParent().getLeft() == node) {// node is the left son of his parent
						node.getParent().setLeft(null);

					} else { // node is the right son of his parent
						node.getParent().setRight(null);
					}
				}
			}
		} else { // node has no parent

			if (node.getRight() != null) { // node has right son

				if (node.getLeft() != null) { // node has left son

					this.switchPositions(node, nodesPredecessor); // nodesPredecessor can't be null because node has
																	// left son
					if (this.root == node) {
						this.root = (nodesPredecessor);
					}
					if (node.getLeft() != null) {
						node.getLeft().setParent(node.getParent());
						if (node.getParent() != null) {
							if (node.getParent().getLeft() == node) {
								node.getParent().setLeft(node.getLeft());

							} else {
								node.getParent().setRight(node.getLeft());
							}
						} else {
							node.getParent().setLeft(null);
						}
					} else {
						if (node.getParent() != null) {
							if (node.getParent().getLeft() == node) {
								node.getParent().setLeft(null);

							} else {
								node.getParent().setRight(null);
							}
						}
					}

				} else { // node has only right son
					node.getRight().setParent(node.getParent()); // node.getParent()==null
				}
			} else { // node has no sons or a parent -> node is the only node in the tree
				this.root = null;
			}
		}
		this.size--;

		IAVLNode temp = node;
		this.updateHeightAndSize(node);

		node = temp;
		int count = 0;
		while (node.getParent() != null) {
			node = node.getParent();
			if (((AVLNode) node).isCriminal()) {
				count += rebalance(node);
			}
		}
		return count;
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 * 
	 * O(logn)
	 */
	public String min() {
		if (this.empty()) {
			return null;
		}
		return this.minNode().getValue();
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 * 
	 * O(logn)
	 */
	public String max() {
		if (this.empty()) {
			return null;
		}
		return this.maxNode().getValue();
	}

	/**
	 * public int[] keysToArray() Returns an array of integers which contains all
	 * keys of the nodes in tree, sorted from smallest the largest, or an empty
	 * array of a size 0 if the tree is empty. Time complexity of O(n), because the
	 * time complexity of keysToArrayRec is O(n).
	 */
	public int[] keysToArray() {
		int[] keysArr = new int[this.size()];
		if (this.size() == 0) {
			return (keysArr);
		}
		return (keysToArrayRec(keysArr, 0, this.root, null));
	}

	/**
	 * private int[] keysToArrayRec(String[] arr , int i, IAVLNode curr, IAVLNode
	 * prev)
	 *
	 * Recursive method, (follows in-order travel) Base case is when arr is full.
	 * Add in index i to arr the key of the curr node if there is no node with a key
	 * smaller then curr's (that we didn't already add to arr), then do the
	 * recursion again, until we get to the next smallest key in the tree. Time
	 * complexity of O(n).
	 */

	private int[] keysToArrayRec(int[] arr, int i, IAVLNode curr, IAVLNode prev) {
		if (i == this.size()) {
			return arr;
		}

		if (prev == null) { // curr is the root
			if (curr.getLeft() != null) {
				return (keysToArrayRec(arr, i, curr.getLeft(), curr));
			} else {
				arr[i] = curr.getKey();
				i++;
				if (curr.getRight() != null) {
					return (keysToArrayRec(arr, i, curr.getRight(), curr));
				} else { // finished
					return (arr);
				}
			}
		} else { // curr is not the root
			if (curr.getParent() == prev) {// prev is curr's parent
				if (prev.getLeft() == curr) {// curr is the left son
					if (curr.getLeft() != null) {// curr has a left son
						return (keysToArrayRec(arr, i, curr.getLeft(), curr));
					} else {
						arr[i] = curr.getKey();
						i++;
						if (curr.getRight() != null) {
							return (keysToArrayRec(arr, i, curr.getRight(), curr));
						} else {
							return (keysToArrayRec(arr, i, curr.getParent(), curr));
						}
					}
				} else {// curr is the right son of his parent
					if (curr.getLeft() != null) {
						return (keysToArrayRec(arr, i, curr.getLeft(), curr));
					} else {
						arr[i] = curr.getKey();
						i++;
						if (curr.getRight() != null) {
							return (keysToArrayRec(arr, i, curr.getRight(), curr));
						} else {
							return (keysToArrayRec(arr, i, curr.getParent(), curr));
						}
					}
				}
			}
			if (curr.getLeft() == prev) {//prev is currs' left son
				arr[i] = curr.getKey();
				i++;
				if (curr.getRight() != null) {
					return (keysToArrayRec(arr, i, curr.getRight(), curr));
				} else {
					return (keysToArrayRec(arr, i, curr.getParent(), curr));
				}
			} else {// prev is curr's right son
				return (keysToArrayRec(arr, i, curr.getParent(), curr));
			}
		}
	}

	/**
	 * public int[] infoToArray()
	 *
	 * Returns an array of strings which contains all values(info) of the nodes in
	 * tree, sorted using the keys of the nodes from smallest the largest, or an
	 * empty array(size 0) if the tree is empty. Time complexity of O(n), because
	 * the time complexity of infoToArrayRec is O(n).
	 */
	public String[] infoToArray() {
		String[] arr = new String[this.size()];
		if (this.size() == 0) {
			return (arr);
		}
		return infoToArrayRec(arr, 0, this.root, null);
	}

	/**
	 * private String[] infoToArrayRec(String[] arr , int i, IAVLNode curr, IAVLNode
	 * prev)
	 *
	 * Recursive method, (follows in-order travel, using the key ) Base case is when
	 * arr is full. Add in index i to arr the info of the curr node if there is no
	 * node with a key smaller then curr's (that we didn't already add to arr), then
	 * do the recursion again, until we get to the next smallest key in the tree.
	 * Time complexity of O(n).
	 */

	private String[] infoToArrayRec(String[] arr, int i, IAVLNode curr, IAVLNode prev) {
		if (i == this.size()) {
			return arr;
		}

		if (prev == null) { //curr is the root
			if (curr.getLeft() != null) {
				return (infoToArrayRec(arr, i, curr.getLeft(), curr));
			} else {
				arr[i] = curr.getValue();
				i++;
				if (curr.getRight() != null) {
					return (infoToArrayRec(arr, i, curr.getRight(), curr));
				} else { //finished
					return (arr);
				}
			}
		} else { //curr is not the root
			if (curr.getParent() == prev) { // prev is curr's parent
				if (prev.getLeft() == curr) {
					if (curr.getLeft() != null) {
						return (infoToArrayRec(arr, i, curr.getLeft(), curr));
					} else {
						arr[i] = curr.getValue();
						i++;
						if (curr.getRight() != null) {
							return (infoToArrayRec(arr, i, curr.getRight(), curr));
						} else {
							return (infoToArrayRec(arr, i, curr.getParent(), curr));
						}
					}
				} else {
					if (curr.getLeft() != null) {
						return (infoToArrayRec(arr, i, curr.getLeft(), curr));
					} else {
						arr[i] = curr.getValue();
						i++;
						if (curr.getRight() != null) {
							return (infoToArrayRec(arr, i, curr.getRight(), curr));
						} else {
							return (infoToArrayRec(arr, i, curr.getParent(), curr));
						}
					}
				}
			}
			if (curr.getLeft() == prev) {
				arr[i] = curr.getValue();
				i++;
				if (curr.getRight() != null) {
					return (infoToArrayRec(arr, i, curr.getRight(), curr));
				} else {
					return (infoToArrayRec(arr, i, curr.getParent(), curr));
				}
			} else {
				return (infoToArrayRec(arr, i, curr.getParent(), curr));
			}
		}
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 * 
	 * O(1)
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 *
	 * precondition: none postcondition: none
	 * 
	 * O(1)
	 */
	public IAVLNode getRoot() {
		return this.root;
	}

	// help methods

	/**
	 * public IAVLNode searchNode(int k) Returns the node with key k from this tree
	 * if exist in the tree, returns null otherwise.
	 * 
	 * @param k
	 * @return node
	 * 
	 *         O(logn)
	 */
	public IAVLNode searchNode(int k) {
		if (this.root == null) {
			return null;
		}
		IAVLNode node = this.root;
		while (node.getKey() != k) {
			if (node.getKey() > k) {
				if (node.getLeft() == null) {
					return null;
				}
				node = node.getLeft();

			} else {
				if (node.getRight() == null) {
					return null;
				}
				node = node.getRight();
			}
		}
		return node;
	}

	/**
	 * public String minNode()
	 *
	 * Returns the node with the smallest key in the tree, or null if the tree is
	 * empty
	 * 
	 * O(logn)
	 * 
	 */
	public IAVLNode minNode() {
		if (this.root == null) {
			return null;
		}
		IAVLNode node = this.root;

		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * public String maxNode()
	 *
	 * Returns the node with the largest key in the tree, or null if the tree is
	 * empty
	 * 
	 * O(logn)
	 */
	public IAVLNode maxNode() {
		if (this.root == null) {
			return null;
		}

		IAVLNode node = this.root;

		while (node.getRight() != null) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * public IAVLNode predecessor(IAVLNode node)
	 * 
	 * Returns the in-order predecessor of node
	 * 
	 * O(logn)
	 * 
	 */
	public IAVLNode predecessor(IAVLNode node) {
		if (node.getLeft() == null) {
			if (node.getParent() != null) {
				if (node.getParent().getLeft() == node) {
					return null;
				} else {
					return node.getParent();
				}
			} else {
				return null;
			}
		}
		node = node.getLeft();
		while (node.getRight() != null) {
			node = node.getRight();
		}
		return node;
	}

	/**
	 * public IAVLNode successor(IAVLNode node)
	 * 
	 * Returns the in-order successor of node
	 * 
	 * O(logn)
	 * 
	 */
	public IAVLNode successor(IAVLNode node) {
		if (node.getRight() == null) {
			if (node.getParent() != null) {
				if (node.getParent().getRight() == node) {
					return null;
				} else {
					return node.getParent();
				}
			} else {
				return null;
			}
		}
		node = node.getRight();
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	/**
	 * private void switchPositions(IAVLNode node1, IAVLNode node2)
	 * 
	 * switch the positions of node1 and node2
	 * 
	 * @param node1, node2
	 * 
	 *               O(1)
	 */
	private void switchPositions(IAVLNode node1, IAVLNode node2) {
		IAVLNode temp = new AVLNode(0, "");

		if (node1.getParent() != null) {
			if (node1.getParent().getLeft() == node1) {
				node1.getParent().setLeft(temp);
			} else {
				node1.getParent().setRight(temp);
			}
		}
		if (node1.getLeft() != null) {
			node1.getLeft().setParent(temp);
		}
		if (node1.getRight() != null) {
			node1.getRight().setParent(temp);
		}
		temp.setParent(node1.getParent());
		temp.setLeft(node1.getLeft());
		temp.setRight(node1.getRight());
		temp.setHeight(node1.getHeight());

		if (node2.getParent() != null) {
			if (node2.getParent().getLeft() == node2) {
				node2.getParent().setLeft(node1);
			} else {
				node2.getParent().setRight(node1);
			}
		}
		if (node2.getLeft() != null) {
			node2.getLeft().setParent(node1);
		}
		if (node2.getRight() != null) {
			node1.getRight().setParent(node1);
		}
		node1.setParent(node2.getParent());
		node1.setLeft(node2.getLeft());
		node1.setRight(node2.getRight());
		node1.setHeight(node2.getHeight());

		if (temp.getParent() != null) {
			if (temp.getParent().getLeft() == temp) {
				temp.getParent().setLeft(node2);
			} else {
				temp.getParent().setRight(node2);
			}
		}
		if (temp.getLeft() != null) {
			temp.getLeft().setParent(node2);
		}
		if (temp.getRight() != null) {
			temp.getRight().setParent(node2);
		}
		node2.setParent(temp.getParent());
		node2.setLeft(temp.getLeft());
		node2.setRight(temp.getRight());
		node2.setHeight(temp.getHeight());
	}

	/**
	 * private int rebalance(IAVLNode node)
	 * 
	 * Rebalance the given node according to AVL rules
	 * 
	 * Returns how many rotations have been made
	 * 
	 * precondition: the given node is an AVL criminal
	 * 
	 * O(logn)
	 **/
	private int rebalance(IAVLNode node) {
		int rebal = 0;
		if (((AVLNode) node).balanceFactor() == -2) {
			if (((AVLNode) node.getRight()).balanceFactor() == -1 || ((AVLNode) node.getRight()).balanceFactor() == 0) {
				if (this.getRoot() == node) {
					this.root = (node.getRight());
				}
				this.leftRotation(node);
				rebal = 1;
			} else {

				if (this.getRoot() == node) {
					this.root = (node.getRight().getLeft());
				}
				this.rightRotation(node.getRight());
				this.leftRotation(node);
				rebal = 2;
			}
		} else {
			if (((AVLNode) node.getLeft()).balanceFactor() == -1) {
				if (this.getRoot() == node) {
					this.root = (node.getLeft().getRight());
				}
				this.leftRotation(node.getLeft());
				this.rightRotation(node);
				rebal = 2;
			} else {
				if (this.getRoot() == node) {
					this.root = (node.getLeft());
				}
				this.rightRotation(node);
				rebal = 1;
			}
		}
		this.updateHeightAndSize(node);
		return rebal;
	}

	/**
	 * private void leftRotation(IAVLNode nodeToRotate)
	 * 
	 * Rotate the given node to the left
	 * 
	 * O(1)
	 */
	private void leftRotation(IAVLNode nodeToRotate) {
		IAVLNode node = nodeToRotate.getRight();
		nodeToRotate.setRight(node.getLeft());
		if (node.getLeft() != null) {
			node.getLeft().setParent(nodeToRotate);
		}
		node.setParent(nodeToRotate.getParent());
		if (node.getParent() == null) {
			this.root = (node);
		} else if (nodeToRotate == nodeToRotate.getParent().getLeft()) {
			nodeToRotate.getParent().setLeft(node);
		} else {
			nodeToRotate.getParent().setRight(node);
		}
		node.setLeft(nodeToRotate);
		nodeToRotate.setParent(node);

		this.updateHeightAndSize(nodeToRotate);
	}

	/**
	 * private void rightRotation(IAVLNode nodeToRotate)
	 * 
	 * Rotate the given node to the right
	 * 
	 * O(1)
	 */
	private void rightRotation(IAVLNode nodeToRotate) {
		IAVLNode node = nodeToRotate.getLeft();
		nodeToRotate.setLeft(node.getRight());
		if (node.getRight() != null) {
			node.getRight().setParent(nodeToRotate);
		}
		node.setParent(nodeToRotate.getParent());
		if (node.getParent() == null) {
			this.root = (node);
		} else if (nodeToRotate == nodeToRotate.getParent().getRight()) {
			nodeToRotate.getParent().setRight(node);
		} else {
			nodeToRotate.getParent().setLeft(node);
		}
		node.setRight(nodeToRotate);
		nodeToRotate.setParent(node);

		this.updateHeightAndSize(nodeToRotate);
	}

	/**
	 * private void updateHeightAndSize(IAVLNode node)
	 * 
	 * Updating the height and the size of all the nodes in the path from node up to
	 * the root
	 * 
	 * @param node
	 * 
	 *             O(logn)
	 *
	 */
	private void updateHeightAndSize(IAVLNode node) {
		int leftHeight, rightHeight, leftSize, rightSize;
		while (node != null) {
			if (node.getLeft() == null) {
				leftHeight = -1;
				leftSize = 0;
			} else {
				leftHeight = node.getLeft().getHeight();
				leftSize = ((AVLNode) node.getLeft()).getSize();
			}
			if (node.getRight() == null) {
				rightHeight = -1;
				rightSize = 0;
			} else {
				rightHeight = node.getRight().getHeight();
				rightSize = ((AVLNode) node.getRight()).getSize();
			}
			if (leftHeight >= rightHeight) {
				node.setHeight(leftHeight + 1);
			} else {
				node.setHeight(rightHeight + 1);
			}

			((AVLNode) node).setSize(leftSize + rightSize + 1);
			node = node.getParent();
		}
	}

	// TreeList help methods

	/**
	 * public Item nodeInIndex(int i)
	 *
	 * Returns the node with rank i+1 if 0<i<this.size-1, otherwise returns null.
	 * 
	 * @param i
	 * @return node
	 * 
	 *         O(logn)
	 */
	public IAVLNode nodeInIndex(int i) {
		if (i < 0 || i > this.size() - 1) {
			return null;
		}
		IAVLNode node = this.getRoot();
		int rank = (node.getLeft() != null) ? ((AVLNode) node.getLeft()).getSize() + 1 : 1;

		while (rank != i + 1) {
			if (rank > i + 1) {
				node = node.getLeft();
				rank = (node.getRight() != null) ? rank - ((AVLNode) node.getRight()).getSize() - 1 : rank - 1;
			} else {
				node = node.getRight();
				rank = (node.getLeft() != null) ? rank + ((AVLNode) node.getLeft()).getSize() + 1 : rank + 1;
			}
		}
		return node;
	}

	/**
	 * public Item itemInIndex(int i)
	 * 
	 * Returns the item of the node with rank i+1
	 * 
	 * @param i
	 * @return
	 * 
	 *         O(logn)
	 */
	public Item itemInIndex(int i) {
		if (nodeInIndex(i) == null) {
			return null;
		}
		return ((AVLNode) nodeInIndex(i)).getItem();
	}

	/**
	 * public int insertInIndex(int i, int k, String s)
	 * 
	 * Inserts a new node with item with key k and value s to the tree so it will be
	 * with rank i+1.
	 * 
	 * @param i
	 * @param k
	 * @param s
	 * @return 0 if 0<=i<=this.size(), -1 otherwise.
	 * 
	 *         O(logn)
	 */
	public int insertInIndex(int i, int k, String s) {
		if (i < 0 || i > this.size()) {
			return -1;
		}

		IAVLNode newNode = new AVLNode(k, s);

		if (this.empty()) {
			this.root = newNode;
			this.size++;
			return 0;
		}

		IAVLNode node = nodeInIndex(i);
		if (node == null) { // insert last
			node = this.maxNode();
			node.setRight(newNode);
			newNode.setParent(node);
		} else {
			if (node.getLeft() == null) {
				node.setLeft(newNode);
				newNode.setParent(node);
			} else {
				node = this.predecessor(node);
				node.setRight(newNode);
				newNode.setParent(node);
			}
		}
		this.size++;

		// fix the tree
		this.updateHeightAndSize(node);
		node = newNode.getParent();
		while (!((AVLNode) node).isCriminal() && node.getParent() != null) { // look for criminals
			node = node.getParent();
		}
		if (((AVLNode) node).isCriminal()) {
			this.rebalance(node);
		}
		return 0;

	}

	/**
	 * public int deleteInIndex(int i)
	 * 
	 * Delete the node with rank i+1 and returns 0 if i>=0 and i<=this.size,
	 * otherwise returns -1.
	 * 
	 * @param i
	 * @return int
	 * 
	 *         O(logn)
	 */
	public int deleteInIndex(int i) {
		if (i < 0 || i > this.size() - 1) {
			return -1;
		}
		IAVLNode node = this.nodeInIndex(i);
		this.delete(node.getKey());
		return 0;
	}

	/**
	 * public interface IAVLNode ! Do not delete or modify this - otherwise all
	 * tests will fail !
	 */
	public interface IAVLNode {
		public int getKey(); // returns node's key

		public String getValue(); // returns node's value [info]

		public void setLeft(IAVLNode node); // sets left child

		public IAVLNode getLeft(); // returns left child (if there is no left child return null)

		public void setRight(IAVLNode node); // sets right child

		public IAVLNode getRight(); // returns right child (if there is no right child return null)

		public void setParent(IAVLNode node); // sets parent

		public IAVLNode getParent(); // returns the parent (if there is no parent return null)

		public void setHeight(int height); // sets the height of the node

		public int getHeight(); // Returns the height of the node
	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree (for example AVLNode), do
	 * it in this file, not in another file. This class can and must be modified.
	 * (It must implement IAVLNode)
	 */
	public class AVLNode implements IAVLNode {
		private Item item;
		private IAVLNode leftSon;
		private IAVLNode rightSon;
		private IAVLNode parent;
		private int height;
		private int size;

		public AVLNode(int k, String i) {
			this.item = new Item(k, i);
			this.parent = null;
			this.leftSon = null;
			this.rightSon = null;
			this.height = 0;
			this.size = 1;
		}

		// The time complexity of the following get/set functions is O(1)

		public Item getItem() {
			return this.item;
		}

		public int getKey() {
			return this.item.getKey();
		}

		public String getValue() {
			return this.item.getInfo();
		}

		public void setLeft(IAVLNode node) {
			this.leftSon = node;
		}

		public IAVLNode getLeft() {
			return this.leftSon;
		}

		public void setRight(IAVLNode node) {
			this.rightSon = node;
		}

		public IAVLNode getRight() {
			return this.rightSon;
		}

		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getHeight() {
			return this.height;
		}

		public void setSize(int s) {
			this.size = s;
		}

		public int getSize() {
			return this.size;
		}

		/**
		 * public int balanceFactor()
		 * 
		 * Returns the balance factor of this node
		 * 
		 * @return int
		 * 
		 *         O(1)
		 */
		public int balanceFactor() {
			if (this.getRight() == null) {
				if (this.getLeft() == null) {
					return 0;
				} else {
					return this.getLeft().getHeight() + 1;
				}
			} else {
				if (this.getLeft() == null) {
					return ((-1) - this.getRight().getHeight());
				} else {
					return this.getLeft().getHeight() - this.getRight().getHeight();
				}
			}
		}

		/**
		 * public boolean isCriminal() Returns true if this is an AVL criminal, false
		 * otherwise.
		 * 
		 * @return boolean
		 * 
		 *         O(1)
		 */
		public boolean isCriminal() {
			if (this.balanceFactor() > 1 || this.balanceFactor() < -1) {
				return true;
			}
			return false;
		}
	}
}

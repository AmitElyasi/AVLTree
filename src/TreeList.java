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
 * Tree list
 *
 * An implementation of a Tree list with key and info
 *
 */
public class TreeList {
	private AVLTree tree = new AVLTree();

	/**
	 * public Item retrieve(int i)
	 *
	 * returns the item in the ith position if it exists in the list. otherwise,
	 * returns null
	 * 
	 * O(logn)
	 */
	public Item retrieve(int i) {
		Item item = this.tree.itemInIndex(i);
		if (item == null) {
			return null;
		}
		return new Item(item.getKey(), item.getInfo());
	}

	/**
	 * public int insert(int i, int k, String s)
	 *
	 * inserts an item to the ith position in list with key k and info s. returns -1
	 * if i<0 or i>n otherwise return 0.
	 * 
	 * O(logn)
	 */
	public int insert(int i, int k, String s) {
		return this.tree.insertInIndex(i, k, s);
	}

	/**
	 * public int delete(int i)
	 *
	 * deletes an item in the ith posittion from the list. returns -1 if i<0 or
	 * i>n-1 otherwise returns 0.
	 * 
	 * O(logn)
	 */
	public int delete(int i) {
		return this.tree.deleteInIndex(i);
	}
}
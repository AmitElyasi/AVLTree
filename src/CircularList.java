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
 * Circular list
 *
 * An implementation of a circular list with key and info
 *
 */

public class CircularList {

	private Item[] circularArr;
	private int start;
	private int end;// will hold -1 if list is empty
	private int size; // will hold -1 if list is empty
	private final int maxLen;

	public CircularList(int length) {
		this.circularArr = new Item[length];
		this.maxLen = length;
		this.start = -1;
		this.end = -1;
		this.size = 0;
	}

	/**
	 * public Item retrieve(int i)
	 *
	 * returns the item in the ith position if it exists in the list. otherwise,
	 * returns null O(1) complexity
	 */

	public Item retrieve(int i) {
		if (0 <= i && i < this.size) {
			return circularArr[(start + i) % maxLen];
		}
		return null;
	}

	/**
	 * public int insert(int i, int k, String s)
	 *
	 * inserts an item to the ith position in list with key k and info s. returns -1
	 * if i<0 or i>n or n=maxLen otherwise return 0.
	 * 
	 * O(n) at worst case. Complexity at worst case is about (size/2), which, at the
	 * worst case, when size=n, comes down to (n/2).
	 */
	public int insert(int i, int k, String s) {
		if (i < 0 || maxLen == size || i > size) {
			return -1;
		}
		Item newItem = new Item(k, s);
		if (size == 0) {
			start = 0;
			end = 0;
			circularArr[start] = newItem;
			size++;
			return 0;
		}
		if (i <= size / 2) { // i is closer to the start
			for (int j = 0; j != i; j = (j + 1) % maxLen) {
				circularArr[(j - 1 + maxLen) % maxLen] = circularArr[j];
			}
			start = (start - 1 + maxLen) % maxLen;
		} else { // i is closer to the end
			for (int j = end; j != i - 1; j = (j - 1 + maxLen) % maxLen) {
				circularArr[(j + 1) % maxLen] = circularArr[j];
			}
			end = (end + 1) % maxLen;
		}
		circularArr[(i + start) % maxLen] = newItem;
		size++;
		return 0;
	}

	/**
	 * public int delete(int i)
	 *
	 * deletes an item in the ith position from the list. returns -1 if i<0 or i>n-1
	 * otherwise returns 0.
	 * 
	 * O(n/2)=O(n)
	 */
	public int delete(int i) {
		if (i < 0 || i > size - 1) {
			return -1;
		}
		if (i <= size / 2) { // i is closer to the start
			for (int j = start + i; j != start; j = (j - 1 + maxLen) % maxLen) {
				circularArr[j] = circularArr[(j - 1 + maxLen) % maxLen];
			}
			circularArr[start] = null;// delete the item in the "previous" start position
			start = (start + 1) % maxLen;
		} else { // i is closer to the end
			for (int j = start + i; j != end; j = (j + 1) % maxLen) {
				circularArr[j] = circularArr[(j + 1) % maxLen];
			}
			circularArr[end] = null;// delete the item in the "previous" end position
			end = (end - 1 + maxLen) % maxLen;
		}
		size--;
		if (size == 0) {
			end = -1;
			start = -1;
		}
		return 0;
	}
}
import java.util.Iterator;


/**
 * <p><tt>BinaryPatriciaTrie</tt> is a Patricia Trie over the binary alphabet &#123;0, 1&#125;. By restricting themselves to
 * this small but terrifically useful alphabet, Binary Patricia Tries combine all the positive aspects of Patricia Tries
 * while shedding the storage cost typically associated with Tries that deal with huge alphabets. </p>
 * @author  ------ YOUR NAME HERE! ----------
 */
public class BinaryPatriciaTrie {

	// You can erase the following line after you erase the exception throwings in the public methods...
	private static final RuntimeException UNIMPL_METHOD_EXC = new RuntimeException("Unimplemented method!");

	Node root = null;
	
	public class Node {
		public int index;
		public Node left;
		public Node right;
		public String key;
		
		public Node() {
			index = 0;
			left = null;
			right = null;
			key = null;
		}
		
		public Node(String s, int i) {
			index = i;
			left = null;
			right = null;
			key = s;
		}
	}

	/*
	 * Put your private / protected fields and methods here!
	 */

	
	
	/* To pass our tests, you will need to complete the class' public interface, declared below.
	 * YOU WILL NEED TO COMMENT OUT THE EXCEPTION THROWINGS, OTHERWISE YOU WILL FAIL ALL TESTS THAT
	  * INCLUDE CALLS TO THE RELEVANT METHODS! */

	/**
	 * Simple constructor that will initialize the internals of <tt>this</tt>.
	 */
	public BinaryPatriciaTrie() {
		//throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}


	/** Searches the trie for a given key.
	 * @param key The input {@link String} key.
	 * @return <tt>true</tt> if and only if <tt>key</tt> is in the trie, <tt>false</tt> otherwise.
	 */
	public boolean search(String key) {
		//throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
		if (key == null || root == null) {
			return false;
		}
		return search(key, root);
	}

	public boolean search(String s, Node curr) {
		if(curr == null)
			return false;
		if(curr.key == null){//root
			if(curr.index >= s.length())
				return false;
			else if(s.charAt(curr.index) == '0')
				return search(s, curr.left);//go left
			else
				return search(s, curr.right);
		}else{
			if(s.equals(curr.key))
				return true;//found
			else{
				int length = Math.min(curr.key.length(), s.length());
				int diff_index = -1;
				
				for (int i = 0; i < length; i++) {
					if (curr.key.charAt(i) != s.charAt(i)) {
						diff_index = i;
						break;
					}
				}
				if (diff_index == -1) {
					if (s.length() >= curr.key.length()) {
						if (s.charAt(length) == '0') {
							return search(s, curr.left);
						} else {
							return search(s, curr.right);
						}
					} else {
						return false;
					}
				} else {
					if (s.charAt(diff_index) == '0') {
						return search(s, curr.left);
					} else {
						return search(s, curr.right);
					}
				}
			}
		}
			
	}

	/** Inserts <tt>key</tt> into the trie.
	 * @param key The input {@link String} key.
	 * @return <tt>true</tt> if and only if the key was not already in the trie, <tt>false</tt> otherwise.
	 */
	public boolean insert(String key) {
		// Check if current trie is empty (Base Case)
				if (root == null) {
					root = new Node(key, key.length());
					return true;
					
				// Check if current trie already contains the key
				} else if (search(key) == true) {
					return false;
				}
				
				Node travel = root;
				return insert(key, travel, null, 0, -1);
	}

	
	public boolean insert(String s, Node travel, Node prev, int direction, int index_level) {
		// Reached null meaning, it is the longest current leaf.
		if (travel == null) {
			Node newNode = new Node(s, s.length());
			if (direction == 0) {
				prev.left = newNode;
			} else {
				prev.right = newNode;
			}
			
		// No Key, figure out which way to traverse
		} else if (travel.key == null) {
			// s length same as index
			if (travel.index == s.length()) {
				int diff_index = -1;
				Node node_with_string = travel;
				
				while (node_with_string.key == null) {
					node_with_string = node_with_string.left;
				}
				
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) != node_with_string.key.charAt(i)) {
						diff_index = i;
						break;
					}
				}
				
				Node newNode = new Node(s, s.length());
				Node diff_node = new Node(null, diff_index);
				
				// Fill in prefix into null key node
				if (diff_index == -1) {
					travel.key = s;
					travel.index = s.length();
				} else {
					// Insertion into middle of tree
					if (prev != null) {
						if (direction == 0) {
							prev.left = diff_node;
						} else {
							prev.right = diff_node;
						}
						if (s.charAt(diff_index) == '0') {
							diff_node.left = newNode;
							diff_node.right = travel;
						} else {
							diff_node.left = travel;
							diff_node.right = newNode;
						}
					
					// Has to be root case
					} else {
						if (s.charAt(diff_index) == '0') {
							diff_node.left = newNode;
							diff_node.right = travel;
						} else {
							diff_node.left = travel;
							diff_node.right = newNode;
						}
						root = diff_node;
					}
				}
			// index is less than s length
			} else if (travel.index < s.length()) {
				int diff_index = -1;
				Node node_with_string = travel;
				
				while (node_with_string.key == null) {
					node_with_string = node_with_string.left;
				}
				
				for (int i = 0; i < travel.index; i++) {
					if (s.charAt(i) != node_with_string.key.charAt(i)) {
						diff_index = i;
						break;
					}
				}
				
				if (diff_index == -1) {
					if (s.charAt(travel.index) == '0') {
						return insert(s, travel.left, travel, 0, travel.index);
					} else {
						return insert(s, travel.right, travel, 1, travel.index);
					}
				} else {
					Node newNode = new Node(s, s.length());
					Node diff_node = new Node(null, diff_index);
					
					if (diff_index < travel.index) {
						if (prev != null) {
							if (s.charAt(diff_index) == '0') {
								diff_node.left = newNode;
								diff_node.right = travel;
							} else {
								diff_node.left = travel;
								diff_node.right = newNode;
							}
							if (direction == 0) {
								prev.left = diff_node;
							} else {
								prev.right = diff_node;
							}
						} else {
							if (s.charAt(diff_index) == '0') {
								diff_node.left = newNode;
								diff_node.right = travel;
							} else {
								diff_node.left = travel;
								diff_node.right = newNode;
							}
							root = diff_node;
						}
					}
				}
			} else {
				int diff_index = -1;
				Node node_with_string = travel;
				
				while (node_with_string.key == null) {
					node_with_string = node_with_string.left;	
				}
				
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) != node_with_string.key.charAt(i)) {
						diff_index = i;
						break;
					}
				}
				
				Node newNode = new Node(s, s.length());
				
				if (diff_index != -1) {
					Node diff_node = new Node(null, diff_index);
					
					if (prev == null) {
						if (s.charAt(diff_index) == '0') {
							diff_node.left = newNode;
							diff_node.right = travel;
						} else {
							diff_node.left = travel;
							diff_node.right = newNode;
						}
						root = diff_node;
					} else {
						if (s.charAt(diff_index) == '0') {
							diff_node.left = newNode;
							diff_node.right = travel;
						} else {
							diff_node.left = travel;
							diff_node.right = newNode;
						}
						
						if (direction == 0) {
							prev.left = diff_node;
						} else {
							prev.right = diff_node;
						}
					}
				} else {
					if (prev != null) {
						if (direction == 0) {
							prev.left = newNode;
						} else {
							prev.right = newNode;	
						}
					
						if (node_with_string.key.charAt(s.length()) == '0') {
							newNode.left = travel;
						} else {
							newNode.right = travel;
						}
					} else {
						if (prev == null) {
							if (node_with_string.key.charAt(s.length()) == '0') {
								newNode.left = travel;
							} else {
								newNode.right = travel;
							}
							root = newNode;
						}
					}
				}
			}
		
		// Finds a key
		} else {
			int length = Math.min(s.length(), travel.key.length());
			int diff_index = -1;
			
			// Find the index where the two strings differ
			for (int i = 0; i < length; i++) {
				if (s.charAt(i) != travel.key.charAt(i)) {
					diff_index = i;
					break;
				}
			}
			// Two strings do differ
			if (diff_index != -1) {
				Node diff_node = new Node(null, diff_index);
				Node newNode = new Node(s, s.length());
				
				if (s.charAt(diff_index) == '0') {
					diff_node.left = newNode;
					diff_node.right = travel;
					if (prev != null) {
						if (direction == 0) {
							prev.left = diff_node;
						} else {
							prev.right = diff_node;
						}
					}
					if (index_level == -1) {
						root = diff_node;
					}
				} else {
					diff_node.left = travel;
					diff_node.right = newNode;
					if (prev != null) {
						if (direction == 0) {
							prev.left = diff_node;
						} else {
							prev.right = diff_node;
						}
					}
					if (index_level == -1) {
						root = diff_node;
					}
				}
			// One string is longer and contains the whole string of the other
			} else {
				Node newNode = new Node(s, s.length());
				
				if (s.length() > travel.key.length()) { 
					if (s.charAt(travel.key.length()) == '0') {
						return insert(s, travel.left, travel, 0, travel.index);
					} else {
						return insert(s, travel.right, travel, 1, travel.index);
					}
					
				} else {
					if (travel.key.charAt(s.length()) == '0') {
						newNode.left = travel;
						
						if (prev != null) {
							if (direction == 0) {
								prev.left = newNode;
							} else {
								prev.right = newNode;
							}
						} else {
							root = newNode;
						}
					} else {
						newNode.right = travel;
						
						if (prev != null) {
							if (direction == 0) {
								prev.left = newNode;
							} else {
								prev.right = newNode;
							}
						} else {
							root = newNode;
						}
					}
				}
				
			}
		}
		return true;	
	}
	
	
	

	/** Deletes <tt>key</tt> from the trie.
	 * @param key The {@link String} key to be deleted.
	 * @return <tt>true</tt> if and only if <tt>key</tt> was contained by the trie before we attempted deletion,
	 * <tt>false</tt> otherwise.
	 */
	public boolean delete(String key) {
		throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}

	/**
	 * Queries the trie for emptiness.
	 * @return <tt>true</tt> if, and only if, {@link #getSize()} == 0, <tt>false</tt> otherwise.
	 */
	public boolean isEmpty() {
		throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}

	/**
	 * Counts the number of keys in the tree.
	 *@return The number of keys in the tree.
	 */
	public int getSize() {
		throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}

	/** Performs an inorder traversal of the <tt>Binary Patricia Trie</tt>. Remember from lecture that <b>inorder traversal in tries is
	 * NOT sorted traversal, unless all the stored keys have the same length.</b> This is of course not required by your implementation,
	 * so you should make sure that in your tests you are not expecting this method to return keys in lexicographic order. We put this
	 * method in the interface because it helps <b>us</b> test your submission thoroughky and it helps <b>you</b> debug your code!
	 * @return An {@link Iterator} over the {@link String} keys stored in the trie, exposing the elements in an &#34;inorder&#34;
	 * ordering. <b>We neither require nor test that the iterator is fail-safe or fail-fast</b>; that is, you do <b>not</b> need to test for thrown
	 * {@link java.util.ConcurrentModificationException}s and we do <b>not</b> test your code for concurrent modifications.
	 */
	public Iterator<String> inorderTraversal() {
		throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}

	/** Finds the longest {@link String} stored in the Binary Patricia Trie. &quot;<i>Longest</i>&quot; is rigorously defined below.
	 * @return The longest {@link String} stored in <tt>this</tt>. The following rules apply:
	 * <ol><li>If the trie is empty, the <b>empty string</b> &quot;&quot; should be returned. <b>Careful: the empty string &quot;&quot; is <b>not</b> the same string as &quot; &quot;&#59;	the latter is a string consisting of a space character!</b></li>
	 * <li>If there exist two strings &sigma; and &sigma;&#39;	with length(&sigma;) = length(&sigma;&#39;), then the string with the highest <b>lexicographical order</b> is considered the longest.</li>
	 * <li>In all other cases, the length of the strings is the only parameter compared.</li>
	 * </ol>
	 */
	public String getLongest () {
		throw UNIMPL_METHOD_EXC; // ERASE THIS BEFORE YOU TEST YOUR CODE!
	}
	
	
	
	public void printTree(Node root) {
		if (root == null) {
			return;
		}
		printTree(root.left);
		
		if (root != null) {
			System.out.println(root.key);
		}
		
		printTree(root.right);
	}


}
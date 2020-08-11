import java.util.ArrayList;

interface PrefixTreeADT {
	
	/**
	 * Gets the size ....An empty trie returns 0.
	 * @return size (number of unique words) in the trie.
	 */
	public int getSize();

	/**
	 * Inserts a string into the trie and increases the count of the word.
	 * New nodes must be inserted in alphabetical order.
	 * @param word can be upper or lower case, but must be stored as lower case
	 * @throws IllegalArgumentException if input string is null or length is 0
	 */
	public void insert(String word) throws IllegalArgumentException;
	
	
	/**
	 * Returns the number of times the word appears in the trie.
	 * @param word can be upper or lower case, but is stored as lower case
	 * @return the number of occurrences of word (returns 0 if word not present)
	 * @throws IllegalArgumentException if input string is null or length is 0
	 */
	public int getFrequency(String word) throws IllegalArgumentException;

	
	
	/**
	 * Returns an ArrayList<String> of all words in the trie that have the given prefix.
	 * If no words match the prefix, return an empty ArrayList<String>.
	 * If an empty string is input, returns all words
	 * Must have Order(tree height) efficiency. In other words, you must traverse your trie :)
	 * NOTE: if your trie is made correctly, your traversal will produce a sorted list
	 *       so you should not need to perform a sorting algorithm on this list
	 * @param prefix (if an empty string is entered, returns all words)
	 * @return an ArrayList<String> 
	 * @throws IllegalArgumentException if the prefix is null
	 */
	public ArrayList<String> getWordsWithPrefix(String prefix) throws IllegalArgumentException;
	
	

	
}

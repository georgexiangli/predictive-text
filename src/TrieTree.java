// Title: WordCountRunner
// Files: TrieTree.java, PrefixTreeADT.java, TextPredictor.java
// Course: Programming III, Fall 2019
//
// Author: George Li
// Email: gli245@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: None
// Online Sources: None

import java.util.ArrayList;
// no other import statements are allowed

/**
 * Trie data structure.
 */
public class TrieTree implements PrefixTreeADT {
  private final char ROOT = '@';
  private TNode root;
  private int size;

  // provide the inner class header and instance variables to students
  /**
   * Private inner node class.
   */
  private class TNode {
    private char letter;
    private boolean isEndOfWord;
    private int endWordCount;
    private ArrayList<TNode> childList;

    public TNode(char letter) {
      this.letter = letter;
      this.isEndOfWord = false;
      this.endWordCount = 0;
      this.childList = new ArrayList<TNode>();
    }

  }// inner class


  /**
   * Trie constructor.
   */
  public TrieTree() {
    this.root = new TNode(ROOT); // stands for root node
    this.size = 0;
  }

  /**
   * Gets the size ....An empty trie returns 0.
   * 
   * @return size (number of unique words) in the trie.
   */
  public int getSize() {
    return this.size;
  }

  /**
   * Inserts a string into the trie and increases the count of the word. New nodes must be inserted
   * in alphabetical order.
   * 
   * @param word can be upper or lower case, but must be stored as lower case
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  public void insert(String word) throws IllegalArgumentException {
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException();
    }

    word = word.toLowerCase();

    insert(this.root, word);
  }

  /**
   * Recursive helper method to insert into trie tree
   * 
   * @param current node to examine
   * @param word    String to insert
   */
  private void insert(TNode current, String word) {
    char target = word.charAt(0);
    TNode next = null;
    int insertIdx = 0; // sorted insert

    // loop through children looking for a match
    for (TNode child : current.childList) {
      if (target > child.letter) {
        insertIdx++;
      }

      if (target == child.letter) {
        next = child;
        break;
      }
    }

    // if no matches
    if (next == null) {
      next = new TNode(target);
      current.childList.add(insertIdx, next);
    }

    if (word.length() == 1) {
      next.isEndOfWord = true;
      next.endWordCount++;
      if (next.endWordCount == 1) {
        this.size++;
      }
    } else {
      insert(next, word.substring(1));
    }

  }

  /**
   * Returns the number of times the word appears in the trie.
   * 
   * @param word can be upper or lower case, but is stored as lower case
   * @return the number of occurrences of word (returns 0 if word not present)
   * @throws IllegalArgumentException if input string is null or length is 0
   */
  public int getFrequency(String word) throws IllegalArgumentException {
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException();
    }

    word = word.toLowerCase();

    return getFrequency(root, word);
  }

  /**
   * Recursive helper method to find frequency of a word
   * 
   * @param current node to examine
   * @param word    target word
   * @return frequency of word
   */
  private int getFrequency(TNode current, String word) {
    char target = word.charAt(0);
    TNode next = null;

    for (TNode child : current.childList) {
      if (target == child.letter) {
        next = child;
        break;
      }
    }

    // found matching char
    if (next != null) {
      if (word.length() == 1) {
        if (next.isEndOfWord) {
          return next.endWordCount;
        }
      } else {
        return getFrequency(next, word.substring(1));
      }
    }
    return 0; // if word is not found
  }

  /**
   * Returns an ArrayList<String> of all words in the trie that have the given prefix. If no words
   * match the prefix, return an empty ArrayList<String>. If an empty string is input, returns all
   * words Must have Order(tree height) efficiency. In other words, you must traverse your trie :)
   * NOTE: if your trie is made correctly, your traversal will produce a sorted list so you should
   * not need to perform a sorting algorithm on this list
   * 
   * @param prefix (if an empty string is entered, returns all words)
   * @return an ArrayList<String>
   * @throws IllegalArgumentException if the prefix is null
   */
  public ArrayList<String> getWordsWithPrefix(String prefix) throws IllegalArgumentException {
    if (prefix == null) {
      throw new IllegalArgumentException();
    }

    prefix = prefix.toLowerCase();
    String subtreePrefix = "";
    ArrayList<String> wordsArray = new ArrayList<String>();

    getWordsWithPrefix(this.root, prefix, prefix.length(), wordsArray, subtreePrefix);

    return wordsArray;
  }

  /**
   * Recursive helper method to return all words
   * 
   * @param current       node to examine
   * @param prefix        prefix to test for
   * @param wordsArray    words list to return
   * @param subtreePrefix all letters in current path
   */
  private void getWordsWithPrefix(TNode current, String prefix, int originalPrefixSize,
      ArrayList<String> wordsArray, String subtreePrefix) {
    String newWord; // new word to add, don't carry subtreePrefix across nodes

    for (TNode child : current.childList) {
      newWord = subtreePrefix + child.letter;

      if (!prefix.isEmpty()) {
        if (prefix.charAt(0) == child.letter) {
          if (child.isEndOfWord && newWord.length() >= originalPrefixSize) {
            wordsArray.add(newWord);
          }
          getWordsWithPrefix(child, prefix.substring(1), originalPrefixSize, wordsArray, newWord);
          return;
        }
      } else { // add all words to list

        if (child.isEndOfWord) {
          wordsArray.add(newWord);
        }

        getWordsWithPrefix(child, prefix, 0, wordsArray, newWord);
      }
    }
  }


  /**
   * Prints the tree to the console.
   */
  public void printTrie() {
    printTrieRecursive(root, "");
  }

  /**
   * Helper method for printTrie().
   * 
   * @param current the current node in the recursive call
   * @param indent  the amount of indent to print this level
   */
  private void printTrieRecursive(TNode current, String indent) {
    if (current != null) {
      if (current.isEndOfWord) {
        System.out.println(indent + current.letter + ":" + current.endWordCount);
      } else {
        System.out.println(indent + current.letter);
      }
      for (TNode child : current.childList) {
        printTrieRecursive(child, indent + "  ");
      }
    }
  }

  /**
   * Main method to test code
   * @param args command line arguments
   */
  public static void main(String[] args) {
    TrieTree tree = new TrieTree();
    System.out.println(tree.getSize());
    tree.insert("hi");
    tree.insert("hi");
    tree.insert("hid");
    tree.insert("hello");
    tree.insert("cat");
    tree.printTrie();
    System.out.println(tree.getFrequency("hi"));
    System.out.println(tree.getSize());
    System.out.println("------");
    System.out.println(tree.getWordsWithPrefix("hi"));
    System.out.println(tree.getWordsWithPrefix(""));
  }

}

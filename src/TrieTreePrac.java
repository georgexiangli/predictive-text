import java.util.ArrayList;

public class TrieTreePrac {
  TNode root;
  
  private class TNode{
    private char letter;
    private boolean isEndOfWord;
    private int endWordCount;  
    private ArrayList<TNode> childList;
    
    private TNode(char letter) {
      this.letter = letter;
      this.isEndOfWord = false;
      this.endWordCount = 0;
      this.childList = new ArrayList<TNode>();

    }    
  }// inner class

  public TrieTree() {
    root = new TNode('@');
  }
  
  public void insert(String word) {
    // TODO: Needs to be alphabetical without array sort
    // linear search on arrayList is ok
    if (word == null || word.length() == 0) {
      throw new IllegalArgumentException();
    }
    insert(root, word); // root never changes
    
  }

  private void insert(TNode current, String word) {
    char firstLetter = word.charAt(0);
    TNode node = null;
    for (TNode child : current.childList) {
      if (child.letter == firstLetter) {
        node = child;
        break;
      }
    }
    
    if (node == null) {
      node = new TNode(firstLetter);
      current.childList.add(node); // add a new node if char not in sequence
    }
    if (word.length() == 1) { // base case
      node.isEndOfWord = true;
      node.endWordCount++;
    } else {
      insert(node, word.substring(1));
    }
  }
  

  public void printTrie() {
      printTrieRecursive(root, "");
  }

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

  
  public static void main(String[] args) {
    TrieTree tree = new TrieTree();
    tree.insert("Hello");
    tree.insert("Help");
    tree.insert("Held");
    tree.insert("Hello");
    tree.printTrie();
  }
  
}

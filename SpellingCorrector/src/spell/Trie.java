package spell;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Locale;
import java.util.Objects;

public class Trie implements ITrie{
    private TrieNode root;
    private int wordCount;
    private int nodeCount;

    /**
     * Constructs a root to the Trie with an empty TrieNode with no wordCount and 1 nodeCount and adds the children array for root
     */
    public Trie() {
        this.root = new TrieNode();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase(Locale.ROOT); //Locale.ROOT makes the lowercase always be the same regardless what character types are being used
        char currLetter;
        int index;
        TrieNode currNode = root;
        for(int i = 0; i < word.length(); ++i){
            currLetter = word.charAt(i);
            index = currLetter - 'a'; //gets the index value for the letter to be added
            TrieNode[] children =(TrieNode[]) currNode.getChildren();
            if(children == null && i <= word.length() - 1){
                currNode.addChildren();
                children = (TrieNode[]) currNode.getChildren();
            } //This adds the children nodes set to null to the current node if they do not already exist and if there are more letters to add
            if(children[index] == null){
                children[index] = new TrieNode();
                nodeCount++; //adds a new node at the child index if the node did not already exist
            }
            if(i == word.length() - 1){
                if(children[index].getValue() == 0) wordCount++;
                children[index].incrementValue(); //the last letter in the word increments the node frequency counter as well as adding a new word
            } else currNode = (TrieNode) currNode.getChildren()[index]; //reset the currNode if the word is not done
        }
    }

    /**
     * Finds the word specified if the word exists in the Trie
     * @param word the word being searched for.
     *
     * @return a reference to the TrieNode that indicates the word
     */
    @Override
    public INode find(String word) {
        if(word.length() == 0){
            return null;
        }
        word = word.toLowerCase(Locale.ROOT);
        char currLetter;
        int index = 0; //initializing for the second to last line in this function
        TrieNode currNode = root;
        if(root.getChildren() == null){
            root.addChildren();
        } //Added this just in case for testing it is necessary to use the find function without first using the add function
        for(int i = 0; i < word.length(); ++i){
            currLetter = word.charAt(i);
            index = currLetter - 'a';
            if(currNode.getChildren() == null) return null;
            INode c = currNode.getChildren()[index];
            if(c == null) return null;
            if(i < word.length() - 1) currNode = (TrieNode) c; //resets the currNode if the word has not been completed
        }
        if(currNode.getChildren()[index].getValue() > 0) return currNode.getChildren()[index];
        return null;
    }

    /**
     * The count of unique words in the trie
     * @return the word count
     */
    @Override
    public int getWordCount() {return wordCount;}

    /**
     * The number of nodes in the Trie
     * @return the number of nodes
     */
    @Override
    public int getNodeCount() {return nodeCount;}

    /**
     * Checks to see if two Tries are equal
     * @param o Object to be compared against this trie
     * @return a boolean representing if the tries are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        if(this.nodeCount != trie.nodeCount || this.wordCount != trie.wordCount) return false;
        boolean[] bool = new boolean[16];
        return equalsHelper(this.root, trie.root);
    }
    private boolean equalsHelper(INode node1, INode node2) {
        boolean bool = true;
        if (node1.getValue() != node2.getValue()) {
            bool = false;
        } else {
            for (int i = 0; i < 26; i++) {
                if (node1.getChildren() != null && node2.getChildren() != null) {
                    if (node1.getChildren()[i] != null && node2.getChildren()[i] != null) {
                        bool = equalsHelper(node1.getChildren()[i], node2.getChildren()[i]);
                    }
                }
            }
        }
        return bool;
    }
    @Override
    public int hashCode() {
        int hash = 1;
        hash = endCount() + wordCount;
        return hash;
    }

    /**
     * This function gets the count for the  closest ending word
     * @return count
     */
    public int endCount(){
        INode child = this.root;
        int index = 1;
        if(this.root.getValue() != 0){
            return this.root.getValue();
        } else {
            while (child.getValue() == 0) {
                for (int i = 0; i < 26; i++) {
                    if (child.getValue() != 0) {
                        index = i;
                        break;
                    }
                    if (child.getChildren() != null) {
                        if (child.getChildren()[i] != null) {
                            child = child.getChildren()[i];
                        }
                    }
                }
            }
        }
        return index;
    }

    @Override
    public String toString() {
        StringBuilder curWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toString_Helper(root, curWord, output);
        return output.toString();
    }
    private void toString_Helper(INode n, StringBuilder curWord, StringBuilder output){
        if(n.getValue() > 0){
            //Append Nodes word to output
            output.append(curWord.toString());
            output.append("\n");
        }
        for(int i = 0; i < root.getChildren().length; ++i){ //used root.getChildren length to avoid errors do to the child array not existing
            TrieNode child;
            if(n.getChildren() != null) {
                child = (TrieNode) n.getChildren()[i];
            } else break;
            if(child != null){
                char childLetter = (char) ('a' + i);
                curWord.append(childLetter);
                toString_Helper(child, curWord, output);
                curWord.deleteCharAt(curWord.length() - 1);
            }
        }
    }
}

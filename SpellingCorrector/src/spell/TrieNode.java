package spell;

import java.util.Arrays;
import java.util.Objects;

public class TrieNode implements INode{
    private int count;
    private TrieNode[] children;

    /**
     * Creates a node with count 0, not that the children is set to null
     */
    public TrieNode() {
        this.count = 0;
        this.children = null;
    }

    /**
     * Adds children array when necessary to avoid wasting space when a leaf node
     */
    public void addChildren(){
        this.children = new TrieNode[26];
    }
    /**
     * Return the frequency of the word at the current TrieNode
     * @return Frequency of the word at the current TrieNode
     */
    @Override
    public int getValue() {return count;}

    /**
     * This increments the count by 1 to measure the frequency of the word ending at the node
     */
    @Override
    public void incrementValue() {
        count++;
    }

    /**
     * This returns the array of the children of the current node
     * @return Returns Array of the children of the current TrieNode
     */
    @Override
    public INode[] getChildren() {
        return children;
    }
}

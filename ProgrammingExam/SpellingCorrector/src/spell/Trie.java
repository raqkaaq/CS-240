package spell;

import java.util.Locale;
import java.util.Objects;

public class Trie implements ITrie{
    private Node root;
    private int wordCount;
    private int nodeCount;

    public Trie() {
        this.root = new Node();
        this.root.setChildren();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    @Override
    public void add(String word) {
        word = word.toLowerCase(Locale.ROOT);
        char letter;
        Node currWord = root;
        for(int i = 0; i < word.length(); i++){
            letter = word.charAt(i);
            int index = letter - 'a';
            if(currWord.getChildren() == null){
                currWord.setChildren();
            }
            Node[] children = (Node[]) currWord.getChildren();
            if(children[index] == null){
                children[index] = new Node();
                nodeCount++;
            }
            if(i == word.length() - 1){
                if(children[index].getValue() == 0) wordCount++;
                children[index].incrementValue();
            } else {
                currWord = (Node) currWord.getChildren()[index];
            }
        }
    }

    //@Override
    public String toString() {
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        return stringHelper(root, a, b);
    }
    private String stringHelper(INode n, StringBuilder a, StringBuilder b){
        if(n.getValue() > 0){
            b.append(a.toString());
            b.append('\n');
        }
        INode child;
        for(int i = 0; i < 26; i++){
            if(n.getChildren() != null) {
                child = n.getChildren()[i];
            } else break;
            if(child != null) {
                char letter = (char) ('a' + i);
                a.append(letter);
                stringHelper(child, a, b);
                a.deleteCharAt(a.length() - 1);
            }
        }
        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trie trie = (Trie) o;
        if(wordCount != trie.wordCount || nodeCount != trie.nodeCount) return false;
        return equalsHelper(root, trie.root);
    }
    private boolean equalsHelper(INode n1, INode n2){
        boolean bool = true;
        if(n1.getValue() != n2.getValue()) bool = false;
        else {
            for(int i = 0; i < 26; i++){
                if(n1.getChildren() != null && n2.getChildren() != null)
                if(n1.getChildren()[i] != null && n2.getChildren()[i] != null){
                    bool = equalsHelper(n1.getChildren()[i],n2.getChildren()[i]);
                }
            }
        }
        return bool;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hashHelper() + wordCount;
        return hash;
    }
    private int hashHelper(){
        INode child = root;
        int counter = 1;
        if(root.getValue() != 0) return root.getValue();
        else{
            while(child.getValue() == 0){
                for(int i = 0; i < 26; i++) {
                    if (child.getValue() != 0) {
                        counter = i;
                        break;
                    }
                    if (child.getChildren()[i] != null) {
                        child = child.getChildren()[i];
                    }
                }
            }
        }
        return counter;
    }

    @Override
    public INode find(String word) {
        if(word.length() == 0) return null;
        word = word.toLowerCase();
        char letter;
        int indexOfLetter = 0;
        INode node = root;
        for(int i = 0; i < word.length(); i++){
            letter = word.charAt(i);
            indexOfLetter = (char) (letter - 'a');
            if(node.getChildren() == null) return null;
            INode child = node.getChildren()[indexOfLetter];
            if(child == null) return null;
            if(node.getChildren()[indexOfLetter].getValue() > 0 && i == (word.length() - 1)){
                return node.getChildren()[indexOfLetter];
            }
            node = child;
        }
        return null;
    }

    @Override
    public int getWordCount() {return this.wordCount;}

    @Override
    public int getNodeCount() {return this.nodeCount;}
}

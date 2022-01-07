package spell;

import java.util.Objects;

public class Words {
    private String word;
    private int times;
    private Words next;

    public Words() {
    }

    public Words(String word, int times) {
        this.word = word;
        this.times = times;
        this.next = null;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Words getNext() {
        return next;
    }

    public void setNext(Words next, Words head) {
        if(next == null) return;
        if(!inSet(head, next)) this.next = next;
    }
    public Boolean inSet(Words head, Words next){
        Words currNode = head;
        while(currNode != null){
            if(currNode.equals(next)) return true;
            else currNode = currNode.next;
        }
        return false;
    }

    public void addWord(String word, int times){
        next = new Words(word, times);
    }
    public void startWord(String word, int times){
        this.word = word;
        this.times = times;
        this.next = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Words words = (Words) o;
        return times == words.times && word.equals(words.word);
    }

    @Override
    public int hashCode() {
        return word.hashCode() + times * next.hashCode();
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        Words a = this;
        while(a != null){
            s.append(a.getWord());
            s.append(' ');
            s.append(a.times);
            s.append('\n');
            a = a.next;
        }
        return s.toString();
    }
}
class WrapInt {
    public int value;
}


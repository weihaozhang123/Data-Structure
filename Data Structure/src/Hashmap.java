import java.lang.reflect.Array;

public class Hashmap<K, V> {
    private class Node {
        K key;
        V value;
        Node next;
        int size;

        // Constructor
        Node(K key, V val)
        {
            this.key = key;
            this.value = val;
            next = null;
            size = 0;
        }
    }
//    Number of elements;
    private int size;
//    Length of the array
    private int length;
    private double factor;
    private Node[] arr;

    public Hashmap() {
        this.size = 0;
        this.length = 4;
        this.factor = 0.75;
        this.arr = (Node[])Array.newInstance(Node.class, this.length);
    }
    private int findIndex(K key) {
        int hash  = key.hashCode();
//      678910
//      0000000011
        int index = hash & (this.length - 1);
        return index;

    }
    private void raise() {
        this.length = this.length * 2;

        Node[] newArr = (Node[])Array.newInstance(Node.class, this.length);
        for (int i = 0; i < this.arr.length; i ++){
            Node list = this.arr[i];
            Node cur = list;
            if(cur == null) {
                continue;
            }
            int newIndex = findIndex(cur.key);
            Node newNode = new Node(cur.key, cur.value);
            newArr[newIndex] = newNode;
            while (cur.next != null) {
                cur = cur.next;
                Node nextNewNode = new Node(cur.key, cur.value);
                newNode.next = nextNewNode;
                newNode = nextNewNode;
            }
        }
        this.arr = newArr;
    }
    public void put(K key, V val) {
        size = size + 1;
        if ((double)size/length > factor){
            this.raise();
        }
        int index = this.findIndex(key);
        Node insertion = new Node(key, val);
        if (this.arr[index] == null) {
            this.arr[index] = insertion;
        } else{
            Node cur = this.arr[index];
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = insertion;
        }
    }
    public V get(K key) {
//        Find the index
        int index = this.findIndex(key);

        Node cur = this.arr[index];
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.value;
            }
            cur = cur.next;
        }
        return null;
    }
    public void remove(K key){
        int index = this.findIndex(key);
        Node prev = null;
        Node cur = this.arr[index];
//no such element
        if (cur == null) {
            return;
        }
        if (cur.key.equals(key)) {
            this.arr[index] = cur.next;
            size--;
        } else {
            while (cur != null && !cur.key.equals(key)) {
                prev = cur;
                cur = cur.next;
            }
            if (cur != null) {
                prev.next = cur.next;
                size--;
            }
        }
    }
    public static void main(String[] args) {
        Hashmap<String, Integer> myHashmap = new Hashmap<>();
        System.out.println(myHashmap.length);
        myHashmap.put("One", 1);
        System.out.println("One: " + myHashmap.get("One"));
        myHashmap.put("Two", 2);
        myHashmap.put("Three", 3);
        myHashmap.put("Four", 4);


        System.out.println("One: " + myHashmap.get("One"));
        System.out.println("Two: " + myHashmap.get("Two"));
        System.out.println("Three: " + myHashmap.get("Three"));
        System.out.println("Four: " + myHashmap.get("Four"));
        System.out.println("Five: " + myHashmap.get("Five"));



        myHashmap.put("Five", 5);
        System.out.println("Five: " + myHashmap.get("Five"));
        System.out.println(myHashmap.size);

        myHashmap.remove("Five");
        System.out.println("Five: " +myHashmap.get( "Five"));
        System.out.println(myHashmap.size);
    }

}

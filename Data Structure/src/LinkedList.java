// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class LinkedList<T> {

     class Node {

        T data;
        Node next;
        int size;

         // Constructor
        Node(T d)
        {
            data = d;
            next = null;
            size = 0;
        }
    }
//    #1. O（1） find size
//
    private Node head; // head of list
    private int size;
    public int getSize() {
        return size;
    }

//#2. Initialize a linked list from a list(can be None)
//
    public void initList(T[] arr) {
//       Corner case: arr == None
        if (arr == null || arr.length == 0){
            return;
        }
        head = new Node(arr[0]);
        int count = 1;
        size ++;
        Node cur = head;
        while (count < arr.length) {
            cur.next = new Node(arr[count]);
            cur = cur.next;
            count++;
            size++;
        }
    }
//#3.增删改查
//查
    public T getDataAtIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index!");

        Node cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return cur.data;
    }
//   insert
    public void insert(T data, int index){
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index!");

//        Corner cases
        if (size == 0) {
            if (index == 0) {
                head = new Node(data);
            }
        }
        Node cur = head;
        for (int i = 1; i < index; i++) {
            cur = cur.next;
        }
        Node temp = cur.next;
        Node newNode = new Node(data);
        cur.next = newNode;
        newNode.next = temp;
        size ++;
    }
//    delete
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index!");

        if (index == 0) {
            head = head.next;
        }
        Node cur = head;
        int count = 0;
        while(count < index - 1) {
            count++;
            cur = cur.next;
        }

        Node remove = cur.next;

        Node temp = null;
        if (cur.next.next != null){
            temp = cur.next.next;
        }

        remove.next = null;
        cur.next = temp;


        size--;
    }

//    #4. clear()
    public void clear() {
        head = null;
        size = 0;
    }
//#5. reverse(list)
//
    public void reverse(){
        if (head == null || this.size == 0 ||this.size == 1) {
            return;
        }
        Node cur = head;
        Node pre = null;
        while (cur != null) {
            Node temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        head = pre;
    }
//#6. contains(any)
//
    public boolean contains(T data) {
        Node cur = head;

        while (cur != null) {
            if (cur.data == data)
                return true;
            cur = cur.next;
        }

        return false;
    }
//#7. empty()
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }else{
            return false;
        }

    }
//
    public void printList() {

        Node cur = head;

        while( cur!= null){
            System.out.print(cur.data + " ");
            cur = cur.next;

        }

    }

    public static void main(String[] args) {
//        Instantiiate a list
        LinkedList<Integer> list = new LinkedList();
        Integer[] arr = { 0, 1, 2, 3, 4};
        list.initList(arr);

        System.out.println("Size of the list: " + list.getSize());
//        查
        System.out.println("Data at the index " + list.getDataAtIndex(1));


//        增
//        list.insert(0,1);
//        System.out.println("Data at the index " + list.getDataAtIndex(1));
//        System.out.println("Size of the list after insertions: " + list.getSize());
//        删
        System.out.println("Data at the index " + list.getDataAtIndex(1));
        list.deleteAtIndex(1);
        System.out.println("Data at the index " + list.getDataAtIndex(1));
        System.out.println("Size of the list after the deletion: " + list.getSize());


        list.printList();
    }
}

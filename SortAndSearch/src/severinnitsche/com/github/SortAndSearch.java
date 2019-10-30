package severinnitsche.com.github;

import java.util.Iterator;
import java.lang.Iterable;

/**
*
* The SortAndSearch class provides methods to sort and
*     search different data structures.
* <p>The Non Array structures are provided within it
* <p>Data Structures:
* <ul>
*   <li>LinkedList</li>
*   <li>BinaryTree</li>
* </ul>
* <p>Sorting Algortihms:
* <ul>
*   <li>Bubbles Sort</li>
*   <li>Binary Tree Sort</li>
*   <li>Quick Sort</li>
*   <li>Radix Sort MSD / LSD</li>
* </ul>
* <p>Searching Algorithms:
* <ul>
*   <li>Linear Search</li>
*   <li>Binary Tree Search</li>
* </ul>
*
*/
public class SortAndSearch {

  /**
  *
  * Returns the index of the <i>item</i> in the <i>collection</i>
  *
  */
  public static int linearSearch(int[] collection, int item) {
    for(int i = 0; i < collection.length; i++) {
      if(collection[i] == item) return i;
    }
    return -1;
  }

  /**
  *
  * Returns the sorted <i>collection</i>
  *
  */
  public static int[] bubbleSort(int[] collection) {
    for(int i = 1; i < collection.length; i++) {
      for(int j = 0; j < collection.length - i; j++) {
        if(collection[j] > collection[j+1]) {
          int temp = collection[j];
          collection[j] = collection[j+1];
          collection[j+1] = temp;
        }
      }
    }
    return collection;
  }

  /**
  *
  * The Comparator class provides a method to compare to objects
  *
  */
  public static abstract class Comparator<K> {
    public static final int GREATER = 1;
    public static final int EQUALS = 0;
    public static final int LOWER = -1;

    /**
    *
    * Returns 1 if i1 > i2, 0 if i1 = i2 and -1 if i1 < i2
    *
    */
    public abstract int compare(K i1, K i2);

    /**
    *
    * The Type Enum contains predefined Comparators for some Types
    *
    */
    public static enum Type {

      INTEGER(new Comparator<Integer>() {
        @Override public int compare(Integer i1, Integer i2) {
          return i1>i2?1:i2>i1?-1:0;
        }
      }),
      STRING(new Comparator<String>() {
        @Override public int compare(String i1, String i2) {
          if(i1.length() > i2.length()) return 1;
          if(i2.length() > i1.length()) return -1;
          for(int i=0; i<i1.length(); i++) {
            if(i1.charAt(i)-i2.charAt(i)!=0) return i1.charAt(i)-i2.charAt(i)>0?1:-1;
          }
          return 0;
        }
      });

      public final Comparator comparator;

      Type(Comparator comparator) {
        this.comparator = comparator;
      }

      /**
      *
      * Returns wether there is a predefined Comparator
      *     for the Type represented by the given class.
      *
      */
      public static boolean has(Class clazz) {
        for(Type t : Type.values()) {
          if(t.name().toUpperCase().equals(clazz.getSimpleName().toUpperCase())) return true;
        }
        return false;
      }

      /**
      *
      * The TypeIsNotPredefinedException shall be thrown if T is not
      *     a predefined Type and no Comparator was specified
      *
      */
      public static class TypeIsNotPredefinedException
          extends RuntimeException {
        public TypeIsNotPredefinedException(Class clazz) {
          super("Could not find Type "+clazz.getSimpleName()+
              " in Comparator.Type.values(). Please make sure to use a "+
              "predefined Type or specify a Comparator.");
        }
      }

      /**
      *
      * The TypeNotFoundException is a RuntimeException that shall be thrown
      *     if Type.get(Class clazz) cannot find the given type
      *
      */
      public static class TypeNotFoundException extends RuntimeException {
        public TypeNotFoundException(Class clazz) {
          super("Could not find "+clazz.getSimpleName()+"!");
        }
      }

      /**
      *
      * Returns clazz's corresponding Type
      *
      */
      public static Type get(Class clazz) {
        for(Type t : Type.values()) {
          if(t.name().toUpperCase().equals(clazz.getSimpleName().toUpperCase()))
            return t;
        }
        throw new TypeNotFoundException(clazz);
      }
    }

  }

  /**
  *
  * The Binary Tree class provides methods to populate a Binary Tree,
  *     get the root value and both sub-trees
  * <p>The use of a enhanced for loop returns the items in ascending order but
  *     is to use with care since it destroyes the tree structure
  *
  */
  public static class BinaryTree<T> implements Iterable<T> {

    /**
    *
    * The Node class provides methods to add values into a tree structure
    * <p>The use of a enhanced for loop returns the items in ascending order but
    *     is to use with care since it destroyes the tree structure
    *
    */
    private static class Node<T> implements Iterable<T>{
      T value;
      Node left;               //lower
      Node right;              //higher Or equals
      Node parent;

      /**
      *
      * Instanciates a Node with the <i>value</i> and the <i>parent</i>
      * <p>parent may be null
      *
      */
      public Node(T value, Node parent) {
        this.value = value;
        this.parent = parent;
      }

      /**
      *
      * Adds the value to the tree structure the node belongs to by
      *     passing the <i>value</i> to the subsequent Node or
      *     creating another if neccessary
      *
      */
      public void add(T value, Comparator<T> comparator) {
        if(comparator.compare(value,this.value) == Comparator.LOWER) {
        //if (value<this.value) {
          if(left == null) left = new Node<T>(value, this);
          else left.add(value, comparator);
        } else {
          if(right == null) right = new Node<T>(value, this);
          else right.add(value, comparator);
        }
      }

      /**
      *
      * The Node Iterator class implements the Iterator<Integer> interface
      *     to traverse the tree defined by Nodes
      *     and return their values in ascending order
      *
      */
      private class NodeIterator implements Iterator<T> {

        Node<T> n;

        /**
        *
        * Instanciates the Node iterator
        * <p><i>n</i> is the trees root
        *
        */
        public NodeIterator(Node n) {
          this.n = n;
        }

        @Override
        public boolean hasNext() {
          return this.n != null;
        }

        @Override
        public T next() {
          while(this.n.left != null) {
            this.n = this.n.left;
          }
          T ret = (T)this.n.value;
          if(this.n.parent != null) {
            this.n.parent.left = this.n.right;
            if(this.n.right != null) this.n.right.parent = this.n.parent;
            this.n = this.n.parent;
          } else {
            if(this.n.right != null) this.n.right.parent = null;
            this.n = this.n.right;
          }
          return ret;
        }

      }

      @Override
      public Iterator<T> iterator() {
        return new NodeIterator(this);
      }

    }

    private Node root;
    private boolean filled;
    private Comparator<T> comparator;

    /**
    *
    * Instanciates a Binary Tree with the root value <i>value</i>
    *
    */
    public BinaryTree(T value) {
      root = new Node<T>(value,null);
      filled = true;
      if (Comparator.Type.has(this.root.value.getClass())) {
        comparator = Comparator.Type.get(this.root.value.getClass()).comparator;
      } else {
        throw new Comparator.Type.TypeIsNotPredefinedException(
            this.root.value.getClass()
        );
      }
    }

    /**
    *
    * Instanciates a Binary Tree with the root value <i>value</i>
    *
    */
    public BinaryTree(T value, Comparator<T> comparator) {
      root = new Node<T>(value,null);
      filled = true;
      this.comparator = comparator;
    }

    /**
    *
    * Instanciates a Binary Tree without a starting value
    * <p>Note:
    * <p>If you instanciate the tree this way <i>isDead()</i> will return true
    *
    */
    public BinaryTree() {
      filled = false;
    }

    /**
    *
    * Instanciates a Binary Tree without a starting value
    * <p>Note:
    * <p>If you instanciate the tree this way <i>isDead()</i> will return true
    *
    */
    public BinaryTree(Comparator<T> comparator) {
      filled = false;
      this.comparator = comparator;
    }

    /**
    *
    * Creates a Binary Tree from the given Node
    *
    */
    private BinaryTree(Node n) {
      root = n;
      filled = true;
    }

    /**
    *
    * Inserts a value into the Tree structure
    *
    */
    public void add(T value) {
      if(!filled) {
        if(comparator == null) {
          if (Comparator.Type.has(value.getClass())) {
            comparator = Comparator.Type.get(value.getClass()).comparator;
          } else {
            throw new Comparator.Type.TypeIsNotPredefinedException(
                value.getClass()
            );
          }
        }
        root = new Node<T>(value,null);
        filled = true;
      } else {
        root.add(value, comparator);
      }
    }

    @Override
    public Iterator<T> iterator() {
      return root.iterator();
    }

    /**
    *
    * Returns the value of the root element
    *
    */
    public T get() {
      return (T)root.value;
    }

    /**
    *
    * Returns the left sub tree (lower)
    *
    */
    public BinaryTree getLeft() {
      return new BinaryTree(root.left);
    }

    /**
    *
    * Returns the right sub tree (higher)
    *
    */
    public BinaryTree getRight() {
      return new BinaryTree(root.right);
    }

    /**
    *
    * Returns wether the tree contains elements or not
    * <p>Note:
    * <p>If a tree is dead you still are able to insert values into it
    *
    */
    public boolean isDead() {
      return root == null;
    }
  }

  /**
  *
  * Returns the sorted <i>collection</i>
  *
  */
  public static <T> T[] binaryTreeSort(T[] collection) {
    BinaryTree<T> tree = new BinaryTree<T>();
    for(int i = 0; i < collection.length; i++) {
      tree.add(collection[i]);
    }
    int i = 0;
    for(T item : tree) {
      collection[i] = item;
      i++;
    }
    return collection;
  }

  /**
  *
  * Returns wether the <i>item</i> is contained within the <i>tree</i> or not
  *
  */
  public static <T> boolean binaryTreeSearch(BinaryTree<T> tree, T item) {
    //setting up comparator
    Comparator<T> comparator;
    if(Comparator.Type.has(item.getClass())) {
      comparator = Comparator.Type.get(item.getClass()).comparator;
    } else {
      throw new Comparator.Type.TypeIsNotPredefinedException(item.getClass());
    }
    //binaray tree search
    while(tree.get() != item) {
      if(/*tree.get() < item*/comparator.compare(tree.get(),item) == Comparator.LOWER) tree = tree.getRight();
      else tree = tree.getLeft();
      if(tree.isDead()) return false;
    }
    return true;
  }

  /**
  *
  * The Linked List class provides Linked List data strucure
  * <p>Note:
  * <p>You can get the size of the list,
  * <p>However you cannot acces individual fields
  *     since it is performance wise discouraged
  *       if you intend to loop through the list
  *       please use the enhanced for loop
  *         and if you want to acces individual fields use an array or alike
  *
  */
  public static class LinkedList<T> implements Iterable<T> {

    /**
    *
    * The Node class provides the possibility to link values together
    *
    */
    private class Node<T> {

      boolean filled;
      T value;
      Node<T> next;
      Node<T> last;

      /**
      *
      * Creates an empty Node
      *
      */
      public Node() {
        filled = false;
        last = this;
      }

      /**
      *
      * Creates a Node structure from the <i>collection</i>
      *
      */
      public Node(T...collection) {
        filled = true;
        this.value = collection[0];
        last = this;
        for(int i = 1; i < collection.length; i++) {
          add(collection[i]);
        }
      }

      /**
      *
      * Returns the last Node in the structure
      * <p>This keeps the first Node always up to date
      *     about the end of the structure
      *       which makes adding elements to it way more performant
      *
      */
      public Node add(T item) {
        if(!filled) {
          filled = true;
          value = item;
          last = this;
        } else if(next == null) {
          next = new Node<T>(item);
          last = next;
        } else {
          if(last != null) {
            last.next = new Node<T>(item);
            last = last.next;
          } else {
            last = next.add(item);
          }
        }
        return last;
      }
    }

    Node<T> root;
    private int size;

    /**
    *
    * Creates an empty Linked List
    *
    */
    public LinkedList() {
      this.root = new Node<T>();
      this.size = 0;
    }

    /**
    *
    * Creates a Linked List from the given <i>collection</i>
    *
    */
    public LinkedList(T...collection) {
      this.root = new Node<T>(collection);
      this.size = collection.length;
    }

    /**
    *
    * Returns the list for better readability in code
    *     i.e. (linkedList.add(10).add(20))
    * <p>Adds the <i>item</i> into the List
    *
    */
    public LinkedList<T> add(T item) {
      this.root.add(item);
      size++;
      return this;
    }

    /**
    *
    * Returns the list for better readability in code
    *     i.e. (linkedList.add(10).add(20))
    * <p>appends the <i>collection</i> onto the list
    * <p>Note:
    * <p>The returned list may be either
    *     <i>this</i> list or the <i>collection</i>
    *
    */
    public LinkedList<T> append(LinkedList<T> collection) {
      if(collection.size == 0) return this;
      if(this.size == 0) return collection;
      this.root.last.next = collection.root;
      this.root.last = collection.root.last;
      this.size += collection.size();
      return this;
    }

    /**
    *
    * Returns the number of elements in the List
    *
    */
    public int size() {
      return size;
    }

    /**
    *
    * The Node Iterator class implements the Iterator<Integer> interface
    *     to traverse a Linked List in performant fashion
    *
    */
    private class NodeIterator implements Iterator<T> {
      private Node<T> n;

      /**
      *
      * Creates a Node Iterator with <i>n</i> as its root element
      *
      */
      public NodeIterator(Node<T> n) {
        this.n = n;
      }

      @Override
      public boolean hasNext() {
        return this.n != null;
      }

      @Override
      public T next() {
        T ret = this.n.value;
        this.n = this.n.next;
        return ret;
      }
    }

    @Override
    public Iterator<T> iterator() {
      return new NodeIterator(root);
    }
  }

  /**
  *
  * Returns the sorted <i>collection</i>
  *
  */
  public static <T> LinkedList<T> quickSort(LinkedList<T> collection) {
    if(collection.size() <= 1) return collection;
    //setting up Comparator
    Comparator<T> comparator;
    if(Comparator.Type.has(collection.root.value.getClass())) {
      comparator = Comparator.Type.get(collection.root.value.getClass()).comparator;
    } else {
      throw new Comparator.Type.TypeIsNotPredefinedException(
          collection.root.getClass()
      );
    }
    //quick sort
    T pivot = collection.root.value;
    LinkedList<T> lower = new LinkedList<T>();
    LinkedList<T> higher = new LinkedList<T>();
    int j = 0;
    for(T i : collection) {
      if(j == 0) {
        j++;
        continue;
      }
      //i < pivot
      if(comparator.compare(i, pivot) == Comparator.LOWER) lower.add(i);
      else higher.add(i);
      j++;
    }
    return quickSort(lower).add(pivot).append(quickSort(higher));
  }

  /**
  *
  * Returns the sorted <i>collection</i> in ascending order
  * <p>Note:
  * <p><i>offset</i> needs to be one otherwise the algorithm may not work
  *
  */
  public static LinkedList<Integer> radixSortMSD(LinkedList<Integer> collection, int offset) {
    if(collection.size() <= 1 || offset > 32) return collection;
    LinkedList<Integer> zeros = new LinkedList();
    LinkedList<Integer> ones = new LinkedList();
    for(int i : collection) {
      if((i >>> (32-offset) & 1) == 0) zeros.add(i);
      else ones.add(i);
    }
    return radixSortMSD(zeros,offset+1).append(radixSortMSD(ones,offset+1));
  }

  /**
  *
  * Returns the sorted <i>collection</i>
  * <p>Note:
  * <p>Unlike the other sorting methods this method returns in descendin order
  * <p>Note:
  * <p><i>offset</i> needs to be zero otherwise the algorithm may not work
  *
  */
  public static LinkedList<Integer> radixSortLSD(LinkedList<Integer> collection, int offset) {
    if(offset > 31) return collection;
    LinkedList<Integer> zeros = new LinkedList();
    LinkedList<Integer> ones = new LinkedList();
    for(int i : collection) {
      if((i >>> (offset) & 1) == 0) zeros.add(i);
      else ones.add(i);
    }

    return radixSortLSD(ones.append(zeros),offset+1);
  }

  public static void main(String[] args) {
    //Testing Linear Search
    System.out.println("\nLinear Search:\n");
    int[] ls = new int[10];
    for(int i=0; i<ls.length; i++) {
      ls[i] = (int)(Math.random() * ls.length);
      System.out.println(ls[i]);
    }
    int lsearch = 5;
    System.out.println("Found: "+lsearch+" at: "+linearSearch(ls,lsearch));

    //Testing Bubble Sort
    System.out.println("\nBubble Sort:\n");
    System.out.println("unsorted:");
    int[] bs = new int[10];
    for(int i=0; i<bs.length; i++) {
      bs[i] = (int)(Math.random() * bs.length);
      System.out.println(bs[i]);
    }
    bubbleSort(bs);
    System.out.println("sorted");
    for(int i : bs) {
      System.out.println(i);
    }

    //Testing Binary Tree sort
    System.out.println("\nBinary Tree Sort:\n");
    Integer[] bts = new Integer[10];
    for(int i=0; i<bts.length; i++) {
      bts[i] = (int)(Math.random() * 10);
    }
    System.out.println("unsorted:");
    for(int i : bts) {
      System.out.println(i);
    }
    bts = binaryTreeSort(bts);
    System.out.println("Sorted:");
    for(int i : bts) {
      System.out.println(i);
    }

    //Testing Binary Tree Search
    System.out.println("\nBinary Tree search\n");
    BinaryTree<Integer> tree = new BinaryTree<Integer>();
    for(int i=0; i<10; i++) {
      tree.add((int)(Math.random()*10));
    }
    System.out.println("Searched 5"+binaryTreeSearch(tree,5));

    //Testing Quick Sort
    System.out.println("\nQuick Sort\n");
    LinkedList<Integer> qs = new LinkedList<Integer>();
    for(int i=0; i<10; i++) {
      qs.add((int)(Math.random()*10));
    }
    System.out.println("unsorted:");
    for(Integer i : qs) {
      System.out.println(i);
    }
    qs = quickSort(qs);
    System.out.println("sorted:");
    for(Integer i : qs) {
      System.out.println(i);
    }

    //Testing Radix Sort MSD
    System.out.println("\nRadix Sort MSD\n");
    LinkedList<Integer> rsm = new LinkedList<Integer>();
    for(int i=0; i<10; i++) {
      rsm.add((int)(Math.random()*10));
    }
    System.out.println("unsorted:");
    for(Integer i : rsm) {
      System.out.println(i);
    }
    rsm = radixSortMSD(rsm, 1);
    System.out.println("sorted:");
    for(Integer i : rsm) {
      System.out.println(i);
    }

    //Testing Radix Sort LSD
    System.out.println("\nRadix Sort LSD\n");
    LinkedList<Integer> list = new LinkedList<Integer>();
    for(int i = 0; i < 10; i++) {
      list.add((int)(Math.random()*10));
    }
    for(Integer i : list) {
      System.out.println(i);
    }
    list = radixSortLSD(list, 0);
    System.out.println("Sorted");
    for(Integer i : list) {
      System.out.println(i);
    }
  }

}

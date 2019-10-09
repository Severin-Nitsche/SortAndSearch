import java.util.Iterator;
import java.lang.Iterable;

public class SortAndSearch {

  public static int linearSearch(int[] collection, int item) {
    for(int i = 0; i < collection.length; i++) {
      if(collection[i] == item) return i;
    }
    return -1;
  }

  public static int[] bubbleSort(int[] collection) {
    for(int i = 1; i < collection.length; i++) {
      for(int j = 0; j < collection.length - i; j++) {
        if(collection[j] > collection[j+1]) {
          int temp = collection[j];
          collection[j] = collection[j+1];
          collection[j+1] = collection[j];
        }
      }
    }
    return collection;
  }


  public static class BinaryTree implements Iterable<Integer> {
    private static class Node implements Iterable<Integer>{
      int value;
      Node left;               //lower
      Node right;              //higher Or equals
      Node parent;

      public Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
      }

      public void add(int value) {
        if (value<this.value) {
          if(left == null) left = new Node(value, this);
          else left.add(value);
        } else {
          if(right == null) right = new Node(value, this);
          else right.add(value);
        }
      }

      private class NodeIterator implements Iterator<Integer> {

        Node n;

        public NodeIterator(Node n) {
          this.n = n;
        }

        @Override
        public boolean hasNext() {
          return this.n != null;
        }

        @Override
        public Integer next() {
          while(this.n.left != null) {
            this.n = this.n.left;
          }
          Integer ret = this.n.value;
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
      public Iterator<Integer> iterator() {
        return new NodeIterator(this);
      }

    }
    private Node root;
    private boolean filled;
    public BinaryTree(int value) {
      root = new Node(value,null);
      filled = true;
    }
    public BinaryTree() {
      filled = false;
    }
    private BinaryTree(Node n) {
      root = n;
      filled = true;
    }
    public void add(int value) {
      if(!filled) {
        root = new Node(value,null);
        filled = true;
      } else {
        root.add(value);
      }
    }
    @Override
    public Iterator<Integer> iterator() {
      return root.iterator();
    }
    public int get() {
      return root.value;
    }
    public BinaryTree getLeft() {
      return new BinaryTree(root.left);
    }
    public BinaryTree getRight() {
      return new BinaryTree(root.right);
    }
    public boolean isDead() {
      return root == null;
    }
  }

  public static int[] binaryTreeSort(int[] collection) {
    BinaryTree tree = new BinaryTree();
    for(int i = 0; i < collection.length; i++) {
      tree.add(collection[i]);
    }
    int i = 0;
    for(Integer item : tree) {
      collection[i] = item;
      i++;
    }
    return collection;
  }

  public static boolean binaryTreeSearch(BinaryTree tree, int item) {
    while(tree.get() != item) {
      if(tree.get() < item) tree = tree.getRight();
      else tree = tree.getLeft();
      if(tree.isDead()) return false;
    }
    return true;
  }

  public static class LinkedList implements Iterable<Integer> {
    private class Node {
      boolean filled;
      int value;
      Node next;
      Node last;
      public Node() {
        filled = false;
        last = this;
      }
      public Node(int...collection) {
        filled = true;
        this.value = collection[0];
        last = this;
        for(int i = 1; i < collection.length; i++) {
          add(collection[i]);
        }
      }
      public Node add(int item) {
        if(!filled) {
          filled = true;
          value = item;
          last = this;
        } else if(next == null) {
          next = new Node(item);
          last = next;
        } else {
          if(last != null) {
            last.next = new Node(item);
            last = last.next;
          } else {
            last = next.add(item);
          }
        }
        return last;
      }
    }
    Node root;
    private int size;
    public LinkedList() {
      this.root = new Node();
      this.size = 0;
    }
    public LinkedList(int...collection) {
      this.root = new Node(collection);
      this.size = collection.length;
    }
    public LinkedList add(int item) {
      this.root.add(item);
      size++;
      return this;
    }
    public LinkedList append(LinkedList collection) {
      if(collection.size == 0) return this;
      if(this.size == 0) return collection;
      this.root.last.next = collection.root;
      this.root.last = collection.root.last;
      this.size += collection.size();
      return this;
    }
    public int size() {
      return size;
    }
    private class NodeIterator implements Iterator<Integer> {
      private Node n;

      public NodeIterator(Node n) {
        this.n = n;
      }

      @Override
      public boolean hasNext() {
        return this.n != null;
      }

      @Override
      public Integer next() {
        Integer ret = this.n.value;
        this.n = this.n.next;
        return ret;
      }
    }
    @Override
    public Iterator<Integer> iterator() {
      return new NodeIterator(root);
    }
  }

  public static LinkedList quickSort(LinkedList collection) {
    if(collection.size() <= 1) return collection;
    int pivot = collection.root.value;
    LinkedList lower = new LinkedList();
    LinkedList higher = new LinkedList();
    int j = 0;
    for(Integer i : collection) {
      if(j == 0) {
        j++;
        continue;
      }
      if(i < pivot) lower.add(i);
      else higher.add(i);
      j++;
    }
    return quickSort(lower).add(pivot).append(quickSort(higher));
  }

  public static LinkedList radixSortMSD(LinkedList collection, int offset) {
    if(collection.size() <= 1 || offset > 32) return collection;
    LinkedList zeros = new LinkedList();
    LinkedList ones = new LinkedList();
    for(int i : collection) {
      if((i >>> (32-offset) & 1) == 0) zeros.add(i);
      else ones.add(i);
    }
    return radixSortMSD(zeros,offset+1).append(radixSortMSD(ones,offset+1));
  }

  public static void main(String[] args) {
    LinkedList list = new LinkedList();
    for(int i = 0; i < 10; i++) {
      list.add((int)(Math.random()*10));
    }
    for(Integer i : list) {
      System.out.println(i);
    }
    list = radixSortMSD(list, 1);
    System.out.println("Sorted");
    for(Integer i : list) {
      System.out.println(i);
    }
  }

}

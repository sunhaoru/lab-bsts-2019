import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import java.util.function.BiConsumer;

/**
 * A simple implementation of binary search trees.
 */
public class SimpleBST<K, V> implements SimpleMap<K, V> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The root of our tree. Initialized to null for an empty tree.
   */
  BSTNode<K, V> root;

  /**
   * The comparator used to determine the ordering in the tree.
   */
  Comparator<K> comparator;

  /**
   * The size of the tree.
   */
  int size;

  /**
   * A cached value (useful in some circumstances.
   */
  V cachedValue;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new binary search tree that orders values using the specified comparator.
   */
  public SimpleBST(Comparator<K> comparator) {
    this.comparator = comparator;
    this.root = null;
    this.size = 0;
    this.cachedValue = null;
  } // SimpleBST(Comparator<K>)

  /**
   * Create a new binary search tree that orders values using a not-very-clever default comparator.
   */
  public SimpleBST() {
    this((k1, k2) -> k1.toString().compareTo(k2.toString()));
  } // SimpleBST()


  // +-------------------+-------------------------------------------
  // | SimpleMap methods |
  // +-------------------+

  @Override
  public V set(K key, V value) {
    if (this.root == null) {
      this.root = new BSTNode<K, V>(key, value);
      this.size++;
      return null;
    }
    BSTNode<K, V> current = root;
    while (current != null) {
      if (this.comparator.compare(current.key, key) == 0) {
        V prev = current.value;
        current.value = value;
        return prev;
      }
      if (this.comparator.compare(key, current.key) < 0) {
        if (current.left == null) {
          current.left = new BSTNode<K, V>(key, value);
          this.size++;
          return null;
        } else {
          current = current.left;
        }
      } else {
        if (current.right == null) {
          current.right = new BSTNode<K, V>(key, value);
          this.size++;
          return null;
        } else {
          current = current.right;
        }
      }
    }
    return null;
  } // set(K,V)

  public V setRec(K key, V value) {
    root = setHelper(root, key, value);
    return this.cachedValue;
  }

  public BSTNode<K, V> setHelper(BSTNode<K, V> node, K key, V value) {
    if (node == null) {
      this.cachedValue = null;
      this.size++;
      return new BSTNode<K, V>(key, value);
    } else if (this.comparator.compare(key, node.key) == 0) {
      this.cachedValue = node.value;
      node.value = value;
      return node;
    } else if (this.comparator.compare(key, node.key) < 0) {
      node.left = setHelper(node.left, key, value);
      return node;
    } else {
      node.right = setHelper(node.right, key, value);
      return node;
    }

  }

  @Override
  public V get(K key) {
    if (key == null) {
      throw new NullPointerException("null key");
    } // if
    return get(key, root);
  } // get(K,V)

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return 0;
  } // size()

  @Override
  public boolean containsKey(K key) {
    // TODO Auto-generated method stub
    return false;
  } // containsKey(K)

  @Override
  public V remove(K key) {
    root = removeHelper(root, key);
    return cachedValue;
  } // remove(K)

  public BSTNode<K, V> removeHelper(BSTNode<K, V> node, K key) {
    if (node == null) {
      cachedValue = null;
      return null;
    } // if
    else {
      if (this.comparator.compare(key, node.key) == 0) {
        if (node.left == null && node.right == null) {
          cachedValue = node.value;
          return null;
        } // if leaf
        else if (node.left == null) {
          cachedValue = node.value;
          return node.right;
        } // else if node.left is null
        else if (node.right == null) {
          cachedValue = node.value;
          return node.left;
        } // else if node.right is null
        else {
          cachedValue = node.value;
          BSTNode<K, V> current = node.left;
          while (current.right != null) {
            current = current.right;
          }
          current.right = node.right;
          return node.left;
        } // else if complicated
      } // if
      else if (this.comparator.compare(key, node.key) < 0) {
        node.left = removeHelper(node.left, key);
        return node;
      } // if less
      else {
        node.right = removeHelper(node.right, key);
        return node;
      } // if greater
    }
  }

  @Override
  public Iterator<K> keys() {
    return new Iterator<K>() {
      Iterator<BSTNode<K, V>> nit = SimpleBST.this.nodes();

      @Override
      public boolean hasNext() {
        return nit.hasNext();
      } // hasNext()

      @Override
      public K next() {
        return nit.next().key;
      } // next()

      @Override
      public void remove() {
        nit.remove();
      } // remove()
    };
  } // keys()

  @Override
  public Iterator<V> values() {
    return new Iterator<V>() {
      Iterator<BSTNode<K, V>> nit = SimpleBST.this.nodes();

      @Override
      public boolean hasNext() {
        return nit.hasNext();
      } // hasNext()

      @Override
      public V next() {
        return nit.next().value;
      } // next()

      @Override
      public void remove() {
        nit.remove();
      } // remove()
    };
  } // values()

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    forEach(action, root);
  } // forEach

  void forEach(BiConsumer<? super K, ? super V> action, BSTNode<K, V> node) {
    if (node == null) {
      return;
    } // if
    forEach(action, node.left);
    action.accept(node.key, node.value);
    forEach(action, node.right);
  }

  // +----------------------+----------------------------------------
  // | Other public methods |
  // +----------------------+

  /**
   * Dump the tree to some output location.
   */
  public void dump(PrintWriter pen) {
    dump(pen, root, "");
  } // dump(PrintWriter)


  // +---------+-----------------------------------------------------
  // | Helpers |
  // +---------+


  /**
   * Dump a portion of the tree to some output location.
   */
  void dump(PrintWriter pen, BSTNode<K, V> node, String indent) {
    if (node == null) {
      pen.println(indent + "<>");
    } else {
      pen.println(indent + node.key + ": " + node.value);
      if ((node.left != null) || (node.right != null)) {
        dump(pen, node.left, indent + "  ");
        dump(pen, node.right, indent + "  ");
      } // if has children
    } // else
  } // dump

  /**
   * Get the value associated with a key in a subtree rooted at node. See the top-level get for more
   * details.
   */
  V get(K key, BSTNode<K, V> node) {
    if (node == null) {
      throw new IndexOutOfBoundsException("Invalid key: " + key);
    }
    int comp = comparator.compare(key, node.key);
    if (comp == 0) {
      return node.value;
    } else if (comp < 0) {
      return get(key, node.left);
    } else {
      return get(key, node.right);
    }
  } // get(K, BSTNode<K,V>)

  /**
   * Get an iterator for all of the nodes. (Useful for implementing the other iterators.)
   */
  Iterator<BSTNode<K, V>> nodes() {
    return new Iterator<BSTNode<K, V>>() {

      Stack<BSTNode<K, V>> stack = new Stack<BSTNode<K, V>>();
      boolean initialized = false;

      @Override
      public boolean hasNext() {
        checkInit();
        return !stack.empty();
      } // hasNext()

      @Override
      public BSTNode<K, V> next() {
        checkInit();
        // TODO Auto-generated method stub
        return null;
      } // next();

      void checkInit() {
        if (!initialized) {
          stack.push(SimpleBST.this.root);
          initialized = false;
        } // if
      } // checkInit
    }; // new Iterator
  } // nodes()

} // class SimpleBST

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * A simple implementation of binary search trees.
 */
public class SimpleBST<K,V> implements SimpleMap<K, V> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The root of our tree.  Initialized to null for an empty tree.
   */
  BSTNode<K,V> root;

  /**
   * The comparator used to determine the ordering in the tree.
   */
  Comparator<K> comparator;

  /**
   * The size of the tree.
   */
  int size;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new binary search tree that orders values using the
   * specified comparator.
   */
  public SimpleBST(Comparator<K> comparator) {
    this.comparator = comparator;
    this.root = null;
    this.size = 0;
  } // SimpleBST(Comparator<K>)

  /**
   * Create a new binary search tree that orders values using a
   * not-very-clever default comparator.
   */
  public SimpleBST() {
    this((k1,k2) -> k1.toString().compareTo(k2.toString()));
  } // SimpleBST()


  // +-------------------+-------------------------------------------
  // | SimpleMap methods |
  // +-------------------+

  @Override
  public V set(K key, V value) {
    // TODO Auto-generated method stub
    return null;
  } // set(K,V)

  @Override
  public V get(K key) {
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return null;
  } // remove(K)

  @Override
  public Iterator<K> keys() {
    // TODO Auto-generated method stub
    return null;
  } // keys()

  @Override
  public Iterator<V> values() {
    // TODO Auto-generated method stub
    return null;
  } // values()
  
  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    // TODO Auto-generated method stub
    
  } // forEach

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
  void dump(PrintWriter pen, BSTNode<K,V> node, String indent) {
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
   * Get an iterator for all of the nodes.  (Useful for implementing
   * the other iterators.)
   */
  private Iterator<BSTNode<K,V>> nodes() {
    return null;
  } // nodes()

} // class SimpleBST

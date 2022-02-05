package lab9;

import java.security.Key;
import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;
        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */
    private Node delete;
    private boolean deleteFlash = false;
    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the value mapped to by KEY in the subtree rooted in P.
     * or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) return null;
        int cmp = key.compareTo(p.key);
        if (cmp == 0) {
            return p.value;
        } else if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /**
     * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
     * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /**
     * Inserts the key KEY
     * If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }


    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
    private void preOrder(Node x , Set<K> keyset){
        if(x == null)return;
        keyset.add(x.key);
        preOrder(x.left,keyset);
        preOrder(x.right,keyset);
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        preOrder(root,set);
        return set;
    }

    /**
     * Removes KEY from the tree if present
     * returns VALUE removed,
     * null on failed removal.
     */
    private Node fetchRight(Node  x){
        Node temp = x;
        Node parent = x;
        while(temp.right != null){
            parent = temp;
            temp = temp.right;
        }
        parent.right = null;
        return temp;
    }
    private Node removeNode(Node x){
        if(x.left == null && x.right == null){
            return null;
        }
        if(x.left == null && x.right != null){
            return x.right;
        }
        if(x.left != null && x.right == null){
            return x.left;
        }
        Node leftRightest = fetchRight(x.left);
        leftRightest.right = x.right;
        if(leftRightest != x.left){
            leftRightest.left = x.left;
        }
        return leftRightest;
    }
    private Node removeHelper(Node x , K key){
        if(x == null)return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = removeHelper(x.left, key);
        }else if(cmp > 0){
            x.right = removeHelper(x.right , key);
        }else {
            size--;
            delete = x;
            deleteFlash = true;
            if(x == root){
                x = removeNode(x);
                root = x;
            }else{
                x = removeNode(x);
            }
        }
        return x;
    }
    @Override
    public V remove(K key) {
        removeHelper(root,key);
        if(!deleteFlash ){
            return null;
        }
        deleteFlash = false;
        return delete.value;
    }

    /**
     * Removes the key-value entry for the specified key only if it is
     * currently mapped to the specified value.  Returns the VALUE removed,
     * null on failed removal.
     **/
    private Node removeHelper(Node x , K key , V value ){
        if(x == null)return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.left = removeHelper(x.left, key);
        }else if(cmp > 0){
            x.right = removeHelper(x.right , key);
        }else if(x.value.equals(value)){
            size--;
            delete = x;
            deleteFlash = true;
            if(x == root){
                x = removeNode(x);
                root = x;
            }else{
                x = removeNode(x);
            }
        }
        return x;
    }
    @Override
    public V remove(K key, V value) {
        removeHelper(root,key ,value );
        if(!deleteFlash ){
            return null;
        }
        deleteFlash = false;
        return delete.value;
    }


    private List<K> keyList(){
        List<K> list = new ArrayList<>();
        preOrder(root,list);
        return list;
    }


    private void preOrder(Node x , List<K> keylist){
        if(x == null)return;
        keylist.add(x.key);
        preOrder(x.left,keylist);
        preOrder(x.right,keylist);
    }
    @Override
    public Iterator<K> iterator() {
        return keyList().iterator();
    }
}

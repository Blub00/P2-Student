package p2.binarytree;

import p2.Node;

import java.util.List;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An implementation of a red-black tree.
 * <p>
 * A red-black tree is a self-balancing binary search tree. It guarantees that the searching and inserting operation
 * have a logarithmic time complexity.
 *
 * @param <T> The type of the keys in the tree.
 * @see AbstractBinarySearchTree
 * @see RBNode
 */
public class RBTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T, RBNode<T>> {

    /**
     * The sentinel node of the tree.
     * <p>
     * The sentinel node is a special node that is used to simplify the implementation of the tree. It is a black
     * node that is used as the parent of the root node and is its own child. It is not considered part of the tree.
     */
    protected final RBNode<T> sentinel = new RBNode<>(null, Color.BLACK);

    /**
     * Creates a new, empty red-black tree.
     */
    public RBTree() {
        sentinel.setParent(sentinel);
        sentinel.setLeft(sentinel);
        sentinel.setRight(sentinel);
    }

    @Override
    public void insert(T value) {
        //TODO: H2 c)
        RBNode<T> node = createNode(value);
        insert(node, sentinel);
        fixColorsAfterInsertion(node);
    }

    /**
     * Ensures that the red-black tree properties are maintained after inserting a new node, which might have
     * added a red node as a child of another red node.
     *
     * @param z The node that was inserted.
     */
    protected void fixColorsAfterInsertion(RBNode<T> z) {
        //TODO: H2 b)
        RBNode<T> y;
        while (z.getParent().isRed()) {
            if (z.getParent().equals(z.getParent().getParent().getLeft())) {
                y = z.getParent().getParent().getRight();
                if (y != null && y.isRed()) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z.equals(z.getParent().getRight())) {
                        z = z.getParent();
                        rotateLeft(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rotateRight(z.getParent().getParent());
                }
            } else {
                y = z.getParent().getParent().getLeft();
                if (y != null && y.isRed()) {
                    z.getParent().setColor(Color.BLACK);
                    y.setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    z = z.getParent().getParent();
                } else {
                    if (z.equals(z.getParent().getLeft())) {
                        z = z.getParent();
                        rotateRight(z);
                    }
                    z.getParent().setColor(Color.BLACK);
                    z.getParent().getParent().setColor(Color.RED);
                    rotateLeft(z.getParent().getParent());
                }
            }
        }
        root.setColor(Color.BLACK);
    }

    /**
     * Rotates the given node to the left by making it the left child of its previous right child.
     * <p>
     * The method assumes that the right child of the given node is not {@code null}.
     *
     * @param x The node to rotate.
     */
    protected void rotateLeft(RBNode<T> x) {
        //TODO: H2 b)
        RBNode<T> y;
        if (x.getRight() != null) {
            y = x.getRight();
            x.setRight(y.getLeft());
            if (y.getLeft() != null) {
                y.getLeft().setParent(x);
            }
            y.setParent(x.getParent());
            if (x.getParent() == sentinel) {
                root = y;
            } else {
                if (x == x.getParent().getLeft()) {
                    x.getParent().setLeft(y);
                } else {
                    x.getParent().setRight(y);
                }
            }
            y.setLeft(x);
            x.setParent(y);
        }
    }

    /**
     * Rotates the given node to the right by making it the right child of its previous left child.
     * <p>
     * The method assumes that the left child of the given node is not {@code null}.
     *
     * @param x The node to rotate.
     */
    protected void rotateRight(RBNode<T> x) {
        //TODO: H2 b)
        if (x.getLeft() != null) {
            RBNode<T> y = x.getLeft();
            x.setLeft(y.getRight());
            if (y.hasRight()) {
                y.getRight().setParent(x);
            }
            y.setParent(x.getParent());
            if (x.getParent().equals(sentinel)) {
                root = y;
            } else {
                if (x.equals(x.getParent().getRight())) {
                    x.getParent().setRight(y);
                } else {
                    x.getParent().setLeft(y);
                }
            }
            y.setRight(x);
            x.setParent(y);
        }
    }

    @Override
    public void inOrder(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.inOrder(rbNode, result, max, predicate);
            return;
        }

        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    @Override
    public void findNext(Node<T> node, List<? super T> result, int max, Predicate<? super T> predicate) {
        if (node instanceof RBNode<T> rbNode) {
            super.findNext(rbNode, result, max, predicate);
            return;
        }
        if (node != null) throw new IllegalArgumentException("Node must be of type RBNode");
    }

    /**
     * Joins this red-black tree with another red-black tree by inserting a join-key.
     * <p>
     * The method assumes that both trees are non-empty and every element in this tree is less than every element in
     * the other tree. Additionally, it assumes that the join-key is greater than every element in this tree and less
     * than every element in the other tree.
     * <p>
     * The method modifies the tree in place, so that the other tree is merged into this tree. The other tree is
     * effectively destroyed in the process, i.e. it is not guaranteed that it is a valid red-black tree anymore.
     * <p>
     * It works by first finding two nodes in both trees with the same black height. For the tree with the smaller black
     * height, this is the node root node. For the tree with the larger black height, this is the largest or smallest
     * node with the same black height as the root node of the other tree. Then, it creates a new, red, node with the
     * join-key as the key and makes it the parent of the two previously found nodes. Finally, it fixes the colors of
     * the tree to ensure that the red-black tree properties are maintained.
     *
     * @param other   The other red-black tree to join with this tree.
     * @param joinKey The key to insert into the tree to join the two trees.
     */
    public void join(RBTree<T> other, T joinKey) {
        //TODO: H4 c)

        RBNode<T> joinedKey = createNode(joinKey);
        RBNode<T> nodeThis;
        RBNode<T> nodeOther;

        if (this.blackHeight() == other.blackHeight()) {

            nodeThis = this.findBlackNodeWithBlackHeight(other.blackHeight(), this.blackHeight(), false);
            nodeOther = other.getRoot();

            joinedKey.setLeft(nodeThis);
            nodeThis.setParent(joinedKey);
            joinedKey.setRight(nodeOther);
            nodeOther.setParent(joinedKey);
            root = joinedKey;
            other.root = joinedKey;
            joinedKey.setParent(sentinel);


        } else if (this.blackHeight() < other.blackHeight()) {

            nodeOther = other.findBlackNodeWithBlackHeight(this.blackHeight(), other.blackHeight(), true);
            nodeThis = this.getRoot();

            joinedKey.setLeft(nodeThis);
            nodeThis.setParent(joinedKey);
            joinedKey.setRight(nodeOther);
            nodeOther.setParent(joinedKey);
            root = joinedKey;
            other.root = joinedKey;
            joinedKey.setParent(sentinel);

        } else {

            nodeThis = this.findBlackNodeWithBlackHeight(other.blackHeight(), this.blackHeight(), false);
            nodeOther = other.getRoot();

            joinedKey.setLeft(nodeThis);
            nodeThis.setParent(joinedKey);
            joinedKey.setRight(nodeOther);
            nodeOther.setParent(joinedKey);
            root = joinedKey;
            other.root = joinedKey;
            joinedKey.setParent(sentinel);
        }

        fixColorsAfterInsertion(joinedKey);
    }

    /**
     * Returns the black height of the tree, i.e. the number of black nodes on a path from the root to a leaf.
     *
     * @return the black height of the tree.
     */
    private int blackNodes = 1;

    public int blackHeight() {
        //TODO: H4 a)
        if(getRoot().getLeft() != null) {
            blackCounter(getRoot().getLeft());
        }else{
            blackCounter(getRoot().getRight());
        }
        return blackNodes;
    }

    private void blackCounter(RBNode<?> node) {
        if (node != null) {
            if (node.isBlack()) {
                blackNodes++;
            }
            blackCounter(node.getLeft());
        }
    }

    /**
     * Finds a black node with the given black height in the tree.
     * <p>
     * Depending on the value of the {@code findSmallest} parameter, the method finds the smallest or largest node with the
     * target black height.
     * <p>
     * It assumes that the tree is non-empty and that there is a node with the target black height.
     *
     * @param targetBlackHeight The target black height to find a node with.
     * @param totalBlackHeight  The total black height of the tree.
     * @param findSmallest      Whether to find the smallest or largest node with the target black height.
     * @return A black node with the target black height.
     */

    public RBNode<T> findBlackNodeWithBlackHeight(int targetBlackHeight, int totalBlackHeight, boolean findSmallest) {
        //TODO: H4 b)
        RBNode<T> node = getRoot();
        if (targetBlackHeight == totalBlackHeight) {
            return node;
        }
        for (int i = targetBlackHeight; i < totalBlackHeight; i++) {
            if (findSmallest) {
                if (node.getLeft() == null) {
                    return node;
                }
                node = node.getLeft();
                if (node.isRed()) {
                    node = node.getLeft();
                }
            } else {
                if (node.getRight() == null) {
                    return node;
                }
                node = node.getRight();
                if (node.isRed()) {
                    node = node.getRight();
                }
            }
        }
        return node;
    }

    @Override
    protected RBNode<T> createNode(T key) {
        return new RBNode<>(key, Color.RED);
    }
}

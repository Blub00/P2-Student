package p2.binarytree;


import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A class for checking the rules of a red-black tree.
 */
public class RBTreeChecker {


    /**
     * Checks if the given tree satisfies all the rules of a red-black tree.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy any of the rules.
     */
    public static void checkAllRules(RBTree<?> rbTree) {
        checkRule1(rbTree);
        checkRule2(rbTree);
        checkRule3(rbTree);
        checkRule4(rbTree);
    }



    private static void destructure(RBNode<?> rbNode) {
        if (rbNode != null) {
            if (rbNode.getColor() == null) {
                throw new RBTreeException("The node of the Tree must be Red or Black");
            }
            destructure(rbNode.getLeft());
            destructure(rbNode.getRight());
        }
    }

    /**
     * Checks if the given tree satisfies the first rule of black tree.
     * <p>
     * The first rule of a red-black tree states that every node is either red or black, i.e. its color is not {@code null}.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule1(RBTree<?> rbTree) {
        //TODO: H1 a)
        if (rbTree.getRoot() != null ){
            destructure(rbTree.getRoot());
        }
    }

    /**
     * Checks if the given tree satisfies the second rule of black tree.
     * <p>
     * The second rule of a red-black tree states that the root of the tree is black.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule2(RBTree<?> rbTree) {
        //TODO: H1 b)
        if (rbTree == null || rbTree.getRoot() == null){
            return;
        }
            if(!rbTree.getRoot().isBlack()){
                throw new RBTreeException("The root of the RBTree must be black");
            }
    }

    /**
     * Checks if the given tree satisfies the third rule of black tree.
     * <p>
     * The third rule of a red-black tree states that no red node has a red child.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule3(RBTree<?> rbTree) {
        //TODO: H1 c)
    }

    /**
     * Checks if the given tree satisfies the fourth rule of black tree.
     * <p>
     * The fourth rule of a red-black tree states that all paths from a node to a leave or half-leave contain the same number of black nodes.
     *
     * @param rbTree the tree to check.
     * @throws RBTreeException if the tree does not satisfy the rule.
     */
    public static void checkRule4(RBTree<?> rbTree) {
        crash(); //TODO: H1 a) - remove if implemented
    }

}

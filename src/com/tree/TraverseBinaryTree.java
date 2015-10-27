package com.tree;

import java.util.Stack;

/**
 * Created by muzilan on 15/10/24.
 */
public class TraverseBinaryTree {
    protected Node root;

    public TraverseBinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    /**
     * 构造树
     */
    public static Node init() {
        Node a = new Node('A');
        Node b = new Node('B', null, a);
        Node c = new Node('C');
        Node d = new Node('D', b, c);
        Node e = new Node('E');
        Node f = new Node('F', e, null);
        Node g = new Node('G', null, f);
        Node h = new Node('H', d, g);
        return h;// root
    }

    /**
     * 访问节点
     */
    public static void visit(Node p) {
        System.out.print(p.getKey() + " ");
    }

    /**
     * 递归实现前序遍历
     */
    protected static void preorder(Node p) {
        if (p != null) {
            visit(p);
            preorder(p.getLeft());
            preorder(p.getRight());
        }
    }

    /**
     * 递归实现中序遍历
     */
    protected static void inorder(Node p) {
        if (p != null) {
            inorder(p.getLeft());
            visit(p);
            inorder(p.getRight());
        }
    }

    /**
     * 递归实现后序遍历
     */
    protected static void postorder(Node p) {
        if (p != null) {
            postorder(p.getLeft());
            postorder(p.getRight());
            visit(p);
        }
    }
    /**********************************************************************************************/
    /**
     * 非递归实现前序遍历
     */
    protected static void iterativePreorder(Node p) {
        Stack<Node> stack = new Stack<Node>();
        if (p != null) {
            stack.push(p);
            while (!stack.empty()) {
                p = stack.pop();
                visit(p);
                if (p.getRight() != null)
                    stack.push(p.getRight());
                if (p.getLeft() != null)  //为什么p.getLeft() 在后，getRight()在前应为while 循环第一句就是pop visit所以要把left放上，先访问。之中方法是即压即访问法。
                    stack.push(p.getLeft());
            }
        }
    }

    /**
     * 非递归实现中序遍历
     */  //思路与上面iterativePreorder 一致。
    protected static void iterativeInorder(Node p) {
        Stack<Node> stack = new Stack<Node>();
        while (p != null) {
            while (p != null) {
                if (p.getRight() != null)
                    stack.push(p.getRight());// 当前节点右子入栈
                stack.push(p);// 当前节点入栈
                p = p.getLeft();
            }
            p = stack.pop();
            while (!stack.empty() && p.getRight() == null) {
                visit(p);
                p = stack.pop();
            }
            visit(p);
            if (!stack.empty())
                p = stack.pop();
            else
                p = null;
        }
    }

/*******************************************************************************************/

/*******************************************************************************************/
    /**
     * 非递归实现前序遍历2
     */
    protected static void iterativePreorder2(Node p) {
        Stack<Node> stack = new Stack<Node>();
        Node node = p;
        while (node != null || stack.size() > 0) {
            while (node != null) {//压入所有的左节点，压入前访问它。左节点压入完后pop访问右节点。像这样算法时思考规律性的东西在哪。不管哪个节点都要压所节点判断右节点。
                visit(node);
                stack.push(node);
                node = node.getLeft();
            }
            if (stack.size() > 0) {//
                node = stack.pop();
                node = node.getRight();
            }
        }
    }

    /**
     * 非递归实现中序遍历2
     */
    protected static void iterativeInorder2(Node p) {
        Stack<Node> stack = new Stack<Node>();
        Node node = p;
        while (node != null || stack.size() > 0) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            if (stack.size() > 0) {
                node = stack.pop();
                visit(node);   //与iterativePreorder2比较只有这句话的位置不一样，弹出时再访问。
                node = node.getRight();
            }
        }
    }

    /*******************************************************************************************/

    /**
     * 非递归实现后序遍历
     */
    protected static void iterativePostorder(Node p) {
        Node q = p;
        Stack<Node> stack = new Stack<Node>();
        while (p != null) {
            // 左子树入栈
            for (; p.getLeft() != null; p = p.getLeft())
                stack.push(p);
            // 当前节点无右子或右子已经输出
            while (p != null && (p.getRight() == null || p.getRight() == q)) {
                visit(p);
                q = p;// 记录上一个已输出节点
                if (stack.empty())
                    return;
                p = stack.pop();
            }
            // 处理右子
            stack.push(p);
            p = p.getRight();
        }
    }

    /**
     * 非递归实现后序遍历 双栈法
     */
    protected static void iterativePostorder2(Node p) {//理解左子树   右子树 根递归性质，把它运用到循环当中去。
        Stack<Node> lstack = new Stack<Node>();//左子树栈
        Stack<Node> rstack = new Stack<Node>();//右子树栈
        Node node = p, right;
        do {
            while (node != null) {
                right = node.getRight();
                lstack.push(node);
                rstack.push(right);
                node = node.getLeft();
            }
            node = lstack.pop();
            right = rstack.pop();
            if (right == null) {
                visit(node);
            } else {
                lstack.push(node);
                rstack.push(null);
            }
            node = right;
        } while (lstack.size() > 0 || rstack.size() > 0);
    }

    /**
     * 非递归实现后序遍历 单栈法
     */
    protected static void iterativePostorder3(Node p) {
        Stack<Node> stack = new Stack<Node>();
        Node node = p, prev = p;
        while (node != null || stack.size() > 0) {
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }
            if (stack.size() > 0) {
                Node temp = stack.peek().getRight();
                if (temp == null || temp == prev) {
                    node = stack.pop();
                    visit(node);
                    prev = node;
                    node = null;
                } else {
                    node = temp;
                }
            }

        }
    }

    /**
     * 非递归实现后序遍历4 双栈法
     */
    protected static void iterativePostorder4(Node p) {
        Stack<Node> stack = new Stack<Node>();
        Stack<Node> temp = new Stack<Node>();
        Node node = p;
        while (node != null || stack.size() > 0) {
            while (node != null) {
                temp.push(node);
                stack.push(node);
                node = node.getRight();
            }
            if (stack.size() > 0) {
                node = stack.pop();
                node = node.getLeft();
            }
        }
        while (temp.size() > 0) {//把插入序列都插入到了temp。
            node = temp.pop();
            visit(node);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TraverseBinaryTree tree = new TraverseBinaryTree(init());
        System.out.print(" 递归遍历 \n");
        System.out.print(" Pre-Order:");
        preorder(tree.getRoot());

        System.out.print(" \n In-Order:");
        inorder(tree.getRoot());

        System.out.print("\n Post-Order:");
        postorder(tree.getRoot());

        System.out.print(" \n非递归遍历");
        System.out.print(" \n Pre-Order:");
        iterativePreorder(tree.getRoot());

        System.out.print("\n Pre-Order2:");
        iterativePreorder2(tree.getRoot());

        System.out.print(" \n In-Order:");
        iterativeInorder(tree.getRoot());

        System.out.print("\n In-Order2:");
        iterativeInorder2(tree.getRoot());

        System.out.print("\n Post-Order:");
        iterativePostorder(tree.getRoot());

        System.out.print("\n Post-Order2:");
        iterativePostorder2(tree.getRoot());

        System.out.print("\n Post-Order3:");
        iterativePostorder3(tree.getRoot());

        System.out.print("\n Post-Order4:");
        iterativePostorder4(tree.getRoot());


    }

}


class Node {
    private char key;
    private Node left, right;

    public Node(char key) {
        this(key, null, null);
    }

    public Node(char key, Node left, Node right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}

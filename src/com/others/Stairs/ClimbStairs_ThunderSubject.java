package com.others.Stairs;


import java.util.ArrayList;
import java.util.List;

/**
 * Description: 一个人爬楼梯，一步可以迈一级，二级，三级台阶，如果楼梯有N级，编写程序，输出所有走法。java实现。
 * Thinking: 先试探性的走出第一步,在第一步的基础上递归处理剩下的台阶数,直到最后走出一棵树,然后遍历树,打印出路径
 *
 * @author karl
 */
public class ClimbStairs_ThunderSubject {


    public static final int step_1 = 1; //一步一级
    public static final int step_2 = 2; //一步二级
    public static final int step_3 = 3; //一步三级

    /**
     * Description: 开始走楼梯,即创建基于根节点root的树
     *
     * @param root 根节点
     * @param n    楼梯级数
     */
    public static void climbStaris_CreateTree(Node root, int n) {
        if (n == 1) {   //若台阶数为1,则只能走一步一级
            Node step = new Node(step_1);
            root.children.add(step);
        } else if (n == 2) { //若台阶数为2,第一步可以走一级,也可以走两阶,用for循环处理
            for (int i = 1; i <= 2; i++) {
                Node step = new Node(i);
                root.children.add(step);

                //走完第一步,剩下的台阶数递归处理
                int left = n - i;
                climbStaris_CreateTree(step, left);
            }
        } else if (n >= 3) { //做台阶数大于等于3,则第一步有三种走法,一级,二级,或三级,for循环处理
            for (int i = 1; i <= 3; i++) {
                Node step = new Node(i);
                root.children.add(step);

                //走完第一步,剩下的台阶数递归处理
                int left = n - i;
                climbStaris_CreateTree(step, left);
            }
        }


    }


    /**
     * Description: 打印楼梯的走法,即遍历整个树
     *
     * @param root     需要遍历的父节点
     * @param rootPath 需要遍历的父节点相对于树根节点的路径
     */
    public static void printPath_ergodicTree(Node root, String rootPath) {
        //若传入的父节点即为叶子几点,则直接打印其路径,完毕后回,遍历下一个节点
        if (root.children.size() == 0) {
            System.out.println(rootPath);
            return;
        }

        // 做传入的父节点有子节点,则递归遍历其子节点
        for (int i = 0; i < root.children.size(); i++) {
            Node child = root.children.get(i);
            String childPath = rootPath + "-" + String.valueOf(child.val);

            //递归处理其子节点
            printPath_ergodicTree(child, childPath);
        }

    }


    public static void main(String[] args) {
        Node root = new Node();
        climbStaris_CreateTree(root, 4);
        printPath_ergodicTree(root, "start");
    }

}


/**
 * Description: 定义树的节点
 *
 * @author karl
 */

class Node {

    int val;
    List<Node> children = new ArrayList<Node>();

    Node() {
    }

    Node(int val) {
        this.val = val;
    }


}








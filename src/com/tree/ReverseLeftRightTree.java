package com.tree;

import java.util.LinkedList;
import java.util.Queue;
/**
 * Created by muzilan on 15/10/27.
 */
public class ReverseLeftRightTree {
    protected  void reverserLeftAndRight(Node p) {
        if(p==null){//这句话绝对不能丢。不然有空指针异常，因为后面操作了p.getLeft();递归的话递归结束条件很重要，所以p==null 判断很重要，不要根节点就不会。要考虑所有情况。
            return;
        }
        if(null==p.getLeft()&&null==p.getRight())
            return;
        Node temp=p.getLeft();
        p.setLeft(p.getRight());
        p.setRight(temp);
        reverserLeftAndRight(p.getLeft());
        reverserLeftAndRight(p.getRight());
    }

    protected  void reverserLeftAndRight1(Node root) {
        if(root==null){
            return;
        }
        if(null==root.getLeft()&&null==root.getRight())
            return;
        Queue<Node> qu=new LinkedList<Node>();
        qu.add(root);
        Node temp;
        Node q=root;
        while(!qu.isEmpty()){
            if(null!=q.getLeft()){//这个必须有。不然把是null 的也加进来了。
                qu.add(q.getLeft());
            }
            if(null!=q.getRight()){
                qu.add(q.getRight());
            }
            temp=q.getLeft();
            q.setLeft(q.getRight());
            q.setRight(temp);
            q=qu.remove();
        }
    }

    /** 得到根结点到叶子结点的路径*/
//    protected  void getPathFromRootToLeaf(Node p) {
//        //this.path;
//        if(null==p.getLeft()&&null==p.getRight()){
//            path[pathLength++]=p.getKey();
//            System.out.print("\n路径：\n");
//            for(int i=0;i<pathLength;i++){
//                System.out.print("  "+path[i]);
//            }
//            System.out.print("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//            pathLength--;//注意：每pathLength++，就要对应pathLength--;
//            return;
//        }else{
//            if(null!=p.getLeft()){
//                path[pathLength++]=p.getKey();
//                getPathFromRootToLeaf(p.getLeft());
//                pathLength--;//每pathLength++，就要对应pathLength--;
//            }
//            if(null!=p.getRight()){
//                path[pathLength++]=p.getKey();
//                getPathFromRootToLeaf(p.getRight());
//                pathLength--;//每pathLength++，就要对应pathLength--;
//            }
//        }
//        //pathLength--;
//    }
}

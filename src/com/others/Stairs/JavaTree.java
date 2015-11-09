package com.others.Stairs;
import java.util.ArrayList;

/**
 * Created by muzilan on 15/10/29.
 */
public class JavaTree
{
    public JavaTree left;
    public JavaTree center;
    public JavaTree right;
    public JavaTree father;

    public boolean isLeaf = false;

    public Integer value;

    public static ArrayList<JavaTree> leaves = new ArrayList<JavaTree>();

    public JavaTree getLeft()
    {
        return left;
    }

    public void setLeft(JavaTree left)
    {
        this.left = left;
    }

    public JavaTree getCenter()
    {
        return center;
    }

    public void setCenter(JavaTree center)
    {
        this.center = center;
    }

    public JavaTree getRight()
    {
        return right;
    }

    public void setRight(JavaTree right)
    {
        this.right = right;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }


    public boolean isLeaf()
    {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf)
    {
        this.isLeaf = isLeaf;
    }

    public JavaTree getFather()
    {
        return father;
    }

    public void setFather(JavaTree father)
    {
        this.father = father;
    }

    public void addTree(int n, JavaTree fa)
    {
        if(n==0)
        {
            setLeaf(true);
            leaves.add(fa);
            return;
        }

        setValue(n);
        if(fa!=null)
        {
            setFather(fa);
        }

        if(n>=3)
        {
            left = new JavaTree();
            center = new JavaTree();
            right = new JavaTree();

            left.setValue(n-1);
            left.addTree(n-1, this);

            center.setValue(n-2);
            center.addTree(n-2, this);

            right.setValue(n-3);
            right.addTree(n-3, this);
        }else if(1<n&&n<=2)
        {
            left = new JavaTree();
            center = new JavaTree();

            left.setValue(n-1);
            left.addTree(n-1, this);

            center.setValue(n-2);
            center.addTree(n-2, this);
        }else if(n==1)
        {
            left = new JavaTree();

            left.setValue(n-1);
            left.addTree(n-1, this);
        }
    }

    public void display()
    {
        System.out.print(getValue()+"	");
    }

    public void display(JavaTree leaf)
    {
        leaf.display();
        if(leaf.getFather()!=null)
        {
            display(leaf.getFather());
        }
    }

    public static void main(String[] args)
    {
        JavaTree root = new JavaTree();
        root.addTree(1, null);

        int count = 0;
        for(JavaTree temp:JavaTree.leaves)
        {
            temp.display(temp);
            System.out.println();
            count++;
        }
        System.out.println("*****************" + count);
//	JavaTree temp = JavaTree.leaves.get(0);
//	temp.display(temp);
    }
}


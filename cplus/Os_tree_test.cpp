#include <iostream>
#include "Os_Tree.h"
using namespace std;

int main()
{
    rb_tree<int, int, identity<int>, less<int> > itree;
    cout << itree.size() << endl;
    itree.insert_unique(10);
    itree.insert_unique(7);
    itree.insert_unique(8);
    itree.insert_unique(15);
    itree.insert_unique(5);
    itree.insert_unique(6);
    itree.insert_unique(11);
    itree.insert_unique(13);
    itree.insert_unique(12);

    cout << itree.size() << endl;
    for(; ite1 != ite2; ++ite1)
        cout << *ite1 << ' ';
    cout << endl;

    //测试颜色和operator＋＋
    rb_tree<int, int, identity<int>, less<int> >::iterator
        ite1 = itree.begin();
    rb_tree<int, int, identity<int>, less<int> >::iterator
            ite2 = itree.end();
    __rb_tree_base_iterator rbtite;

    for(; ite1 != ite2; ++ite1) {
        rbtite = __rb_tree_base_iterator(ite1);
        cout << *ite1 << '(' << rbtite.node->color << ")";
    }
    cout << endl;

    int num = 5;
    cout << itree.Os_Rank(itree->root)->value_type << endl;
    cout << *itree.Os_Select(itree->root, num) << endl;
}
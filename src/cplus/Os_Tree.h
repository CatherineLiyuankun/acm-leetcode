//G++ 2.91.57，cygnus\cygwin-b20\include\g++\stl_tree.h 完整列表

#ifndef __SGI_STL_INTERNAL_TREE_H
#define __SGI_STL_INTERNAL_TREE_H

/*
(1) header 不仅指向 root，也指向红黑树的最左节点，以便实现出常数时间之
begin()；並且也指向红黑树的最右节点，以便set 相关泛型演算法（如set_union 
等等）有线性时间之表现。

(2) 当一个即将被刪除之节点擁有两个子节点时，它的successor node is
relinked into its place, rather than copied, 如此一来唯一失效（invalidated）的迭代器就只是那些referring to the deleted node.

主要基于《STL源码剖析》作者 侯捷
*/

#include <stl_algobase.h>
#include <stl_alloc.h>
#include <stl_construct.h>
#include <stl_function.h>

__STL_BEGIN_NAMESPACE 


//---------------------------------------------RB-tree  节点设计------------------------------------------

typedef bool __rb_tree_color_type;
typedef int __rb_tree_node_size;
const __rb_tree_color_type __rb_tree_red = false;    // 红色为 0
const __rb_tree_color_type __rb_tree_black = true; // 黑色为 1

struct __rb_tree_node_base
{
  typedef __rb_tree_color_type color_type;
  typedef __rb_tree_node_base* base_ptr;
  typedef __rb_tree_node_size node_size;

  color_type color;     // 节点，非红即黑。
  base_ptr parent;      // RB 树的许多操作，必须知道父节点。
  base_ptr left;        // 指向左节点。
  base_ptr right;       // 指向右节点。
  node_size nodeSize;  // 本节点为根的子树（包括其本身）的节点数

  static base_ptr minimum(base_ptr x)
  {
    while (x->left != 0) x = x->left;   // 一直向左走，就会找到最小值，
    return x;                           // 这是二元搜索树的特性。
  }

  static base_ptr maximum(base_ptr x)
  {
    while (x->right != 0) x = x->right;     // 一直向右走，就会找到最大值，
    return x;                           // 这是二元搜寻树的特性。
  }
};

template <class Value>
struct __rb_tree_node : public __rb_tree_node_base
{
  typedef __rb_tree_node<Value>* link_type;
  Value value_field;    // 节点实值
};

//---------------------------------------------RB-tree  迭代器------------------------------------------

struct __rb_tree_base_iterator
{
  typedef __rb_tree_node_base::base_ptr base_ptr;
  typedef bidirectional_iterator_tag iterator_category;
  typedef ptrdiff_t difference_type;

  base_ptr node;    // 它用来与容器之间产生一个连结关系（make a reference）

  // 以下其实可实现于 operator++ 內，因为再无他处会调用此函数了。
  void increment()
  {
    if (node->right != 0) {     // 如果有右子节点。状况(1)
      node = node->right;       // 就向右走
      while (node->left != 0)   // 然后一直往左子树走到底
        node = node->left;      // 即是解答
    }
    else {                  // 沒有右子节点。状况(2)
      base_ptr y = node->parent;    // 找出父节点
      while (node == y->right) {    // 如果现行节点本身是个右子节点，
        node = y;               // 就一直上溯，直到「不为右子节点」止。
        y = y->parent;
      }
      if (node->right != y)     // 「若此时的右子节点不等于此时的父节点」。
        node = y;               // 状况(3) 此时的父节点即为解答。
                                      // 否则此时的node 为解答。状况(4)
    }                       
    // 注意，以上判断「若此时的右子节点不等于此时的父节点」，是为了应付一种
    // 特殊情況：我们欲寻找根节点的下一节点，而恰巧根节点无右子节点。
    // 当然，以上特殊作法必須配合 RB-tree 根节点与特殊之header 之间的
    // 特殊关系。
  }

  // 以下其实可实现于 operator-- 內，因为再无他处会调用此函数了。
  void decrement()
  {
    if (node->color == __rb_tree_red && // 如果是红节点，且
        node->parent->parent == node)       // 父节点的父节点等于自己，
      node = node->right;               // 状况(1) 右子节点即为解答。
    // 以上情況发生于node为header时（亦即 node 为 end() 时）。
    // 注意，header 之右子节点即 mostright，指向整棵树的 max 节点。
    else if (node->left != 0) {         // 如果有左子节点。状况(2)
      base_ptr y = node->left;          // 令y指向左子节点
      while (y->right != 0)             // 当y有右子节点时
        y = y->right;                   // 一直往右子节点走到底
      node = y;                     // 最后即为答案
    }
    else {                          // 既非根节点，亦无左子节点。
      base_ptr y = node->parent;            // 状况(3) 找出父节点
      while (node == y->left) {         // 当现行节点身为左子节点
        node = y;                       // 一直交替往上走，直到现行节点
        y = y->parent;                  // 不为左子节点
      }
      node = y;                     // 此时之父节点即为答案
    }
  }
};

template <class Value, class Ref, class Ptr>
struct __rb_tree_iterator : public __rb_tree_base_iterator
{
  typedef Value value_type;
  typedef Ref reference;
  typedef Ptr pointer;
  typedef __rb_tree_iterator<Value, Value&, Value*>     iterator;
  typedef __rb_tree_iterator<Value, const Value&, const Value*> const_iterator;
  typedef __rb_tree_iterator<Value, Ref, Ptr>   self;
  typedef __rb_tree_node<Value>* link_type;

  __rb_tree_iterator() {}
  __rb_tree_iterator(link_type x) { node = x; }
  __rb_tree_iterator(const iterator& it) { node = it.node; }

  reference operator*() const { return link_type(node)->value_field; }
#ifndef __SGI_STL_NO_ARROW_OPERATOR
  pointer operator->() const { return &(operator*()); }
#endif /* __SGI_STL_NO_ARROW_OPERATOR */

  self& operator++() { increment(); return *this; }
  self operator++(int) {
    self tmp = *this;
    increment();
    return tmp;
  }
    
  self& operator--() { decrement(); return *this; }
  self operator--(int) {
    self tmp = *this;
    decrement();
    return tmp;
  }
};

inline bool operator==(const __rb_tree_base_iterator& x,
                       const __rb_tree_base_iterator& y) {
  return x.node == y.node;
  // 两个迭代器相等，意指其所指的节点相等。
}

inline bool operator!=(const __rb_tree_base_iterator& x,
                       const __rb_tree_base_iterator& y) {
  return x.node != y.node;
  // 两个迭代器不等，意指其所指的节点不等。
}

#ifndef __STL_CLASS_PARTIAL_SPECIALIZATION

inline bidirectional_iterator_tag
iterator_category(const __rb_tree_base_iterator&) {
  return bidirectional_iterator_tag();
}

inline __rb_tree_base_iterator::difference_type*
distance_type(const __rb_tree_base_iterator&) {
  return (__rb_tree_base_iterator::difference_type*) 0;
}

template <class Value, class Ref, class Ptr>
inline Value* value_type(const __rb_tree_iterator<Value, Ref, Ptr>&) {
  return (Value*) 0;
}

#endif /* __STL_CLASS_PARTIAL_SPECIALIZATION */

// 以下都是全域函数：__rb_tree_rotate_left(), __rb_tree_rotate_right(),
// __rb_tree_rebalance(), __rb_tree_rebalance_for_erase()

// 新节点必为红节点。如果安插处之父节点亦为红节点，就违反红黑树规则，此时必須
// 做树形旋转（及颜色改变，在程式它处）。
inline void 
__rb_tree_rotate_left(__rb_tree_node_base* x, __rb_tree_node_base*& root)
{
  // x 为旋转点
  __rb_tree_node_base* y = x->right;    // 令y 为旋转点的右子节点
  x->right = y->left;
  if (y->left !=0)
    y->left->parent = x;        // 別忘了回马枪设定父节点
  y->parent = x->parent;

  // 令 y 完全顶替 x 的地位（必須将 x 对其父节点的关系完全接收过来）
  if (x == root)                    // x 为根节点
    root = y;
  else if (x == x->parent->left)    // x 为其父节点的左子节点
    x->parent->left = y;
  else                          // x 为其父节点的右子节点
    x->parent->right = y;           
  y->left = x;
  x->parent = y;
  //对nodeSize信息维护
  y->nodeSize = x->nodeSize;
  x->nodeSize = x->left->nodeSize + x->right->nodeSize+ 1;
}

// 新节点必为红节点。如果安插处之父节点亦为红节点，就违反红黑树规则，此时必須
// 做树形旋转（及颜色改变，在程式它处）。
inline void 
__rb_tree_rotate_right(__rb_tree_node_base* x, __rb_tree_node_base*& root)
{
  // x 为旋转点
  __rb_tree_node_base* y = x->left; // y 为旋转点的左子节点
  x->left = y->right;
  if (y->right != 0)
    y->right->parent = x;   // 別忘了回马枪设定父节点
  y->parent = x->parent;

  // 令 y 完全顶替 x 的地位（必須将 x 对其父节点的关系完全接收过来）
  if (x == root)                    // x 为根节点
    root = y;
  else if (x == x->parent->right)   // x 为其父节点的右子节点
    x->parent->right = y;
  else                          // x 为其父节点的左子节点
    x->parent->left = y;
  y->right = x;
  x->parent = y;
    //对nodeSize信息维护
    y->nodeSize = x->nodeSize;
    x->nodeSize = x->left->nodeSize + x->right->nodeSize+ 1;
}

// 重新令树形平衡（改变颜色及旋转树形）
// 参数一为新增节点，参数二为 root
inline void 
__rb_tree_rebalance(__rb_tree_node_base* x, __rb_tree_node_base*& root)
{
  x->color = __rb_tree_red;     // 新节点必为红
  while (x != root && x->parent->color == __rb_tree_red) { // 父节点为红
    if (x->parent == x->parent->parent->left) { // 父节点为祖父节点之左子节点
      __rb_tree_node_base* y = x->parent->parent->right;    // 令y 为伯父节点
      if (y && y->color == __rb_tree_red) {         // 伯父节点存在，且为红
        x->parent->color = __rb_tree_black;         // 更改父节点为黑
        y->color = __rb_tree_black;             // 更改伯父节点为黑
        x->parent->parent->color = __rb_tree_red;   // 更改祖父节点为红
        x = x->parent->parent;
      }
      else {    // 无伯父节点，或伯父节点为黑
        if (x == x->parent->right) { // 如果新节点为父节点之右子节点
          x = x->parent;
          __rb_tree_rotate_left(x, root); // 第一参数为左旋点
        }
        x->parent->color = __rb_tree_black; // 改变颜色
        x->parent->parent->color = __rb_tree_red;
        __rb_tree_rotate_right(x->parent->parent, root); // 第一参数为右旋点
      }
    }
    else {  // 父节点为祖父节点之右子节点
      __rb_tree_node_base* y = x->parent->parent->left; // 令y 为伯父节点
      if (y && y->color == __rb_tree_red) {     // 有伯父节点，且为红
        x->parent->color = __rb_tree_black;     // 更改父节点为黑
        y->color = __rb_tree_black;                 // 更改伯父节点为黑
        x->parent->parent->color = __rb_tree_red;   // 更改祖父节点为红
        x = x->parent->parent;  // 准备繼續往上層檢查...
      }
      else {    // 无伯父节点，或伯父节点为黑
        if (x == x->parent->left) { // 如果新节点为父节点之左子节点
          x = x->parent;
          __rb_tree_rotate_right(x, root);  // 第一参数为右旋点
        }
        x->parent->color = __rb_tree_black; // 改变颜色
        x->parent->parent->color = __rb_tree_red;
        __rb_tree_rotate_left(x->parent->parent, root); // 第一参数为左旋点
      }
    }
  } // while 結束
  root->color = __rb_tree_black;    // 根节点永远为黑
}

//删除区间的时候是一个一个删除的，在删除的时候我们主要调用的就是_Rb_tree_rebalance_for_erase()函数，它有四个参数，要删除的结点，根，最左结点和最右结点。下面是_Rb_tree_rebalance_for_erase函数的定义。
//我们平常的二叉查找树中删除一个结点：
//1. 如果结点至多只有一个子结点，那么将其链接到父结点
//2.否则找到其后继结点，先删除后继，再将后继结点的值放到原来要删除的结点中。
inline __rb_tree_node_base*
__rb_tree_rebalance_for_erase(__rb_tree_node_base* z,
                              __rb_tree_node_base*& root,
                              __rb_tree_node_base*& leftmost,
                              __rb_tree_node_base*& rightmost)
{
  __rb_tree_node_base* y = z; //y操作后指向是要删除的结点
  __rb_tree_node_base* x = 0;
  __rb_tree_node_base* x_parent = 0;
  if (y->left == 0)             // z has at most one non-null child. y == z.左子树为空时
    x = y->right;               // x might be null.x指向y的右子树，x可能是NULL
  else
    if (y->right == 0)          // z has exactly one non-null child.  y == z.右子树为空时
      x = y->left;              // x is not null.x指向左子树，x一定不为空，前一个判断判断过了
    else {                      // z has two non-null children.  Set y to  当z的左右子树都不为空时
      y = y->right;             //   z's successor.  x might be null. 令y指向z的后继，x为后继的右子树，可能为NULL
      while (y->left != 0)
        y = y->left;
      x = y->right;   //x可能为空
    }

  //nodeSize
  while(y!=root)
  {
    y->parent->nodeSize=y->parent->nodeSize-1;
    y=y->parent;
  }

  if (y != z) {                 // relink y in place of z.  y is z's successor 如果y!=z，现在y是原来z的后继 //将z的左子树接到y的左子树上(y的左子树之前一定是NULL)
    z->left->parent = y; 
    y->left = z->left;
    if (y != z->right) {    //如果后继结点不是z的右儿子
      x_parent = y->parent; //先将y的parent记录下来
      if (x) x->parent = y->parent; //如果x不是NULL，将它指向y的父结点
      y->parent->left = x;      // y must be a left child y原来是左儿子
      y->right = z->right;    //将原来的z的右子树接到y的右子树上面
      z->right->parent = y;
    }
    else                  //如果y=-z，z至少有一个子树为空
      x_parent = y;  
    if (root == z)        //如果要删除的是根
      root = y;      //新根结点
    else if (z->parent->left == z) //如果原来z是左儿子
      z->parent->left = y;
    else 
      z->parent->right = y;
    y->parent = z->parent;
    __STD::swap(y->color, z->color);
    y = z;
    // y now points to node to be actually deleted
  }
  else {                        // y == z
    x_parent = y->parent;
    if (x) x->parent = y->parent;   
    if (root == z)
      root = x;
    else 
      if (z->parent->left == z)
        z->parent->left = x;
      else
        z->parent->right = x;
    if (leftmost == z) 
      if (z->right == 0)        // z->left must be null also
        leftmost = z->parent;
    // makes leftmost == header if z == root
      else
        leftmost = __rb_tree_node_base::minimum(x);
    if (rightmost == z)  
      if (z->left == 0)         // z->right must be null also
        rightmost = z->parent;  
    // makes rightmost == header if z == root
      else                      // x == z->left
        rightmost = __rb_tree_node_base::maximum(x);
  }
  if (y->color != __rb_tree_red) { 
    while (x != root && (x == 0 || x->color == __rb_tree_black))
      if (x == x_parent->left) {
        __rb_tree_node_base* w = x_parent->right;
        if (w->color == __rb_tree_red) {
          w->color = __rb_tree_black;
          x_parent->color = __rb_tree_red;
          __rb_tree_rotate_left(x_parent, root);
          w = x_parent->right;
        }
        if ((w->left == 0 || w->left->color == __rb_tree_black) &&
            (w->right == 0 || w->right->color == __rb_tree_black)) {
          w->color = __rb_tree_red;
          x = x_parent;
          x_parent = x_parent->parent;
        } else {
          if (w->right == 0 || w->right->color == __rb_tree_black) {
            if (w->left) w->left->color = __rb_tree_black;
            w->color = __rb_tree_red;
            __rb_tree_rotate_right(w, root);
            w = x_parent->right;
          }
          w->color = x_parent->color;
          x_parent->color = __rb_tree_black;
          if (w->right) w->right->color = __rb_tree_black;
          __rb_tree_rotate_left(x_parent, root);
          break;
        }
      } else {                  // same as above, with right <-> left.
        __rb_tree_node_base* w = x_parent->left;
        if (w->color == __rb_tree_red) {
          w->color = __rb_tree_black;
          x_parent->color = __rb_tree_red;
          __rb_tree_rotate_right(x_parent, root);
          w = x_parent->left;
        }
        if ((w->right == 0 || w->right->color == __rb_tree_black) &&
            (w->left == 0 || w->left->color == __rb_tree_black)) {
          w->color = __rb_tree_red;
          x = x_parent;
          x_parent = x_parent->parent;
        } else {
          if (w->left == 0 || w->left->color == __rb_tree_black) {
            if (w->right) w->right->color = __rb_tree_black;
            w->color = __rb_tree_red;
            __rb_tree_rotate_left(w, root);
            w = x_parent->left;
          }
          w->color = x_parent->color;
          x_parent->color = __rb_tree_black;
          if (w->left) w->left->color = __rb_tree_black;
          __rb_tree_rotate_right(x_parent, root);
          break;
        }
      }
    if (x) x->color = __rb_tree_black;
  }
  return y;
}

//---------------------------------------------RB-tree  数据结构------------------------------------------

template <class Key, class Value, class KeyOfValue, class Compare,
          class Alloc = alloc>
class rb_tree {
protected:
  typedef void* void_pointer;
  typedef __rb_tree_node_base* base_ptr;
  typedef __rb_tree_node<Value> rb_tree_node;
  typedef simple_alloc<rb_tree_node, Alloc> rb_tree_node_allocator;
  typedef __rb_tree_color_type color_type;
  typedef __rb_tree_node_size node_size;
public:
  // 注意，沒有定义 iterator（喔，不，定义在后面）
  typedef Key key_type;
  typedef Value value_type;
  typedef value_type* pointer;
  typedef const value_type* const_pointer;
  typedef value_type& reference;
  typedef const value_type& const_reference;
  typedef rb_tree_node* link_type;
  typedef size_t size_type;
  typedef ptrdiff_t difference_type;
protected:
  link_type get_node() { return rb_tree_node_allocator::allocate(); }
  void put_node(link_type p) { rb_tree_node_allocator::deallocate(p); }

  link_type create_node(const value_type& x) {
    link_type tmp = get_node();         // 配置空间
    __STL_TRY {
      construct(&tmp->value_field, x);  // 构造內容
    }
    __STL_UNWIND(put_node(tmp));
    return tmp;
  }

  link_type clone_node(link_type x) {   // 复制一个节点（的值和色）
    link_type tmp = create_node(x->value_field);
    tmp->color = x->color;
    tmp->left = 0;
    tmp->right = 0;
//    tmp->nodeSize = 0;
    return tmp;
  }

  void destroy_node(link_type p) {
    destroy(&p->value_field);       // 析构內容
    put_node(p);                    // 释放内存
  }

protected:
  // RB-tree 只以三笔资料表现。
  size_type node_count; // 追踪记录树的大小（节点数量）
  link_type header;  
  Compare key_compare;   // 节点间的键值大小比较准则。应该会是个 function object。

  // 以下三个函数用来方便取得 header 的成员
  link_type& root() const { return (link_type&) header->parent; }
  link_type& leftmost() const { return (link_type&) header->left; }
  link_type& rightmost() const { return (link_type&) header->right; }

  // 以下7个函数用来方便取得节点 x 的成员
  static link_type& left(link_type x) { return (link_type&)(x->left); }
  static link_type& right(link_type x) { return (link_type&)(x->right); }
  static link_type& parent(link_type x) { return (link_type&)(x->parent); }
  static reference value(link_type x) { return x->value_field; }
  static const Key& key(link_type x) { return KeyOfValue()(value(x)); }
  static color_type& color(link_type x) { return (color_type&)(x->color); }
  static node_size& nodeSize(link_type x) { return (node_size&)(x->nodeSize); }

  // 以下7个函数用来方便取得节点 x 的成员
  static link_type& left(base_ptr x) { return (link_type&)(x->left); }
  static link_type& right(base_ptr x) { return (link_type&)(x->right); }
  static link_type& parent(base_ptr x) { return (link_type&)(x->parent); }
  static reference value(base_ptr x) { return ((link_type)x)->value_field; }
  static const Key& key(base_ptr x) { return KeyOfValue()(value(link_type(x)));} 
  static color_type& color(base_ptr x) { return (color_type&)(link_type(x)->color); }
  static node_size& nodeSize(base_ptr x) { return (node_size&)(x->nodeSize); }

  // 求取极大值和极小值。node class 有实现此功能，交給它们完成即可。
  static link_type minimum(link_type x) { 
    return (link_type)  __rb_tree_node_base::minimum(x);
  }
  static link_type maximum(link_type x) {
    return (link_type) __rb_tree_node_base::maximum(x);
  }

public:
  typedef __rb_tree_iterator<value_type, reference, pointer> iterator;
  typedef __rb_tree_iterator<value_type, const_reference, const_pointer> 
          const_iterator;

#ifdef __STL_CLASS_PARTIAL_SPECIALIZATION
  typedef reverse_iterator<const_iterator> const_reverse_iterator;
  typedef reverse_iterator<iterator> reverse_iterator;
#else /* __STL_CLASS_PARTIAL_SPECIALIZATION */
  typedef reverse_bidirectional_iterator<iterator, value_type, reference,
                                         difference_type>
          reverse_iterator; 
  typedef reverse_bidirectional_iterator<const_iterator, value_type,
                                         const_reference, difference_type>
          const_reverse_iterator;
#endif /* __STL_CLASS_PARTIAL_SPECIALIZATION */ 
private:
  iterator __insert(base_ptr x, base_ptr y, const value_type& v);
  link_type __copy(link_type x, link_type p);
  void __erase(link_type x);
  void init() {
    header = get_node();    // 产生一个节点空间，令 header 指向它
    color(header) = __rb_tree_red; // 令 header 为红色，用来区分 header  
                                   // 和 root（在 iterator.operator++ 中）
    //nodeSize
    nodeSize(header) = 0;
    root() = 0;
    leftmost() = header;    // 令 header 的左子节点为自己。
    rightmost() = header;   // 令 header 的右子节点为自己。
  }
public:
                                // allocation/deallocation
  rb_tree(const Compare& comp = Compare())
    : node_count(0), key_compare(comp) { init(); }

  // 以另一个 rb_tree 物件 x 为初值
  rb_tree(const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x) 
    : node_count(0), key_compare(x.key_compare)
  { 
    header = get_node();    // 产生一个节点空间，令 header 指向它
    color(header) = __rb_tree_red;  // 令 header 为红色
    if (x.root() == 0) {    //  如果 x 是个空白树
      root() = 0;
      leftmost() = header;  // 令 header 的左子节点为自己。
      rightmost() = header; // 令 header 的右子节点为自己。
    }
    else {  //  x 不是一个空白树
      __STL_TRY {
        root() = __copy(x.root(), header);
      }
      __STL_UNWIND(put_node(header));
      leftmost() = minimum(root()); // 令 header 的左子节点为最小节点
      rightmost() = maximum(root());    // 令 header 的右子节点为最大节点
    }
    node_count = x.node_count;
  }
  ~rb_tree() {
    clear();
    put_node(header);
  }
  rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& 
  operator=(const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x);

public:    
                                // accessors:
  Compare key_comp() const { return key_compare; }
  iterator begin() { return leftmost(); }       // RB 树的起头为最左（最小）节点处
  const_iterator begin() const { return leftmost(); }
  iterator end() { return header; } // RB 树的終点为 header所指处
  const_iterator end() const { return header; }
  reverse_iterator rbegin() { return reverse_iterator(end()); }
  const_reverse_iterator rbegin() const { 
    return const_reverse_iterator(end()); 
  }
  reverse_iterator rend() { return reverse_iterator(begin()); }
  const_reverse_iterator rend() const { 
    return const_reverse_iterator(begin());
  } 
  bool empty() const { return node_count == 0; }
  size_type size() const { return node_count; }
  size_type max_size() const { return size_type(-1); }

  void swap(rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& t) {
    // RB-tree 只以三个资料成员表现。所以互换两个 RB-trees时，
    // 只需将这三个成员互换即可。
    __STD::swap(header, t.header);
    __STD::swap(node_count, t.node_count);
    __STD::swap(key_compare, t.key_compare);
  }
    
public:
                                // insert/erase
  // 将 x 安插到 RB-tree 中（保持节点值独一无二）。
  pair<iterator,bool> insert_unique(const value_type& x);
  // 将 x 安插到 RB-tree 中（允许节点值重复）。
  iterator insert_equal(const value_type& x);

  iterator insert_unique(iterator position, const value_type& x);
  iterator insert_equal(iterator position, const value_type& x);

#ifdef __STL_MEMBER_TEMPLATES  
  template <class InputIterator>
  void insert_unique(InputIterator first, InputIterator last);
  template <class InputIterator>
  void insert_equal(InputIterator first, InputIterator last);
#else /* __STL_MEMBER_TEMPLATES */
  void insert_unique(const_iterator first, const_iterator last);
  void insert_unique(const value_type* first, const value_type* last);
  void insert_equal(const_iterator first, const_iterator last);
  void insert_equal(const value_type* first, const value_type* last);
#endif /* __STL_MEMBER_TEMPLATES */

  void erase(iterator position);
  size_type erase(const key_type& x);
  void erase(iterator first, iterator last);
  void erase(const key_type* first, const key_type* last);
  void clear() {
    if (node_count != 0) {
      __erase(root());
      leftmost() = header;
      root() = 0;
      rightmost() = header;
      node_count = 0;
    }
  }      

public:
                                // 集合（set）的各种操作行为:
  iterator find(const key_type& x);
  const_iterator find(const key_type& x) const;
  size_type count(const key_type& x) const;
  size_type Os_Rank(base_ptr x);
  size_type Os_Rank(link_type x);
  link_type& Os_Select(base_ptr x, int i);
  link_type& Os_Select(link_type x, int i);
  iterator lower_bound(const key_type& x);
  const_iterator lower_bound(const key_type& x) const;
  iterator upper_bound(const key_type& x);
  const_iterator upper_bound(const key_type& x) const;
  pair<iterator,iterator> equal_range(const key_type& x);
  pair<const_iterator, const_iterator> equal_range(const key_type& x) const;

public:
                                // Debugging.
  bool __rb_verify() const;
};

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
inline bool operator==(const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x, 
                       const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& y) {
  return x.size() == y.size() && equal(x.begin(), x.end(), y.begin());
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
inline bool operator<(const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x, 
                      const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& y) {
  return lexicographical_compare(x.begin(), x.end(), y.begin(), y.end());
}

#ifdef __STL_FUNCTION_TMPL_PARTIAL_ORDER

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
inline void swap(rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x, 
                 rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& y) {
  x.swap(y);
}

#endif /* __STL_FUNCTION_TMPL_PARTIAL_ORDER */


template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::
operator=(const rb_tree<Key, Value, KeyOfValue, Compare, Alloc>& x) {
  if (this != &x) {
                                // Note that Key may be a constant type.
    clear();
    node_count = 0;
    key_compare = x.key_compare;        
    if (x.root() == 0) {
      root() = 0;
      leftmost() = header;
      rightmost() = header;
    }
    else {
      root() = __copy(x.root(), header);
      leftmost() = minimum(root());
      rightmost() = maximum(root());
      node_count = x.node_count;
    }
  }
  return *this;
}

//---------------------------------------------RB-tree  元素操作------------------------------------------

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::
__insert(base_ptr x_, base_ptr y_, const Value& v) {
// 参数x_ 为新值安插点，参数y_ 为安插点之父节点，参数v 为新值。
  link_type x = (link_type) x_;
  link_type y = (link_type) y_;
  link_type z;

  // key_compare 是键值大小比较准则。应该会是个 function object。
  if (y == header || x != 0 || key_compare(KeyOfValue()(v), key(y))) {
    z = create_node(v);  // 产生一个新节点
    left(y) = z;          // 这使得当 y 即为 header时，leftmost() = z
    if (y == header) {
      root() = z;
      rightmost() = z;
    }
    else if (y == leftmost())   // 如果y为最左节点
      leftmost() = z;               // 维护leftmost()，使它永远指向最左节点
  }
  else {
    z = create_node(v);     // 产生一个新节点
    right(y) = z;               // 令新节点成为安插点之父节点 y 的右子节点
    if (y == rightmost())
      rightmost() = z;              // 维护rightmost()，使它永远指向最右节点
  }
  parent(z) = y;        // 设定新节点的父节点
  left(z) = 0;      // 设定新节点的左子节点
  right(z) = 0;         // 设定新节点的右子节点
                          // 新节点的颜色将在 __rb_tree_rebalance() 设定（並调整）
  __rb_tree_rebalance(z, header->parent);   // 参数一为新增节点，参数二为 root
  ++node_count;     // 节点数累加
  return iterator(z);   // 传回一个迭代器，指向新增节点
}

// 安插新值；节点键值允许重复。
// 注意，传回值是一个 RB-tree 迭代器，指向新增节点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::insert_equal(const Value& v)
{
  link_type y = header;
  link_type x = root(); // 从根节点开始
  if (x == 0) {
    nodeSize(x) = 1;
  }
  while (x != 0) {      // 从根节点开始，往下寻找适当的安插点
    nodeSize(x) = nodeSize(x) + 1;
    y = x;
    x = key_compare(KeyOfValue()(v), key(x)) ? left(x) : right(x);
    // 以上，遇「大」则往左，遇「小于或等于」则往右
  }
  return __insert(x, y, v);
}

// 安插新值；节点键值不允许重复，若重复则安插无效。
// 注意，传回值是个pair，第一元素是个 RB-tree 迭代器，指向新增节点，
// 第二元素表示安插成功与否。
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
pair<typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator, bool>
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::insert_unique(const Value& v)
{
  link_type y = header;
  link_type x = root(); // 从根节点开始
  if (x == 0) {
    nodeSize(x) = 1;
    }
  bool comp = true;
  while (x != 0) {      // 从根节点开始，往下寻找适当的安插点
    y = x;
    comp = key_compare(KeyOfValue()(v), key(x)); // v 键值小于目前节点之键值
    x = comp ? left(x) : right(x);  // 遇「大」则往左，遇「小于或等于」则往右
  }
  // 离开 while 循环之后，y 所指即安插点之父节点（此时的它必为叶节点）

  iterator j = iterator(y);   // 令迭代器j指向安插点之父节点 y
  if (comp) // 如果离开 while 循环时 comp 为真（表示遇「大」，将安插于左侧）
    if (j == begin()) {  // 如果安插点之父节点为最左节点
        return pair<iterator,bool>(__insert(x, y, v), true);

        //如果可以插入,则增加路径上节点的nodeSize
        link_type yy = header;
        link_type xx = root();
        while (xx != 0) {      // 从根节点开始，往下寻找适当的安插点
            nodeSize(xx) = nodeSize(xx) + 1;
            yy = xx;
            comp = key_compare(KeyOfValue()(v), key(x)); // v 键值小于目前节点之键值？
            xx = comp ? left(xx) : right(xx);  // 遇「大」则往左，遇「小于或等于」则往右
          }
      }
      // 以上，x 为安插点，y 为安插点之父节点，v 为新值。
    else    // 否则（安插点之父节点不为最左节点）
      --j;  // 调整 j，回头准备测试...
  if (key_compare(key(j.node), KeyOfValue()(v))) {
      // 小于新值（表示遇「小」，将安插于右侧）
      return pair<iterator,bool>(__insert(x, y, v), true);

       //如果可以插入,则增加路径上节点的nodeSize
      yy = header;
      xx = root();
      while (xx != 0) {      // 从根节点开始，往下寻找适当的安插点
          nodeSize(xx) = nodeSize(xx) + 1;
          yy = xx;
          comp = key_compare(KeyOfValue()(v), key(x)); // v 键值小于目前节点之键值？
          xx = comp ? left(xx) : right(xx);  // 遇「大」则往左，遇「小于或等于」则往右
        }
    }

  // 进行至此，表示新值一定与树中键值重复，那么就不该插入新值。
  return pair<iterator,bool>(j, false);
}


template <class Key, class Val, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Val, KeyOfValue, Compare, Alloc>::iterator 
rb_tree<Key, Val, KeyOfValue, Compare, Alloc>::insert_unique(iterator position,
                                                             const Val& v) {
  if (position.node == header->left) // begin()
    if (size() > 0 && key_compare(KeyOfValue()(v), key(position.node)))
      return __insert(position.node, position.node, v);
  // first argument just needs to be non-null 
    else
      return insert_unique(v).first;
  else if (position.node == header) // end()
    if (key_compare(key(rightmost()), KeyOfValue()(v)))
      return __insert(0, rightmost(), v);
    else
      return insert_unique(v).first;
  else {
    iterator before = position;
    --before;
    if (key_compare(key(before.node), KeyOfValue()(v))
        && key_compare(KeyOfValue()(v), key(position.node)))
      if (right(before.node) == 0)
        return __insert(0, before.node, v); 
      else
        return __insert(position.node, position.node, v);
    // first argument just needs to be non-null 
    else
      return insert_unique(v).first;
  }
}

template <class Key, class Val, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Val, KeyOfValue, Compare, Alloc>::iterator 
rb_tree<Key, Val, KeyOfValue, Compare, Alloc>::insert_equal(iterator position,
                                                            const Val& v) {
  if (position.node == header->left) // begin()
    if (size() > 0 && key_compare(KeyOfValue()(v), key(position.node)))
      return __insert(position.node, position.node, v);
  // first argument just needs to be non-null 
    else
      return insert_equal(v);
  else if (position.node == header) // end()
    if (!key_compare(KeyOfValue()(v), key(rightmost())))
      return __insert(0, rightmost(), v);
    else
      return insert_equal(v);
  else {
    iterator before = position;
    --before;
    if (!key_compare(KeyOfValue()(v), key(before.node))
        && !key_compare(key(position.node), KeyOfValue()(v)))
      if (right(before.node) == 0)
        return __insert(0, before.node, v); 
      else
        return __insert(position.node, position.node, v);
    // first argument just needs to be non-null 
    else
      return insert_equal(v);
  }
}

#ifdef __STL_MEMBER_TEMPLATES  

template <class K, class V, class KoV, class Cmp, class Al> template<class II>
void rb_tree<K, V, KoV, Cmp, Al>::insert_equal(II first, II last) {
  for ( ; first != last; ++first)
    insert_equal(*first);
}

template <class K, class V, class KoV, class Cmp, class Al> template<class II>
void rb_tree<K, V, KoV, Cmp, Al>::insert_unique(II first, II last) {
  for ( ; first != last; ++first)
    insert_unique(*first);
}

#else /* __STL_MEMBER_TEMPLATES */

template <class K, class V, class KoV, class Cmp, class Al>
void
rb_tree<K, V, KoV, Cmp, Al>::insert_equal(const V* first, const V* last) {
  for ( ; first != last; ++first)
    insert_equal(*first);
}

template <class K, class V, class KoV, class Cmp, class Al>
void
rb_tree<K, V, KoV, Cmp, Al>::insert_equal(const_iterator first,
                                          const_iterator last) {
  for ( ; first != last; ++first)
    insert_equal(*first);
}

template <class K, class V, class KoV, class Cmp, class A>
void 
rb_tree<K, V, KoV, Cmp, A>::insert_unique(const V* first, const V* last) {
  for ( ; first != last; ++first)
    insert_unique(*first);
}

template <class K, class V, class KoV, class Cmp, class A>
void 
rb_tree<K, V, KoV, Cmp, A>::insert_unique(const_iterator first,
                                          const_iterator last) {
  for ( ; first != last; ++first)
    insert_unique(*first);
}

#endif /* __STL_MEMBER_TEMPLATES */

//删除迭代器指向的结点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
inline void
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::erase(iterator position) {
  link_type y = (link_type) __rb_tree_rebalance_for_erase(position.node,
                                                          header->parent,
                                                          header->left,
                                                          header->right);
  link_type xx = y;
  destroy_node(y);
  while(parent(xx)!=header)
      {
        nodeSize(parent(xx))=nodeSize(parent(xx))-1;
        xx=parent(xx);
      }
  --node_count;
}

//删除key为x的结点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::size_type 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::erase(const Key& x) {
  pair<iterator,iterator> p = equal_range(x);
  size_type n = 0;
  distance(p.first, p.second, n);
  erase(p.first, p.second);//一个一个删除
  return n;
}

template <class K, class V, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<K, V, KeyOfValue, Compare, Alloc>::link_type 
rb_tree<K, V, KeyOfValue, Compare, Alloc>::__copy(link_type x, link_type p) {
                                // structural copy.  x and p must be non-null.
  link_type top = clone_node(x);
  top->parent = p;
 
  __STL_TRY {
    if (x->right)
      top->right = __copy(right(x), top);
    p = top;
    x = left(x);

    while (x != 0) {
      link_type y = clone_node(x);
      p->left = y;
      y->parent = p;
      if (x->right)
        y->right = __copy(right(x), y);
      p = y;
      x = left(x);
    }
  }
  __STL_UNWIND(__erase(top));

  return top;
}

//删掉x为根的子树，并不会改变树的性质，故不需要rebalance
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
void rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::__erase(link_type x) {
                                // erase without rebalancing
  while (x != 0) {
    link_type xx = x;
    __erase(right(x));
    link_type y = left(x);
    destroy_node(x);
    x = y;
    while(parent(xx)!=header)
    {
      nodeSize(parent(xx))=nodeSize(parent(xx))-1;
      xx=parent(xx);
    }
  }
}

//删除整个迭代区间内结点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
void rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::erase(iterator first, 
                                                            iterator last) {
  if (first == begin() && last == end())
    clear();
  else
    while (first != last) erase(first++);
}

//删除区间内的key
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
void rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::erase(const Key* first, 
                                                            const Key* last) {
  while (first != last) erase(*first++);
}

// 寻找 RB 树中是否有键值为 k 的节点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::find(const Key& k) {
  link_type y = header;        // Last node which is not less than k. 
  link_type x = root();        // Current node. 

  while (x != 0) 
    // 以下，key_compare 是节点键值大小比较准则。应该会是个 function object。
    if (!key_compare(key(x), k)) 
      // 进行到这裡，表示 x 键值大于 k。遇到大值就向左走。
      y = x, x = left(x);   // 注意語法!
    else
      // 进行到这裡，表示 x 键值小于 k。遇到小值就向右走。
      x = right(x);

  iterator j = iterator(y);   
  return (j == end() || key_compare(k, key(j.node))) ? end() : j;
}

// 寻找 RB 树中是否有键值为 k 的节点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::const_iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::find(const Key& k) const {
  link_type y = header; /* Last node which is not less than k. */
  link_type x = root(); /* Current node. */

  while (x != 0) {
    // 以下，key_compare 是节点键值大小比较准则。应该会是个 function object。
    if (!key_compare(key(x), k))
      // 进行到这裡，表示 x 键值大于 k。遇到大值就向左走。
      y = x, x = left(x);   // 注意語法!
    else
      // 进行到这裡，表示 x 键值小于 k。遇到小值就向右走。
      x = right(x);
  }
  const_iterator j = const_iterator(y);   
  return (j == end() || key_compare(k, key(j.node))) ? end() : j;
}

//查找以x为根结点的树中第i大的结点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
link_type& rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::Os_Select(base_ptr x, int i)
{  
    //令x左子树中点的个数为r-1,  
    int r = x->left->nodeSize +1;//在有相等的key的情况下，中序遍历树的输出位置为元素的秩，消除原顺序统计树的不确定性
    //那么x是x树中第r大的结点  
    if(r == i)  
        return x;  
    //第i大的元素在x->left中  
    else if(i < r)  
        return Os_Select(x->left, i);  
    //第i大的元素在x->right中  
    else  
        return Os_Select(x->right, i - r);  
}
//查找以x为根结点的树中第i大的结点
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
link_type& rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::Os_Select(link_type x, int i)
{
    //令x左子树中点的个数为r-1,
    int r = x->left->nodeSize +1;//在有相等的key的情况下，中序遍历树的输出位置为元素的秩，消除原顺序统计树的不确定性
    //那么x是x树中第r大的结点
    if(r == i)
        return x;
    //第i大的元素在x->left中
    else if(i < r)
        return Os_Select(x->left, i);
    //第i大的元素在x->right中
    else
        return Os_Select(x->right, i - r);
}
//计算树T中进行顺序遍历后得到的线性序中x的位置
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::size_type
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::Os_Rank(base_ptr x)
{  
    //置r为以x为根的子树中key[x]的秩  
    int r = x->left->nodeSize + 1;
    node *y = x;  
    while(y != root)  
    {  
        //若y是p[y]的右孩子，p[y]和p[y]左子树中所有结点前于x  
        if(y == y->p->right)  
            r = r + y->p->left->nodeSize + 1;
        y = y->p;  
    }  
    return r;  
}
 //计算树T中进行顺序遍历后得到的线性序中x的位置
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::size_type
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::Os_Rank(link_type x)
{
    //置r为以x为根的子树中key[x]的秩
    int r = x->left->nodeSize + 1;
    node *y = x;
    while(y != root)
    {
        //若y是p[y]的右孩子，p[y]和p[y]左子树中所有结点前于x
        if(y == y->p->right)
            r = r + y->p->left->nodeSize + 1;
        y = y->p;
    }
    return r;
}

// 计算 RB 树中键值为 k 的节点个数
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::size_type 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::count(const Key& k) const {
  pair<const_iterator, const_iterator> p = equal_range(k);
  size_type n = 0;
  distance(p.first, p.second, n);
  return n;
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::lower_bound(const Key& k) {
  link_type y = header; /* Last node which is not less than k. */
  link_type x = root(); /* Current node. */

  while (x != 0) 
    if (!key_compare(key(x), k))
      y = x, x = left(x);
    else
      x = right(x);

  return iterator(y);
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::const_iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::lower_bound(const Key& k) const {
  link_type y = header; /* Last node which is not less than k. */
  link_type x = root(); /* Current node. */

  while (x != 0) 
    if (!key_compare(key(x), k))
      y = x, x = left(x);
    else
      x = right(x);

  return const_iterator(y);
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::upper_bound(const Key& k) {
  link_type y = header; /* Last node which is greater than k. */
  link_type x = root(); /* Current node. */

   while (x != 0) 
     if (key_compare(k, key(x)))
       y = x, x = left(x);
     else
       x = right(x);

   return iterator(y);
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::const_iterator 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::upper_bound(const Key& k) const {
  link_type y = header; /* Last node which is greater than k. */
  link_type x = root(); /* Current node. */

   while (x != 0) 
     if (key_compare(k, key(x)))
       y = x, x = left(x);
     else
       x = right(x);

   return const_iterator(y);
}

template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
inline pair<typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator,
            typename rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::iterator>
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::equal_range(const Key& k) {
  return pair<iterator, iterator>(lower_bound(k), upper_bound(k));
}

template <class Key, class Value, class KoV, class Compare, class Alloc>
inline pair<typename rb_tree<Key, Value, KoV, Compare, Alloc>::const_iterator,
            typename rb_tree<Key, Value, KoV, Compare, Alloc>::const_iterator>
rb_tree<Key, Value, KoV, Compare, Alloc>::equal_range(const Key& k) const {
  return pair<const_iterator,const_iterator>(lower_bound(k), upper_bound(k));
}

// 计算从 node 至 root 路径中的黑节点数量。
inline int __black_count(__rb_tree_node_base* node, __rb_tree_node_base* root)
{
  if (node == 0)
    return 0;
  else {
    int bc = node->color == __rb_tree_black ? 1 : 0;
    if (node == root)
      return bc;
    else
      return bc + __black_count(node->parent, root); // 累加
  }
}

// 验证己身这棵树是否符合 RB 树的条件
template <class Key, class Value, class KeyOfValue, class Compare, class Alloc>
bool 
rb_tree<Key, Value, KeyOfValue, Compare, Alloc>::__rb_verify() const
{
  // 空树，符合RB树標准
  if (node_count == 0 || begin() == end())
    return node_count == 0 && begin() == end() &&
      header->left == header && header->right == header;

  // 最左（叶）节点至 root 路径內的黑节点数
  int len = __black_count(leftmost(), root()); 
  // 以下走访整个RB树，針对每个节点（从最小到最大）...
  for (const_iterator it = begin(); it != end(); ++it) { 
    link_type x = (link_type) it.node; // __rb_tree_base_iterator::node
    link_type L = left(x);      // 这是左子节点
    link_type R = right(x);     // 这是右子节点

    if (x->color == __rb_tree_red)
      if ((L && L->color == __rb_tree_red) ||
          (R && R->color == __rb_tree_red))
        return false;   // 父子节点同为红色，不符合 RB 树的要求。

    if (L && key_compare(key(x), key(L))) // 目前节点的键值小于左子节点键值
      return false;             // 不符合二元搜寻树的要求。
    if (R && key_compare(key(R), key(x))) // 目前节点的键值大于右子节点键值
      return false;     // 不符合二元搜寻树的要求。

    // 「叶节点至 root」路径內的黑节点数，与「最左节点至 root」路径內的黑节点数不同。
    // 这不符合 RB 树的要求。
    if (!L && !R && __black_count(x, root()) != len) 
      return false;
  }

  if (leftmost() != __rb_tree_node_base::minimum(root()))
    return false;   // 最左节点不为最小节点，不符合二元搜寻树的要求。
  if (rightmost() != __rb_tree_node_base::maximum(root()))
    return false;   // 最右节点不为最大节点，不符合二元搜寻树的要求。

  return true;
}

__STL_END_NAMESPACE 

#endif /* __SGI_STL_INTERNAL_TREE_H */

// Local Variables:
// mode:C++
// End:

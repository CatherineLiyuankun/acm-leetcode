package swordForOffer.p16ReverseList;

/**
 * Created by muzilan on 15/10/25.
 */
public class ReverseList {
    public static void main(String args[])
    {
        ListNode head=new ListNode();
        ListNode second=new ListNode();
        ListNode third=new ListNode();
        ListNode forth=new ListNode();
        head.next=second;
        second.next=third;
        third.next=forth;
        head.value=1;
        second.value=2;
        third.value=3;
        forth.value=4;
        ReverseList test=new ReverseList();
        ListNode resultListNode=test.reverseList(head);
        System.out.println(resultListNode.value);
    }

    public ListNode reverseList(ListNode head) {
        ListNode reverseHead = null;
        ListNode p = head;
        ListNode pre = null;
        while (p != null) {
            ListNode pNext = p.next;
            if (pNext == null) {
                reverseHead = p;
            }
            p.next = pre;
            pre = p;
            p = pNext;
        }
        return reverseHead;
    }

}

class ListNode {
    int value;
    ListNode next;
}

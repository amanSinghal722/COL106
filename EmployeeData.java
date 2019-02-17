import java.io.*;
import java.util.*;
public class EmployeeData
{
    public static void main(String args[])
    {
        employee_data MyCompany = new employee_data();
        File Employees = new File(args[0]);
        try
        {
            Scanner S = new Scanner(Employees);
            String N = S.nextLine();
            int NI = Integer.valueOf(N);
            for (int i = 1; i < NI; i++)
            {
                String[] L = S.nextLine().split(" ");
                MyCompany.AddEmployee(L[0], L[1]);
            }
            String n = S.nextLine();
            int n_commands = Integer.valueOf(n);
            int n0 = 0, n1=0, n2=0, n3=0;
            for (int j=0; j < n_commands; j++)
            {
                String[] L1 = S.nextLine().split(" ");
                int y = Integer.valueOf(L1[0]);
                if (y == 0)
                {
                    n0++;
                    MyCompany.AddEmployee(L1[1], L1[2]);
                }
                if (y == 1)
                {
                    n1++;
                    MyCompany.DeleteEmployee(L1[1], L1[2]);
                }
                if (y == 2)
                {
                    n2++;
                    MyCompany.LowestCommonBoss(L1[1], L1[2]);
                }
                if (y == 3)
                {
                    n3++;
                    MyCompany.PrintEmployees();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
class employee_data
{
    treeNode CEO = new treeNode();
    public void AddEmployee(String S, String S1) throws Exception
    {
        try 
        {
            treeNode N = new treeNode();
            N.name = S;
            if (CEO.rightchild == null && CEO.leftchild == null) 
            {
                CEO.name = S1;
                N.realBoss = CEO;
                InsertInTree(N, CEO);
                CEO.children.InsertAtHead(N);
            } 
            else
            {
                treeNode T = SearchTree(S1, CEO);
                N.realBoss = T;
                T.children.InsertAtTail(N);
                InsertInTree(N, CEO);
            }
        }
        catch (EmployeeNotFoundException e)
        {
            throw new NoEmployeeException(S1);
        }
        catch (EmployeeAlreadyPresent x)
        {
            return;
        }
    }
    public void DeleteEmployee(String S, String S1) throws Exception
    {
        try
        {
            treeNode s = SearchTree(S, CEO);
            treeNode s1 = SearchTree(S1, CEO);
            sameDepth(s, s1);
            checkCEO(s);
            Node<treeNode> h = s.children.header;
            if (h != null)
            {
                if (h != s.children.trailer)
                {
                    while (h != s.children.trailer)
                    {
                        s1.children.InsertAtTail(h.data);
                        h.data.realBoss = s1;
                        h = h.next;
                    }
                    s1.children.InsertAtTail(h.data);
                    h.data.realBoss = s1;
                }
                else
                {
                    s1.children.InsertAtTail(h.data);
                    h.data.realBoss = s1;
                }
            }
            treeNode rb = s.realBoss;
            Node<treeNode> n = rb.children.header;
            if (n == null)
            {
                throw new NoObjectException();
            }
            else if (n == rb.children.trailer)
            {
                if (n.data.name.compareTo(S) == 0)
                {
                    rb.children.header = null;
                    rb.children.trailer = rb.children.trailer;
                }
                else
                {
                    throw new NoObjectException();
                }
            }
            else
            {
                while (n.data.name.compareTo(S) != 0)
                {
                    if (n.next != null)
                    {
                        n = n.next;
                    }
                    else
                    {
                        throw new NoObjectException();
                    }
                }
                if (n.prev == null)
                {
                    rb.children.header = rb.children.header.next;
                    rb.children.header.prev = null;
                }
                else if (n.next == null)
                {
                    rb.children.trailer = rb.children.trailer.prev;
                    rb.children.trailer.next = null;
                }
                else
                {
                    n.prev.next = n.next;
                    n.next.prev = n.prev;
                }
            }
            if (s.rightchild == null && s.leftchild == null)
            {
                if (S.compareTo(s.treeParent.name) > 0)
                {
                    s.treeParent.rightchild = null;
                }
                else
                {
                    s.treeParent.leftchild = null;
                }
            }
            else if (s.rightchild == null && s.leftchild != null)
            {
                if (S.compareTo(s.treeParent.name) > 0)
                {
                    s.treeParent.rightchild = s.leftchild;
                    s.leftchild.treeParent = s.treeParent;
                }
                else
                {
                    s.treeParent.leftchild = s.leftchild;
                    s.leftchild.treeParent = s.treeParent;
                }
            }
            else if (s.rightchild != null && s.leftchild == null)
            {
                if (S.compareTo(s.treeParent.name) > 0)
                {
                    s.treeParent.rightchild = s.rightchild;
                    s.rightchild.treeParent = s.treeParent;
                }
                else
                {
                    s.treeParent.leftchild = s.rightchild;
                    s.rightchild.treeParent = s.treeParent;
                }
            }
            else
            {
                treeNode l = s.leftchild;
                while (l.rightchild != null)
                {
                    l = l.rightchild;
                }
                s.children = l.children;
                s.realBoss = l.realBoss;
                s.name = l.name;
                if (l.leftchild == null)
                {
                    if (l.name.compareTo(l.treeParent.name) < 0)
                    {
                        l.treeParent.leftchild = null;
                    }
                    else
                    {
                        l.treeParent.rightchild = null;
                    }
                }
                else
                {
                    if (l.name.compareTo(l.treeParent.name) < 0)
                    {
                        l.treeParent.leftchild = l.leftchild;
                        l.leftchild.treeParent = l.treeParent;
                    }
                    else
                    {
                        l.treeParent.rightchild = l.leftchild;
                        l.leftchild.treeParent = l.treeParent;
                    }
                }
            }


        }
        catch (NotSameLevelException le)
        {
            System.out.println("ERROR!");
        }
        catch (CEOdeletionException ceo)
        {
            System.out.println("ERROR!");
        }
        catch (EmployeeNotFoundException nf)
        {
            System.out.println("ERROR!");
        }
        catch (NoObjectException no)
        {
            System.out.println("ERROR!");
        }
        catch (Exception e)
        {
            System.out.println("ERROR!");
        }
    }
    public String LowestCommonBoss(String S, String S1) throws EmployeeNotFoundException
    {
        treeNode s;
        treeNode s1;
        try
        {
            s = SearchTree(S, CEO);
        }
        catch (EmployeeNotFoundException e)
        {
            throw new EmployeeNotFoundException();
        }
        try
        {
            s1 = SearchTree(S1, CEO);
        } 
        catch (EmployeeNotFoundException e)
        {
            throw new EmployeeNotFoundException();
        }
        treeNode c = s.realBoss;
        linklist<treeNode> L = new linklist<treeNode>();
        while (c != CEO)
        {
            L.InsertAtHead(c);
            c = c.realBoss;
        }
        L.InsertAtHead(c);
        treeNode c1 = s1.realBoss;
        linklist<treeNode> L1 = new linklist<treeNode>();
        while (c1 != CEO)
        {
            L1.InsertAtHead(c1);
            c1 = c1.realBoss;
        }
        L1.InsertAtHead(c1);
        Node<treeNode> h = L.header;
        Node<treeNode> h1 = L1.header;
        while (check(h.data, h1.data))
        {
            if (h.next != null && h1.next != null)
            {
                h = h.next;
                h1 = h1.next;
            }
            else
            {
                if (h.next == null)
                {
                    System.out.println(h.data.name);
                    return h.data.name;
                }
                else if (h1.next == null)
                {
                    System.out.println(h.data.name);
                    return h.data.name;
                }
            }
        }
        System.out.println(h.prev.data.name);
        return h.prev.data.name;
    }
    public void PrintEmployees() throws EmptyQueueException
    {
        queue<treeNode> Q = new queue<treeNode>();
        if (CEO != null)
        {
            Q.enqueue(CEO);
        }
        while (Q.IsEmpty() == false)
        {
            treeNode O = new treeNode();
            try
            {
                O = Q.dequeue();
                System.out.println(O.name);
                linklist<treeNode> L = O.children;
                Node<treeNode> h = new Node<treeNode>();
                h = L.header;
                if (h != null)
                {
                    while (h.next != null)
                    {
                        Q.enqueue(h.data);
                        h = h.next;
                    }
                    Q.enqueue(h.data);
                }
            }
            catch (EmptyQueueException q)
            {
                throw new EmptyQueueException();
            } 
        }
    }
    public void sameDepth(treeNode T, treeNode T1) throws NotSameLevelException
    {
        int count1 = 0;
        int count2 = 0;
        treeNode t = T;
        treeNode t1 = T1;
        while (t.realBoss != CEO)
        {
            t = t.realBoss;
            count1++;
        }
        while (t1.realBoss != CEO)
        {
            t1 = t1.realBoss;
            count2++;
        }
        if (count1 != count2)
        {
            throw new NotSameLevelException();
        }
    }
    public void checkCEO(treeNode T) throws CEOdeletionException
    {
        if (T == CEO)
        {
            throw new CEOdeletionException();
        }
    }
    public boolean check(treeNode T, treeNode T1)
    {
        return (T == T1);
    }
    public void InsertInTree(treeNode T, treeNode T1) throws EmployeeAlreadyPresent
    {
        int k = T.name.compareTo(T1.name);
        if (k > 0)
        {
            if (T1.rightchild == null)
            {
                T1.rightchild = T;
                T.treeParent = T1;
            }
            else
            {
                InsertInTree(T, T1.rightchild);
            }
        }
        else if (k<0)
        {
            if (T1.leftchild == null)
            {
                T1.leftchild = T;
                T.treeParent = T1;
            }
            else
            {
                InsertInTree(T, T1.leftchild);
            }
        }
        else
        {
            throw new EmployeeAlreadyPresent();
        }
    }
    public treeNode SearchTree(String S, treeNode T) throws EmployeeNotFoundException
    {
        int k = S.compareTo(T.name);
        if (k == 0)
        {
            return T;
        }  
        else
        {
            if (k > 0)
            {
                if (T.rightchild != null)
                {
                    return SearchTree(S, T.rightchild);
                }
                else
                {
                    throw new EmployeeNotFoundException();
                }
            }
            else
            {
                if (T.leftchild != null)
                {
                    return SearchTree(S, T.leftchild);
                }
                else
                {
                    throw new EmployeeNotFoundException();
                }
            }
        } 
    }
}
class queue<E>
{
    linklist<E> Q = new linklist<E>();
    public void enqueue(E o)
    {
        Q.InsertAtHead(o);
    }
    public E dequeue() throws EmptyQueueException
    {
        try
        {
            E ret = Q.RemoveAtTail();
            return ret;
        }
        catch(EmptyListException e)
        {
            throw new EmptyQueueException();
        }
    }
    public boolean IsEmpty()
    {
        if (Q.header == null)
        {
            return true;
        }
        return false;
    }
}
class linklist<E>
{
    Node<E> header;
    Node<E> trailer; 
    public void InsertAtHead(E o)
    {
        Node<E> n = new Node<E>();
        n.data = o;
        if (header == null)
        {
            header = n;
            trailer = header;
        }
        else
        {
            n.next = header;
            header.prev = n;
            header = n;
        }
    }
    public void InsertAtTail(E o)
    {
        Node<E> n = new Node<E>();
        n.data = o;
        if (trailer == null)
        {
            header = n;
            trailer = header;
        }
        else
        {
            n.prev = trailer;
            trailer.next = n;
            trailer = n;
        }
    }
    public E RemoveAtHead() throws EmptyListException
    {
        if (header == null)
        {
            throw new EmptyListException();
        }
        else
        {
            if (header == trailer)
            {
                E ret = header.data;
                header = null;
                trailer = null;
                return ret;
            }
            E ret = header.data;
            header = header.next;
            return ret;
        }
    }
    public E RemoveAtTail() throws EmptyListException
    {
        if (header == null)
        {
            throw new EmptyListException();
        }
        else
        {
            if (header == trailer)
            {
                E ret = header.data;
                header = null;
                trailer = header;
                return ret;
            }
            E ret = trailer.data;
            trailer = trailer.prev;
            trailer.next = null;
            return ret;
        }
    }
    public void delete(E o) throws NoObjectException
    {
        Node<E> F = header;
        if (header == null)
        {
            throw new NoObjectException();
        }
        else if (header == trailer)
        {
            if (header.data == o)
            {
                header = null;
                trailer = header;
            }
            else
            {
                throw new NoObjectException();
            }
        }
        else 
        {
            if (F.data == o)
            {
                header = header.next;
                header.prev = null;
            }
            else
            {
                while (F.data != o || F.next != null)
                {
                    F = F.next;
                }
                if (F.data == o && F.next != null)
                {
                    Node<E> P = F.prev;
                    Node<E> N = F.next;
                    P.next = F.next;
                    N.prev = F.prev;
                }
                else if (F.data == o && F.next == null)
                {
                    trailer = trailer.prev;
                    trailer.next = null;
                }
                else
                {
                    throw new NoObjectException();
                }
            }
        }
    }
    public boolean IsEmpty()
    {
        if (header == null)
        {
            return true;
        }
        return false;
    }
}
class Node<E>
{
    E data;
    Node<E> prev;
    Node<E> next;
}
class treeNode
{
    String name;
    treeNode realBoss;
    linklist<treeNode> children = new linklist<treeNode>();
    treeNode treeParent;
    treeNode rightchild;
    treeNode leftchild;
}
class EmptyListException extends Exception
{
    public EmptyListException()
    {
        System.out.println("EmptyListException: List is Empty");
    }
}
class EmptyQueueException extends Exception
{
    public EmptyQueueException()
    {
        System.out.println("EmptyQueueException: Queue is Empty");
    }
}
class EmployeeNotFoundException extends Exception
{
    public EmployeeNotFoundException()
    {
        System.out.println("EmployeeNotFoundException: Employee not found");
    }
}
class NoEmployeeException extends Exception
{
    public NoEmployeeException(String S)
    {
        System.out.println("NoEmployeeException: No employee with name " + S);
    }
}
class NoObjectException extends Exception
{
    public NoObjectException()
    {
        System.out.println("NoObjectException: Object not Found");
    }
}
class CEOdeletionException extends Exception
{
    public CEOdeletionException()
    {
        System.out.println("CEOdeletionException: CEO cannot be removed.");
    }
}
class EmployeeAlreadyPresent extends Exception
{
    public EmployeeAlreadyPresent()
    {
        System.out.println("EmoplyeeAlreadyPresent: Employee already added to the company");
    }
}
class NotSameLevelException extends Exception 
{
    public NotSameLevelException()
    {
        System.out.println("NotSameLevelException: Employees do not have the same level;");
    }
}

import java.lang.Math;
import java.util.*;
import java.util.ArrayList; 
import javafx.util.Pair;
import java.io.*;
public class BestFit
{
    static AVLTree<CapTreeInp> CapTree;
    static AVLTree<ObjTreeInp> ObjTree;                // Object Tree root has been added but the object is not added in the bins yet.
    static AVLTree<BinTreeInp> BinTree;
    public static void main(String args[])
    {
        int n1=1, n2=1, n3=0, n4=0;
        File file = new File(args[0]);
        try
        {
            System.out.println(args[0]);
            BestFit B = new BestFit(file);
            Scanner S = new Scanner(file);
            S.nextLine();
            while (S.hasNextLine())
            {
                String[] Operations = S.nextLine().split(" ");
                if (Integer.parseInt(Operations[0]) == 1)
                {
                    B.add_bin(Integer.parseInt(Operations[1]), Integer.parseInt(Operations[2]));
                    n1++;
                    //B.CapTree.inOrderTraversal(B.CapTree.root);
                }
                else if (Integer.parseInt(Operations[0]) == 2)
                {
                    if (n2 == 1)
                    {
                        AVLNode<CapTreeInp> i = CapTree.root;
                        while (i.rightChild != null)
                        {
                            i = i.rightChild;
                        }
                        if (i.data >= ObjTree.root.newClass.size)
                        {
                            ObjTree.root.newClass.BinId = i.newClass.BinId;
                            i.newClass.leftCap = i.newClass.leftCap - ObjTree.root.newClass.size;
                            int size = ObjTree.root.newClass.size;
                            Object ob = ObjTree.root.newClass.O;
                            System.out.println(i.newClass.BinId);
                            i.newClass.Obj = new LinkedList<Object>();
                            i.newClass.Obj.addLast(ob);
                            if (i != CapTree.root)
                            {
                                CapTree.delete(i.data, i);
                                CapTree.insert(i.data - size, i.newClass);
                            }
                            else
                            {
                                if (i.leftChild == null)
                                {
                                    CapTree.insert(i.data - size, i.newClass);
                                    CapTree.delete(i.data, i);
                                }
                                else
                                {
                                    CapTree.delete(i.data, i);
                                    CapTree.insert(i.data - size, i.newClass);
                                }
                            }
                        }
                        else
                        {
                            System.out.println("ERROR: Not Enough Space");
                            return;
                        }
                        //B.add_object(Integer.parseInt(Operations[1]), Integer.parseInt(Operations[2]));
                    }
                    else
                    {
                        //System.out.println(Integer.parseInt(Operations[1]));
                        System.out.println(B.add_object(Integer.parseInt(Operations[1]), Integer.parseInt(Operations[2])));
                        //B.CapTree.inOrderTraversal(B.CapTree.root);
                    }
                    n2++;
                }
                else if (Integer.parseInt(Operations[0]) == 3)
                {
                    System.out.println(B.delete_object(Integer.parseInt(Operations[1])));
                    n3++;
                }
                else if (Integer.parseInt(Operations[0]) == 4)
                {
                    B.contents(Integer.parseInt(Operations[1]));
                    n4++;
                }
                //System.out.println(n1 + " " + n2 + " " + n3 + " " + n4);
                //System.out.println();
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public BestFit(File file)
    {
        try
        {
            Scanner Q = new Scanner(file);
            String[] Operation1 = Q.nextLine().split(" ");
            int BinId = Integer.parseInt(Operation1[1]);
            int Cap = Integer.parseInt(Operation1[2]);
            CapTreeInp c = new CapTreeInp(BinId, Cap);
            BinTreeInp b = new BinTreeInp(BinId, Cap);
            CapTree = new AVLTree<CapTreeInp>(Cap, c);
            CapTree.root.newClass.BTI = b;
            b.Bin = CapTree.root;
            BinTree = new AVLTree<BinTreeInp>(BinId, b);
            String[] Operation2 = Q.nextLine().split(" ");
            while (Integer.parseInt(Operation2[0]) != 2)
            {
                Operation2 = Q.nextLine().split(" ");
            }
            int ObjId = Integer.parseInt(Operation2[1]);
            int size = Integer.parseInt(Operation2[2]);
            ObjTreeInp O = new ObjTreeInp(ObjId, size);
            Object op = new Object(ObjId, size);
            O.O = op;
            ObjTree = new AVLTree<ObjTreeInp>(ObjId, O);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void add_bin(int bin_id, int cap)
    {
        CapTreeInp c = new CapTreeInp(bin_id, cap);
        AVLNode<CapTreeInp> p = CapTree.insert(cap, c);
        BinTreeInp b = new BinTreeInp(bin_id, cap);
        p.newClass.BTI = b;
        b.Bin = p;
        BinTree.insert(bin_id, b);
    }
    public int add_object(int object_id, int size)
    {
        // return the bin id to which this object is added
        int ret = 0;
        AVLNode<CapTreeInp> i = new AVLNode<CapTreeInp>(0);
        i = CapTree.root;
        while (i.rightChild != null)
        {
            i = i.rightChild;
        }
        if (i.data >= size)
        {
            ret = i.newClass.BinId;
            Object ob = new Object(object_id, size);
            if (i.newClass.Obj == null)
            {
                i.newClass.Obj = new LinkedList<Object>();
            }
            i.newClass.Obj.addLast(ob);

            i.newClass.leftCap = i.newClass.leftCap - size;
             //System.out.println(i.newClass.leftCap);
            //System.out.println(i.data);
            if (i != CapTree.root)
            {
                CapTree.delete(i.data, i);
                AVLNode<CapTreeInp> inp = CapTree.insert(i.data - size, i.newClass);
                inp.newClass.BTI = i.newClass.BTI;
                i.newClass.BTI.Bin = inp;
                //CapTree.insert(i.newClass.leftCap, i.newClass);
            }
            else
            {
                if (i.leftChild == null)
                {
                    CapTree.insert(i.data - size, i.newClass);
                    CapTree.delete(i.data, i);
                }
                else
                {
                    CapTree.delete(i.data, i);
                    CapTree.insert(i.data - size, i.newClass);
                }
            }
            ObjTreeInp Oi = new ObjTreeInp(object_id, size);
            Oi.BinId = ret;
            Oi.O = ob;
            ObjTree.insert(object_id, Oi);

        }
        else
        {
            System.out.println("ERROR: Not enough space");                                          //ERRORRRRRRRRRRRRR!!!!!!
        }
        return ret;
    }
    public int delete_object(int obj_id)
    {
        AVLNode<ObjTreeInp> O = ObjTree.Search(obj_id, ObjTree.root);
        if (O.height == -5)
        {
            return -100;
        }
        int bin_id = O.newClass.BinId;
        AVLNode<BinTreeInp> B = BinTree.Search(bin_id, BinTree.root);
        //System.out.println(obj_id);
        B.newClass.Bin.newClass.Obj.remove(O.newClass.O);
        B.newClass.Bin.newClass.leftCap = B.newClass.Bin.newClass.leftCap + O.newClass.size;
        CapTree.delete(B.newClass.Bin.data, B.newClass.Bin);
        CapTree.insert(B.newClass.Bin.data + O.newClass.size, B.newClass.Bin.newClass).newClass.BTI = B.newClass;
        ObjTree.delete(obj_id, ObjTree.root);
        return bin_id;
    }
    public LinkedList<Object> contents(int bin_id)
    {
        AVLNode<BinTreeInp> B = BinTree.Search(bin_id, BinTree.root);
        LinkedList I = new LinkedList();
        if (B.newClass.Bin.newClass.Obj != null)
        {
            ListIterator<Object> L = B.newClass.Bin.newClass.Obj.listIterator();
            ArrayList<Pair<Integer, Integer>> L1 = new ArrayList<Pair<Integer, Integer>>();
            while (L.hasNext())
            {
                Pair<Integer, Integer> p = new Pair<Integer, Integer>(L.next().ObjId, L.next().size);
                L1.addLast(p);
            }
        }
        return I;
    }

}
class AVLTree<E>
{
    AVLNode<E> root;
    public AVLTree(int x, E newClass)
    {
        root = new AVLNode<E>(x);
        root.newClass = newClass;
    }
    public AVLNode<E> Search(int v, AVLNode<E> node)
    {
        AVLNode<E> x = new AVLNode<E>(0);
        x.height = -5;
        if (v == node.data)
        {
            return node;
        }
        else if (v > node.data)
        {
            if (node.rightChild == null)
            {
                return x;
            }
            return Search(v, node.rightChild);
        }
        else
        {
            if (node.leftChild == null)
            {
                return x;
            }
            return Search(v, node.leftChild);
        }
    }
    public int maxValue(int a, int b)
    {
        if (a>b)
        {
            return a;
        }
        else if (b>a)
        {
            return b;
        }
        else 
        {
            return a;
        }
    }
    public void inOrderTraversal(AVLNode<E> node)
    {
        if (node.leftChild != null)
        {
            inOrderTraversal(node.leftChild);
        }
        System.out.println(node.data + "   " + node.height);
        if (node.rightChild != null)
        {
            inOrderTraversal(node.rightChild);
        }
    }
    public AVLNode<E> insert(int v, E newClass)
    {
        AVLNode<E> i = insertBST(v, newClass, root);
        AVLNode<E> z = errorNode(i);
        if (z.height == -1)
        {
            return i;
        }
        else
        {
            if (v >= z.data)
            {
                AVLNode<E> y = z.rightChild;
                if (v >= y.data)
                {
                    AVLNode<E> x = y.rightChild;
                    if (z != root)
                    {
                        AVLNode<E> P = z.parent;
                        AVLNode<E> N = leftRotate(z);
                        N.parent = P;
                        if (N.data >= P.data)
                        {
                            P.rightChild = N;
                        }
                        else
                        {
                            P.leftChild = N;
                        }
                        updateHeight(N);
                    }
                    else
                    {
                        root = leftRotate(z);
                        root.parent = null;
                    }
                }
                else
                {
                    AVLNode<E> x = y.leftChild;
                    z.rightChild = rightRotate(y);
                    z.rightChild.parent = z;
                    if (z != root)
                    {
                        AVLNode<E> P = z.parent;
                        AVLNode<E> N = leftRotate(z);
                        N.parent = P;
                        if (N.data >= P.data)
                        {
                            P.rightChild = N;
                        }
                        else
                        {
                            P.leftChild = N;
                        }
                        updateHeight(N);
                    }
                    else
                    {
                        root = leftRotate(z);
                        root.parent = null;
                    }
                }
            }
            else
            {
                AVLNode<E> y = z.leftChild;
                if (v > y.data)
                {
                    AVLNode<E> x = y.rightChild;
                    z.leftChild = leftRotate(y);
                    z.leftChild.parent = z;
                    if (z != root)
                    {
                        AVLNode<E> P = z.parent;
                        AVLNode<E> N = rightRotate(z);
                        N.parent = P;
                        if (N.data >= P.data)
                        {
                            P.rightChild = N;
                        }
                        else
                        {
                            P.leftChild = N;
                        }
                        updateHeight(N);
                    }
                    else
                    {
                        root = rightRotate(z);
                        root.parent = null;
                    }
                }
                else
                {
                    AVLNode<E> x = y.leftChild;
                    if (z != root)
                    {
                        AVLNode<E> P = z.parent;
                        AVLNode<E> N = rightRotate(z);
                        N.parent = P;
                        if (N.data >= P.data)
                        {
                            P.rightChild = N;
                        }
                        else
                        {
                            P.leftChild = N;
                        }
                        updateHeight(N);
                    }
                    else
                    {
                        root = rightRotate(z);
                        root.parent = null;
                    }
                }
            }
        }
        return i;
    }
    public void delete(int v, AVLNode<E> nod)
    {
        AVLNode<E> d = deleteBST(v, nod);
        if (d.height == -2)
        {
            System.out.println("value not found");
        }
        else if (d == root && d.rightChild == null && d.leftChild == null && v == d.data)
        {
            System.out.println("THE ONLY ELEMENT CANNOT BE DELETED.");
        }
        else
        {
            AVLNode<E> z = errorNode(d);
            while (z.height != -1)
            {
                if (h(z.rightChild) > h(z.leftChild))
                {
                    AVLNode<E> y = z.rightChild;
                    if (h(y.rightChild) >= h(y.leftChild))
                    {
                        AVLNode<E> x = y.rightChild;
                        if (z != root)
                        {
                            AVLNode<E> P = z.parent;
                            AVLNode<E> N = leftRotate(z);
                            N.parent = P;
                            if (z.data >= P.data)
                            {
                                P.rightChild = N;
                            }
                            else
                            {
                                P.leftChild = N;
                            }
                            updateHeight(N);
                        }
                        else
                        {
                            root = leftRotate(z);
                            root.parent = null;
                        }
                        
                    }
                    else
                    {
                        AVLNode<E> x = y.leftChild;
                        z.rightChild = rightRotate(y);
                        z.rightChild.parent = z;
                        if (z != root)
                        {
                            AVLNode<E> P = z.parent;
                            AVLNode<E> N = leftRotate(z);
                            N.parent = P;
                            if (z.data >= P.data)
                            {
                                P.rightChild = N;
                            }
                            else
                            {
                                P.leftChild = N;
                            }
                            updateHeight(N);
                        }
                        else
                        {
                            root = leftRotate(z);
                            root.parent = null;
                        }
                    }
                }  
                else if (h(z.rightChild) < h(z.leftChild))
                {
                    AVLNode<E> y = z.leftChild;
                    if (h(y.rightChild) > h(y.leftChild))
                    {
                        AVLNode<E> x = y.rightChild;
                        z.leftChild = leftRotate(y);
                        z.leftChild.parent = z;
                        if (z != root)
                        {
                            AVLNode<E> P = z.parent;
                            AVLNode<E> N = rightRotate(z);
                            N.parent = P;
                            if (z.data >= P.data)
                            {
                                P.rightChild = N;
                            }
                            else
                            {
                                P.leftChild = N;
                            }
                            updateHeight(N);
                        }
                        else
                        {
                            root = rightRotate(z);
                            root.parent = null;
                        }

                    }
                    else
                    {
                        AVLNode<E> x = y.leftChild;
                        if (z != root)
                        {
                            AVLNode<E> P = z.parent;
                            AVLNode<E> N = rightRotate(z);
                            N.parent = P;
                            if (z.data >= P.data)
                            {
                                P.rightChild = N;
                            }
                            else
                            {
                                P.leftChild = N;
                            }
                            updateHeight(N);
                        }
                        else
                        {
                            root = rightRotate(z);
                            root.parent = null;
                        }
                    }
                } 
                z = errorNode(z);
            }
            
        }
    }
    public int h(AVLNode<E> node)
    {
        if (node == null)
        {
            return 0;
        }
        return node.height;
    }
    public AVLNode<E> rightRotate(AVLNode<E> y)
    {
        AVLNode<E> x = y.leftChild;
        if (x.rightChild != null)
        {
            AVLNode<E> T2 = x.rightChild;
            T2.parent = y;
            y.leftChild = T2;
        }
        else
        {
            y.leftChild = null;
        }
        y.parent = x;
        if (y.rightChild != null)
        {
            if (y.leftChild != null)
            {
                y.height = maxValue(h(y.leftChild), h(y.rightChild)) + 1;
            }
            else
            {
                y.height = y.rightChild.height + 1;
            }
        }
        else 
        {
            if (y.leftChild != null)
            {
                y.height = y.leftChild.height + 1;
            }
            else
            {
                y.height = 1;
            }
        }
        x.rightChild = y;
        if (x.leftChild != null)
        {
            x.height = maxValue(h(x.leftChild), h(x.rightChild)) + 1;
        }
        else
        {
            x.height = x.rightChild.height + 1;
        }
        return x;
    }
    public AVLNode<E> leftRotate(AVLNode<E> x)
    {
        AVLNode<E> y = x.rightChild;
        if (y.leftChild != null)
        {
            AVLNode<E> T2 = y.leftChild;
            T2.parent = x;
            x.rightChild = T2;
        }
        else
        {
            x.rightChild = null;
        }
        x.parent = y;
        if (x.leftChild != null)
        {
            if (x.rightChild != null)
            {
                x.height = maxValue(x.leftChild.height, x.rightChild.height) +1;
            }
            else
            {
                x.height = x.leftChild.height + 1;
            }
        }
        else
        {
            if (x.rightChild != null)
            {
                x.height = x.rightChild.height + 1;
            }
            else
            {
                x.height = 1;
            }
        }
        y.leftChild = x;
        if (y.rightChild != null)
        {
            y.height = maxValue(y.leftChild.height, y.rightChild.height) + 1;
        }
        else
        {
            y.height = y.leftChild.height + 1;
        }
        return y;
    }
    public AVLNode<E> insertBST(int v, E newClass, AVLNode<E> node)
    {
        if (node.data > v)
        {
            if (node.leftChild == null)
            {
               
                AVLNode<E> n = new AVLNode<E>(v);
                node.leftChild = n;
                n.newClass = newClass;
                n.parent = node;
                updateHeight(n);
                return n;
            }
            return insertBST(v, newClass, node.leftChild);
        }
        else
        {
            if (node.rightChild == null)
            {
                AVLNode<E> n = new AVLNode<E>(v);
                node.rightChild = n;
                n.newClass = newClass;
                n.parent = node;
                updateHeight(n);
                return n;
            }
            return insertBST(v, newClass, node.rightChild);
        }
    }
    public AVLNode<E> deleteBST(int v, AVLNode<E> n)
    {
        AVLNode<E> x = new AVLNode<E>(0);
        x.height = -2;
        if (n.data == v)
        {
            if (n.leftChild == null)
            {
                if (n.rightChild == null)
                {
                    if (n != root)
                    {
                        if (v >= n.parent.data)
                        {
                            n.parent.rightChild = null;
                            if (n.parent.leftChild != null)
                            {
                                n.parent.height = n.parent.leftChild.height + 1;
                            }
                            else
                            {
                                n.parent.height = 1;
                            }
                        }
                        else if (v < n.parent.data)
                        {
                            n.parent.leftChild = null;
                            if (n.parent.rightChild != null)
                            {
                                n.parent.height = n.parent.rightChild.height + 1;
                            }
                            else
                            {
                                n.parent.height = 1;
                            }
                        }
                        updateHeight(n.parent);
                        return n.parent;
                    }
                    else
                    {
                        System.out.println("THE ONLY ELEMENT CANNOT BE DELETED");
                        return x;
                    }
                }
                else
                {
                    if (n != root)
                    {
                        if (v >= n.parent.data)
                        {
                            n.parent.rightChild = n.rightChild;
                        }
                        else
                        {
                            n.parent.leftChild = n.rightChild;
                        }
                        n.rightChild.parent = n.parent;
                        n.parent.height = maxValue(h(n.parent.rightChild), h(n.parent.leftChild)) + 1;
                        updateHeight(n.parent);
                        return n.parent;
                    }
                    else
                    {
                        root = n.rightChild;
                        root.parent = null;
                        return root;
                    }
                }
            }
            else
            {
                if (n.rightChild == null)
                {
                    if (n != root)
                    {
                        if (v > n.parent.data)
                        {
                            n.parent.rightChild = n.leftChild;
                        }
                        else
                        {
                            n.parent.leftChild = n.leftChild;
                        }
                        n.leftChild.parent = n.parent;
                        n.parent.height = maxValue(h(n.parent.rightChild), h(n.parent.leftChild)) + 1;
                        updateHeight(n.parent);
                        return n.parent;
                    }
                    else
                    {
                        root = n.leftChild;
                        root.parent = null;
                        return root;
                    }
                }
                else
                {
                    AVLNode<E> r = n.rightChild;
                    while (r.leftChild != null)
                    {
                        r = r.leftChild;
                    }
                    n.data = r.data;
                    n.newClass = r.newClass;
                    return deleteBST(r.data, r);
                }
            }
        }
        else
        {
            if (v > n.data)
            {
                if (n.rightChild == null)
                {
                    return x;
                }
                else
                {
                    return deleteBST(v, n.rightChild);
                }
            }
            else
            {
                if (n.leftChild == null)
                {
                    return x;
                }
                else
                {
                    return deleteBST(v, n.leftChild);
                }
            }
        }
    }
    public void updateHeight(AVLNode<E> node)                      // assume that node's height is already correct.
    {
        if (node != root)
        {
            node.parent.height = maxValue(h(node.parent.leftChild), h(node.parent.rightChild)) + 1;
            updateHeight(node.parent);
            return;
            // if (node.parent.leftChild != null && node.parent.rightChild != null)
            // {
            //     if (node.parent.height != maxValue(h(node.parent.leftChild), h(node.parent.rightChild)) + 1)
            //     {
            //         node.parent.height = maxValue(h(node.parent.leftChild), h(node.parent.rightChild)) + 1;
            //         updateHeight(node.parent);
            //         return;
            //     }
            // }
            // else
            // {
            //     node.parent.height = node.height + 1;
            //     updateHeight(node.parent);
            //     return;
        }
        return;
    }
    public AVLNode<E> errorNode(AVLNode<E> node)               
    {
        AVLNode<E> n = node;
        AVLNode<E> x = new AVLNode<E>(0);
        x.height = -1;
        while (checkAVL(n))
        {
            if (n == root)
            {
                if (checkAVL(n))
                {
                    return x;
                }
                return n;
            }
            else
            {
                n = n.parent;
            }
        }
        return n;
    }
    public boolean checkAVL(AVLNode<E> node)
    {
        if (node.leftChild == null)
        {
            if (node.rightChild == null || node.rightChild.height == 1)
            {
                return true;
            }
        }
        else
        {
            if (node.rightChild != null)
            {
                if (Math.abs(node.leftChild.height - node.rightChild.height) <= 1)
                {
                    return true;
                }
            }
            else
            {
                if (node.leftChild.height == 1)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
class AVLNode<E>
{   
    int data;
    E newClass;
    AVLNode<E> parent;
    AVLNode<E> rightChild;
    AVLNode<E> leftChild;
    int height;
    public AVLNode(int x)
    {
        data = x;
        height = 1;
    }
}
class CapTreeInp
{
    int BinId;
    int actCap;
    int leftCap;
    LinkedList<Object> Obj;
    BinTreeInp BTI;
    public CapTreeInp(int Id, int Cap)
    {
        BinId = Id;
        actCap = Cap;
        leftCap = Cap;
    }
}
class ObjTreeInp
{
    int ObjId;
    int size;
    int BinId;
    Object O;
    public ObjTreeInp(int Id, int s)
    {
        ObjId = Id;
        size = s;
    }
}
class BinTreeInp
{
    int BinId;
    int actCap;
    AVLNode<CapTreeInp> Bin;
    public BinTreeInp(int Id, int Cap)
    {
        BinId = Id;
        actCap = Cap;
    }
}
class Object
{
    int ObjId;
    int size;
    public Object(int id, int s)
    {
        ObjId = id;
        size = s;
    }
}
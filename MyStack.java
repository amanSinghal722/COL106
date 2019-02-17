public class MyStack<E>
{
    linkedlist<E> stack = new linkedlist<E>();
    public void push(E item)
    {
        stack.insert(item);                     
    }
    public E pop()
    {
        E obj = null;
        try
        {
            obj = stack.removeFront();
        }
        catch (EmptyStackException e)
        {
            System.out.println(e);
        } 
        return obj;
    }
    public E peek()
    {
        E obj = null;
        try 
        {
            obj = stack.returnFront();
        }
        catch (EmptyStackException m)
        {
            System.out.println(m);
        }
        return obj;
    }
    public boolean empty()
    {
        if (stack.head == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private class linkedlist<E>
    {
        private Node<E> head;
        private void insert(E item)
        {
            Node<E> n = new Node<E>();
            n.data = item;
            n.next = null;
            if (head == null)
            {
                head = n;
            }
            else
            {
                n.next = head;
                head = n;
            }
        }
        private E removeFront() throws EmptyStackException
        {
            if (head == null)
            {
                throw new EmptyStackException("Stack is Empty");
            }
            else
            {
                E ret = head.data;
                head = head.next;
                return ret;
            }
        }
        private E returnFront() throws EmptyStackException
        {
            if (head == null)
            {
                throw new EmptyStackException("Stack is Empty");
            }
            else
            {
                E ret = head.data;
                return ret;
            }
        }
    }
    private class Node<E>
    {
        private E data;
        private Node<E> next;
    }
}
class EmptyStackException extends Exception
{
    public EmptyStackException(String s)
    {
        super(s);
    }
}
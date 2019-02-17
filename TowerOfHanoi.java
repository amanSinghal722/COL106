public class TowerOfHanoi
{
    public static void toh_with_recursion(int num_disks, int start_pos, int end_pos)
    {
        if (start_pos == end_pos)
        {
            return;
        }
        if (num_disks == 1)
        {
            System.out.println(start_pos + " " + end_pos);
        }
        else
        {
            toh_with_recursion(num_disks-1, start_pos, (6-start_pos-end_pos));
            System.out.println(start_pos + " " + end_pos);
            toh_with_recursion(num_disks-1, (6-start_pos-end_pos), end_pos);
        }
    }
    public static void toh_without_recursion(int num_disks, int start_pos, int end_pos)
    {
        if (start_pos == end_pos)
        {
            return;
        }
        MyStack<stack_inp> S = new MyStack<stack_inp>();
        stack_inp I = new stack_inp();
        I.num_disks = num_disks;
        I.start_pos = start_pos;
        I.end_pos = end_pos;
        I.call = 0;
        S.push(I);
        while (S.empty() == false)
        {
            stack_inp peek = S.peek();
            if (peek.num_disks == 1 && peek.call == 0)
            {
                System.out.println(peek.start_pos + " " + peek.end_pos);
                stack_inp out = S.pop();
            }
            else if (peek.call == 0)
            {
                stack_inp i = new stack_inp();
                i.num_disks = peek.num_disks - 1;
                i.start_pos = peek.start_pos;
                i.end_pos = (6 - peek.start_pos - peek.end_pos);
                i.call = 0;
                peek.call = 1;
                S.push(i);
            }
            else if (peek.call == 1)
            {
                System.out.println(peek.start_pos + " " + peek.end_pos);
                stack_inp i = new stack_inp();                
                i.num_disks = peek.num_disks - 1;
                i.start_pos = 6-peek.start_pos-peek.end_pos;
                i.end_pos = peek.end_pos;
                i.call = 0;
                peek.call = 2;
                S.push(i);
            }
            else if (peek.call == 2)
            {
                stack_inp out = S.pop();
            }
        }
    }
    public static class stack_inp
    {
        int num_disks; 
        int start_pos; 
        int end_pos;
        int call;
    }
}
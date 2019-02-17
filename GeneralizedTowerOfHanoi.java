class GeneralizedTowerOfHanoi
{
    public static void gtoh_with_recursion(int num_disks, int start_pos, int r, int b)
    {
        if (start_pos == r && start_pos == b)
        {
            return;
        }
        else if (r == b && r != start_pos)
        {
            toh_with_recursion(num_disks, start_pos, r);
        }
        else if (r != b)
        {
            if (num_disks == 1)
            {
                if (start_pos == b)
                {
                    return;
                }
                else
                {
                    System.out.println(start_pos + " " + b);
                }
            }
            else
            {
                if (num_disks % 2 == 0)
                {
                    if (r == start_pos)
                    {
                        gtoh_with_recursion(num_disks-1, start_pos, r, b);
                    }
                    else
                    {
                        toh_with_recursion(num_disks - 1, start_pos, (6-r-start_pos));
                        System.out.println(start_pos + " " + r);
                        gtoh_with_recursion(num_disks - 1, (6-r-start_pos), r, b);
                    }
                }
                else
                {
                    if (b == start_pos)
                    {
                        gtoh_with_recursion(num_disks -1, start_pos, r, b);
                    }
                    else
                    {
                        toh_with_recursion(num_disks - 1, start_pos, (6-b-start_pos));
                        System.out.println(start_pos + " " + b);
                        gtoh_with_recursion(num_disks - 1, (6-b-start_pos), r, b);
                    }
                }
            }
        }
    }
    public static void gtoh_without_recursion(int num_disks, int start_pos, int r, int b)
    {
        if (start_pos == r && start_pos == b)
        {
            return;
        }
        else if (r == b && r != start_pos)
        {
            toh_without_recursion(num_disks, start_pos, r);
        }
        else if (r != b)
        {
            gStack_inp I1 = new gStack_inp();
            MyStack<gStack_inp> GS = new MyStack<gStack_inp>();
            I1.num_disks = num_disks;
            I1.start_pos = start_pos;
            I1.r = r;
            I1.b = b;
            I1.tCall = 0;
            I1.gCall = 0;
            GS.push(I1);
            while (GS.empty() == false)
            {
                gStack_inp peek = GS.peek();
                if (peek.num_disks == 1)
                {
                    if (peek.b == peek.start_pos)
                    {
                        gStack_inp out = GS.pop();
                        return;
                    }
                    else
                    {
                        gStack_inp out = GS.pop();
                        System.out.println(peek.start_pos + " " + peek.b);
                        return;
                    }
                }
                if (peek.num_disks % 2 == 0)
                {
                    if (peek.r == peek.start_pos)
                    {
                        peek.num_disks = peek.num_disks - 1;
                    }
                    else
                    {
                        if (peek.tCall == 0)
                        {
                            toh_without_recursion(peek.num_disks - 1, peek.start_pos, (6-peek.r-peek.start_pos));
                            peek.tCall = 1;
                        }
                        else
                        {
                            System.out.println(peek.start_pos + " " + peek.r);
                            if (peek.gCall == 0)
                            {
                                gStack_inp I2 = new gStack_inp();
                                I2.num_disks = peek.num_disks - 1;
                                I2.start_pos = 6 - peek.start_pos - peek.r;
                                I2.r = peek.r;
                                I2.b = peek.b;
                                I2.tCall = 0;
                                I2.gCall = 0;
                                peek.gCall = 1;
                                GS.push(I2);
                            }
                            else
                            {
                                gStack_inp out = GS.pop();
                            }
                        }

                    }
                }
                else
                {
                    if (peek.b == peek.start_pos)
                    {
                        peek.num_disks = peek.num_disks - 1;
                    }
                    else
                    {
                        if (peek.tCall == 0)
                        {
                            toh_without_recursion(peek.num_disks - 1, peek.start_pos, (6-peek.b-peek.start_pos));
                            peek.tCall = 1;
                        }
                        else
                        {
                            System.out.println(peek.start_pos + " " + peek.b);
                            if (peek.gCall == 0)
                            {
                                gStack_inp I2 = new gStack_inp();
                                I2.num_disks = peek.num_disks - 1;
                                I2.start_pos = 6 - peek.start_pos - peek.b;
                                I2.r = peek.r;
                                I2.b = peek.b;
                                I2.tCall = 0;
                                I2.gCall = 0;
                                peek.gCall = 1;
                                GS.push(I2);
                            }
                            else
                            {
                                gStack_inp out = GS.pop();
                            }
                        }

                    }
                }
            }
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
    public static class stack_inp
    {
        int num_disks; 
        int start_pos; 
        int end_pos;
        int call;
    }
    public static class gStack_inp
    {
        int num_disks;
        int start_pos;
        int r;
        int b;
        int tCall;
        int gCall;
    }
}

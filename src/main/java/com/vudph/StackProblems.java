package com.vudph;

import java.util.Stack;

public class StackProblems {
    static class MinStack155 {
        Stack<Integer> stk = new Stack<>();
        Stack<Integer> minStk = new Stack<>();
        public MinStack155() {
        }

        public void push(int val) {
            stk.push(val);
            if (stk.isEmpty()) {
                minStk.push(val);
            } else {
                minStk.push(Math.min(val, minStk.peek()));
            }
        }
        public void pop() {
            stk.pop();
            minStk.pop();
        }

        public int top() {
            return stk.peek();
        }

        public int getMin() {
            return minStk.peek();
        }
    }

    static class MinEle {
        int val, min;
        public MinEle(int v, int m) {
            this.val = v;
            this.min = m;
        }
    }

    public int evalRPN150(String[] tokens) {
        Stack<Integer> stk = new Stack<>();
        int res = 0;
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+") || tokens[i].equals("-") ||
                    tokens[i].equals("*") || tokens[i].equals("/")) {
                int a = stk.pop();
                int b = stk.pop();
                if (tokens[i].equals("+")) {
                    stk.push(b + a);
                } else if (tokens[i].equals("-")) {
                    stk.push(b - a);
                } else if (tokens[i].equals("*")) {
                    stk.push(b * a);
                } else {
                    stk.push(b / a);
                }
            } else {
                stk.push(Integer.parseInt(tokens[i]));
            }
        }
        return stk.peek();
    }

    public static void main(String[] args) {
        StackProblems sp = new StackProblems();
        System.out.println("evalRPN150: " + sp.evalRPN150(new String[] {"4","13","5","/","+"}));
    }
}

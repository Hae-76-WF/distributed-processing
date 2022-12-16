package Parallel_threading.Logical_clock;

public class Stack {
    private int size;
    private Object[] arr;
    private int top = -1;

    public Stack(int size){
        this.size = size;
        arr = new Object[this.size];

    }
    public Stack(){
        this.size = 100;
        arr = new Object[this.size];
    }
    public void push(Object x){
        if (this.top != this.size-1){
            this.top ++;
            arr[this.top] = x;
        }else System.out.println("Stack is full");

    }
    public Object pop(){
        if (this.top == -1){
            System.out.println("Stack is empty");
            return null;
        }else{
            Object x = arr[this.top];
            this.top--;
            return x;
        }
    }
    public Object peek(){
        if (this.top == -1) return null;
        else return arr[this.top];
    }


}

// Stack.java
public class Stack {
    private Node top;
    // For storing multi-character operators
    public String specialOp;

    public Stack() {
        this.top = null;
        this.specialOp = "";
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Push an element onto the stack
    public void push(char data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    // Pop an element from the stack
    public char pop() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Cannot pop.");
            return '\0';
        }
        char data = top.data;
        top = top.next;
        return data;
    }

    // Peek at the top element without removing it
    public char peek() {
        if (isEmpty()) {
            System.out.println("Stack is empty. Cannot peek.");
            return '\0';
        }
        return top.data;
    }
}
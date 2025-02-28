import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/*
672115037 Phutawan Mueangma
ADT Programming Assignment 3
*/

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        String fileName;

        if (args.length < 1) {
            System.out.print("Enter input file name : ");
            fileName = userInput.nextLine().trim();
        } else {
            fileName = args[0];
        }

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            
            int expressionNumber = 1;
            while (scanner.hasNextLine()) {
                String infixExpression = scanner.nextLine().trim();
                if (!infixExpression.isEmpty()) {
                    System.out.println("Expression " + expressionNumber + ":");
                    System.out.println("Infix exp: " + infixExpression);
                    
                    if (isValidInfixExpression(infixExpression)) {
                        System.out.println("Valid");
                        String postfixExpression = infixToPostfix(infixExpression);
                        System.out.println("Postfix exp: " + postfixExpression);
                    } else {
                        System.out.println("Not-Valid");
                    }
                    expressionNumber++;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    private static boolean isValidInfixExpression(String expression) {
        if (expression == null || expression.isEmpty()) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        boolean expectOperand = true;
        char prevChar = ' ';

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                expectOperand = false;
            } else if (isOperator(c)) {
                if (expectOperand) {
                    return false;
                }
                expectOperand = true;
            } else if (c == '(') {
                stack.push(c);
                expectOperand = true;
            } else if (c == ')') {
                if (expectOperand && prevChar != '(') {
                    return false;
                }
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
                expectOperand = false;
            } else if (!Character.isWhitespace(c)) {
                return false;
            }

            if (!Character.isWhitespace(c)) {
                prevChar = c;
            }
        }
        
        return stack.isEmpty() && !expectOperand;
    }

    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
    }

    private static boolean isOperator(char c) {
        return "+-*/^><=!.&|".indexOf(c) != -1;
    }
    
    private static int precedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return -1;
        }
    }
}

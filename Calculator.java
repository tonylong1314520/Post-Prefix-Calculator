
import java.util.EmptyStackException;
import java.util.Stack;

/**.
 * @author tonyl
 *
 */
public class Calculator implements ICalculator {
  private static Calculator instance;
  private Stack<Number> operands;

  /**
   * this is a calculator instance.
   * @return instance.
   */
  public static Calculator getInstance() {
    if (instance == null) {
      instance = new Calculator();
    }
    return instance;
  }
  
  @Override
  public void clear() {
    // clears all calculation
    instance = null;
    // emptys the stack
    operands.clear();   
  }
  
  /**
   * Creates a new calculator.
   */
  private Calculator() {
    // initialize member variables
    operands = new Stack<Number>();
    instance = null;
  }

  // found code on https://www.geeksforgeeks.org/expression-evaluation/
  @Override
  public Number postFixCalculate(String expression) throws InvalidExpressionException {
    // convert the expressions to tokens
    // try
    // loop over tokens
    //  if tokens is a number
    //    convert string to number and push it to the operand stack
    //  else if token is an operator
    //    pop off the rhs from the operand stack
    //    pop off the lhs from the operand stack
    //    push doing the math onto the operand stack
    //  else 
    //    throw new InvalidExpressionException
    // pop the answer off of the operand stack 
    // if operand stack is not empty 
    //  throw new InvalidExpressionException
    // return answer
    // catch EmptyStackException
    //  throw InvalidExpressionException
    String[] tokens = expression.split(" |\\st");
    Number answer = null;
    try {
      for (int i = 0; i < tokens.length; i++) {
        if (isNumber(tokens[i])) {
          operands.push(convertToNumber(tokens[i])); 
        } else if (isOperator(tokens[i])) {
          operands.push(doTheMath(operands.pop(), operands.pop(), tokens[i]));
        } else {
          throw new InvalidExpressionException();
        }
      }
      answer = operands.pop();
      if (!operands.isEmpty()) {
        throw new InvalidExpressionException();
      }
      return answer;
    } catch (EmptyStackException e) {
      throw new InvalidExpressionException();
    }
  }
  
  private boolean isNumber(String s) {
    // try
    try {
      //integer.parseInt(s)
      Integer.parseInt(s);
      // return true
      return true;
    // catch NFE
    } catch (NumberFormatException a) {
      try {
        Double.parseDouble(s);
        return true;
      } catch (NumberFormatException b) {
        return false;
      }
    }

  }

  private Number convertToNumber(String s) throws InvalidExpressionException {
    // try
    //  return Integer.parseInt(s)
    // catch NFE
    //  try 
    //    return Double.parseDouble(s)
    //  catch NFE
    //    throw InvalidExpressionException
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException a) {
      try {
        return Double.parseDouble(s);
      } catch (NumberFormatException b) {
        throw new InvalidExpressionException();
      }
    }
  }
  
  private boolean isOperator(String s) {
    // switch on s
    // four cases :+-/*
    //  return true
    // default
    //  return false
    switch (s) {
      case "+" : 
        return true;
      case "-" : 
        return true;
      case "/" :
        return true;
      case "*" :
        return true;
      default :
        return false;
    }
  }
  
  private Number doTheMath(Number lhs, Number rhs, String operator) {
    // if lhs and rhs are integers
    //  cast lhs and rhs to integers
    //  switch on the operator
    //    do the math, return lhsInt + rhsInt
    // else 
    //  cast lhs and rhs to doubles
    //  switch on the operator
    //  return the answer.
    String a = lhs.toString();
    String b = rhs.toString();
    if (lhs instanceof Integer && rhs instanceof Integer) {
      int c = Integer.parseInt(a);
      int d = Integer.parseInt(b);
      switch (operator) {
        case "+" : 
          return d + c;
        case "-" :
          return d - c;
        case "/" :
          return d / c;
        case "*" :
          return d * c;
        default :
          return 0;
      }
    } else {
      double e = Double.parseDouble(a);
      double f = Double.parseDouble(b);
      switch (operator) {
        case "+" : 
          return f + e;
        case "-" :
          return f - e;
        case "/" :
          return f / e;
        case "*" :
          return f * e;
        default :
          return 0;
      }
    }
  }
  
  @Override
  public Number preFixCalculate(String expression) throws InvalidExpressionException {
    // convert the expressions to tokens
    // try
    // loop from end of tokens to front
    //  if tokens is a number
    //    convert string to number and push it to the operand stack
    //  else if token is an operator
    //    pop off the rhs from the operand stack
    //    pop off the lhs from the operand stack
    //    push doing the math onto the operand stack
    //  else 
    //    throw new InvalidExpressionException
    // pop the answer off of the operand stack 
    // if operand stack is not empty 
    //  throw new InvalidExpressionException
    // return answer
    // catch EmptyStackException
    //  throw InvalidExpressionException
    Number answer = null;
    String[] tokens = expression.split(" |\\st");
    try {
      for (int i = tokens.length - 1; i >= 0; i--) {
        if (isNumber(tokens[i])) {
          operands.push(convertToNumber(tokens[i]));
        } else if (isOperator(tokens[i])) {
          Number o1 = operands.pop();
          Number o2 = operands.pop();
          operands.push(doTheMath(o2, o1, tokens[i]));
        } else {
          throw new InvalidExpressionException();
        }
      }
      answer = operands.pop();
      if (!operands.isEmpty()) {
        throw new InvalidExpressionException();
      }
      return answer;
    } catch (EmptyStackException e) {
      throw new InvalidExpressionException();
    }
    //throw new UnsupportedOperationException();
  }

}

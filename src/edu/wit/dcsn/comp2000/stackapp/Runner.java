package edu.wit.dcsn.comp2000.stackapp;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* Noah D'Alelio, Corey Pierce, Jacob Casey
 * COMP-2000-03
 * Stack APP Project (Calculator)
 * 10/9/17
 */

public class Runner {

	public static void main(String args[]) {

		// Testing methods
		// System.out.println(Evaluate("1+1*3"));
		// System.out.println(parenthEval("(1+1)+(3*2)"));
		// System.out.println(parenthEval("10/-2"));

		try {
			readFile("Infix Calculator Expressions - valid -- 2016-10-13 01.txt");
			readFile("Infix Calculator Expressions - valid multi-digit -- 2017-10-04 01.txt");
			readFile("Infix Calculator Expressions - multi-digit with invalid expressions -- 2016-10-04 01.txt");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void readFile(String Path) {
		System.out.println("File: " + Path + "\n");
		System.out.printf("%-35s        %10s%n", "Expression", "Result");
		try (Scanner fileReader = new Scanner(new File(Path))) {
			while (fileReader.hasNextLine()) {
				String expression = fileReader.nextLine();
				System.out.printf("%-35s      = %10s%n", expression, parenthEval(expression));
			}
			System.out.println("======================================================\n");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
			System.exit(0);
		}
	}

	public static String parenthEval(String input) {
		boolean parenthetical = false; // if expression has parentheses
		int parenRight = 0; // locations of parentheses dealt with in current loop
		int parenLeft = 0;
		StringBuilder master = new StringBuilder(input); // StringBuilder object to store input
		while (master.length() > 1) { // while expression isn't full evaluated
			parenLeft = 0;
			parenRight = 0;
			parenthetical = false;
			for (int i = 0; i < master.length(); i++) { // find first end parentheses
				if (master.charAt(i) == ')') {
					parenRight = i;
					parenthetical = true;
					break;
				}
			}
			if (!parenthetical) {
				return Evaluate(master.toString()).toString(); // immediately evaluate if not parenthetical
			} else {
				StringBuilder out = new StringBuilder("");
				for (int i = parenRight; i >= 0; i--) {
					if (master.charAt(i) == '(') { // find corresponding open parentheses
						parenLeft = i;
						break;
					}
				}
				for (int i = parenLeft + 1; i < parenRight; i++) { // put expression inside parentheses in out
					out.append(master.charAt(i));
				}
				Integer replace = Evaluate(out.toString()); // evaluate number to replace parentheses
				int k = parenRight - parenLeft;
				while (k > 0) { 
					master.deleteCharAt(parenLeft + 1); // delete parentheses
					k--;
				}
				master.replace(parenLeft, parenLeft + 1, replace.toString()); // replace with evaluated number
			}
		}
		return master.toString(); // return fully evaluated expression
	}

	/**
	 * Method to solve simple expressions with no parenthesis.
	 * 
	 * @param Input
	 *            = Expression to evaluate as a string (Can not contain parenthesis)
	 * @return The value of the simple expression.
	 */
	public static Integer Evaluate(String Input) {

		VectorStack<Expression> ExpressionStack = new VectorStack<Expression>();

		Scanner stream = new Scanner(Input);
		stream.useDelimiter(""); // Allows us to read in one character at a time

		Integer LeftNum;
		Operator operator;
		Expression expression = null;

		// Loop through the stream until the expression is solved
		while (true) {
			LeftNum = null;
			operator = null;

			if (stream.hasNextInt()) {
				LeftNum = stream.nextInt(); // Reads in the number

				while (stream.hasNextInt()) { // This allows for multi-digit inputs
					LeftNum = LeftNum * 10 + stream.nextInt();
				}

			}
			if (stream.hasNext()) { // Reads in the operator
				operator = Operator.Type(stream.next().charAt(0));
			}

			// Will go to this case if there is a value for the left number and operator
			// (ex. 4+)
			if (LeftNum != null && operator != null) {
				// If the expression is not null, evaluate the expression with the new left number and operator
				expression = expression != null ? expression.Evaluate(new Expression(LeftNum, operator))
						: new Expression(LeftNum, operator); // If it is null, set it equal to the new left and new
																// operator

				// If the operation is addition or subtraction, it gets pushed onto the stack to
				// be evaluated later. This is for order of operations.
				switch (operator) {
				case ADD:
				case SUBTRACT:
					if (!ExpressionStack.isEmpty()) { // If the stack isn't empty then evaluate this with what is on the
														// stack and push it back
						Expression ex2 = ExpressionStack.pop();
						ExpressionStack.push(ex2.Evaluate(expression));
						expression = null;
					} else { // If the stack is empty then just push it onto the stack and clear expression
						ExpressionStack.push(expression);
						expression = null;
					}

				}

				// Will go to this case if there is a value for the left number but no operator
				// (ex. +4 [Usually the end of the expression])
			} else if (LeftNum != null && operator == null) {
				// If the expression is not null, evaluate the expression with the new left number and operator
				expression = expression != null ? expression.Evaluate(new Expression(LeftNum, Operator.NONE))
						: new Expression(LeftNum, Operator.NONE);
				// If it is null, set it equal to the new left and no operator
				
				if (!ExpressionStack.isEmpty()) { // If the stack isn't empty then evaluate this with what is on the
													// stack and push it back
					Expression ex2 = ExpressionStack.pop();
					expression = ex2.Evaluate(expression);
				}

				// Will go to this case if there is no value for the left number but there is a
				// value for the operator (ex. - [Usually at the beginning of an expression
				// showing negative first number])
			} else if (LeftNum == null && operator != null) {
				expression = expression != null ? expression : new Expression(0, operator);
				// There is a problem with this line when evaluating two operators in a row (ex. 6/-3)
			} else {
				break; // Expression is evaluated
			}

		}

		return expression.getValue();

	}
}
import java.util.Scanner;

// (5 * (4 - 3) + 2)

public class Runner {

	public static void main(String args[]) {

		VectorStack<Expression> ExpressionStack = new VectorStack<Expression>();

		String x = "5+(4*3)";
		Scanner stream = new Scanner(x);
		stream.useDelimiter("");

		Integer left;
		Operator op;
		Expression ex = null;

		while (true) {
			left = null;
			op = null;

			if (stream.hasNextInt()) {
				left = stream.nextInt();
			}
			if (stream.hasNext()) {
				op = Operator.Type(stream.next().charAt(0));
			}

			if (left != null && op != null) {
				ex = ex != null ? ex.Evaluate(new Expression(left, op)) : new Expression(left, op);

				switch (op) {
				case ADD:
				case SUBTRACT:
					if (!ExpressionStack.isEmpty()) {
						Expression ex2 = ExpressionStack.pop();
						ExpressionStack.push(ex2.Evaluate(ex));
						ex = null;
					} else {
						ExpressionStack.push(ex);
						ex = null;
					}
				case CLOSE:
					if (!ExpressionStack.isEmpty()) {
						Expression ex2 = ExpressionStack.pop();
						ex = ex2.Evaluate(ex);
						if(stream.hasNext()) {
							op = Operator.Type(stream.next().charAt(0));
							ex = new Expression(ex.getValue(), op);
						}
					} else {
						ExpressionStack.push(ex);
						ex = null;
					}
				}

			} else if (left != null && op == null) {
				ex = ex != null ? ex.Evaluate(new Expression(left, Operator.NONE))
						: new Expression(left, Operator.NONE);

				if (!ExpressionStack.isEmpty()) {
					Expression ex2 = ExpressionStack.pop();
					ex = ex2.Evaluate(ex);
				}

			} else if (left == null && op != null) {

				switch (op) {
				case OPEN:
					ex = null;					
				}
				
			} else {
				break;
			}

		}

		System.out.println(ex.getValue());

	}
}
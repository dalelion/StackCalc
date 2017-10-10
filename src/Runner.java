import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
// (5 * (4 - 3) + 2)

public class Runner {
	
	public static void main(String args[]){
		//StringBuilder k = new StringBuilder("test");
		//k.replace(1, 2, "oa");
		//System.out.println(k.toString());
		//System.out.println(Evaluate("1+1*3"));
		//System.out.println(parenthEval("(1+1)+(3*2)"));
		//System.out.println(parenthEval("10/-2"));
		try {
		readFile("Infix Calculator Expressions - valid multi-digit -- 2017-10-04 01.txt");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	public static void readFile (String Path) {
		System.out.printf("%5s   %10s%n", "File: ", Path);
		System.out.printf("%-35s      |%10s    | %10s%n", "Expression", "Result", "Expected");
		try(Scanner fileReader = new Scanner(new File(Path))){
			while(fileReader.hasNextLine()) {
				String expression = fileReader.nextLine();
				System.out.printf("%-35s      = %10s%10s%n", expression,  parenthEval(expression));
			}
		}catch(FileNotFoundException e) {
			System.out.println("File Not Found!");
			System.exit(0);
		}
	}
	
	public static String parenthEval(String input){
		boolean parenthetical = false;
		int parenRight = 0;
		int parenLeft = 0;
		StringBuilder master = new StringBuilder(input);
		for(int i = 0; i < input.length(); i++){
			if(master.charAt(i) == ')'){
				parenRight = i;
				parenthetical = true;
				break;
			}
		}
		
		if(!parenthetical){
			return Evaluate(master.toString()).toString();
		}
		
		
		else{
			while(master.length() > 1){
				parenLeft = 0;
				parenRight = 0;
				parenthetical = false;
				for(int i = 0; i < master.length(); i++){
					if(master.charAt(i) == ')'){
						parenRight = i;
						parenthetical = true;
						break;
					}
				}
				if(!parenthetical){
					return Evaluate(master.toString()).toString();
				}
				else{
					StringBuilder out = new StringBuilder("");
					for(int i = parenRight; i >= 0; i--){
						if(master.charAt(i) == '('){
							parenLeft = i;
							break;
						}
					}
					//System.out.println(parenRight); // test parenRight
					//System.out.println(parenLeft); // test parenLeft
					for(int i = parenLeft+1; i < parenRight; i++){ // 5 + (5 * 2)
						out.append(master.charAt(i));
					}
					Integer replace = Evaluate(out.toString()); // (5 * 2) = 10
					int k = parenRight - parenLeft;
					while(k > 0){ // 5 + ()
						master.deleteCharAt(parenLeft+1);
						k--;
					}
					master.replace(parenLeft, parenLeft+1, replace.toString()); // 5 + 10
				}
			}
			return master.toString();
		}
			/*
			 * stack1 -> (5*(4+5)-3)
			 * 
			 * push onto stack2 -> (5*(4+5)
			 * push back onto stack1 until ( -> stack1 = (5+4)-3)
			 * stack3 pulls out just simple parenthetical expression -> stack3 = (4+5)
			 */
	}

	public static Integer Evaluate(String x) {

		VectorStack<Expression> ExpressionStack = new VectorStack<Expression>();

//		String x = "3";
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
				
				while(stream.hasNextInt()) {
					left = left * 10 + stream.nextInt();
				}
				
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
				
				}

			} else if (left != null && op == null) {
				ex = ex != null ? ex.Evaluate(new Expression(left, Operator.NONE))
						: new Expression(left, Operator.NONE);

				if (!ExpressionStack.isEmpty()) {
					Expression ex2 = ExpressionStack.pop();
					ex = ex2.Evaluate(ex);
				}

			} else if (left == null && op != null) {
				ex = ex != null ? ex : new Expression(0, op);
				
			} else {
				break;
			}

		}

		return ex.getValue();
//		System.out.println(ex.getValue());

	}
}
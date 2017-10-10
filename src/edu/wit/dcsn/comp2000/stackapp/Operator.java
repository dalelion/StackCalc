package edu.wit.dcsn.comp2000.stackapp;

/* Noah D'Alelio, Corey Pierce, Jacob Casey
 * COMP-2000-03
 * Stack APP Project (Calculator)
 * 10/9/17
 */

public enum Operator {

	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE,
	OPEN,
	CLOSE,
	NONE;
	
	public static Operator Type (char c) {
		switch (c) {
		case '+':
			return Operator.ADD;
		case '-':
			return Operator.SUBTRACT;
		case '*':
			return Operator.MULTIPLY;
		case '/':
			return Operator.DIVIDE;
		case '(':
			return Operator.OPEN;
		case ')':
			return Operator.CLOSE;
		default:
			return Operator.NONE;
		}
	}
	
	@Override
	public String toString() {
		switch (this) {
		case ADD:
			return "+";
		case SUBTRACT:
			return "-";
		case MULTIPLY:
			return "*";
		case DIVIDE:
			return "/";
		case OPEN:
			return "(";
		case CLOSE:
			return ")";
		default:
			return "NONE";
		}
	}
}
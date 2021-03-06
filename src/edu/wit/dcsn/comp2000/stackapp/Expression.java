package edu.wit.dcsn.comp2000.stackapp;

/* Noah D'Alelio, Corey Pierce, Jacob Casey
 * COMP-2000-03
 * Stack APP Project (Calculator)
 * 10/9/17
 */

public class Expression {

	private Operator Op;
	private int Left;

	public Expression(int L, Operator O) {
		Left = L;
		Op = O;
	}

	public Expression Evaluate(Expression Right) {

		switch (this.Op) {
		case ADD:
			return new Expression(this.Left + Right.getValue(), Right.Op);
		case SUBTRACT:
			return new Expression(this.Left - Right.getValue(), Right.Op);
		case MULTIPLY:
			return new Expression(this.Left * Right.getValue(), Right.Op);
		case DIVIDE:
			return new Expression(this.Left / Right.getValue(), Right.Op);
		default:
			return this;
		}

	}
	
	public int getValue() {
		return this.Left;
	}
	
	@Override
	public String toString() {
		return "" + this.getValue() + this.Op.toString();
	}

}

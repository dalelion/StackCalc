import java.util.Arrays;
import java.util.EmptyStackException;

import java.util.Arrays;
import java.util.EmptyStackException;



/**
    A class of stacks whose entries are stored in an array.
    @author Frank M. Carrano and Timothy M. Henry
    @version 4.0
 */

public class ArrayStack<T> implements StackInterface<T>
{
	private T[] stack;    // Array of stack entries
	private int topIndex; // Index of top entry
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;
	
	public static void main(String args[]){
		ArrayStack<String> tester = new ArrayStack<>(100);
		for(int i = 0; i < 10; i++){
			tester.push("test");
		}
		System.out.println("Expected: test\nActual: " + tester.peek()); // test peek
		System.out.println("Expected: test\nActual: " + tester.pop()); // test pop
		tester.clear();
		System.out.println("Expected: true\nActual: " + tester.isEmpty()); // test clear and isEmpty
	}

	public ArrayStack()
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor

	public ArrayStack(int initialCapacity)
	{
		checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[])new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		initialized = true;
	} // end constructor

	//  < Implementations of the stack operations go here. >
	public void push(T newEntry)
	{
		checkInitialization();
		ensureCapacity();
		stack[topIndex + 1] = newEntry;
		topIndex++;
	} // end push

	// 6.18
	public T peek()
	{
		checkInitialization();
		if (isEmpty())
			throw new EmptyStackException();
		else
			return stack[topIndex];
	} // end peek

	// 6.19
	public T pop()
	{
		checkInitialization();
		if (isEmpty()){
			throw new EmptyStackException();
		}
		else{
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		}
	} // end pop

	// 6.20
	public boolean isEmpty()
	{
		return topIndex < 0;
	} // end isEmpty

	// 6.20
	public void clear()
	{
		topIndex = -1;	
	} // end clear
	
	//  < Implementations of the private methods go here; checkCapacity and
	//    checkInitialization are analogous to those in Chapter 2. >
	
	private void ensureCapacity(){
		if(topIndex == stack.length -1){
			int newLength = 2 * stack.length;
			checkCapacity(newLength);
			stack = Arrays.copyOf(stack, newLength);
		}
	}
	
	private void checkInitialization(){
		if (!initialized)
			throw new SecurityException ("ArrayStack object is not initialized properly.");
	}
	
	private void checkCapacity(int capacity)
	{
		if (capacity > MAX_CAPACITY)
			throw new IllegalStateException("Attempt to create a stack " +
					"whose capacity exceeds " +
					"allowed maximum.");
	} // end checkCapacity
	//  . . .
} // end ArrayStack
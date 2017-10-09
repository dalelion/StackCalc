import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * A class of stacks whose entries are stored in an array.
 * @author corey, jacob
 *
 */
public final class ArrayStack<T> implements StackInterface<T> {
	
	private T[] stack; //Array of stack entries
	private int topIndex; //Index of top entry
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;	
	private static final int MAX_CAPACITY = 10000;
	
	public ArrayStack() {
		this(DEFAULT_CAPACITY);
	} //end default constructor
	
	public ArrayStack(int initialCapacity){
		checkCapacity(initialCapacity);
		
		//The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[])new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		initialized = true;
	} //end constructor
	
	/**
	 * Checks to see if the stack has room for more entries
	 * @param capacity	the amount of entries in the stack array
	 * @return	True if there is still room 
	 */
	//not sure if this is the way to do this method
	private boolean checkCapacity(int capacity){
		return stack.length < MAX_CAPACITY;
	}
	/**
	 * checks to see if the stack array is initialized.
	 * If not then throws a SecurityException
	 */
	private void checkInitialization() {
		if(!initialized){
			throw new SecurityException(
					"Stack Array object "
					+"is not initialized properly"
					);
		}
	}
	
	@Override
	public void push(T newEntry) {
		checkInitialization();
		ensureCapacity();
		stack[topIndex + 1] = newEntry;
		topIndex++;
	} //end push
	
	private void ensureCapacity(){
		if(topIndex == stack.length -1){ //if array is full, double its size
			int newLength = 2 * stack.length;
			checkCapacity(newLength);
			stack = Arrays.copyOf(stack, newLength);
		} //end if
	} //end ensureCapacity

	@Override
	public T pop() {
		checkInitialization();
		if(isEmpty()){
			throw new EmptyStackException();
		}else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		} //end if 
	} //end pop

	@Override
	public T peek() {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyStackException();
		}else {
			return stack[topIndex];
		}
	} //end of peek

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
	}

} //end of ArrayStack

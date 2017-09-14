// Name: Ziqian Fu
// USC loginid: ziqianfu
// CS 455 PA4
// Fall 2016
import java.io.IOException;
/**
 * 
 * @author ziqianfu
 * Exception class which deal with lacking arguments.
 *
 */
public class InsufficientArgumentsException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InsufficientArgumentsException(){}
	public InsufficientArgumentsException(String s){
		super(s);
	}
}

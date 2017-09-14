// Name: Ziqian Fu
// USC loginid: ziqianfu
// CS 455 PA4
// Fall 2016
import java.io.IOException;
/**
 * 
 * @author ziqianfu
 * Exception class which deal with illegal arguments
 */
public class BadDataException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BadDataException(){}
	public BadDataException(String s){
		super(s);
	}
}

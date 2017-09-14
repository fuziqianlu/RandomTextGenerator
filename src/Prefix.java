// Name: Ziqian Fu
// USC loginid: ziqianfu
// CS 455 PA4
// Fall 2016
import java.util.LinkedList;
import java.util.ListIterator;
/**
 * 
 * @author ziqianfu
 * the class which stores prefix and realize associate functions
 */
public class Prefix {
	private final int HASH_MULTIPLIER=31;
	private LinkedList<String> pre;
	private int len;
	/**
	 * 
	 * @param a the linkedList of words in the prefix
	 * @param l the length of the prefix
	 */
	public Prefix(LinkedList<String> a, int l){
		len=l;
		pre=new LinkedList<String>(a);
	}
	/**
	 * 
	 * @return the length of prefix
	 */
	public int getLength(){
		return len;
	}
	/**
	 * used to generate the string format prefix
	 */
	public String toString(){
		ListIterator<String> iter=pre.listIterator();
		String s=iter.next();
		while(iter.hasNext()){
			s=s+" "+iter.next();
		}
		return s;
	}
	/**
	 * generate hashcode.
	 */
	public int hashCode(){
		ListIterator<String> iter=pre.listIterator();
		int h1=iter.next().hashCode();
		int h2=0;
		if(pre.size()>1){
			h2=iter.next().hashCode();
		}
		int h=0;
		h=h1*HASH_MULTIPLIER+h2;
		while(iter.hasNext()){
			h=HASH_MULTIPLIER*h+iter.next().hashCode();
		}
		return h;
	}
	/**
	 * 
	 * @param str the next word the generate the next prefix
	 * @return the next prefix with a new end word
	 */
	public Prefix generateNext(String str){
		LinkedList<String> tmp=new LinkedList<String>(pre);
		tmp.removeFirst();
		tmp.addLast(str);
		Prefix result=new Prefix(tmp, len);
		return result;
	}

	public boolean equals(Object obj){
		Prefix tmp=(Prefix)obj;
		if(len!=tmp.getLength()) return false;
		if(this.toString().equals(tmp.toString())) return true;
		else return false;
	}
	public static void main(String[] args) {
		LinkedList<String> tp= new LinkedList<String>();
		tp.add("tdd");
		tp.add("tbb");
		Prefix a=new Prefix(tp, 2);
		Prefix b=new Prefix(tp, 2);
		boolean res=a.equals(b);
		System.out.println(res);
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(a.toString()==b.toString());
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		

	}

}

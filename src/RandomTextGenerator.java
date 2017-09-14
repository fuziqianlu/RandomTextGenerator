// Name: Ziqian Fu
// USC loginid: ziqianfu
// CS 455 PA4
// Fall 2016
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
/**
 * 
 * @author ziqianfu
 *	the class which creates the random text by the input data.
 */
public class RandomTextGenerator {
	private Random r;
	private int numOfWords;
	private Map<Prefix, ArrayList<String>> dictionary;
	/**
	 * 
	 * @param the prefix-successors map used to generate random text;
	 * @param numWords the length of the generated text.
	 * @throws BadDataException
	 */
	public RandomTextGenerator(Map<Prefix, ArrayList<String>> dic, int numWords) 
			throws BadDataException{
		if(numWords<0){
			throw new BadDataException("numWords could not be less than 0");
		}
		r=new Random();
		numOfWords=numWords;
		dictionary=dic;
	}
	/**
	 *
	 * @return a initial prefix which has successors.
	 */
	public Prefix generateInitial(){
		int index = r.nextInt(dictionary.size());
		Iterator<Prefix> iter= dictionary.keySet().iterator();
		for(int i=0;i<index;i++){
			iter.next();
		}
		Prefix tmp=iter.next();
		
		if(dictionary.get(tmp).isEmpty()){
			return generateInitial();
		}
		else return tmp;
	}
	/**
	 * 
	 * @return generate the random text without line break.
	 */
	public String generateText(){
		String result="";
		Prefix initial=generateInitial();
		Prefix forward=initial;
		int count=0;
		while(count<numOfWords){
			result+=" ";
			int sz=dictionary.get(forward).size();
			if(sz==0){
				forward=generateInitial();
			}
			int nextIndex=r.nextInt(dictionary.get(forward).size());
			String newWord=dictionary.get(forward).get(nextIndex);
			result+=newWord;
			forward=forward.generateNext(newWord);
			count++;
		}
		return result;
		
	}
	/**
	 * a help method to separate the Text to multiple lines.
	 * @param charPerLine the number of character every line contains.
	 * @param s the input text
	 * @return a well-formatted text.
	 * @throws BadDataException.
	 */
	public String separateText(int charPerLine, String s) throws BadDataException{
		if(s.length()==0){
			throw new BadDataException("no random ext generated");
		}
		int count=0;
		String str=s.substring(1);
		Scanner line = new Scanner(str);
		String result="";
		result+=line.next();
		count+=result.length();
		
		while(line.hasNext()){
			String tmp=line.next();
			
			if(count+tmp.length()+1>charPerLine){
					result+="\r\n";
					result+=tmp;
					count=tmp.length();
			}
			else{
				result+=" ";
				result+=tmp;
				count=count+tmp.length()+1;
			}
		}
		line.close();
		return result;
		
	}
	/**
	 * the class used to debug by printing every step when generating random text.
	 * @return the generated random text.
	 */
	public String generateTextDebugModel(){
		r=new Random(1);
		String result="";
		Prefix initial=generateInitial();
		System.out.println("DEBUG: chose a new initial prefix: "+initial.toString());
		Prefix forward=initial;
		int count=0;
		while(count<numOfWords){
			result+=" ";
			System.out.println("DEBUG: prefix: "+forward.toString());
			
			int sz=dictionary.get(forward).size();
			if(sz==0){
				System.out.println("DEBUG: successors: "+"<END OF FILE>");
				forward=generateInitial();
				System.out.println("DEBUG: chose a new initial prefix: "+forward.toString());
				System.out.println("DEBUG: prefix: "+forward.toString());
				System.out.println("DEBUG: successors: "+dictionary.get(forward));
			}
			else{
				System.out.println("DEBUG: successors: "+dictionary.get(forward));
			}
			int nextIndex=r.nextInt(dictionary.get(forward).size());
			String newWord=dictionary.get(forward).get(nextIndex);
			System.out.println("DEBUG: word generated: "+newWord);
			result+=newWord;
			forward=forward.generateNext(newWord);
			count++;
		}
		return result;
		
	}
	public static void main(String[] args) {
		

	}

}

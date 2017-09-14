// Name: Ziqian Fu
// USC loginid: ziqianfu
// CS 455 PA4
// Fall 2016
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import java.util.Scanner;
import java.util.ArrayList;
/**
 * 
 * @author ziqianfu
 * the main class which reads and writes data files and handles associated exceptions. 
 */
public class GenText {
	private static final int CHAR_PER_LINE=80;
	public static void main(String[] args) {
		try {
			boolean debugModel=false;
			int startIndex=0;
			checkCommandLine(args);
			if(args[0].equals("-d")){
				debugModel=true;
				startIndex=1;
			}
			int prefixLen=Integer.parseInt(args[startIndex]);
			int numWords=Integer.parseInt(args[startIndex+1]);
			String sourceFile=args[startIndex+2];
			String outFile=args[startIndex+3];
			File input=new File(sourceFile);
			Scanner in=new Scanner(input);
			PrintWriter out = new PrintWriter(outFile);
			Map<Prefix, ArrayList<String>> dictionary = generateMap(in, prefixLen);
			RandomTextGenerator rtg=new RandomTextGenerator(dictionary, numWords);
			String str;
			if(debugModel==true){
				str=rtg.generateTextDebugModel();
			}
			else{
				str=rtg.generateText();
			}
			String str1=rtg.separateText(CHAR_PER_LINE, str);
			out.print(str1);	
			in.close();
			out.close();

		} catch (InsufficientArgumentsException e){
			System.out.println("ERROR: "+e.getMessage());
			System.out.println("You should run the program in such command:"
					+ "java GenText [-d] prefixLength numWords sourceFile outFile ");
		} catch(BadDataException e){
			System.out.println("ERROR: "+e.getMessage());
			if(!e.getMessage().equals("prefixLength >= number of words in sourceFile")){
				System.out.println("You should run the program in such command:"
						+ "java GenText [-d] prefixLength numWords sourceFile outFile ");
			}
		} catch (NumberFormatException e) {
			System.out.println("ERROR: prefixLength and numWords must be integers");
			System.out.println("You should run the program in such command:"
					+ "java GenText [-d] prefixLength numWords sourceFile outFile ");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * 
	 * @param in the Scanner which could read strings from input file.
	 * @param prefixLength the length of prefix
	 * @return a hashmap which map prefix and successors together.
	 * @throws BadDataException
	 */
	public static Map<Prefix, ArrayList<String>> generateMap(Scanner in, int prefixLength)
			throws BadDataException{
		if(prefixLength<1){
			throw new BadDataException("prefixLength <1, it should not be less than 1.");
		}
		Map<Prefix, ArrayList<String>> dictionary = new HashMap<Prefix, ArrayList<String>>();
		LinkedList<String> preq=new LinkedList<String>();
		for(int i=0;i<prefixLength;i++){
			if(!in.hasNext()){
				throw new BadDataException("prefixLength >= number of words in sourceFile");
			}
			preq.add(in.next());
		}
		if(!in.hasNext()){
			throw new BadDataException("prefixLength >= number of words in sourceFile");
		}
		Prefix init=new Prefix(preq, prefixLength);
		dictionary.put(init, new ArrayList<String>());
		while(in.hasNext()){
			String tmp=in.next();
			dictionary.get(init).add(tmp);
			preq.removeFirst();
			preq.addLast(tmp);
			Prefix tmpPre=new Prefix(preq, prefixLength);
			if(dictionary.keySet().contains(tmpPre)==false){
				dictionary.put(tmpPre, new ArrayList<String>());
			}
			init=tmpPre;
		}
		return dictionary;
	}
	
	public static boolean checkCommandLine(String[] args) throws InsufficientArgumentsException{
		if(args.length==0){
			throw new InsufficientArgumentsException("No command-line arguments");
		}
		if(args[0].equals("-d")&&args.length<5){
			throw new InsufficientArgumentsException("Missing command-line arguments");
		}
		if(!args[0].equals("-d")&&args.length<4){
			throw new InsufficientArgumentsException("Missing command-line arguments");
		}
		return true;
		
	}
}

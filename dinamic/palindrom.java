import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

class Step implements Comparable<Step>{
	String str;
	int n;
	public Step(){}
	public Step(String s, int n){
		str=s;
		this.n=n;
	}
	@Override
	public int compareTo(Step o) {
		return n-o.n;
	}
	@Override
	public String toString(){
		return str+" "+String.valueOf(n);
	}
}

public class Demo {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner (new File("input.txt"));
		String s=in.next();
		Step[][]array=new Step[s.length()][s.length()];
		for (int i=0; i<s.length(); i++){
			array[i][i]=new Step(s.substring(i,i+1),1);
		}
		for (int i=0; i<s.length()-1; i++){
			if (s.charAt(i)==s.charAt(i+1))
				array[i+1][i]=new Step(s.substring(i,i+2),2);
			else
				array[i+1][i]=new Step(s.substring(i,i+1),1);
		}
		for (int i=3; i<=s.length(); i++){
			for (int j=0; j<=s.length()-i; j++){
				String substr=s.substring(j,j+i);
				if (substr.charAt(0)==substr.charAt(i-1)){
					array[j+i-1][j]=new Step(substr.charAt(0)+array[j+i-2][j+1].str+substr.charAt(0),
							array[j+i-2][j+1].n+2);
				}
				else{
					array[j+i-1][j]=max(array[j+i-2][j],array[j+i-1][j+1]);
				}
			}
		}
		PrintStream filewriter = new PrintStream(new File("output.txt"));
		filewriter.println(array[s.length()-1][0].n);
		filewriter.print(array[s.length()-1][0].str);
	}

	private static Step max(Step step, Step step2) {
		if (step.compareTo(step2)>=0)
			return step;
		return step2;
	}

}


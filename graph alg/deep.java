import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Demo {
	static int [][]graph;
	static int[] use;
	static boolean answer=true;
	static int n;

	public static void deep(int i, int numb){
		use[i]=numb;
		for (int j=0; j<n; j++){
			if (graph[i][j]==1){
				if (use[j]==numb){
					answer=false;
					break;
				}
				if (use[j]==0)
					deep(j, 3-numb);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner (new File("input.in"));
		n=in.nextInt();
		graph=new int[n][n];
		use=new int [n];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				graph[i][j]=in.nextInt();
			}
		}
		for (int i=0; i<n; i++){
			if (use[i]==0)
				deep(i,1);
		}
		PrintStream filewriter = new PrintStream(new File("output.out"));
		if (answer)
		{
			int count=1;
			filewriter.println("YES");
			for (int i=0; i<n; i++){
				if (use[i]==1 && count<n){
					count++;
					filewriter.print(i+1+" ");
				}
			}
		}
		else{
			filewriter.print("NO");
		}
	}

}


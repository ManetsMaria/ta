import java.io.*;
import java.util.Scanner;
import java.util.*;
public class Demo{
    public static void main(String args[]) throws FileNotFoundException{
        Scanner in = new Scanner(new File("input.txt"));
        int n=in.nextInt();
        int [] arr= new int [n];
        for (int i=0; i<n; i++)
            arr[i]=in.nextInt();
        int[] dp = new int[arr.length];
        int len = 0;

        for(int x : arr) {
            int i = Arrays.binarySearch(dp, 0, len, x);
            if(i < 0) i = -(i + 1);
            dp[i] = x;
            if(i == len) len++;
        }

        PrintStream filewriter=new PrintStream ("output.txt");
        filewriter.print(len);
    }
}


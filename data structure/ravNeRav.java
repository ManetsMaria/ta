import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.util.regex.*;
public class Demo{
    static final String ok="Yes";
    static final String neok="No";
    static class MySet{
        int [] matrix;
        int [] rank;
        public MySet(int n){
            matrix=new int [n];
            rank=new int [n];
        }
        public void MakeSet(int x)
        {
            //System.out.println("MakeSet"+x);
            if (matrix[x]==0){
                matrix[x] = x;
                rank[x]=1;
            }
            //System.out.println("MakeSet"+matrix[x]);
        }
        public int Find(int x)
        {
            //System.out.println("Find"+x);
            if (matrix[x] == x) return x;
            //System.out.println("ContinueFind"+x);
            return this.Find(matrix[x]);
        }
        public boolean Unite(int x, int y)
        {
            //System.out.println("Unite 1"+y+" "+x);
            x = Find(x);
            //System.out.println("Unite 2"+y+" "+x);
            y = Find(y);
            //System.out.println("Unite 3"+y+" "+x);
            if (x==y)
                return true;
            //System.out.println("Unite false"+y+" "+x);
            if (rank[x]<rank[y]){
                matrix[x] = y;
                rank[y]+=rank[x];
            }
            else{
                matrix[y] = x;
                rank[x]+=rank[y]; 
            }
            return false;
        }
        public String toString(){
            String str="";
            for (int i=0; i<matrix.length; i++){
                str+=String.valueOf(matrix[i]);
            }
            return str;
        }
    }
    public static String s() throws FileNotFoundException{
        Scanner in = new Scanner(new File("equal-not-equal.in"));
        Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        int n=in.nextInt();
        int l=in.nextInt();
        MySet equals=new MySet(n+1);
        ArrayList <Integer> list = new ArrayList();
       // MySet notEquals=new MySet(n+1);
        for (int i=1; i<=l; i++){
                String str=in.next();
                Matcher matcher=pat.matcher(str);
                matcher.find();
                int x=Integer.valueOf(matcher.group());
                //System.out.print(x);
                str=in.next();
                char eq=str.charAt(0);
                //System.out.print(eq);
                str=in.next();
                matcher=pat.matcher(str);
                matcher.find();
                int y=Integer.valueOf(matcher.group());
                //System.out.println(y);
                if (eq=='='){
                        //System.out.println(notEquals.Find(y));
                    //if (notEquals.Find(x)==notEquals.Find(y) && notEquals.Find(x)!=0){
                       // return neok;
                   // }
                    equals.MakeSet(x);
                    equals.MakeSet(y);
                    equals.Unite(x,y);
                    //System.out.println(equals.toString());
                    //System.out.println(notEquals.toString());
                }
                else{
                    if (x==y)
                        return neok;
                   list.add(x);
                   list.add(y);
                }
            }
            for (int i=0; i<list.size(); i=i+2){
                if (equals.Find(list.get(i))==equals.Find(list.get(i+1)) && equals.Find(list.get(i))!=0)
                    return neok;
            }
            return ok;
    }
    public static void main(String args[]) throws FileNotFoundException{
        PrintStream filewriter=new PrintStream ("equal-not-equal.out");
        String answer=s();
        filewriter.print(answer);
    }
}


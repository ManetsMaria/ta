import java.io.*;
import java.util.Scanner;
import java.util.*;
public class Demo{
    static class Point{
        public int x;
        public int y;
        public int z;
        public Point(int x, int y, int z){
            this.x=x;
            this.y=y;
            this.z=z;
        }
        @Override
    public String toString(){
        StringBuffer s=new StringBuffer();
        s.append(x);
        s.append(" ");
        s.append(y);
        s.append(" ");
        s.append(z);
        return s.toString();
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Point)) return false;
        Point other=(Point) obj;
        return x==other.x && other.y==y && other.z==z;
    }
    public Point next(Point p){
        if (x!=p.x){
            if (x>p.x)
                return new Point(x-1,y, z);
            return (new Point(x+1, y, z));
        }
        if (y!=p.y){
            if (y>p.y)
                return new Point(x,y-1, z);
            return (new Point(x, y+1, z));
        }
        if (z>p.z)
            return new Point(x, y, z-1);
        return new Point(x, y, z+1);
    }
    }
    static class MySet{
        Point [][][] matrix;
        int [][][] rank;
        public MySet(int n, int m, int k){
            matrix=new Point [n][m][k];
            rank=new int [n][m][k];
        }
        public void MakeSet(Point x)
        {
           // System.out.println("MakeSet"+x.toString());
            if (matrix[x.x][x.y][x.z]==null){
                matrix[x.x][x.y][x.z] = x;
                rank[x.x][x.y][x.z]=1;
            }
           // System.out.println("MakeSet"+matrix[x.x][x.y][x.z].toString());
        }
        public Point Find(Point x)
        {
           // System.out.println("Find"+x.toString());
            if (matrix[x.x][x.y][x.z] == x) return x;
           // System.out.println("ContinueFind"+x.toString());
            return this.Find(matrix[x.x][x.y][x.z]);
        }
        public boolean Unite(Point x, Point y)
        {
           // System.out.println("Unite 1"+y.toString()+" "+x.toString());
            x = Find(x);
            //System.out.println("Unite 2"+y.toString()+" "+x.toString());
            y = Find(y);
            //System.out.println("Unite 3"+y.toString()+" "+x.toString());
            if (x==y)
                return true;
            //System.out.println("Unite false"+y.toString()+" "+x.toString());
            if (rank[x.x][x.y][x.z]<rank[y.x][y.y][y.z]){
                matrix[x.x][x.y][x.z] = y;
                rank[y.x][y.y][y.z]+=rank[x.x][x.y][x.z];
            }
            else{
                matrix[y.x][y.y][y.z] = x;
                rank[x.x][x.y][x.z]+=rank[y.x][y.y][y.z]; 
            }
            return false;
        }
    }
    public static void main(String args[]) throws FileNotFoundException{
        Scanner in = new Scanner(new File("input.txt"));
        PrintStream filewriter=new PrintStream ("output.txt");
        int n=in.nextInt();
        int m=in.nextInt();
        int k=in.nextInt();
        int l=in.nextInt();
        boolean flag=false;
        MySet ms=new MySet(n+1, m+1, k+1);
        for (int i=1; i<=l; i++){
            Point one=new Point(in.nextInt(),in.nextInt(), in.nextInt());
            ms.MakeSet(one);
            //System.out.println("1");
            Point two=new Point(in.nextInt(),in.nextInt(), in.nextInt());
            while(!one.equals(two)){
                Point next=one.next(two);
                //System.out.println("2");
                ms.MakeSet(next);
                //System.out.println("3");
                if (ms.Unite(one, next))
                {
                    //System.out.println("4");
                    if (flag){
                            filewriter.println();
                        }
                       // System.out.println("5");
                        filewriter.print(one);
                        filewriter.print(" ");
                        filewriter.print(next);
                        flag=true;
                }
                //System.out.println("6");
                one=next;
            }
        }
    }
}


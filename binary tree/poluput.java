import java.io.*;
import java.util.Scanner;
import java.util.*;
class BSTree {
    static class Node {
        int key;
        int value;
        Node left, right;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    private int H=0;
    public int getH(){
        return H;
    }
    private Node root = null;
    PrintStream filewriter;
    public boolean containsKey(int k) {
        Node x = root;
        while (x != null) {
            int cmp = Integer.compare(k,x.key);
            if (cmp == 0) {
                return true;
            }
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return false;
    }

    public int get(int k) {
        Node x = root;
        while (x != null) {
            int cmp = Integer.compare(k,x.key);
            if (cmp == 0) {
                return x.value;
            }
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return 0;
    }

    public void add(int k) {
        Node x = root, y = null;
        int level=0;
        while (x != null) {
            int cmp = Integer.compare(k,x.key);
            if (cmp == 0) {
                return;
            } else {
                y = x;
                level++;
                if (cmp < 0) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
        }
        H=Math.max(H, level);
        Node newNode = new Node(k, level);
        if (y == null) {
            root = newNode;
        } else {
            if (Integer.compare(k,y.key) < 0) {
                y.left = newNode;
            } else {
                y.right = newNode;
            }
        }
    }
    private Node find_min(Node v){
        if (v.left != null)
            return find_min(v.left);
        return v;
    }
    public void remove(int x){
        root=delete_recursively(root,x);
    }
    private Node delete_recursively(Node v, int x){
        if (v==null)
            return null;

        if (x < v.key){
            v.left = delete_recursively(v.left, x);
            return v;
        }
        if (x > v.key){
            v.right = delete_recursively(v.right, x);
            return v;
        }

        if (v.left==null)
            return v.right;
        if (v.right==null)
            return v.left;
        int min_key = find_min(v.right).key;
        v.key = min_key;
        v.right = delete_recursively(v.right, min_key);
        return v;
    }
    public boolean isEmpty() {
        return (root == null);
    }
    public void printRoLR(){
        if (isEmpty())
            return;
        try{
            filewriter = new PrintStream(new File("tst.out"));
            printRoLR(root);
        }
        catch(FileNotFoundException a){ System.out.println("cvj");}
    }
    private void printRoLR(Node node){
        if (node==null)
            return;
        filewriter.println((node.key));
        printRoLR(node.left);
        printRoLR(node.right);

    }
    public TreeSet <Integer> rootWay(int H){
        TreeSet <Integer> answ=new TreeSet <Integer>();
        rootWay(root, H, answ);
        return answ;
    }
    private TreeSet <Integer> rootWay(Node  node, int H, TreeSet <Integer> r){
        TreeSet <Integer> forReturn = new TreeSet <Integer>();
        if (node==null)
            return forReturn;
        if (node.left==null && node.right==null)
        {
            forReturn.add(0);
            return forReturn;
        }
        TreeSet <Integer> left=rootWay(node.left, H, r);
        TreeSet <Integer> right=rootWay(node.right, H, r);
        if (r.size()>0 && node.value<get(r.iterator().next()))
            return forReturn;
        //System.out.println(node.key);
        Iterator <Integer> itr=right.iterator();
        Iterator <Integer> itl;
        outer:  while (itr.hasNext()){
            itl=left.iterator();
            int i=itr.next();
            forReturn.add(i+1);
            outer2: while (itl.hasNext()){
                int y=itl.next();
                if (i+y+2==H){
                    if (r.size()>0 && node.value>get(r.iterator().next()))
                        r.clear();
                    r.add(node.key);
                    break outer;
                }
                if (i+y+2>H){
                    break outer2;
                }
            }
        }
        itl=left.iterator();
        while(itr.hasNext()){
            forReturn.add(itr.next()+1);
        }
        while (itl.hasNext()) {
            forReturn.add(itl.next()+1);
        }
        return forReturn;
    }

    public int answer(TreeSet <Integer> t){
        int n=t.size();
        if (n%2==0)
            return -997;
        n=n/2+1;
        int c=1;
        Iterator <Integer> it = t.iterator();
        while(it.hasNext()){
            if (c==n)
                return it.next();
            c++;
            it.next();
        }
        return 0;
    }
}
class Solve{
    public void solve()throws FileNotFoundException{
        BSTree myTree = new BSTree ();
        Scanner in = new Scanner(new File("tst.in"));
        while (in.hasNext()){
            int next=in.nextInt();
            myTree.add(next);
        }
        //myTree.printRoLR();
        //myTree.remove(key);
        //myTree.findLevel();
        //System.out.println(myTree.rootWay(myTree.findH()));
        myTree.remove(myTree.answer(myTree.rootWay(myTree.getH())));
        myTree.printRoLR();
    }
}
public class Demo{
    public static void main(String args[])throws FileNotFoundException{
    	new Thread(null,()->{
    		try {
				new Solve().solve();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	},"",1024*1024*32).start();
    }
}


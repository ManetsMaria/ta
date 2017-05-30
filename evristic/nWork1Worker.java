import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Demo {
	static class Job implements Comparable<Job>{
		int start;
		int time;
		int numb;
		public Job(int s, int t, int n){
			start=s;
			time=t;
			numb=n;
		}
		public Job(){}
		@Override
		public int compareTo(Job o) {
			int result= start-o.start;
			if (result!=0)
				return result;
			return o.time-time;
		}
		@Override
		public String toString(){
			return "start: "+String.valueOf(start)+" time: "+String.valueOf(time);
		}
		
	}

	public static void main(String[] args)throws FileNotFoundException {
		Scanner in = new Scanner (new File("input.txt"));
		int n=in.nextInt();
		int c=1;
		List<Job> list=new ArrayList();
		for (int i=0; i<n; i++){
			Job job=new Job(in.nextInt(), in.nextInt(),c);
			list.add(job);
			c++;
		}
		//System.out.println(list.toString());
		Collections.sort(list);
		//System.out.println(list.toString());
		List<Job>answer=list;
		//System.out.println(answer.toString());
		List <Job> a=new ArrayList();
		/*for (int i=0; i<n; i++){
			int min=Integer.MAX_VALUE; int index=0;
			int memoryCount=0; int memoryTime=0;
			for (int j=0; j<=a.size(); j++){
				int count=0; int current=0;
				count=memoryCount;
				current=memoryTime;
				System.out.println("memoryCount: "+memoryCount);
				System.out.println("memoryTime: "+memoryTime);
				for (int k=j; k<a.size(); k++){
					if (current-a.get(k).start<0)
						current=a.get(k).start;
					count+=(current-a.get(k).start);
					current+=a.get(k).time;
					System.out.println("count: "+count);
					System.out.println("current: "+current);
				}
				if (memoryTime-a.get(j).start<0){
					memoryTime=a.get(j).start;
					System.out.println("if "+memoryTime);
				}
				memoryCount+=(memoryTime-a.get(j).start);
				memoryTime+=a.get(j).time;
				System.out.println(a.get(j));
				if (count<min){
					min=count;
					index=j;
				}
			}
			a.add(index, answer.get(i));
		}*/
		for (int i=0; i<n; i++){
			int index=0; int min=Integer.MAX_VALUE;
			int current=0;
			for (int j=0; j<=a.size(); j++){
				if (Math.abs(current-answer.get(i).start)<=min){
					min=Math.abs(current-answer.get(i).start);
					index=j;
				}
				if (j<a.size()){
					current+=a.get(j).time;
				}
			}
			a.add(index, answer.get(i));
			//System.out.println(a.toString());
		}
		PrintStream filewriter = new PrintStream(new File("output.txt"));
		String ans="";
		int count=0;
		int current=0;
		for (int i=0; i<n; i++){
			if (current-a.get(i).start<0){
				current=a.get(i).start;
			}
			count+=current-a.get(i).start;
			ans+=String.valueOf(a.get(i).numb)+" ";
			current+=a.get(i).time;
		}
		filewriter.println(String.valueOf(n)+" "+String.valueOf(count));
		filewriter.print(ans);
	}

}


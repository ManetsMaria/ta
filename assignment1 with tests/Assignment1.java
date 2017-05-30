
public class Assignment1 {
	
	private final static String right="Ok";
	private final static String wrong="not Ok";
	
	public static boolean isPermutation(String str1, String str2){ //O(n)
		if (str1.length()!=str2.length()){
			return false;
		}
		str1=str1.toLowerCase();
		str2=str2.toLowerCase();
		int[] symbolsCounter = new int[256];
		for (int i=0; i<str1.length(); i++){
			symbolsCounter[str1.charAt(i)]++;
		}
		for (int i=0; i<str2.length(); i++){
			char currentChar=str2.charAt(i);
			if (--symbolsCounter[currentChar]<0){
				return false;
			}
		}
		return true;
	}
	
public static Node kthElementFromLast(Node head, int k){ //O(n)
	if (k<0)
		return null;
	Node p1=head;
	Node p2=head;
	int flag=0;
	while(p1.next!=null && flag<k){
		p1=p1.next;
		flag++;
	}
	if (flag<k)
		return null;
	while(p1.next!=null){
		p2=p2.next;
		p1=p1.next;
	}
	return p2;
}

private static <T> String convertTestResultToString(T result, T expectedResult){
	if (result==null){
		if (expectedResult==null){
			return right;
		}
		else
			return wrong;
	}
	if (result.equals(expectedResult))
		return right;
	return wrong;
}

public static void testPermutation(){
	System.out.println("part 1 of assignment1 test:");
	System.out.println(convertTestResultToString(isPermutation("abba","baba"),true));	
	System.out.println(convertTestResultToString(isPermutation("ab","baba"),false));
	System.out.println(convertTestResultToString(isPermutation("Listen","Silent"),true));
	System.out.println(convertTestResultToString(isPermutation("Triangle","Integral"),true));
	System.out.println(convertTestResultToString(isPermutation("Apple","Pabble"),false));
	System.out.println(convertTestResultToString(isPermutation("3%yt","y%t3%"),false));
	System.out.println(convertTestResultToString(isPermutation("3%yt","3t%y"),true));
}

private static Node createListFromIntArray(int [] array){
	if (array.length==0)
		return null;
	Node head=new Node (array[0]);
	Node walker=head;
	for (int i=1; i<array.length; i++){
		Node next=new Node(array[i]);
		walker.next=next;
		walker=next;
	}
	return head;
}

public static void testKElementFromLast(){
	System.out.println("part 2 of assignment1 test:");
	int array1[]={0,1,2,3,4,5,6,7};
	int array2[]={4,5,6,7};
	Node head=createListFromIntArray(array1);
	Node checker=createListFromIntArray(array2);
	System.out.println(convertTestResultToString(kthElementFromLast(head,3),checker));
	int array3[]={7};
	checker=createListFromIntArray(array3);
	System.out.println(convertTestResultToString(kthElementFromLast(head,0),checker));
	System.out.println(convertTestResultToString(kthElementFromLast(head,-1),null));
	System.out.println(convertTestResultToString(kthElementFromLast(head,8),null));
	System.out.println(convertTestResultToString(kthElementFromLast(head,7),head));
	
}

	public static void main(String[] args) {
		testPermutation();
		testKElementFromLast();
	}

}

class Node <T>{
	T value;
	Node<T> next;
	public Node(T value){
		this.value=value;
		next=null;
	}
	@Override
	public boolean equals(Object other){
		boolean result;
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } 
	    else{
	        Node otherNode = (Node)other;
	        result = this.toString().equals(otherNode.toString());
	    } 
	    return result;
	}
	@Override
	public String toString(){
		if (next==null)
			return String.valueOf(value);
		return String.valueOf(value)+next.toString();
	}
}

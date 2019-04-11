package otherActions;

public class Actions {
	
	public static void test(int[] sequences) {
		for(int i=0; i<sequences.length-1; i++) {
			if(sequences[i]>sequences[i+1]) {
				System.out.println(i + " " + (i+1) + " " + sequences[i] + " " + sequences[i+1]);
				break;
			}
		}
	}
}

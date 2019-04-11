package sorting;


import java.util.Random;

public class QuicksortWithVariants implements SortingAlgorithm {
	
	private Order order;
	private Random rand = new Random();
	
	@Override
	public void sort(int[][] sequences) {
		
		order = new Ascending();
		
		for(int i=0; i<sequences.length; i++)
			quicksort(sequences[i], 0, sequences[i].length);
	}
	
	public void reverseSort(int[][] sequences) {
		
		order = new Descending();
		
		for(int i=0; i<sequences.length; i++)
			quicksort(sequences[i], 0, sequences[i].length);
	}
	
	public void halfSort(int[][] sequences) {
		
		order = new Ascending();
		
		for(int i=0; i<sequences.length; i++)
			quicksort(sequences[i], 0, sequences[i].length/2);
	}
	
	public void quicksort(int[] sequence, int begin, int end) {
		if(begin + 1 < end) {
			int middle = split(sequence, begin, end);
			quicksort(sequence, begin, middle);
			quicksort(sequence, middle+1, end);
		}
	}
	
	private int split(int[] sequence, int begin, int end) {
		
		swap(sequence, begin, begin + rand.nextInt(end-begin));
		int pivot = sequence[begin];
		
		int i = begin+1;
		int j = end-1;
		
		while(true) {
			
			while(i<=j && order.firstCondition(sequence[i], pivot))
				i++;
			
			while(order.secondCondition(sequence[j], pivot))
				j--;
			
			if(i >= j) {
				swap(sequence, begin, j);
				return j;
			}
			
			swap(sequence, i, j);
		}
	}
	
	private void swap(int[] sequence, int idx1, int idx2) {
		
		if (idx1 != idx2) {
			int tmp = sequence[idx1];
			sequence[idx1] = sequence[idx2];
			sequence[idx2] = tmp;
		}
	}
	
	private interface Order {
		boolean firstCondition(int number, int pivot);
		boolean secondCondition(int number, int pivot);
	}
	
	private class Ascending implements Order {
		public boolean firstCondition(int number, int pivot) {
			return number <= pivot;
		}

		public boolean secondCondition(int number, int pivot) {
			return number > pivot;
		}
	}
	
	private class Descending implements Order {
		public boolean firstCondition(int number, int pivot) {
			return number >= pivot;
		}
		
		public boolean secondCondition(int number, int pivot) {
			return number < pivot;
		}
	}
}

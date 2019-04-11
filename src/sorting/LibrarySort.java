package sorting;

import otherActions.Actions;

public class LibrarySort implements SortingAlgorithm {
	
	private int gap;
	private int none = -1;
	
	public LibrarySort(int gap) throws IllegalArgumentException {
		if(gap <= 0)
			throw new IllegalArgumentException("The gap should be at least 1");
		else
			this.gap = gap+1;
	}
	
	@Override
	public void sort(int[][] sequences) {
		long start;
		long end;
		
		for(int i=0; i<sequences.length; i++) {
			
			start = System.nanoTime();
			Librarysort(sequences[i]);
			end = System.nanoTime();
			
			System.out.println(end-start);
			
			Actions.test(sequences[i]);
		}
	}
	
	public void Librarysort(int[] sequence) {
		
		if(sequence.length <= 1) return;
		int[] gapped = new int[gap];
		gapped[0] = sequence[0];
		for(int i=1; i<gapped.length; i++)
			gapped[i] = none;
		
		gapped = startSorting(sequence, gapped);
		
		for(int i=0, j=0; i<sequence.length; j++)
			if(gapped[j] != none)
				sequence[i++] = gapped[j];
	}
	
	private int[] startSorting(int[] sequence, int[] gapped) {
		
		//wstawiamy co przejscie 2 razy wiecej elementow niz poprzednio
		for(int pos = 1, goal = 1; pos<sequence.length; goal *= 2) {
			for(int i=0; i<goal; i++) {
				
				int insPos = binary_search(gapped, sequence[pos]);
				
				insPos++; //wstawiamy za dany element
				
				if(insPos == gapped.length) {	//dodatkowy warunek kiedy wstawiamy na sam koniec,
					//lepiej zrobic osobno aby nie powtarzac najpierw szukania w prawo
					//i sprawdzania dodatkowych warunkow
					
					//musimy wstawic na koniec a nie za zakres tablicy
					insPos--;
					
					//szukamy w lewo
					int free = insPos - 1;
					while (gapped[free] != none)
						free--;
					
					//jakies miejsce na pewno sie znajdzie wiec przepisujemy bez sprawdzania
					for (; free < insPos; free++)
						gapped[free] = gapped[free + 1];
				}
				//jesli nie jest wolne to trzeba przesunac elementy, zaczynamy patrzac w prawo
				else if(gapped[insPos] != none) {
					int free = insPos + 1;
					while (free < gapped.length && gapped[free] != none)
						free++;
					
					//jesli w prawo juz jest pelne to szukamy w lewo
					if (free == gapped.length) {
						
						//przy przesuwaniu w lewo element takze musi znalezc sie o 1 wczesniej
						insPos--;
						
						//szukamy w lewo
						free = insPos - 1;
						while (gapped[free] != none)
							free--;
						
						//jakies miejsce na pewno sie znajdzie wiec przepisujemy bez sprawdzania
						for (; free < insPos; free++)
							gapped[free] = gapped[free + 1];
					}
					else  //przepisujemy w prawo jesli jest miejsce
						for (; free > insPos; free--)
							gapped[free] = gapped[free - 1];
				}
				gapped[insPos] = sequence[pos++];
				
				if(pos >= sequence.length) return gapped;
			}
			gapped = rebalance(gapped, sequence);
		}
		return gapped;
	}
	
	private int[] rebalance(int[] gapped, int[] sequence) {
		
		int[] rebalanced = new int[min(2*gapped.length, gap*sequence.length)];
		int eps = gap-1;
		
		//przepisywanie elementow do nowej tablicy
		for(int i = gapped.length - 1, j = rebalanced.length - 1; i >= 0; i--)
			if(gapped[i] != none) {
				rebalanced[j--] = gapped[i];
				
				for(int k=0; k<eps; k++)
					rebalanced[j--] = none;
			}
		
		return rebalanced;
	}
	
	private int binary_search(int[] gapped, int elem) {
		
		int bot = 0;
		int mid;
		int top = gapped.length - 1;
		
		//pierwsze wartosci
		while(gapped[top] == none)
			top--;
		while(gapped[bot] == none)
			bot++;
		
		while(bot <= top) {
			
			mid = (bot+top)/2;
			
			//jesli nie ma wartosci na mid to musimy znalezc
			if(gapped[mid] == none) {
				
				int tmp = mid + 1;
				
				//szukamy wartosci w prawo
				while (tmp < top && gapped[tmp] == none)
					tmp++;
				
				if (gapped[tmp] > elem || gapped[tmp] == none) {
					
					while (mid >= bot && gapped[mid] == none)
						mid--;
					
					if (gapped[mid] < elem)
						return mid;					//zwraca pozycje mniejsza niz miejsce do wstawienia
					
					top = mid - 1;
				} else
					bot = tmp + 1;
			}
			else if(gapped[mid] < elem)
				bot = mid + 1;
			else
				top = mid - 1;
		}
		
		//jesli zwracamy mid to pozycja jest o 1 mniejsza niz do wstawienia, funkcja powinna zachowywac sie tak samo
		//wiec robimy podobnie jesli funkcja zwraca top
		while(top >= 0 && gapped[top] == none)
			top--;
		
		return top;
	}
	
	private int min(int n1, int n2) {
		if(n1 < n2)
			return n1;
		else
			return n2;
	}
}
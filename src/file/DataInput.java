package file;

import sorting.QuicksortWithVariants;

import java.io.*;
import java.util.Random;

public class DataInput {
	
	private static final int sNumber = 100;
	private static final int[] lengths = {100000, 500000, 1000000, 2000000};
	private static final int sTypes = 4;
	private static final File sequencesFile = new File("./ciagi.dat");
	private static final Random rand = new Random();
	private static final QuicksortWithVariants sortAlg = new QuicksortWithVariants();
	
	public static void generate() {

		try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(sequencesFile))) {
			
			for(int lengthIndex = 0; lengthIndex < lengths.length; lengthIndex++)
				generateLength(lengths[lengthIndex], writer);
			
			writer.reset();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void generateLength(int length, ObjectOutputStream writer) {
		
		int bound = 5*length;
		
		for(int typeIndex = 0; typeIndex<sTypes; typeIndex++) {
			
			int[][] oneType = new int[sNumber][];
			
			for(int sequenceIndex = 0; sequenceIndex < sNumber; sequenceIndex++) {
				int[] sequence = new int[length];
				
				for(int i=0; i<length; i++)
					sequence[i] = rand.nextInt(bound);
				
				oneType[sequenceIndex] = sequence;
			}
			System.out.println(length);
			switch (typeIndex) {
				
				case 0:
					saveToFile(oneType, writer);
					break;
				case 1:
					sortAlg.halfSort(oneType);
					saveToFile(oneType, writer);
					break;
				case 2:
					sortAlg.sort(oneType);
					saveToFile(oneType, writer);
					break;
				case 3:
					sortAlg.reverseSort(oneType);
					saveToFile(oneType, writer);
					break;
			}
		}
	}
	
	public static void saveToFile(int[][] sequences, ObjectOutputStream writer) {
		
		try {
			writer.writeObject(sequences);
			writer.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[][] readFromFile(int index) {
		
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(sequencesFile))) {
			
			for(int i=0; i<index; i++)
				reader.readObject();
			
			return (int[][]) reader.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int[][] GenerateImmediate(int length, int type) {
		
		int[][] generated = new int[1][];
		generated[0] = new int [length];
		
		int bound = 5*length;
		
		for(int i=0; i<length; i++)
			generated[0][i] = rand.nextInt(bound);
		
		switch (type) {
			case 1:
				sortAlg.halfSort(generated);
				break;
			case 2:
				sortAlg.sort(generated);
				break;
			case 3:
				sortAlg.reverseSort(generated);
				break;
		}
		
		return generated;
	}
}

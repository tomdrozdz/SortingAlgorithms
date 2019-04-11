package ui;

import file.DataInput;
import main.Main;
import sorting.LibrarySort;
import sorting.ShellSort;
import sorting.Quicksort;
import java.util.Scanner;

public class UI {

	private final Main project;
	private int algorithm;
	private int sequence;
	private final int sequenceTypes;
	private final Scanner input;

	public UI(Main project) {

		this.project = project;
		algorithm = 0;
		sequence = 0;
		sequenceTypes = 4;
		input = new Scanner(System.in);
		startProgram();
	}

	private void startProgram() {

		boolean running = true;

		do {
			showOptions();

			switch(getInput()) {

				case 1:
					System.out.println("Podaj dlugosc ciagu: ");
					int[][] generated = DataInput.GenerateImmediate(getInput(), sequence%sequenceTypes);
					printSequence(generated[0]);
					project.getSortingAlgorithm().sort(generated);
					printSequence(generated[0]);
					break;

				case 2:
					project.getSortingAlgorithm().sort(DataInput.readFromFile(sequence));
					break;

				case 3:
					algorithmSettings();
					break;

				case 4:
					typeSettings();
					break;

				case 5:
					lengthSettings();
					break;

				case 6:
					running = false;
					input.close();
					break;

				case 999:
					DataInput.generate();
					break;

				default:
					System.out.println("Nie ma takiej opcji!");
			}

		} while(running);
	}

	private void showOptions() {

		System.out.print("\nObecne ustawienia: ");
		showSetting();
		System.out.println("1 - Funckja testujaca - podaj dlugosc i przeprowadz sortowanie w oparciu o ustawienia");
		System.out.println("2 - Przeprowadz sortowanie");
		System.out.println("3 - Wybierz algorytm sortujacy");
		System.out.println("4 - Wybierz typ sortowanego ciagu");
		System.out.println("5 - Wybierz dlugosc sortowanego ciagu");
		System.out.println("6 - Wyjdz");
		System.out.println("999 - Wygeneruj nowy zestaw danych (5GB)");
	}

	private void showSetting() {

		System.out.print("algorytm - ");
		switch(algorithm) {
			case 0: System.out.print("Quicksort"); break;
			case 1: System.out.print("Library-sort"); break;
			case 2: System.out.print("Shell-sort"); break;
		}

		System.out.print(", dlugosc - ");
		if(sequence<sequenceTypes)
			System.out.print("100 000");
		else if(sequence<2*sequenceTypes)
			System.out.print("500 000");
		else if(sequence<3*sequenceTypes)
			System.out.print("1 000 000");
		else
			System.out.print("2 000 000");

		System.out.print(", typ - ");
		if(sequence%sequenceTypes == 0)
			System.out.print("nieposortowany");
		else if(sequence%sequenceTypes == 1)
			System.out.print("posortowany w 50%");
		else if(sequence%sequenceTypes == 2)
			System.out.print("posortowany");
		else
			System.out.print("posortowany odwrotnie");

		System.out.println();
	}

	private void algorithmSettings() {

		System.out.println("\nWybierz algorytm:");
		System.out.println("1 - Quicksort");
		System.out.println("2 - Library-sort");
		System.out.println("3 - Shell-sort");

		switch(getInput()) {
			case 1:
				project.setSortingAlgorithm(new Quicksort());
				algorithm = 0;
				break;
			case 2:
				System.out.println("Podaj epsilon: ");
				project.setSortingAlgorithm(new LibrarySort(getInput()));
				algorithm = 1;
				break;
			case 3:
				System.out.println("Wybierz odstepy: ");
				System.out.println("1 - Oryginalna seria Shella (n/2)");
				System.out.println("2 - Seria Pratta ( (3^n - 1)/2 )");
				System.out.println("3 - Seria Ciury (uzyskana doswiadczalnie)");
				loop: while(true)
					switch(getInput()) {
						case 1:
							project.setSortingAlgorithm(new ShellSort(1));
							break loop;

						case 2:
							project.setSortingAlgorithm(new ShellSort(2));
							break loop;

						case 3:
							project.setSortingAlgorithm(new ShellSort(3));
							break loop;

						default: System.out.println("Nie ma takiej mozliwosci!");
					}

				algorithm = 2;
				break;
			default:
				System.out.println("Nie ma takiej opcji! Nie wprowadzono zmian");
		}
	}

	private void lengthSettings() {

		int type = sequence%sequenceTypes;

		System.out.println("\nWybierz dlugosc ciagu:");
		System.out.println("1 - 100 000");
		System.out.println("2 - 500 000");
		System.out.println("3 - 1 000 000");
		System.out.println("4 - 2 000 000");

		switch(getInput()) {
			case 1:
				sequence = type;
				break;
			case 2:
				sequence = sequenceTypes + type;
				break;
			case 3:
				sequence = 2*sequenceTypes + type;
				break;
			case 4:
				sequence = 3*sequenceTypes + type;
				break;
			default:
				System.out.println("Nie ma takiej opcji! Nie wprowadzono zmian");
		}
	}

	private void typeSettings() {

		int length = sequence - sequence%sequenceTypes;

		System.out.println("\nWybierz typ ciagu:");
		System.out.println("1 - nieposortowany");
		System.out.println("2 - posortowany w 50%");
		System.out.println("3 - posortowany");
		System.out.println("4 - posortowany odwrotnie");

		switch(getInput()) {
			case 1:
				sequence = length;
				break;
			case 2:
				sequence = length + 1;
				break;
			case 3:
				sequence = length + 2;
				break;
			case 4:
				sequence = length + 3;
				break;
			default:
				System.out.println("Nie ma takiej opcji! Nie wprowadzono zmian");
		}
	}

	private int getInput() {
		while(!input.hasNextInt()) {
			System.out.println("Wprowadz liczbe!");
			input.next();
		}
		return input.nextInt();
	}

	private void printSequence(int[] sequence) {
		for(int i=0; i<sequence.length; i++)
			System.out.print(sequence[i] + " ");
		System.out.println();
	}
}

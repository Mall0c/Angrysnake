import java.util.Scanner;

public class Menue {
	private int spielerzahl = 0;
	private int kiStufe1 = 0;
	private int kiStufe2 = 0;
	private String name1 = "Comp1";
	private String name2 = "Comp2";
	private int steine = 0;
	private Scanner scanner = new Scanner(System.in);
	
	public Menue() {
		this.aufrufMenue();
	}
	
	private void aufrufMenue() {
		do {
			System.out.print("Wie viele Spieler nehmen teil? (0, 1 oder 2 sind erlaubt!) ");
			spielerzahl = scanner.nextInt();
			System.out.println("");
			if (!checkValid(spielerzahl, 0, 2)) {
				System.out.println("Eingabe ungültig, bitte die Regeln beachten!");
			}
		} while (!checkValid(spielerzahl, 0, 2));
		
		switch (spielerzahl) {
		case 0:
			do {
				System.out.print("Bitte KI-Stufe für Computer1 eingeben");
				kiStufe1 = scanner.nextInt();
				System.out.println("");
				System.out.print("Bitte KI-Stufe für Computer2 eingeben");
				kiStufe2 = scanner.nextInt();
				System.out.println("");
			} while (!checkValid(kiStufe1, 0, 2) || !checkValid(kiStufe2, 0, 2));
			break;
		case 1:
			do {
				System.out.print("Bitte KI-Stufe für Computer1 eingeben");
				kiStufe1 = scanner.nextInt();
				System.out.println("");
				System.out.print("Wie soll der Spieler heißen? ");
				name1 = scanner.next();
				System.out.println("");
			} while (!checkValid(kiStufe1, 0, 2));
			break;
		case 2:
			System.out.print("Wie soll der Spieler 1 heißen? ");
			name1 = scanner.next();
			System.out.println("");
			System.out.print("Wie soll der Spieler 2 heißen? ");
			name2 = scanner.next();
			System.out.println("");
			break;
		}
		
		do {
			System.out.print("Wie viele Zufallsobjekte sollen zu Beginn herumliegen? ");
			steine = scanner.nextInt();
			System.out.println("");
		} while (!checkValid(steine, 0, 5));
		
	}
	
	public int getSpielerzahl() {
		return spielerzahl;
	}
	
	public int getKiStufe1() {
		return kiStufe1;
	}
	
	public int getKiStufe2() {
		return kiStufe2;
	}
	
	public String getName1() {
		return name1;
	}
	
	public String getName2() {
		return name2;
	}

	private boolean checkValid(int toCheck, int range1, int range2) {
		if (toCheck >= range1 && toCheck <= range2) {
			return true;
		} else {
			return false;
		}
	}

	public int getSpieleranzahl() {
		return this.spielerzahl;
	}
	public Scanner getScanner() {
		return this.scanner;
	}
	
	public int getSteine() {
		return this.steine;
	}
}


public class Spielmaster {

	private Spieler spieler1;
	private Spieler spieler2;
	private Spielfeld spielfeld;
	private boolean victor = false; // wird true gesetzt, wenn Spieler1 gewinnt
	public Spielmaster() {
		this.spiel();
	}

	public void spiel() {
		boolean gueltigerZug = false;
		Menu menue = new Menu();
		int anzahlSpieler = menue.getSpieleranzahl();
		spielfeld = new Spielfeld(menue.getStoneCount());
		switch (anzahlSpieler) {
		case 0:
			this.spieler1 = new Computerspieler(menue.getName1(), Spieler.types.COMP.ordinal(), menue.getKiLevel01(), spielfeld);
			this.spieler2 = new Computerspieler(menue.getName2(), Spieler.types.COMP.ordinal(), menue.getKiLevel02(), spielfeld);
			break;
		case 1:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.HUMAN.ordinal(), spielfeld);
			this.spieler2 = new Computerspieler(menue.getName2(), Spieler.types.COMP.ordinal(), menue.getKiLevel01(),spielfeld);
			break;
		// Ob die Eingabe korrekt ist, wird in Menue gepr�ft, also kann hier default
		// genommen werden
		default:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.HUMAN.ordinal(), spielfeld);
			this.spieler2 = new Spieler(menue.getName2(), Spieler.types.HUMAN.ordinal(), spielfeld);
			break;
		}
		String eingabe = "";
		// Spielablauf: Wird von Pascal programmiert
		while (true) {
			System.out.println(spielfeld.printField());
			if (!spielfeld._isRunning(this.spieler1)) {
				System.out.println(this.spieler2.getName() + " hat gewonnen.");
				break;
			}
			if (!spielfeld._isRunning(this.spieler2)) {
				System.out.println(this.spieler1.getName() + " hat gewonnen.");
				break;
			}
			System.out.println("Maulw�rfe: " + this.spieler1.getName() + ": " + this.spieler1.getAnzahlMaulwuerfe()
					+ " " + this.spieler2.getName() + ": " + this.spieler2.getAnzahlMaulwuerfe());
			System.out.print(this.spieler1.getName() + " ist am Zug: ");
			while (!gueltigerZug) {
				
				// eingabe = menue.getScanner().next();  Was soll das? Hatte keine getScanner in Menu implementiert, und getScaner.next schon gar nicht?
				
				eingabe = menue.getScanner();
				gueltigerZug = spieler1.zugMachen(eingabe);
			}
			gueltigerZug = false;
			System.out.println(spielfeld.printField());
			if (!spielfeld._isRunning(this.spieler1)) {
				System.out.println(this.spieler2.getName() + " hat gewonnen.");
				break;
			}
			if (!spielfeld._isRunning(this.spieler2)) {
				System.out.println(this.spieler1.getName() + " hat gewonnen.");
				this.victor = true;
				break;
			}
			System.out.println("Maulw�rfe: " + this.spieler1.getName() + ": " + this.spieler1.getAnzahlMaulwuerfe()
			+ " " + this.spieler2.getName() + ": " + this.spieler2.getAnzahlMaulwuerfe());
			System.out.print(this.spieler2.getName() + " ist am Zug: ");
			while (!gueltigerZug) {
				
				// eingabe = menue.getScanner().next();
				
				eingabe = menue.getScanner();
								gueltigerZug = spieler2.zugMachen(eingabe);
			}
			gueltigerZug = false;
		}
	}
	
	public boolean isRunning() {
		return this.spielfeld._isRunning(this.spieler1) || this.spielfeld._isRunning(this.spieler2);
	}
	
	public boolean whoWon() {
		return this.victor;
	}

	public Spieler getSpieler1() {
		return this.spieler1;
	}

	public Spieler getSpieler2() {
		return this.spieler2;
	}
	public String printBoard() {
		return spielfeld.printField();
	}

}

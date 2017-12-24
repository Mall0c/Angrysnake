public class Spielmaster {

	private Spieler spieler1;
	private Spieler spieler2;
	private Spielfeld spielfeld;
	private boolean victor = false; // wird true gesetzt, wenn Spieler1 gewinnt
	public Spielmaster() throws InterruptedException {
		this.spiel();
	}

	public void spiel() throws InterruptedException {
		boolean gueltigerZug = false;
		Menu menue = new Menu();
		int anzahlSpieler = menue.getPlayerCount();
		spielfeld = new Spielfeld(menue.getStoneCount());
		switch (anzahlSpieler) {
		case 0:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.COMP.ordinal(), spielfeld, true);
			this.spieler2 = new Spieler(menue.getName2(), Spieler.types.COMP.ordinal(), spielfeld, false);
			break;
		case 1:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.COMP.ordinal(), spielfeld, true);
			this.spieler2 = new Spieler(menue.getName2(), Spieler.types.HUMAN.ordinal(),spielfeld, false);
			break;
		// Ob die Eingabe korrekt ist, wird in Menue geprüft, also kann hier default
		// genommen werden
		default:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.HUMAN.ordinal(), spielfeld, true);
			this.spieler2 = new Spieler(menue.getName2(), Spieler.types.HUMAN.ordinal(), spielfeld, false);
			break;
		}
		String eingabe = "";
		boardVal k = new boardVal(0,3,6,3,0,0,false);
		TreeNode y = new TreeNode(k,6,this.spielfeld.getGameField());
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
			System.out.println("Maulwürfe: " + this.spieler1.getName() + ": " + this.spieler1.getAnzahlMaulwuerfe()
					+ " " + this.spieler2.getName() + ": " + this.spieler2.getAnzahlMaulwuerfe());
			System.out.print(this.spieler1.getName() + " ist am Zug: ");
			while (!gueltigerZug) {
				if(anzahlSpieler == 2) {
					eingabe = menue.getScanner().next();
					//eingabe = y.getMove();
				} else if(anzahlSpieler == 1) {
					if(spieler1.getTyp() == 0) { // Human
						eingabe = menue.getScanner().next();
					} else { // Computer
						eingabe = y.getMove();
					}
				} else {
					eingabe = y.getMove();
						Thread.sleep(2000);
				}
				gueltigerZug = spieler1.zugMachen(eingabe);
			}
			if(anzahlSpieler == 1 && spieler2.getTyp() == 1) {
				k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler1.getXLast(), spieler1.getYLast(), true);
				y = new TreeNode(k,6,this.spielfeld.getGameField());
			} else if(anzahlSpieler == 0) {
				k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler1.getXLast(), spieler1.getYLast(), true);
				y = new TreeNode(k,6,this.spielfeld.getGameField());
			}
			gueltigerZug = false;
			System.out.println(spielfeld.printField());
			if (!spielfeld._isRunning(this.spieler2)) {
				System.out.println(this.spieler1.getName() + " hat gewonnen.");
				break;
			}
			if (!spielfeld._isRunning(this.spieler1)) {
				System.out.println(this.spieler2.getName() + " hat gewonnen.");
				this.victor = true;
				break;
			}
			System.out.println("Maulwürfe: " + this.spieler1.getName() + ": " + this.spieler1.getAnzahlMaulwuerfe()
			+ " " + this.spieler2.getName() + ": " + this.spieler2.getAnzahlMaulwuerfe());
			System.out.print(this.spieler2.getName() + " ist am Zug: ");
			while (!gueltigerZug) {
				if(anzahlSpieler == 2) {
					eingabe = menue.getScanner().next();
				} else if(anzahlSpieler == 1) {
					if(spieler2.getTyp() == 0) { // Human
						eingabe = menue.getScanner().next();
					} else { // Computer
						eingabe = y.getMove();
					}
				} else {
					eingabe = y.getMove();
						Thread.sleep(2000);
				}
				gueltigerZug = spieler2.zugMachen(eingabe);
			}
			if(anzahlSpieler == 1 && spieler1.getTyp() == 1) {
				k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler2.getXLast(), spieler2.getYLast(), false);
				y = new TreeNode(k,6,this.spielfeld.getGameField());
			} else if(anzahlSpieler == 0) {
				k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler2.getXLast(), spieler2.getYLast(), false);
				y = new TreeNode(k,6,this.spielfeld.getGameField());
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

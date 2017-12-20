

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
		int anzahlSpieler = menue.getPlayerCount();
		spielfeld = new Spielfeld(menue.getStoneCount());
		switch (anzahlSpieler) {
		case 0:
			this.spieler1 = new Computerspieler(menue.getName1(), Spieler.types.COMP.ordinal(), menue.getAiLevel01(), spielfeld);
			this.spieler2 = new Computerspieler(menue.getName2(), Spieler.types.COMP.ordinal(), menue.getAiLevel02(), spielfeld);
			break;
		case 1:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.HUMAN.ordinal(), spielfeld);
			this.spieler2 = new Computerspieler(menue.getName2(), Spieler.types.COMP.ordinal(), menue.getAiLevel01(),spielfeld);
			break;
		// Ob die Eingabe korrekt ist, wird in Menue geprüft, also kann hier default
		// genommen werden
		default:
			this.spieler1 = new Spieler(menue.getName1(), Spieler.types.HUMAN.ordinal(), spielfeld);
			this.spieler2 = new Spieler(menue.getName2(), Spieler.types.HUMAN.ordinal(), spielfeld);
			break;
		}
		String eingabe = "";
		// ***********
		/*boardVal k = new boardVal(4,2,4,3,0,0,false);
		spieler1.setXKopf(4);
		spieler1.setYKopf(2);
		spieler2.setXKopf(4);
		spieler2.setYKopf(3);
		char[][] temp = spielfeld.getGameField();
		temp[0][3] = '#';
		temp[4][2] = 'O';
		temp[4][3] = 'X';
		temp[0][4] = '#';
		temp[1][4] = '#';
		temp[4][4] = '#';
		temp[5][4] = '#';
		temp[6][4] = '#';
		temp[2][4] = '#';
		temp[3][4] = '#';
		temp[6][3] = '#';
		temp[6][2] = '#';
		temp[6][1] = '#';
		temp[5][1] = '#';
		temp[4][1] = '#';
		temp[5][2] = '#';
		temp[5][3] = '#';*/
		boardVal k = new boardVal(0,3,6,3,0,0,false);
		@SuppressWarnings("unused")
		TreeNode y = new TreeNode(k,2,this.spielfeld.getGameField());
		// ***********
		while (true) {
			System.out.println(spielfeld.printField());
			// ***********
			//y.getMove();
			// ***********
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
				//eingabe = menue.getScanner().next();
				eingabe = y.getMove();
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
			System.out.println("Maulwürfe: " + this.spieler1.getName() + ": " + this.spieler1.getAnzahlMaulwuerfe()
			+ " " + this.spieler2.getName() + ": " + this.spieler2.getAnzahlMaulwuerfe());
			System.out.print(this.spieler2.getName() + " ist am Zug: ");
			while (!gueltigerZug) {
				eingabe = menue.getScanner().next();
				gueltigerZug = spieler2.zugMachen(eingabe);
			}
			gueltigerZug = false;
			// ***********
			k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler2.getXLast(), spieler2.getYLast(), false);
			y = new TreeNode(k,2,this.spielfeld.getGameField());
			// ***********
		}
		/*while(true) {
			System.out.println(spielfeld.printField());
			while (!gueltigerZug) {
				eingabe = menue.getScanner().next();
				gueltigerZug = spieler1.zugMachen(eingabe);
			}
			gueltigerZug = false;
			System.out.println(spielfeld.printField());
			while (!gueltigerZug) {
				eingabe = menue.getScanner().next();
				gueltigerZug = spieler2.zugMachen(eingabe);
			}
			gueltigerZug = false;
		}*/
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

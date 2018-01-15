
public class Spieler {

	public static int anzahlSpieler = 0; // to distinguish players	
	private int spielerNummer;
	private int anzahlMaulwuerfe = 2;
	private String name;
	private Spielfeld spielfeld;
	private int typ;
	private boolean start;
	private int xKopf;
	private int xLast;
	private int yKopf;
	private int yLast;
	private char kopfToken;
	private char schlangeToken;
	
	public Spieler(String name, int typ, Spielfeld spielfeld, boolean first, char kopfToken, char schlangeToken, int xKopf, int yKopf){
		this.name = name;
		this.typ = typ;
		this.start = first;
		this.spielfeld = spielfeld;
		this.kopfToken = kopfToken;
		this.schlangeToken = schlangeToken;
		this.xKopf = xKopf;
		this.yKopf = yKopf;;
	}
	
	public char getKopfToken() {
		return kopfToken;
	}

	public int getTyp() {
		return this.typ;
	}
	
	public boolean getStart() {
		return this.start;
	}

	public char getSchlangeToken() {
		return schlangeToken;
	}

	public enum types {
		HUMAN,
		COMP;
	}
	
	public String getSpieler(){
		return this.name;
	}
	
	public int getXKopf() {
		return this.xKopf;
	}
	
	public int getYKopf() {
		return this.yKopf;
	}
	
	public int getXLast() {
		return this.xLast;
	}
	
	public int getYLast() {
		return this.yLast;
	}
	
	public int getSpielerNummer() {
		return this.spielerNummer;
	}
	
	public void setXKopf(int value) {
		this.xKopf = value;
	}
	
	public void setYKopf(int value) {
		this.yKopf = value;
	}
	
	public void setXLast(int value) {
		this.xLast = value;
	}
	
	public void setYLast(int value) {
		this.yLast = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAnzahlMaulwuerfe() {
		return this.anzahlMaulwuerfe;
	}
	
	public void maulwurfReduzieren() {
		this.anzahlMaulwuerfe--;
	}
	public boolean isValidMove(String eingabe) {
		eingabe = eingabe.toUpperCase();
		if(eingabe.length() == 4) {
			int erstesZeichen = eingabe.charAt(0) - 65;
			int zweitesZeichen = eingabe.charAt(1) - 48 - 1;
			int drittesZeichen = eingabe.charAt(2) - 65;
			int viertesZeichen = eingabe.charAt(3) - 48 - 1;
			if(!this.imSpielbrett(erstesZeichen, zweitesZeichen) || !this.imSpielbrett(drittesZeichen, viertesZeichen)) {
				System.out.print("Eingegebene Koordinaten befinden sich nicht im Spielfeld. Erneut eingeben: ");
				return false;
			}
			// Erste Koordinate pr�fen
			if ((Math.abs(erstesZeichen - xKopf) == 1 || Math.abs(erstesZeichen - xKopf) == 6) && (zweitesZeichen - yKopf) == 0 || 
					(Math.abs(zweitesZeichen - yKopf) == 1 || Math.abs(zweitesZeichen - yKopf) == 6) && erstesZeichen - xKopf == 0) {
				// Zweite Koordinate pr�fen
				if((Math.abs(drittesZeichen - erstesZeichen) == 1 || Math.abs(drittesZeichen - erstesZeichen) == 6) && (viertesZeichen - zweitesZeichen) == 0 || 
						(Math.abs(viertesZeichen - zweitesZeichen) == 1 || Math.abs(viertesZeichen - zweitesZeichen) == 6) && drittesZeichen - erstesZeichen == 0) {
					if(this.spielfeld.getGameField()[erstesZeichen][zweitesZeichen] == ' ' && this.spielfeld.getGameField()[drittesZeichen][viertesZeichen] == ' ') {
						return true;
					} else {
						System.out.println("Koordinaten bereits belegt");
						return false;
					}
				}
				System.out.println("Zweite Koordinaten falsch");
				return false;
			}
			System.out.println("Erste Koordinaten falsch s");
			return false;
		} else if(eingabe.length() == 2) {
			int erstesZeichen = eingabe.charAt(0) - 65;
			int zweitesZeichen = eingabe.charAt(1) - 48 - 1;
			// Somit Maulwurf, da letztes Zeichen eine Zahl
			if(zweitesZeichen >= 0 && zweitesZeichen <= 6) {
				if(!this.imSpielbrett(erstesZeichen, zweitesZeichen)) {
					System.out.print("Eingegebene Koordinaten befinden sich nicht im Spielfeld. Bitte erneut eingeben: ");
					return false;
				}
				if(this.spielfeld.getGameField()[erstesZeichen][zweitesZeichen] != ' ') {
					System.out.print("Spielfeld bereits belegt. Erneut eingeben: ");
					return false;
				}
				if(this.getAnzahlMaulwuerfe() <= 0) {
					System.out.print("Keine Maulw�rfe mehr vorhanden. Erneut eingeben: ");
					return false;
				}
				return true;
			} else if((erstesZeichen+65 == 'W' || erstesZeichen+65 == 'A' || erstesZeichen+65 == 'S' || erstesZeichen+65 == 'D') && 
					(zweitesZeichen+49 == 'W' || zweitesZeichen+49 == 'A' || zweitesZeichen+49 == 'S' || zweitesZeichen+49 == 'D') ) {
				erstesZeichen += 65;
				zweitesZeichen += 49;
				int xKopfTemp = this.xKopf; // N�tig f�r das zweite Switch-case
				int yKopfTemp = this.yKopf; // da Schlange bewegt wird
				switch(erstesZeichen) {
					case 'W': if(this.spielfeld.getGameField()[this.xKopf][(this.yKopf+1+7)%7] == ' ') { yKopfTemp = (this.yKopf+1+7)%7; break; }return false;
					case 'A': if(this.spielfeld.getGameField()[(this.xKopf-1+7)%7][this.yKopf] == ' ') { xKopfTemp = (this.xKopf-1+7)%7; break; }return false;
					case 'S': if(this.spielfeld.getGameField()[this.xKopf][(this.yKopf-1+7)%7] == ' ') { yKopfTemp = (this.yKopf-1+7)%7; break; }return false;
					case 'D': if(this.spielfeld.getGameField()[(this.xKopf+1+7)%7][this.yKopf] == ' ') { xKopfTemp = (this.xKopf+1+7)%7; break; }return false;
				}
				switch(zweitesZeichen) {
					case 'W': if(this.spielfeld.getGameField()[xKopfTemp][(yKopfTemp+1+7)%7] == ' ') break; return false;
					case 'A': if(this.spielfeld.getGameField()[(xKopfTemp-1+7)%7][yKopfTemp] == ' ') break; return false;
					case 'S': if(this.spielfeld.getGameField()[xKopfTemp][(yKopfTemp-1+7)%7] == ' ') break; return false;
					case 'D': if(this.spielfeld.getGameField()[(xKopfTemp+1+7)%7][yKopfTemp] == ' ') break; return false;
				}
				return true;
			} else {
				System.out.print("Eingabe ung�ltig. Bitte erneut eingeben: ");
				return false;
			}
		} // Cheats: 
		else if(eingabe.length() > 5 && eingabe.substring(0, 5).equals("ODUS-")) {
			if(eingabe.substring(5, eingabe.length()).length() == 4) {
				String sub = eingabe.substring(5, eingabe.length());
				int erstesZeichen = sub.charAt(0);
				int zweitesZeichen = sub.charAt(1);
				int drittesZeichen = sub.charAt(2);
				int viertesZeichen = sub.charAt(3);
				// Vier Schritte mit WASD
				if((erstesZeichen == 'W' || erstesZeichen == 'A' || erstesZeichen == 'S' || erstesZeichen == 'D') && 
					(zweitesZeichen == 'W' || zweitesZeichen == 'A' || zweitesZeichen == 'S' || zweitesZeichen == 'D') && 
					(drittesZeichen == 'W' || drittesZeichen == 'A' || drittesZeichen == 'S' || drittesZeichen == 'D') && 
					(viertesZeichen == 'W' || viertesZeichen == 'A' || viertesZeichen == 'S' || viertesZeichen == 'D')) {
					int xKopfTemp1 = this.xKopf; 
					int yKopfTemp1 = this.yKopf;
					switch(erstesZeichen) {
						case 'W': if(this.spielfeld.getGameField()[this.xKopf][(this.yKopf+1+7)%7] == ' ') { yKopfTemp1 = (this.yKopf+1+7)%7; break; }return false;
						case 'A': if(this.spielfeld.getGameField()[(this.xKopf-1+7)%7][this.yKopf] == ' ') { xKopfTemp1 = (this.xKopf-1+7)%7; break; }return false;
						case 'S': if(this.spielfeld.getGameField()[this.xKopf][(this.yKopf-1+7)%7] == ' ') { yKopfTemp1 = (this.yKopf-1+7)%7; break; }return false;
						case 'D': if(this.spielfeld.getGameField()[(this.xKopf+1+7)%7][this.yKopf] == ' ') { xKopfTemp1 = (this.xKopf+1+7)%7; break; }return false;
					}
					switch(zweitesZeichen) {
						case 'W': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1+1+7)%7] == ' ') { yKopfTemp1 = (yKopfTemp1+1+7)%7; break; } return false;
						case 'A': if(this.spielfeld.getGameField()[(xKopfTemp1-1+7)%7][yKopfTemp1] == ' ') { xKopfTemp1 = (xKopfTemp1-1+7)%7; break; } return false;
						case 'S': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1-1+7)%7] == ' ') { yKopfTemp1 = (yKopfTemp1-1+7)%7; break; } return false;
						case 'D': if(this.spielfeld.getGameField()[(xKopfTemp1+1+7)%7][yKopfTemp1] == ' ') { xKopfTemp1 = (xKopfTemp1+1+7)%7; break; } return false;
					}	
					switch(drittesZeichen) {
						case 'W': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1+1+7)%7] == ' ') { yKopfTemp1 = (yKopfTemp1+1+7)%7; break; } return false;
						case 'A': if(this.spielfeld.getGameField()[(xKopfTemp1-1+7)%7][yKopfTemp1] == ' ') { xKopfTemp1 = (xKopfTemp1-1+7)%7; break; } return false;
						case 'S': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1-1+7)%7] == ' ') { yKopfTemp1 = (yKopfTemp1-1+7)%7; break; } return false;
						case 'D': if(this.spielfeld.getGameField()[(xKopfTemp1+1+7)%7][yKopfTemp1] == ' ') { xKopfTemp1 = (xKopfTemp1+1+7)%7; break; } return false;
					}
					switch(viertesZeichen) {
						case 'W': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1+1+7)%7] == ' ') break; return false;
						case 'A': if(this.spielfeld.getGameField()[(xKopfTemp1-1+7)%7][yKopfTemp1] == ' ') break; return false;
						case 'S': if(this.spielfeld.getGameField()[xKopfTemp1][(yKopfTemp1-1+7)%7] == ' ') break; return false;
						case 'D': if(this.spielfeld.getGameField()[(xKopfTemp1+1+7)%7][yKopfTemp1] == ' ') break; return false;
					}
					return true;
				} else { // Zwei Schritte mit A5B5
					return this.isValidMove(sub);
				}
			} else if(eingabe.substring(5, eingabe.length()).length() == 2) {
				String sub = eingabe.substring(5, eingabe.length());
				int erstesZeichen = sub.charAt(0);
				int zweitesZeichen = sub.charAt(1);
				// Maulwurf platzieren
				if(zweitesZeichen >= 0 && zweitesZeichen <= 6) {
					if(this.anzahlMaulwuerfe == 0) {
						this.anzahlMaulwuerfe++;
					}
				}
				return this.isValidMove(sub);
			} else if(eingabe.substring(5, eingabe.length()).length() == 8) {
				String sub = eingabe.substring(5, eingabe.length());
				int erstesZeichen = sub.charAt(0) - 65;
				int zweitesZeichen = sub.charAt(1) - 48 - 1;
				int drittesZeichen = sub.charAt(2) - 65;
				int viertesZeichen = sub.charAt(3) - 48 - 1;
				int fuenftesZeichen = sub.charAt(4) - 65;
				int sechstesZeichen = sub.charAt(5) - 48 - 1;
				int siebtesZeichen = sub.charAt(6) - 65;
				int achtesZeichen = sub.charAt(7) - 48 - 1;
				if(!this.imSpielbrett(erstesZeichen, zweitesZeichen) || !this.imSpielbrett(drittesZeichen, viertesZeichen) || 
						!this.imSpielbrett(fuenftesZeichen, sechstesZeichen) || !this.imSpielbrett(siebtesZeichen, achtesZeichen)) {
					System.out.print("Eingegebene Koordinaten befinden sich nicht im Spielfeld. Erneut eingeben: ");
					return false;
				}
				// Erste Koordinate pr�fen
				if ((Math.abs(erstesZeichen - xKopf) == 1 || Math.abs(erstesZeichen - xKopf) == 6) && (zweitesZeichen - yKopf) == 0 || 
						(Math.abs(zweitesZeichen - yKopf) == 1 || Math.abs(zweitesZeichen - yKopf) == 6) && erstesZeichen - xKopf == 0) {
					// Zweite Koordinate pr�fen
					if((Math.abs(drittesZeichen - erstesZeichen) == 1 || Math.abs(drittesZeichen - erstesZeichen) == 6) && (viertesZeichen - zweitesZeichen) == 0 || 
							(Math.abs(viertesZeichen - zweitesZeichen) == 1 || Math.abs(viertesZeichen - zweitesZeichen) == 6) && drittesZeichen - erstesZeichen == 0) {
						// Dritte Koordinate pr�fen
						if((Math.abs(fuenftesZeichen - drittesZeichen) == 1 || Math.abs(fuenftesZeichen - drittesZeichen) == 6) && (sechstesZeichen - viertesZeichen) == 0 || 
								(Math.abs(sechstesZeichen - viertesZeichen) == 1 || Math.abs(sechstesZeichen - viertesZeichen) == 6) && fuenftesZeichen - drittesZeichen == 0) {
							// Vierte Koordinate pr�fen
							if((Math.abs(siebtesZeichen - fuenftesZeichen) == 1 || Math.abs(siebtesZeichen - fuenftesZeichen) == 6) && (achtesZeichen - sechstesZeichen) == 0 || 
									(Math.abs(achtesZeichen - sechstesZeichen) == 1 || Math.abs(achtesZeichen - sechstesZeichen) == 6) && siebtesZeichen - fuenftesZeichen == 0) {
						if(this.spielfeld.getGameField()[erstesZeichen][zweitesZeichen] == ' ' && this.spielfeld.getGameField()[drittesZeichen][viertesZeichen] == ' '
								&& this.spielfeld.getGameField()[fuenftesZeichen][sechstesZeichen] == ' ' && this.spielfeld.getGameField()[siebtesZeichen][achtesZeichen] == ' ' ) {
							return true;
						} else {
							System.out.println("Koordinaten bereits belegt");
							return false;
						} 
						}
							System.out.println("Vierte Koordinaten falsch");
							return false;
						}
						System.out.println("Dritte Koordinaten falsch");
						return false;
					}
					System.out.println("Zweite Koordinaten falsch");
					return false;
				}
				System.out.println("Erste Koordinaten falsch");
				return false;
			}
		}
		System.out.print("Eingabe ung�ltig. Bitte erneut eingeben: ");
		return false;
	}
	public boolean zugMachen(String eingabe) {
		eingabe = eingabe.toUpperCase();
		if(!this.isValidMove(eingabe)) {
			return false;
		}
		if(eingabe.length() == 4) {
			int erstesZeichen = eingabe.charAt(0) - 65;
			int zweitesZeichen = eingabe.charAt(1) - 48 - 1;
			int drittesZeichen = eingabe.charAt(2) - 65;
			int viertesZeichen = eingabe.charAt(3) - 48 - 1;
			this.spielfeld.setField(this.xKopf, this.yKopf, this.schlangeToken);
			this.spielfeld.setField(erstesZeichen, zweitesZeichen, this.schlangeToken);
			this.spielfeld.setField(drittesZeichen, viertesZeichen, this.kopfToken);
			this.xLast = erstesZeichen;
			this.yLast = zweitesZeichen;
			this.xKopf = drittesZeichen;
			this.yKopf = viertesZeichen;
			return true;
		} else if(eingabe.length() == 2) {
			int erstesZeichen = eingabe.charAt(0) - 65;
			int zweitesZeichen = eingabe.charAt(1) - 48 - 1;
			if(zweitesZeichen >= 0 && zweitesZeichen <= 6) {
				this.spielfeld.setField(erstesZeichen, zweitesZeichen, '#');
				this.maulwurfReduzieren();
			} else if((erstesZeichen+65 == 'W' || erstesZeichen+65 == 'A' || erstesZeichen+65 == 'S' || erstesZeichen+65 == 'D') && 
					(zweitesZeichen+49 == 'W' || zweitesZeichen+49 == 'A' || zweitesZeichen+49 == 'S' || zweitesZeichen+49 == 'D') ) {
				this.spielfeld.setField(this.xKopf, this.yKopf, this.schlangeToken); 
				erstesZeichen += 65;
				zweitesZeichen += 49;
				switch(erstesZeichen) {
				case 'W': 
					this.spielfeld.setField(this.xKopf, (this.yKopf+1+7)%7, this.schlangeToken);
					this.yKopf = (this.yKopf+1+7)%7;
					break;
				case 'A': 
					this.spielfeld.setField((this.xKopf-1+7)%7, this.yKopf, this.schlangeToken);
					this.xKopf = (this.xKopf-1+7)%7;
					break;
				case 'S': 
					this.spielfeld.setField(this.xKopf, (this.yKopf-1+7)%7, this.schlangeToken);
					this.yKopf = (this.yKopf-1+7)%7;
					break;
				case 'D': 
					this.spielfeld.setField((this.xKopf+1+7)%7, this.yKopf, this.schlangeToken);
					this.xKopf = (this.xKopf+1+7)%7;
					break;
				}
				this.xLast = this.xKopf;
				this.yLast = this.yKopf;
				switch(zweitesZeichen) {
				case 'W':
					this.spielfeld.setField(this.xKopf, (this.yKopf+1+7)%7, this.kopfToken);
					this.yKopf = (this.yKopf+1+7)%7;
					break;
				case 'A': 
					this.spielfeld.setField((this.xKopf-1+7)%7, this.yKopf, this.kopfToken);
					this.xKopf = (this.xKopf-1+7)%7;
					break;
				case 'S': 
					this.spielfeld.setField(this.xKopf, (this.yKopf-1+7)%7, this.kopfToken);
					this.yKopf = (this.yKopf-1+7)%7;
					break;
				case 'D': 
					this.spielfeld.setField((this.xKopf+1+7)%7, this.yKopf, this.kopfToken);
					this.xKopf = (this.xKopf+1+7)%7;
					break;
				}
			}
			return true;
		} // Cheats 
		else if(eingabe.substring(0, 5).equals("ODUS-")) {
			String sub = eingabe.substring(5, eingabe.length());
			if(sub.length() == 4) {
				int erstesZeichen = sub.charAt(0);
				int zweitesZeichen = sub.charAt(1);
				int drittesZeichen = sub.charAt(2);
				int viertesZeichen = sub.charAt(3);
				if(zweitesZeichen-49 >= 0 && zweitesZeichen-49 <= 6) {
					return this.zugMachen(sub);
				} else {
					return this.zugMachen(sub.substring(0, 2)) && this.zugMachen(sub.substring(2,4));
				} // Maulwurf oder Bewegung mit ww:
			} else if(sub.length() == 2) {
					return this.zugMachen(sub);
			} else { // Bewegung mit A5B5C5D5
				int erstesZeichen = sub.charAt(0) - 65;
				int zweitesZeichen = sub.charAt(1) - 48 - 1;
				int drittesZeichen = sub.charAt(2) - 65;
				int viertesZeichen = sub.charAt(3) - 48 - 1;
				int fuenftesZeichen = sub.charAt(4) - 65;
				int sechstesZeichen = sub.charAt(5) - 48 - 1;
				int siebtesZeichen = sub.charAt(6) - 65;
				int achtesZeichen = sub.charAt(7) - 48 - 1;
				this.spielfeld.setField(this.xKopf, this.yKopf, this.schlangeToken);
				this.spielfeld.setField(erstesZeichen, zweitesZeichen, this.schlangeToken);
				this.spielfeld.setField(drittesZeichen, viertesZeichen, this.schlangeToken);
				this.spielfeld.setField(fuenftesZeichen, sechstesZeichen, this.schlangeToken);
				this.spielfeld.setField(siebtesZeichen, achtesZeichen, this.kopfToken);
				this.xLast = fuenftesZeichen;
				this.yLast = sechstesZeichen;
				this.xKopf = siebtesZeichen;
				this.yKopf = achtesZeichen;
				return true;
			}
		}
		return false;
	}
	private boolean imSpielbrett(int x, int y) {
		if(x >= 0 && x <= 6 && y >= 0 && y <= 6) {
			return true;
		}
		return false;
	}
}
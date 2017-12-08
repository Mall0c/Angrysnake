
public class Spieler {

	private static int anzahlSpieler = 0; // to distinguish players	
	private int spielerNummer;
	private int anzahlMaulwuerfe = 2;
	private String name;
	private Spielfeld spielfeld;
	private int typ;
	private int xKopf;
	private int yKopf;
	private char kopfToken;
	private char schlangeToken;
	
	public Spieler(String name, int typ, Spielfeld spielfeld){
		this.name = name;
		this.typ = typ;
		this.spielfeld = spielfeld;
		this.spielerNummer = ++anzahlSpieler;
		if(this.spielerNummer == 1) {
			this.kopfToken = 'O';
			this.schlangeToken = '+';
			this.xKopf = 0;
			this.yKopf = 3;
		} else {
			this.kopfToken = 'X';
			this.schlangeToken = '-';
			this.xKopf = 6;
			this.yKopf = 3;
		}
	}
	
	public char getKopfToken() {
		return kopfToken;
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
	
	public int getSpielerNummer() {
		return this.spielerNummer;
	}
	
	public void setXKopf(int value) {
		this.xKopf = value;
	}
	
	public void setYKopf(int value) {
		this.yKopf = value;
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
			// Erste Koordinate prüfen
			if ((Math.abs(erstesZeichen - xKopf) == 1 || Math.abs(erstesZeichen - xKopf) == 6) && (zweitesZeichen - yKopf) == 0 || 
					(Math.abs(zweitesZeichen - yKopf) == 1 || Math.abs(zweitesZeichen - yKopf) == 6) && erstesZeichen - xKopf == 0) {
				// Zweite Koordinate prüfen
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
			System.out.println("Erste Koordinaten falsch");
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
					System.out.println("Spielfeld bereits belegt.");
					return false;
				}
				return true;
			} else if((erstesZeichen+65 == 'W' || erstesZeichen+65 == 'A' || erstesZeichen+65 == 'S' || erstesZeichen+65 == 'D') && 
					(zweitesZeichen+49 == 'W' || zweitesZeichen+49 == 'A' || zweitesZeichen+49 == 'S' || zweitesZeichen+49 == 'D') ) {
				erstesZeichen += 65;
				zweitesZeichen += 49;
				int xKopfTemp = this.xKopf; // Nötig für das zweite Switch-case
				int yKopfTemp = this.yKopf; // da Schlange bewegt wird
				if(erstesZeichen == 'A' && zweitesZeichen == 'S') {
					System.out.println();
				}
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
				System.out.print("Eingabe ungültig. Bitte erneut eingeben: ");
				return false;
			}
		}
		System.out.print("Eingabe ungültig. Bitte erneut eingeben: ");
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

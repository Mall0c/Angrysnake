public class Teamwaldi {
 
    private boolean start;
    private Spielfeld spielfeld;
    private Spieler spieler1;
    private Spieler spieler2;
    private boolean victor;
    public Teamwaldi() {
        this.spielfeld = new Spielfeld(0);
    }
    public boolean isRunning() {
        if (!spielfeld._isRunning(this.spieler2)) {
            System.out.println(this.spieler1.getName() + " hat gewonnen.");
            this.victor = true;
            return false;
        }
        if (!spielfeld._isRunning(this.spieler1)) {
            System.out.println(this.spieler2.getName() + " hat gewonnen.");
            this.victor = false;
            return false;
        }
        return true;
    }
     
    public boolean whoWon() {
        return this.victor;
    }
     
    public boolean isValidMove(String s) {
        if (start) {
            return this.spieler2.isValidMove(s);
        } else {
            return this.spieler1.isValidMove(s);
        }
    }
     
    public String yourMove() {
        if(start) {
            boardVal k = new boardVal(spieler1.getXKopf(), spieler1.getYKopf(), spieler2.getXKopf(), spieler2.getYKopf(), spieler2.getXLast(), spieler2.getYLast(), false);
            TreeNode y = new TreeNode(k,6,this.spielfeld.getGameField());
            String move = y.getMove();
            spieler1.zugMachen(move);
            return move;
        } else {
            boardVal k = new boardVal(spieler2.getXKopf(), spieler2.getYKopf(), spieler1.getXKopf(), spieler1.getYKopf(), spieler1.getXLast(), spieler1.getYLast(), true);
            TreeNode y = new TreeNode(k,6,this.spielfeld.getGameField());
            String move = y.getMove();
            spieler1.zugMachen(move);
            return move;        
        }
    }
     
    public void myMove(String s) {
        if(start) {
            this.spieler2.zugMachen(s);
        } else {
            this.spieler2.zugMachen(s);
        }
    }
     
    public void printBoard() {
        spielfeld.printField();
    }
     
    public void setStart(boolean setStart) {
        this.start = setStart;
        if(setStart) {
            this.spieler1 = new Spieler("Angrynerds", Spieler.types.COMP.ordinal(), spielfeld, true,'O', '+', 0, 3);
            this.spieler2 = new Spieler("Teamwaldi", Spieler.types.COMP.ordinal(), spielfeld, false, 'X', '-', 6, 3);
        } else {
            this.spieler1 = new Spieler("Angrynerds", Spieler.types.COMP.ordinal(), spielfeld, false, 'X', '-', 6, 3);
            this.spieler2 = new Spieler("Teamwaldi", Spieler.types.COMP.ordinal(), spielfeld, true, 'O', '+', 0, 3);
        }
    }
     
}
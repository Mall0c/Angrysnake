public class Tournament {
    public static void main(String[] args) {
    	final int aiDepth = 9;
    	int pointsEnemy = 0;
    	int pointsWaldi = 0;
    	
        for (int i = 0; i < 10; i++) {
            Spielfeld spielfeld = new Spielfeld(0);
            Spieler enemy_spieler, teamwaldi_spieler;       	
            if(i % 2 == 0) {
	            enemy_spieler = new Spieler("Angrynerds", Spieler.types.HUMAN.ordinal(), spielfeld,
	                    true, 'O', '+', 0, 3);
	            teamwaldi_spieler = new Spieler("Teamwaldi", Spieler.types.COMP.ordinal(), spielfeld,
	                    false, 'X', '-', 6, 3);
            } else {
	            enemy_spieler = new Spieler("Angrynerds", Spieler.types.HUMAN.ordinal(), spielfeld,
	                    false, 'X', '-', 6, 3);
	            teamwaldi_spieler = new Spieler("Teamwaldi", Spieler.types.COMP.ordinal(), spielfeld,
	                    true, 'O', '+', 0, 3);
            }
            AngryNerds enemy_wrapper = new AngryNerds();
            // enemy_wrapper.setStart((i%2==0)?true:false);
            String ourMove = "";
            String enemyMove = "";
            while (true) {
                if (teamwaldi_spieler.getStart()) {
                	// boardVal & TreeNode erstellen, Zug machen + übergeben an den Wrapper
            		boardVal k = new boardVal(teamwaldi_spieler.getXKopf(),teamwaldi_spieler.getYKopf(),enemy_spieler.getXKopf(),
            				enemy_spieler.getYKopf(),enemy_spieler.getXLast(),enemy_spieler.getYLast(),false , teamwaldi_spieler.getAnzahlMaulwuerfe(), enemy_spieler.getAnzahlMaulwuerfe());
            		TreeNode y = new TreeNode(k,aiDepth,spielfeld.getGameField());
            		ourMove = y.getMove();
            		teamwaldi_spieler.zugMachen(ourMove);
            		enemy_wrapper.myMove(ourMove);
        			System.out.println(spielfeld.printField());
        			// Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				pointsEnemy += 2;
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				pointsWaldi += 2;
        				break;
        			}
        			System.out.println("Maulw�rfe: " + teamwaldi_spieler.getName() + ": " + teamwaldi_spieler.getAnzahlMaulwuerfe()
					+ " " + enemy_spieler.getName() + ": " + enemy_spieler.getAnzahlMaulwuerfe());
                    // Wrapper nach yourMove fragen und eintragen
        			enemyMove = enemy_wrapper.yourMove();
        			enemy_spieler.zugMachen(enemyMove);
        			System.out.println(spielfeld.printField());
                    // Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				pointsEnemy += 2;
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				pointsWaldi += 2;
        				break;
        			}
        			System.out.println("Maulw�rfe: " + teamwaldi_spieler.getName() + ": " + teamwaldi_spieler.getAnzahlMaulwuerfe()
					+ " " + enemy_spieler.getName() + ": " + enemy_spieler.getAnzahlMaulwuerfe());
                } else {
                    // Wrapper nach yourMove fragen und eintragen              		
        			enemyMove = enemy_wrapper.yourMove();
        			enemy_spieler.zugMachen(enemyMove);
        			System.out.println(spielfeld.printField());
        			System.out.println("Maulw�rfe: " + teamwaldi_spieler.getName() + ": " + teamwaldi_spieler.getAnzahlMaulwuerfe()
					+ " " + enemy_spieler.getName() + ": " + enemy_spieler.getAnzahlMaulwuerfe());
                    // Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				pointsEnemy += 2;
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				pointsWaldi += 2;
        				break;
        			}
                	// boardVal & TreeNode erstellen, Zug machen + übergeben an den Wrapper
            		boardVal k = new boardVal(enemy_spieler.getXKopf(),enemy_spieler.getYKopf(),teamwaldi_spieler.getXKopf(),
            				teamwaldi_spieler.getYKopf(),teamwaldi_spieler.getXLast(),teamwaldi_spieler.getYLast(),true, enemy_spieler.getAnzahlMaulwuerfe(), teamwaldi_spieler.getAnzahlMaulwuerfe());
            		TreeNode y = new TreeNode(k,aiDepth,spielfeld.getGameField());
            		ourMove = y.getMove();
            		enemy_wrapper.myMove(ourMove);
            		teamwaldi_spieler.zugMachen(ourMove);
        			System.out.println(spielfeld.printField());
        			// Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				pointsEnemy += 2;
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				pointsWaldi += 2;
        				break;
        			}
        			System.out.println("Maulw�rfe: " + teamwaldi_spieler.getName() + ": " + teamwaldi_spieler.getAnzahlMaulwuerfe()
					+ " " + enemy_spieler.getName() + ": " + enemy_spieler.getAnzahlMaulwuerfe());
                }
            }
            System.out.println("");
            System.out.println("Points Enemy: " + pointsEnemy);
            System.out.println("Points Waldi: " + pointsWaldi);
        }
    }
}
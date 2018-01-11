public class Tournament {
    public static void main(String[] args) {
        for (int i = 1; i < 2; i++) {
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
            Teamwaldi enemy_wrapper = new Teamwaldi();
            enemy_wrapper.setStart((i%2==0)?true:false);
            String ourMove = "";
            String enemyMove = "";
            while (true) {
                if (teamwaldi_spieler.getStart()) {
                	// boardVal & TreeNode erstellen, Zug machen + übergeben an den Wrapper
            		boardVal k = new boardVal(teamwaldi_spieler.getXKopf(),teamwaldi_spieler.getYKopf(),enemy_spieler.getXKopf(),
            				enemy_spieler.getYKopf(),enemy_spieler.getXLast(),enemy_spieler.getYLast(),false);
            		TreeNode y = new TreeNode(k,6,spielfeld.getGameField());
            		ourMove = y.getMove();
            		teamwaldi_spieler.zugMachen(ourMove);
            		enemy_wrapper.myMove(ourMove);
        			System.out.println(spielfeld.printField());
        			// Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				break;
        			}
                    // Wrapper nach yourMove fragen und eintragen
        			enemyMove = enemy_wrapper.yourMove();
        			enemy_spieler.zugMachen(enemyMove);
        			System.out.println(spielfeld.printField());
                    // Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				break;
        			}
                } else {
                    // Wrapper nach yourMove fragen und eintragen              		
        			enemyMove = enemy_wrapper.yourMove();
        			enemy_spieler.zugMachen(enemyMove);
        			System.out.println(spielfeld.printField());
                    // Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				break;
        			}
                	// boardVal & TreeNode erstellen, Zug machen + übergeben an den Wrapper
            		boardVal k = new boardVal(enemy_spieler.getXKopf(),enemy_spieler.getYKopf(),teamwaldi_spieler.getXKopf(),
            				teamwaldi_spieler.getYKopf(),teamwaldi_spieler.getXLast(),teamwaldi_spieler.getYLast(),true);
            		TreeNode y = new TreeNode(k,6,spielfeld.getGameField());
            		ourMove = y.getMove();
            		enemy_wrapper.myMove(ourMove);
            		teamwaldi_spieler.zugMachen(ourMove);
        			System.out.println(spielfeld.printField());
        			// Prüfen isRunning, else break
        			if (!spielfeld._isRunning(teamwaldi_spieler)) {
        				System.out.println(enemy_spieler.getName() + " hat gewonnen.");
        				break;
        			}
        			if (!spielfeld._isRunning(enemy_spieler)) {
        				System.out.println(teamwaldi_spieler.getName() + " hat gewonnen.");
        				break;
        			}
                }
            }
        }
    }
}
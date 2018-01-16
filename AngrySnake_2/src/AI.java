

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class AI {

    private static int maxDepth;
    private static String bestMove;

    static String getMove(boolean player1, Gameboard board, int difficulty) {

    		 bestMove = null;
    		 char[][] matrixCopy = board.copyMatrix();
    		 int player1HeadColumnCopy = board.player1.getPlayerHeadColumn();
    		 int player1HeadRowCopy = board.player1.getPlayerHeadRow();
    		 int player1MaulfwurfCopy = board.player1.getMaulwurf();
   
    		 int player2HeadColumnCopy = board.player2.getPlayerHeadColumn();
    		 int player2HeadRowCopy = board.player2.getPlayerHeadRow();
    		 int player2MaulfwurfCopy = board.player2.getMaulwurf();
    		   
   	 if (difficulty == 1)
   		 return getRandomMove(player1, board);

   	 else if (difficulty == 2)
   		 maxDepth = 4;

   	 else {
   		 maxDepth = getAdaptiveDepth(board);
   	//	 System.out.println("Predicting " + maxDepth + " moves ahead");
   	 }
   	 
   	 if (maxDepth < 1) {
   		 throw new IllegalArgumentException("Maximum depth must be greater than 0.");
   	 }

   	 else {
   		 /*List<String> availableMovesList = new ArrayList<>(AI.getAvailableMoves(!player1, board));
      	 int availableMovesNumber = availableMovesList.size();
     
      	 int freeFields = getAvailableMoleMoves(player1, board).size();
      	 if(difficulty == 3 & availableMovesNumber <= 6 && freeFields<26) {
      		 System.out.println("Moles are taken in consideration");
   		  System.out.print("Mole moves relevant for AI: ");
   		 
   		  for(String move : getAvailableRelevantMoleMoves(player1, board))
   			  System.out.print(move + " ");
   		  System.out.println();
      	 }
      	 else
      		 System.out.println("Moles are not taken in cosideration" );*/
 
   		  alphaBeta(player1, board, -100000, 100000,0);
   		  board.board = matrixCopy;
   		  board.player1.setPlayerHeadColumn(player1HeadColumnCopy);
   		  board.player1.setPlayerHeadRow(player1HeadRowCopy);
   		  board.player2.setPlayerHeadColumn(player2HeadColumnCopy);
   		  board.player2.setPlayerHeadRow(player2HeadRowCopy);
   		  board.player1.setMaulwurf(player1MaulfwurfCopy);
   		  board.player2.setMaulwurf(player2MaulfwurfCopy);
   		  
   		  if(bestMove == null)
   			  if(player1 == true && board.player1.getPlayerMoleCount()!=0  
   			  || player1 == false && board.player2.getPlayerMoleCount()!=0)
   					  return getRandomMove(player1,board);
   		  
   		  
   		  return bestMove;
   	 }
   		 
   	 }
    

    private static String getRandomMove(boolean player1, Gameboard board) {

   	 List<String> possibleMoves = AI.getAvailableMoves(player1, board);
   	 Random random = new Random();
   	 int randomIndex;
   	 if (player1 == true && board.player1.getPlayerMoleCount() > 0 
   			 || player1 == false && board.player2.getPlayerMoleCount() > 0)
   		 randomIndex = random.nextInt(possibleMoves.size() + 1);
   	 else
   		 randomIndex = random.nextInt(possibleMoves.size());

   	 if (randomIndex == possibleMoves.size()) {
   		 List<String> possibleMoleMoves = AI.getAvailableMoleMoves(player1, board);
   		 randomIndex = random.nextInt(possibleMoleMoves.size());
   		 return possibleMoleMoves.get(randomIndex);
   	 }
   	 return possibleMoves.get(randomIndex);
    }

    private static int alphaBeta(boolean player1, Gameboard board, int alpha, int beta, int currentDepth) {

   	 Commander commander = new Commander(board, player1);
   	 
   
   	 if (currentDepth == maxDepth || commander.whoIsTheWinner() != 1 
   			 || getAvailableMoves(true,board).isEmpty() && getAvailableMoves(false,board).isEmpty())
   		 return score(player1, board, currentDepth);

   	 if (player1 == true)
   		 return getMax(true, board, alpha, beta, currentDepth + 1);
   	 else
   		 return getMin(false, board, alpha, beta, currentDepth + 1);
    }

    private static int getMax(boolean player1, Gameboard board, int alpha, int beta, int currentDepth) {

   	 String indexOfBestMove = "";
   	 Commander commanderBoard = new Commander(board, player1);
   	 List<String> availableMovesList = new ArrayList<>(AI.getAvailableMoves(player1, board));
   	 int availableMovesNumber = getAvailableMoves(!player1, board).size();
   	 int freeFields = getAvailableMoleMoves(player1, board).size();
   	 if(availableMovesNumber <= 6 && freeFields<26)
  		 if(player1 == true && board.player1.getMaulwurf()>0 || player1==false && board.player2.getMaulwurf()>0) {
  			 List<String> availableMoleMovesList = getAvailableRelevantMoleMoves(player1, board);
  		
  			 if(availableMoleMovesList.isEmpty())
  				 availableMovesList.addAll(getAvailableMoleMoves(player1, board));
  			 else
  				 availableMovesList.addAll(AI.getAvailableRelevantMoleMoves(player1, board));
  		 }
   	 
   	 for (String theMove : availableMovesList) {

   		 
   		
  		 Gameboard newBoard = board.getCopy();
   		 Commander commanderNewBoard = new Commander(newBoard, player1);
   		 commanderNewBoard.makeMove(theMove);
   		 int score = alphaBeta(!player1, newBoard, alpha, beta, currentDepth);
   		 
   		
   		 if (score > alpha) {
   			 alpha = score;
   			 indexOfBestMove = theMove;
   		 }

   		 if (alpha >= beta)
   			 break;

   	 }
   
   	 if (indexOfBestMove != "") {
   		
   		 commanderBoard.makeMove(indexOfBestMove);
   		 bestMove = indexOfBestMove;
   	 }

   	 return alpha;
    }

    private static int getMin(boolean player1, Gameboard board, int alpha, int beta, int currentDepth) {

   	 String indexOfBestMove = "";
   	 Commander commanderBoard = new Commander(board, player1);
   	 List<String> availableMovesList = new ArrayList<>(AI.getAvailableMoves(player1, board));
   	 int availableMovesNumber = getAvailableMoves(!player1, board).size();
  
   	 int freeFields = getAvailableMoleMoves(player1, board).size();
   	 if(availableMovesNumber <= 6 && freeFields<26)
   		 if(player1 == true && board.player1.getMaulwurf()>0 || player1==false && board.player2.getMaulwurf()>0) {
   			 List<String> availableMoleMovesList = getAvailableRelevantMoleMoves(player1, board);
   		
   			 if(availableMoleMovesList.isEmpty())
   				 availableMovesList.addAll(getAvailableMoleMoves(player1, board));
   			 else
   				 availableMovesList.addAll(AI.getAvailableRelevantMoleMoves(player1, board));
   		 }
   	
   	 for (String theMove : availableMovesList) {
   			
   	
   		 Gameboard newBoard = board.getCopy();
   		 Commander commanderNewBoard = new Commander(newBoard, player1);
   		 commanderNewBoard.makeMove(theMove);
   		 int score = alphaBeta(!player1, newBoard, alpha, beta, currentDepth);
   		 
   		 
   		 if (score < beta) {
   			 beta = score;
   			 indexOfBestMove = theMove;
   		 }

   		 if (alpha >= beta)
   			 break;

   	 }
   	 
   	 if (indexOfBestMove != "") {
   	
  		 commanderBoard.makeMove(indexOfBestMove);
   		 bestMove = indexOfBestMove;
   	 }

   	 return beta;
    }

    private static List<String> getAvailableMoves(boolean player1, Gameboard board) {

   	 Commander commander = new Commander(board, player1);
   	 List<String> availableMoves = new ArrayList<>();

   	 String searchedMoves[] = { "wa", "ww", "wd", "dw", "dd", "ds", "sd", "ss", "sa", "as", "aa", "aw" };
   	 for (String i : searchedMoves) {
   		 if (commander.isValidMove(i))
   			 availableMoves.add(i);

   	 }

   	 return availableMoves;
    }

    private static List<String> getAvailableMoleMoves(boolean player1, Gameboard board) {

   	 Commander commander = new Commander(board, player1);
   	 List<String> availableMoleMoves = new ArrayList<>();

   	 String moleMove;
   	 for (char i = 'a'; i < 'h'; i++)
   		 for (int j = 1; j < 8; j++) {
   			 moleMove = i + Integer.toString(j);
   			 if (commander.isValidMove(moleMove))
   				 availableMoleMoves.add(moleMove);
   		 }

   	 return availableMoleMoves;
    }
    
    private static List<String> getAvailableRelevantMoleMoves(boolean player1, Gameboard board) {

    	     Gameboard newBoard = new Gameboard();
    	     newBoard = board.getCopy();
      	 Commander commander = new Commander(newBoard, player1);
      	 List<String> availableMoleMoves = new ArrayList<>();
      	 
      	 if(player1 == true && board.player1.getPlayerMoleCount()== 0 || 
      			 player1 == false && board.player2.getPlayerMoleCount() == 0)
      		 return availableMoleMoves;
      	 

      	 String moleMove;
      	 for (char i = 'a'; i < 'h'; i++)
      		 for (int j = 1; j < 8; j++) {
      			 moleMove = i + Integer.toString(j);
      			 if (commander.isValidMove(moleMove))
      				 availableMoleMoves.add(moleMove);
      		 }
      	 
      	 List<String> availableRelevantMoleMoves = new ArrayList<>();
      	 TreeMap<String, Integer> movesMap = new TreeMap<>();
      	 int enemyMovesBefore = getAvailableMoves(!player1, newBoard).size();
      	 int min = 100;
      	 
      	 for(String move : availableMoleMoves) {
      		 commander.board = board.getCopy();
      		 commander.makeMove(move);
      		 int enemyMovesAfter = getAvailableMoves(!player1, commander.board).size();
      		 if(enemyMovesAfter<enemyMovesBefore) {
      			 if(enemyMovesAfter < min)
      				 min = enemyMovesAfter;
      			 movesMap.put(move, enemyMovesAfter);
      			 
      		 }
      		
      	 }
      	 
      	 for(Map.Entry<String,Integer> entry : movesMap.entrySet())
  			 if(entry.getValue()==min)
  				 availableRelevantMoleMoves.add(entry.getKey());
         
      	 return availableRelevantMoleMoves;

      	 
       }
    
    private static int getAdaptiveDepth(Gameboard board) {
    	
    	int sum = AI.getAvailableMoves(true, board).size() + AI.getAvailableMoves(false,board).size() + 1;
    int 	depth = (128)/sum;
    
    if(depth>6 && depth<9 && sum>15)
    	depth = 6;
    if(depth>8 && depth<=12)
    		depth = 8;
    if(depth == 8 && sum>9)
		depth = 6;
    	if(depth>8) {
    		int freeFields = getAvailableMoleMoves(true, board).size();
    		if(freeFields > 24)
    			depth=8;
    	}
    	return depth;
    }

    private static int score(boolean player1, Gameboard board, int currentDepth) {

   
    	int score;
   	 Commander commander = new Commander(board, player1);
   	 List<String> player1Moves = new ArrayList<>();
   	 List<String> player2Moves = new ArrayList<>();
   	 player1Moves = getAvailableMoves(true, board);
   	 player2Moves = getAvailableMoves(false, board);
   
   	if(getAvailableMoves(false, board).isEmpty() && getAvailableMoves(true,board).isEmpty()) {
   		
   		score = board.player1.getMaulwurf() - board.player2.getMaulwurf();
   		if(score == 0)
   			if(player1 == true)
   				return -100 + currentDepth;
   			else
   				return 100 + currentDepth;
   		if(score>0)
   			return 100 - currentDepth;
   		if(score<0)
   			return -100 + currentDepth;
   	}
   	
   	if (commander.whoIsTheWinner() == 2)
  		 return 100 - currentDepth;
  	 else if (commander.whoIsTheWinner() == 3)
  		 return -100 + currentDepth;
   	
   	score = player1Moves.size() - player2Moves.size() + board.player1.getMaulwurf()*10 - board.player2.getMaulwurf()*10;
   	
   	 return score;
  	 
    
   	 }

}




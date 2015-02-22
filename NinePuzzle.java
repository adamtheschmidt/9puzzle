/* NinePuzzle.java
   CSC 225 - Fall 2014
   Assignment 5 - Template for the 9-puzzle

   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.

   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).

   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt

   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the
   empty square. For example, a sample board with the middle square empty is

    1 2 3
    4 0 5
    6 7 8

   And a solved board is

    1 2 3
    4 5 6
    7 8 0

   An input file can contain an unlimited number of boards; each will be
   processed separately.

*/

import java.util.Scanner;
import java.io.File;

public class NinePuzzle{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;
	

///////////////////////////////////////////////////////////////////////////////////
//THIS IS THE MASTER ARRAY and it's functions
///////////////////////////////////////////////////////////////////////////////////
	//solution board is ID 0
	public static int[][] THE_ARRAY = new int[(NUM_BOARDS)][7];
	//0 is B1
	//1 is B2
	//2 is B3
	//3 is B4
	//4 is the visited boolean
	//5 is the path potition
	//6 is solveable trip


	public static int depth = 0;
//this function is not working, the alt version works better
	public static void BuildArray(int ID){//should be functional
		depth++;
		if(depth > 30000){return;}
		if(THE_ARRAY[ID][6] == 1){return;}
		
		THE_ARRAY[ID][6] = 1;
		int[] moves = findMoves(ID);//will need to default at -1 instead of 0;
		int i = 0;
		for(i = 0;i<4;i++){
			if(moves[i] == -1){break;}
			THE_ARRAY[ID][i] = moves[i];
			BuildArray(moves[i]);
			//System.out.print(moves[i]);
		}

			
	}

	public static void clearTrace(){
	
		for(int i = 0; i<NUM_BOARDS; i++){
			THE_ARRAY[i][4] = 0;
		}

	}



	public static void BuildArrayQ(int ID){

		Enqueue(ID);
		THE_ARRAY[ID][5] = 1;
		while(Qlen != 0){
			if(THE_ARRAY[ID][6] == 0){
				THE_ARRAY[ID][6] = 1;
				int[] moves = findMoves(ID);
				for(int i = 0; i<4; i++){
					THE_ARRAY[ID][i] = moves[i];
					if(moves[i] != -1){
						Enqueue(moves[i]);
						if(THE_ARRAY[moves[i]][5] == 0){THE_ARRAY[moves[i]][5] = (THE_ARRAY[ID][5]+1);
					}}
				}
			}
			ID = Dequeue();
		}
		ClearQueue();
	}


	public static int[] findMoves(int ID){
		int zero = findZero(ID);
		int[] moves = new int[4];
		for(int i = 0; i<4; i++){
			moves[i] = -1;
		}		

		int[][] board = getBoardFromIndex(ID);


		switch(zero){
			case 0:
				moves[0] = getIndexFromBoard(swap(board,0,1));
				moves[1] = getIndexFromBoard(swap(board,0,3));
				break;
			case 1:
				moves[0] = getIndexFromBoard(swap(board,1,0));
				moves[1] = getIndexFromBoard(swap(board,1,2));
				moves[2] = getIndexFromBoard(swap(board,1,4));
				break;
			case 2:
				moves[0] = getIndexFromBoard(swap(board,2,1));
				moves[1] = getIndexFromBoard(swap(board,2,5));
				break;
			case 3:
				moves[0] = getIndexFromBoard(swap(board,3,0));
				moves[1] = getIndexFromBoard(swap(board,3,4));
				moves[2] = getIndexFromBoard(swap(board,3,6));
				break;
			case 4:
				moves[0] = getIndexFromBoard(swap(board,4,1));
				moves[1] = getIndexFromBoard(swap(board,4,3));
				moves[2] = getIndexFromBoard(swap(board,4,5));
				moves[3] = getIndexFromBoard(swap(board,4,7));
				break;
			case 5:
				moves[0] = getIndexFromBoard(swap(board,5,4));
				moves[1] = getIndexFromBoard(swap(board,5,2));
				moves[2] = getIndexFromBoard(swap(board,5,8));
				break;
			case 6:
				moves[0] = getIndexFromBoard(swap(board,6,3));
				moves[1] = getIndexFromBoard(swap(board,6,7));
				break;
			case 7:
				moves[0] = getIndexFromBoard(swap(board,7,6));
				moves[1] = getIndexFromBoard(swap(board,7,4));
				moves[2] = getIndexFromBoard(swap(board,7,8));
				break;
			case 8:
				moves[0] = getIndexFromBoard(swap(board,8,7));
				moves[1] = getIndexFromBoard(swap(board,8,5));
				break;
		}
		return moves;

	}

	public static int[][] swap(int[][] inBoard, int i1, int i2){
		int[][] copy = new int[3][3];
		for(int i = 0;i<3;i++){
			for(int j=0;j<3;j++){
				copy[i][j] = inBoard[i][j];
			}
		}
		int x1; int y1;
		int x2; int y2;

		switch(i1){
			
		case 0: x1 = y1 = 0; break;
		case 1: x1 = 0; y1 = 1; break;
		case 2: x1 = 0; y1 = 2; break;
		case 3: x1 = 1; y1 = 0; break;
		case 4: x1 = 1; y1 = 1; break;
		case 5: x1 = 1; y1 = 2; break;
		case 6: x1 = 2; y1 = 0; break;
		case 7: x1 = 2; y1 = 1; break;
		case 8: x1 = 2; y1 = 2; break;
		default: throw new RuntimeException();	
		}
		
		switch(i2){
			
		case 0: x2 = y2 = 0; break;
		case 1: x2 = 0; y2 = 1; break;
		case 2: x2 = 0; y2 = 2; break;
		case 3: x2 = 1; y2 = 0; break;
		case 4: x2 = 1; y2 = 1; break;
		case 5: x2 = 1; y2 = 2; break;
		case 6: x2 = 2; y2 = 0; break;
		case 7: x2 = 2; y2 = 1; break;
		case 8: x2 = 2; y2 = 2; break;
		default: throw new RuntimeException();
		}
		
		int temp = copy[x1][y1];
		copy[x1][y1] = copy[x2][y2];
		copy[x2][y2] = temp;

		return copy;

	}
	

	public static int findZero(int ID){
		int[][] board = getBoardFromIndex(ID);
		int z = 0;
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				if(board[i][j] == 0){return z;}
				z++;	
			}
		}
		//system error if we get here
		throw new RuntimeException();

	}

	public static void printStats(int ID){

		System.out.print("ID: " + ID +" position: " + THE_ARRAY[ID][5]+'\n'); 
		printBoard(getBoardFromIndex(ID));


	}

	public static void printStatsDerivs(int ID){

		printStats(ID);
		System.out.print("Derivs: \n");
		for(int i = 0; i<4 ; i++){
			if(THE_ARRAY[ID][i] != -1){printStats(THE_ARRAY[ID][i]);}
		}


	}




//////////////////////////////////////////////////////////

		//END OF ARRAY FUNCTIONS


////////////////////////////////////////////////////////



///////////////////////////////////
////      QUEUE AND STACK AND FUNCTIONS /////
///////////////////////////////////

	public static int[] THE_QUEUE = new int[500000];
	
	public static int QStart = 0;
	public static int Qlen = 0;
	public static int QEnd = 0;

	public static void Enqueue(int ID){

		THE_QUEUE[QEnd] = ID;
		QEnd++;
		Qlen++;
		
	}

	public static int Dequeue(){
		
		int temp = THE_QUEUE[QStart];
		QStart++;
		Qlen--;
		return temp;

	}

	public static void ClearQueue(){

		QStart = 0;
		QEnd = 0;
		Qlen = 0;

	}

///////////////////////////////////////////
/////           end of QUEUE AND STACK and FUNCTIONS

//////////////////////////////////////////

///////////////////////////////////////////
//////////   Route functions
/////////////////////////////////////////

	public static void traceRoute(int ID){

		clearTrace();
		int mc = 0;
		while(true){
			if(mc >35){break;}
			printBoard(getBoardFromIndex(ID));
			if(ID == 0){break;}
			ID = evaluateMove(ID);
			mc++;

		}


	}	
//issues here
	public static int evaluateMove(int ID){
		
		THE_ARRAY[ID][4] = 1;
		int position = THE_ARRAY[ID][5];
		int compare = ID;
		int moveto = ID;
		for(int i = 0; i<4; i++){
			if(
				(THE_ARRAY[ID][i] > -1) && 
				(THE_ARRAY[(THE_ARRAY[ID][i])][5] <  position)//&&
			//	(THE_ARRAY[(THE_ARRAY[ID][i])][4] == 0)
			  )
			{ 
				moveto = THE_ARRAY[ID][i];
				break;
				}
		}
		if(compare == moveto){
			printStatsDerivs(ID);
			System.exit(1);
		}

		return moveto;
	}









//////////////////////////////////////////////
//// end route functions
/////////////////////////////////////////


	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the
		value 0),return true if the board is solvable and false otherwise.
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	public static boolean SolveNinePuzzle(int[][] B){

		BuildArrayQ(0);
		int ID = getIndexFromBoard(B);

		if(THE_ARRAY[ID][6] == 0){return false;}
		//System.out.print("will solve in "+ THE_ARRAY[ID][5] + " moves.");
		traceRoute(ID);

		return true;

	}

	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}


	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284.
	*/
	public static int getIndexFromBoard(int[][] B){
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++){
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for(n = 9; n > 1; n--){
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;

			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}

	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--){
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for(i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}


	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */



		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}

		int graphNum = 0;
		double totalTimeSeconds = 0;

		//Read boards until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++){
				for (int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9){
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;

			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);

	}

}

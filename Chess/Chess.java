import java.io.*;
import java.util.*;

public class Chess {
    public static void main(String[] args){
	if (args.length != 2) {
	    System.out.println("Usage: java Chess layout moves");
	}
	Piece.registerPiece(new KingFactory());
	Piece.registerPiece(new QueenFactory());
	Piece.registerPiece(new KnightFactory());
	Piece.registerPiece(new BishopFactory());
	Piece.registerPiece(new RookFactory());
	Piece.registerPiece(new PawnFactory());
	Board.theBoard().registerListener(new Logger());
	// args[0] is the layout file name
	// args[1] is the moves file name
	// Put your code to read the layout file and moves files

		Board b = Board.theBoard();
	// here.
		try {
			File f = new File(args[0]);
			Scanner input = new Scanner(f);
			if (f.exists() && f.length() > 0) {
				while (input.hasNext()) {
					String text = input.nextLine().replaceAll(" ", "");
					if (text.charAt(0) == '#')
						continue;
					// s[0] is the location, s[1] is the piece
					String[] s = text.split("=", 2);
					Piece p = Piece.createPiece(s[1]);
					b.addPiece(p, s[0]);
				}
				input.close();
			}
		}catch (IOException e){

			System.out.println("Error reading file!");
			//throw new UnsupportedOperationException();
		}



		// read moves file
		try {
			File f1 = new File(args[1]);
			Scanner input1 = new Scanner(f1);
			if (f1.exists() && f1.length() > 0) {
				while (input1.hasNext()) {
					String text1 = input1.nextLine().replaceAll(" ", "");
					if (text1.charAt(0) == '#')
						continue;
					// s[0] is from, s[1] is to
					String[] s1 = text1.split("-", 2);
					b.movePiece(s1[0], s1[1]);
				}
			}
			input1.close();
		}catch(IOException e){
			System.out.println("Error reading file!");
			//throw new UnsupportedOperationException();
		}


	// Leave the following code at the end of the simulation:
	System.out.println("Final board:");
	Board.theBoard().iterate(new BoardPrinter());
    }
}
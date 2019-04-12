import java.util.*;

public class Test {

    // Run "java -ea Test" to run with assertions enabled (If you run
    // with assertions disabled, the default, then assert statements
    // will not execute!)

	private static boolean isPermutation(List<String> l1, List<String> l2){
		Collections.sort(l1);
		Collections.sort(l2);
		return(l1.equals(l2));
	}


    public static void test1() {
	Board b = Board.theBoard();
	Piece.registerPiece(new PawnFactory());
	Piece p = Piece.createPiece("bp");
	b.addPiece(p, "a3");
	assert b.getPiece("a3") == p;
    }

    //test for bishop
	public static void test_bishop() {
		Board b = Board.theBoard();
		Piece.registerPiece(new BishopFactory());
		Piece p;
		Piece o1;
		List<String> movements;

		// Create WHITE piece
		p = Piece.createPiece("wb");

		// Test WHITE Color and toString
		assert p.color().equals(Color.WHITE);
		assert p.toString().equals("wb");

		// Test WHITE uninhibited movement
		b.clear();
		movements = Arrays.asList(new String[] {"c5", "d6", "e7", "f8", "a5",
				"c3", "d2", "e1", "a3"});
		assert isPermutation(p.moves(b, "b4"),movements);

		// Test WHITE-WHITE inhibited movement
		b.clear();
		b.addPiece(p, "e7");
		movements = Arrays.asList(new String[] {"c5", "d6", "a5", "c3", "d2",
				"e1", "a3"});
		assert isPermutation(p.moves(b, "b4"),movements);

		// Test WHITE-BLACK inhibited movement
		b.clear();
		b.addPiece(Piece.createPiece("bb"), "e7");
		movements = Arrays.asList(new String[] {"c5", "d6", "e7", "a5", "c3",
				"d2", "e1", "a3"});
		assert isPermutation(p.moves(b, "b4"),movements);

		// Check that I can move to an empty
		b.clear();
		b.addPiece(Piece.createPiece("wb"), "b4");
		b.movePiece("b4", "e7");

		// Check that I can move to a capture
		b.clear();
		b.addPiece(Piece.createPiece("wb"), "b4");
		b.addPiece(Piece.createPiece("bb"), "e7");
		b.movePiece("b4", "e7");

		// Check that I cannot move to a bad place (wrong color capture)
		b.clear();
		b.addPiece(Piece.createPiece("wb"), "b4");
		b.addPiece(Piece.createPiece("wb"), "e7");
		try {
			b.movePiece("b4", "e7");
		} catch (Exception e) {
			assert e instanceof Exception;
		}

		// Check that I cannot move to a bad place (non-moveable)
		b.clear();
		b.addPiece(Piece.createPiece("wb"), "b4");
		try {
			b.movePiece("b4", "c7");
		} catch (Exception e) {
			assert e instanceof Exception;
		}
	}

	public static void test_shinny() {
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new KnightFactory());
		Piece p1 = Piece.createPiece("bp");
		Piece p2 = Piece.createPiece("wk");
		Piece p3 = Piece.createPiece("wn");
		assert p1.toString().equals("bp");
		assert p2.toString().equals("wk");
		assert p3.toString().equals("wn");
		assert !p3.toString().equals("bp");
	}

	//test for queen
	public static void test_Drew() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new PawnFactory());
		Piece p = Piece.createPiece("bq");
		Piece pawn1 = Piece.createPiece("wp");
		Piece pawn2 = Piece.createPiece("wp");
		Piece pawn3 = Piece.createPiece("bp");
		Piece pawn4 = Piece.createPiece("bp");
		List<String> a1Moves = p.moves(b, "a1");
		assert a1Moves.size() == 21;
		assert a1Moves.contains("a2");
		assert a1Moves.contains("a3");
		assert a1Moves.contains("a4");
		assert a1Moves.contains("a5");
		assert a1Moves.contains("a6");
		assert a1Moves.contains("a7");
		assert a1Moves.contains("a8");
		assert a1Moves.contains("b1");
		assert a1Moves.contains("c1");
		assert a1Moves.contains("d1");
		assert a1Moves.contains("e1");
		assert a1Moves.contains("f1");
		assert a1Moves.contains("g1");
		assert a1Moves.contains("h1");
		assert a1Moves.contains("b2");
		assert a1Moves.contains("c3");
		assert a1Moves.contains("d4");
		assert a1Moves.contains("e5");
		assert a1Moves.contains("f6");
		assert a1Moves.contains("g7");
		assert a1Moves.contains("h8");

		List<String> d4Moves = p.moves(b, "d4");
		assert d4Moves.size() == 27;
		assert d4Moves.contains("d8");
		assert d4Moves.contains("d7");
		assert d4Moves.contains("d6");
		assert d4Moves.contains("d5");
		assert d4Moves.contains("d3");
		assert d4Moves.contains("d2");
		assert d4Moves.contains("d1");
		assert d4Moves.contains("a4");
		assert d4Moves.contains("b4");
		assert d4Moves.contains("c4");
		assert d4Moves.contains("e4");
		assert d4Moves.contains("f4");
		assert d4Moves.contains("g4");
		assert d4Moves.contains("h4");
		assert d4Moves.contains("e5");
		assert d4Moves.contains("f6");
		assert d4Moves.contains("g7");
		assert d4Moves.contains("h8");
		assert d4Moves.contains("c5");
		assert d4Moves.contains("b6");
		assert d4Moves.contains("a7");
		assert d4Moves.contains("e3");
		assert d4Moves.contains("f2");
		assert d4Moves.contains("c3");
		assert d4Moves.contains("h8");
		assert d4Moves.contains("b2");
		assert d4Moves.contains("a1");

		b.addPiece(pawn1, "b6");
		b.addPiece(pawn2, "d2");
		b.addPiece(pawn3, "a4");
		b.addPiece(pawn4,"g7");
		List<String> list3 = Arrays.asList(new String[]{"a1","b2","c3","e5","f6","g1",
				"f2","e3","c5","b6","b4","c4","e4","f4","g4","h4","d2","d3","d5","d6","d7","d8"});

		assert isPermutation(p.moves(b, "d4"), list3);


	}

	public static void test_Byron() {
		Board b = Board.theBoard();
		b.clear();
		Piece p = Piece.createPiece("wq");
		b.addPiece(p, "a1");
		b.movePiece("a1", "a4");
		b.movePiece("a4", "h4");
		b.movePiece("h4", "h3");
		b.movePiece("h3", "f3");
		b.movePiece("f3", "c6");
		b.movePiece("c6", "e8");
		b.movePiece("e8", "f7");
		b.movePiece("f7", "a2");
		assert b.getPiece("a2") == p;
		boolean exceptionThrown = false;
		try {
			b.movePiece("a2", "b4");
		} catch (Exception e) {
			exceptionThrown = true;
		}
		assert exceptionThrown;
	}

	// test for king and queen
	public static void test_Victor() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KingFactory());
		Piece.registerPiece(new KnightFactory());
		Piece bq = Piece.createPiece("bq");
		Piece bk = Piece.createPiece("bk");
		Piece wn = Piece.createPiece("wn");
		b.addPiece(bq, "c3");
		b.addPiece(bk, "e5");
		b.addPiece(wn, "c4");
		assert b.getPiece("c3") == bq;
		assert b.getPiece("e5") == bk;
		assert b.getPiece("c4") == wn;
		/* check the correct moves of King */
		List<String> k_moves = bk.moves(b, "e5");
		List<String> list = Arrays.asList(new String[]{"d6","f6","d5","f5","d4","e4","f4"});
		assert k_moves != null && k_moves.size() == 8;
		assert k_moves.contains("d6");
		assert k_moves.contains("e6");
		assert k_moves.contains("f6");
		assert k_moves.contains("d5");
		assert k_moves.contains("f5");
		assert k_moves.contains("d4");
		assert k_moves.contains("e4");
		assert k_moves.contains("f4");

		Piece wn2 = Piece.createPiece("wn");
		Piece bn = Piece.createPiece("bn");
		b.addPiece(wn2, "d6");
		b.addPiece(bn, "e6");
		assert isPermutation(bk.moves(b, "e5"), list);

		/* check the correct moves of Queen */
		List<String> q_moves = bq.moves(b, "c3");
		assert q_moves != null && q_moves.size() == 17;
		assert q_moves.contains("c4");
		assert q_moves.contains("c1");
		assert q_moves.contains("a3");
		assert q_moves.contains("b4");
		assert q_moves.contains("d4");
		assert q_moves.contains("e1");
		assert !q_moves.contains("c5");
		assert !q_moves.contains("e5");
	}

	public static void test_Diana() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new RookFactory());
		Piece.registerPiece(new PawnFactory());
		Piece p1 = Piece.createPiece("br");
		b.addPiece(p1, "h1");
		assert b.getPiece("h4") == null;
		assert b.getPiece("h1") == p1;
		Piece p2 = Piece.createPiece("wp");
		b.addPiece(p2, "h4");
		assert b.getPiece("h4") == p2;
		b.movePiece("h1", "h4");
		assert b.getPiece("h4") == p1;
	}


	// for rook test
	public static void test_Erica() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new RookFactory());
		Piece p = Piece.createPiece("wr");
		List<String> list = Arrays.asList(new String[] {"b2","c2","d2","e2","f2","g2","h2","a1","a3","a4","a5","a6","a7","a8"});
		List<String> list1 = Arrays.asList((new String[]{"a5","b5","c5","e5","f5","d1","d2","d3","d4","d6","d7"}));
		assert isPermutation(p.moves(b, "a2"), list);
		Piece q = Piece.createPiece("br");
		Piece m = Piece.createPiece("wr");
		b.addPiece(p, "d5");
		b.addPiece(q,"d7");
		b.addPiece(m, "g5");
		assert isPermutation(p.moves(b, "d5"),list1);
		try {
			b.movePiece("c3", "d5");
		} catch(Exception e) {
			assert e instanceof Exception;
		}

	}

	//for pawn
	public static void test_Trevor(){
		Board b= Board.theBoard();
		b.clear();
		Piece.registerPiece(new PawnFactory());
		Piece p1 = Piece.createPiece("wp");
		Piece p2 = Piece.createPiece("bp");
		Piece p3 = Piece.createPiece("bp");

		b.addPiece(p1, "b3");
		b.addPiece(p2, "b4");
		b.addPiece(p3, "a4");
		try {
			b.movePiece("b3", "b4");
		} catch(Exception e) {
			assert e instanceof InvalidMoveException;
		}
		b.movePiece("b3", "a4");

		Piece p4 = Piece.createPiece("wp");
		Piece p5 = Piece.createPiece("bp");
		b.addPiece(p4,"c2");
		List<String> list = Arrays.asList(new String[]{"c3","c4"});
		assert isPermutation(p4.moves(b, "c2"), list);

		b.addPiece(p5,"d3");
		List<String> list2 = Arrays.asList(new String[]{"c3","c4","d3"});
		assert isPermutation(p4.moves(b, "c2"), list2);

		System.out.println(p5.moves(b,"d3"));

	}

	public static void test_Shi(){
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new KingFactory());
		Piece p = Piece.createPiece("wk");
		b.addPiece(p, "e1");

		// check valid king movement
		b.movePiece("e1","e2");
		b.movePiece("e2","e3");
		b.movePiece("e3","f3");
		b.movePiece("f3","f2");
		b.movePiece("f2","e2");
		b.movePiece("e2","e1");
		assert b.getPiece("e1") == p;

		// check invalid king movement
		try{
			b.movePiece("e1", "e4");
			assert false;
		} catch(Exception e){
			assert true;
		}
	}

	public static void test_Sophia() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new KnightFactory());
		Piece p1 = Piece.createPiece("wn");
		Piece p2 = Piece.createPiece("wn");

		b.addPiece(p1, "b1");
		b.addPiece(p2, "g1");

		b.movePiece("b1", "d2");
		b.movePiece("g1", "e2");
		b.movePiece("d2", "e4");
		b.movePiece("e2", "d4");
		b.movePiece("e4", "f6");
		b.movePiece("d4", "b5");
		b.movePiece("f6", "d7");
		b.movePiece("b5", "c7");
		b.movePiece("d7", "b8");

		assert b.getPiece("b8") == p1;
		assert b.getPiece("c7") == p2;
		assert !(b.getPiece("d7") == p1);
	}

	public static void test_Ashley() {
		Board b = Board.theBoard();
		b.clear();

		Piece.registerPiece(new PawnFactory());
		Piece.registerPiece(new QueenFactory());

		Piece pawn = Piece.createPiece("bp");
		Piece queen = Piece.createPiece("wq");

		b.addPiece(pawn, "a7");
		b.addPiece(queen, "b6");

		// The pawn captures the queen!
		b.movePiece("a7", "b6");
		assert b.getPiece("b6") == pawn;
	}

	public static void testGu() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new PawnFactory());
		b.addPiece(Piece.createPiece("bq"), "d8");
		b.addPiece(Piece.createPiece("wq"), "d1");
		b.addPiece(Piece.createPiece("bp"), "d7");
		b.addPiece(Piece.createPiece("wp"), "d2");
		b.movePiece("d1", "a4");
		b.movePiece("a4", "c6");
		b.movePiece("d2", "d4");
		b.movePiece("d7", "d5");
		b.movePiece("d8", "d6");
		b.movePiece("c6", "d6");
		assert b.getPiece("d6").toString().equals("wq");
		assert b.getPiece("d5").toString().equals("bp");
		assert b.getPiece("d4").toString().equals("wp");
	}

	public static void testLindsay() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new KingFactory());
		Piece king = Piece.createPiece("bk");

		b.addPiece(king, "e8");
		b.movePiece("e8", "e7");
		b.movePiece("e7", "e6");
		b.movePiece("e6","f6");
		b.movePiece("f6","f7");
		b.movePiece("f7","e7");
		b.movePiece("e7","e8");
		assert b.getPiece("e8") == king;

		try{
			b.movePiece("e8", "e5");
			assert false;
		} catch(Exception e){
			assert true;
		}
	}

	public static void testJennifer() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new RookFactory());
		Piece rook = Piece.createPiece("wr");

		assert b.getPiece("a2") == null;

		try {
			b.addPiece(rook, "2a");
			assert false;
		} catch (Exception e) {
			assert e instanceof Exception;
		}

		b.addPiece(rook, "a2");
		assert b.getPiece("a2") == rook;

		b.movePiece("a2", "a6");
		assert b.getPiece("a2") != rook;

		b.movePiece("a6", "c6");
		b.movePiece("c6", "b6");
		b.movePiece("b6", "b1");

		try {
			b.movePiece("b1", "d3");
			assert false;
		} catch (Exception e) {
			assert e instanceof Exception;
		}
	}

	public static void testQinlong() {
		Board b = Board.theBoard();
		b.clear();
		Piece p = Piece.createPiece("wb");
		b.addPiece(p, "a1");
		b.movePiece("a1", "b2");
		b.movePiece("b2", "h8");
		assert b.getPiece("h8") == p;
	}

	public static void testWenhao(){
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new QueenFactory());
		Piece.registerPiece(new KingFactory());
		Piece p1 = Piece.createPiece("bq");
		Piece p2 = Piece.createPiece("wk");

		// Move black Queen and then check
		b.addPiece(p1, "e5");
		b.movePiece("e5", "d4");
		b.movePiece("d4", "c3");
		b.movePiece("c3", "b4");
		b.movePiece("b4", "a5");
		b.movePiece("a5", "b6");
		b.movePiece("b6", "c7");
		b.movePiece("c7", "d6");
		b.movePiece("d6", "e7");
		assert b.getPiece("e7") == p1;

		// Move white King and then check
		b.addPiece(p2, "e1");
		b.movePiece("e1", "d1");
		b.movePiece("d1", "c1");
		b.movePiece("c1", "c2");
		b.movePiece("c2", "c3");
		b.movePiece("c3", "d3");
		assert b.getPiece("d3") == p2;
	}

	public static void testChristos() {
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new KnightFactory());
		Piece knight = Piece.createPiece("wn");

		b.addPiece(knight, "b1");

		b.movePiece("b1", "c3");
		b.movePiece("c3", "b5");
		b.movePiece("b5", "d4");
		assert b.getPiece("d4") == knight;
		assert !(b.getPiece("b1") == knight);
	}

	//test for Knight
	public static void test_Knight(){
		Board b = Board.theBoard();
		b.clear();
		Piece.registerPiece(new KnightFactory());
		Piece k1 = Piece.createPiece("wn");
		Piece k2 = Piece.createPiece("bn");
		b.addPiece(k1, "e7");
		b.addPiece(k2,"e6");
		List<String> list1 = Arrays.asList(new String[]{"c8","g8","c6","g6","d5","f5"});
		List<String> list2 = Arrays.asList(new String[]{"d8","f8","c7","g7","c5","g5","d4","f4"});
		assert  isPermutation(k1.moves(b,"e7" ), list1);
		assert isPermutation(k2.moves(b,"e6"),list2);
	}







	public static void main(String[] args) {

    	test1();
    	test_bishop();
    	test_shinny();
    	test_Drew();
    	test_Byron();
		test_Victor();
		test_Diana();
		test_Erica();
		test_Trevor();
		test_Shi();
		test_Sophia();
		test_Ashley();
		testGu();
		testLindsay();
		testJennifer();
		testQinlong();
		testWenhao();
		testChristos();
		test_Knight();


	}
}
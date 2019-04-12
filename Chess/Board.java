import java.util.*;

public class Board {

    private Piece[][] pieces = new Piece[8][8];
    final static Board theBoard = new Board();
    private List<BoardListener> list = new ArrayList<>();

    private  Board() { }
    
    public static Board theBoard() {
        return theBoard; // implement this
    }

    // Returns piece at given loc or null if no such piece
    // exists
    public Piece getPiece(String loc) {
            int[] location = convertLocToInt(loc);
            if(0 <= location[0] &&  location[0] <= 7 && 0 <= location[1] && location[1] <=7){
                return pieces[location[0]][location[1]];
            }else{
                throw new OutOfBoundException();
            }
    }

    public void addPiece(Piece p, String loc) {
            int[] location = convertLocToInt(loc);
            if (0 <= location[0] && location[0] <= 7 && 0 <= location[1] && location[1] <= 7) {
                Piece p_loc = getPiece(loc);
                if(p_loc == null){
                    pieces[location[0]][location[1]] = p;
                }else{
                    throw new PieceExistException();
                }
            } else {
                throw new OutOfBoundException();
            }

    }

    public void movePiece(String from, String to) {
            Logger l = new Logger();
            Piece p = getPiece(from);
            Piece p2 = getPiece(to);
            boolean flag = true;
            if(p != null) {
                if(p2 != null){
                    flag = false;
                }
                List<String> list = p.moves(theBoard(), from);
                if (list.contains(to)) {
                    int[] froms = convertLocToInt(from);
                    pieces[froms[0]][froms[1]] = null;
                    int[] tos = convertLocToInt(to);
                    pieces[tos[0]][tos[1]] = p;
                    Iterator<BoardListener> iter = this.list.iterator();
                    while(iter.hasNext()){
                        BoardListener b = iter.next();
                        if(flag == true) {
                            b.onMove(from, to, p);
                        }else{
                            b.onMove(from, to, p);
                            b.onCapture(p, p2);
                        }
                    }


                }else{
                    throw new InvalidMoveException();
                }
            }else{
                throw new NoSuchElementException();
            }
    }

    public void clear(){
            pieces = new Piece[8][8];
    }

    public void registerListener(BoardListener bl) {
        list.add(bl);

    }

    public void removeListener(BoardListener bl) {
	    list.remove(bl);
    }

    public void removeAllListeners(){
        this.list = new ArrayList<>();
    }

    public void iterate(BoardExternalIterator bi) {
        for(int i = 0; i <= 7; i++){
            for(int j = 0; j <= 7; j++){
                String loc = convertLocToString(i,j);
                Piece p = getPiece(loc);
                bi.visit(loc,p);
            }
        }

    }

    public boolean checkBoardBoundary(int loc_h, int loc_v){
        if(0 <= loc_h && loc_h <= 7 && 0 <= loc_v && loc_v <= 7 ){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkValidKingMove(String loc, Color c){
        Piece p = getPiece(loc);
        if(p != null && p.color() == c){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkValidQueenMove(String loc, String loc2, Color c){
        int[] locs1 = convertLocToInt(loc);
        int[] locs2 = convertLocToInt(loc2);
        boolean res = true;
        if(Math.abs(locs1[0] - locs2[0]) == Math.abs(locs1[1] - locs2[1])) {
            if(pieces[locs1[0]][locs1[1]] == null ){
                for (int i = 1; i <= Math.abs(locs1[0] - locs2[0]); i++) {
                    int sign1 = locs1[0] > locs2[0]? 1 : -1;
                    int sign2 = locs1[1] > locs2[1]? 1 : -1;
                    if(pieces[locs2[0] + i * sign1][locs2[1] + i * sign2] != null){
                            res = false;
                            break;
                    }
                }
            }else if(pieces[locs1[0]][locs1[1]].color() != c){
               res = true;
            } else{
                res = false;
            }
        }else if(locs1[0] == locs2[0]){
            if(pieces[locs1[0]][locs1[1]] == null){
                for(int i = 1; i <=  Math.abs(locs1[1] - locs2[1]); i++){
                    int sign = locs1[1] > locs2[1]? 1 : -1;
                    if(pieces[locs2[0]][locs2[1] + i * sign] != null){
                        res = false;
                        break;
                    }
                }
            }else if(pieces[locs1[0]][locs1[1]].color() != c){
                res = true;
            }else{
                res = false;
            }
        }else if(locs1[1] == locs2[1]){
            if(pieces[locs1[0]][locs1[1]] == null){
                for(int i = 1; i <=  Math.abs(locs1[0] - locs2[0]); i++){
                    int sign = locs1[0] > locs2[0]? 1 : -1;
                    if(pieces[locs2[0] + i * sign][locs2[1]] != null){
                        res = false;
                        break;
                    }
                }
            }else if(pieces[locs1[0]][locs1[1]].color() != c){
                 res = true;
            }else{
                res = false;
            }
        }else{
            res = false;
        }
        return res;
    }

    public boolean checkValidKnightMove(String loc, Color c){
        Piece p = getPiece(loc);
        if(p != null && p.color() == c){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkValidBishopMove(String loc, String loc2, Color c) {
        int[] locs1 = convertLocToInt(loc);
        int[] locs2 = convertLocToInt(loc2);
        boolean res = true;
        if (pieces[locs1[0]][locs1[1]] == null) {
            for (int i = 1; i <= Math.abs(locs1[0] - locs2[0]); i++) {
                int sign1 = locs1[0] > locs2[0] ? 1 : -1;
                int sign2 = locs1[1] > locs2[1] ? 1 : -1;
                if (pieces[locs2[0] + i * sign1][locs2[1] + i * sign2] != null) {
                        res = false;
                        break;
                }
            }
        }else if(pieces[locs1[0]][locs1[1]].color() != c){
            res = true;
        }else{
            res = false;

        }
        return res;
    }

    public boolean checkvalidRookMove(String loc, String loc2, Color c){
        int[] locs1 = convertLocToInt(loc);
        int[] locs2 = convertLocToInt(loc2);
        boolean res = true;
        if(locs1[0] == locs2[0]) {
                if (pieces[locs1[0]][locs1[1]] == null) {
                    for (int i = 1; i <= Math.abs(locs1[1] - locs2[1]); i++) {
                        int sign = locs1[1] > locs2[1] ? 1 : -1;
                        if (pieces[locs2[0]][locs2[1] + i * sign] != null) {
                            res = false;
                            break;
                        }
                    }
                }else if(pieces[locs1[0]][locs1[1]].color() != c){
                    res = true;
                }else {
                    res = false;
                }
            }
        if(locs1[1] == locs2[1]){
                if(pieces[locs1[0]][locs1[1]] == null){
                    for(int i = 1; i <=  Math.abs(locs1[0] - locs2[0]); i++){
                        int sign = locs1[0] > locs2[0]? 1 : -1;
                        if(pieces[locs2[0] + i* sign][locs2[1]] != null){
                            res = false;
                            break;
                        }
                    }
                }else if(pieces[locs1[0]][locs1[1]].color() != c){
                    res = true;
                }else{
                    res = false;
                }
            }
        return  res;
    }

    public boolean checkValidPawnEat(String loc, Color c){
        int[] locs = convertLocToInt(loc);
        Piece p = getPiece(loc);
        if(p != null && p.color() != c){
            return true;
        }else{
            return false;
        }
    }

    public boolean checkValidPawnMove(String loc, String loc2, Color c){
        int[] locs = convertLocToInt(loc);
        int[] locs2 = convertLocToInt(loc2);
        boolean res = false;
        int sign = locs[0] > locs2[0]? 1 : -1;
        for(int i = 1; i <= Math.abs(locs[0] - locs2[0]); i++){
            if(pieces[locs2[0] + sign * i][locs2[1]] == null){
                res = true;
            }
        }
        return res;
    }


    public int[] convertLocToInt(String loc){
        int[] res = new int[2];
        int loc_h = loc.charAt(1) - '1';
        int loc_v = loc.charAt(0) - 'a';
        res[0] = loc_h;
        res[1] = loc_v;
        return res;
    }

    public String convertLocToString(int i, int j){
        StringBuilder sb = new StringBuilder();
        sb.append((char)('a' + j));
        sb.append(i+1);
        return(sb.toString());
    }
}
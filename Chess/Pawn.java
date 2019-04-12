import java.util.*;

public class Pawn extends Piece {
    public Pawn(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK? 'b':'w';
            sb.append(col);
            sb.append('p');
            return sb.toString();
    }

    public List<String> moves(Board b, String loc) {
            int sign = color == Color.WHITE? 1 : -1;
            List<String> list = new ArrayList<>();
            int[] locs = b.convertLocToInt(loc);
            for(int j = locs[1]- 1; j <= locs[1] + 1; j = j+2){
                String s = b.convertLocToString(locs[0] + sign, j);
                if(b.checkBoardBoundary(locs[0]+sign, j)){
                    if(b.checkValidPawnEat(s, color)){
                        list.add(s);
                    }
                }
            }

            for(int i = 1; i <= 2; i++){
                String s = b.convertLocToString(locs[0]+ sign * i,locs[1]);
                if(b.checkBoardBoundary(locs[0]+sign*i,locs[1])){
                    if(b.checkValidPawnMove(s, loc, color)){
                        list.add(s);
                    }
                }
            }

            if(color == Color.WHITE && locs[0] != 1 && b.checkBoardBoundary(locs[0] + 2,locs[1])){
                String s = b.convertLocToString(locs[0]+2, locs[1]);
                list.remove(s);
            }

            if(color == Color.BLACK && locs[0] != 6 && b.checkBoardBoundary(locs[0] - 2,locs[1])){
                String s= b.convertLocToString(locs[0] - 2, locs[1]);
                list.remove(s);
            }

            return list;
    }

}
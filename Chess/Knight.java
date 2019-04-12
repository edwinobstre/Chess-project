import java.util.*;

public class Knight extends Piece {
    public Knight(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK? 'b':'w';
            sb.append(col);
            sb.append('n');
            return sb.toString();
    }
    public List<String> moves(Board b, String loc) {
            List<String> list = new LinkedList<>();
            int[] locs = b.convertLocToInt(loc);
            int loc_h = locs[0];
            int loc_v = locs[1];
            for(int i = -2; i <= 2; i++){
                for(int j = -2; j <= 2; j++){
                    if(i * j == 2 || i * j == -2 ) {
                        String s = b.convertLocToString(loc_h + i, loc_v + j);
                        if(b.checkBoardBoundary(loc_h + i, loc_v+j)) {
                            if (b.checkValidKnightMove(s, color)) {
                                list.add(s);
                            }
                        }
                    }
                }
            }
            return list;
    }

}
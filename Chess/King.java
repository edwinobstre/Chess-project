import java.util.*;

public class King extends Piece {
    public King(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK ? 'b' : 'w';
            sb.append(col);
            sb.append('k');
            return sb.toString();
    }

    public List<String> moves(Board b, String loc) {
            List<String> list = new ArrayList<>();
            int loc_h = b.convertLocToInt(loc)[0];
            int loc_v = b.convertLocToInt(loc)[1];
            for (int i = loc_h - 1; i <= loc_h + 1; i++) {
                for (int j = loc_v - 1; j <= loc_v + 1; j++) {
                    String s = b.convertLocToString(i, j);
                    if (b.checkBoardBoundary(i, j)) {
                        if (b.checkValidKingMove(s, color)) {
                            list.add(s);
                        }
                    }
                }
            }
            list.remove(loc);
            return list;
    }
}
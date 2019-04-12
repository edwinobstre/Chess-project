import java.util.*;

public class Bishop extends Piece {
    public Bishop(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK? 'b':'w';
            sb.append(col);
            sb.append('b');
            return sb.toString();

    }
    public List<String> moves(Board b, String loc) {
            int[] locs = b.convertLocToInt(loc);
            List<String> list = new ArrayList<>();
            for(int i = 0; i <= 7; i++){
                for(int j = 0; j <= 7; j++){
                    if(Math.abs(i - locs[0]) == Math.abs(j - locs[1])){
                        String s = b.convertLocToString(i, j);
                        if(b.checkValidBishopMove(s, loc, color)){
                                list.add(s);
                        }
                    }
                }
            }
            list.remove(loc);
            return list;
        }


}
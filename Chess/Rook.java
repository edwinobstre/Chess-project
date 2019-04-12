import java.util.*;

public class Rook extends Piece {
    public Rook(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK? 'b':'w';
            sb.append(col);
            sb.append('r');
            return sb.toString();
    }
    public List<String> moves(Board b, String loc) {
            List<String> list = new ArrayList<>();
            int[] locs = b.convertLocToInt(loc);
            for(int i = 0; i <= 7; i++){
                for(int j = 0; j <= 7; j++){
                    if(i == locs[0] || j == locs[1]){
                        String s = b.convertLocToString(i, j);
                        if(b.checkvalidRookMove(s, loc, color)){
                            list.add(s);
                        }
                    }
                }
            }
            list.remove(loc);
            return list;
    }

}
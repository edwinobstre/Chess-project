import java.util.*;

public class Queen extends Piece {
    public Queen(Color c) {
            color = c;
    }
    // implement appropriate methods

    public String toString() {
            StringBuilder sb = new StringBuilder();
            char col = super.color() == Color.BLACK? 'b':'w';
            sb.append(col);
            sb.append('q');
            return sb.toString();
    }
    public List<String> moves(Board b, String loc) {
            List<String> list = new ArrayList<>();
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    String s = b.convertLocToString(i,j);
                    if(b.checkValidQueenMove(s, loc, color)){
                        list.add(s);
                    }
                }
            }
            list.remove(loc);
            return list;
    }

}
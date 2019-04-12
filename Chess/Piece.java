import java.util.*;

abstract public class Piece {
    public Color color;
    private static HashMap<Character, PieceFactory> map = new HashMap<>();
    public static void registerPiece(PieceFactory pf) {
        if(pf != null) {
            map.put(pf.symbol(), pf);
        }else
        {
            throw new UnsupportedOperationException();
        }
    }

    public static Piece createPiece(String name) {
        if(map.containsKey(name.charAt(1)) && name.charAt(0) == 'w' || name.charAt(0) == 'b') {
            PieceFactory pf = map.get(name.charAt(1));
            if (pf != null) {
                Color c = name.charAt(0) == 'b' ? Color.BLACK : Color.WHITE;
                return pf.create(c);
            } else {
                throw new NoSuchElementException();
            }
        }else{
            throw new UnsupportedOperationException();
        }
    }

    public Color color() {
        // You should write code here and just inherit it in
        // subclasses. For this to work, you should know
        // that subclasses can access superclass fields.
            return this.color;
    }

    abstract public String toString();

    abstract public List<String> moves(Board b, String loc);
}
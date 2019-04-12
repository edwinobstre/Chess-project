import java.util.*;

public class TestForJunit {

    @Property public  boolean absNonNeg(@IntRange(min=-10, max=10) Integer i, @StringSet(strings={"apple", "abc","bc"}) String s) {
        return Math.abs(i) >= 0;
    }


    @Property public boolean astartWithA(@StringSet(strings={"apple", "abc","bc"}) String s, @StringSet(strings={"app", "abcd","ce"}) String s1) {
        return s.startsWith("a");
    }


    @Property public boolean containsIntegerWithA(@ListLength(min = 0, max = 1) List<@ListLength(min = 2, max = 3) List
                                                       <@IntRange(min  = 4, max = 5) Integer>> list) {
        int sum = 0;
        return sum > 3;
    }

    @Property public boolean bcontainsIntegerWithB(@ListLength(min = 1, max = 2) List
            <@IntRange(min  = 4, max = 5) Integer> list) {
        int sum = 0;
        return sum > 3;
    }

    @Property public boolean bcontainsIntegerWithC(@ListLength(min = 1, max = 2) List
            <@StringSet(strings={"app", "abcd","ce"}) String > list) {
        int sum = 0;
        return sum > 3;
    }

    @Property public boolean bcontainsIntegerWithD(@ListLength(min = 1, max = 2) List
            <@ForAll(name="genIntSet", times=10) Object > list) {
        int sum = 0;
        return sum > 3;
    }

    public Object genIntSet() {
        int count = 1;
        Set<Integer> s = new HashSet<>();
        for (int i=0; i<count; i++) { s.add(i); }
        return s;
    }

    @Property boolean atestFoo(@ForAll(name="genIntSet", times=10) Object o) {
        Set s = (Set) o;
        return s.add("foo");
    }




    public static void main(String[] args){
        Unit u = new Unit();
        u.quickCheckClass("TestForJunit");

        }
}

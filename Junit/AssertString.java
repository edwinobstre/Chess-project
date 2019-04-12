public class AssertString {
    private String s;

    public AssertString(String s) {
        this.s = s;
    }

    public AssertString isNotNull() {
        if(s.equals(null)) new UnsupportedOperationException();
        return this;
    }

    public AssertString isNull() {
        if(s != null) throw new UnsupportedOperationException();
        return this;
    }

    public AssertString isEqualTo(Object o2) {
        if(s != o2) throw new UnsupportedOperationException();
        return this;
    }

    public AssertString isNotEqualTo(Object o2) {
        if(s == o2) throw new UnsupportedOperationException();
        return this;
    }

    public AssertString startsWith(String s2) {
        if(!s.startsWith(s2)) throw new UnsupportedOperationException();
        return this;
    }

    public AssertString isEmpty() {
        if(s.length() != 0) throw new UnsupportedOperationException();
        return this;
    }

    public AssertString contains(String s2) {
        if(!s.contains(s2)) throw new UnsupportedOperationException();
        return this;
    }
}
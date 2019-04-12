public class AssertObject {
    private Object o;

    public AssertObject(Object o) {
        this.o = o;
    }

    public AssertObject isNotNull() {
        if(o.equals(null)) new UnsupportedOperationException();
        return this;
    }

    public AssertObject isNull() {
        if(o != null) throw new UnsupportedOperationException();
        return this;
    }

    public AssertObject isEqualTo(Object o2) {
        if(o != o2) throw new UnsupportedOperationException();
        return this;
    }

    public AssertObject isNotEqualTo(Object o2) {
        if(o == o2) throw new UnsupportedOperationException();
        return this;
    }

    public AssertObject isInstanceOf(Class c) {
        if(!o.getClass().equals(c)) throw new UnsupportedOperationException();
        return this;
    }
}

package util;

public class Holder<T> {
    public T obj = null;

    @Override
    public int hashCode() {
        return obj == null ? 0 : obj.hashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        assert (this != this.obj);
        if (obj == null)
            return this.obj == null;
        return obj.equals(this.obj);
    }

    @Override
    public String toString() {
        return obj.toString();
    }
}

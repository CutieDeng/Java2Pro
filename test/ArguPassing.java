import util.Holder;

public class ArguPassing {

    private static int getValue() {
        return 21;
    }

    private static void getValue(Holder<Integer> holder) {
        holder.obj = 21;
    }

    public static void main(String[] args) {
        System.out.println(getValue());
        Holder<Integer> integerHolder = new Holder<>();
        getValue(integerHolder);
        System.out.println(integerHolder.obj);
    }

}

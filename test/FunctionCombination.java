import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionCombination {

    public static void main(String[] args) {
        Consumer<Void> consumer = v -> System.out.println("consumer invoke! ");
        Function<Void, Void> function = (a) -> {
            System.out.println("function!"); return null;
        };

    }

}

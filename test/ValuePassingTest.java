import org.junit.Assert;
import org.junit.Test;
import util.Holder;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ValuePassingTest {

    /**
     * 基本数据类型只允许值传递，这意味着在栈中的基本数据类型，你无法直接通过其他线程或异步的方法去修改他！
     */
    @Test
    public void PrimaryValuePassing() {
        final int[] localValue = {2};
        Consumer<Void> plusOne = v -> ++localValue[0];
        Assert.assertEquals(localValue[0], 2);
        plusOne.accept(null);
        Assert.assertEquals(localValue[0], 3);
    }

    @Test
    public void ObjectPassing() {
        Holder<Integer> holder = new Holder<>();
        holder.obj = 2;
        Consumer<Void> decrease = v -> --holder.obj;
        Assert.assertEquals(holder.obj, Integer.valueOf(2));
        decrease.accept(null);
        Assert.assertEquals(holder.obj, Integer.valueOf(1));
        // 你无法改变一个被「捕获」的值的字面量？
        // holder = new Holder<>();
    }

    @Test
    public void ObjectChanging() {
        @SuppressWarnings("WriteOnlyObject") AtomicReference<Holder<Integer>> holder = new AtomicReference<>(new Holder<>());
        Consumer<Void> changeConsumer = v -> holder.set(new Holder<>());

    }

}

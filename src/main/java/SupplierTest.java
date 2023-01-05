import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SupplierTest {

    @Test
    public void test() {

        A a = new A();
        A a1 = doSomething(() -> a);
        B b = new B();
        B b1 = doSomething(() -> b);
    }

    class A {
    }

    class B {
    }

    private <T> T doSomething(Supplier<T> supplier) {
        try {
            login();
//            supplier.
        } finally {
            logout();
        }

        return null;
    }

    private void logout() {

    }

    private void login() {

    }

    @Test
    public void getterSetterTest() {
        PajoA a = new PajoA();
        a.id = "1";

        PajoB b = new PajoB();
//        b.id = a.id;
        setValueIfExist(a::getId, b::setId);

    }

    @Getter
    private static class PajoA {
        String id;
    }

    @Setter
    private static class PajoB {
        String id;
    }

    private <T> void setValueIfExist(Supplier<T> getter, Consumer<T> setter) {
        if (getter.get() != null) {
            setter.accept(getter.get());
        }
    }
}

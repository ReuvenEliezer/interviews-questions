import org.junit.Test;

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
}

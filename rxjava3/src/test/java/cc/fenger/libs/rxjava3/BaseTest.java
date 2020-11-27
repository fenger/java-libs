package cc.fenger.libs.rxjava3;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

public class BaseTest {

    @Test
    public void test01() {
        Flowable.just("Hello world").subscribe(System.out::println);
    }

}

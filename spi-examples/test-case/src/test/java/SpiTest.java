import org.iproute.spi.basic.Welcome;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * SpiTest
 *
 * @author tech@intellij.io
 * @since 2025-04-22
 */
public class SpiTest {

    @Test
    public void testSayHello() {

        ServiceLoader<Welcome> serviceLoader = ServiceLoader.load(Welcome.class);

        for (Welcome welcome : serviceLoader) {
            System.out.println(welcome.sayHello("world"));
        }
    }

}

import com.sun.tools.javac.Main;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicProxyTest {

    @Test
    public void testJdkDynamicProxy() {
        Greeting commonGreeting = new WorldCommonGreeting();
        Greeting proxyGreeting = (Greeting) Proxy.newProxyInstance(Main.class.getClassLoader(),
                new Class[]{Greeting.class},
                new KoreanGreetingProxyHandler(commonGreeting));

        assertEquals("Hello?", commonGreeting.greeting());
        assertEquals("Hello?_안녕하세욥", proxyGreeting.greeting());
    }

    @Test
    public void testCglibDynamicProxyWithMethodInterceptor() {
        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            final WorldCommonGreeting commonGreeting = new WorldCommonGreeting();

            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws InvocationTargetException, IllegalAccessException {
                String invokedResult = (String) method.invoke(commonGreeting, objects);
                return invokedResult + "_안녕하세욥";
            }
        };

        WorldCommonGreeting proxy =
                (WorldCommonGreeting) Enhancer.create(WorldCommonGreeting.class, methodInterceptor);


        assertEquals("Hello?_안녕하세욥", proxy.greeting());
    }
}

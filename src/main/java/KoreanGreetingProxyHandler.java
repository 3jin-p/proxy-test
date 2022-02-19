import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class KoreanGreetingProxyHandler implements InvocationHandler {

    private Object proxyTarget;

    public KoreanGreetingProxyHandler(Object proxyTarget) {
        this.proxyTarget = proxyTarget;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object methodResult = method.invoke(proxyTarget, args);
        if (method.getName().startsWith("greeting")) {
            return methodResult + "_안녕하세욥";
        }
        return methodResult;
    }
}

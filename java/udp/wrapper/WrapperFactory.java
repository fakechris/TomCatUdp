package udp.wrapper;

import java.lang.reflect.Method;
import java.util.Arrays;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class WrapperFactory {
	
	@SuppressWarnings("unchecked")
	public static <S,W> W createWrapper(final S source, final Class<W> wrapperClass) {
	    Enhancer enhancer = new Enhancer();
	    enhancer.setSuperclass(wrapperClass);
	    enhancer.setInterfaces(wrapperClass.getInterfaces());
	    enhancer.setCallback(new MethodInterceptor() {
	    	
	    	@Override
	        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
	            if ("getSource".equals(method.getName()) && Wrapper.class.equals(method.getDeclaringClass())) {
	                return source;
	            }
	            if (Arrays.asList(wrapperClass.getDeclaredMethods()).contains(method)) {
	                return methodProxy.invokeSuper(proxy, args);
	            }
	            return methodProxy.invoke(source, args);
	        }

	    });

	    return (W) enhancer.create();
	}

}

package net.machtalk.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by zhaop on 2018/4/2
 */
public class HelloMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before invoke " + method.getName());
        methodProxy.invokeSuper(o, objects);
        System.out.println("after invoke " + method.getName());
        return objects;
    }

}

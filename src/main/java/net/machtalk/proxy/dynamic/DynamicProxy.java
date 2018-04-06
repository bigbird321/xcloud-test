package net.machtalk.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by zhaop on 2018/4/2
 */
public class DynamicProxy implements InvocationHandler{
    private Object subject;

    public DynamicProxy(Object subject){
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke " + method.getName());
        method.invoke(subject, args);
        System.out.println("after invoke " + method.getName());
        return null;
    }
}

package net.machtalk.proxy.dynamic;

import net.machtalk.proxy.service.IHello;
import net.machtalk.proxy.service.impl.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by zhaop on 2018/4/2
 */
public class Main {

    public static void main(String[] args){
        Hello hello = new Hello();

        InvocationHandler handler = new DynamicProxy(hello);

        IHello helloInterface = (IHello)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                hello.getClass().getInterfaces(), handler);

        helloInterface.sayHello();
    }

}

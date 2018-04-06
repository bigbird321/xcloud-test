package net.machtalk.proxy.staticProxy;

import net.machtalk.proxy.dynamic.DynamicProxy;
import net.machtalk.proxy.service.IHello;
import net.machtalk.proxy.service.impl.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by zhaop on 2018/4/2
 */
public class Main {

    public static void main(String[] args){
        StaticProxy hello = new StaticProxy();
        hello.sayHello();
    }

}

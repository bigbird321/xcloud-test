package net.machtalk.proxy.cglib;

import net.machtalk.proxy.dynamic.DynamicProxy;
import net.machtalk.proxy.service.IHello;
import net.machtalk.proxy.service.impl.Hello;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by zhaop on 2018/4/2
 */
public class Main {

/*    public static <T> T getProxyInstance(HelloMethodInterceptor myProxy, T t){
        Enhancer enhancer = new Enhancer();
        // 将Enhancer中的superclass属性赋值成T
        enhancer.setSuperclass(t.getClass());
        // 将Enhancer中的callbacks属性赋值成myProxy
        enhancer.setCallback(myProxy);
        return (T) enhancer.create();
    }*/

    public static Object getProxyInstance(Class a){
        Enhancer enhancer = new Enhancer();
        // 将Enhancer中的superclass属性赋值成T
        enhancer.setSuperclass(a);
        // 将Enhancer中的callbacks属性赋值成myProxy
        enhancer.setCallback(new HelloMethodInterceptor());
        return enhancer.create();
    }

    public static void main(String[] args){
        /*Enhancer enhancer = new Enhancer();
        //继承被代理类
        enhancer.setSuperclass(Hello.class);
        //设置回调
        enhancer.setCallback(new HelloMethodInterceptor());
        //生成代理对象
        Hello hello = (Hello)enhancer.create();*/

        Hello hello = (Hello)getProxyInstance(Hello.class);
        hello.sayHello();
    }

}

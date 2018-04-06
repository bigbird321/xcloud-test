package net.machtalk.proxy.staticProxy;


import net.machtalk.proxy.service.IHello;
import net.machtalk.proxy.service.impl.Hello;

/**
 * Created by zhaop on 2018/4/2
 */
public class StaticProxy implements IHello {
    private IHello hello = new Hello();

    @Override
    public void sayHello() {
        System.out.println("before invoke sayHello");
        hello.sayHello();
        System.out.println("after invoke sayHello");
    }
}

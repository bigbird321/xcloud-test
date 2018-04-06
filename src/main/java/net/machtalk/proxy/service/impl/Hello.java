package net.machtalk.proxy.service.impl;

import net.machtalk.proxy.service.IHello;

/**
 * Created by zhaop on 2018/4/2
 */
public class Hello implements IHello {
    @Override
    public void sayHello() {
        System.out.println("hello proxy ...");
    }
}

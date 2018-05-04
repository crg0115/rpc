package com.nk.rpc.spring;

import com.google.common.reflect.Reflection;
import com.nk.rpc.invoke.ClientInvokeProxy;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class RpcReference implements FactoryBean {

    private String interfaceName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public Object getObject() throws Exception {
        return Reflection.newProxy(getObjectType(), new ClientInvokeProxy<>());
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return this.getClass().getClassLoader().loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
//        return com.nk.rpc.service.TestService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

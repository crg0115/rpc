package com.nk.rpc.spring;

import com.nk.rpc.context.RpcContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class RpcService implements ApplicationContextAware, InitializingBean {

    private String interfaceName;

    private String ref;

    private ApplicationContext applicationContext;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Object bean = applicationContext.getBean(ref);
        RpcContext.getInstance().getHandlerMap().put(interfaceName, bean);
    }
}

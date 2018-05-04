package com.nk.rpc.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Created by niuyang on 2018/5/4.
 */
public class RpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new RpcReferenceParser());
        registerBeanDefinitionParser("service", new RpcServiceParser());
    }

}

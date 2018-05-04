package com.nk.rpc.service.impl;

import com.nk.rpc.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author Created by niuyang on 2018/5/4.
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

}

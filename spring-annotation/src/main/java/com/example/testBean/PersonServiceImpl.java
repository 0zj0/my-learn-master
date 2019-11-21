package com.example.testBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author zhangjie
 * @date 2019/10/28 11:45
 */
@Service("personService")
public class PersonServiceImpl {

    @Value("service test value")
    private String servcieValue;

}

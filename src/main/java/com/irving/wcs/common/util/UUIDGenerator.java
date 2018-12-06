package com.irving.wcs.common.util;

import java.util.UUID;

/**
 * @Description
 * @Author yuanyc
 * @Date 2018/12/6 8:07 PM
 **/
public class UUIDGenerator {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

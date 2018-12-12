package com.irving.wcs.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description string 解析工具类
 * @Author yuanyc
 * @Date 2018/12/10 7:48 PM
 **/
public class StringUtil {

    /**
     * str to intArr
     * @Author yuanyc
     * @Date 7:56 PM 2018/12/10
     * @Param delimeter 分隔符
     * @Return int[]
     **/
    public static int[] toIntArr(String str, String delimeter) {
        if (StringUtils.isNoneBlank(str)) {
            String[] strArr = str.split(delimeter);
            int length = strArr.length;
            int[] intArr = new int[length];
            for (int i=0; i<length; i++) {
                intArr[i] = Integer.parseInt(strArr[i]);
            }
            return intArr;
        }
        return null;
    }
}

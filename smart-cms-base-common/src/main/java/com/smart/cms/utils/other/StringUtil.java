package com.smart.cms.utils.other;

import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @Author: huilai.huang
 * @Date: 2021/5/21 18:12
 * @Version: 1.0
 */
public class StringUtil {
    public static String firstCharToUpper(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] - 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    public static String[] toStrArray(String split, String str) {
        return StringUtils.isBlank(str) ? new String[0] : str.split(split);
    }
}

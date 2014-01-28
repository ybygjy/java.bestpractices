package org.ybygjy.ui.comp;

import java.util.Map;

/**
 * 定义获组组件中表单项的值
 * @author WangYanCheng
 * @version 2012-9-10
 */
public interface ComponentValue {
    /**
     * 取值集
     * @return rtnMap {Key:元素名称;Value:元素值}
     */
    Map<String, String> getValues();
}

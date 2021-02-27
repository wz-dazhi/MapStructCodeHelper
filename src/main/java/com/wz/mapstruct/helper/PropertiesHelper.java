package com.wz.mapstruct.helper;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

/**
 * @package: com.wz.com.wz.mapstruct.util
 * @className: PropertiesHelper
 * @description:
 * @author: zhi
 * @date: 2021/1/27
 * @version: 1.0
 */
public final class PropertiesHelper {
    private static PropertiesComponent propertiesComponent;

    private PropertiesHelper() {
    }

    static void init(Project p) {
        propertiesComponent = PropertiesComponent.getInstance(p);
    }

    public static void setValue(String key, String value) {
        propertiesComponent.setValue(key, value);
    }

    public static String getValue(String key) {
        return propertiesComponent.getValue(key);
    }

    public static String[] getValues(String key) {
        return propertiesComponent.getValues(key);
    }

}

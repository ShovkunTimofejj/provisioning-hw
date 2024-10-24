package com.voxloud.provisioning.util;

import java.util.*;

public class PropertyFileUtils {
    public static Map<String, Object> processProperties(Properties props, String prefix) {
        Map<String, Object> propertiesMap = new HashMap<>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            if (keyStr.startsWith(prefix)) {
                Object value = props.getProperty(keyStr);
                if (value instanceof String && ((String) value).contains(Delimiter.LIST_DELIMETER_PROP_FILE.getValue())) {
                    List<String> convertedListType = Arrays.asList(((String) value).split(Delimiter.LIST_DELIMETER_PROP_FILE.getValue()));
                    value = convertedListType;
                }
                propertiesMap.put(keyStr.replace(prefix + ".", ""), value);
            }
        }
        return propertiesMap;
    }

    public static void processOverrideFragment(Map<String, Object> propertyWithNewValue, Map<String, Object> existingProperties) {
        for (Object key : propertyWithNewValue.keySet()) {
            String keyStr = key.toString();
            Object value = propertyWithNewValue.get(keyStr);

            if (existingProperties.containsKey(keyStr)) {
                if (value instanceof String && ((String) value).contains(Delimiter.LIST_DELIMETER_PROP_FILE.getValue())) {
                    List<String> convertedListType = Arrays.asList(((String) value).split(Delimiter.LIST_DELIMETER_PROP_FILE.getValue()));
                    value = convertedListType;
                }
                existingProperties.replace(keyStr, value);
            }
        }
    }
}

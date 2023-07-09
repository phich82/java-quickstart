package com.quickstart;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public abstract class Ref1 {
    private static List<String> fieldsSecure = Arrays.asList();
    /** Use: Refl.<TargetClass>get(myObject,"x.y[0].z"); */
    public static<T> T get(Object obj, String fieldPath) {
        return (T) getValue(obj, fieldPath);
    }

    public static void setSecureFields(List<String> fields) {
        fieldsSecure = fields;
    }

    public static Object getValue(Object obj, String fieldPath) {
        String[] fieldNames = fieldPath.split("[\\.\\[\\]]");
        String success = "";
        Object res = obj;
        for (String fieldName : fieldNames) {
            if (fieldName.isEmpty()) {
                continue;
            }
            int index = toIndex(fieldName);
            if (index >= 0) {
                try {
                    res = ((Object[])res)[index];
                } catch (ClassCastException e) {
                    throw new RuntimeException("cannot cast " + res.getClass() + " object " + res + " to array, path:" + success, e);
                } catch (IndexOutOfBoundsException e) {
                    throw new RuntimeException("bad index " + index + ", array size " + ((Object[])res).length + " object " + res + ", path:" + success, e);
                }
            } else {
                Field field = getField(res.getClass(), fieldName);
                field.setAccessible(true);
                try {
                    Object value = field.get(res);
                    if (fieldsSecure.contains(fieldName) && value != null && !value.equals("")) {
                        field.set(res, "*****");
                    }
                    res = field.get(res);
                } catch (Exception e) {
                    throw new RuntimeException("cannot get value of ["+fieldName+"] from "+res.getClass()+" object "+res +", path:"+success, e);
                }
            }
            success += fieldName + ".";
        }
        return res;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Class<?> tmpClass = clazz;
        do {
            try {
                return tmpClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                tmpClass = tmpClass.getSuperclass();
            }
        } while (tmpClass != null);

        throw new RuntimeException("Field '" + fieldName + "' not found in class " + clazz);
    }

    private static int toIndex(String s) {
        int idx = -1;
        if (s != null && s.length() > 0 && Character.isDigit(s.charAt(0))) {
            try {
                idx = Integer.parseInt(s);
                if (idx < 0) {
                    idx = -1;
                }
            } catch (Exception e) {
                idx = -1;
            }
        }
        return idx;
    }

}

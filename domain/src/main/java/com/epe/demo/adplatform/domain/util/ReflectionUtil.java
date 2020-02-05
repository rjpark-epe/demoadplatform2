package com.epe.demo.adplatform.domain.util;


import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.data.util.ReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.ReflectionUtils.handleReflectionException;

@UtilityClass
public class ReflectionUtil {

    private static final Map<Class<?>,Map<String,Field>> REFLECTION_CACHE_MAP = new ConcurrentHashMap<Class<?>, Map<String,Field>>();

    public static Map<String, Field> getAllDeclaredFieldMap(Class<?> clazz){
        Map<String,Field> fieldMap = REFLECTION_CACHE_MAP.get(clazz);
        if(fieldMap==null){
            //fieldMap = Maps.newHashMap();
            fieldMap = Maps.newLinkedHashMap();
            List<Field> fields = getAllDeclaredFields(clazz);
            for(Field each : fields){
                each.setAccessible(true);
                fieldMap.put(each.getName(), each);
            }
            REFLECTION_CACHE_MAP.put(clazz, fieldMap);
        }
        return fieldMap;
    }

    public static List<Field> getAllDeclaredFields(Class<?> clazz){
        List<Field> fields = new ArrayList<Field>();
        List<Class<?>> classes = ClassUtils.getAllSuperclasses(clazz);
        CollectionUtils.addAll(fields, clazz.getDeclaredFields());
        for(Class<?> each : classes){
            if(each==Object.class) continue;
            CollectionUtils.addAll(fields, each.getDeclaredFields());
        }
        Iterator<Field> i = fields.iterator();
        while(i.hasNext()){
            Field field = i.next();
            if(Modifier.isStatic(field.getModifiers())) i.remove();
            else field.setAccessible(true);
        }
        return fields;
    }

    @Nullable
    public static Object getField(Field field, @Nullable Object target) {
        try {
            return field.get(target);
        }
        catch (IllegalAccessException ex) {
            handleReflectionException(ex);
            throw new IllegalStateException(
                    "Unexpected reflection exception - " + ex.getClass().getName() + ": " + ex.getMessage());
        }
    }

}

package com.epe.demo.adplatform.domain.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CompareUtil {
    public static <T> boolean isEqualIgnoreNullEmptyIncludeFilds(List<T> aList, List<T> bList, Collection<String> includes, Collection<String> excludes){
        if(aList.size() != bList.size()) return false;
        for(int i=0;i<aList.size();i++){
            T a = aList.get(i);
            T b = bList.get(i);
            boolean eq = CompareUtil.isEqualIgnoreNullEmptyIncludeFilds(a, b, includes, excludes);
            if(!eq) return false;
        }
        return true;
    }

    public static <T> boolean isEqualIgnoreNullEmptyIncludeFilds(T a, T b, Collection<String> includes, Collection<String> excludes){
        Map<String, Field> map = ReflectionUtil.getAllDeclaredFieldMap(a.getClass());
        if(isEmpty(includes)) includes = map.keySet();
        if(!isEmpty(excludes)) includes.removeAll(excludes);
        for(String key : includes){
            Field field = map.get(key);
            Preconditions.checkNotNull(field, "{0}에 {1}로 field를 찾을 수 없습니다.", a.getClass(),key);
            Object aValue = ReflectionUtil.getField(field, a);
            Object bValue = ReflectionUtil.getField(field, b);
            Class<?> type = field.getType();
            if(String.class.isAssignableFrom(type)){
                boolean isEqual = isEqualIgnoreNullEmpty((String)aValue,(String)bValue);
                if(!isEqual) return false;
            }else{
                boolean isEqual = isEqualIgnoreNull(aValue,bValue);
                if(!isEqual) return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(Collection<?> list){
        if(list==null) return true;
        return list.isEmpty();
    }

    /** null safe한 isEmpty() */
    public static <T> boolean isEmpty(T[] array) {
        if (array == null || array.length == 0) return true;
        return false;
    }

    /** 널과 Empty는 동등하게 취급.
     *  둘다 널이면 true를 리턴 */
    public static boolean isEqualIgnoreNullEmpty(String a,String b){
        boolean aEmpty = Strings.isNullOrEmpty(a);
        boolean bEmpty = Strings.isNullOrEmpty(b);
        if(aEmpty && bEmpty) return true;
        else if(!aEmpty && !bEmpty) return a.equals(b);
        else  return false;
    }

    /**
     * 둘다 널이면 true를 리턴
     * oracleString 이 true 이면 null과 공백문자를 같은것으로 취급한다.
     *  */
    public static <T> boolean isEqualIgnoreNull(T a,T b,boolean oracleString){
        boolean aEmpty = a == null;
        if(a instanceof String && oracleString) aEmpty = Strings.isNullOrEmpty((String)a);
        boolean bEmpty = b == null;
        if(b instanceof String && oracleString) bEmpty = Strings.isNullOrEmpty((String)b);
        if(aEmpty && bEmpty) return true;
        else if(!aEmpty && !bEmpty) return a.equals(b);
        else  return false;
    }

    /**  둘다 널이면 true를 리턴 */
    public static <T> boolean isEqualIgnoreNull(T a,T b){
        return isEqualIgnoreNull(a,b,false);
    }

    public static <T extends Comparable> int compareTo(T a,T b,boolean asc){
        //추가~ null이면 작은걸로 기록..
        if(a==null && b==null) return 0;

        if(a==null && b!=null) return 1;
        if(a!=null && b==null) return -1;

        return a.compareTo(b) * (asc ? 1 : -1);
    }
}
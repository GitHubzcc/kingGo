package com.go.util.sysUtil;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.collection.AbstractPersistentCollection;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by eriz on 9/2/2015.
 */
public final class JsonMessage implements Serializable {
    /**
     * 用户未登录
     */
    public static final JsonMessage USER_NOT_LOGIN = JsonMessage.error(2, "用户未登录");
    /**
     * 未知错误
     */
    public static final JsonMessage UNKNOWN_ERROR = JsonMessage.error(4, "未知错误");

    /**
     * 成功代码
     */
    public static final int CODE_SUCCESS = 0;

    /**
     * 基本错误代码
     */
    public static final int CODE_ERROR = 1;

    /**
     * 返回码
     */
    @JsonProperty
    private int code;

    /**
     * 错误信息
     */
    @JsonProperty
    private Object errmsg;

    /**
     * 返回数据
     */
    @JsonProperty
    private Object data;

    // 禁止创建对象
    private JsonMessage(int code, Object data) {
        this.code = code;
        if (code == 0) {
            this.data = data;
        } else {
            this.errmsg = data;
        }
    }

    /**
     * 转换Map为List
     *
     * @param map       待转换map
     * @param keyProp   转换后key对应的参数名
     * @param valueProp 转换后value对应的参数名
     * @return
     */
    public static List<Map<String, Object>> transformMapToList(Map map, String keyProp, String valueProp) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        if (map != null) {
            if (keyProp == null) {
                keyProp = "key";
            }
            if (valueProp == null) {
                valueProp = "value";
            }

            for (Object key : map.keySet()) {
                Map<String, Object> itemMap = new HashMap<String, Object>();
                itemMap.put(keyProp, key);
                itemMap.put(valueProp, map.get(key));
                list.add(itemMap);
            }
        }

        return list;
    }

    /**
     * /**
     * 成功消息(code=0)<br>
     * 单参数返回格式为:{code:0,data:<Object>}<br>
     * 多参数格式:参数名1,参数值1,参数名2,参数值2...<br>
     * 返回Json格式{code:0,data:{'参数1':参数值1(Object),'参数2':参数值2(Object)}}
     *
     * @param data 数据组
     * @return
     */
    public static JsonMessage success(Object... data) {
        return message(CODE_SUCCESS, data);
    }

    /**
     * 失败消息(code=1)<br>
     * 单参数返回格式为:{code:1,data:<Object>}<br>
     * 多参数格式:参数名1,参数值1,参数名2,参数值2...<br>
     * 返回Json格式{code:1,data:{'参数1':参数值1(Object),'参数2':参数值2(Object)}}
     *
     * @param data 失败消息
     * @return
     */
    public static JsonMessage error(Object data) {
        return message(CODE_ERROR, data);
    }

    /**
     * 失败消息(code=1)<br>
     * 单参数返回格式为:{code:1,data:<Object>}<br>
     * 多参数格式:参数名1,参数值1,参数名2,参数值2...<br>
     * 返回Json格式{code:1,data:{'参数1':参数值1(Object),'参数2':参数值2(Object)}}
     *
     * @param data 失败消息
     * @return
     */
    public static JsonMessage error(int code, Object data) {
        return message(code, data);
    }

    /**
     * 通用消息消息<br>
     * 单参数返回格式为:{code:code(int),data:<Object>}<br>
     * 多参数格式:参数名1,参数值1,参数名2,参数值2...<br>
     * 返回Json格式{code:code(int),data:{'参数1':参数值1(Object),'参数2':参数值2(Object)}}
     *
     * @param code 返回码
     * @param data 返回数据,支持多数据返回,多数据参数格式:参数名1(String),参数值1(Object),参数名2(String),参数值2(Object)...
     * @return
     */
    private static JsonMessage message(int code, Object... data) {
        if (data == null) data = new Object[]{new Object()};
        if (data.length == 1) {
            // 单一数据
            return new JsonMessage(code, data[0]);
        } else {
            Map<String, Object> map = new LinkedHashMap<String, Object>();

            for (int i = 0; i < data.length; i++) {
                String key = data[i++].toString();
                map.put(key, data[i]);
            }

            return new JsonMessage(code, map);
        }
    }

    /**
     * 优化对象数据
     *
     * @param bean       对象
     * @param properties 输出字段
     * @return
     */
    public static Object optimiseObj(Object bean, String... properties) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (bean == null) {
            //TODO 前端需要null
            return null;
            // TODO 前端需要空对象
//            return map;
        }
        // 判断是否列表
        if (isCollection(bean.getClass())) {
            Collection list = (Collection) bean;
            return optimiseList(list, properties);
        }
        // 2. 循环每一个属性
        for (String property : properties) {
            if (StringUtils.isEmpty(property)) {
                // 跳过空属性
                continue;
            }
            // 是否递归
            boolean isRecursive = StringUtils.startsWith(property, "R");
            if (isRecursive) {
                property = property.replaceFirst("R", "");
            } else {
                isRecursive = StringUtils.contains(property, ".R");
                if (isRecursive) {
                    property = property.replace(".R", ".");
                }
            }
            // 处理级联属性
            String[] names = property.split("\\.");
            Map<String, Object> tmp = map;
            Map<String, Object> field;
            // 3. 循环每一个级联属性
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < names.length; i++) {
                if (i == (names.length - 1)) {
                    // 最后一个field
                    Object obj = getPropertyValue(bean, property);
                    Class type = getPropertyType(bean, property);
                    if (isRecursive) {
                        if (isCollection(type)) {
                            tmp.put(names[i], optimiseList((Collection) obj, properties));
                        } else {
                            tmp.put(names[i], optimiseObj(obj, properties));
                        }
                    } else {
                        tmp.put(names[i], getPropertyValue(bean, property));
                    }
                } else {
                    field = (Map<String, Object>) tmp.get(names[i]);
                    if (field == null) {
                        field = new HashMap<String, Object>();
                        tmp.put(names[i], field);
                    }
                    prefix.append(names[i]);
                    // 处理list
                    Class type = getPropertyType(bean, prefix.toString());
                    Object obj = getPropertyObject(bean, prefix.toString());
                    prefix.append(".");//必须在这里添加
                    if (obj == null) {
                        tmp.put(names[i], null);
                        break;
                    } else if (isCollection(type)) {
                        String suffix = property.replace(prefix.toString(), "");
                        tmp.put(names[i], optimiseList((Collection) getPropertyObject(bean, prefix.substring(0, prefix.length() - 1)), suffix.split("#")));
                        break;
                    }

                    tmp = field; // 必须在处理list之后修改
                }
            }
        }

        return map;
    }

    /**
     * 优化列表数据
     *
     * @param list       列表
     * @param properties 需要的属性
     * @return
     */
    private static Object optimiseList(Collection list, String... properties) {
        List<Object> newList = new ArrayList<Object>();
        if (list != null && properties != null) {
            // 1. 循环每一个list item
            for (Object bean : list) {
                newList.add(optimiseObj(bean, properties));
            }
        }

        return newList;
    }

    /**
     * 反射获取字段值,支持级联
     *
     * @param bean
     * @param property
     * @return
     */

    private static Object getPropertyValue(Object bean, String property) {
        try {
            return PropertyUtils.getProperty(bean, property);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 获取属性类型,支持级联
     *
     * @param bean
     * @param property
     * @return
     */
    private static Class getPropertyType(Object bean, String property) {
        try {
            String[] names = property.split("\\.");
            Object obj = bean;
            Method method = null;
            for (String name : names) {
                method = obj.getClass().getMethod("get" + toUpperCaseFirstOne(name));
                obj = method.invoke(obj);
            }

            return method.getReturnType();
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 获取属性对象,支持级联
     *
     * @param bean
     * @param property
     * @return
     */
    private static Object getPropertyObject(Object bean, String property) {
        try {
            String[] names = property.split("\\.");
            Object obj = bean;
            Method method;
            for (String name : names) {
                method = obj.getClass().getMethod("get" + toUpperCaseFirstOne(name));
                obj = method.invoke(obj);
            }

            return obj;
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 判断类型是否是集合
     *
     * @param type
     * @return
     */


    private static boolean isCollection(Class type) {
//        System.out.println(type);
        while (type != null && !Object.class.equals(type)) {
            Class[] classes = type.getInterfaces();
            for (Class c : classes) {
                if (Collection.class.equals(c)) {
                    return true;
                }
            }

            if (AbstractCollection.class.equals(type) || AbstractPersistentCollection.class.equals(type)) {
                return true;
            } else {
                type = type.getSuperclass();
            }
        }

        return false;
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    private static String toUpperCaseFirstOne(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }

        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 获取返回码
     *
     * @return 返回码
     */
    @JSONField
    public int getCode() {
        return code;
    }

    /**
     * 设置返回码
     *
     * @param code 返回码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取出错信息
     *
     * @return 出错信息
     */
    @JSONField
    public Object getErrmsg() {
        return errmsg;
    }

    /**
     * 设置出错信息
     *
     * @param errmsg 出错信息
     */
    public void setErrmsg(Object errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * 获取返回数据
     *
     * @return 返回数据
     */
    @JSONField
    public Object getData() {
        return data;
    }

    /**
     * 设置返回数据
     *
     * @param data 返回数据
     */
    public void setData(Object data) {
        this.data = data;
    }
}
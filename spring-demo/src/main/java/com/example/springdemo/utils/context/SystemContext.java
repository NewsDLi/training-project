//package com.example.springdemo.utils.context;
//
//import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
//import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class SystemContext {
//    private static final String COMPANY_ID_KEY = "companyId_";
//    private static final String USER_ID = "userId_";
//    private static final String USER_NAME = "userName_";
//    private static final String LOCALE = "locale_";
//    private static final transient HystrixRequestVariableDefault<ConcurrentHashMap<String, String>> hystrixContextMap = new HystrixRequestVariableDefault<>();
//    private static final int MAX_SIZE = 200;
//
//    public SystemContext() {
//    }
//
//    private static void init() {
//        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
//            HystrixRequestContext.initializeContext();
//        }
//
//    }
//
//    public static ConcurrentHashMap<String, String> getContextMap() {
//        init();
//        return hystrixContextMap.get();
//    }
//
//    public static void setContextMap(ConcurrentHashMap<String, String> contextMap) {
//        init();
//        hystrixContextMap.set(contextMap);
//    }
//
//    /**
//     * @deprecated use {@link SystemContext#getNullable(String)}
//     */
//    @Deprecated
//    public static String get(String key) {
//        Map<String, String> contextMap = getContextMap();
//        return contextMap == null ? null : contextMap.get(key);
//    }
//
//    public static String getNullable(String key) {
//        if (!HystrixRequestContext.isCurrentThreadInitialized()) {
//            return null;
//        }
//        ConcurrentHashMap<String, String> kv = hystrixContextMap.get();
//        if (kv == null) {
//            return null;
//        }
//        return kv.get(key);
//    }
//
//    public static void put(String key, String value) {
//        if (key != null && value != null) {
//            if (key.length() > MAX_SIZE) {
//                throw new RuntimeException("key is more than " + MAX_SIZE + ", i can't put it into the context map");
//            } else if (value.length() > MAX_SIZE) {
//                throw new RuntimeException("value is more than " + MAX_SIZE + ", i can't put it into the context map");
//            } else {
//                ConcurrentHashMap<String, String> contextMap = getContextMap();
//                if (contextMap == null) {
//                    contextMap = new ConcurrentHashMap<>();
//                    setContextMap(contextMap);
//                }
//
//                int MAX_CAPACITY = 20;
//                if (contextMap.size() > MAX_CAPACITY) {
//                    throw new RuntimeException("the context map is full, can't put anything");
//                } else {
//                    hystrixContextMap.get().put(key, value);
//                    contextMap.put(key, value);
//                }
//            }
//        } else {
//            throw new RuntimeException("key:" + key + " or value:" + value + " is null,i can't put it into the context map");
//        }
//    }
//
//    /**
//     * @deprecated use {@link SystemContext#getCompanyIdNullable()}
//     */
//    @Deprecated
//    public static Long getCompanyId() {
//        String companyId = get(COMPANY_ID_KEY);
//        if (companyId == null) {
//            return null;
//        } else {
//            return !"null".equalsIgnoreCase(companyId) && !"".equals(companyId) ? Long.valueOf(companyId) : null;
//        }
//    }
//
//    public static Long getCompanyIdNullable() {
//        String companyId = getNullable(COMPANY_ID_KEY);
//        if (companyId == null) {
//            return null;
//        } else {
//            return !"null".equalsIgnoreCase(companyId) && !"".equals(companyId) ? Long.valueOf(companyId) : null;
//        }
//    }
//
//    public static Long getDefaultCompanyId() {
//        return -1L;
//    }
//
//    public static void setCompanyId(Long companyId) {
//        if (companyId != null) {
//            put(COMPANY_ID_KEY, companyId + "");
//        }
//
//    }
//
//    /**
//     * @deprecated use {@link SystemContext#getLocaleNullable()}
//     */
//    @Deprecated
//    public static String getLocale() {
//        return get(LOCALE);
//    }
//
//    public static String getLocaleNullable() {
//        return getNullable(LOCALE);
//    }
//
//    public static void setLocale(String locale) {
//        put(LOCALE, locale);
//    }
//
//    public static void setUserId(Long userId) {
//        if (userId != null) {
//            put(USER_ID, userId + "");
//        }
//
//    }
//
//    /**
//     * @deprecated use {@link SystemContext#getUserIdNullable()}
//     */
//    @Deprecated
//    public static Long getUserId() {
//        String userId = get(USER_ID);
//        return userId != null && !"null".equalsIgnoreCase(userId.trim()) ? Long.valueOf(userId) : null;
//    }
//
//    public static Long getUserIdNullable() {
//        String userId = getNullable(USER_ID);
//        return userId != null && !"null".equalsIgnoreCase(userId.trim()) ? Long.valueOf(userId) : null;
//    }
//
//    public static void setUserName(String userName) {
//        if (userName != null) {
//            if (userName.length() > MAX_SIZE) {
//                put(USER_NAME, userName.substring(0, MAX_SIZE));
//            } else {
//                put(USER_NAME, userName);
//            }
//        }
//
//    }
//
//    /**
//     * @deprecated use {@link SystemContext#getUserNameNullable()}
//     */
//    @Deprecated
//    public static String getUserName() {
//        return get(USER_NAME);
//    }
//
//    public static String getUserNameNullable() {
//        return getNullable(USER_NAME);
//    }
//
//    public static void clean() {
//        if (HystrixRequestContext.isCurrentThreadInitialized()) {
//            HystrixRequestContext.getContextForCurrentThread().shutdown();
//        }
//
//        hystrixContextMap.remove();
//    }
//}

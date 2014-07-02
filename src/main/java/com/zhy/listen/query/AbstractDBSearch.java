package com.zhy.listen.query;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.zhy.listen.bean.Paging;

/**
 * db 查询
 * 
 * @author zhanghongyan
 * 
 */
public abstract class AbstractDBSearch<T extends Paging> implements ApplicationContextAware{

    private ApplicationContext applicationContext;
    
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }
    
    protected Paging<T> findByPaging(T t, String methodName, Object... args) { 
        String className = t.getClass().getSimpleName().toLowerCase();
        try {
            Object o = applicationContext.getBean(className + "Service");
            Method method = o.getClass().getDeclaredMethod(methodName, t.getClass());
            List<T> list = (List<T>) method.invoke(o, args);
            Method countMethod = o.getClass().getDeclaredMethod(methodName + "Count", t.getClass());
            int count = (Integer) countMethod.invoke(o, args);
            return new Paging<T>(count, t.getPage(), t.getPageSize(), list);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return new Paging<T>();
    }
    
}

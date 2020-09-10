package com.hxzy.config;


import com.hxzy.entity.hr.Employee;

/**
 * 得到当前登录的员工
 */
public class CurrentEmployee {
    private final static ThreadLocal<Employee>  THREAD_LOCAL=new ThreadLocal<Employee>();

    /**
     * 赋值
     * @param employee
     */
    public static void set(Employee employee){
        THREAD_LOCAL.set(employee);
    }

    /**
     * 取值
     * @return
     */
    public static Employee get(){
        return THREAD_LOCAL.get();
    }

    /**
     * 移出，防止本地线程池内存溢出
     */
    public static void remove(){
        if(THREAD_LOCAL.get()!=null){
            THREAD_LOCAL.remove();
        }
    }

}

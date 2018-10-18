package xzzb.com.myeventbus;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventBus {
    private static final String TAG = "EventBus";
    //存放Context 和我们自己注解的方法的的集合，一对多
    private HashMap<Object, ArrayList<Method>> storageMap = new HashMap<>();
    //装载有注解的方法
    private ArrayList<Method> subscribemethodList;


    private EventBus() {
    }


    private static EventBus instance = null;

    public static EventBus getDefault() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    /**
     * @author Administrator
     * @time 2018/10/10  20:49
     * @describe 注册
     */
    void register(Context context) {
        //我们把注册的context作为key查找当前页面我们注解的方法
        subscribemethodList = storageMap.get(context);
        if (subscribemethodList == null) {
            subscribemethodList = new ArrayList<>();
        } else {
            subscribemethodList.clear();
        }
        //拿到类的所有方法
        Method[] methodsList = context.getClass().getMethods();
        //遍历方法
        for (Method method : methodsList) {
            //方法上面是否有我们特定的注解，如果有就返回true，不然就为false
            boolean annotationPresent = method.isAnnotationPresent(Subscribe.class);
            if (annotationPresent) {
                subscribemethodList.add(method);
                storageMap.put(context, subscribemethodList);
            }
        }


    }

    /**
     * @author Administrator
     * @time 2018/10/10  20:49
     * @describe 取消注册方法 避免内存泄露了
     */
    void unregister(Context context) {
        storageMap.remove(context);
    }


    public void post(Object event) {
        //事件匹配
        matchingCheck(event);
    }

    /**
     * @author Administrator
     * @time 2018/10/10  20:52
     * @describe 根据发送的事件进行页面事件匹配
     */
    private void matchingCheck(Object event) {
        //遍历我们缓存的context和符合要求的方法 对比参数
        Iterator<Map.Entry<Object, ArrayList<Method>>> iterator = storageMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, ArrayList<Method>> next = iterator.next();
            //当前遍历的类
            Object reflexClass = next.getKey();
            //获取我们当前Context里面的自定义注解方法
            ArrayList<Method> value = next.getValue();
            //反射调用
            for (Method method : value) {
                //方法参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                //只有一个参数，而且参数是我们的事件类型
                if (parameterTypes.length == 1 && parameterTypes[0].getName().equals(event.getClass().getName())) {
                    //反射调用
                    reflectionCall(reflexClass, method, event);
                }
            }

        }
    }

    /**
     * @author Administrator
     * @time 2018/10/10  20:56
     * @describe 反射调用
     */
    private void reflectionCall(Object reflexClass, Method method, Object event) {

        try {
            method.invoke(reflexClass, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}

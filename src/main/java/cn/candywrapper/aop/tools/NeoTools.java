package cn.candywrapper.aop.tools;

import cn.candywrapper.aop.interceptor.CommonCglibAdviceInterceptorFactory;
import cn.candywrapper.aop.interceptor.CommonJdkAdviceInterceptorFactory;
import cn.candywrapper.aop.proxy.CglibAopProxy;
import cn.candywrapper.aop.proxy.JdkDynamicAopProxy;

public class NeoTools {
	public static Object neoForInterface(Class<?> clazz) throws InstantiationException, IllegalAccessException{
		CommonJdkAdviceInterceptorFactory factory = new CommonJdkAdviceInterceptorFactory();
		JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(factory);
		Object obj = clazz.newInstance();
		return proxy.getProxy(obj);
	}
	
	public static Object neoForClass(Class<?> clazz) throws InstantiationException, IllegalAccessException{
		CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		Object obj = clazz.newInstance();
		return proxy.getProxy(obj);
	}
}

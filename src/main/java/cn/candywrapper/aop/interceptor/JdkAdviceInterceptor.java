package cn.candywrapper.aop.interceptor;

import java.lang.reflect.Method;
import cn.candywrapper.aop.advice.AdviceChain;

public interface JdkAdviceInterceptor{
	public void addAdviceChain(AdviceChain chain);
	public Object intercept(Object obj, Method method, Object[] args) throws Throwable;
}

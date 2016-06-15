package cn.candywrapper.aop.interceptor;

import cn.candywrapper.aop.advice.AdviceChain;
import net.sf.cglib.proxy.MethodInterceptor;

public interface CglibAdviceInterceptor extends MethodInterceptor{
	public void addAdviceChain(AdviceChain chain);
}

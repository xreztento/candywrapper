package cn.candywrapper.aop.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import cn.candywrapper.aop.advice.AdviceChain;
import cn.candywrapper.aop.advice.AdviceNode;

public class CommonJdkAdviceInterceptor implements JdkAdviceInterceptor{
	
	protected AdviceChain chain = null;
	
	@Override
	public void addAdviceChain(AdviceChain chain){
		this.chain = chain;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args) throws Throwable {
		
		Object object = null;
		//利用AdviceChain按顺序执行通知和原方法
		if(chain != null){
			for(AdviceNode node : chain.getBeforeList()){
				node.getMethod().invoke(node.getAdvice(), method, args, obj);
			}
			
			try{
				object = chain.getMain().getMethod().invoke(obj, args);
			} catch(InvocationTargetException e){
				
				for(AdviceNode node : chain.getThrowsList()){
					node.getMethod().invoke(node.getAdvice(), e.getTargetException());
				}
			}
			
			for(AdviceNode node : chain.getAfterList()){
				node.getMethod().invoke(node.getAdvice(), object, method, args, obj);
			}
		} else {
			object = chain.getMain().getMethod().invoke(obj, args);
		}
		
		return object;
	}

}

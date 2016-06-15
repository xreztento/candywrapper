package cn.candywrapper.aop.interceptor;

import java.lang.reflect.Method;
import cn.candywrapper.aop.advice.AdviceChain;
import cn.candywrapper.aop.advice.AdviceNode;
import net.sf.cglib.proxy.MethodProxy;


public class CommonCglibAdviceInterceptor implements CglibAdviceInterceptor{
	protected AdviceChain chain = null;
	
	@Override
	public void addAdviceChain(AdviceChain chain){
		this.chain = chain;
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object object = null;
		if( chain != null){
			for(AdviceNode node : chain.getBeforeList()){
				node.getMethod().invoke(node.getAdvice(), method, args, obj);
			}
			
			try {
				
				object = proxy.invokeSuper(obj, args); 

			} catch(Exception e){
				
				for(AdviceNode node : chain.getThrowsList()){
					node.getMethod().invoke(node.getAdvice(), e);
				}
			}
			
			for(AdviceNode node : chain.getAfterList()) {
				node.getMethod().invoke(node.getAdvice(), object, method, args, obj);
			}
		} else {
			object = proxy.invokeSuper(obj, args); 
		}
		
		return object;
	}

}

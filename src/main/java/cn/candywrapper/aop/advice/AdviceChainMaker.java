package cn.candywrapper.aop.advice;

import java.lang.reflect.Method;

import cn.candywrapper.aop.AfterAdvice;
import cn.candywrapper.aop.BeforeAdvice;
import cn.candywrapper.aop.ThrowsAdvice;

/**
 * 实现生成一个通知链
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public class AdviceChainMaker {
	private AdviceChain chain = null;
	
	public AdviceChainMaker(AdviceChain chain){
		this.chain = chain;
	}
	
	public void add(Class<?> advice) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException{
		
		if("BeforeAdvice".equals(advice.getInterfaces()[0].getSimpleName())){
			Method before = advice.getMethod("before", new Class<?>[]{Method.class, Object[].class, Object.class});
			Object obj = advice.newInstance();
			AdviceNode node = new AdviceNode();
			node.setAdvice((BeforeAdvice)obj);
			node.setMethod(before);
			chain.addBeforeMethod(node);
			
		} else if("AfterAdvice".equals(advice.getInterfaces()[0].getSimpleName())){
			Method after = advice.getMethod("afterReturning", new Class<?>[]{Object.class, Method.class, Object[].class, Object.class});
			Object obj = advice.newInstance();
			AdviceNode node = new AdviceNode();
			node.setAdvice((AfterAdvice)obj);
			node.setMethod(after);
			chain.addAfterMethod(node);				
			
		} else if("ThrowsAdvice".equals(advice.getInterfaces()[0].getSimpleName())){
			Method after = advice.getMethod("afterThrowing", new Class<?>[]{Throwable.class});
			Object obj = advice.newInstance();
			AdviceNode node = new AdviceNode();
			node.setAdvice((ThrowsAdvice)obj);
			node.setMethod(after);
			chain.addThrowsMethod(node);				
		}
	}
}

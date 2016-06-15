package cn.candywrapper.aop.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.candywrapper.aop.AopProxy;
import cn.candywrapper.aop.advice.Advice;
import cn.candywrapper.aop.advice.AdviceChain;
import cn.candywrapper.aop.advice.AdviceChainMaker;
import cn.candywrapper.aop.advice.AdviceNode;
import cn.candywrapper.aop.advice.Adviced;
import cn.candywrapper.aop.advice.Advices;
import cn.candywrapper.aop.interceptor.JdkAdviceInterceptor;
import cn.candywrapper.aop.interceptor.JdkAdviceInterceptorFactory;
/**
 * 实现对接口对象的动态代理
 * @author xreztento@vip.sina.com
 *
 */
public class JdkDynamicAopProxy implements AopProxy{
	private JdkAdviceInterceptorFactory factory = null;
	public JdkDynamicAopProxy(JdkAdviceInterceptorFactory factory){
		this.factory = factory;
	}
	@Override
	public <T> T getProxy(final T interfaceTarget) {
		
		T interfaceProxy = null;
		//注入处理
		InvocationHandler h = (Object proxy, Method method, Object[] args) -> {
			AdviceChain chain = null;
			for(Annotation annotation : method.getAnnotations()){//多个通知声明的处理
				if(annotation instanceof Advices){
					chain = new AdviceChain();//通过使用AdviceChain结构，实现对通知方法的按顺序保存
					AdviceChainMaker maker = new AdviceChainMaker(chain);
					AdviceNode node = new AdviceNode();
					node.setMethod(method);
					chain.setMain(node);
					for(Advice adviceValue : ((Advices)annotation).value()){
						Class<?> advice = Class.forName(adviceValue.className());
						maker.add(advice);
					}
				} else if(annotation instanceof Advice){//单个通知声明的处理
					chain = new AdviceChain();//通过使用AdviceChain结构，实现对通知方法的按顺序保存
					AdviceChainMaker maker = new AdviceChainMaker(chain);
					AdviceNode node = new AdviceNode();
					node.setMethod(method);
					chain.setMain(node);
					Class<?> advice = Class.forName(((Advice)annotation).className());
					maker.add(advice);
				}
				
				JdkAdviceInterceptor interceptor = factory.getInterceptor();
				interceptor.addAdviceChain(chain);
				return interceptor.intercept(interfaceTarget, method, args);
				
			}
			
			return method.invoke(interfaceTarget, args);
		};
		
		Class<?>[] oldInterfaces = null;
		
		//获取和判断接口信息
		if(!interfaceTarget.getClass().isInterface()){
			int interfaceNum = interfaceTarget.getClass().getInterfaces().length;
			oldInterfaces = new Class<?>[interfaceNum];
			int i = 0;
			for(Class<?> clazz : interfaceTarget.getClass().getInterfaces()){
				
				for(Annotation annotation : clazz.getAnnotations()){
					if(annotation instanceof Adviced){
						oldInterfaces[i] = clazz;
					}
				}
				i++;
			}
		} else {
			oldInterfaces =	new Class<?>[1];
			oldInterfaces[0] = interfaceTarget.getClass();
		}
		interfaceProxy = (T)Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), oldInterfaces, h);
		return interfaceProxy;
	}

}

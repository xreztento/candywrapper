package cn.candywrapper.aop.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import cn.candywrapper.aop.advice.Advice;
import cn.candywrapper.aop.advice.AdviceChain;
import cn.candywrapper.aop.advice.AdviceChainMaker;
import cn.candywrapper.aop.advice.AdviceNode;
import cn.candywrapper.aop.advice.Advices;
import cn.candywrapper.aop.interceptor.CglibAdviceInterceptor;
import cn.candywrapper.aop.interceptor.CglibAdviceInterceptorFactory;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.NoOp;


/**
 * 根据声明动态生成Callback和CallbackFilter
 * @author xreztento@vip.sina.com
 *
 */
public class CglibAopProxyHelper {
	private CglibAdviceInterceptorFactory factory = null;
	
	public CglibAopProxyHelper(CglibAdviceInterceptorFactory factory){
		this.factory = factory;
	}
	private HashMap<Integer, Callback> callbackMap = new HashMap<Integer, Callback>();
	private HashMap<String, Integer> methodNameMap = new  HashMap<String, Integer>();
	
	public Callback[] genCallbacks(Class<?> clazz) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException{
		int callbackNum = 0;
		//配置一个什么都不做的回调方法，以保持与原方法一致
		callbackMap.put(callbackNum++, NoOp.INSTANCE);
		
		for(Method method : clazz.getDeclaredMethods()){
			for(Annotation annotation : method.getAnnotations()){
				if(annotation instanceof Advices){
					AdviceChain chain = new AdviceChain();
					AdviceChainMaker maker = new AdviceChainMaker(chain);
					AdviceNode node = new AdviceNode();
					node.setMethod(method);
					chain.setMain(node);
					for(Advice adviceValue : ((Advices)annotation).value()){
						Class<?> advice = Class.forName(adviceValue.className());
						maker.add(advice);
					}
					methodNameMap.put(method.toGenericString(), callbackNum);
					CglibAdviceInterceptor interceptor = factory.getInterceptor();
					interceptor.addAdviceChain(chain);
					callbackMap.put(callbackNum++, interceptor);
				} else if(annotation instanceof Advice){
					AdviceChain chain = new AdviceChain();
					AdviceChainMaker maker = new AdviceChainMaker(chain);
					AdviceNode node = new AdviceNode();
					node.setMethod(method);
					chain.setMain(node);
					Class<?> advice = Class.forName(((Advice)annotation).className());
					maker.add(advice);
					methodNameMap.put(method.toGenericString(), callbackNum);
					CglibAdviceInterceptor interceptor = factory.getInterceptor();
					interceptor.addAdviceChain(chain);
					callbackMap.put(callbackNum++, interceptor);
				}
			}
		}
		
		Callback[] callbacks = new Callback[callbackMap.size()];
		for(int i = 0; i < callbacks.length; i++){
			callbacks[i] = callbackMap.get(i);
		}
		return callbacks;
	}
	
	public CallbackFilter genFilter(Callback[] callbackArray){
		CallbackFilter filter = (Method method) -> {
			if(methodNameMap.containsKey(method.toGenericString())){
				return methodNameMap.get(method.toGenericString());
			}
			return 0;
			
		};
		
		return filter;
	}
}

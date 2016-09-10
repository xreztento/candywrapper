package cn.candywrapper.aop.proxy;

import cn.candywrapper.aop.AopProxy;
import cn.candywrapper.aop.interceptor.CglibAdviceInterceptor;
import cn.candywrapper.aop.interceptor.CglibAdviceInterceptorFactory;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class CglibAopProxy implements AopProxy{
	private CglibAdviceInterceptorFactory factory = null;
	public CglibAopProxy(CglibAdviceInterceptorFactory factory){
		this.factory = factory;
	}
	@Override
	public <T> T getProxy(T classTarget) {
		
	    Enhancer enhancer = new Enhancer();
	    enhancer.setSuperclass(classTarget.getClass());
	    CglibAopProxyHelper helper = new CglibAopProxyHelper(factory);
	    Callback[] callbackArray = null;
	    try {
			callbackArray = helper.genCallbacks(classTarget.getClass());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    enhancer.setCallbacks(callbackArray);
	    enhancer.setCallbackFilter(helper.genFilter(callbackArray));
	    
	    T proxy = (T)enhancer.create();
	    return proxy;
	}

}

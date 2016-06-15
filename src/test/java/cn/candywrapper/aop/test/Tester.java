package cn.candywrapper.aop.test;

import java.util.HashMap;
import java.util.Map;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import cn.candywrapper.aop.advisor.AdvicePointCutAdvisor;
import cn.candywrapper.aop.interceptor.CommonCglibAdviceInterceptorFactory;
import cn.candywrapper.aop.proxy.CglibAopProxy;
import cn.candywrapper.aop.util.JavassistMethodDescriptionMaker;

public final class Tester {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException{
		CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put(JavassistMethodDescriptionMaker.combine("connect", void.class, null), 
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
				"cn.candywrapper.aop.test.TestBeforeAdvice2"});
		
		map.put(JavassistMethodDescriptionMaker.combine("reset", int.class, new Class<?>[]{int.class, float.class}),
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
		"cn.candywrapper.aop.test.TestAfterAdvice"});
		AdvicePointCutAdvisor advisor = new AdvicePointCutAdvisor();
		TestAopForNoAdvice testAop = proxy.getProxy(advisor.pointCut("cn.candywrapper.aop.test.TestAopForNoAdvice", map));
		testAop.connect();
		testAop.reset(1, 1f);
	}
}

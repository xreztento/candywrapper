package cn.candywrapper.aop.test;

import cn.candywrapper.aop.advice.Advice;
import cn.candywrapper.aop.advice.Adviced;

@Adviced
public interface TestAopInterface2 {
	
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	@Advice(className = "cn.candywrapper.aop.test.TestThrowsAdvice")
	public String reset(String command);
}

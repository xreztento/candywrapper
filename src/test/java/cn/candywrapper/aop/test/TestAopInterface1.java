package cn.candywrapper.aop.test;

import cn.candywrapper.aop.advice.Advice;
import cn.candywrapper.aop.advice.Adviced;

@Adviced
public interface TestAopInterface1 {
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice2")
	public void connect();
	
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	public String close();
}

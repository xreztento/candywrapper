package cn.candywrapper.aop.test;

import cn.candywrapper.aop.advice.Advice;
import cn.candywrapper.aop.advice.Adviced;

@Adviced
public class TestAopForClass {
	
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice2")
	public void connect(){
		System.out.print("connection");
	}
	
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	public String close(){
		System.out.print("close");
		return "close";
	}

	
	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice1")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	@Advice(className = "cn.candywrapper.aop.test.TestThrowsAdvice")
	public String reset(String command) {
		// TODO Auto-generated method stub
		System.out.print(1/0);
		return command;
	}
}

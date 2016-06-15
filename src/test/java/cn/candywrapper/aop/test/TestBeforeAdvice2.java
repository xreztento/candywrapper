package cn.candywrapper.aop.test;

import java.lang.reflect.Method;

import cn.candywrapper.aop.BeforeAdvice;

public class TestBeforeAdvice2 implements BeforeAdvice{

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.print("Test Before2");
		
	}

}

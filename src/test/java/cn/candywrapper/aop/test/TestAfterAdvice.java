package cn.candywrapper.aop.test;

import java.lang.reflect.Method;
import cn.candywrapper.aop.AfterAdvice;

public class TestAfterAdvice implements AfterAdvice{

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.print("Test After");
	}

}

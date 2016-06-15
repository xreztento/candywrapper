package cn.candywrapper.aop.test;

import cn.candywrapper.aop.ThrowsAdvice;

public class TestThrowsAdvice implements ThrowsAdvice{

	@Override
	public void afterThrowing(Throwable e) throws Throwable {
		// TODO Auto-generated method stub
		System.out.print("throw");
		e.printStackTrace();
	}

}

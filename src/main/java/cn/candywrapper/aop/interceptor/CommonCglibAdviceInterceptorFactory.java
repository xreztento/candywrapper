package cn.candywrapper.aop.interceptor;

public class CommonCglibAdviceInterceptorFactory implements CglibAdviceInterceptorFactory{

	@Override
	public CglibAdviceInterceptor getInterceptor() {
		// TODO Auto-generated method stub
		return new CommonCglibAdviceInterceptor();
	}
	
}

package cn.candywrapper.aop.interceptor;

public class CommonJdkAdviceInterceptorFactory implements JdkAdviceInterceptorFactory{

	@Override
	public JdkAdviceInterceptor getInterceptor() {
		// TODO Auto-generated method stub
		return new CommonJdkAdviceInterceptor();
	}

}

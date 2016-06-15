package cn.candywrapper.aop;

import java.lang.reflect.Method;
import org.aopalliance.aop.Advice;

/**
 * 后置通知器接口
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public interface AfterAdvice extends Advice{
	void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
package cn.candywrapper.aop;

import org.aopalliance.aop.Advice;
/**
 * 异常通知器接口
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public interface ThrowsAdvice  extends Advice{
	void afterThrowing(Throwable e) throws Throwable;
}

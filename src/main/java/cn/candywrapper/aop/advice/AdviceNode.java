package cn.candywrapper.aop.advice;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
/**
 * 通知节点数据结构
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public class AdviceNode {
	private Advice advice = null;
	private Method method = null;
	public Advice getAdvice() {
		return advice;
	}
	public void setAdvice(Advice advice) {
		this.advice = advice;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	
	
}

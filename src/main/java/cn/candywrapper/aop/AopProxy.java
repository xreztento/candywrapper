package cn.candywrapper.aop;

/**
 * AOP代理接口
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public interface AopProxy {
	public <T> T getProxy(T target);
}

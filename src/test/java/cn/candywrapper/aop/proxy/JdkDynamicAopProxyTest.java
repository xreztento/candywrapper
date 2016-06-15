package cn.candywrapper.aop.proxy;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.candywrapper.aop.interceptor.CommonJdkAdviceInterceptor;
import cn.candywrapper.aop.interceptor.CommonJdkAdviceInterceptorFactory;
import cn.candywrapper.aop.test.TestAopForInterface;
import cn.candywrapper.aop.test.TestAopInterface1;
import cn.candywrapper.aop.test.TestAopInterface2;

public class JdkDynamicAopProxyTest {
	
	PrintStream console = null;          // 声明（为null）：输出流 (字符设备) console
	ByteArrayOutputStream bytes = null;  // 声明（为null）：bytes 用于缓存console 重定向过来的字符流
	@Before
	public void before(){
		bytes = new ByteArrayOutputStream();    // 分配空间
		console = System.out;                   // 获取System.out 输出流的句柄
		System.setOut(new PrintStream(bytes));  // 将原本输出到控制台Console的字符流 重定向 到 bytes
	}
	
	@Test
	public void testGetProxyForInterface1(){
		
		TestAopInterface1 testAop = new TestAopForInterface();
		CommonJdkAdviceInterceptorFactory factory = new CommonJdkAdviceInterceptorFactory();
		JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(factory);
		TestAopInterface1 proxyTestAopInterface1 = proxy.getProxy(testAop);
		proxyTestAopInterface1.connect();
		assertEquals("Test Before1Test Before2connection", bytes.toString().trim());
		String returnValue = proxyTestAopInterface1.close();
		assertEquals("close", returnValue);
		assertEquals("Test Before1Test Before2connectionTest Before1closeTest After", bytes.toString().trim());

	}
	
	@Test
	public void testGetProxyForInterface2(){
		TestAopInterface2 testAop = new TestAopForInterface();
		CommonJdkAdviceInterceptorFactory factory = new CommonJdkAdviceInterceptorFactory();
		JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(factory);
		TestAopInterface2 proxyTestAopInterface2 = proxy.getProxy(testAop);
		proxyTestAopInterface2.reset("test");
		assertEquals("Test Before1throwTest After", bytes.toString().trim());
	}

	@After
	public void after(){
		System.setOut(console);
	}
}

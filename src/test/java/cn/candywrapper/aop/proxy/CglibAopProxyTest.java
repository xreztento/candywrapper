package cn.candywrapper.aop.proxy;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.candywrapper.aop.advisor.AdvicePointCutAdvisor;
import cn.candywrapper.aop.interceptor.CommonCglibAdviceInterceptorFactory;
import cn.candywrapper.aop.test.TestAopForClass;
import cn.candywrapper.aop.test.TestAopForNoAdviceInterface;
import cn.candywrapper.aop.util.JavassistMethodDescriptionMaker;


public class CglibAopProxyTest {
	PrintStream console = null;          // 声明（为null）：输出流 (字符设备) console
	ByteArrayOutputStream bytes = null;  // 声明（为null）：bytes 用于缓存console 重定向过来的字符流
	@Before
	public void before(){
		bytes = new ByteArrayOutputStream();    // 分配空间
		console = System.out;                   // 获取System.out 输出流的句柄
		System.setOut(new PrintStream(bytes));  // 将原本输出到控制台Console的字符流 重定向 到 bytes
	}
	
	@Test
	public void testAopForClass(){
		TestAopForClass testAop = new TestAopForClass();
		CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		TestAopForClass proxyTestAop = proxy.getProxy(testAop);
		proxyTestAop.connect();
		assertEquals("Test Before1Test Before2connection", bytes.toString().trim());
		String returnValue = proxyTestAop.close();
		assertEquals("close", returnValue);
		assertEquals("Test Before1Test Before2connectionTest Before1closeTest After", bytes.toString().trim());
		proxyTestAop.reset("test");
		assertEquals("Test Before1Test Before2connectionTest Before1closeTest AfterTest Before1throwTest After", bytes.toString().trim());
	}
	
	@Test
	public void testAopForNoAdvice() throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException{
		
		CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put(JavassistMethodDescriptionMaker.combine("connect", void.class, null), 
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
				"cn.candywrapper.aop.test.TestBeforeAdvice2"});
		
		map.put(JavassistMethodDescriptionMaker.combine("reset", int.class, new Class<?>[]{int.class, float.class}),
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
		"cn.candywrapper.aop.test.TestAfterAdvice"});
		AdvicePointCutAdvisor advisor = new AdvicePointCutAdvisor(new MyClassLoader());
		TestAopForNoAdviceInterface testAop = (TestAopForNoAdviceInterface)proxy.getProxy(advisor.pointCut("cn.candywrapper.aop.test.TestAopForNoAdvice", map));
		testAop.connect();
		assertEquals("Test Before1Test Before2connection", bytes.toString().trim());
		int resultValue = testAop.reset(1,0.1f);
		assertEquals("Test Before1Test Before2connectionTest Before1Test After", bytes.toString().trim());
		assertEquals(0, resultValue);
	}
	
	@After
	public void after(){
		System.setOut(console);
	}
	
	class MyClassLoader extends ClassLoader{
		
	}
}

package cn.candywrapper.aop.tools;

import org.junit.Test;

import cn.candywrapper.aop.test.TestAopForClass;
import cn.candywrapper.aop.test.TestAopForInterface;
import cn.candywrapper.aop.test.TestAopInterface1;

public class NeoToolsTest {
	@Test
	public void testNeoForInterface() throws InstantiationException, IllegalAccessException{
		TestAopInterface1 test = (TestAopInterface1)NeoTools.neoForInterface(TestAopForInterface.class);
		test.connect();
	}
	
	@Test
	public void testNeoForClass() throws InstantiationException, IllegalAccessException{
		TestAopForClass test = (TestAopForClass)NeoTools.neoForClass(TestAopForClass.class);
		test.connect();
	}
	
}

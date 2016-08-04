# candywrapper
##介绍
一个极简的AOP框架，使用JDK自带的动态代理实现接口级别的注入，使用cglib、javassist框架实现类级别的注入，并通过静态注解和动态注解方式实现对被注入对象标识。
##快速开始
AOP框架为两部分
* 使用JDK自带的动态代理实现接口级别的注入
* 使用cglib、javassist框架实现类级别的注入

### 接口级别注入
 本框架采用注解方式给被注入接口方法确定需要注入的方法类，提供了BeforeAdvice、AfterAdvice、ThrowsAdvice通知接口分别定义被注入接口方法在执行前、执行后和异常处理时的注入方法，可以通过继承通知接口的方法自定义注入方法类。
 对接口方法进行通知的注解，完成注入：
```java
@Adviced
public interface TestAopInterface {

	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	public String test();
}
```
当使用时，使用JdkDynamicAopProxy实现代理接口的生成：
```java
public class JDKProxyTester{
    public static void main(String[] args){
        TestAopInterface testAop = new TestAopForInterface();
        CommonJdkAdviceInterceptorFactory factory = new CommonJdkAdviceInterceptorFactory();
		JdkDynamicAopProxy proxy = new JdkDynamicAopProxy(factory);
		TestAopInterface proxyTestAopInterface = proxy.getProxy(testAop);
		proxyTestAopInterface.test();
}
```

### 类级别注入
本框架采用注解方式和硬编码定义方式给被注入类方法确定需要注入的方法类。
对类方法进行通知的注解，完成注入：

```java
public class TestAopForClass {

	@Advice(className = "cn.candywrapper.aop.test.TestBeforeAdvice")
	@Advice(className = "cn.candywrapper.aop.test.TestAfterAdvice")
	public String test(){
		System.out.print("test");
		return "test";
	}
}
```

当使用时，使用CglibAopProxy实现代理类的生成：
```java
public class CglibProxyTester{
    public static void main(String[] args){
        TestAopForClass testAop = new TestAopForClass();
        CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		TestAopForClass proxyTestAop = proxy.getProxy(testAop);
		proxyTestAop.test();
}
```

硬编码定义方式，测试类参考如下：
```java
public class TestAopForNoAdvice{
	public void connect(){
		System.out.print("connection");
	}
	
	public String close(){
		System.out.print("close");
		return "close";
	}
	
	public String reset(String command) {
		System.out.print(1/0);
		return command;
	}
	
	public int reset(int x, float y) {
		return 0;
	}
}
```

硬编码定义方式的实现：
```java
public final class Tester {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NotFoundException, CannotCompileException{
		CommonCglibAdviceInterceptorFactory factory = new CommonCglibAdviceInterceptorFactory();
		CglibAopProxy proxy = new CglibAopProxy(factory);
		
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put(JavassistMethodDescriptionMaker.combine("connect", void.class, null), 
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
				"cn.candywrapper.aop.test.TestBeforeAdvice2","cn.candywrapper.aop.test.TestAfterAdvice"});
		
		map.put(JavassistMethodDescriptionMaker.combine("reset", int.class, new Class<?>[]{int.class, float.class}),
				new String[]{"cn.candywrapper.aop.test.TestBeforeAdvice1",
		"cn.candywrapper.aop.test.TestAfterAdvice"});
		AdvicePointCutAdvisor advisor = new AdvicePointCutAdvisor();
		TestAopForNoAdvice testAop = proxy.getProxy(advisor.pointCut("cn.candywrapper.aop.test.TestAopForNoAdvice", map));
		
		
		testAop.connect();
		testAop.reset(1, 1f);
	}
}
```

**That's all**

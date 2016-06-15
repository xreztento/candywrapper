package cn.candywrapper.aop.test;

public interface TestAopForNoAdviceInterface {
	public void connect();
	public String close();
	public String reset(String command);
	public int reset(int x, float y);
}

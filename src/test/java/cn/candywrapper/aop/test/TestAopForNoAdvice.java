package cn.candywrapper.aop.test;

public class TestAopForNoAdvice implements TestAopForNoAdviceInterface{
	public void connect(){
		System.out.print("connection");
	}
	
	public String close(){
		System.out.print("close");
		return "close";
	}
	
	public String reset(String command) {
		// TODO Auto-generated method stub
		System.out.print(1/0);
		return command;
	}
	
	public int reset(int x, float y) {
		// TODO Auto-generated method stub
		return 0;
	}
}

package cn.candywrapper.aop.test;

public class TestAopForInterface implements TestAopInterface1, TestAopInterface2{

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
}

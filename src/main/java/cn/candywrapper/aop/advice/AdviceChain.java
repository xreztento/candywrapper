package cn.candywrapper.aop.advice;
import java.util.ArrayList;
/**
 * 通知链数据结构
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public class AdviceChain {
	
	private AdviceNode main = null;
	private ArrayList<AdviceNode> beforeList = new ArrayList<AdviceNode>();
	private ArrayList<AdviceNode> afterList = new ArrayList<AdviceNode>();
	private ArrayList<AdviceNode> throwsList = new ArrayList<AdviceNode>();
	
	public void addBeforeMethod(AdviceNode node){
		beforeList.add(node);
	}
	
	public void addAfterMethod(AdviceNode node){
		afterList.add(node);
	}
	
	public void addThrowsMethod(AdviceNode node){
		throwsList.add(node);
	}
	
	public AdviceNode getMain() {
		return main;
	}
	
	public void setMain(AdviceNode main) {
		this.main = main;
	}
	
	public ArrayList<AdviceNode> getBeforeList() {
		return beforeList;
	}
	
	public void setBeforeList(ArrayList<AdviceNode> beforeList) {
		this.beforeList = beforeList;
	}
	
	public ArrayList<AdviceNode> getAfterList() {
		return afterList;
	}
	
	public void setAfterList(ArrayList<AdviceNode> afterList) {
		this.afterList = afterList;
	}

	public ArrayList<AdviceNode> getThrowsList() {
		return throwsList;
	}

	public void setThrowsList(ArrayList<AdviceNode> throwsList) {
		this.throwsList = throwsList;
	}
	
}

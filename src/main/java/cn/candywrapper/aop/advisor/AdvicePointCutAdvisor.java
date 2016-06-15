package cn.candywrapper.aop.advisor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import cn.candywrapper.aop.util.JavassistMethodDescriptionMaker;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.AnnotationMemberValue;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.StringMemberValue;
/**
 * 实现动态将Advice声明注入到被代理类
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public class AdvicePointCutAdvisor {
	
	private static final String ADVICED = "cn.candywrapper.aop.advice.Adviced";
	
	private static final String ADVICES = "cn.candywrapper.aop.advice.Advices";
	
	private static final String ADVICE = "cn.candywrapper.aop.advice.Advice";
	
	private ClassLoader cl = null;
	
	public AdvicePointCutAdvisor(){
		
	}
	
	public AdvicePointCutAdvisor(ClassLoader cl){
		this.cl = cl;
	}
	
	/**
	 * 为传入类动态添加Advice相关声明,并返回这个伪造类的对象,传入类无构造函数
	 * @param className
	 * @param advicesMap
	 * @return
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public <T> T pointCut(String className, Map<String, String[]> advicesMap) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException{

		ClassPool pool = ClassPool.getDefault();
		
	    CtClass cc = pool.getCtClass(className);
	    
	    ClassFile ccFile = cc.getClassFile();
	    ConstPool constpool = ccFile.getConstPool();
	    AnnotationsAttribute attr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
	   
	    Annotation adviced = new Annotation(ADVICED, constpool);	    
	    attr.addAnnotation(adviced);
	    
	    ccFile.addAttribute(attr);
	    
    	advicesMap.forEach((m, a) -> {
	    	AnnotationsAttribute attribute = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        	Annotation advices = new Annotation(ADVICES, constpool);
        	AnnotationMemberValue[] amv = new AnnotationMemberValue[a.length];
        	
	    	for(int i = 0; i < a.length; i++){
	    		Annotation advice = new Annotation(ADVICE, constpool);
	    		advice.addMemberValue("className",new StringMemberValue(a[i], constpool));
	    		amv[i] = new AnnotationMemberValue(advice, constpool);
	    	}
	    	ArrayMemberValue memberValue = new ArrayMemberValue(constpool);
	    	memberValue.setValue(amv);
    		advices.addMemberValue("value", memberValue);
	    	attribute.addAnnotation(advices);
	    	
	    	try {
				cc.getMethod(JavassistMethodDescriptionMaker.getMethodName(m),
						JavassistMethodDescriptionMaker.getDescription(m)).getMethodInfo().addAttribute(attribute);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    });
    	
	    Class<T> dynamiqueClass = null;
	    if( cl != null){
	    	dynamiqueClass = cc.toClass(cl);
	    } else {
	    	dynamiqueClass = cc.toClass();
	    }
	    		
	    T t = dynamiqueClass.newInstance();
	    return t;
	}
}

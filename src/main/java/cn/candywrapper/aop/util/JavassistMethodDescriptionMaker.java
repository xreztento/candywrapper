package cn.candywrapper.aop.util;

/**
 * 实现生成和获取符合Javassist中反射Method获取条件的方法描述字符串
 * @author xreztento@vip.sina.com
 * @version 1.0
 * @since 2016-06-15
 */
public class JavassistMethodDescriptionMaker {
	
	/**	combine字符串中方法名和描述字符串之间的分隔符	**/
	private static final String SPLIT = "|";
	
	
	public static String getMethodName(String combine){
		return combine.split("\\|")[0];
	}
	
	public static String getDescription(String combine){
		return combine.split("\\|")[1];
	}
	
	public static String combine(String methodName, Class<?> returnType, Class<?>[] paramTypes){
		if(methodName != null && returnType != null){
			StringBuffer combine = new StringBuffer();
			combine.append(methodName);
			combine.append(SPLIT);
			combine.append("(");
			if(paramTypes != null){
				for(Class<?> paramType : paramTypes){
					
					combine.append(getTypeDescription(paramType));
				}
			}
			combine.append(")");
			combine.append(getTypeDescription(returnType));
			return combine.toString();
		
		} else {
			return null;
		}
	}

	
	private static String getTypeDescription(Class<?> type){
		String typeName = type.getName().replace(".", "/");
		switch(typeName){
			case "boolean" : typeName = "Z"; break;
			case "byte" : typeName = "B"; break;
			case "char" : typeName = "C"; break;
			case "double" : typeName = "D"; break;
			case "float" : typeName = "F"; break;
			case "int" : typeName = "I"; break;
			case "long" : typeName = "L"; break;
			case "short" : typeName = "S"; break;
			case "void" : typeName = "V"; break;
			default : {
				if(!typeName.contains("[")){
					typeName = "L" + typeName + ";";
				}
				break;
			}
				
		}
		return typeName;
	}
	
}

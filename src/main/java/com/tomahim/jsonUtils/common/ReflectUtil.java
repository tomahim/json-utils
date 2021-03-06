package com.tomahim.jsonUtils.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.json.JsonValue;

public final class ReflectUtil {

	private static final Set<Class<?>> WRAPPER_TYPES = getWrapperTypes();

	private ReflectUtil() {

	}

	public static boolean isWrapperType(Class<?> clazz) {
		return WRAPPER_TYPES.contains(clazz);
	}

	public static boolean needRecusivity(Method m) {
		Class<?> returnType = m.getReturnType();
		return !ReflectUtil.isPrimiveObject(returnType);
	}
	
	public static boolean multipleObjectsReturned(Method m) {
		Class<?> returnType = m.getReturnType();
		return returnType.equals(List.class) || returnType.equals(Set.class);
	}

	private static Set<Class<? extends Object>> getWrapperTypes() {
		Set<Class<? extends Object>> ret = new HashSet<Class<? extends Object>>();
		ret.add(Boolean.class);
		ret.add(Character.class);
		ret.add(Byte.class);
		ret.add(Date.class);
		ret.add(Short.class);
		ret.add(Integer.class);
		ret.add(Long.class);
		ret.add(Float.class);
		ret.add(Double.class);
		ret.add(BigDecimal.class);
		ret.add(Void.class);
		ret.add(String.class);
		return ret;
	}

	public static boolean isPrimiveObject(Class<?> c) {
		return c.isPrimitive() || isWrapperType(c);
	}

	private static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers())
				&& method.getParameterTypes().length == 0) {
			if (method.getName().matches("^get[A-Z].*")
					&& !method.getReturnType().equals(void.class)) {
				return true;
			}
			if (method.getName().matches("^is[A-Z].*")
					&& method.getReturnType().equals(boolean.class)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Method> findGetters(Class<?> c) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				list.add(method);
			}
		}
		// Include also getters of super class
		for (Method method : c.getSuperclass().getDeclaredMethods()) {
			if (isGetter(method)) {
				list.add(method);
			}
		}
		return list;
	}
	
	private static String getMemberNameFromGetterName(String getterName) {
		if(getterName.length() > 3) {
			String attributeName = getterName.substring(3, getterName.length());
			return attributeName.substring(0, 1).toLowerCase() + attributeName.substring(1, attributeName.length());
		} else {
			return "";
		}
	}
	
	public static Method getGetterByMemberName(Class classType, String memberName) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = classType.getDeclaredMethods();
		for (Method method : methods) {
			String searchName = getMemberNameFromGetterName(method.getName());
			if (isGetter(method) && searchName.equals(memberName)) {
				return method;
			}
		}
		// Include also getters of super class
		for (Method method : classType.getSuperclass().getDeclaredMethods()) {
			String searchName = getMemberNameFromGetterName(method.getName());
			if (isGetter(method) && searchName.equals(memberName)) {
				return method;
			}
		}
		return null;
	}

	public static String getPropertyFromMethod(Method method) {
		String name = method.getName();
		if (name.startsWith("get")) {
			return StringUtil.lowercaseFirstLetter(name.substring(3, method
					.getName().length()));
		}
		return name;
	}

	public static List<Method> getAccessibleGettersMethods(Object o) {
		List<Method> methodsAccessible = new ArrayList<Method>();
		if (o != null && o.getClass() != null) {
			ArrayList<Method> methods = ReflectUtil.findGetters(o.getClass());
			for (Method method : methods) {
				method.setAccessible(true);
				if (method.isAccessible()) {
					methodsAccessible.add(method);
				}
			}
		}
		return methodsAccessible;
	}	
	
	public static Method getSetterByMemberName(Class classType, String name, Class jsonValueClass) throws NoSuchMethodException, SecurityException {
		String setterName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		return classType.getMethod(setterName, jsonValueClass);
	}
	
	public static Method getGetterByMemberName(Class classType, String name, Class jsonValueClass) throws NoSuchMethodException, SecurityException {
		String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		return classType.getMethod(getterName, jsonValueClass);
	}
}

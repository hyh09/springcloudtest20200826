/**
 * @单位名称：科大国创—电信资源事业部
 *            USTC Sinovate Software Co.,Ltd.
 * 		      Copyright (c) 2013 All Rights Reserved.
 * @系统名称：NGRMS—下一代网络资源管理系统
 * @工程名称：cptlifts.platformcore
 * @文件名称: Exceptions.java
 * @类路径: com.sinovate.ngrms.cptlifts.platformcore.exception
 */

package com.example.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @see		
 * @author  sun.peiwen@ustcinfo.com
 * @date	2013-3-28 下午3:44:26
 * @version	 
 * @desc    TODO
 */
public class ExceptionTools {

	private ExceptionTools(){}
	
	/**
	 * 将已检查异常转换成未检查异常
	 * @param exp
	 * @return
	 */
	public static RuntimeException cvtUnchecked(Exception exp) {
		if(exp instanceof RuntimeException) {
			return (RuntimeException)exp;
		} else {
			return new RuntimeException(exp);
		}
	}
	
	/**
	 * 将异常错误信息转换成字符串
	 * @param exp
	 * @return
	 */
	public static String getStackTraceAsString(Exception exp) {
		StringWriter strWriter = new StringWriter();
		exp.printStackTrace(new PrintWriter(strWriter));
		return strWriter.toString();
	}
	
	/**
	 * 判断异常是否由某些底层的异常引起.
	 * @param ex
	 * @param causeExceptionClasses
	 * @return
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
}


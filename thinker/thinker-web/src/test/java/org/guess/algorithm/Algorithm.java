package org.guess.algorithm;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * 各类算法
 * 
 * @author dview
 * 
 */
public class Algorithm {

	/**
	 * 题目：将一个正整数分解质因数。例如：输入90,打印出90=2*3*3*5。
	 * 
	 * @param n
	 * @return
	 */
	public String fengjie(int n) {
		for (int i = 2; i < n / 2; i++) {
			if (n % i == 0) {
				return i + "*" + fengjie(n / i);
			}
		}
		return n + "";
	}

	/**
	 * 用条件运算符的嵌套来完成此题：学习成绩> =90分的同学用A表示，60-89分之间的用B表示，60分以下的用C表示。
	 * 
	 * @param n
	 * @return
	 */
	public String dengji(int n) {
		return (n > 90 ? "A" : (n > 60 ? "B" : "C"));
	}

	@Test
	public void test() throws ParseException {
		// System.out.println(fengjie(200));
		// System.out.println(dengji(80));
		// System.out.println(DateUtil.format(DateUtil.addDay(DateUtil.parse("2014-10-22"),75)));

		// System.out.println(DateUtil.getCurrenDate());
		// System.out.println(DateUtil.getYear(DateUtil.getCurrenDateTime()));
		System.out.println(StringUtils.substring("62.210.83.26", 0, 7));
		System.out.println();
	}

}

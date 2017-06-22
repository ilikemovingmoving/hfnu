package test;

import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
//		String s="909090";
//		System.out.println(s.lastIndexOf("0")==s.length()-1);
		String s="安徽省芜湖市无为县石涧镇";
		System.out.println(s.split("省")[0]);
		System.out.println(s.split("省")[1].split("市")[0]);
		System.out.println(s.split("省")[1].split("市")[1].split("县|区")[0]);
		System.out.println(s.split("省")[1].split("市")[1].split("县|区")[1].split("镇")[0]);
	}
}

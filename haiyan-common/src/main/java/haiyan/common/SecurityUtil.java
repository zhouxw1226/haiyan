package haiyan.common;

import com.zhouxw.utils.security.EncryptUtil;

import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IContext;

public class SecurityUtil {
	// 原始数据指纹(TODO 需要使用非对称加密算法:各SOA集群内部保留自己的私钥，用对方SOA公钥加密传到对方)
	public static String sign(String datas) throws Throwable {
		String sign = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		return sign;
	}
	public static void checkSign(String sign, String datas) throws Throwable {
		String sign1 = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		if (!sign.equals(sign1)) // 指纹比对
			throw new Warning(400, "[bad request sign]");
	}
	public static void checkSign(IContext context, String datas) throws Throwable {
		String sign = (String)context.getAttribute("sign"); // getParameter("encryption")
		String sign1 = EncryptUtil.toHexString(EncryptUtil.security(datas, null), "", "");
		if (!sign.equals(sign1)) // 指纹比对
			throw new Warning(400, "[bad request sign]");
	}
}

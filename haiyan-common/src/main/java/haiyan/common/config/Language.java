/*
 * Created on 2006-8-23
 *
 */
package haiyan.common.config;

import haiyan.common.PropUtil;
import haiyan.common.StringUtil;
import haiyan.common.exception.Warning;
import haiyan.common.intf.session.IUser;


/**
 * @author zhouxw
 * 
 */
public class Language {

	/**
	 * 默认locale语言
	 */
	private final static String DEFAULT_LANGUAGE = "lang-zh_CN_GB2312";
	/**
	 * 默认locale语言名称
	 */
	private String name = DEFAULT_LANGUAGE;
	/**
	 * 
	 */
	public Language() {
		super();
	}
	/**
	 * @param name
	 */
	public Language(String name) {
		this.name = name;
	}
	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param user
	 * @return
	 */
	public static Language getLanguage(IUser user) {
        String value = PropUtil.getProperty(user==null||user.getLanguageName()==null?"LANGUAGE":user.getLanguageName());
        if (StringUtil.isStrBlankOrNull(value)) {
            throw new Warning("Can't load language!");
        }
        return new Language(value);
	}
}
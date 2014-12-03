package haiyan.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来定位get方法，并确定取的是哪个属性的值
 * @author 商杰
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMethod {
	/**
	 * 用来获取属性名
	 * @return 属性名
	 */
	public String value() ;
}

package xiaozaiyi.crowd.util;


import org.jetbrains.annotations.Nullable;
import org.springframework.util.ObjectUtils;

public class ObjectUtil extends ObjectUtils {

	/**
	 * 判断元素不为空
	 * @param obj object
	 * @return boolean
	 */
	public static boolean isNotEmpty(@Nullable Object obj) {
		return !ObjectUtil.isEmpty(obj);
	}

}

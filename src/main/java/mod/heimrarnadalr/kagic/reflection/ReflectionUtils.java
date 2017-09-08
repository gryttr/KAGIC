package mod.heimrarnadalr.kagic.reflection;

import java.lang.reflect.Field;

public class ReflectionUtils {
	public static Field getFieldFromSuperclass(Class<?> inClass, Class<?> superClass, String fieldName) {
		Class<?> c = inClass;
		while (c != superClass) {
			c = c.getSuperclass();
			if (c == Object.class) {
				return null;
			}
		}
		
		try {
			return c.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}
}

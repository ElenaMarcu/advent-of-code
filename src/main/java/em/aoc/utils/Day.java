package em.aoc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Day {
    private static final Logger logger = LoggerFactory.getLogger(Day.class);
    private Object dayClass = null;

    public Day(String year, String day) {
        try {
            String className = "em.aoc.year" + year + ".Day" + day;
            dayClass = Class.forName(className).getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException exception) {
            logger.error("Error creating instance: {}", exception.toString());
        }
    }

    public String invokePartNo(String partNo) {
        String methodName = "part" + partNo;
        try {
            Method method = dayClass.getClass().getMethod(methodName);
            return (String) method.invoke(dayClass);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
            logger.error("Error invoking the method {}: {}", methodName, exception.toString());
        }
        return null;
    }

    public String invokeBothParts() {
        return invokePartNo("1") + "/n" + invokePartNo("2");
    }
}

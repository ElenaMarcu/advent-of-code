package em.aoc.utils;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class Day {
    private static final Logger logger = LoggerFactory.getLogger(Day.class);
    protected List<String> lines;

    public static Day getClassInstance(String year, String day, String className) {
        try {
            String classPath = "em.aoc.year" + year + "." + className + day;
            return (Day) Class.forName(classPath).getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 ClassNotFoundException exception) {
            logger.error("Error creating instance: {}", exception.toString());
        }
        return null;
    }

    protected abstract String part1();
    protected abstract String part2();
}

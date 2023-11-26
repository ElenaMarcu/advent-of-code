package em.aoc;


import em.aoc.utils.Day;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        if (args.length >= 2) {
            String year = args[0];
            String dayNo = args[1];
            Day day = new Day(year, dayNo);
            String result;
            if (args.length >= 3) {
                String partNo = args[2];
                result = day.invokePartNo(partNo);
            } else {
                result = day.invokeBothParts();
            }
            logger.info(result);
        } else {
            logger.error("Insufficient command-line arguments.");
            logger.error("The arguments should be the following format: year day (part).");
        }
    }
}

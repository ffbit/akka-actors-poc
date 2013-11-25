package akka.lp;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class Utils {

    // TODO: write tests for this code
    public static boolean isCollectionOf(Object msg, Class<?> clazz) {
        if (!(msg instanceof Collection || msg instanceof ParameterizedType)) {
            return false;
        }

        // TODO: problems go here
        /*
        Type msgType = msg.getClass().getGenericSuperclass();
        if (msgType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) msgType;

            Type[] types = type.getActualTypeArguments();

            if (types.length == 0) {
                return false;
            }


            if (clazz.isAssignableFrom(types[0].getClass())) {
                return true;
            }

            return false;
        } */

        // TODO: Work around
        boolean typeFlag = true;

        for (Object e : (Collection) msg) {
            typeFlag &= clazz.isAssignableFrom(e.getClass());
        }

        return typeFlag;
    }

    public static void sleep() {
        sleep(10);
    }

    public static void sleep(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage(), e);
        }
    }

}

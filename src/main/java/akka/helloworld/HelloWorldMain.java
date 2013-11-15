package akka.helloworld;

import akka.Main;

public class HelloWorldMain {

    public static void main(String... args) {
        String[] akkaArgs = {
                HelloWold.class.getCanonicalName()
        };

        Main.main(akkaArgs);
    }

}

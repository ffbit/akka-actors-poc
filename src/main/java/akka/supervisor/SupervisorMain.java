package akka.supervisor;

import akka.Main;

public class SupervisorMain {

    public static void main(String... args) {
        String[] akkaArgs = {
                Supervisor.class.getCanonicalName()
        };

        Main.main(akkaArgs);
    }

}

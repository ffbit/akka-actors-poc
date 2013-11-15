package akka.greeting;

import java.io.Serializable;

public class Greeting implements Serializable {
    private final String who;

    public Greeting(String who) {
        this.who = who;
    }

    public String getWho() {
        return who;
    }

}


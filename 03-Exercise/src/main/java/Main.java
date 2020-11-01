import core.EngineImpl;
import core.interfaces.Engine;
public class Main {

    public static void main(String[] args) {
        String pleaseDoRead = "If you need pom file COPY mine --> main.resources.DATABASE-SCRIPT.POM ";
        Engine engine = new EngineImpl();
        engine.run();
    }
}



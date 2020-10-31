package core;

import core.interfaces.Engine;
import core.interfaces.FactoryExecuteTasks;
import io.CustomReader;
import io.CustomWriterImpl;
import io.InputParserImpl;
import io.interfaces.CustomWriter;
import io.interfaces.InputParser;
import lombok.SneakyThrows;

import javax.persistence.EntityManager;

import static io.CustomReader.*;

public class EngineImpl implements Engine {

    public final EntityManager entityManager;
    private CustomWriter writer;
    private CustomReader reader;
    private InputParser parser;

    public EngineImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.writer = new CustomWriterImpl();
        this.reader = new CustomReader();
        this.parser = new InputParserImpl();

    }

    @SneakyThrows
    @Override
    public void run() {

        this.writer.writeln("Enter number of task, else enter EXIT");
        String input;
        while (!(input=this.reader.read()).equals("EXIT")) {
            FactoryExecuteTasks factory = new FactoryTasksImpl();
            Integer tasksInput = Integer.parseInt(input);
            factory.execute(tasksInput,entityManager);
        }
        entityManager.close();
    }
}

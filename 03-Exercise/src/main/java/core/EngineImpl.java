package core;

import core.interfaces.Engine;
import core.interfaces.FactoryTasks;
import io.CustomReader;
import io.CustomWriterImpl;
import io.InputParserImpl;
import io.interfaces.CustomWriter;
import io.interfaces.InputParser;
import lombok.SneakyThrows;

import javax.persistence.EntityManager;

import java.io.IOException;

import static constants.OutputConstantMessages.*;


public class EngineImpl implements Engine {

    public final EntityManager entityManager;
    private final CustomWriter writer;
    private final CustomReader reader;
    private final InputParser parser;
    private final FactoryTasks factory;


    public EngineImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.writer = new CustomWriterImpl();
        this.reader = new CustomReader();
        this.parser = new InputParserImpl();
        this.factory = new FactoryTasksImpl();
    }


    @Override
    public void run() {

        String input;
        try{
            while (!(input = this.reader.read()).equals("EXIT")) {
                this.writer.writeln(TASK_NAVIGATION_MSG);
                Integer tasksInput = Integer.parseInt(input);
                execute(tasksInput);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.close();
    }


    private void execute (Integer tasksInput) throws IOException {


        switch (tasksInput){
            case 2:
                this.factory.townsToLowerCase(this.entityManager);
                writer.writeln(DB_CHANGED);
                break;
            case 3:
                writer.writeln(ENTER_EMPLOYEE_NAME);
                String employee = reader.read();
                String output = this.factory
                        .checkExistenceOfEmployee(this.entityManager, employee)
                        ? NEGATIVE : POSITIVE;
                writer.writeln(output);
                break;
            case 4:

                break;
        }

    }
}

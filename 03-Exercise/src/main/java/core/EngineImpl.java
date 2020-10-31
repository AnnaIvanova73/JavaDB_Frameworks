package core;

import core.interfaces.Engine;
import core.interfaces.FactoryTasks;
import io.CustomReader;
import io.CustomWriterImpl;
import io.InputParserImpl;
import io.interfaces.CustomWriter;
import io.interfaces.InputParser;

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
        this.writer.writeln(TASK_NAVIGATION_MSG);
        try{
            while (!(input = this.reader.read()).equals("EXIT")) {
                Integer tasksInput = Integer.parseInt(input);
                execute(tasksInput);
                this.writer.writeln(TASK_NAVIGATION_MSG);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.close();
    }


    private void execute (Integer tasksInput) throws IOException {


        switch (tasksInput){
            case 2:
                this.factory.townsToLowerCaseEx2(this.entityManager);
                writer.writeln(DB_CHANGED);
                break;
            case 3:
                writer.writeln(ENTER_EMPLOYEE_NAME);
                String employee = reader.read();
                String output = this.factory
                        .checkExistenceOfEmployeeEx3(this.entityManager, employee)
                        ? NEGATIVE : POSITIVE;
                writer.writeln(output);
                break;
            case 4:
                writer.writeln(this.factory.employeesWithSalaryOver5000Ex4(entityManager));
                break;
        }

    }
}
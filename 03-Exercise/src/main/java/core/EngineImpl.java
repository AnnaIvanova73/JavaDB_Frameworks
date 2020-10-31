package core;

import core.interfaces.Engine;
import core.interfaces.FactoryTasks;
import io.CustomReader;
import io.CustomWriterImpl;
import io.InputParserImpl;
import io.interfaces.CustomWriter;
import io.interfaces.InputParser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.io.IOException;

import static constants.OutputConstantMessages.*;


public class EngineImpl implements Engine {

    public final EntityManagerFactory emf;
    public EntityManager entityManager;
    private final FactoryTasks factory;
    private final CustomWriter writer;
    private final CustomReader reader;
    private final InputParser parser;


    public EngineImpl() {
        this.emf = Persistence.createEntityManagerFactory("PU_Name");
        this.entityManager = emf.createEntityManager();

        this.factory = new FactoryTasksImpl();
        this.writer = new CustomWriterImpl();
        this.reader = new CustomReader();
        this.parser = new InputParserImpl();
    }


    @Override
    public void run() {

        String input;
        this.writer.writeln(TASK_NAVIGATION_MSG);
        try {
            while (!(input = this.reader.read()).equals("EXIT")) {
                Integer tasksInput = Integer.parseInt(input);
                execute(tasksInput);
                this.writer.writeln(TASK_NAVIGATION_MSG);
                this.entityManager = emf.createEntityManager();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        entityManager.close();
    }


    private void execute(Integer tasksInput) throws IOException {


        switch (tasksInput) {
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
            case 5:
                writer.writeln(this.factory.extractAllEmployeesFromDepartmentsEx5(entityManager));
                break;
            case 6:
                writer.writeln(REMINDER_CLEAR_DB);
                String lastName = reader.read();
                writer.writeln(this.factory.updateAddressByLastNameEx6(entityManager,lastName));
                writer.writeln(DB_CHANGED);
                break;
        }

    }
}

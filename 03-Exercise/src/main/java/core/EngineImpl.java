package core;

import core.interfaces.Engine;
import core.interfaces.FactoryTasks;
import io.CustomReader;
import io.CustomWriterImpl;
import io.interfaces.CustomWriter;

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


    public EngineImpl() {
        this.emf = Persistence.createEntityManagerFactory("PU_Name");
        this.entityManager = emf.createEntityManager();

        this.factory = new FactoryTasksImpl();
        this.writer = new CustomWriterImpl();
        this.reader = new CustomReader();
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
                this.writer.writelnInRed(TRUNCATE_OR_RELOAD);
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
                entityManager.close();
                break;
            case 3:
                writer.writeln(ENTER_EMPLOYEE_NAME);
                String employee = reader.read();
                String output = this.factory
                        .checkExistenceOfEmployeeEx3(this.entityManager, employee)
                        ? NEGATIVE : POSITIVE;
                writer.writeln(output);
                entityManager.close();
                break;
            case 4:
                writer.writeln(this.factory.employeesWithSalaryOver5000Ex4(entityManager));
                entityManager.close();
                break;
            case 5:
                writer.writeln(this.factory.extractAllEmployeesFromDepartmentsEx5(entityManager));
                entityManager.close();
                break;
            case 6:
                writer.writeln(REMINDER_CLEAR_DB);
                String lastName = reader.read();
                writer.writeln(this.factory.updateAddressByLastNameEx6(entityManager, lastName));
                writer.writeln(DB_CHANGED);
                entityManager.close();
                break;
            case 7:
                writer.writeln(this.factory.addressesWithEmployeeCountEx7(entityManager));
                entityManager.close();
                break;
            case 8:
                writer.writeln(EMPLOYEE_ID);
                int id = Integer.parseInt(reader.read());
                writer.writeln(this.factory.getEmployeeByIdEx8(entityManager, id));
                entityManager.close();
                break;
            case 9:
                writer.writeln(this.factory.findLatestProjectsEx9(entityManager));
                entityManager.close();
                break;
            case 10:
                writer.writeln(this.factory.increaseSalariesEx10(entityManager));
                entityManager.close();
                break;
            case 11:
                writer.writeln(PATTERN);
                String input = reader.read().toUpperCase();
                writer.writeln(this.factory.findEmployeesByFirstNameEx11(entityManager, input));
                entityManager.close();
                break;
            case 12:
                writer.writelnInRed(TRUNCATE_TABLES);
                writer.writeln(this.factory.employeesMaximumSalariesEx12(entityManager));
                entityManager.close();
                break;
            case 13:
                writer.writeln(ASK_TOWN_NAME);
               String read = reader.read();
                writer.writeln(this.factory.deleteAddressByTown(entityManager, read));
                entityManager.close();
                break;
            default:
                writer.writelnInRed(TASK_NOT_EXISTS);
                break;
        }

    }
}

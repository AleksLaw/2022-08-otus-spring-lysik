package otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static otus.config.JobConfig.MIGRATE;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommandMigration {

    private final JobOperator jobOperator;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm")
    public void startMigrationJobWithJobOperator() throws Exception {
        long l = System.currentTimeMillis();
        Long executionId = jobOperator.start(MIGRATE, String.valueOf(l));
        System.out.println(jobOperator.getSummary(executionId));
    }
}

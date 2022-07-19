package ru.digitalleague.gogolev.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.task.TaskStatus;
import ru.digitalleague.gogolev.entities.task.Task_;

import java.time.LocalDate;
import java.util.Map;

@UtilityClass
public class TasksSpecificationProvider {

    public static Specification<Task> getTasksSpecification(Map<String, String> inputParameters) {
        Specification<Task> spec = Specification.where(null);

        if (inputParameters.containsKey("to")) {
            spec = spec.and(dateLessThan(inputParameters.get("to")));
        }
        if (inputParameters.containsKey("fr")) {
            spec = spec.and(dateGreaterThan(inputParameters.get("fr")));
        }
        if (inputParameters.containsKey("s")) {
            spec = spec.and(statusEquals(inputParameters.get("s")));
        }
        return spec;
    }

    private static Specification<Task> statusEquals(String shortStatus) {

        TaskStatus status = TaskUtils.getStatus(shortStatus);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Task_.status), status);
    }


    private static Specification<Task> dateLessThan(String dateTo) {
        LocalDate date = StringUtils.validateAndParseDateFromString(dateTo);
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get(Task_.dateTo), date);

    }

    private static Specification<Task> dateGreaterThan(String dateFrom) {
        LocalDate date = StringUtils.validateAndParseDateFromString(dateFrom);
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get(Task_.dateTo), date);

    }
}

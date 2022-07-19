package ru.digitalleague.gogolev.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.digitalleague.gogolev.entities.task.Task;
import ru.digitalleague.gogolev.entities.task.Task_;
import ru.digitalleague.gogolev.entities.user.User;
import ru.digitalleague.gogolev.entities.user.User_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final EntityManager em;

    @Override
    public Optional<User> findUserWithMaxQuantityOfTasks(Specification<Task> spec) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<Task> root = criteria.from(Task.class);
        Join<Task, User> user = root.join(Task_.user);
        criteria.select(user)
                .groupBy(user.get(User_.id))
                .orderBy(cb.desc(cb.count(root.get(Task_.id))));
        Predicate predicate = spec.toPredicate(root, criteria, cb);
        if (predicate != null) {
            criteria.where(predicate);
        }

        User singleResult = null;
        try {
            singleResult = em.createQuery(criteria).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            log.error("No user found with provided params");
        }
        return Optional.ofNullable(singleResult);
    }
}

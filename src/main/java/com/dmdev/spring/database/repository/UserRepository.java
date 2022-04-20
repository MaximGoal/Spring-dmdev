package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.PersonalInfoInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
//@RequiredArgsConstructor
//public class UserRepository {}
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {
//    @Qualifier("pool2")
//    private final ConnectionPool connectionPool;

    //    public UserRepository(@Qualifier("pool2") ConnectionPool connectionPool) {
//        this.connectionPool = connectionPool;
//    }

    @Query("select u from User u where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);
//    List<User> findAllByFirstnameContainingAndLastnameContaining(String firstname, String lastname);

    @Query(value = "select u.* from users u where u.username = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true )
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long ... ids);

    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<User> findTop3ByBirthDateBefore(LocalDate birthdate, Sort sort);

    List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthdate);

//    @EntityGraph("User.company") = @EntityGraph(attributePaths = {"company"})
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query( value = "select u from User u",
            countQuery = "select count(distinct u.username) from User u")
    Page<User> findAllBy(Pageable pageable);

//    List<PersonalInfo> findAllByCompanyId(Integer companyId);

    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> clazz);

//    "birth_date birthDate" projection is used to correlate DB column "birth_date" and PersonalInfoInterface field "birthDate"
    @Query(value = "SELECT firstname, lastname, birth_date birthDate " +
            "FROM users " +
            "WHERE company_id = :companyId",
            nativeQuery = true
    )
    List<PersonalInfoInterface> findAllByCompanyId(Integer companyId);
}

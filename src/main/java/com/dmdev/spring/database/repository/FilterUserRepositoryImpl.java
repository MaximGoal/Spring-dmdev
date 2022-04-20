package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.QUser;
import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.querydsl.QPredicates;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.dmdev.spring.database.entity.QUser.user;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    private static final String FIND_BY_COMPANY_AND_ROLE = """
            SELECT firstname, lastname, birth_date
            FROM users
            WHERE company_id = ?
            AND role = ?
            """;

    private static final String UPDATE_COMPANY_AND_ROLE = """
        UPDATE users 
        SET company_id = ?,
         role = ?
        WHERE id = ?
    """;

    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
        UPDATE users 
        SET company_id = :companyId,
         role = :role
        WHERE id = :id
    """;

    @Override
    public List<User> findAllByFilter(UserFilter userFilter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);

        var user = criteria.from(User.class);
        criteria.select(user);
        List<Predicate> predicates = new ArrayList<>();
        if(userFilter.firstname() != null) {
            predicates.add(cb.like(user.get("firstname"), userFilter.firstname()));
        }
        if(userFilter.lastname() != null) {
            predicates.add(cb.like(user.get("lastname"), userFilter.lastname()));
        }
        if(userFilter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), userFilter.birthDate()));
        }
        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    public List<User> findAllByFilterQ(UserFilter userFilter) {
        var predicate = QPredicates.builder()
                .add(userFilter.firstname(), user.firstname::containsIgnoreCase)
                .add(userFilter.lastname(), user.lastname::containsIgnoreCase)
                .add(userFilter.birthDate(), user.birthDate::before)
                .build();
        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }

    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        return jdbcTemplate.query(
                FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birth_date").toLocalDate()),
                companyId, role.name());
    }

    @Override
    public void updateCompanyIdAndRole(List<User> users) {
        var args = users.stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    @Override
    public void updateCompanyIdAndRoleNamed(List<User> users) {
        var args = users.stream()
                .map(user -> Map.of(
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole(),
                        "id", user.getId()
                ))
                .map(MapSqlParameterSource::new)
                .toArray(MapSqlParameterSource[]::new);
        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}

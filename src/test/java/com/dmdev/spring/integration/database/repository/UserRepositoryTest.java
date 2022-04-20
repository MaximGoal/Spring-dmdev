package com.dmdev.spring.integration.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkBatch() {
        var users = userRepository.findAll();
        userRepository.updateCompanyIdAndRole(users);
        System.out.println();
    }

    @Test
    void checkJdbcTemplate() {
        var users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(users).hasSize(1);
    }

    @Test
//    @Commit
    void checkAuditing() {
        var ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkCustomImplementation() {
        UserFilter filter = new UserFilter(
                null, "%ov%", LocalDate.now()
        );
        userRepository.findAllByFilter(filter);
        System.out.println();
    }

    @Test
    void checkCustomImplementationQuerydsl() {
        UserFilter filter = new UserFilter(
                null, "%ov%", LocalDate.now()
        );
        userRepository.findAllByFilterQ(filter);
        System.out.println();
    }

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1);
//        var users = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        assertThat(users).hasSize(2);
         System.out.println();
    }

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(0, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageable);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));
//        assertThat(slice).hasSize(2);

        while (slice.hasNext()) {
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void checkSort() {
        var sortById = Sort.by("id").descending();
        var sortByName = Sort.by("firstname").and(Sort.by("lastname")).descending();

        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        var top3Users = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sortById);
        var top3UsersByName = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sortByName);
        var top3UsersByNameTyped = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sort);

        assertEquals(3, top3Users.size());
        assertEquals(3, top3UsersByName.size());
        assertEquals(3, top3UsersByNameTyped.size());
    }

    @Test
    void checkFirstTop() {
        var top3Users = userRepository.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now());
//        assertThat(top3Users).hasSize(3);
        assertEquals(3, top3Users.size());

        var topUser = userRepository.findTopByOrderByIdDesc();
        assertThat(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

    @Test
    void checkUpdate() {
        var ivan = userRepository.getById(1L);
        assertSame(Role.ADMIN, ivan.getRole());

        var resCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resCount);

        var theSameIvan = userRepository.getById(1L);
        assertSame(Role.USER, theSameIvan.getRole());
    }

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
        System.out.println(users);
    }

}
package com.dmdev.spring.integration.service;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.dto.PersonalInfoInterface;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.database.pool.ConnectionPool;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    public static final Integer COMPANY_1 = 1;

    private final UserService userService;
//    private final ConnectionPool pool1;

    @Test
    public void test() {
        System.out.println();
    }

    @Test
    public void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    public void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
                LocalDate.now(),
                new MockMultipartFile("test", new byte[0])
                );

        var actualResult = userService.create(userDto);

        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompanyReadDto().id());
        assertSame(userDto.getRole(), actualResult.getRole()); // Enums should be compared by "=="
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                "test",
                "test",
                Role.ADMIN,
                COMPANY_1,
                LocalDate.now(),
                new MockMultipartFile("test", new byte[0])
                );

        var actualResult = userService.update(USER_1, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
                assertEquals(userDto.getUsername(), user.getUsername());
                assertEquals(userDto.getBirthDate(), user.getBirthDate());
                assertEquals(userDto.getFirstname(), user.getFirstname());
                assertEquals(userDto.getLastname(), user.getLastname());
                assertEquals(userDto.getCompanyId(), user.getCompanyReadDto().id());
                assertSame(userDto.getRole(), user.getRole()); // Enums should be compared by "=="
        });
    }

    @Test
    void delete(){
        assertFalse(userService.delete((-125L)));
        assertTrue(userService.delete(USER_1));
    }
}

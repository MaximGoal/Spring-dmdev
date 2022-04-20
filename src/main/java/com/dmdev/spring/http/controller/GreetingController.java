package com.dmdev.spring.http.controller;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@SessionAttributes({"user"})
public class GreetingController {

//    this method will be invoked every time when this class methods will work
    @ModelAttribute("roles")
    private List<Role> roles () {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model, HttpServletRequest request,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto) { // In this case model of UserReadDto will be created through URL parameters
//        model.addAttribute("user", new UserReadDto(1L, "Ivan"));
        model.addAttribute("user", userReadDto);
        return "greeting/hello";
    }

    @GetMapping("/hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                               @RequestParam Integer age,
                               @RequestHeader String accept,
                               @CookieValue("JSESSIONID") String jsessionId,
                               @PathVariable("id") Integer id) {

        var ageParam = request.getParameter("age");
        var acceptHeader = request.getHeader("accept");
        var cookies = request.getCookies();
        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    @GetMapping("/bye")
    public ModelAndView bye(ModelAndView modelAndView,
                            @SessionAttribute("user") UserReadDto user) {
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }
}

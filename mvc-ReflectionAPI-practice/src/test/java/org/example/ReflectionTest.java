package org.example;

import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));

        logger.debug("beans: [{}]", beans);
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        logger.debug("User all declared fields: [{}]", Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList()));
        logger.debug("User all declared constructors: [{}]", Arrays.stream(clazz.getConstructors()).collect(Collectors.toList()));
        logger.debug("User all declared methods: [{}]", Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList()));
    }

    @Test
    void load() throws ClassNotFoundException {
        // 힙 영역에 로드되어 있는 클래스 타입의 객체 가져오기 1
        Class<User> clazz1 = User.class;

        // 힙 영역에 로드되어 있는 클래스 타입의 객체 가져오기 2
        User user = new User("laphayen", "qwe");
        Class<? extends User> clazz2 = user.getClass();

        // 힙 영역에 로드되어 있는 클래스 타입의 객체 가져오기 3
        Class<?> clazz3 = Class.forName("org.example.model.User");

        logger.debug("clazz: [{}]", clazz1);
        logger.debug("clazz: [{}]", clazz2);
        logger.debug("clazz: [{}]", clazz3);

        assertThat(clazz1 == clazz2).isTrue();
        assertThat(clazz2 == clazz3).isTrue();
        assertThat(clazz3 == clazz1).isTrue();
    }

    private static Set<Class<?>> getTypesAnnotatedWith(List<Class<? extends Annotation>> anootations) {
        Reflections reflections = new Reflections("org.example");

        Set<Class<?>> beans = new HashSet<>();
        anootations.forEach(anootation -> beans.addAll(reflections.getTypesAnnotatedWith(anootation)));

        return beans;
    }
}

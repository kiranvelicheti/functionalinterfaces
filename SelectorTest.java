package com.ford.sumo.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SelectorTest {

    @InjectMocks
    private Selector<Object> selector;
    @Rule
    public ExpectedException expectedException=ExpectedException.none();

    @Test
    public void test_option_should_throw_first_result() {

        Object object1 = new Object();
        Object object2 = new Object();
        Object actual = selector
                .option(object1, () -> true)
                .option(object2, () -> false).orElse(null);

        assertThat(actual).isSameAs(object1);
    }

    @Test
    public void test_option_should_return_second_object() {

        Object object1 = new Object();
        Object object2 = new Object();
        Object actual = selector
                .option(object1, () -> false)
                .option(object2, () -> true).orElse(null);

        assertThat(actual).isSameAs(object2);
    }

    @Test
    public void test_option_should_return_null() {

        Object object1 = new Object();
        Object object2 = new Object();
        Object actual = selector
                .option(object1, () -> false)
                .option(object2, () -> false)
                .orElse(null);

        assertThat(actual).isNull();
    }

    @Test
    public void test_option_should_throwException() {
        expectedException.expect(IllegalArgumentException.class);
        Object object1 = new Object();
        Object object2 = new Object();
       selector
                .option(object1, () -> false)
                .option(object2, () -> false)
                .orElseThrow(IllegalArgumentException::new);

    }
}
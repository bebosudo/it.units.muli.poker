package test.poker;


import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class Foo {

    @Test
    public void foo(){
        assertThat("Hello World", is(CoreMatchers.equalTo("Hello World")));
    }

}

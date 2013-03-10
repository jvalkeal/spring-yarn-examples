package org.springframework.yarn.examples;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

/**
 * Example item reader used in chunk.
 * 
 * @author Janne Valkealahti
 *
 */
@Component("reader")
public class ExampleItemReader implements ItemReader<String> {

    private String[] input = { "Hello world!", null };

    private int index = 0;

    public String read() throws Exception {
        if (index < input.length) {
            return input[index++];
        } else {
            return null;
        }

    }

}

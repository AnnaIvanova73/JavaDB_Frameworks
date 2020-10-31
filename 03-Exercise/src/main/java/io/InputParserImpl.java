package io;

import io.interfaces.InputParser;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Arrays;

public class InputParserImpl extends CustomReader implements InputParser {

    @Override
    public String[] parseStringArray() throws IOException {
        return read().split("\\s+");
    }

    @Override
    public Integer[] parseIntegerArray() throws IOException {
        return Arrays.stream(read().split("\\s+")).mapToInt(Integer::parseInt)
                .boxed().toArray(Integer[]::new);
    }
}

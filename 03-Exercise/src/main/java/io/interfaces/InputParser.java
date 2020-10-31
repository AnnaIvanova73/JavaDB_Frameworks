package io.interfaces;

import java.io.IOException;

public interface InputParser {

     String[] parseStringArray() throws IOException;
     Integer[] parseIntegerArray() throws IOException;
}

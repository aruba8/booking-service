package ca.erik.bs.dao.impl;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class UtilHelper {

    public static String readFile(String fileName) throws IOException {
        return FileUtils.readFileToString(new File(fileName));
    }

}

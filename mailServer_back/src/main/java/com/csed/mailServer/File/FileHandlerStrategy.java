package com.csed.mailServer.File;

import java.util.List;

public interface FileHandlerStrategy {

     <T> List<T> readData(String email, String type, Class<T[]> clazz);

     <T> boolean writeData(String email, String type, List<T> data);

}

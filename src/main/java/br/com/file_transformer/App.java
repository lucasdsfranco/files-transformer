package br.com.file_transformer;


import br.com.file_transformer.exception.InputArgumentDoesNotExistException;
import br.com.file_transformer.service.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InputArgumentDoesNotExistException {

        FileService fileService = new FileService();
        List<Path> filePaths = fileService.getFilePath(args[0]);

    }
}

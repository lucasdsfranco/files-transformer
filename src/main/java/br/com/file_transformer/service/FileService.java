package br.com.file_transformer.service;


import br.com.file_transformer.exception.InputArgumentDoesNotExistException;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileService {

    public List<String> readFile(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }

    public List<Path> getFilePath(String possibleFilePath) throws IOException, InputArgumentDoesNotExistException {

        List<Path> pathFiles = new java.util.ArrayList<>();
        File file = new File(possibleFilePath);

        if(file.exists()){
            if(file.isFile()){
                pathFiles.add(file.getAbsoluteFile().toPath());
            }

            if(file.isDirectory()){
                pathFiles.addAll(getFilePathsFromDir(file.toPath()));
            }

        }else{
            throw new InputArgumentDoesNotExistException("The input argument does not exist.");
        }
        return pathFiles;
    }

    private List<Path> getFilePathsFromDir(Path path) throws IOException {

        DirectoryStream<Path> stream = Files.newDirectoryStream(path);

        List<Path> pathFiles = new java.util.ArrayList<>();
        for (Path entry : stream)
        {
            if (entry.toFile().isFile()){
                pathFiles.add(entry);
            }
        }

        stream.close();
        return pathFiles;
    }

    public Path writeFile(Path filePath, String content) throws IOException {
        return Files.write(Path.of(filePath.toString().concat("_normalized.json")), content.getBytes());
    }

}

package br.com.file_transformer.service;

import br.com.file_transformer.exception.InputArgumentDoesNotExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileServiceTest {

    @Test
        public void shouldReturnAListOfOneFilePathWhenAFilePathIsGivenAsInput() throws IOException, InputArgumentDoesNotExistException {
            //Given
            String inputPath = "src/test/resources/dataFile1.txt";
            FileService fileService = new FileService();

            //When
            List<Path> pathFiles = fileService.getPathFiles(inputPath);

            //Then
            assertEquals(1, pathFiles.size());
            assertTrue(pathFiles.get(0).toString().contains("dataFile1.txt"));
        }

    @Test
    public void shouldReturnAListOfMultiplePathsFilesWhenADirWithFilesIsGivenAsInput() throws IOException, InputArgumentDoesNotExistException {
        //Given
        String inputPath = "src/test/resources/";
        FileService fileService = new FileService();

        //When
        List<Path> pathFiles = fileService.getPathFiles(inputPath);

        //Then
        assertEquals(2, pathFiles.size());
        assertTrue(pathFiles.get(0).toString().contains("dataFile1.txt"));
        assertTrue(pathFiles.get(1).toString().contains("dataFile2.txt"));
    }

    @Test
    public void shouldThrowsExceptionWhenANonexistentFileIsGivenAsInput(){
        //Given
        String inputPath = "this-file-does-not-exist.txt";
        FileService fileService = new FileService();

        //When
        //Then
        Assertions.assertThrows(InputArgumentDoesNotExistException.class, () -> fileService.getPathFiles(inputPath));
    }

}

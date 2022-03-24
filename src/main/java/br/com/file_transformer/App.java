package br.com.file_transformer;


import br.com.file_transformer.exception.InputArgumentDoesNotExistException;
import br.com.file_transformer.model.TransactionRegister;
import br.com.file_transformer.service.ConversionService;
import br.com.file_transformer.service.FileService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InputArgumentDoesNotExistException {

        FileService fileService = new FileService();
        List<Path> filePaths = fileService.getFilePath(args[0]);

        for (Path filePath : filePaths) {
            List<String> lines = fileService.readFile(filePath);

            ConversionService conversionService = new ConversionService();
            List<TransactionRegister> transactionRegisters = new ArrayList<>();
            for (String line : lines) {
                transactionRegisters = conversionService.toModel(line);
            }

            conversionService.normalize(transactionRegisters);

            fileService.writeFile(filePath,  conversionService.normalize(transactionRegisters));
        }
    }
}

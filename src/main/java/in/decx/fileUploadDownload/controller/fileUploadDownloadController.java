package in.decx.fileUploadDownload.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/files")
public class fileUploadDownloadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        System.out.print("inside controller");
        try{
            Path directory = Paths.get("src/main/resources", uploadDir);
            Files.createDirectories(directory);
            File destFile = directory.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
            FileUtils.writeByteArrayToFile(destFile, file.getBytes());
            return ResponseEntity.ok("File uploaded successfully: " + destFile.getAbsolutePath());
        }catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed " + e);
        }
    }

}

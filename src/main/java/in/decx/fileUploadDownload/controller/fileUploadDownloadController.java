package in.decx.fileUploadDownload.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("src/main/resources", uploadDir, fileName);
            File file = filePath.toFile();
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            return ResponseEntity.ok().body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

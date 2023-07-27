package com.example.spring_study.Service;

import com.example.spring_study.Entity.Image;
import com.example.spring_study.Repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public Image save (MultipartFile file) throws IOException {
        Image image = Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .picByte(compressBytes(file.getBytes()))
                .build();
        return fileRepository.save(image);
    }

    public Optional<Image> findById(Long id) {
        return fileRepository.findById(id);
    }

    /** 문자열 압축 */
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);

            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
        return outputStream.toByteArray();
    }

    /** 문자열 압축 풀기 */
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ignored) {
        }

        return outputStream.toByteArray();
    }

}

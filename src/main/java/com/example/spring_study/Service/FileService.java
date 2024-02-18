package com.example.spring_study.Service;

import com.example.spring_study.Entity.Diary;
import com.example.spring_study.Entity.Image;
import com.example.spring_study.Repository.DiaryRepository;
import com.example.spring_study.Repository.FileRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final DiaryRepository diaryRepository;

    public void save(List<MultipartFile> files, Long diaryId) throws IOException {
        Diary diary = diaryRepository.findByDiaryId(diaryId);
        if (diary == null) {
            // 오류 처리 - 해당 ID에 해당하는 일기를 찾지 못한 경우
            return;
        }
        files.forEach(file -> {
            try {
                byte[] bytes = file.getBytes();
                Image image = Image.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .picByte(compressBytes(bytes))
                        .diary(diary)
                        .build();

                fileRepository.save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public Optional<Image> findAllById(Long id) {
        return fileRepository.findAllById(id);
    }


    /**
     * 문자열 압축
     */
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

    /**
     * 문자열 압축 풀기
     */
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

package com.sparta.hotdeal.product.application.service;

import com.sparta.hotdeal.product.domain.entity.product.File;
import com.sparta.hotdeal.product.domain.repository.product.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public File saveFile() {
        File file = fileRepository.save(new File());
        return file;
    }

    @Transactional
    public void deleteFile(File file, String username) {
        file.delete(username);
    }
}

package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.FileService;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String upLoadFile(MultipartFile file, String path) throws IOException {
        String filename = file.getOriginalFilename();
        logger.info("Filename : {}", filename);
        String Filename= UUID.randomUUID().toString();
        String extension=filename.substring(filename.lastIndexOf("."));
        String filenameWithExtension=filename+extension;
        String fullPathWithFileName=path+filenameWithExtension;

        logger.info("Full image path :{}",fullPathWithFileName);
        if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")){

            logger.info("file extension is {} ", extension);
            File folder=new File(path);
            if (!folder.exists()){
                //create
                folder.mkdirs();
            }

            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return filenameWithExtension;
        }else{
            throw new BadApiRequestException("File with this "+ extension+" Not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}

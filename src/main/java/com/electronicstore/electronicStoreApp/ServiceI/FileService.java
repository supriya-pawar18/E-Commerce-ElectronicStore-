package com.electronicstore.electronicStoreApp.ServiceI;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    public String upLoadFile(MultipartFile file,String path) throws IOException;

    public InputStream getResource(String path,String name) throws FileNotFoundException;

}

package org.ecomm.ecommproduct.rest.services.admin;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AdminProductImageService {

    void uploadImageForVariant(List<MultipartFile> files, Integer variant);

}

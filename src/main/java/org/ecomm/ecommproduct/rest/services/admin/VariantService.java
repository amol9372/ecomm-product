package org.ecomm.ecommproduct.rest.services.admin;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VariantService {

    List<?> getFeaturesVariant(int category);
}

package org.ecomm.ecommproduct.rest.services.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecomm.ecommproduct.persistance.entity.EFeatureTemplate;
import org.ecomm.ecommproduct.persistance.repository.FeatureTemplateRepository;
import org.ecomm.ecommproduct.rest.request.admin.AddFeatureTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminFeatureTemplateServiceImpl implements AdminFeatureTemplateService {

  @Autowired ObjectMapper mapper = new ObjectMapper();

  @Autowired FeatureTemplateRepository featureTemplateRepository;

  @Override
  public void addFeatureTemplate(AddFeatureTemplate template) {

    JsonNode features = mapper.valueToTree(template.getFeatures());
    EFeatureTemplate eFeatureTemplate =
        EFeatureTemplate.builder().categoryId(template.getCategoryId()).features(features).build();

    featureTemplateRepository.save(eFeatureTemplate);
  }
}

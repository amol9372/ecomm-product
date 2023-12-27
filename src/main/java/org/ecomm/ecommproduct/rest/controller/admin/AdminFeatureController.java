package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.request.admin.AddFeatureTemplate;
import org.ecomm.ecommproduct.rest.services.admin.AdminFeatureTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/feature-template")
public class AdminFeatureController {

  @Autowired AdminFeatureTemplateService adminFeatureTemplateService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void createFeatures(@RequestBody AddFeatureTemplate featureTemplate) {
    adminFeatureTemplateService.addFeatureTemplate(featureTemplate);
  }
}

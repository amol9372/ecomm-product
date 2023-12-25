package org.ecomm.ecommproduct.rest.services.admin;

import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.request.admin.AddCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminCategoryService {


    List<Category> getCategories();

    void addCategory(AddCategory category);

    void deleteCategory(Integer id);
}

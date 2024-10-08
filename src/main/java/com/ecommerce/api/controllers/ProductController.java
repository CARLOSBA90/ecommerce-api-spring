package com.ecommerce.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.api.dtos.ProductDTO;
import com.ecommerce.api.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;

    public ProductController(ProductService productService, PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler) {
        this.productService = productService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> pagedResult = productService.getAllProducts(pageable);
        PagedModel<EntityModel<ProductDTO>> pagedModel = pagedResourcesAssembler.toModel(pagedResult);

        if (pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pagedModel);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/description")
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> findByDescription(
            @RequestParam String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> pagedResult = productService.getProductsByDescription(description, pageable);
        PagedModel<EntityModel<ProductDTO>> pagedModel = pagedResourcesAssembler.toModel(pagedResult);

        if (pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pagedModel);
        }
    }
}

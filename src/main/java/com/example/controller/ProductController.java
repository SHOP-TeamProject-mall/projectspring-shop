package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Product;
import com.example.entity.ProductMainImage;
import com.example.entity.ProductSubImage;
import com.example.repository.ProductMainImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.ProductSubImageRepository;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/product")
public class ProductController {
    
    @Autowired
    ProductService pService;

    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductMainImageRepository productMainImageRepository;

    @Autowired
    ProductSubImageRepository productSubImageRepository;

    //127.0.0.1:8080/HOST/product/insertproduct.json
    @PostMapping(value="/insertproduct.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertProduct(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try{
            Product product2 = new Product();
            Double psale = product.getProductsale() / 100;
            product2.setProductbrand(product.getProductbrand());
            product2.setProductprice(product.getProductprice());
            product2.setProductcategory(product.getProductcategory());
            product2.setProductdeliveryfee(product.getProductdeliveryfee());
            product2.setProductfabric(product.getProductfabric());
            product2.setProductfinalprice(product.getProductfinalprice());
            product2.setProductquantity(product.getProductquantity());
            product2.setProducttitle(product.getProducttitle());
            product2.setProductsale(psale);

            long no = pService.inserProduct(product2);

            map.put("status", 200);
            map.put("no", no);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 메인 이미지 한개등록
    //127.0.0.1:8080/HOST/product/insertproduct_mainimage.json
    @PostMapping(value="/insertproduct_mainimage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertProduct_MainImage(
        @RequestParam(name = "productno") long productno,
        @RequestParam(name = "product_mainfile") MultipartFile files) throws IOException {
        
            Map<String, Object> map = new HashMap<>();
            try{
                Product product = pRepository.getById(productno);
                if(files != null){
                    ProductMainImage productMainImage = new ProductMainImage();
                        productMainImage.setProduct(product);
                        productMainImage.setProductmainimage(files.getBytes());
                        productMainImage.setProductmainimagename(files.getOriginalFilename());
                        productMainImage.setProductmainimagesize(files.getSize());
                        productMainImage.setProductmainimagetype(files.getContentType());
                        productMainImageRepository.save(productMainImage);
                }
                else{
                    ProductMainImage productMainImage = new ProductMainImage();
                        productMainImage.setProduct(product);
                        productMainImage.setProductmainimage(null);
                        productMainImage.setProductmainimagename("");
                        productMainImage.setProductmainimagesize(0L);
                        productMainImage.setProductmainimagetype("");
                        productMainImageRepository.save(productMainImage);
                }
                map.put("mainimage",200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
        
        return map;
    }
    // 서브 이미지 첫번째 한개등록
    //127.0.0.1:8080/HOST/product/insertproduct_firstsubimage.json
    // @PostMapping(value="/insertproduct_firstsubimage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // public Map<String, Object> InsertProduct_FirstSubImage(
    //     @RequestParam(name = "productno") long productno,
    //     @RequestParam(name = "product_firstsubfile") MultipartFile files) throws IOException {
    //         Map<String, Object> map = new HashMap<>();
    //         try{
    //             Product product = pRepository.getById(productno);
    //             if(files != null){
    //                 ProductSubImage productSubImage = new ProductSubImage();
    //                     productSubImage.setProduct(product);
    //                     productSubImage.setProductsubimage(files.getBytes());
    //                     productSubImage.setProductsubimagename(files.getOriginalFilename());
    //                     productSubImage.setProductsubimagesize(files.getSize());
    //                     productSubImage.setProductsubimagetype(files.getContentType());
    //                     productSubImageRepository.save(productSubImage);
    //             }
    //             else{
    //                 ProductSubImage productSubImage = new ProductSubImage();
    //                     productSubImage.setProduct(product);
    //                     productSubImage.setProductsubimage(null);
    //                     productSubImage.setProductsubimagename("");
    //                     productSubImage.setProductsubimagesize(0L);
    //                     productSubImage.setProductsubimagetype("");
    //                     productSubImageRepository.save(productSubImage);
    //             }
    //             map.put("firstsubimage", 200);
    //         }
    //         catch(Exception e){

    //         }
    //         return map;
    //     }

    // 서브 이미지 여러개 등록
    //127.0.0.1:8080/HOST/product/insertproduct_subimage.json
    @PostMapping(value="/insertproduct_subimage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertProduct_SubImage(
        @RequestParam(name = "productno") long productno,
        @RequestParam(name = "product_subfile") MultipartFile[] files) throws IOException {

            // System.out.println("productno : -> " + productno);

            Map<String, Object> map = new HashMap<>();
            try{
                Product product = pRepository.getById(productno);
                List<ProductSubImage> list = new ArrayList<>();
                for(int i = 0; i < files.length; i++){
                    ProductSubImage productSubImage = new ProductSubImage();
                        productSubImage.setProduct(product);
                        productSubImage.setProductsubimage(files[i].getBytes());
                        productSubImage.setProductsubimagename(files[i].getOriginalFilename());
                        productSubImage.setProductsubimagesize(files[i].getSize());
                        productSubImage.setProductsubimagetype(files[i].getContentType());
                        list.add(productSubImage);
                }
                productSubImageRepository.saveAll(list);
                map.put("subimage", 200);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }

        return map;
    }
    
}

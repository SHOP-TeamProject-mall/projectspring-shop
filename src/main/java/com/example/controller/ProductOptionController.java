package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Product;
import com.example.entity.ProductOption;
import com.example.entity.ProductOptionImage;
import com.example.repository.ProductOptionImageRepository;
import com.example.repository.ProductOptionRepository;
import com.example.repository.ProductRepository;
import com.example.service.ProductOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import oracle.net.aso.e;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/productoption")
public class ProductOptionController {

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOptionService productOptionService;

    @Autowired
    ProductOptionImageRepository productOptionImageRepository;
    
    // 127.0.0.1:8080/HOST/productoption/insert_productoption.json
    @PostMapping(value="/insert_productoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> InsertProductOption (
        @RequestBody ProductOption productOption,
        @RequestParam(name = "productno") long productno ) throws IOException {
            System.out.println("fdsfdsf");
        Map<String,Object> map = new HashMap<>();
        try{
            Product product = productRepository.findByProductno(productno);
            productOption.setProduct(product);

            ProductOption productOption2 = productOptionService.inserProductOption(productOption);
            map.put("status", 200);
            map.put("option", productOption2);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    // 127.0.0.1:8080/HOST/select_productoption.json?productno=
    @GetMapping(value="/select_productoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectProductOption(
        @RequestParam(name = "productno") long productno ) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try{
            List<ProductOption> list = productOptionService.selectProductOption_productno(productno);
            map.put("list", list);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/productoption/insert_productoption_image.json
    @PostMapping(value="/insert_productoption_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertProduct_OptionImage(
        @RequestParam(name = "productoptionno") long productoptionno,
        @RequestParam(name = "product_optionfile") MultipartFile files) throws IOException{

        Map<String, Object> map = new HashMap<>();
        try{
            System.out.println("23232");
            ProductOption productoption = productOptionRepository.getById(productoptionno);
            if(files != null){
                ProductOptionImage productOptionImage = new ProductOptionImage();
                    productOptionImage.setProductOption(productoption);
                    productOptionImage.setProductoptionimage(files.getBytes());
                    productOptionImage.setProductoptionimagename(files.getOriginalFilename());
                    productOptionImage.setProductoptionimagesize(files.getSize());
                    productOptionImage.setProductoptionimagetype(files.getContentType());
                    productOptionImageRepository.save(productOptionImage);
            }
            else{
                ProductOptionImage productOptionImage = new ProductOptionImage();
                    productOptionImage.setProductOption(productoption);
                    productOptionImage.setProductoptionimage(null);
                    productOptionImage.setProductoptionimagename("");
                    productOptionImage.setProductoptionimagesize(0L);
                    productOptionImage.setProductoptionimagetype("");
                    productOptionImageRepository.save(productOptionImage);
            }
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        
        return map;
    }

    // 127.0.0.1:8080/HOST/productoption/select_productoption_image.json?productoptionno=
    @GetMapping(value="/select_productoption_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> SelectProduct_OptionImage(@RequestParam(name = "productoptionno") long productoptionno) throws IOException{
        try{
            ProductOptionImage productOptionImage = productOptionImageRepository.findByProductOption_productoptionno(productoptionno);
            if(productOptionImage.getProductoptionimage().length > 0){
                HttpHeaders headers = new HttpHeaders();
                if(productOptionImage.getProductoptionimagetype().equals("image/jpeg")){
                    headers.setContentType(MediaType.IMAGE_JPEG);
                }
                else if(productOptionImage.getProductoptionimagetype().equals("image/png")){
                    headers.setContentType(MediaType.IMAGE_PNG);
                }
                else if(productOptionImage.getProductoptionimagetype().equals("image/gif")){
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(productOptionImage.getProductoptionimage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }

    // 옵션정보 변경
    // 127.0.0.1:8080/HOST/productoption/update_productoption.json?productoptionno=
    @PutMapping(value="/update_productoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> UpdateProductoption(
        @RequestBody ProductOption productOption,
        @RequestParam(name = "productoptionno") long productoptionno) throws IOException{
        Map<String, Object> map = new HashMap<>();
        try{
            ProductOption productOption2 = productOptionRepository.findByProductoptionno(productoptionno);
            productOption2.setProductoptionname(productOption.getProductoptionname());
            productOption2.setProductoptioncolor(productOption.getProductoptioncolor());
            productOption2.setProductoptionsize(productOption.getProductoptionsize());
            productOption2.setProductoptionadditionalamount(productOption.getProductoptionadditionalamount());

            productOptionService.UpdateProductOption(productOption2);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 옵션이미지 변경
    // 127.0.0.1:8080/HOST/productoption/update_productoptionImage.json?productoptionno=
    @PostMapping(value="/update_productoptionImage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> UpdateProductoptionImage(
        @RequestParam(name = "productoptionno") long productoptionno,
        @RequestParam(name = "updateproductoptionimage") MultipartFile files) throws IOException{
        Map<String, Object> map = new HashMap<>();
        try{
            if(files != null){
                ProductOptionImage productOptionImage = productOptionImageRepository.findByProductOption_productoptionno(productoptionno);
                productOptionImage.setProductoptionimage(files.getBytes());
                productOptionImage.setProductoptionimagename(files.getOriginalFilename());
                productOptionImage.setProductoptionimagesize(files.getSize());
                productOptionImage.setProductoptionimagetype(files.getContentType());
                productOptionImageRepository.save(productOptionImage);
            }
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 옵션삭제
    // 127.0.0.1:8080/HOST/productoption/delete_productoption.json?productno=
    @DeleteMapping(value="/delete_productoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> DeleteProductOption(
        @RequestParam(name = "productoptionno") long productoptionno) throws IOException{
            Map<String, Object> map = new HashMap<>();
        try{
            ProductOption productOption = productOptionRepository.findByProductoptionno(productoptionno);
            long no = productOption.getProductoptionno();
            productOptionRepository.deleteById(no);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}


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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
                        productSubImage.setProductsubimageidx(i+1);
                        list.add(productSubImage);
                        // System.out.println(i + "i입니다");
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

    // 전체화면 조회 (제목별 / 브랜드별)
    //127.0.0.1:8080/HOST/product/select_product.json?page=${this.page}&title=?&brand=
    @GetMapping(value="/select_product.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Select_Product(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "title", defaultValue = "") String title,
        @RequestParam(name = "brand", defaultValue = "") String brand) {
            PageRequest pageable = PageRequest.of(page - 1, 3);

            List<Product> list = pRepository.findByProducttitleIgnoreCaseContainingOrProductbrandIgnoreCaseContainingOrderByProductnoDesc(title, brand, pageable);

            long pages = pRepository.countByProducttitleContaining(title);

            Map<String, Object> map = new HashMap<>();
            try{
                map.put("ppage", (pages - 1) / 3 + 1);
                map.put("list", list);
            }
            catch(Exception e){
                e.printStackTrace();
                map.put("status", e.hashCode());
            }
        return map;
    }

    // 메인이미지 조회
    // 127.0.0.1:8080/HOST/product/select_productmain_image.json
    @GetMapping(value="/select_productmain_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> SelectMainImage(@RequestParam(name = "productno") long productno) throws IOException {
        try{
            ProductMainImage productMainImage = productMainImageRepository.findByProduct_productno(productno);
            if(productMainImage.getProductmainimage().length > 0){
                HttpHeaders headers = new HttpHeaders();
                if(productMainImage.getProductmainimagetype().equals("image/jpeg")){
                    headers.setContentType(MediaType.IMAGE_JPEG);
                }
                else if(productMainImage.getProductmainimagetype().equals("image/png")){
                    headers.setContentType(MediaType.IMAGE_PNG);
                }
                else if(productMainImage.getProductmainimagetype().equals("image/gif")){
                    headers.setContentType(MediaType.IMAGE_GIF);
                }

                ResponseEntity<byte[]> response = new ResponseEntity<>(productMainImage.getProductmainimage(), headers, HttpStatus.OK);
                return response;
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }

    // 서브이미지 조회
    // 127.0.0.1:8080/HOST/product/select_productsub_image.json
    @GetMapping(value="/select_productsub_image.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> SelectSubImage(
        @RequestParam(name = "productno") long productno,
        @RequestParam(name = "productidx") int productidx ) throws IOException {
        try{
            
            ProductSubImage productSubImage = productSubImageRepository.findByProduct_productnoAndProductsubimageidx(productno, productidx);
            // System.out.print(productSubImage.toString());
            
            for(int i=0; i<productSubImage.getProductsubimage().length; i++){
                if(productSubImage.getProductsubimage().length > 0){
                    HttpHeaders headers = new HttpHeaders();
                    if(productSubImage.getProductsubimagetype().equals("image/jpeg")){
                        headers.setContentType(MediaType.IMAGE_JPEG);
                    }
                    else if(productSubImage.getProductsubimagetype().equals("image/png")){
                        headers.setContentType(MediaType.IMAGE_PNG);
                    }
                    else if(productSubImage.getProductsubimagetype().equals("image/gif")){
                        headers.setContentType(MediaType.IMAGE_GIF);
                    }
                    ResponseEntity<byte[]> response = new ResponseEntity<>(productSubImage.getProductsubimage(), headers, HttpStatus.OK);
                    return response;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }

    // 상품 카테고리별 조회
    // 127.0.0.1:8080/HOST/product/select_product_category.json?productcategory=
    @GetMapping(value = "/select_product_category.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Select_Product_Category(
            @RequestParam(name = "productcategory", defaultValue = "") String productcategory,
            @RequestParam(name = "menu") int menu) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try{
            if(menu == 1){
                productcategory.equalsIgnoreCase("남성상의");
                List<Product> masculine = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(masculine);
                map.put("masculine", masculine);
            }
            if(menu == 2){
                productcategory.equalsIgnoreCase("남성하의");
                List<Product> mensbottoms = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(mensbottoms);
                map.put("mensbottoms", mensbottoms);
            }
            if(menu == 3){
                productcategory.equalsIgnoreCase("남성외투");
                List<Product> mensovercoat = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(mensovercoat);
                map.put("mensovercoat", mensovercoat);
            }
            if(menu == 4 ){
                productcategory.equalsIgnoreCase("남성속옷");
                List<Product> mensunderwear = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(mensunderwear);
                map.put("mensunderwear", mensunderwear);
            }
            if(menu == 5 ){
                productcategory.equalsIgnoreCase("여성상의");
                List<Product> femaletop = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(femaletop);
                map.put("femaletop", femaletop);
            }
            if(menu == 6 ){
                productcategory.equalsIgnoreCase("여성하의");
                List<Product> womensbottoms = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(womensbottoms);
                map.put("womensbottoms", womensbottoms);
            }
            if(menu == 7 ){
                productcategory.equalsIgnoreCase("여성외투");
                List<Product> womensovercoat = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(womensovercoat);
                map.put("womensovercoat", womensovercoat);
            }
            if(menu == 8 ){
                productcategory.equalsIgnoreCase("여성속옷");
                List<Product> womensunderwear = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(womensunderwear);
                map.put("womensunderwear", womensunderwear);
            }
            if(productcategory.equalsIgnoreCase("상품1")){
                List<Product> product1 = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(product1);
                map.put("product1", product1);
            }
            if(productcategory.equalsIgnoreCase("상품2")){
                List<Product> product2 = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(product2);
                map.put("product2", product2);
            }
            if(productcategory.equalsIgnoreCase("상품3")){
                List<Product> product3 = pRepository.findByProductcategoryIgnoreCaseContaining(productcategory);
                // System.out.println(product3);
                map.put("product3", product3);
            }
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/product/selectone_product.json?productno=
    @GetMapping(value="/selectone_product.json" , consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectOneProduct(@RequestParam(name = "productno") long productno) {
        Map<String, Object> map = new HashMap<>();
        try{
            Product product = pService.selectOneProduct(productno);
            map.put("list", product);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 신상품 상품번호별 마지막 3개조회 // 질문
    // 127.0.0.1:8080/HOST/product/selectthree_newproduct.json
    @GetMapping(value="/selectthree_newproduct.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> Select_NewProduct(@RequestParam(name = "title", defaultValue = "") String title) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<Product> product = pService.selectProductList();
            
            System.out.println(product.size());
            for(int i=0; i<3; i++){
                Product product1 = new Product();
                product1.getProductno();
            }
            map.put("product", product);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    
}

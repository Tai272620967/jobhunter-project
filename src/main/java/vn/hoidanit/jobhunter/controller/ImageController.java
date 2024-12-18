// package vn.hoidanit.jobhunter.controller;

// import java.nio.file.Paths;

// import org.springframework.core.io.UrlResource;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.core.io.Resource;
// import java.nio.file.Path;

// @RestController
// public class ImageController {
//     // Endpoint để lấy hình ảnh
//     @GetMapping("/uploads/images/{filename}")
//     public ResponseEntity<Resource> getImage(@PathVariable String filename) {
//         System.out.println("get image");
//         try {
//             Path filePath = Paths.get("uploads/images").resolve(filename).normalize();
//             Resource resource = new UrlResource(filePath.toUri());

//             if (resource.exists() && resource.isReadable()) {
//                 return ResponseEntity.ok(resource);
//             } else {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                         .body(null); // Trả về 404 nếu không tìm thấy hình ảnh
//             }
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(null); // Trả về lỗi 500 nếu có sự cố khi đọc tệp
//         }
//     }
// }

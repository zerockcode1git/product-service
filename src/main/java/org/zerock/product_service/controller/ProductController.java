package org.zerock.product_service.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Log4j2
public class ProductController {

  @Value("${app.title}")
  private String title;
  @Value("${app.subtitle}")
  private String subtitle;

  @Value("${app.awstitle}")
  private String awstitle;



 @PostConstruct
 public void init() {
     log.info("ProductController....................");
     log.info("title: " + title);
     log.info("subtitle: " + subtitle);
     log.info("awstitle: " + awstitle);
 }


  @GetMapping("hello")
  public String[] hello() {

    log.info("---------------------");

    return new String[]{
      "Hello",
      "Product",
      "Service"
    };
  }

  @GetMapping("hello2")
 public String[] hello2(@RequestHeader(value = "X-Authenticated", required = false) boolean authenticated) {

   log.info("hello2----------");

   log.info("X-Authenticated: " + authenticated);

   return new String[]{"Hello", "Product", "Service"};
 }

@GetMapping("helloTime")
 public ResponseEntity<String> helloTime(@RequestParam("time") int time) {

   log.info("helloTime----------");
   log.info("time: " + time);

   if(time < 0 || time > 5) {
     return ResponseEntity.badRequest().body("Time must be between 0 and 5 seconds");
   }
   try {
     Thread.sleep(time * 1000);
   } catch (InterruptedException e) {
     Thread.currentThread().interrupt();
   }

   return ResponseEntity.ok("Hello Time: " + time); 

 }

  @GetMapping("{pno}")
 public ResponseEntity<Map<String, Object>> getOne(@PathVariable("pno") Long pno) {

   if(pno > 100) {
     return ResponseEntity.badRequest().body(
       Map.of("error", "Product number must be less than or equal to 100"));
   } 

   return ResponseEntity.ok(
     Map.of(
       "pno", pno,
       "pname", "Sample Product",
       "price", 1000,
       "regDate", java.time.LocalDateTime.now()));
 }


}

package com.itac.login.control;

import com.itac.login.entity.store.Store;
import com.itac.login.service.MemberService;
import com.itac.login.service.StoreService;
import com.itac.login.dto.StoreDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@Slf4j
public class StoreController {

    String url ="http://localhost:8080";

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/info")
    public void store(HttpServletRequest req, Authentication auth, HttpServletResponse resp) throws IOException {
        // 추후 AuthSuccessHandler을 통해 제한
        try{
            if(auth.getAuthorities() != null){
                String email = req.getUserPrincipal().getName();
                if(!memberService.getAuth(email).equals("store")){
                    resp.sendRedirect(url+"/mypage");

                }else{
                    resp.sendRedirect(url+"/storePage");
                }
            }
        }catch(Exception e){
            resp.sendRedirect(url+"/login");
        }
    }
    
    @GetMapping("/list")
    public ResponseEntity<Object> list(){
        
        return ResponseEntity.status(HttpStatus.OK).body(storeService.allStores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> store(@PathVariable Long id){
        Store store = storeService.show(id);

        return ResponseEntity.status(HttpStatus.OK).body(store);
    }

    @PostMapping(value="/create")
    public ResponseEntity<Store> Create(HttpServletRequest req, @RequestPart("store") StoreDto storeDto, @RequestPart("file")List<MultipartFile> multipartFile) throws IOException{
        String user = req.getUserPrincipal().getName();
        Store store = storeService.create(storeDto, user, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(store);

    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(HttpServletRequest req, @PathVariable Long id, @RequestPart("store") StoreDto storeDto, @RequestPart("file")List<MultipartFile> multipartFile){
        String user = req.getUserPrincipal().getName();
        boolean result = storeService.update(user, id, storeDto, multipartFile);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body(storeDto);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Store> delete(@PathVariable Long id, HttpServletRequest req){
        String user = req.getUserPrincipal().getName();
        boolean result = storeService.delete(id, user);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}

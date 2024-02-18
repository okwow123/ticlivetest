package com.itac.login.service;

import com.itac.login.dto.StoreDto;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import com.itac.login.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {

    // 파일 저장 위치
    private final String imageDir = "c:/temp";

    //멤버
    private final UserRepository userRepo;

    //

    private final StoreRepository storeRepository;
    private Sort gradeDesc = Sort.by(Sort.Direction.DESC, "grade");

    public List<Store> allStores() {
        return storeRepository.findAll(gradeDesc);
    }

    public List<Store> searchWithWord(String searchWord) {

        return storeRepository.findByStoreNameContaining(searchWord);
    }

    @GetMapping("/location/{locationWord}")
    public List<Store> searchWithLocation(String locationWord){
        return storeRepository.findByStoreLocationContaining(locationWord);
    }

    public Store create(StoreDto storeDto, String user,List<MultipartFile> files){
        storeDto.setStoreNum(null);
        storeDto.setUsers(userRepo.findByUserEmail(user));
        try{
            List<String> images = new ArrayList<String>();
            for(MultipartFile file : files){
                UUID id = UUID.randomUUID();
                String imageName = id + "_" + file.getOriginalFilename();
                String filePath = imageDir + "/" + id.toString().charAt(0) + "/";
                File folder = new File(filePath);
                if(!folder.exists()) {
                    try {
                        folder.mkdir();
                    } catch (Exception e) {

                    }
                }

                File dest = new File(filePath+imageName);
                file.transferTo(dest);
                images.add(imageName);
            }
            storeDto.setImages(images);

        }catch(Exception e){
            log.info(e.getMessage());
        }
        Store store = storeDto.toEntity();
        return storeRepository.save(store);
    }

    public boolean update(Long id, StoreDto dto){

        Store store = dto.toEntity();
        Store target = storeRepository.findById(id).orElse(null);
        if(target == null || !dto.getStoreNum().equals(id)){
            return false;
        }

        storeRepository.save(store);

        return true;
    }

    public Store show(Long id){

        return storeRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id){

        Store target = storeRepository.findById(id).orElse(null);
        if(target!=null){
            storeRepository.delete(target);
            return true;
        }
        return false;

    }
}
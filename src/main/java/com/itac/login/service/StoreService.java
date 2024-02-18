package com.itac.login.service;

import com.itac.login.dto.StoreDto;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import com.itac.login.entity.user.UserRepository;
import com.itac.login.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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

    private final StoreRepository storeRepository;

    private Sort gradeDesc = Sort.by(Sort.Direction.DESC, "grade");
    private Sort gradeAsc = Sort.by(Sort.Direction.ASC, "grade");

    public List<Store> allStores() {
        return storeRepository.findAll(gradeAsc);
    }

    public List<Store> searchWithWord(String searchWord) {
        return storeRepository.findByStoreNameContaining(searchWord);
    }

    public List<Store> searchWithLocation(String locationWord){
        return storeRepository.findByStoreLocationContaining(locationWord);
    }

    public Store create(StoreDto storeDto, String user,List<MultipartFile> files){
        storeDto.setStoreNum(null);
        storeDto.setUsers(userRepo.findByUserEmail(user));
        try{
            List<String> images = new ArrayList<String>();
            for(MultipartFile file : files){
                UUID uuid = UUID.randomUUID();
                String imageName = uuid + "_" + file.getOriginalFilename();
                String filePath = imageDir + "/" + uuid.toString().charAt(0) + "/";
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

    public boolean update(String user, Long id, StoreDto storeDto, List<MultipartFile> files){

        // 가게 정보 여부 확인
        Store target = storeRepository.findById(id).orElse(null);
        if(target == null || !storeDto.getStoreNum().equals(id)){
            return false;
        }

        // 수정자 확인
        if(!checkUser(id, user)) { return false; }

        // 이미지 정보 확인 및 추가
        // 1. 드라이브에서 이미지 가져오기
        // 2.
        //if()
/*

        try{
            List<String> images = new ArrayList<String>();
            for(MultipartFile file : files){
                UUID uuid = UUID.randomUUID();
                String imageName = uuid + "_" + file.getOriginalFilename();
                String filePath = imageDir + "/" + uuid.toString().charAt(0) + "/";
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
*/

        Store store = storeDto.toEntity();
        storeRepository.save(store);

        return true;
    }

    public Store show(Long id){

        return storeRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id, String user){

        // 권한 확인
        /*if(!checkUser(id, user)){
            return false;
        }*/

        // 제거 가게 확인
        Store target = storeRepository.findById(id).orElse(null);

        // 정상 작동
        if(target!=null){
            // 이미지 제거
            try{
                long imageNum = target.getImages().size();
                for(int i=0; i<imageNum; i++) {
                    File folder = new File(imageDir+"/"+target.getImages().get(i).charAt(0));
                    if(folder.isDirectory() && folder.exists()){
                        File file = new File(target.getImages().get(i));
                        file.delete();
                    }
                }

            }catch(Exception e){

            }

            storeRepository.delete(target);

            return true;
        }
        return false;
    }

    private boolean checkUser(Long id, String user){

        Users users = userRepo.findByUserEmail(user);
        Store createdUser = storeRepository.createUser(id);

        return users.equals(createdUser.getUsers());
    }


}
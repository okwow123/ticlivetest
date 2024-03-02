package com.itac.login.service;

import com.itac.login.dto.StoreDto;
import com.itac.login.entity.reservation.Reservation;
import com.itac.login.entity.reservation.ReservationRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.store.StoreRepository;
import com.itac.login.entity.user.UserRepository;
import com.itac.login.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.DocValueFormat;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private final ReservationRepository reservationRepository;

    private Sort gradeDesc = Sort.by(Sort.Direction.DESC, "grade");
    private Sort gradeAsc = Sort.by(Sort.Direction.ASC, "grade");

    public List<Store> allStores() {
        return storeRepository.findAll(gradeAsc);
    }

    public List<Store> manageStoreInfo(long usernum){
        return storeRepository.findByManageStore(usernum);
    }

    public List<Store> searchWithWord(String searchWord) {
        return storeRepository.findByStoreNameContaining(searchWord);
    }

    public List<Store> searchWithLocation(String locationWord) {
        return storeRepository.findByStoreLocationContaining(locationWord);
    }

    public Store create(StoreDto storeDto, String user, List<MultipartFile> files) {
        storeDto.setStoreNum(null);
        storeDto.setGrade(0f);
        storeDto.setUsers(userRepo.findByUserEmail(user));
        if(files != null){
            storeDto.setImages(uuidImage(files));
        }else{
            List<String> empty = new ArrayList<>();
            storeDto.setImages(empty);
        }

        Store store = storeDto.toEntity();
        return storeRepository.save(store);
    }

    public boolean update(String user, Long id, StoreDto storeDto, List<MultipartFile> files) {

        // 가게 정보 여부 확인
        Store target = storeRepository.findById(id).orElse(null);
        if (target == null || !target.getStoreNum().equals(id)) {
            return false;
        }

        // 수정자 확인
        if (!checkUser(id, user)) {
            return false;
        }
        // 기존 이미지 제거
        imageDel(target);

        // 이미지 저장
        storeDto.setStoreNum(id);
        storeDto.setImages(uuidImage(files));
        Store store = storeDto.toEntity();
        storeRepository.save(store);

        return true;
    }

    public Store show(Long id) {
        return storeRepository.findById(id).orElse(null);
    }

    public boolean delete(Long id, String user) {

        // 권한 확인
        if (!checkUser(id, user)) {
            return false;
        }

        // 제거 가게 확인
        Store target = storeRepository.findById(id).orElse(null);

        // 이미지 제거 로직
        if (target != null) {
            boolean result = imageDel(target);
            System.out.println("result : " + result);
            if(result){
                storeRepository.delete(target);
                return true;
            }
        }
        return false;
    }

    private boolean checkUser(Long id, String user) {

        Users users = userRepo.findByUserEmail(user);
        Store createdUser = storeRepository.createUser(id);

        return users.equals(createdUser.getUsers());
    }

    // 이미지 생성
    private List<String> uuidImage(List<MultipartFile> files) {
        try {
            List<String> images = new ArrayList<>();
            for (MultipartFile file : files) {
                UUID uuid = UUID.randomUUID();
                String imageName = uuid + "_" + file.getOriginalFilename();
                String filePath = imageDir + "/" + uuid.toString().charAt(0) + "/";
                File folder = new File(filePath);
                if (!folder.exists()) {
                    try {
                        folder.mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                File dest = new File(filePath + imageName);
                file.transferTo(dest);
                images.add(imageName);
            }
            return images;

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return null;
    }

    // 이미지 제거
    private boolean imageDel(Store target) {
        try {
            long imageNum = target.getImages().size();
            for (int i = 0; i < imageNum; i++) {
                String dir = imageDir + "/" + target.getImages().get(i).charAt(0);
                // 디렉토리 및 파일 확인 로직
                File folder = new File(dir);
                if (folder.isDirectory() && folder.exists()) {
                    // 이미지 파일 지정
                    File file = new File(folder + "/" + target.getImages().get(i));
                    // 이미지 제거
                    if (file.delete()) {
                        log.info("이미지 제거");
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public List<LocalTime> getReservableTimes(Long storeNum, LocalDate anticipatedDate){
        List<LocalTime> reservableTimes = new ArrayList<>();
        Store store = storeRepository.findById(storeNum).orElse(null);
        //store가 null일때 또는 reservable이 0일때 예외처리가 필요
        //openingtime, closingtime이 null일떄를 체크해야함   
        
        //오프닝 시간
        LocalTime openingtime = store.getOpeningtime();
        if(openingtime == null){
            openingtime = LocalTime.of(9,0);
        }
        openingtime = openingtime.plusHours(1);
        
        //클로징 시간
        LocalTime closingtime = store.getClosingtime();
        if(openingtime == null){
            openingtime = LocalTime.of(18,0);
        }

        for (LocalTime time = openingtime; time.isBefore(closingtime); time = time.plusHours(1)) {
            reservableTimes.add(time);
        }
        //기본 예약가능한 시간들에서 예약된 시간들을 빼야함
        List<Reservation> removerList = reservationRepository.findAllReservedTimesByStoreNumberAndDateTime(storeNum,anticipatedDate);
        System.out.println(removerList);
        System.out.println(removerList);

        HashSet<LocalTime> reservedTimes = new HashSet<>();

        for(Reservation r : removerList){
            reservedTimes.add(r.getReservationTime());
        }

        reservableTimes.removeIf(reservable-> reservedTimes.contains(reservable));
        log.info(reservedTimes.toString());

        return reservableTimes;
    }

    public List<LocalTime> getReservableTimes(Store store,LocalDate anticipatedDate) {
        return getReservableTimes(store.getStoreNum(),anticipatedDate);
    }

    public Store findById(Long storeNum){
        return storeRepository.findById(storeNum).orElse(null);
    }




}
package com.itac.login.control;

import com.itac.login.common.util.SortMethod;
import com.itac.login.entity.store.Store;
import com.itac.login.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiSearchController {

    private final StoreService storeService;
    private final SortMethod sortMethod;

    @GetMapping(value={"/search","/location","/category"})
    public ResponseEntity<Object> allStores(){
        return ResponseEntity.ok().body(storeService.allStores());
    }

    //검색어로 검색
    @GetMapping("/search/{searchWord}")
    public ResponseEntity<Object> searchWithWord(@PathVariable String searchWord){
        List<Store> storeList = storeService.searchWithWord(searchWord);
        if(!storeList.isEmpty()){
            return ResponseEntity.ok().body(storeList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 데이터 없음");
    }
    //거리별 가까운순

    //현재 위치와 같은 순
    @GetMapping("/location/{locationWord}")
    public ResponseEntity<Object> searchWithLocation(@PathVariable String locationWord){
        List<Store> storeList = storeService.searchWithLocation(locationWord);
        if(!storeList.isEmpty()){
            return ResponseEntity.ok().body(storeList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 데이터 없음");
    }

    //카테고리로 검색
    //카테고리가 아직 없기에 비활성화
//    @GetMapping("/category/{categoryWord}")
//    public ResponseEntity<Object> searchWithCategory(@PathVariable String categoryWord){
//        List<Store> storeList = storeRepository.findByStoreCategoryContaining(categoryWord);
////        List<Store> storeList = storeRepository.findAll();
//        if(!storeList.isEmpty()){
//            return ResponseEntity.ok().body(storeList);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 데이터 없음");
//    }

    //평점이 좋은순


    //추천 알고리즘이 정상적으로 동작하는지 확인하는 TestMapping
    @GetMapping("/searchrecommend")
    public ResponseEntity<Object> searchrecommend(){
        List<Store> storeList = storeService.allStores();

        log.info("searchrecommend Mapping에서 조회된 List<Store> value:"+ storeList.toString());
        storeList = sortMethod.recommendfy(storeList);

        if(!storeList.isEmpty()){
            return ResponseEntity.ok().body(storeList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("조회된 데이터 없음");
    }
}

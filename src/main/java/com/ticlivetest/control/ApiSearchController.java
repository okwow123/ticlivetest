package com.ticlivetest.control;

import com.ticlivetest.common.util.SortMethod;
import com.ticlivetest.dto.UserDto;
import com.ticlivetest.entity.reservation.Reservation;
import com.ticlivetest.entity.store.Store;
import com.ticlivetest.entity.user.Users;
import com.ticlivetest.service.ReservationService;
import com.ticlivetest.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.StringTokenizer;


@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiSearchController {

    private final StoreService storeService;
    private final SortMethod sortMethod;
    private final ReservationService reservationService;

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

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations(){
        List<Reservation> reservations = reservationService.allReservations();
        log.info("reservations : "+reservations);

        return reservations!=null? ResponseEntity.ok().body(reservations):
                ResponseEntity.notFound().build();
    }

    @GetMapping("/reservation/{localDatestr}")
    public ResponseEntity<Reservation> getReservation2(@PathVariable String localDatestr) {
        log.info("localDate : "+localDatestr);
        StringTokenizer st = new StringTokenizer(localDatestr,"-");

        LocalDate localDate = LocalDate.of(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
        Reservation reservations = reservationService.findByLocalDate(localDate);
        log.info("reservations : "+reservations);
        return reservations!=null? ResponseEntity.ok().body(reservations):
                ResponseEntity.notFound().build();
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserDto> userInfo(Authentication authentication){
        Object principal = authentication.getPrincipal();
        if(!(principal instanceof Users)){
            return ResponseEntity.badRequest().build();
        }
        Users user = (Users) principal;

        UserDto userDto = new UserDto(user);

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/reservationCancel/{reservationNum}")
    public ResponseEntity<Object> reservationCancel(Authentication authentication,@PathVariable Long reservationNum){
        Object principal = authentication.getPrincipal();
        
        //로그인이 안되어있으면 예외처리
        if(!(principal instanceof Users)){
            return ResponseEntity.badRequest().build();
        }

        Users user = (Users) principal;

        Reservation reservation = reservationService.findById(reservationNum);
        
        //예약번호가 없을 경우 예외처리
        if(reservation == null){
            return ResponseEntity.badRequest().build();
        }

        //예약자와 예약취소 요청자가 다를 경우 예외처리
        if(!reservation.getUsers().equals(user)){
            return ResponseEntity.badRequest().build();
        }

        reservationService.deleteById(reservationNum);

        if(!reservationService.existsById(reservationNum)){
            //정상적인 처리인상태
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        //비정상적인 처리인 상태
        return ResponseEntity.badRequest().build();
    }
}

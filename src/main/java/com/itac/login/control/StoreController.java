package com.itac.login.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itac.login.entity.reservation.Reservation;
import com.itac.login.entity.reservation.ReservationRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import com.itac.login.service.MemberService;
import com.itac.login.service.ReservationService;
import com.itac.login.service.StoreService;
import com.itac.login.dto.StoreDto;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/store")
@Slf4j
public class StoreController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/info")
    public void store(HttpServletRequest req, Authentication auth, HttpServletResponse resp) throws IOException {
        // 추후 AuthSuccessHandler을 통해 제한
        try{
            if(auth.getAuthorities() != null){
                String email = req.getUserPrincipal().getName();
                if(!memberService.getAuth(email).equals("store")){
                    resp.sendRedirect("/mypage");

                }else{
                    resp.sendRedirect("/mypage/storePage");
                }
            }
        }catch(Exception e){
            resp.sendRedirect("/login");
        }
    }
    
    @GetMapping("/list")
    public ResponseEntity<Object> list(HttpServletRequest req){
        int userId = memberService.findMember(req.getUserPrincipal().getName());
        List<Store> stores = storeService.manageStoreInfo(userId);

        return ResponseEntity.status(HttpStatus.OK).body(stores);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Store> store(@PathVariable Long id){
        Store store = storeService.show(id);

        return ResponseEntity.status(HttpStatus.OK).body(store);
    }

    @GetMapping("/{id}/{localDateStr}")
    public ResponseEntity<List<LocalTime>> getReservableTimes(@PathVariable Long id,@PathVariable String localDateStr) {
        log.info("@GetMapping(/{id}/{localDate}) -> id : "+ id+", localDateStr : "+localDateStr);
        StringTokenizer st = new StringTokenizer(localDateStr,"-");
        LocalDate parsedDate = LocalDate.of(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));

        List<LocalTime> reservableTimes = storeService.getReservableTimes(id,parsedDate);
        return reservableTimes != null && !reservableTimes.isEmpty()?
               ResponseEntity.status(HttpStatus.OK).body(reservableTimes):
               ResponseEntity.status(HttpStatus.NO_CONTENT).body(reservableTimes);
    }

    @PostMapping(value="/create")
    public ResponseEntity<Store> Create(HttpServletRequest req, @RequestPart("store")StoreDto storeDto, @RequestPart(value = "file", required = false)List<MultipartFile> multipartFile){
    //public ResponseEntity<Store> Create(HttpServletRequest req, @RequestPart(value = "file", required = false)List<MultipartFile> multipartFile, @RequestParam("store") String storeString) throws IOException{

        //StoreDto storeDto = objectMapper.readValue(storeString, StoreDto.class);
        String user = req.getUserPrincipal().getName();
        //String user = "test@test.org";
        Store store = storeService.create(storeDto, user, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(store);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(HttpServletRequest req, @PathVariable Long id, @RequestPart("store") StoreDto storeDto, @RequestPart(value="file", required=false)List<MultipartFile> multipartFile){
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

    @PostMapping("/reservation")
    public ResponseEntity<Reservation> post(@RequestBody String body, Authentication authentication){
        ObjectMapper objectMapper = new ObjectMapper();

        HashMap<String,String> data=null;
        
        try{
            data = objectMapper.readValue(body, new TypeReference<HashMap<String, String>>() {
            });
        } catch (Exception e){
            e.printStackTrace();
            log.info("reservation 파싱중에 에러 발생");
        }

        //정보조회
        for(HashMap.Entry<String,String> entry:data.entrySet()){
            log.info("key : "+entry.getKey()+" , value : "+entry.getValue());
        }

        Reservation reservation = new Reservation();

        int numberOfPerson = Integer.parseInt(data.get("numberOfPerson"));
        reservation.setNumberOfPerson(numberOfPerson);

        String reservationDateTimeInfo = data.get("reservationDate");
        reservationDateTimeInfo = reservationDateTimeInfo.substring(0,19);
        LocalDateTime localDateTime = LocalDateTime.parse(reservationDateTimeInfo);

        reservation.setReservationDate(localDateTime.toLocalDate());
        reservation.setReservationTime(LocalTime.parse(data.get("reservationTime")));

        log.info("reservation : "+reservation);

        Object principal = authentication.getPrincipal();
        if(! (principal instanceof Users)){
            log.info("reservation 저장 실패 사유 : 로그인 되지 않은 유저 ");
            return ResponseEntity.badRequest().body(null);
        }

        log.info("principal : "+ principal);

        Users user = (Users)principal;
        reservation.setUsers(user);

        Store store = storeService.findById(Long.parseLong(data.get("storeNum")));
        reservation.setStore(store);

        Reservation saved = reservationService.save(reservation);
        log.info("reservation 저장 전 : " + reservation);

        if(saved!=null){
            log.info("reservation 저장 성공 추정 : " + saved);
            return ResponseEntity.status(HttpStatus.OK).body(saved);
        }else{
            log.info("reservation 저장 실패 추정 : " + reservation);
            return ResponseEntity.badRequest().body(null);
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        // 예상치 못한 오류 발생 시
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @GetMapping("userReservation")
    public ResponseEntity<List<Reservation>> getReservations(Authentication authentication){
        Object principal = authentication.getPrincipal();

        if(!(principal instanceof  Users)){
            return ResponseEntity.badRequest().build();
        }

        Users user = (Users) principal;
        Long userNum =user.getUserNum();
//        List<Reservation> reservations = reservationService.findByUserNum(userNum);
//        List<Reservation> reservations = user.getReservations();
        List<Reservation> reservations = reservationService.findAllByUsers(user);
        for (Reservation reservation : reservations) {
            reservation.setUsers(null);
        }

        return (reservations != null && !reservations.isEmpty())?
                ResponseEntity.status(HttpStatus.OK).body(reservations):
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

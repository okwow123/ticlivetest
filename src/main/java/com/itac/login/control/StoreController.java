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
        return ResponseEntity.status(HttpStatus.OK).body(storeService.manageStoreInfo(userId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Store> store(@PathVariable Long id){
        Store store = storeService.show(id);

        return ResponseEntity.status(HttpStatus.OK).body(store);
    }

    @GetMapping("/{id}/{localDateStr}")
    public ResponseEntity<List<LocalTime>> getReservableTimes(@PathVariable Long id,@PathVariable String localDateStr) {
        log.info("@GetMapping(/{id}/{localDate}) -> id : "+ id+", localDateStr : "+localDateStr);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 이 부분을 try catch문으로 바꿔야함
//        Map<String, Integer> dateMap = objectMapper.readValue(localDate, Map.class);
//        // Map에서 년, 월, 일 값을 추출하여 LocalDate 객체 생성
//        LocalDate parsedDate = LocalDate.of(dateMap.get("year"), dateMap.get("month"), dateMap.get("day"));
        StringTokenizer st = new StringTokenizer(localDateStr,"-");
        LocalDate parsedDate = LocalDate.of(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));

        List<LocalTime> reservableTimes = storeService.getReservableTimes(id,parsedDate);
        return reservableTimes != null && !reservableTimes.isEmpty()?
               ResponseEntity.status(HttpStatus.OK).body(reservableTimes):
               ResponseEntity.status(HttpStatus.NO_CONTENT).body(reservableTimes);
    }

    @PostMapping(value="/create")
    public ResponseEntity<Store> Create(HttpServletRequest req, @RequestPart("store") StoreDto storeDto, @RequestPart(value = "file", required = false)List<MultipartFile> multipartFile) throws IOException{
        String user = req.getUserPrincipal().getName();
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
    public ResponseEntity<LocalTime> post(@RequestBody String body, Authentication authentication){
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
        Instant instant = Instant.parse(reservationDateTimeInfo);
        LocalDateTime reservationDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//        LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDateTimeInfo);

        reservation.setReservationDate(reservationDateTime.toLocalDate());
        reservation.setReservationTime(reservationDateTime.toLocalTime());

        log.info("reservation : "+reservation);


//        String memberId  = SecurityContextHolder.getContext().getAuthentication().getName();
//        log.info("memberId : "+ memberId);

        Object principal = authentication.getPrincipal();

        log.info("principal : "+ principal);

        Users user = (Users)principal;
        reservation.setUsers(user);

        Store store = storeService.findById(Long.parseLong(data.get("storeNum")));
        reservation.setStore(store);

        Reservation savedReservation = reservationService.save(reservation);




        return null;
    }
}

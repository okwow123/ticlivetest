package com.itac.login.service;

import com.itac.login.entity.reservation.Reservation;
import com.itac.login.entity.reservation.ReservationRepository;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> allReservations(){
        return reservationRepository.findAll();
    }

    public Reservation findByLocalDate(LocalDate localDate){
        return reservationRepository.findByReservationDate(localDate);
    }

    public Reservation save(Reservation reservation) {
        Reservation saved = reservationRepository.save(reservation);
        return saved;
    }

    public List<Reservation> findByUserNum(Long userNum) {
        List<Reservation> list = reservationRepository.findAllByUsersUserNum(userNum);
        return list;
    }

    public List<Reservation> findAllByUsers(Users user) {
        List<Reservation> list = reservationRepository.findAllByUsers(user);
        return list;
    }

    public Reservation findById(Long reservationNum) {
        Reservation reservation = reservationRepository.findById(reservationNum).orElse(null);
        return reservation;
    }

    public void deleteById(Long reservationNum){
        reservationRepository.deleteById(reservationNum);
    }

    public boolean existsById(Long reservationNum){
        return reservationRepository.existsById(reservationNum);
    }
}

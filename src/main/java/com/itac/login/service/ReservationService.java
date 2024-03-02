package com.itac.login.service;

import com.itac.login.entity.reservation.Reservation;
import com.itac.login.entity.reservation.ReservationRepository;
import com.itac.login.entity.store.Store;
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
}

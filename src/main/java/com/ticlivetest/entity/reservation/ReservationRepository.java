package com.ticlivetest.entity.reservation;

import com.ticlivetest.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByStoreStoreNum(Long storeNum);

    List<Reservation> findAllByUsersUserNum(Long userNum);

    @Query(value = "SELECT * FROM Reservation r INNER JOIN store s ON r.reservationstorenum = s.storenum WHERE s.storenum = :storenum and r.reservationDate = :reservationDate", nativeQuery = true)
    List<Reservation> findAllReservedTimesByStoreNumberAndDateTime(@Param("storenum") Long storenum,@Param("reservationDate") LocalDate value);

    List<Reservation> findAll();
    Reservation findByReservationDate(LocalDate localDate);

    List<Reservation> findAllByUsers(Users user);


}

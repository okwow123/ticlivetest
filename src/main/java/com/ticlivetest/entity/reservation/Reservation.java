package com.ticlivetest.entity.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ticlivetest.entity.store.Store;
import com.ticlivetest.entity.user.Users;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@EqualsAndHashCode(of={"reservationNum"})
@NoArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@ToString
@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationNum;

    private int numberOfPerson;

    private LocalDate reservationDate;

    private LocalTime reservationTime;

    @ManyToOne
    @JsonManagedReference // 순환참조 방지
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @JoinColumn(name = "reservationusernum")
    private Users users;

    @ManyToOne
    @JsonManagedReference // 순환참조 방지
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    @JoinColumn(name="reservationstorenum")
    private Store store;

    @Builder
    public Reservation(LocalDate reservationDate, LocalTime reservationTime) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    @PostLoad
    private void postLoad() {
        log.info("Reservation Entity loaded: " + this.toString());
    }
}

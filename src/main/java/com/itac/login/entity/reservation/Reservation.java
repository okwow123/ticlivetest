package com.itac.login.entity.reservation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.itac.login.entity.store.Store;
import com.itac.login.entity.user.Users;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@EqualsAndHashCode(of={"reservationNum"})
@NoArgsConstructor
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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "reservationusernum")
    private Users users;

    @ManyToOne
    @JsonManagedReference // 순환참조 방지
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name="storenum")
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

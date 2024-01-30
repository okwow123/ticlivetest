package com.itac.login.repository;

import com.itac.login.dto.MoneyResponse;
import com.itac.login.dto.RawResponse;
import com.itac.login.entity.price.QRawList;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PriceQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager entityManager;

    // 관리자 - 공통코드관리 - 초기
    public List<RawResponse> readRawList(String param) {
        QRawList rawList = QRawList.rawList;

        return jpaQueryFactory
                .select(Projections.fields(RawResponse.class
                        , rawList.product
                        , rawList.exchange_place
                        , rawList.delivery_month
                        , rawList.unit
                        , rawList.price
                        , rawList.updown
                        , rawList.updown_percent
                        , rawList.period
                ))
                .from(rawList)
                .orderBy(rawList.dates.desc())
                //.where(rawlist.code.eq("$"))
                .limit(16)
                .fetch();
    }

    public List<MoneyResponse> readMoneyList(String param) {

        StringBuffer sql = new StringBuffer();
        sql.append("select aa.currency_name,aa.price,aa.before_price,aa.before_price_percent,aa.buy,aa.sell,aa.send,aa.receive from (");
        sql.append("        select * from crawlingMoneyExchange  ");
        sql.append("        order by dates desc  ");
        sql.append(")aa  ");
        sql.append("order by aa.no asc  ");


        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        List<Object[]> resultList = nativeQuery.getResultList();
        List<MoneyResponse> resultLists = resultList.stream().map(product -> new MoneyResponse
            (
                    (String) product[0],
                    (String) product[1],
                    (String) product[2],
                    (String) product[3],
                    (String) product[4],
                    (String) product[5],
                    (String) product[6],
                    (String) product[7]
            )
        ).collect(Collectors.toList());

        return resultLists;

    }




    public String readRawDate() {

        StringBuffer sql = new StringBuffer();
        sql.append("select max(dates) from crawlingExchange");

        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        String result = nativeQuery.getSingleResult().toString();

        return result;
    }

    public String readMoneyDate() {

        StringBuffer sql = new StringBuffer();
        sql.append("select max(dates) from crawlingMoneyExchange");

        Query nativeQuery = entityManager.createNativeQuery(sql.toString());
        String result = nativeQuery.getSingleResult().toString();

        return result;
    }


}

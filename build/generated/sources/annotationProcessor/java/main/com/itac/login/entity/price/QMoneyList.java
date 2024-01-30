package com.itac.login.entity.price;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMoneyList is a Querydsl query type for MoneyList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMoneyList extends EntityPathBase<MoneyList> {

    private static final long serialVersionUID = -1436471897L;

    public static final QMoneyList moneyList = new QMoneyList("moneyList");

    public final StringPath before_price = createString("before_price");

    public final StringPath before_price_percent = createString("before_price_percent");

    public final StringPath buy = createString("buy");

    public final StringPath currency_name = createString("currency_name");

    public final StringPath dates = createString("dates");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final StringPath price = createString("price");

    public final StringPath receive = createString("receive");

    public final StringPath sell = createString("sell");

    public final StringPath send = createString("send");

    public QMoneyList(String variable) {
        super(MoneyList.class, forVariable(variable));
    }

    public QMoneyList(Path<? extends MoneyList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMoneyList(PathMetadata metadata) {
        super(MoneyList.class, metadata);
    }

}


package com.itac.login.entity.price;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRawList is a Querydsl query type for RawList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRawList extends EntityPathBase<RawList> {

    private static final long serialVersionUID = -1919236273L;

    public static final QRawList rawList = new QRawList("rawList");

    public final StringPath dates = createString("dates");

    public final StringPath delivery_month = createString("delivery_month");

    public final StringPath exchange_place = createString("exchange_place");

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final StringPath period = createString("period");

    public final StringPath price = createString("price");

    public final StringPath product = createString("product");

    public final StringPath unit = createString("unit");

    public final StringPath updown = createString("updown");

    public final StringPath updown_percent = createString("updown_percent");

    public QRawList(String variable) {
        super(RawList.class, forVariable(variable));
    }

    public QRawList(Path<? extends RawList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRawList(PathMetadata metadata) {
        super(RawList.class, metadata);
    }

}


package com.example.spring_study.QueryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    public List<Schedule> getSchedules(String email) {
//        return jpaQueryFactory.selectFrom(schedule)
//                .where(schedule.user.email.eq(email))
//                .orderBy(schedule.achieve.asc(), schedule.createDate.desc())
//                .fetch();
//    }
//
//    @Override
//    public List<Schedule> getUniqueSchedules(String email, LocalDate date) {
//        System.out.println(date);
//        return jpaQueryFactory.selectFrom(schedule)
//                .where(schedule.user.email.eq(email), schedule.createDate.eq(date))
//                .orderBy(schedule.achieve.asc(), schedule.createDate.desc())
//                .fetch();
//    }
}

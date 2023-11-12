package com.example.spring_study.QueryDsl;

import static com.example.spring_study.Entity.QSchedule.schedule;

import com.example.spring_study.Entity.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Schedule> getSchedules(String email) {
        return jpaQueryFactory.selectFrom(schedule)
                .where(schedule.user.email.eq(email))
                .orderBy(schedule.achieve.asc(), schedule.createDate.desc())
                .fetch();
    }

    @Override
    public List<Schedule> getUniqueSchedules(String email, LocalDate date) {
        System.out.println(date);
        return jpaQueryFactory.selectFrom(schedule)
                .where(schedule.user.email.eq(email), schedule.createDate.eq(date))
                .orderBy(schedule.achieve.asc(), schedule.createDate.desc())
                .fetch();
    }
}

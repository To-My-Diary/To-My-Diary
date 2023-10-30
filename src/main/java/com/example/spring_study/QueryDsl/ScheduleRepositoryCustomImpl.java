package com.example.spring_study.QueryDsl;

import static com.example.spring_study.Entity.QSchedule.schedule;

import com.example.spring_study.Entity.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Schedule> getSchedules(String name) {
        return jpaQueryFactory.select(schedule)
                .from(schedule)
                .where(schedule.user.email.eq(name))
                .orderBy(schedule.createDate.desc())
                .fetch();

    }
}

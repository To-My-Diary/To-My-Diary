package com.example.spring_study.Service;

import static com.example.spring_study.DTO.Response.ResponseStatus.NO_SCHEDULE;

import com.example.spring_study.DTO.ClickDate;
import com.example.spring_study.DTO.ScheduleDto;
import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Exception.BaseException;
import com.example.spring_study.Exception.NotFoundScheduleException;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.Repository.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    public List<Schedule> getSchedule(Principal principal) {
        return scheduleRepository.getSchedules(principal.getName());
    }

    public List<Schedule> getUniqueSchedule(Principal principal, ClickDate date) {
        return scheduleRepository.getUniqueSchedules(principal.getName(),
                LocalDate.of(date.getYear(), date.getMonth(), date.getDay()));
    }

    /**
     * 할 일 저장
     */
    public void saveSchedule(ScheduleDto scheduleDto, Principal principal) {
        scheduleDto.setUserId(principal.getName());
        scheduleRepository.save(dtoToEntity(scheduleDto));
        System.out.println("[" + scheduleDto.getMsg() + "] 할 일이 저장됐습니다.");
    }

    /**
     * 할 일 수정
     */
    @Transactional
    public void modifySchedule(ScheduleDto scheduleDto, Principal principal) {
        Schedule schedule = scheduleRepository.findByScheduleId(scheduleDto.getScheduleId())
                .orElseThrow(() ->
                {
                    throw new BaseException(NO_SCHEDULE);
                });
        schedule.update(scheduleDto.getMsg(), scheduleDto.getAchieve(), scheduleDto.getPlanDate());
        System.out.println("[" + scheduleDto.getMsg() + "] 할 일을 수정했습니다.");
    }

    //  달성 여부 수정
    @Transactional
    @Modifying
    public Schedule modifyAchieve(ScheduleDto scheduleDto) {
        // schedule 객체가 있으면 가져오고, 없으면 예외처리
        Schedule schedule = scheduleRepository.findByUser_emailAndScheduleId(scheduleDto.getUserId(),
                        scheduleDto.getScheduleId())
                .orElseThrow(() -> new NotFoundScheduleException(
                        String.format("Not Found %d Schedule", scheduleDto.getScheduleId())));
        // 할 일 달성 여부 변경
        schedule.setAchieve(scheduleDto.getAchieve());
        // 할 일 달성 여부 변경 저장
        scheduleRepository.save(schedule);

        return schedule;
    }

    /**
     * 할 일 삭제
     */
    public void deleteSchedule(Long scheduleId, Principal principal) {
        scheduleRepository.deleteById(scheduleId);
        System.out.println(scheduleId + " 번 할 일이 삭제됐습니다.");
    }

    /**
     * Dto -> Entity
     */
    public Schedule dtoToEntity(ScheduleDto dto) {
        Schedule schedule = Schedule.builder()
//                .scheduleId(dto.getScheduleId())
                .msg(dto.getMsg())
                .achieve(dto.getAchieve())
                .planDate(dto.getPlanDate())
                .user(userRepository.findByEmail(dto.getUserId()).get())
                .build();
        return schedule;
    }
}

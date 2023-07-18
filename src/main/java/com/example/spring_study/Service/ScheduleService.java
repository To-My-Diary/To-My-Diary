package com.example.spring_study.Service;

import com.example.spring_study.DTO.ScheduleDto;
import com.example.spring_study.Entity.Schedule;
import com.example.spring_study.Repository.ScheduleRepository;
import com.example.spring_study.DTO.ClickDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import com.example.spring_study.Repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public List<Schedule> getSchedule() {
        System.out.println("실행1");
        return scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.now());
    }

    public List<Schedule> getSchedule(ClickDate date) {
        System.out.println("실행2");
        return scheduleRepository.findAllByUser_emailAndCreateDate("111@naver.com", LocalDate.of(date.getYear(), date.getMonth(),date.getDay()));
    }

    /** 할 일 저장 */
    public void saveSchedule(ScheduleDto scheduleDto) {
        scheduleRepository.save(dtoToEntity(scheduleDto));
        System.out.println("[" + scheduleDto.getMsg() + "] 할 일이 저장됐습니다.");
    }

    /** 할 일 수정 */
    @Transactional
    public void modifySchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleRepository.findByScheduleId(scheduleDto.getScheduleId());
        schedule.update(scheduleDto.getMsg(), scheduleDto.getAchieve());
        System.out.println("[" + scheduleDto.getMsg() + "] 할 일을 수정했습니다.");
    }
    
    /** 할 일 삭제 */
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
        System.out.println(scheduleId + " 번 할 일이 삭제됐습니다.");
    }

    /** Dto -> Entity */
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

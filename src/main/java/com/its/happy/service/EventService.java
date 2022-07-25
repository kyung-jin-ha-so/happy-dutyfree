package com.its.happy.service;

import com.its.happy.common.PagingConst;
import com.its.happy.dto.CouponDTO;
import com.its.happy.dto.EventDTO;
import com.its.happy.dto.EventFilesDTO;
import com.its.happy.entity.CouponEntity;
import com.its.happy.entity.EventEntity;
import com.its.happy.entity.EventFilesEntity;
import com.its.happy.repository.CouponRepository;
import com.its.happy.repository.EventFilesRepository;
import com.its.happy.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final CouponRepository couponRepository;
    private final EventRepository eventRepository;
    private final EventFilesRepository eventFilesRepository;

    public Long save(EventDTO eventDTO, CouponDTO couponDTO) throws IOException{
        MultipartFile eventThumbnail = eventDTO.getEventThumbnail();
        String eventThumbnailName = eventThumbnail.getOriginalFilename();
        eventThumbnailName = System.currentTimeMillis()+"-"+eventThumbnailName;
        String savePath = "C:\\happy_img\\"+eventThumbnailName;
        if(!eventThumbnail.isEmpty()){
            eventThumbnail.transferTo(new File(savePath));
        }
        eventDTO.setEventThumbnailName(eventThumbnailName);
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(couponDTO.getCouponId());
        if(optionalCouponEntity.isPresent()){
         CouponEntity couponEntity = optionalCouponEntity.get();
         Long savedId = eventRepository.save(EventEntity.toEvent(eventDTO, couponEntity)).getEventId();
         return savedId;
        }
        return null;
    }

    public Long update(EventDTO eventDTO, CouponDTO couponDTO) {
        Optional<CouponEntity> optionalCouponEntity = couponRepository.findById(couponDTO.getCouponId());
        if(optionalCouponEntity.isPresent()){
            CouponEntity couponEntity = optionalCouponEntity.get();
            Long savedId = eventRepository.save(EventEntity.toUpdateEntity(eventDTO, couponEntity)).getEventId();
            return savedId;
        }
        return null;
    }

    public void fileSave(Long savedId, List<MultipartFile> multipartFileList) throws IOException{
        Optional<EventEntity> optionalEventEntity = eventRepository.findById(savedId);
        System.out.println("optionalEventEntity = " + optionalEventEntity);
        if(optionalEventEntity.isPresent()){
            EventEntity eventEntity = optionalEventEntity.get();
            EventFilesDTO eventFilesDTO = new EventFilesDTO();
            for(MultipartFile file: multipartFileList){
                String eventFileName = file.getOriginalFilename();
                eventFileName = System.currentTimeMillis()+"-"+ eventFileName;
                String savePath = "C:\\happy_img\\"+eventFileName;
                if(!file.isEmpty()){
                    file.transferTo(new File(savePath));
                    eventFilesDTO.setEventFileName(eventFileName);
                    eventFilesRepository.save(EventFilesEntity.toEventFile(eventFilesDTO, eventEntity));
                }

            }
        }
    }

    public Page<EventDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1)? 0: (page-1);
        Page<EventEntity> boardEntities = eventRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "eventId")));
        Page<EventDTO> eventList = boardEntities.map(
                event -> new EventDTO(event.getEventId(),
                        event.getEventTitle(),
                        event.getEventContents(),
                        event.getCreatedTime(),
                        event.getUpdatedTime(),
                        event.getEventThumbnail(),
                        event.getCouponEntity()
                ));
        return eventList;
    }

    public EventDTO findById(Long eventId) {
        Optional<EventEntity>optionalEventEntity = eventRepository.findById(eventId);
        if(optionalEventEntity.isPresent()){
            return EventDTO.toEventDTO(optionalEventEntity.get());
        } else {
            return null;
        }
    }

    public void deleteById(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<EventDTO> search(String q) {
        List<EventEntity> eventEntityList = eventRepository.findByEventTitleContainingOrEventContentsContaining(q, q);
        List<EventDTO> eventDTOList = new ArrayList<>();
        for(EventEntity eventEntity: eventEntityList){
            eventDTOList.add(EventDTO.toEventDTO(eventEntity));
        }
        return eventDTOList;
    }
}
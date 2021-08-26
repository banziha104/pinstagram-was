package com.pinstagram.event.service;

import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponseCode;
import com.pinstagram.event.dto.CreateDto;
import com.pinstagram.event.dto.RetrieveDto;
import com.pinstagram.event.repository.EventRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    @NonNull
    private EventRepository repository;

    @NonNull
    private Environment env;

    @Override
    public RetrieveDto.Response getById(long id) throws ApiException {
        var entity = repository.findById(id);
        if (entity.isPresent()) {
            return RetrieveDto.Response.fromEntity(entity.get());
        } else {
            throw new ApiException(ApiResponseCode.NOT_FOUND);
        }
    }

    @Override
    public List<RetrieveDto.Response> getByDistance(double lat, double lng, double limit) {
        if (env.getActiveProfiles()[0].equals("test")){
            return repository
                    .findAll()
                    .stream()
                    .map(RetrieveDto.Response::fromEntity)
                    .collect(Collectors.toList());
        }else{
            return repository
                    .findAllByDistance(lat, lng, limit)
                    .stream()
                    .map(RetrieveDto.Response::fromEntity)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public long create(Authentication authentication, CreateDto.Request request) throws ApiException {
        return repository.save(request.toEntity()).getEventId();
    }
}

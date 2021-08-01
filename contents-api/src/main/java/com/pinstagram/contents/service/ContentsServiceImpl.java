package com.pinstagram.contents.service;

import com.pinstagram.common.response.ApiResponse;
import com.pinstagram.contents.config.ServerAddressConfig;
import com.pinstagram.contents.dto.CreateDto;
import com.pinstagram.contents.dto.RetrieveDto;
import com.pinstagram.contents.repository.AccountRepository;
import com.pinstagram.dto.geo.GeoCodingResponse;
import com.pinstagram.contents.dto.UpdateDto;
import com.pinstagram.contents.repository.ContentsRepository;
import com.pinstagram.domain.entity.contents.ContentsEntity;
import com.pinstagram.common.jwt.AuthManager;
import com.pinstagram.common.response.ApiException;
import com.pinstagram.common.response.ApiResponseCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsServiceImpl implements ContentsService {

    @NonNull
    Environment env;

    @NonNull
    private final ContentsRepository contentsRepository;

    @NonNull
    private final AccountRepository accountRepository;

    @NonNull
    private final AuthManager authManager;

    @NonNull
    private final ServerAddressConfig addressConfig;

    private final RestTemplate template = new RestTemplate();

    @Override
    public RetrieveDto.Response getById(long id) throws ApiException {
        var entity = contentsRepository.findById(id);
        if (entity.isPresent()) {
            return RetrieveDto.Response.fromEntity(entity.get());
        } else {
            throw new ApiException(ApiResponseCode.NOT_FOUND);
        }
    }

    @Override
    public long create(Authentication authentication, CreateDto.Request request) throws ApiException {

        var id = authManager.getId(authentication);
        if (!authManager.isValidateToken(authentication) || id.isEmpty()) {
            throw new ApiException(ApiResponseCode.FORBIDDEN);
        }
        var account = accountRepository.findById(id.get());
        if (account.isEmpty()) {
            throw new ApiException(ApiResponseCode.USER_NOT_FOUNDED);
        }
        var geoResponse = template.exchange(
                addressConfig.getGeoServerAddress() + "/?latlng=" + request.getLat() + "," + request.getLng(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<GeoCodingResponse>>() {
                }
        );
        if (geoResponse.getStatusCode() != HttpStatus.OK) {
            throw new ApiException(ApiResponseCode.NOT_FOUND);
        }
        var geoData = geoResponse.getBody().getData();
        var entity = request.toEntity();
        entity.setAccount(account.get());
        entity.setFullAddress(geoData.getResults()[0].getFormattedAddress());
        entity.setCreateAt(LocalDateTime.now());
        var saved = contentsRepository.save(entity);
        return saved.getContentsId();
    }

    @Override
    public long update(Authentication authentication, UpdateDto.Request request, long contentsId) throws ApiException {
        var userId = authManager.getId(authentication);
        var entity = contentsRepository.findById(contentsId);
        if (entity.isEmpty()) {
            throw new ApiException(ApiResponseCode.NO_CONTENTS);
        }

        if (!authManager.isValidateToken(authentication) || userId.isEmpty() || userId.get() != entity.get().getAccount().getAccountId()) {
            throw new ApiException(ApiResponseCode.FORBIDDEN);
        }
        var updated = request.update(entity.get());
        var saved = contentsRepository.save(updated);
        return saved.getContentsId();
    }

    @Override
    public List<RetrieveDto.Response> getByDistance(double lat, double lng, double limit) {
        // TODO : 수정 필요
        // H2 에는 st_distance_sphere 함수가 없음으로, 테스팅이 안되어서 문서화가 안되어서 별도로 분리.
        // 추후 대책이 세워지면 바로 수정
        if (env.getActiveProfiles()[0].equals("test")){
            return contentsRepository
                    .findAll()
                    .stream()
                    .map(RetrieveDto.Response::fromEntity)
                    .collect(Collectors.toList());
        }else{
            return contentsRepository
                    .findAllByDistance(lat, lng, limit)
                    .stream()
                    .map(RetrieveDto.Response::fromEntity)
                    .collect(Collectors.toList());
        }
    }


    @Override
    public long deleteEntity(Authentication authentication, long contentsId) throws ApiException {
        var id = authManager.getId(authentication);
        if (id.isEmpty()) {
            throw new ApiException(ApiResponseCode.BAD_PARAMETER);
        } else {
            var entity = contentsRepository.findById(contentsId);
            if (entity.isEmpty() || entity.get().getAccount().getAccountId() != id.get()) {
                throw new ApiException(ApiResponseCode.NOT_FOUND);
            } else {
                contentsRepository.delete(entity.get());
                return entity.get().getContentsId();
            }
        }
    }
}

package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class of service for {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final UserRepository userRepository;

    @Override
    public CompanyDto add(NewCompanyDto companyDto, Long userId) {
        User owner = checkIsUserExistAndActive(userId);
        Company newCompany = CompanyMapper.toCompany(companyDto);
        newCompany.setOwner(owner);
        newCompany.setStatus(CompanyStatus.PENDING);
        newCompany = repository.save(newCompany);
        CompanyDto newCompanyDto = CompanyMapper.toDto(newCompany);
        log.info("Company id={} successfully created", newCompany.getId());
        return newCompanyDto;
    }

    @Override
    public String delete(Long companyId) {
        Company company = repository.findById(companyId).orElseThrow(() -> new NotFoundException(String
                .format("Company didn't delete. Company id=%s not found", companyId)));
        repository.delete(company);
        log.info("Company id={} successfully deleted", companyId);
        return String.format("Company id=%s successfully deleted", companyId);
    }

    @Override
    public CompanyDto update(CompanyAdminUpdateDto companyDto, Long companyId) {
        Company companyFromRepository = repository.findById(companyId).orElseThrow(() -> new NotFoundException(String
                .format("Company didn't update. Company id=%s not found", companyId)));

        Company companyForUpdate = CompanyMapper.toCompany(companyDto);
        companyFromRepository.setStatus(Optional.ofNullable(companyForUpdate.getStatus()).orElse(companyFromRepository.getStatus()));
        companyFromRepository = repository.save(companyFromRepository);

        CompanyDto updatedCompanyDto = CompanyMapper.toDto(companyFromRepository);
        log.info("Company id={} successfully updated", companyId);
        return updatedCompanyDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CompanyDto> getAllByStatus(Set<String> statusStingSet) {
        Set<CompanyStatus> statusSet = statusStingSet.stream().map(CompanyStatus::valueOf).collect(Collectors.toSet());
        Set<Company> companySet = repository.findAllByStatusIn(statusSet);
        Set<CompanyDto> companyDtoSet = companySet.stream().map(CompanyMapper::toDto).collect(Collectors.toSet());
        log.info("Set of companies successfully received");
        return companyDtoSet;
    }

    private User checkIsUserExistAndActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("User didn't update. User id=%s not found", userId)));
        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s is blocked", userId));
        }
        return user;
    }

}

package com.PhotoStudio.PhotoStudio.service;

import com.PhotoStudio.PhotoStudio.model.ServiceType;
import com.PhotoStudio.PhotoStudio.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceTypeService {
    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public List<ServiceType> findAll() {
        return serviceTypeRepository.findAll();
    }

    public ServiceType findById(Long id) {
        return serviceTypeRepository.findById(id).orElse(null);
    }

    public ServiceType save(ServiceType serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public void deleteById(Long id) {
        serviceTypeRepository.deleteById(id);
    }
}
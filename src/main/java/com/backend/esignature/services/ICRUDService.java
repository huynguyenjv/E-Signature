package com.backend.esignature.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICRUDService<RQ,RP,T> {
    public RP create(RQ entity);
    public RP update(RQ entity, T id);
    public RP delete(T id);
    public RP findById(RQ entity);
    public List<RP> findAll();
}

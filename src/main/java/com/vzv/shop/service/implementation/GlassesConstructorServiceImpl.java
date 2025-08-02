package com.vzv.shop.service.implementation;

import com.vzv.shop.entity.constructor.GlassesConstructor;
import com.vzv.shop.repository.GlassesConstructorRepository;
import com.vzv.shop.service.GlassesConstructorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlassesConstructorServiceImpl implements GlassesConstructorService{

	private final GlassesConstructorRepository repository;
	
	public GlassesConstructorServiceImpl(GlassesConstructorRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public List<GlassesConstructor> getAll() {
		return repository.findAll();
	}

	@Override
	public GlassesConstructor getById(String id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<GlassesConstructor> saveAll(List<GlassesConstructor> glasses) {
		return repository.saveAll(glasses);
	}

	@Override
	public GlassesConstructor save(GlassesConstructor glasses) {
		return repository.save(glasses);
	}

	@Override
	public GlassesConstructor update(GlassesConstructor glasses) {
		return repository.save(glasses);
	}

	@Override
	public boolean delete(GlassesConstructor glassesConstructor) {
		repository.delete(glassesConstructor);
		return repository.findById(glassesConstructor.getId()).orElse(null) == null;
	}

	@Override
	public boolean deleteById(String constructorId) {
		repository.deleteById(constructorId);
		return repository.findById(constructorId).orElse(null) == null;
	}

}

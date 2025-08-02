package com.vzv.shop.service;

import java.util.List;

import com.vzv.shop.entity.constructor.GlassesConstructor;

public interface GlassesConstructorService {

	List<GlassesConstructor> getAll();
	GlassesConstructor getById(String id);
	List<GlassesConstructor> saveAll(List<GlassesConstructor> glasses);
	GlassesConstructor save(GlassesConstructor glasses);
	GlassesConstructor update(GlassesConstructor glasses);
	boolean delete(GlassesConstructor glassesConstructor);
	boolean deleteById(String constructorId);
}
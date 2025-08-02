package com.vzv.shop.service;

import com.vzv.shop.entity.user.FullUser;

import java.util.List;

public interface FullUserService {

    List<FullUser> getAll();

    FullUser getById(String id);

    FullUser save(FullUser user);

    FullUser update(FullUser user);

    boolean delete(String id);
}

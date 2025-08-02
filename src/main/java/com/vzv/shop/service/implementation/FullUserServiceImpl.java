package com.vzv.shop.service.implementation;

import com.vzv.shop.entity.user.FullUser;
import com.vzv.shop.repository.ContactRepository;
import com.vzv.shop.repository.FullUserRepository;
import com.vzv.shop.service.FullUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullUserServiceImpl implements FullUserService {

    private final FullUserRepository userRepository;

    public FullUserServiceImpl(ContactRepository contactRepository, FullUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<FullUser> getAll() {
        return userRepository.findAll();
    }

    @Override
    public FullUser getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public FullUser save(FullUser user) {
        return userRepository.save(user);
    }

    @Override
    public FullUser update(FullUser user) {
        FullUser fullUser = userRepository.findById(user.getId()).orElse(null);
        if (fullUser != null) {
            BeanUtils.copyProperties(user, fullUser, "id");
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(String id) {
        userRepository.deleteById(id);
        return userRepository.findById(id).orElse(null) == null;
    }
}

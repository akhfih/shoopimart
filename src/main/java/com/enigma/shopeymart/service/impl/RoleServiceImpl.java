package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Role;
import com.enigma.shopeymart.repository.RoleRepository;
import com.enigma.shopeymart.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getOrSave(Role role) {
        Optional<Role> optionalRole = repository.findByName(role.getName());
        //jika ada di DB di get
        if(!optionalRole.isEmpty()){
            return optionalRole.get();
        }
        //jika tidak ada maka di create baru
        return repository.save(role);
    }
}

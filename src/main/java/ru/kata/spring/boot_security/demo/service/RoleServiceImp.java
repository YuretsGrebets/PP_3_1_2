package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    @Override
    public void update(Role updatedRole) {
        roleRepository.save(updatedRole);
    }

    @Override
    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }

    @Transactional
    @Override
    public void delete(long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}

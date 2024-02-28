package com.example.todo.todo.Service;

import com.example.todo.todo.Model.Users;
import com.example.todo.todo.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    private UsersRepository repository;

    public Users saveUsers(Users user){
        return repository.save(user);
    }

    public List<Users> getAllUsers(){
        return repository.findAll();
    }

    public Optional<Users> getUsersById(Long user_id){
        return repository.findById(user_id);
    }

    public Users updateUsers(Long user_id, Users user){
        if(repository.existsById(user_id)){
            user.setUser_id(user_id);
            return repository.save(user);
        }
        return null;
    }

    public void deleteUsers(Long user_id){
        repository.deleteById(user_id);
    }
}

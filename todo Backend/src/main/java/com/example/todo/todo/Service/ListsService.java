package com.example.todo.todo.Service;

import com.example.todo.todo.Model.Lists;
import com.example.todo.todo.Repository.ListsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListsService {
    @Autowired
    private ListsRepository repository;

    public Lists saveLists(Lists list){
        return repository.save(list);
    }

    public List<Lists> getAllLists(){
        return repository.findAll();
    }

    public Optional<Lists> getListsById(Long list_id){
        return repository.findById(list_id);
    }

    public Lists updateLists(Long list_id, Lists list){
        if(repository.existsById(list_id)){
            list.setList_id(list_id);
            return repository.save(list);
        }
        return null;
    }

    public void deleteLists(Long list_id){
        repository.deleteById(list_id);
    }
}

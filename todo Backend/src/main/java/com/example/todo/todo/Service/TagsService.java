package com.example.todo.todo.Service;

import com.example.todo.todo.Model.Tags;
import com.example.todo.todo.Repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {
    @Autowired
    private TagsRepository repository;

    public Tags saveTags(Tags tag){
        return repository.save(tag);
    }

    public List<Tags> getAllTags(){
        return repository.findAll();
    }

    public Optional<Tags> getTagsById(Long tag_id){
        return repository.findById(tag_id);
    }

    public Tags updateTags(Long tag_id, Tags tag){
        if(repository.existsById(tag_id)){
            tag.setTag_id(tag_id);
            return repository.save(tag);
        }
        return null;
    }

    public void deleteTags(Long tag_id){
        repository.deleteById(tag_id);
    }
}

package com.example.todo.todo.Service;

import com.example.todo.todo.Model.Comments;
import com.example.todo.todo.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository repository;

    public Comments saveComments(Comments comment){
        return repository.save(comment);
    }

    public List<Comments> getAllComments(){
        return repository.findAll();
    }

    public Optional<Comments> getCommentsById(Long comment_id){
        return repository.findById(comment_id);
    }

    public Comments updateComments(Long comment_id, Comments comment){
        if(repository.existsById(comment_id)){
            comment.setComment_id(comment_id);
            return repository.save(comment);
        }
        return null;
    }

    public void deleteComments(Long comment_id){
        repository.deleteById(comment_id);
    }
}

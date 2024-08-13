package com.example.safeagile.Services.IServices;

import com.example.safeagile.Models.UserStory;

import java.util.List;
import java.util.Map;

public interface IUserStoryService {
    UserStory createUserStory(UserStory userStory);
    List<UserStory> createUserStories(List<UserStory> userStories);
    UserStory getUserStoryById(String id);
    List<UserStory> getAllUserStories();
    UserStory updateUserStory(String id, UserStory userStory);
    void deleteUserStory(String id);
      Map<String, Integer> getuscout() ;

    }

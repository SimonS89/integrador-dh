package com.example.workoutWonderland.service;

import com.example.workoutWonderland.entity.Booking;
import com.example.workoutWonderland.entity.Favorite;
import com.example.workoutWonderland.exception.ResourceNotFoundException;

import java.util.List;

public interface IFavoriteService extends IModelService<Favorite>{
    List<Favorite> listFavoritesByUser(Long userId) throws ResourceNotFoundException;
}

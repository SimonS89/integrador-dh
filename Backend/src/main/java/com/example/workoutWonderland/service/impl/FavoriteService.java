package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Favorite;
import com.example.workoutWonderland.exception.AlreadyExistsException;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.IFavoriteRepository;
import com.example.workoutWonderland.service.IFavoriteService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
public class FavoriteService implements IFavoriteService {

    private final IFavoriteRepository favoriteRepository;
    private static final String NOT_FOUND = "Favorite not found";

    public FavoriteService(IFavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }


    @Override
    public Favorite create(Favorite favorite) throws AlreadyExistsException {
        return favoriteRepository.save(favorite);
    }

    @Override
    public Favorite findById(Long id) throws ResourceNotFoundException {
        Optional<Favorite> favorite= favoriteRepository.findById(id);
        if(favorite.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);

        return favorite.get();
    }

    @Override
    public Favorite modify(Favorite favorite) throws ResourceNotFoundException {
        findById(favorite.getId());


        return favoriteRepository.save(favorite);
    }

    public List<Favorite> listFavoritesByUser(Long userId) throws ResourceNotFoundException {
        List<Favorite> filteredFavorites = favoriteRepository.findByUserId(userId);
        if(filteredFavorites.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return filteredFavorites;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Favorite> favorite= favoriteRepository.findById(id);
        if(favorite.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        favoriteRepository.deleteById(id);

    }

    @Override
    public Set<Favorite> findAll() throws ResourceNotFoundException {
        List<Favorite> favorites = favoriteRepository.findAll();
        if(favorites.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return new HashSet<>(favorites);
    }
}

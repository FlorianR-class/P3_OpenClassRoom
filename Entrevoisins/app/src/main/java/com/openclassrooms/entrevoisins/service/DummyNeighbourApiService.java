package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavorites() {
        List<Neighbour> favorites = new ArrayList<>();

        for (Neighbour n :neighbours){                                                              // Check de chaque voisin pour savoir s'ils sont favoris si la réponse est "true" ils sont ajoutés dans la liste "favorite"
            if (n.isFavorite()) favorites.add(n);
        }
        return favorites;
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public boolean updateFavorite(Neighbour neighbour) {
        int position = neighbours.indexOf(neighbour);
        neighbours.get(position).setFavorite(!neighbours.get(position).isFavorite());               // setFavorite l'inverse de isFavorite (toggle)

        return neighbours.get(position).isFavorite();
    }
}

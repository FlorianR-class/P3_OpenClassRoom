package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoriteWithSuccess() {
        Neighbour favoriteNeighbour = service.getNeighbours().get(0);
        service.updateFavorite(favoriteNeighbour);
        List<Neighbour> favorite = service.getFavorites();
        assertTrue(favorite.contains(favoriteNeighbour));
    }

    @Test
    public void updateFavoriteWithSuccess() {
        Neighbour neighbourToFavorite = service.getNeighbours().get(1);
        assertFalse(neighbourToFavorite.isFavorite());
        service.updateFavorite(neighbourToFavorite);
        assertTrue(neighbourToFavorite.isFavorite());
        service.updateFavorite(neighbourToFavorite);
        assertFalse(neighbourToFavorite.isFavorite());
    }
    @Test
    public void getFavoriteListWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        neighbours.get(0).setFavorite(true);
        neighbours.get(1).setFavorite(true);
        neighbours.get(2).setFavorite(true);
        List<Neighbour> favNeighbours = service.getFavorites();
        assertThat(favNeighbours.size(), is(3));
        assertTrue(favNeighbours.get(0).isFavorite());
        assertTrue(favNeighbours.get(1).isFavorite());
        assertTrue(favNeighbours.get(2).isFavorite());
    }
}

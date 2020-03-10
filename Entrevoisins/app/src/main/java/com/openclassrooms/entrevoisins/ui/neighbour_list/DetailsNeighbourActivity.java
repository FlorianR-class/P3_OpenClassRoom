package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.details_avatar)
    ImageView mAvatar;
    @BindView(R.id.details_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.details_collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.details_na)
    TextView mDetailsName;
    @BindView(R.id.details_address)
    TextView mDetailsAddress;
    @BindView(R.id.details_number)
    TextView mDetailsPhoneNumber;
    @BindView(R.id.about_me)
    TextView mDetailsAbout;
    @BindView(R.id.details_website)
    TextView mWebsite;
    @BindView(R.id.fav)
    FloatingActionButton mFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        ButterKnife.bind(this);

        // Affichage du Bouton precedent sur la Toolbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Neighbour neighbour = getIntent().getParcelableExtra("neighbour");                   // Parcelable

        mCollapsingToolbar.setTitle(neighbour.getName());                                           // Ajout du nom du voisin selectionné dans la CollapsingToolbar
        mDetailsName.setText(neighbour.getName());                                                  // Ajout du nom du voisin selectionné dans le TextView
        mDetailsAddress.setText(neighbour.getAddress());                                            // Ajout de l'adresse du voisin selectionné dans le TextView
        mDetailsPhoneNumber.setText(neighbour.getPhoneNumber());                                    // Ajout du telephone du voisin selectionné dans le TextView
        mDetailsAbout.setText(neighbour.getAboutMe());                                              // Ajout du AboutMe du voisin selectionné dans le TextView
        mWebsite.setText(String.format("www.facebook.fr/%s", neighbour.getName().toLowerCase()));   // Ajout du site web facebook avec en parametre en fin d'url rajout du nom du voisin selectionné transformé en miniscule.

        Glide.with(mAvatar.getContext())                                                            // Ajout de l'image du voisin selectionné dans l'ImageView
                .load(neighbour.getAvatarUrl())
                .into(mAvatar);

        // Action sur le bouton Favoris
        mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // Action sur le bouton Back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

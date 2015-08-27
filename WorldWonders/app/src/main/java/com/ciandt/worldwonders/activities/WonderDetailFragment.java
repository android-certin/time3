package com.ciandt.worldwonders.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.helpers.Helper;
import com.ciandt.worldwonders.helpers.WonderDetailHelper;
import com.ciandt.worldwonders.models.Wonder;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WonderDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WonderDetailFragment extends android.support.v4.app.Fragment {

    Wonder wonder;
    Menu menu;
    WonderDetailHelper wonderDetailHelper;
    MenuItem bookmarkMenuItem;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment WonderDetailFragment.
     */
    public static WonderDetailFragment newInstance(Wonder wonder) {
        WonderDetailFragment fragment = new WonderDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("wonder", wonder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        if(args != null) {
            this.wonder = (Wonder) args.getSerializable("wonder");
        }
        wonderDetailHelper = new WonderDetailHelper(getContext(), this.wonder);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wonder_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageWonder = (ImageView) view.findViewById(R.id.image_detail);
        TextView description = (TextView) view.findViewById(R.id.description_detail);

        String pictureFilename = wonder.photo.split("\\.")[0];
        int pictureResource = Helper.getRawResourceID(getContext(), pictureFilename);

        Picasso.with(getContext())
                .load(pictureResource)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imageWonder);

        description.setText(wonder.description);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu = menu;
        inflater.inflate(R.menu.menu_wonderdetail, menu);

        MenuItem directionsItem = menu.findItem(R.id.action_directions);
        if (!wonderDetailHelper.hasLocation()) {
            directionsItem.setVisible(false);
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, wonder.description);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(share);

        bookmarkMenuItem = menu.findItem(R.id.action_bookmark);
        if (wonderDetailHelper.isBookmarked()) {
            bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.action_directions:
                return wonderDetailHelper.startDirections();

            case R.id.action_bookmark:
                wonderDetailHelper.toggleBookmark();
                if (wonderDetailHelper.isBookmarked()) {
                    bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_white_24dp);
                } else {
                    bookmarkMenuItem.setIcon(R.drawable.ic_bookmark_border_white_24dp);
                }
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

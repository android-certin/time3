package com.ciandt.worldwonders.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.ActivityChooserModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciandt.worldwonders.R;
import com.ciandt.worldwonders.models.Wonder;
import com.squareup.picasso.Picasso;

/**
 * Created by bdias on 21/08/15.
 */
public class HighlightFragment extends Fragment {

    public static final String WONDER_ARG = "wonder";

    private Wonder wonder;

    private ImageView image;
    private TextView name;

    public static HighlightFragment newInstance(Wonder wonder) {

        Bundle args = new Bundle();
        args.putSerializable(WONDER_ARG, wonder);
        HighlightFragment highlightFragment = new HighlightFragment();
        highlightFragment.setArguments(args);
        return highlightFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            wonder = (Wonder)args.getSerializable(WONDER_ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highligth, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = (TextView)view.findViewById(R.id.name);
        image = (ImageView)view.findViewById(R.id.image);

        if (wonder != null) {
            name.setText(wonder.name.toUpperCase());

            Picasso.with(getContext())
                    .load(wonder.url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(image);

        }

    }
}

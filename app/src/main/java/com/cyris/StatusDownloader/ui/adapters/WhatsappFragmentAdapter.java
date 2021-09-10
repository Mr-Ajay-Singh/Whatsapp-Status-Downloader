package com.cyris.StatusDownloader.ui.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.cyris.StatusDownloader.ui.fragments.Images;
import com.cyris.StatusDownloader.ui.fragments.Saved;
import com.cyris.StatusDownloader.ui.fragments.Video;

public class
WhatsappFragmentAdapter extends FragmentStatePagerAdapter {


    public WhatsappFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:return new Images();
            case 1:return new Video();
            case 2:return new Saved();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position)
        {
            case 0: return "Images";
            case 1: return "Video";
            case 2:return "Saved";
        }
        return super.getPageTitle(position);
    }
}

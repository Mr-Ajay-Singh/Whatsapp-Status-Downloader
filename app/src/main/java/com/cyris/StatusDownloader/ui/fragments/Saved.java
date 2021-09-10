package com.cyris.StatusDownloader.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cyris.StatusDownloader.R;
import com.cyris.StatusDownloader.ui.adapters.savedAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Saved extends Fragment {


    View view;
    RecyclerView recyclerView;
    savedAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    static LinearLayout countZeroDialog;
    Button buttonHowInSaved;
    Dialog dialog;

    public Saved() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved, container, false);
        countZeroDialog = view.findViewById(R.id.countZeroDialog);
        swipeRefreshLayout = view.findViewById(R.id.swipeInSaved);
        buttonHowInSaved = view.findViewById(R.id.buttonHowInSaved);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        buttonHowInSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_view);
                dialog.show();
            }
        });
        recyclerView = view.findViewById(R.id.recyclerViewInSaved);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new savedAdapter(getContext(), new savedAdapter.ShowInterface() {
            @Override
            public void ShowDialog() {
                countZeroDialog.setVisibility(View.VISIBLE);
            }

            @Override
            public void HideDialog() {
                countZeroDialog.setVisibility(View.INVISIBLE);
            }
        });
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter = new savedAdapter(getContext(), new savedAdapter.ShowInterface() {
                    @Override
                    public void ShowDialog() {
                        countZeroDialog.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void HideDialog() {
                        countZeroDialog.setVisibility(View.INVISIBLE);
                    }
                });
                recyclerView.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


}

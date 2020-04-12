package com.mobile.azrinurvani.dagger2practice.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.azrinurvani.dagger2practice.R;
import com.mobile.azrinurvani.dagger2practice.models.Post;
import com.mobile.azrinurvani.dagger2practice.ui.main.Resource;
import com.mobile.azrinurvani.dagger2practice.util.VerticalSpaceItemDecoration;
import com.mobile.azrinurvani.dagger2practice.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    private RecyclerView recyclerView;
    private PostViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    PostRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: PostsFragment was created");
        recyclerView = view.findViewById(R.id.recycler_view);

        viewModel = ViewModelProviders.of(this,providerFactory).get(PostViewModel.class);
        initRecylcerView();
        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.observerPosts().removeObservers(getViewLifecycleOwner());
        viewModel.observerPosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource!=null){
                    switch (listResource.status){
                        case LOADING:{
                            Log.d(TAG, "onChanged: loading...");
                            break;
                        }
                        case SUCCESS:{
                            Log.d(TAG, "onChanged: Success got posts...");
                            adapter.setPosts(listResource.data);
                            break;
                        }
                        case ERROR:{
                            Log.e(TAG, "onChanged: error : "+listResource.message );
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initRecylcerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // harusnya bagian ini dimasukkan dalam module MainModule dengan anotation @Provides
        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(15);// masukkan dalam module
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }


}

package com.kkkhhh.socialblinddate.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kkkhhh.socialblinddate.Interface.OnLoadMoreListener;
import com.kkkhhh.socialblinddate.Model.User;
import com.kkkhhh.socialblinddate.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class FirstMainFrg extends Fragment {
    private List<User> mUsers = new ArrayList<>();
    private UserAdapter mUserAdapter;
    private RecyclerView mRecyclerView;
    public FirstMainFrg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first_main, container, false);
      for(int i =0; i<30;i++){
            User user = new User();
            user.setName("Name " + i);
            user.setEmail("alibaba" + i + "@gmail.com");
            mUsers.add(user);
        }

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.rv_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserAdapter = new UserAdapter();
        mRecyclerView.setAdapter(mUserAdapter);

        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);

        mUserAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");
                mUsers.add(null);
                mUserAdapter.notifyItemInserted(mUsers.size() - 1);

                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");

                        //Remove loading item
                        mUsers.remove(mUsers.size() - 1);
                        mUserAdapter.notifyItemRemoved(mUsers.size());

                        //Load data
                        int index = mUsers.size();
                        int end = index + 20;
                        for (int i = index; i < end; i++) {
                            User user = new User();
                            user.setName("Name " + i);
                            user.setEmail("alibaba" + i + "@gmail.com");
                            mUsers.add(user);
                        }
                        mUserAdapter.notifyDataSetChanged();
                        mUserAdapter.setLoaded();
                    }
                }, 5000);
            }
        });
        return rootView;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvEmailId;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

            tvEmailId = (TextView) itemView.findViewById(R.id.tvEmailId);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final int VIEW_TYPE_ITEM = 0;
        private final int VIEW_TYPE_LOADING = 1;

        private OnLoadMoreListener mOnLoadMoreListener;

        private boolean isLoading;
        private int visibleThreshold = 5;
        private int lastVisibleItem, totalItemCount;

        public UserAdapter() {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
        }

        public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
            this.mOnLoadMoreListener = mOnLoadMoreListener;
        }

        @Override
        public int getItemViewType(int position) {
            return mUsers.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_user_item, parent, false);
                return new UserViewHolder(view);
            } else if (viewType == VIEW_TYPE_LOADING) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_loading_item, parent, false);
                return new LoadingViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof UserViewHolder) {
                User user = mUsers.get(position);
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.tvName.setText(user.getName());
                userViewHolder.tvEmailId.setText(user.getEmail());
            } else if (holder instanceof LoadingViewHolder) {
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setIndeterminate(true);
            }
        }

        @Override
        public int getItemCount() {
            return mUsers == null ? 0 : mUsers.size();
        }

        public void setLoaded() {
            isLoading = false;
        }
    }


}

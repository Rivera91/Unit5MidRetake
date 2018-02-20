package nyc.c4q.unit5midretake.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.unit5midretake.R;
import nyc.c4q.unit5midretake.model.User;
import nyc.c4q.unit5midretake.views.UserViewHolder;

/**
 * Created by AmyRivera on 2/20/18.
 */

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    List<User> userList;
    View.OnClickListener userDetailClick;
    Context context;

    public UserAdapter(List<User> userList, View.OnClickListener userDetailClick, Context context) {
        this.userList = userList;
        this.userDetailClick = userDetailClick;
        this.context = context;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View childView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_main_itemview, parent, false);

        return new UserViewHolder(childView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.onBind(user, context);
        holder.itemView.setTag(user.getEmail());
        holder.itemView.setOnClickListener(userDetailClick);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

package nyc.c4q.unit5midretake.views;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import nyc.c4q.unit5midretake.R;
import nyc.c4q.unit5midretake.model.User;

/**
 * Created by AmyRivera on 2/20/18.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public ImageView userThumb;
    public UserViewHolder(View itemView) {
        super(itemView);
        userName = (TextView) itemView.findViewById(R.id.user_item_name_textview);
        userThumb = (ImageView) itemView.findViewById(R.id.user_item_thumb_image_view);
    }
    public void onBind(User user, Context context){
        String fullName = user.getName().getTitle();
        StringBuilder sb = new StringBuilder(fullName);
        sb.append(" ").append(user.getName().getFirst());
        sb.append(" ").append(user.getName().getLast());
        userName.setText(sb.toString());
        Picasso.with(context)
                .load(user.getPicture().getLarge())
                .resize(500, 500)
                .centerInside()
                .into(userThumb);
        Log.d("thumbnail", "userthumb" + user.getPicture().getMedium());
    }

}


package nyc.c4q.unit5midretake.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nyc.c4q.unit5midretake.R;
import nyc.c4q.unit5midretake.RetrofitService;
import nyc.c4q.unit5midretake.UserUtility;
import nyc.c4q.unit5midretake.controller.UserAdapter;
import nyc.c4q.unit5midretake.model.User;
import nyc.c4q.unit5midretake.model.UserResults;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {
    View rootView;
    User detailUser;
    ImageView userPic;
    TextView name, location,email,dob,cell;
    Context context;


    public UserDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        context = getActivity();
        name = (TextView) rootView.findViewById(R.id.user_detail_name_textview);
        location = (TextView) rootView.findViewById(R.id.user_detail_location_textview);
        email = (TextView) rootView.findViewById(R.id.user_detail_email_textview);
        dob = (TextView) rootView.findViewById(R.id.user_detail_dob_textview);
        cell = (TextView) rootView.findViewById(R.id.user_detail_cell_textview);
        userPic = (ImageView) rootView.findViewById(R.id.user_detail_image_view);

        Picasso.with(context)
                .load(detailUser.getPicture().getLarge())
                .resize(800, 800)
                .centerCrop()
                .into(userPic);
        String fullName = detailUser.getName().getTitle();
        StringBuilder sbName = new StringBuilder(fullName);
        sbName.append(" ").append(detailUser.getName().getFirst());
        sbName.append(" ").append(detailUser.getName().getLast());

        name.setText(sbName.toString());

        String fullLocation = detailUser.getLocation().getStreet();
        StringBuilder sbLocation = new StringBuilder(fullLocation);
        sbLocation.append(" ").append(detailUser.getLocation().getCity());
        sbLocation.append(" ").append(detailUser.getLocation().getState());
        sbLocation.append(" ").append(String.valueOf(detailUser.getLocation().getPostcode()));

        location.setText(sbLocation.toString());
        email.setText(detailUser.getEmail());
        dob.setText(detailUser.getDob());
        cell.setText(detailUser.getCell());

        return rootView;
    }

    public void updateUser(User user){
        detailUser = user;
    }

    /**
     * Created by AmyRivera on 2/20/18.
     */

    public static class UserListFragment extends Fragment {
        View rootView;
        RecyclerView recyclerView;
        UserAdapter userAdapter;
        Context context;
        List<User> userList = new ArrayList<>();
        View.OnClickListener detailClickListener;
        StaggeredGridLayoutManager layoutManager;

        String baseURL = "https://randomuser.me";
        Map<String, String> queries = new HashMap<>();

        public UserListFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_user_list, container, false);
            context = getActivity();
            setRecyclerView();

            return rootView;
        }

        public void setRecyclerView(){
            recyclerView = (RecyclerView) rootView.findViewById(R.id.user_list_recycler_view);
            setDetailClickListener();
            userAdapter = new UserAdapter(userList, detailClickListener, context);
            recyclerView.setAdapter(userAdapter);
            layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            recyclerView.setLayoutManager(layoutManager);
            fetchResults();
        }

        public void setDetailClickListener(){
            detailClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = v.getTag().toString();
                    UserDetailFragment userDetailFragment = new UserDetailFragment();
                    UserUtility utility = new UserUtility();
                    User user = utility.getModelFromMap(utility.buildMap(userList), email);
                    userDetailFragment.updateUser(user);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_fragment_container, userDetailFragment);
                   // fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                    fragmentTransaction.addToBackStack("next");
                    fragmentTransaction.commit();
                }
            };
        }

        public void fetchResults(){
            queries.put("results", String.valueOf(20));
            queries.put("inc", "name,location,cell,email,picture");
            queries.put("nat", "us");
            connectWithRetrofit(queries);

        }

        public void connectWithRetrofit(Map<String, String> queries){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);
            Call<UserResults> call = service.getResults(queries);
            call.enqueue(new retrofit2.Callback<UserResults>() {
                @Override
                public void onResponse(Call<UserResults> call, retrofit2.Response<UserResults> response) {
                    UserResults results = response.body();
                    userList.addAll(Arrays.asList(results.getResults()));
                    Log.d("retrofit: ", "onResponse: " + userList.size());
                    userAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(Call<UserResults> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }



    }
}


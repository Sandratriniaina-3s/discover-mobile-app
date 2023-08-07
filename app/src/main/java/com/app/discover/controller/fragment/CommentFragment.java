package com.app.discover.controller.fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import com.android.volley.VolleyError;
import com.app.discover.R;
import com.app.discover.adapter.CommentAdapter;
import com.app.discover.controller.DataManager;
import com.app.discover.dal.interfaces.CommentInterface;
import com.app.discover.dal.service.CommentService;
import com.app.discover.model.Comment;
import com.app.discover.model.User;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CommentFragment extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private CommentService commentService;
    private String url, siteId;
    private Gson gson;
    private DataManager dataManager;
    private Comment comment;
    private User user;
    private Comment[] comments;
    private CommentAdapter commentAdapter;
    private EditText commentValueInput;
    private ImageButton btnPostComment;
    private JSONObject jsonObject;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        init(view);

        if (getArguments() != null) {
            siteId = getArguments().getStringArray("SITES")[0];
            if(siteId != null){
                getAllSiteComment(siteId);
            }
        }

        commentValueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                comment.setValue(String.valueOf(commentValueInput.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        comment.setUser(user);

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isInputDataEmpty(commentValueInput)){

                    try {
                        jsonObject = new JSONObject(gson.toJson(comment));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    commentService.addComment(url + siteId, jsonObject, new CommentInterface() {
                        @Override
                        public void handleObjectResponse(JSONObject jsonObject) {

                            getAllSiteComment(siteId);
                            commentValueInput.setText("");
                        }

                        @Override
                        public void handleArrayResponse(JSONArray jsonArray) {

                        }

                        @Override
                        public void handleError(VolleyError volleyError) {

                        }
                    });
                }

            }
        });

        return view;
    }

    private void init(View view){
        commentAdapter = null;
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recycler_view_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        commentService = new CommentService(context);
        gson = new Gson();
        commentValueInput = view.findViewById(R.id.edit_text_comment_value);
        btnPostComment = view.findViewById(R.id.btn_post_comment);
        url = "https://discover-api.onrender.com/comment/site/";
        dataManager = DataManager.getInstance(context);
        jsonObject = null;
        comment = new Comment();
        user = new User();
        user.setId(dataManager.getSetting().getInformation().getUserId());
        siteId = null;
    }

    private void getAllSiteComment(String siteId){
        commentService.getAllComment(url+siteId, new CommentInterface() {
            @Override
            public void handleObjectResponse(JSONObject jsonObject) {

            }

            @Override
            public void handleArrayResponse(JSONArray jsonArray) {
                comments = gson.fromJson(jsonArray.toString(), Comment[].class);
                updateRecyclerView(context,comments);
            }

            @Override
            public void handleError(VolleyError volleyError) {

            }
        });
    }

    private void updateRecyclerView(Context context, Comment[] comments){
        commentAdapter = new CommentAdapter(context, comments);
        recyclerView.setAdapter(commentAdapter);
    }

    private Boolean isInputDataEmpty(EditText textInputEditText){

        String inputText = textInputEditText.getText().toString().trim();

        if (inputText.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

}
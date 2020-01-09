package com.mtsealove.github.buslinkerpt.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.mtsealove.github.buslinkerpt.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TutorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorialFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String title;
    private String contents;
    private String video;

    private OnFragmentInteractionListener mListener;

    public TutorialFragment() {
        // Required empty public constructor
    }

    public static TutorialFragment newInstance(String title, String contents, String video) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        args.putString(ARG_PARAM2, contents);
        args.putString(ARG_PARAM3, video);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_PARAM1);
            contents = getArguments().getString(ARG_PARAM2);
            video = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        TextView titleTv = view.findViewById(R.id.titleTv);
        TextView contentsTv = view.findViewById(R.id.contentsTv);
        VideoView videoView = view.findViewById(R.id.videoView);

        titleTv.setText(title);
        contentsTv.setText(contents);

        try {
            //set video file
            Uri videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/raw/" + video);
            videoView.setVideoURI(videoUri);
            //auto replay
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.seekTo(0);
                    mp.start();
                }
            });
            //play video
            videoView.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

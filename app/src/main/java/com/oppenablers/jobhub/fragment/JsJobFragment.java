package com.oppenablers.jobhub.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oppenablers.jobhub.R;
import com.oppenablers.jobhub.adapter.JobCardAdapter;
import com.oppenablers.jobhub.databinding.FragmentJsJobBinding;
import com.oppenablers.jobhub.model.Job;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.List;

public class JsJobFragment extends Fragment {

    private FragmentJsJobBinding binding;

    public JsJobFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJsJobBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Software Engineer", "Makati"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));
        jobs.add(new Job("Software Engineer", "Some company"));

        JobCardAdapter jobCardAdapter = new JobCardAdapter(jobs);

        CardStackLayoutManager layoutManager = new CardStackLayoutManager(getContext());
//        layoutManager.setTranslationInterval(0.9f);
//        layoutManager.setScaleInterval(0.9f);
        layoutManager.setStackFrom(StackFrom.Top);

        ArrayList<Direction> directions = new ArrayList<>();
        directions.add(Direction.Left);
        directions.add(Direction.Right);

        layoutManager.setDirections(directions);
        layoutManager.setCanScrollVertical(false);

        CardStackView cardStackView = binding.cardStackView;

        cardStackView.setLayoutManager(layoutManager);
        cardStackView.setAdapter(jobCardAdapter);

        binding.fabSwipeLeft.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });

        binding.fabSwipeRight.setOnClickListener(v -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .build();
            layoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
    }
}
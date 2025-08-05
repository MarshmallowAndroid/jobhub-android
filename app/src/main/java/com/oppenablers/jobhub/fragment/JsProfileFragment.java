package com.oppenablers.jobhub.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.oppenablers.jobhub.AuthManager;
import com.oppenablers.jobhub.activity.JsProfileSettingsActivity;
import com.oppenablers.jobhub.api.JobHubClient;
import com.oppenablers.jobhub.databinding.FragmentJsProfileBinding;
import com.oppenablers.jobhub.model.JobSeeker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class JsProfileFragment extends Fragment {

    FragmentJsProfileBinding binding;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    public JsProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentJsProfileBinding.inflate(inflater, container, false);

        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), o -> {
            if (o != null) {
                Context context = getContext();
                if (context == null) return;

                ContentResolver contentResolver = context.getContentResolver();
                try (InputStream is = contentResolver.openInputStream(o)) {
                    if (is == null) return;

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    while (true) {
                        int read = is.read(buffer);
                        if (read < 0) break;
                        baos.write(buffer);
                    }

                    JobHubClient.changeProfilePicture(baos.toByteArray(), new JobHubClient.JobHubCallbackVoid() {
                        @Override
                        public void onFailure() {
                            Log.d("profpic", "failed");
                        }

                        @Override
                        public void onSuccess() {
                            Log.d("profpic", "changed");
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.objectives.profileItemName.setText("Objectives");
        binding.education.profileItemName.setText("Education");
        binding.experience.profileItemName.setText("Work Experience");
        binding.skills.profileItemName.setText("Skills");
        binding.award.profileItemName.setText("Award and Certification");
        binding.character.profileItemName.setText("Character Preference");

//        binding.objectives.getRoot().setOnClickListener(v -> {
//            startActivity(createSettingIntent("Objectives", "objectives"));
//        });
//
//        binding.education.getRoot().setOnClickListener(v -> {
//            startActivity(createSettingIntent("Education", "education"));
//        });
//
//        binding.experience.getRoot().setOnClickListener(v -> {
//            startActivity(createSettingIntent("Work Experience", "experience"));
//        });
//
//        binding.skills.getRoot().setOnClickListener(v -> {
//            startActivity(createSettingIntent("Skills", "skills"));
//        });

        binding.objectives.btnEditSection.setOnClickListener(v -> {
            startActivity(createSettingIntent("Objectives", "objectives"));
        });

        binding.education.btnEditSection.setOnClickListener(v -> {
            startActivity(createSettingIntent("Education", "education"));
        });

        binding.experience.btnEditSection.setOnClickListener(v -> {
            startActivity(createSettingIntent("Work Experience", "experience"));
        });

        binding.skills.btnEditSection.setOnClickListener(v -> {
            startActivity(createSettingIntent("Skills", "skills"));
        });

        binding.profilePicture.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        updateInfo();
    }

    private Intent createSettingIntent(String title, String settingName) {
        Intent intent = new Intent(requireContext(), JsProfileSettingsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("profile_setting", settingName);
        return intent;
    }

    private void updateInfo() {
        Fragment fragment = this;

        JobHubClient.getProfilePicture(AuthManager.getCurrentUser().getUid(), new JobHubClient.JobHubCallback<byte[]>() {
            @Override
            public void onFailure() {
                Log.d("profile pic", "failed");
            }

            @Override
            public void onSuccess(byte[] result) {
                Glide.with(fragment).load(result).into(binding.profilePicture);
            }
        });

        JobHubClient.getAccountInfoJobSeeker(new JobHubClient.JobHubCallback<JobSeeker>() {
            @Override
            public void onFailure() {

            }

            @Override
            public void onSuccess(JobSeeker result) {

                binding.profileName.setText(result.getName());
                binding.profileName.setOnClickListener(v -> {
                    startActivity(createSettingIntent("Personal Info", "personal"));
                });
                binding.objectives.profileItemValue.setText(result.getObjectives());
                binding.objectives.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Objectives", "objectives"));
                });
                binding.education.profileItemValue.setText(result.getEducation());
                binding.education.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Education", "education"));
                });
                binding.experience.profileItemValue.setText(result.getWorkExperience());
                binding.experience.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Work Experience", "experience"));
                });
                binding.skills.profileItemValue.setText("");
                binding.skills.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Skills", "skills"));
                });
                binding.award.profileItemValue.setText("");
                binding.award.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Award and Certification", "award"));
                });
                binding.skills.profileItemValue.setText("");
                binding.skills.getRoot().setOnClickListener(v -> {
                    startActivity(createSettingIntent("Character Preference", "character"));
                });
            }
        });
    }
}


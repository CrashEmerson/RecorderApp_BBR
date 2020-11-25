package com.example.recorderapp_bbr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecorderFragment extends Fragment {

    ImageButton recordNowImgBtn, recordStopImgBtn;
    TextView recordStart_stop, audioFileTxtView;
    boolean isRecording;

    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;

    private MediaRecorder mediaRecorder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recorder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recordNowImgBtn = view.findViewById(R.id.recordNowImgBtn);
        recordStart_stop = view.findViewById(R.id.recordStart_stop);
        audioFileTxtView = view.findViewById(R.id.audioFileTxtView);

        audioFileTxtView.setText("");

        recordNowImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isRecording) {
                    stopRecording();

                    recordNowImgBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_record_pause, null));
                    Toast.makeText(getContext(), "stops Recording", Toast.LENGTH_SHORT).show();
                    isRecording = false;
                } else {
                    if(checkPermission()){
                        startRecording();
                        recordNowImgBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_record_now, null));
                        Toast.makeText(getContext(), "starts Recording", Toast.LENGTH_SHORT).show();
                        isRecording = true;
                    }
                }
            }
        });
    }

    private void stopRecording() {
        recordStart_stop.setText("Recording Stopped");
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startRecording() {

        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.UK);
        Date now = new Date();

        String recordFile = "Audio_" + formatter.format(now) + ".3gp";

        recordStart_stop.setText("Recording Started");
        audioFileTxtView.setText(recordFile);

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        }catch(IOException e){
            e.printStackTrace();
        }

        mediaRecorder.start();
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), recordPermission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{recordPermission}, PERMISSION_CODE);
            return false;
        }
    }
}
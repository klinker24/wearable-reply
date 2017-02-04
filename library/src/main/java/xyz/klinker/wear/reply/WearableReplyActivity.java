/*
 * Copyright (C) 2017 Luke Klinker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xyz.klinker.wear.reply;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import java.util.List;

public class WearableReplyActivity extends Activity implements ItemClickListener {

    private static final String ARG_CANNED_RESPONSES = "canned_responses";
    private static final String ARG_RESULT_TEXT = "result_text";

    private static final int ACTIVITY_REQUEST_CODE = 0;
    private static final int VOICE_REQUEST_CODE = 1;

    public static void start(Activity context) {
        start(context, null);
    }

    public static void start(Activity context, @ArrayRes int cannedResponseList) {
        start(context, context.getResources().getStringArray(cannedResponseList));
    }

    public static void start(Activity context, @Nullable String[] cannedResponses) {
        Intent intent = new Intent(context, WearableReplyActivity.class);
        intent.putExtra(ARG_CANNED_RESPONSES, cannedResponses);
        context.startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }

    public static String getResultText(Intent data) {
        if (data != null) {
            return data.getStringExtra(ARG_RESULT_TEXT);
        } else {
            return null;
        }
    }

    private View voiceReply;
    private View textReply;
    private ScrollView scrollView;
    private RecyclerView recyclerView;

    private TextAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wearreply_activity_reply);

        voiceReply = findViewById(R.id.voice);
        textReply = findViewById(R.id.text);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        voiceReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureSpeech();
            }
        });

        textReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextReplyActivity.start(WearableReplyActivity.this);
            }
        });

        CharSequence[] cannedResponses = getIntent().getStringArrayExtra(ARG_CANNED_RESPONSES);
        if (cannedResponses == null) {
            adapter = new TextAdapter(getResources().getStringArray(R.array.wearreply_reply_choices), this);
        } else {
            adapter = new TextAdapter(cannedResponses, this);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.getLayoutParams().height = (int) getResources().getDimension(R.dimen.wearreply_text_height) *
                adapter.getItemCount();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0,0);
            }
        });
    }

    private void captureSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, VOICE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String text = null;

        if (requestCode == VOICE_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            text = results.get(0);
        } else {
            text = TextReplyActivity.getResultText(data);
        }

        if (text != null) {
            finishWithResult(text);
        }
    }

    private void finishWithResult(CharSequence text) {
        Intent result = new Intent();
        result.putExtra(ARG_RESULT_TEXT, text);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    @Override
    public void onItemClick(int position) {
        finishWithResult(adapter.getText(position));
    }
}

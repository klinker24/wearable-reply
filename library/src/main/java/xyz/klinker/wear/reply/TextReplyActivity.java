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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TextReplyActivity extends Activity {

    private static final int ACTIVITY_REQUEST_CODE = 5;
    private static final String RESULT_TEXT = "result_text";

    protected static void start(Activity context) {
        Intent intent = new Intent(context, TextReplyActivity.class);
        context.startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
    }

    public static String getResultText(Intent data) {
        if (data != null) {
            return data.getStringExtra(RESULT_TEXT);
        } else {
            return null;
        }
    }

    private EditText text;
    private View confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wearreply_activity_text);

        text = (EditText) findViewById(R.id.text);
        confirm = findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithResult(text.getText().toString());
            }
        });
    }

    private void finishWithResult(CharSequence text) {
        Intent result = new Intent();
        result.putExtra(RESULT_TEXT, text);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}

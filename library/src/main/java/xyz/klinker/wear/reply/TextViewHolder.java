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

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public static TextViewHolder create(ViewGroup parent) {
        return create(parent, R.layout.wearreply_item_text);
    }

    public static TextViewHolder create(ViewGroup parent, @LayoutRes int res) {
        View root = LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
        return new TextViewHolder(root);
    }

    private TextViewHolder(View itemView) {
        super(itemView);

        if (itemView instanceof TextView) {
            textView = (TextView) itemView;
        }
    }
}

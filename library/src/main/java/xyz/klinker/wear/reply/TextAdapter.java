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

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {

    private static final int TYPE_TEXT = 1;
    private static final int TYPE_FOOTER = 2;

    private CharSequence[] texts;
    private ItemClickListener itemClickListener;

    public TextAdapter(@NonNull CharSequence[] texts, ItemClickListener itemClickListener) {
        this.texts = texts;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_TEXT;
        }
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return TextViewHolder.create(parent, R.layout.wearreply_item_footer);
        }

        return TextViewHolder.create(parent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(TextViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (position == getItemCount() - 1) {
            return;
        }

        holder.textView.setText("•  " + getText(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(position);
            }
        });
    }

    public CharSequence getText(int position) {
        return texts[position];
    }

    @Override
    public int getItemCount() {
        return texts.length + 1;
    }
}

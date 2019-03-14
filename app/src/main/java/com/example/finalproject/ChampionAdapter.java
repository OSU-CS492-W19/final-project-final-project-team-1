package com.example.finalproject;

import android.preference.ListPreference;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.data.AllChampionItem;

import java.util.List;

public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ChampionItemViewHolder> {
    private List<AllChampionItem> mAllChampionItems;
    private OnChampionItemClickListener mChampionItemClickListener;

    public interface OnChampionItemClickListener {
        void onChampionItemClick(AllChampionItem forecastItem);
    }

    public ChampionAdapter(OnChampionItemClickListener clickListener) {
        mChampionItemClickListener = clickListener;
    }

    public void updateChampionItems(List<AllChampionItem> championItems) {
        mAllChampionItems = championItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAllChampionItems != null) {
            return mAllChampionItems.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public ChampionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.champion_list_item, parent, false);
        return new ChampionItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionItemViewHolder holder, int position) {
        holder.bind(mAllChampionItems.get(position));
    }

    class ChampionItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mChampionNameTV;
        private ImageView mChampionIconIV;

        public ChampionItemViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(AllChampionItem championItem) {

        }

        @Override
        public void onClick(View v) {
            AllChampionItem championItem = mAllChampionItems.get(getAdapterPosition());
            mChampionItemClickListener.onChampionItemClick(championItem);
        }
    }
}

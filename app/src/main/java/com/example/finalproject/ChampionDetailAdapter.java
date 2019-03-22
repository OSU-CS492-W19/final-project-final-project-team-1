package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.ChampionDetailItem;
import com.example.finalproject.utils.LOLChampionUtils;

import java.util.List;

public class ChampionDetailAdapter extends RecyclerView.Adapter<ChampionDetailAdapter.ChampionDetailViewHolder> {

    private static final String TAG = ChampionDetailAdapter.class.getSimpleName();

    private List<ChampionDetailItem> mChampionDetailItem;
    private OnChampionDetailItemClickListener mChampionDetailItemClickListener;
    private Context context;

    public interface OnChampionDetailItemClickListener {
        void onChampionDetailItemClick(ChampionDetailItem championDetailItem);
    }

    public ChampionDetailAdapter(ChampionDetailAdapter.OnChampionDetailItemClickListener clickListener, Context context) {
        mChampionDetailItemClickListener = clickListener;
        this.context = context;
    }

    public void updateChampionItems(List<ChampionDetailItem> championDetailItems) {
        mChampionDetailItem = championDetailItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mChampionDetailItem != null) {
            return mChampionDetailItem.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public ChampionDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.champion_detail_list_item, parent, false);
        return new ChampionDetailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChampionDetailViewHolder holder, int position) {
        holder.bind(mChampionDetailItem.get(position));
    }

    class ChampionDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mChampionNameTV;
        private TextView mChampionTitleTV;
        private TextView mChampionLoreTV;
        private ImageView mChampionIconIV;
        private ImageView mChampionSplashArtIV;
        private ImageView mChampionPassiveIV;
        private ImageView mChampionQIV;
        private ImageView mChampionWIV;
        private ImageView mChampionEIV;
        private ImageView mChampionRIV;
        private TextView mChampionPassiveTV;
        private TextView mChampionQTV;
        private TextView mChampionWTV;
        private TextView mChampionETV;
        private TextView mChampionRTV;

        public ChampionDetailViewHolder(View itemView) {
            super(itemView);
            mChampionNameTV = itemView.findViewById(R.id.tv_champion_detail_name);
            mChampionTitleTV = itemView.findViewById(R.id.tv_champion_detail_title);
//            mChampionLoreTV = itemView.findViewById(R.id.tv_champion_detail_lore);
            mChampionIconIV = itemView.findViewById(R.id.iv_champion_detail_icon);
            mChampionSplashArtIV = itemView.findViewById(R.id.iv_champion_splash_art);
            mChampionPassiveIV = itemView.findViewById(R.id.iv_champion_passive_icon);
            mChampionPassiveTV = itemView.findViewById(R.id.tv_champion_passive_name);
            mChampionQIV = itemView.findViewById(R.id.iv_champion_q_icon);
            mChampionQTV = itemView.findViewById(R.id.tv_champion_q_name);
            mChampionWIV = itemView.findViewById(R.id.iv_champion_w_icon);
            mChampionWTV = itemView.findViewById(R.id.tv_champion_w_name);
            mChampionEIV = itemView.findViewById(R.id.iv_champion_e_icon);
            mChampionETV = itemView.findViewById(R.id.tv_champion_e_name);
            mChampionRIV = itemView.findViewById(R.id.iv_champion_r_icon);
            mChampionRTV = itemView.findViewById(R.id.tv_champion_r_name);
            itemView.setOnClickListener(this);
        }

        public void bind(ChampionDetailItem championDetailItem) {
            String champion_name = mChampionNameTV.getContext().getString(R.string.champion_name, championDetailItem.champion_name);
            String champion_title = mChampionTitleTV.getContext().getString(R.string.champion_title, championDetailItem.champion_title);
//            String champion_lore = mChampionLoreTV.getContext().getString(R.string.champion_lore, championDetailItem.champion_lore);
            String champion_id = championDetailItem.champion_id;
            String champion_passive_id = championDetailItem.champion_passive_image;
            String champion_q_id = championDetailItem.champion_q_id;
            String champion_w_id = championDetailItem.champion_w_id;
            String champion_e_id = championDetailItem.champion_e_id;
            String champion_r_id = championDetailItem.champion_r_id;
            String champion_passive_name = mChampionPassiveTV.getContext().getString(R.string.champion_name, championDetailItem.champion_passive_name);
            String champion_q_name = mChampionQTV.getContext().getString(R.string.champion_name, championDetailItem.champion_q_name);
            String champion_w_name = mChampionWTV.getContext().getString(R.string.champion_name, championDetailItem.champion_w_name);
            String champion_e_name = mChampionETV.getContext().getString(R.string.champion_name, championDetailItem.champion_e_name);
            String champion_r_name = mChampionRTV.getContext().getString(R.string.champion_name, championDetailItem.champion_r_name);

            String champion_skin = championDetailItem.champion_default_skin;
            champion_skin = champion_id + champion_skin;

            mChampionNameTV.setText(champion_name);
            mChampionTitleTV.setText(champion_title);
//            mChampionLoreTV.setText(champion_lore);
            mChampionPassiveTV.setText(champion_passive_name);
            mChampionQTV.setText(champion_q_name);
            mChampionWTV.setText(champion_w_name);
            mChampionETV.setText(champion_e_name);
            mChampionRTV.setText(champion_r_name);

            String skinURL = LOLChampionUtils.buildChampionSplashArtURL(champion_skin);
            Glide.with(mChampionSplashArtIV.getContext()).load(skinURL).into(mChampionSplashArtIV);

            String champion_icon = champion_id+".png";
            String iconURL = LOLChampionUtils.buildChampionIconURL(champion_icon);
            Glide.with(mChampionIconIV.getContext()).load(iconURL).into(mChampionIconIV);

            String passiveURL = LOLChampionUtils.buildChampionPassiveURL(champion_passive_id);
            Glide.with(mChampionPassiveIV.getContext()).load(passiveURL).into(mChampionPassiveIV);

            String q_icon = champion_q_id+".png";
            String qURL = LOLChampionUtils.buildChampionSpellURL(q_icon);
            Glide.with(mChampionQIV.getContext()).load(qURL).into(mChampionQIV);

            String w_icon = champion_w_id+".png";
            String wURL = LOLChampionUtils.buildChampionSpellURL(w_icon);
            Glide.with(mChampionWIV.getContext()).load(wURL).into(mChampionWIV);

            String e_icon = champion_e_id+".png";
            String eURL = LOLChampionUtils.buildChampionSpellURL(e_icon);
            Glide.with(mChampionEIV.getContext()).load(eURL).into(mChampionEIV);

            String r_icon = champion_r_id+".png";
            String rURL = LOLChampionUtils.buildChampionSpellURL(r_icon);
            Glide.with(mChampionRIV.getContext()).load(rURL).into(mChampionRIV);
        }

        @Override
        public void onClick(View v) {
            ChampionDetailItem championDetailItem = mChampionDetailItem.get(getAdapterPosition());
            mChampionDetailItemClickListener.onChampionDetailItemClick(championDetailItem);
            String webURL = LOLChampionUtils.buildChampionWebURL(championDetailItem.champion_id);
            Log.d(TAG, "champion url is: " + webURL);
            Uri championURL = Uri.parse(webURL);
            Intent intent = new Intent(Intent.ACTION_VIEW, championURL);
//            if (intent.resolveActivity(getPackageManager()) != null) {
                context.startActivity(intent);
//            }
        }
    }

}

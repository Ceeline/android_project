package mobile_dev.project.android_project.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mobile_dev.project.android_project.R;
import mobile_dev.project.android_project.frag_Cocktail.BitmapDownloaderTask;
import mobile_dev.project.android_project.frag_Cocktail.Cocktail;

public class CocktailsListAdapter extends RecyclerView.Adapter<CocktailsListAdapter.CocktailViewHolder> {


    class CocktailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nameCocktail;
        private final ImageView imageCocktail;
        private final Context context;
        private int mPosition;
        OnCocktailListener onCocktailListener;

        private CocktailViewHolder(View itemView, Context context, OnCocktailListener onCocktailListener) {
            super(itemView);
            nameCocktail = itemView.findViewById(R.id.cocktail_Name);
            imageCocktail = itemView.findViewById(R.id.cocktail_Img);
            this.context = context;
            itemView.setOnClickListener(this);
            this.onCocktailListener = onCocktailListener;
        }

        public void setData(String name, String url, int position) {
            BitmapDownloaderTask downloadImage = new BitmapDownloaderTask(context, url, imageCocktail);
            nameCocktail.setText(name);
            mPosition = position;
            downloadImage.doInBackground();
        }

        @Override
        public void onClick(View v) {
            onCocktailListener.OnCocktailClick(mCocktails.get(mPosition));
        }
    }


    private final LayoutInflater mInflater;
    private List<Cocktail> mCocktails;
    private final Context context;
    private final OnCocktailListener onCocktailListener;

    public CocktailsListAdapter(Context context, OnCocktailListener onCocktailListener) {
        mInflater = LayoutInflater.from(context);
        this.onCocktailListener = onCocktailListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = mInflater.inflate(R.layout.item_cocktail_list, parent, false);
        return new CocktailViewHolder(itemView, context, onCocktailListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder holder, int position) {
        if (mCocktails != null) {
            Cocktail current = mCocktails.get(position);
            holder.setData(current.name, current.image, position);
        } else {
            holder.nameCocktail.setText("No Cocktails");
        }
    }

    public void setCocktails(List<Cocktail> cocktail) {
        mCocktails = cocktail;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCocktails != null)
            return mCocktails.size();
        else return 0;
    }

    public interface OnCocktailListener {
        void OnCocktailClick(Cocktail cocktail);
    }


}

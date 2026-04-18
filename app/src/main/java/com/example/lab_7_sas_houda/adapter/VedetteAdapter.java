package com.example.lab_7_sas_houda.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lab_7_sas_houda.R;
import com.example.lab_7_sas_houda.beans.Vedette;
import com.example.lab_7_sas_houda.service.VedetteService;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class VedetteAdapter extends RecyclerView.Adapter<VedetteAdapter.VedetteHolder>
        implements Filterable {

    private List<Vedette> catalogue;
    private List<Vedette> catalogueFiltré;
    private Context contexte;
    private FiltreVedette filtre;

    public VedetteAdapter(Context contexte, List<Vedette> catalogue) {
        this.contexte = contexte;
        this.catalogue = catalogue;
        this.catalogueFiltré = new ArrayList<>(catalogue);
        this.filtre = new FiltreVedette(this);
    }

    @NonNull
    @Override
    public VedetteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contexte).inflate(R.layout.item_vedette, parent, false);
        final VedetteHolder holder = new VedetteHolder(v);

        holder.itemView.setOnClickListener(vue -> {
            View popup = LayoutInflater.from(contexte)
                    .inflate(R.layout.popup_modifier_note, null, false);

            CircleImageView imgPopup = popup.findViewById(R.id.imgPopup);
            RatingBar ratingPopup   = popup.findViewById(R.id.ratingPopup);
            TextView tvPopupId      = popup.findViewById(R.id.tvPopupId);

            Bitmap bmp = ((BitmapDrawable)
                    ((ImageView) vue.findViewById(R.id.imgVedette)).getDrawable()).getBitmap();
            imgPopup.setImageBitmap(bmp);
            ratingPopup.setRating(
                    ((RatingBar) vue.findViewById(R.id.ratingVedette)).getRating());
            tvPopupId.setText(
                    ((TextView) vue.findViewById(R.id.tvIdentifiant)).getText().toString());

            new AlertDialog.Builder(contexte)
                    .setTitle("Modifier la note")
                    .setMessage("Donnez une note entre 1 et 5 :")
                    .setView(popup)
                    .setPositiveButton("Confirmer", (dialog, which) -> {
                        float nouvelleNote = ratingPopup.getRating();
                        int idVedette = Integer.parseInt(tvPopupId.getText().toString());
                        Vedette v2 = VedetteService.getInstance().findById(idVedette);
                        v2.setNote(nouvelleNote);
                        VedetteService.getInstance().update(v2);
                        notifyItemChanged(holder.getAdapterPosition());
                    })
                    .setNegativeButton("Annuler", null)
                    .show();
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VedetteHolder holder, int position) {
        Vedette v = catalogueFiltré.get(position);

        Glide.with(contexte)
                .asBitmap()
                .load(v.getPhoto())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.imgVedette);

        holder.tvPrenom.setText(v.getPrenom().toUpperCase());
        holder.ratingVedette.setRating(v.getNote());
        holder.tvIdentifiant.setText(String.valueOf(v.getId()));
    }

    @Override public int getItemCount() { return catalogueFiltré.size(); }

    @Override public Filter getFilter() { return filtre; }

    public class VedetteHolder extends RecyclerView.ViewHolder {
        CircleImageView imgVedette;
        TextView tvPrenom, tvIdentifiant;
        RatingBar ratingVedette;

        public VedetteHolder(@NonNull View itemView) {
            super(itemView);
            imgVedette    = itemView.findViewById(R.id.imgVedette);
            tvPrenom      = itemView.findViewById(R.id.tvPrenom);
            tvIdentifiant = itemView.findViewById(R.id.tvIdentifiant);
            ratingVedette = itemView.findViewById(R.id.ratingVedette);
        }
    }

    public class FiltreVedette extends Filter {
        public RecyclerView.Adapter adaptateur;

        public FiltreVedette(RecyclerView.Adapter adaptateur) {
            this.adaptateur = adaptateur;
        }

        @Override
        protected FilterResults performFiltering(CharSequence sequence) {
            List<Vedette> résultat = new ArrayList<>();
            if (sequence == null || sequence.length() == 0) {
                résultat.addAll(catalogue);
            } else {
                String motif = sequence.toString().toLowerCase().trim();
                for (Vedette v : catalogue) {
                    if (v.getPrenom().toLowerCase().startsWith(motif)) {
                        résultat.add(v);
                    }
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = résultat;
            fr.count  = résultat.size();
            return fr;
        }

        @Override
        protected void publishResults(CharSequence sequence, FilterResults fr) {
            catalogueFiltré = (List<Vedette>) fr.values;
            adaptateur.notifyDataSetChanged();
        }
    }
}
package ma.emsi.projetws.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ma.emsi.projetws.R;
import ma.emsi.projetws.beans.Etudiant;

public class EtudiantAdapter  extends RecyclerView.Adapter<EtudiantAdapter.StarViewHolder> {
    private static final String TAG = "StarAdapter";
    private List<Etudiant> stars;
    private Context context;


    public EtudiantAdapter(Context context, List<Etudiant> stars) {
        this.stars = stars;
        this.context = context;
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.etudiant_iteam,
                viewGroup, false);
        return new StarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder starViewHolder, int i) {
        Log.d(TAG, "onBindView call ! " + i);
        Glide.with(context)
                .asBitmap()
                .load(stars.get(i).getImage())
                .apply(new RequestOptions().override(100, 100))
                .into(starViewHolder.img);
        starViewHolder.nom.setText(stars.get(i).getNom().toUpperCase());
        starViewHolder.prenom.setText(stars.get(i).getPrenom().toUpperCase());
        starViewHolder.ville.setText(stars.get(i).getVille().toUpperCase());
        starViewHolder.sexe.setText(stars.get(i).getSexe().toUpperCase());
        starViewHolder.idss.setText(stars.get(i).getId() + "");
    }

    @Override
    public int getItemCount() {
        return stars.size();
    }

    public class StarViewHolder extends RecyclerView.ViewHolder {
        TextView idss;
        ImageView img;
        TextView nom;
        TextView prenom;
        TextView ville;
        TextView sexe;
        RelativeLayout parent;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            idss = itemView.findViewById(R.id.ids);
            img = itemView.findViewById(R.id.img);
            nom = itemView.findViewById(R.id.nom);
            prenom = itemView.findViewById(R.id.prenom);
            ville = itemView.findViewById(R.id.ville);
            sexe = itemView.findViewById(R.id.sexe);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}


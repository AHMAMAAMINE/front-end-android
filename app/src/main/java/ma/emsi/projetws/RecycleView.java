package ma.emsi.projetws;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.projetws.adapter.EtudiantAdapter;
import ma.emsi.projetws.beans.Etudiant;
import ma.emsi.projetws.service.EtudiantService;

public class RecycleView extends AppCompatActivity {
    private List<Etudiant> etudiants;
    private RecyclerView recyclerView;
    private EtudiantAdapter starAdapter = null;
    private EtudiantService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        etudiants = new ArrayList<>();
        service = EtudiantService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new EtudiantAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void init() {
        service.create(new Etudiant("Saad","thami","Agadir","homme", "https://png.pngtree.com/png-vector/20190411/ourlarge/pngtree-business-male-icon-vector-png-image_916468.jpg"));
        service.create(new Etudiant("amine","ahmama","Marrakech","homme", "https://images8.alphacoders.com/746/thumb-1920-746199.jpg"));

    }



}

package ma.emsi.projetws.service;

import java.util.ArrayList;
import java.util.List;

import ma.emsi.projetws.beans.Etudiant;
import ma.emsi.projetws.dao.IDao;

public class EtudiantService  implements IDao<Etudiant> {
        private List<Etudiant> Etudiants;
        private static EtudiantService instance;
    private EtudiantService() {
        this.Etudiants = new ArrayList<>();
    }

        public static EtudiantService getInstance () {
        if (instance == null)
            instance = new EtudiantService();
        return instance;
    }
        @Override
        public boolean create (Etudiant o){
        return Etudiants.add(o);
    }

    @Override
    public boolean update(Etudiant o) {
        return false;
    }

    @Override
        public boolean delete (Etudiant o){
        return Etudiants.remove(o);
    }
        @Override
        public Etudiant findById ( int id){
        for (Etudiant s : Etudiants) {
            if (s.getId() == id)
                return s;
        }
        return null;
    }
        @Override
        public List<Etudiant> findAll () {
        return Etudiants;
    }
}


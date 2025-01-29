package gal.cifpacarballeira.unidad4_tarea7gestordeberes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class HomeworkListViewModel  extends ViewModel {
    private MutableLiveData<ArrayList<Homework>> lista = new MutableLiveData<>(new ArrayList<Homework>());

    public LiveData<ArrayList<Homework>> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Homework> nuevaLista) {
        this.lista.setValue(nuevaLista);
    }

    public void addHomework(Homework homework){
        ArrayList<Homework> temporal= this.lista.getValue();

        if(homework != null){
            temporal.add(homework);
        }
        this.lista.setValue(temporal);
    }

}
